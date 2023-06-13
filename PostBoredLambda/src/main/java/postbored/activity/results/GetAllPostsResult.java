package postbored.activity.results;

import postbored.models.PostModel;

import java.util.List;

public class GetAllPostsResult {
    private final List<PostModel> posts;

    public GetAllPostsResult(List<PostModel> posts) {
        this.posts = posts;
    }

    public List<PostModel> getPosts() {
        return posts;
    }

    @Override
    public String toString() {
        return "GetAllPostsResult{" +
                "posts=" + posts +
                '}';
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

        public GetAllPostsResult build() {
            return new GetAllPostsResult(posts);
        }
    }
}
