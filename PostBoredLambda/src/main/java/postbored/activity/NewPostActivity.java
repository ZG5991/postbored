package postbored.activity;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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

    public NewPostResult handleRequest(final NewPostRequest newPostRequest) {

        if (newPostRequest.getPosterID().isEmpty() || newPostRequest.getPosterID() == null) {
            System.out.println("UserID [" +
                    newPostRequest.getPosterID() + "] is invalid!");
        }

        if (newPostRequest.getPostTitle().isEmpty() || newPostRequest.getPostTitle() == null) {
            System.out.println("Post Title cannot be empty.");
        }

        if (newPostRequest.getPostBody().isEmpty() || newPostRequest.getPostBody() == null) {
            System.out.println("Post Body cannot be empty.");
        }


        List<String> postComments = null;
        if (newPostRequest.getComments() != null) {
            postComments = new ArrayList<>(newPostRequest.getComments());
        }

        Post post = new Post();

        post.setPostID(UUID.randomUUID().toString());
        post.setDateSent(LocalDateTime.now());
        post.setPostTitle(newPostRequest.getPostTitle());
        post.setPostBody(newPostRequest.getPostBody());
        post.setPosterID(newPostRequest.getPosterID());
        post.setPosterName(newPostRequest.getPosterName());
        post.setTopic(newPostRequest.getTopic());
        post.setComments(postComments);
        post.setLikesCounter(0);

        postDao.savePost(post);

        PostModel postModel = new ModelConverter().toPostModel(post);
        return NewPostResult.builder()
                .withPost(postModel)
                .build();
    }

}
