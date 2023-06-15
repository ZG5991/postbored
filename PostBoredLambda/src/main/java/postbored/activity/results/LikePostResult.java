package postbored.activity.results;

public class LikePostResult {

    private final String postID;

    public LikePostResult(String postID) {
        this.postID = postID;
    }

    public String getPostID() {
        return postID;
    }

    @Override
    public String toString() {
        return "LikePostResult{" +
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
        public LikePostResult build() {
            return new LikePostResult(postID);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "postID='" + postID + '\'' +
                    '}';
        }
    }
}
