package postbored.activity;

import postbored.activity.requests.DeletePostRequest;
import postbored.activity.requests.LikePostRequest;
import postbored.activity.results.DeletePostResult;
import postbored.activity.results.LikePostResult;
import postbored.dynamodb.PostDao;
import postbored.dynamodb.models.Post;

import javax.inject.Inject;

public class LikePostActivity {

    private final PostDao postDao;

    @Inject
    public LikePostActivity(PostDao postDao) {
        this.postDao = postDao;
    }

    public LikePostResult handleRequest(final LikePostRequest likePostRequest){

        if (likePostRequest.getPostID() == null) {
            throw new RuntimeException();
        }

        postDao.likePost(likePostRequest.getPostID());

        return LikePostResult.builder()
                .withPostID(likePostRequest.getPostID())
                .build();
    }

}
