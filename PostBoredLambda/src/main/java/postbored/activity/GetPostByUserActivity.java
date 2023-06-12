package postbored.activity;

import postbored.activity.requests.GetPostByUserRequest;
import postbored.activity.results.GetPostByUserResult;
import postbored.dynamodb.PostDao;
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

    public GetPostByUserResult handleRequest(final GetPostByUserRequest getPostByUserRequest) throws InvalidAttributeValueException {

        if (getPostByUserRequest.getPosterID() == null) {
            throw new InvalidAttributeValueException("UserID [" +
                    getPostByUserRequest.getPosterID() + "] is invalid!");
        }

        PostModel postModel = new ModelConverter().toPostModel(postDao.getPost(getPostByUserRequest.getPosterID()));

        return GetPostByUserResult.builder().withPost(postModel).build();
    }

}
