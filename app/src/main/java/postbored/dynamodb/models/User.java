package postbored.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Represents a record in the album_tracks table.
 */
@DynamoDBTable(tableName = "users")
public class User {

    private String userID;
    private List<String> postHistory;
    private List<String> commentHistory;
    @DynamoDBHashKey(attributeName = "userID")
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @DynamoDBAttribute(attributeName = "commentHistory")
    public String getCommentHistory() {
        return convertListToString(commentHistory);
    }

    public void setCommentHistory(List<String> commentHistory) {
        this.commentHistory = commentHistory;
    }

    @DynamoDBAttribute(attributeName = "postHistory")
    public String getPostHistory() {
        return convertListToString(postHistory);
    }

    public void setPostHistory(List<String> postHistory) {
        this.postHistory = postHistory;
    }

    public String convertListToString(List<String> stringList) {
        StringJoiner joiner = new StringJoiner(",");
        for (String str : stringList) {
            joiner.add(str);
        }
        return joiner.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(userID, user.userID) && Objects.equals(commentHistory, user.commentHistory) && Objects.equals(postHistory, user.postHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, commentHistory, postHistory);
    }
}
