package postbored.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDateTime;

@JsonDeserialize(builder = GetPostByDateRequest.Builder.class)
public class GetPostByDateRequest {

    private String postID;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public GetPostByDateRequest(String postID, LocalDateTime startDate, LocalDateTime endDate) {
        this.postID = postID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getPostID() {
        return postID;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "GetPostByDateRequest{" +
                "postID='" + postID + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    public static Builder builder() { return new Builder(); }
    @JsonPOJOBuilder
    public static class Builder {

        private String postID;
        private LocalDateTime startDate;
        private LocalDateTime endDate;

        public Builder withPostID(String postID) {
            this.postID = postID;
            return this;
        }

        public Builder withStartDateD(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder withEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public GetPostByDateRequest build() {
            return new GetPostByDateRequest(postID, startDate, endDate);
        }
    }
}
