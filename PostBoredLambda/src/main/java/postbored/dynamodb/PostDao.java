package postbored.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDeleteExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import postbored.dynamodb.models.Post;

import java.time.LocalDateTime;
import java.util.*;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Accesses data for a playlist using {@link Post} to represent the model in DynamoDB.
 */
@Singleton
public class PostDao {
    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates a PlaylistDao object.
     *
     * @param dynamoDbMapper   the {@link DynamoDBMapper} used to interact with the playlists table
     *
     */
    @Inject
    public PostDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     * Returns the {@link Post} corresponding to the specified id.
     *
     * @param id the Message ID
     * @return the stored Playlist, or null if none was found.
     */
    public Post getPost(String id) {
        Post post = dynamoDbMapper.load(Post.class, id);

        if (post == null) {
            throw new IllegalArgumentException("Could not find message with id " + id);
        }

        return post;
    }

    public List<Post> getPostsByPosterName(String posterName) {
        DynamoDBScanExpression expression = new DynamoDBScanExpression();
        expression.withFilterExpression("posterName = :posterName")
                .withExpressionAttributeValues(Map.of(":posterName", new AttributeValue().withS(posterName)));

        return dynamoDbMapper.scan(Post.class, expression);
    }

    public List<Post> getAllPosts() {
        return this.dynamoDbMapper.scan(Post.class, new DynamoDBScanExpression());
    }

    /**
     * Saves (creates or updates) the given post.
     * Initializes non-user input fields for a new unique post.
     * @param post The playlist to save
     */
    public Post savePost(Post post) {
        this.dynamoDbMapper.save(post);
        return post;
    }


    /**
     * deletes the given post
     *
     * @param post The post to delete from the table
     *
     */
    public void deletePost(Post post) {
        this.dynamoDbMapper.delete(post);
    }

    public void likePost(String id) {
        Post post = this.dynamoDbMapper.load(Post.class, id);
        Integer currentLikes = post.getLikesCounter();
        post.setLikesCounter(currentLikes != null ? currentLikes + 1 : 1);
        this.dynamoDbMapper.save(post);
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
    public List<Post> searchPosts(String[] criteria) {
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
                        filterExpressionPart("postID", valueMapNamePrefix, i));
                tagsFilterExpression.append(
                        filterExpressionPart("timeSent", valueMapNamePrefix, i));
            }

            dynamoDBScanExpression.setExpressionAttributeValues(valueMap);
            dynamoDBScanExpression.setFilterExpression(
                    "(" + nameFilterExpression + ") or (" + tagsFilterExpression + ")");
        }

        return this.dynamoDbMapper.scan(Post.class, dynamoDBScanExpression);
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
