package postbored.activity.results;

import postbored.models.PostModel;

public class GetPostByUserResult {
    private final PostModel post;

    public GetPostByUserResult(PostModel post) {
        this.post = post;
    }

    public PostModel getPost() {
        return post;
    }

    @Override
    public String toString() {
        return "GetPostByUserResult{" +
                "post=" + post +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private PostModel post;

        public Builder withPost(PostModel post) {
            this.post = post;
            return this;
        }

        public GetPostByUserResult build() {
            return new GetPostByUserResult(post);
        }
    }
}
