package postbored.activity;

import postbored.activity.requests.NewPostRequest;
import postbored.activity.results.NewPostResult;
import postbored.dynamodb.PostDao;
import postbored.dynamodb.models.Post;
import postbored.models.PostModel;
import postbored.utilities.ModelConverter;

import javax.inject.Inject;

public class NewPostActivity {

    private final PostDao postDao;

    @Inject
    public NewPostActivity(PostDao postDao) {
        this.postDao = postDao;
    }

    public NewPostResult handleRequest(final NewPostRequest newPostRequest) {

        //TODO : add logic here to handle null/empty stuff!!

        Post post = new Post();
        post.setPostID("PLACEHOLDER"); //generate this as a new UUID
        post.setDateSent(newPostRequest.getTimeSent());
        post.setPostTitle(newPostRequest.getPostTitle());
        post.setPostBody(newPostRequest.getPostBody());
        post.setPosterID(newPostRequest.getPosterID());
        post.setPosterName(newPostRequest.getPosterName());
        post.setTopic(newPostRequest.getTopic());
        post.setComments(newPostRequest.getComments()); //initialize an empty list if null
        post.setLikesCounter(newPostRequest.getLikesCounter()); //should default to zero

        postDao.savePost(post);

        PostModel postModel = new ModelConverter().toPostModel(post);
        return NewPostResult.builder()
                .withPost(postModel)
                .build();
    }

}
