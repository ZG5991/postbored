package postbored.activity.results;

import postbored.models.PostModel;

public class NewPostResult {
    private final PostModel post;

    public NewPostResult(PostModel post) {
        this.post = post;
    }

    public PostModel getPost() {
        return post;
    }

    @Override
    public String toString() {
        return "NewPostResult{" +
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

        public NewPostResult build() {
            return new NewPostResult(post);
        }
    }
}
