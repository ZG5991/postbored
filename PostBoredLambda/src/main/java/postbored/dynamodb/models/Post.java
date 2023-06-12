package postbored.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import postbored.utilities.ListConverter;
import postbored.utilities.LocalDateTimeConverter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Represents a record in the playlists table.
 */

@DynamoDBTable(tableName = "posts")
public class Post {

    private String postID;
    private LocalDateTime dateSent;
    private String postBody;
    private String posterID;
    private String posterName;
    private Integer likesCounter;

    @DynamoDBHashKey(attributeName = "postID")
    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    @DynamoDBRangeKey(attributeName = "dateSent")
    public LocalDateTime getDateSent() {
        return dateSent;
    }

    public void setDateSent(LocalDateTime dateSent) {
        this.dateSent = dateSent;
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
        return Objects.equals(postID, post.postID) && Objects.equals(dateSent, post.dateSent) && Objects.equals(postBody, post.postBody)
                && Objects.equals(posterID, post.posterID) && Objects.equals(likesCounter, post.likesCounter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postID, dateSent, postBody, posterID, likesCounter);
    }
}
