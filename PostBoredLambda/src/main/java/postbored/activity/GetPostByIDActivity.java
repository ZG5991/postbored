package postbored.activity;

import postbored.activity.requests.GetPostByIDRequest;
import postbored.activity.results.GetPostByIDResult;
import postbored.dynamodb.PostDao;
import postbored.models.PostModel;
import postbored.utilities.ModelConverter;

import javax.inject.Inject;
import javax.management.InvalidAttributeValueException;

public class GetPostByIDActivity {

    private final PostDao postDao;

    @Inject
    public GetPostByIDActivity(PostDao postDao) {
        this.postDao = postDao;
    }

    public GetPostByIDResult handleRequest(final GetPostByIDRequest getPostByIDRequest) throws InvalidAttributeValueException {

        if (getPostByIDRequest.getPostID() == null) {
            throw new InvalidAttributeValueException("UserID [" +
                    getPostByIDRequest.getPostID() + "] is invalid!");
        }

        PostModel postModel = new ModelConverter().toPostModel(postDao.getPost(getPostByIDRequest.getPostID()));
        postDao.getPost(postModel.getPostID());

        return GetPostByIDResult.builder().withPost(postModel).build();
    }

}
