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

    public GetPostByIDResult handleRequest(final GetPostByIDRequest getPostByUserRequest){

        if (getPostByUserRequest.getPostID() == null) {
            throw new RuntimeException("UserID [" +
                    getPostByUserRequest.getPostID() + "] is invalid!");
        }

        PostModel postModel = new ModelConverter().toPostModel(postDao.getPost(getPostByUserRequest.getPostID()));

        return GetPostByIDResult.builder().withPost(postModel).build();
    }

}
