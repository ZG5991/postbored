package postbored.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetPostByPosterNameRequest.Builder.class)
public class GetPostByPosterNameRequest {

    private String posterName;

    public GetPostByPosterNameRequest(String posterID) {
        this.posterName = posterID;
    }


    public String getPosterName() {
        return posterName;
    }

    @Override
    public String toString() {
        return "GetPostByIDRequest{" +
                "postID='" + posterName + '\'' +
                '}';
    }

    public static Builder builder() { return new Builder(); }
    @JsonPOJOBuilder
    public static class Builder {
        private String posterName;

        public Builder withPosterName(String posterName) {
            this.posterName = posterName;
            return this;
        }

        public GetPostByPosterNameRequest build() {
            return new GetPostByPosterNameRequest(posterName);
        }
    }
}
