package postbored.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = LikePostRequest.Builder.class)
public class LikePostRequest {

    private String postID;

    public LikePostRequest(String postID) {
        this.postID = postID;
    }

    public String getPostID() {
        return postID;
    }

    @Override
    public String toString() {
        return "LikePostRequest{" +
                "postID='" + postID + '\'' +
                '}';
    }


    public static Builder builder() { return new Builder(); }
    @JsonPOJOBuilder
    public static class Builder {

        private String postID;

        public Builder withPostID(String postID) {
            this.postID = postID;
            return this;
        }

        public LikePostRequest build() {
            return new LikePostRequest(postID);
        }
    }
}
