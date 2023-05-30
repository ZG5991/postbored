package postbored.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = NewPostRequest.Builder.class) //getting bad request on class call with lambda, not sure why yet
public class NewPostRequest {

    private String postTitle;
    private String postBody;
    private String posterID;
    private String posterName;
    private String topic;

    public NewPostRequest(){}

    public NewPostRequest(String postTitle, String postBody,
                          String posterID, String posterName, String topic) {
        this.postTitle = postTitle;
        this.postBody = postBody;
        this.posterID = posterID;
        this.posterName = posterName;
        this.topic = topic;
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

    @Override
    public String toString() {
        return "NewPostRequest{" +
                "postTitle='" + postTitle + '\'' +
                ", postBody='" + postBody + '\'' +
                ", posterID='" + posterID + '\'' +
                ", posterName='" + posterName + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }

    public static Builder builder() { return new Builder(); }
    @JsonPOJOBuilder
    public static class Builder {
        private String postTitle;
        private String postBody;
        private String posterID;
        private String posterName;
        private String topic;

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


        public NewPostRequest build() {
            return new NewPostRequest(
                    postTitle,
                    postBody,
                    posterID,
                    posterName,
                    topic
                    );
        }
    }
}
