package postbored.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import postbored.utilities.LocalDateTimeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@DynamoDBTable(tableName = "comments")
public class Comment {

    private final LocalDateTimeConverter converter = new LocalDateTimeConverter();

    private String commentID;
    private LocalDateTime timeSent;
    private String commentContent;
    private String commenterID;
    private String postID;

    @DynamoDBHashKey(attributeName = "commentID")
    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    @DynamoDBRangeKey(attributeName = "timeSent")
    public String getTimeSent() {
        return converter.localTimeToString(timeSent);
    }

    public void setTimeSent(LocalDateTime timeSent) {
        this.timeSent = timeSent;
    }

    @DynamoDBAttribute(attributeName = "commentContent")
    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    @DynamoDBAttribute(attributeName = "commenterID")
    public String getCommenterID() {
        return commenterID;
    }

    public void setCommenterID(String commenterID) {
        this.commenterID = commenterID;
    }

    @DynamoDBAttribute(attributeName = "postID")
    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(commentID, comment.commentID) && Objects.equals(timeSent, comment.timeSent) && Objects.equals(commentContent, comment.commentContent) && Objects.equals(commenterID, comment.commenterID) && Objects.equals(postID, comment.postID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentID, timeSent, commentContent, commenterID, postID);
    }
}
