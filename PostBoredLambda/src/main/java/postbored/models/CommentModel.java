package postbored.models;

import org.checkerframework.checker.units.qual.C;

import java.time.LocalDateTime;
import java.util.Objects;

public class CommentModel {

    private String commentID;
    private LocalDateTime timeSent;
    private String commentContent;
    private String commenterID;
    private String postID;

    public CommentModel(String commentID, LocalDateTime timeSent, String commentContent, String commenterID, String postID) {
        this.commentID = commentID;
        this.timeSent = timeSent;
        this.commentContent = commentContent;
        this.commenterID = commenterID;
        this.postID = postID;
    }

    public String getCommentID() {
        return commentID;
    }

    public LocalDateTime getTimeSent() {
        return timeSent;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public String getCommenterID() {
        return commenterID;
    }

    public String getPostID() {
        return postID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentModel that = (CommentModel) o;
        return Objects.equals(commentID, that.commentID) && Objects.equals(timeSent, that.timeSent) && Objects.equals(commentContent, that.commentContent) && Objects.equals(commenterID, that.commenterID) && Objects.equals(postID, that.postID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentID, timeSent, commentContent, commenterID, postID);
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {

        private String commentID;
        private LocalDateTime timeSent;
        private String commentContent;
        private String commenterID;
        private String postID;

        public Builder withCommentID(String commentID) {
            this.commentID = commentID;
            return this;
        }

        public Builder withTimeSent(LocalDateTime timeSent) {
            this.timeSent = timeSent;
            return this;
        }

        public Builder withCommentContent(String commentContent) {
            this.commentContent = commentContent;
            return this;
        }

        public Builder withCommenterID(String commenterID) {
            this.commenterID = commenterID;
            return this;
        }

        public Builder withPostID(String postID) {
            this.postID = postID;
            return this;
        }

        public CommentModel build() {
            return new CommentModel(
                    commentID,
                    timeSent,
                    commentContent,
                    commenterID,
                    postID);
        }
    }
}
