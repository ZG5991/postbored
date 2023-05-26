package postbored.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDateTime;
import java.util.List;

@JsonDeserialize(builder = NewPostRequest.Builder.class)
public class NewPostRequest {

    private String postID;
    private LocalDateTime timeSent;
    private String postTitle;
    private String postBody;
    private String posterID;
    private String posterName;
    private String topic;
    private List<String> comments;
    private Integer likesCounter;

    public NewPostRequest() {}

    public NewPostRequest(String postID, LocalDateTime timeSent, String postTitle, String postBody,
                          String posterID, String posterName, String topic, List<String> comments, Integer likesCounter) {
        this.postID = postID;
        this.timeSent = timeSent;
        this.postTitle = postTitle;
        this.postBody = postBody;
        this.posterID = posterID;
        this.posterName = posterName;
        this.topic = topic;
        this.comments = comments;
        this.likesCounter = likesCounter;
    }

    public String getPostID() {
        return postID;
    }

    public LocalDateTime getTimeSent() {
        return timeSent;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostBody() {
        return postBody;
    }

    public String getPosterID() {
        return posterID;
    }

    public String getPosterName() {
        return posterName;
    }

    public String getTopic() {
        return topic;
    }

    public List<String> getComments() {
        return comments;
    }

    public Integer getLikesCounter() {
        return 0;
    }

    @Override
    public String toString() {
        return "NewPostRequest{" +
                "postID='" + postID + '\'' +
                ", timeSent=" + timeSent +
                ", postTitle='" + postTitle + '\'' +
                ", postBody='" + postBody + '\'' +
                ", posterID='" + posterID + '\'' +
                ", posterName='" + posterName + '\'' +
                ", topic='" + topic + '\'' +
                ", comments=" + comments +
                ", likesCounter=" + likesCounter +
                '}';
    }

    public static Builder builder() { return new Builder(); }
    @JsonPOJOBuilder
    public static class Builder {

        private String postID;
        private LocalDateTime timeSent;
        private String postTitle;
        private String postBody;
        private String posterID;
        private String posterName;
        private String topic;
        private List<String> comments;
        private Integer likesCounter;

        public Builder withPostID(String postID) {
            this.postID = postID;
            return this;
        }

        public Builder withTimeSent(LocalDateTime timeSent) {
            this.timeSent = timeSent;
            return this;
        }

        public Builder withPostTitle(String postTitle) {
            this.postTitle = postTitle;
            return this;
        }

        public Builder withPostBody(String postBody) {
            this.postBody = postBody;
            return this;
        }

        public Builder withPosterID(String posterID) {
            this.posterID = posterID;
            return this;
        }

        public Builder withPosterName(String posterName) {
            this.posterName = posterName;
            return this;
        }

        public Builder withTopic(String topic) {
            this.topic = topic;
            return this;
        }

        public Builder withComments(List<String> comments) {
            this.comments = comments;
            return this;
        }

        public Builder withLikesCounter(Integer likesCounter) {
            this.likesCounter = likesCounter;
            return this;
        }

        public NewPostRequest build() {
            return new NewPostRequest(
                    postID,
                    timeSent,
                    postTitle,
                    postBody,
                    posterID,
                    posterName,
                    topic,
                    comments,
                    likesCounter);
        }
    }
}
