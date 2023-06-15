package postbored.activity.results;

import postbored.models.PostModel;

import java.util.List;

public class GetPostByPosterNameResult {
    private final List<PostModel> postList;

    public GetPostByPosterNameResult(List<PostModel> postList) {
        this.postList = postList;
    }

    public List<PostModel> getPosts() {
        return postList;
    }

    @Override
    public String toString() {
        return "GetPostByPosterNameResult{" +
                "post=" + postList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<PostModel> postList;

        public Builder withPosts(List<PostModel> postList) {
            this.postList = postList;
            return this;
        }

        public GetPostByPosterNameResult build() {
            return new GetPostByPosterNameResult(postList);
        }
    }
}
