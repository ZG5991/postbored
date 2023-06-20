package postbored.activity;

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

    public DeletePostResult handleRequest(final DeletePostRequest deletePostRequest) {

        if (deletePostRequest.getPostID() == null) {
            throw new RuntimeException();
        }

        Post post = postDao.getPost(deletePostRequest.getPostID());

        postDao.deletePost(post);

        return DeletePostResult.builder()
                .withPostID(deletePostRequest.getPostID())
                .build();
    }

}
