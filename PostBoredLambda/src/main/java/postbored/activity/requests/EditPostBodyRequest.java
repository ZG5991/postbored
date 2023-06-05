package postbored.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.management.InvalidAttributeValueException;

@JsonDeserialize(builder = EditPostBodyRequest.Builder.class) //getting bad request on class call with lambda, not sure why yet
public class EditPostBodyRequest {

    private String postID;
    private String postBody;
    private String posterID;

    public EditPostBodyRequest(){}

    public EditPostBodyRequest(String postID, String postBody, String posterID) {
        this.postID = postID;
        this.postBody = postBody;

        if (!posterID.equals(builder().posterID)) {
            this.posterID = null;
        } else this.posterID = posterID;

    }

    public String getPostID() {
        return postID;
    }

    public String getPostBody() {
        return postBody;
    }

    public String getPosterID() {
        return posterID;
    }

    @Override
    public String toString() {
        return "EditPostBodyRequest{" +
                "postID='" + postID + '\'' +
                ", postBody='" + postBody + '\'' +
                ", posterID='" + posterID + '\'' +
                '}';
    }

    public static <E extends Throwable> void sneakyThrow(Throwable e) throws E{
        throw (E) e;
    }
    public static Builder builder() { return new Builder(); }
    @JsonPOJOBuilder
    public static class Builder {
        private String postBody;
        private String posterID;

        private String postID;

        public Builder withPostBody(String postBody) {
            this.postBody = postBody;
            return this;
        }

        public Builder withPosterID(String posterID) {
            this.posterID = posterID;
            return this;
        }

        public Builder withPostID(String postID) {
            this.postID = postID;
            return this;
        }


        public EditPostBodyRequest build() {
            return new EditPostBodyRequest(
                    postID,
                    postBody,
                    posterID
                    );
        }
    }
}
