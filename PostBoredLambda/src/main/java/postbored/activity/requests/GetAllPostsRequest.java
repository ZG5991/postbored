package postbored.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetAllPostsRequest.Builder.class)
public class GetAllPostsRequest {

    public GetAllPostsRequest() {}

    @Override
    public String toString() {
        return "GetAllPostsRequest";
    }

    public static Builder builder() { return new Builder(); }
    @JsonPOJOBuilder
    public static class Builder {

        public GetAllPostsRequest build() {
            return new GetAllPostsRequest();
        }
    }
}
