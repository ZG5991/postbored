package postbored.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import postbored.dynamodb.models.Comment;
import postbored.dynamodb.models.Post;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Accesses data for a playlist using {@link Post} to represent the model in DynamoDB.
 */
@Singleton
public class CommentDao {
    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates a PlaylistDao object.
     *
     * @param dynamoDbMapper   the {@link DynamoDBMapper} used to interact with the playlists table
     *
     */
    @Inject
    public CommentDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     * Returns the {@link Comment} corresponding to the specified id.
     *
     * @param id the Comment ID
     * @return the stored Comment, or null if none was found.
     */
    public Comment getComment(String id) {
        Comment post = this.dynamoDbMapper.load(Comment.class, id);

        if (post == null) {
            throw new IllegalArgumentException("Could not find comment with id " + id);
        }

        return post;
    }

    /**
     * Saves (creates or updates) the given playlist.
     *
     * @param comment The comment to save
     * @return The Comment object that was saved
     */
    public Comment saveComment(Comment comment) {
        this.dynamoDbMapper.save(comment);
        return comment;
    }

    /**
     * Perform a search (via a "scan") of the playlist table for playlists matching the given criteria.
     *
     * Both "playlistName" and "tags" attributes are searched.
     * The criteria are an array of Strings. Each element of the array is search individually.
     * ALL elements of the criteria array must appear in the playlistName or the tags (or both).
     * Searches are CASE SENSITIVE.
     *
     * @param criteria an array of String containing search criteria.
     * @return a List of Playlist objects that match the search criteria.
     */
    public List<Comment> searchPosts(String[] criteria) {
        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();

        if (criteria.length > 0) {
            Map<String, AttributeValue> valueMap = new HashMap<>();
            String valueMapNamePrefix = ":c";

            StringBuilder nameFilterExpression = new StringBuilder();
            StringBuilder tagsFilterExpression = new StringBuilder();

            for (int i = 0; i < criteria.length; i++) {
                valueMap.put(valueMapNamePrefix + i,
                        new AttributeValue().withS(criteria[i]));
                nameFilterExpression.append(
                        filterExpressionPart("commentID", valueMapNamePrefix, i));
                tagsFilterExpression.append(
                        filterExpressionPart("timeSent", valueMapNamePrefix, i));
            }

            dynamoDBScanExpression.setExpressionAttributeValues(valueMap);
            dynamoDBScanExpression.setFilterExpression(
                    "(" + nameFilterExpression + ") or (" + tagsFilterExpression + ")");
        }

        return this.dynamoDbMapper.scan(Comment.class, dynamoDBScanExpression);
    }

    private StringBuilder filterExpressionPart(String target, String valueMapNamePrefix, int position) {
        String possiblyAnd = position == 0 ? "" : "and ";
        return new StringBuilder()
                .append(possiblyAnd)
                .append("contains(")
                .append(target)
                .append(", ")
                .append(valueMapNamePrefix).append(position)
                .append(") ");
    }
}