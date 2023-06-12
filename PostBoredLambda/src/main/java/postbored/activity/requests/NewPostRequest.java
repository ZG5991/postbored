package postbored.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = NewPostRequest.Builder.class) //getting bad request on class call with lambda, not sure why yet
public class NewPostRequest {

    private String postBody;
    private String posterID;
    private String posterName;

    public NewPostRequest(){}

    public NewPostRequest(String postBody,
                          String posterID, String posterName) {
        this.postBody = postBody;
        this.posterID = posterID;
        this.posterName = posterName;
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


    @Override
    public String toString() {
        return "NewPostRequest{" +
                ", postBody='" + postBody + '\'' +
                ", posterID='" + posterID + '\'' +
                ", posterName='" + posterName + '\'' +
                '}';
    }

    public static Builder builder() { return new Builder(); }
    @JsonPOJOBuilder
    public static class Builder {
        private String postBody;
        private String posterID;
        private String posterName;

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


        public NewPostRequest build() {
            return new NewPostRequest(
                    postBody,
                    posterID,
                    posterName
                    );
        }
    }
}
