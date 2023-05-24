package postbored.activity.results;

import postbored.models.PostModel;

import java.util.List;

public class GetPostsByDateResult {
    private final List<PostModel> posts;

    public GetPostsByDateResult(List<PostModel> posts) {
        this.posts = posts;
    }

    public List<PostModel> getPost() {
        return posts;
    }


    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<PostModel> posts;

        public Builder withPosts(List<PostModel> posts) {
            this.posts = posts;
            return this;
        }

        public GetPostsByDateResult build() {
            return new GetPostsByDateResult(posts);
        }
    }
}
