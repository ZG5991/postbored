package postbored.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = DeletePostRequest.Builder.class)
public class DeletePostRequest {

    private String postID;

    public DeletePostRequest(String postID) {
        this.postID = postID;
    }

    public String getPostID() {
        return postID;
    }

    @Override
    public String toString() {
        return "DeletePostRequest{" +
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

        public DeletePostRequest build() {
            return new DeletePostRequest(postID);
        }
    }
}
