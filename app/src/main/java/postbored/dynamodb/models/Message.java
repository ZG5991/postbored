package postbored.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a record in the playlists table.
 */
@DynamoDBTable(tableName = "messageTable")
public class Message {

    private String messageID;
    private LocalDateTime timeSent;
    private String messageContent;
    private String posterID;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "messageID-index", attributeName = "messageID")
    @DynamoDBHashKey(attributeName = "messageID")
    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "posterID_timeSent-index", attributeName = "timeSent")
    @DynamoDBRangeKey(attributeName = "timeSent")
    public String getTimeSent() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return timeSent.format(formatter);
    }

    public void setTimeSent(LocalDateTime timeSent) {
        this.timeSent = timeSent;
    }

    @DynamoDBAttribute(attributeName = "messageContent")
    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "posterID_timeSent-index", attributeName = "posterID")
    @DynamoDBAttribute(attributeName = "posterID")
    public String getPosterID() {
        return posterID;
    }

    public void setPosterID(String posterID) {
        this.posterID = posterID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(messageID, message.messageID) && Objects.equals(timeSent, message.timeSent) && Objects.equals(messageContent, message.messageContent) && Objects.equals(posterID, message.posterID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageID, timeSent, messageContent, posterID);
    }
}
