package postbored.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Represents a record in the album_tracks table.
 */
@DynamoDBTable(tableName = "userTable")
public class User {

    private String userID;
    private List<String> friendsList;
    private List<String> messageHistory;

    @DynamoDBHashKey(attributeName = "userID")
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "friendsList-index", attributeName = "friendsList")
    @DynamoDBAttribute(attributeName = "friendsList")
    public String getFriendsList() {
        return convertListToString(friendsList);
    }

    public void setFriendsList(List<String> friendsList) {
        this.friendsList = friendsList;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "messageHistory-index", attributeName = "messageHistory")
    @DynamoDBAttribute(attributeName = "messageHistory")
    public String getMessageHistory() {
        return convertListToString(messageHistory);
    }

    public void setMessageHistory(List<String> messageHistory) {
        this.messageHistory = messageHistory;
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
        return Objects.equals(userID, user.userID) && Objects.equals(friendsList, user.friendsList) && Objects.equals(messageHistory, user.messageHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, friendsList, messageHistory);
    }
}
