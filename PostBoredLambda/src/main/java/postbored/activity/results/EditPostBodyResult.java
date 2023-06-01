package postbored.activity.results;

import postbored.models.PostModel;

public class EditPostBodyResult {
    private final PostModel post;

    public EditPostBodyResult(PostModel post) {
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

        public EditPostBodyResult build() {
            return new EditPostBodyResult(post);
        }
    }
}
