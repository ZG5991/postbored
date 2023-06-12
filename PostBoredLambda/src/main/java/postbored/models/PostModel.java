package postbored.models;

import postbored.utilities.LocalDateTimeConverter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PostModel {

    private final String postID;
    private final String timeSent;
    private final String postBody;
    private final String posterID;
    private final String posterName;
    private final int likesCounter;

    private PostModel(String postID, String timeSent, String postBody, String posterID,
        String posterName, int likesCounter) {
        this.postID = postID;
        this.timeSent = timeSent;
        this.postBody = postBody;
        this.posterID = posterID;
        this.posterName = posterName;
        this.likesCounter = likesCounter;
    }

    public String getPostID() {
        return postID;
    }

    public String getTimeSent() {
        return timeSent;
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


    public int getLikesCounter() {
        return likesCounter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostModel postModel = (PostModel) o;
        return Objects.equals(postID, postModel.postID) && Objects.equals(timeSent, postModel.timeSent) &&
                Objects.equals(postBody, postModel.postBody) && Objects.equals(posterID, postModel.posterID) &&
                Objects.equals(posterName, postModel.posterName) && Objects.equals(likesCounter, postModel.likesCounter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postID, timeSent, postBody, posterID, posterName, likesCounter);
    }

    @Override
    public String toString() {
        return "PostModel{" +
                "postID='" + postID + '\'' +
                ", timeSent=" + timeSent +
                ", postBody='" + postBody + '\'' +
                ", posterID='" + posterID + '\'' +
                ", posterName='" + posterName + '\'' +
                ", likesCounter=" + likesCounter +
                '}';
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {

        private String postID;
        private String timeSent;
        private String postBody;
        private String posterID;
        private String posterName;
        private int likesCounter;

        public Builder withPostID(String postID) {
            this.postID = postID;
            return this;
        }

        public Builder withTimeSent(LocalDateTime timeSent) {
            LocalDateTimeConverter converter = new LocalDateTimeConverter();
            this.timeSent = converter.convert(timeSent);
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

        public Builder withLikesCounter(int likesCounter) {
            this.likesCounter = likesCounter;
            return this;
        }

        public PostModel build() {
            return new PostModel(
                postID,
                timeSent,
                postBody,
                posterID,
                posterName,
                likesCounter);
        }
    }
}
