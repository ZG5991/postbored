package postbored.activity.results;

public class DeletePostResult {

    private final String postID;

    public DeletePostResult(String postID) {
        this.postID = postID;
    }

    public String getPostID() {
        return postID;
    }

    @Override
    public String toString() {
        return "DeletePostResult{" +
                "postID='" + postID + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String postID;

        public Builder withPostID(String postID) {
            this.postID = postID;
            return this;
        }
        public DeletePostResult build() {
            return new DeletePostResult(postID);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "postID='" + postID + '\'' +
                    '}';
        }
    }
}
