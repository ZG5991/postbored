package postbored.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class PostModel {

    private String postID;
    private LocalDateTime timeSent;
    private String postTitle;
    private String postBody;
    private String posterID;
    private String posterName;
    private String topic;
    private List<String> comments;
    private Integer likesCounter;

    private PostModel(String postID, LocalDateTime timeSent, String postTitle, String postBody, String posterID,
        String posterName, String topic, List<String> comments, Integer likesCounter) {
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
        return likesCounter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostModel postModel = (PostModel) o;
        return Objects.equals(postID, postModel.postID) && Objects.equals(timeSent, postModel.timeSent) && Objects.equals(postTitle, postModel.postTitle) && Objects.equals(postBody, postModel.postBody) && Objects.equals(posterID, postModel.posterID) && Objects.equals(posterName, postModel.posterName) && Objects.equals(topic, postModel.topic) && Objects.equals(comments, postModel.comments) && Objects.equals(likesCounter, postModel.likesCounter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postID, timeSent, postTitle, postBody, posterID, posterName, topic, comments, likesCounter);
    }

    public static Builder builder() { return new Builder(); }

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

        public PostModel build() {
            return new PostModel(
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
