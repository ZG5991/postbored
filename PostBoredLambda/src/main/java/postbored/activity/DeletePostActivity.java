package postbored.activity;

import postbored.activity.requests.DeletePostRequest;
import postbored.activity.results.DeletePostResult;
import postbored.dynamodb.PostDao;
import postbored.models.PostModel;
import postbored.utilities.ModelConverter;

import javax.inject.Inject;
import javax.management.InvalidAttributeValueException;

public class DeletePostActivity {

    private final PostDao postDao;

    @Inject
    public DeletePostActivity(PostDao postDao) {
        this.postDao = postDao;
    }

    public DeletePostResult handleRequest(final DeletePostRequest deletePostRequest) throws InvalidAttributeValueException {

        if (deletePostRequest.getPostID() == null) {
            throw new InvalidAttributeValueException("UserID [" +
                    deletePostRequest.getPostID() + "] is invalid!");
        }

        PostModel postModel = new ModelConverter().toPostModel(postDao.getPost(deletePostRequest.getPostID()));
        postDao.deletePost(postModel.getPostID());

        return DeletePostResult.builder().withPost(postModel).build();
    }

}
