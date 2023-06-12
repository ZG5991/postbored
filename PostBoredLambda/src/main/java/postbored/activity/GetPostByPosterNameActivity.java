package postbored.activity;

import postbored.activity.requests.GetPostByIDRequest;
import postbored.activity.requests.GetPostByPosterNameRequest;
import postbored.activity.results.GetPostByIDResult;
import postbored.activity.results.GetPostByPosterNameResult;
import postbored.dynamodb.PostDao;
import postbored.dynamodb.models.Post;
import postbored.models.PostModel;
import postbored.utilities.ModelConverter;

import javax.inject.Inject;
import javax.management.InvalidAttributeValueException;
import java.util.List;

public class GetPostByPosterNameActivity {

    private final PostDao postDao;

    @Inject
    public GetPostByPosterNameActivity(PostDao postDao) {
        this.postDao = postDao;
    }

    public GetPostByPosterNameResult handleRequest(final GetPostByPosterNameRequest getPostByPosterNameRequest) throws InvalidAttributeValueException {

        if (getPostByPosterNameRequest.getPosterName() == null) {
            throw new InvalidAttributeValueException("Username [" +
                    getPostByPosterNameRequest.getPosterName() + "] is invalid!");
        }

        List<Post> postList = postDao.getPostsByPosterName(getPostByPosterNameRequest.getPosterName());

        for (Post p : postList) {
            PostModel postModel = new ModelConverter().toPostModel(p);
            return GetPostByPosterNameResult.builder().withPost(postModel).build();
        }

        return GetPostByPosterNameResult.builder().build();
    }

}
