package postbored.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = DeletePostRequest.Builder.class)
public class DeletePostRequest {

    private String postID;
    private String posterID;

    public DeletePostRequest(String postID, String posterID) {
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
        return "DeletePostRequest{" +
                "postID='" + postID + '\'' +
                ", posterID='" + posterID + '\'' +
                '}';
    }

    public static <E extends Throwable> void sneakyThrow(Throwable e) throws E{
        throw (E) e;
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

        public DeletePostRequest build() {
            return new DeletePostRequest(postID, posterID);
        }
    }
}
