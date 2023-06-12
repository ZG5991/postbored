package postbored.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetPostByIDRequest.Builder.class)
public class GetPostByIDRequest {

    private String postID;

    public GetPostByIDRequest(String posterID) {
        this.postID = posterID;
    }


    public String getPostID() {
        return postID;
    }

    @Override
    public String toString() {
        return "GetPostByIDRequest{" +
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

        public GetPostByIDRequest build() {
            return new GetPostByIDRequest(postID);
        }
    }
}
