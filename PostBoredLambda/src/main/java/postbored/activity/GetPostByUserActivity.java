package postbored.activity;

import postbored.activity.requests.GetPostByIDRequest;
import postbored.activity.results.GetPostByIDResult;
import postbored.dynamodb.PostDao;
import postbored.dynamodb.models.Post;
import postbored.models.PostModel;
import postbored.utilities.ModelConverter;

import javax.inject.Inject;
import javax.management.InvalidAttributeValueException;

public class GetPostByUserActivity {

    private final PostDao postDao;

    @Inject
    public GetPostByUserActivity(PostDao postDao) {
        this.postDao = postDao;
    }

    public GetPostByIDResult handleRequest(final GetPostByIDRequest getPostByUserRequest) throws InvalidAttributeValueException {

        if (getPostByUserRequest.getPostID() == null) {
            throw new InvalidAttributeValueException("UserID [" +
                    getPostByUserRequest.getPostID() + "] is invalid!");
        }

        PostModel postModel = new ModelConverter().toPostModel(postDao.getPost(getPostByUserRequest.getPostID()));

        return GetPostByIDResult.builder().withPost(postModel).build();
    }

}
