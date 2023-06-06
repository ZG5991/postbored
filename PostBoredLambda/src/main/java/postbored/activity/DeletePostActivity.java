package postbored.activity;

import postbored.Exceptions.UnauthorizedEditException;
import postbored.activity.requests.DeletePostRequest;
import postbored.activity.results.DeletePostResult;
import postbored.dynamodb.PostDao;
import postbored.dynamodb.models.Post;

import javax.inject.Inject;

public class DeletePostActivity {

    private final PostDao postDao;

    @Inject
    public DeletePostActivity(PostDao postDao) {
        this.postDao = postDao;
    }

    public DeletePostResult handleRequest(final DeletePostRequest deletePostRequest) throws UnauthorizedEditException {

        if (deletePostRequest.getPosterID() == null || deletePostRequest.getPostID() == null) {
            throw new UnauthorizedEditException();
        }

        Post post = postDao.getPost(deletePostRequest.getPostID());

        if (!post.getPosterID().equals(deletePostRequest.getPosterID())) {
            throw new UnauthorizedEditException();
        }

        postDao.deletePost(post);

        return DeletePostResult.builder()
                .withPostID(deletePostRequest.getPostID())
                .build();
    }

}
