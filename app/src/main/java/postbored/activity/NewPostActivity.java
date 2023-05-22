package postbored.activity;

import postbored.activity.requests.NewPostRequest;
import postbored.activity.results.NewPostResult;
import postbored.dynamodb.PostDao;
import postbored.dynamodb.models.Post;
import postbored.models.PostModel;
import postbored.utilities.ModelConverter;

import javax.inject.Inject;
import javax.management.InvalidAttributeValueException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NewPostActivity {

    private final PostDao postDao;

    @Inject
    public NewPostActivity(PostDao postDao) {
        this.postDao = postDao;
    }

    public NewPostResult handleRequest(final NewPostRequest newPostRequest) throws InvalidAttributeValueException {

        if (newPostRequest.getPosterID().isEmpty() || newPostRequest.getPosterID() == null) {
            throw new InvalidAttributeValueException("UserID [" +
                    newPostRequest.getPosterID() + "] is invalid!");
        }

        if (newPostRequest.getPostTitle().isEmpty() || newPostRequest.getPostTitle() == null) {
            throw new InvalidAttributeValueException("Post Title cannot be empty.");
        }

        if (newPostRequest.getPostBody().isEmpty() || newPostRequest.getPostBody() == null) {
            throw new InvalidAttributeValueException("Post Body cannot be empty.");
        }


        List<String> postComments;
        if (newPostRequest.getComments() != null && newPostRequest.getComments().isEmpty()) {
            postComments = new ArrayList<>();
        } else postComments = newPostRequest.getComments();

        String uniqueID = UUID.randomUUID().toString(); //generates a new unique UUID for the new post
        Post post = new Post();

        post.setPostID(uniqueID);
        post.setDateSent(LocalDateTime.now()); //since this is a new post the current time is captured
        post.setPostTitle(newPostRequest.getPostTitle());
        post.setPostBody(newPostRequest.getPostBody());
        post.setPosterID(newPostRequest.getPosterID());
        post.setPosterName(newPostRequest.getPosterName());
        post.setTopic(newPostRequest.getTopic());
        post.setComments(postComments);
        post.setLikesCounter(0); //should always default to zero for a new post

        postDao.savePost(post);

        PostModel postModel = new ModelConverter().toPostModel(post);
        return NewPostResult.builder()
                .withPost(postModel)
                .build();
    }

}
