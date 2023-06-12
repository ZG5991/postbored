package postbored.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetPostByUserRequest.Builder.class)
public class GetPostByUserRequest {

    private String postID;
    private String posterID;

    public GetPostByUserRequest(String postID, String posterID) {
        this.postID = postID;
        this.posterID = posterID;
    }

    public String getPostID() {
        return postID;
    }

    public String getPosterID() {
        return posterID;
    }

    @Override
    public String toString() {
        return "GetPostByIDRequest{" +
                "postID='" + postID + '\'' +
                "posterID='" + posterID + '\'' +
                '}';
    }

    public static Builder builder() { return new Builder(); }
    @JsonPOJOBuilder
    public static class Builder {

        private String postID;
        private String posterID;

        public Builder withPostID(String postID) {
            this.postID = postID;
            return this;
        }

        public Builder withPosterID(String posterID) {
            this.posterID = posterID;
            return this;
        }

        public GetPostByUserRequest build() {
            return new GetPostByUserRequest(postID, posterID);
        }
    }
}
