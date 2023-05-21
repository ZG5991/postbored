package postbored.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import postbored.utilities.LocalDateTimeConverter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Represents a record in the playlists table.
 */
@DynamoDBTable(tableName = "posts")
public class Post {

    private final LocalDateTimeConverter converter = new LocalDateTimeConverter();

    private String postID;
    private LocalDateTime dateSent;
    private String postTitle;
    private String postBody;
    private String posterID;
    private String posterName;
    private String topic;
    private List<String> comments;
    private Integer likesCounter;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "post-date-index", attributeName = "postID")
    @DynamoDBHashKey(attributeName = "postID")
    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    @DynamoDBIndexRangeKey(globalSecondaryIndexNames =
            {"post-date-index", "topic-index", "post-comments-index"}, attributeName = "dateSent")
    public String getDateSent() {
        return converter.localDateToString(dateSent);
    }

    public void setDateSent(LocalDateTime dateSent) {
        this.dateSent = dateSent;
    }

    @DynamoDBAttribute(attributeName = "postTitle")
    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    @DynamoDBAttribute(attributeName = "postBody")
    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postContent) {
        this.postBody = postContent;
    }

    @DynamoDBAttribute(attributeName = "posterID")
    public String getPosterID() {
        return posterID;
    }

    public void setPosterID(String posterID) {
        this.posterID = posterID;
    }

    @DynamoDBAttribute(attributeName = "posterName")
    public String getPosterName() {
        return posterName;
    }

    public void setPosterName(String posterName) {
        this.posterName = posterName;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "topic-index", attributeName = "topic")
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "post-comments-index", attributeName = "comments")
    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    @DynamoDBAttribute(attributeName = "likesCounter")
    public Integer getLikesCounter() {
        return likesCounter;
    }

    public void setLikesCounter(Integer likesCounter) {
        this.likesCounter = likesCounter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(postID, post.postID) && Objects.equals(dateSent, post.dateSent) && Objects.equals(postTitle, post.postTitle) && Objects.equals(postBody, post.postBody) && Objects.equals(posterID, post.posterID) && Objects.equals(topic, post.topic) && Objects.equals(comments, post.comments) && Objects.equals(likesCounter, post.likesCounter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postID, dateSent, postTitle, postBody, posterID, topic, comments, likesCounter);
    }
}
