package postbored.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDateTime;

@JsonDeserialize(builder = GetPostsByDateRequest.Builder.class)
public class GetPostsByDateRequest {

    private String postID;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public GetPostsByDateRequest(LocalDateTime startDate, LocalDateTime endDate) {
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

        public Builder withStartDateD(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder withEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public GetPostsByDateRequest build() {
            return new GetPostsByDateRequest(startDate, endDate);
        }
    }
}
