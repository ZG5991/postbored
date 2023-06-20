package postbored.activity;

import postbored.activity.requests.GetPostByPosterNameRequest;
import postbored.activity.results.GetPostByPosterNameResult;
import postbored.dynamodb.PostDao;
import postbored.utilities.ModelConverter;

import javax.inject.Inject;
public class GetPostByPosterNameActivity {

    private final PostDao postDao;

    @Inject
    public GetPostByPosterNameActivity(PostDao postDao) {
        this.postDao = postDao;
    }

    public GetPostByPosterNameResult handleRequest(final GetPostByPosterNameRequest getPostByPosterNameRequest) {

        if (getPostByPosterNameRequest.getPosterName() == null) {
            throw new RuntimeException("Username [" +
                    getPostByPosterNameRequest.getPosterName() + "] is invalid!");
        }


        return GetPostByPosterNameResult.builder()
                .withPosts(new ModelConverter()
                .toPostModelList(postDao.getPostsByPosterName(getPostByPosterNameRequest.getPosterName())))
                .build();
    }

}
