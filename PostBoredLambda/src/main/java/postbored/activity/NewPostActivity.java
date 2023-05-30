package postbored.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import postbored.activity.requests.NewPostRequest;
import postbored.activity.results.NewPostResult;
import postbored.dynamodb.PostDao;
import postbored.dynamodb.models.Post;
import postbored.models.PostModel;
import postbored.utilities.ModelConverter;

import javax.inject.Inject;
import javax.management.InvalidAttributeValueException;

public class NewPostActivity {

    private final Logger log = LogManager.getLogger();
    private final PostDao postDao;

    @Inject
    public NewPostActivity(PostDao postDao) {
        this.postDao = postDao;
    }

    public NewPostResult handleRequest(final NewPostRequest newPostRequest) throws InvalidAttributeValueException {

        log.info("Received NewPostRequest {}", newPostRequest);

        if (newPostRequest.getPosterID().isEmpty() || newPostRequest.getPosterName() == null) {
            throw new InvalidAttributeValueException("PosterID or Name could not be found.");
        }

        if (newPostRequest.getPostTitle().isEmpty() || newPostRequest.getPostTitle() == null) {
            throw new InvalidAttributeValueException("Post Title cannot be empty.");
        }

        if (newPostRequest.getPostBody().isEmpty() || newPostRequest.getPostBody() == null) {
            throw new InvalidAttributeValueException("Post Body cannot be empty.");
        }

        Post post = new Post();

        post.setPosterID(newPostRequest.getPosterID());
        post.setPosterName(newPostRequest.getPosterName());
        post.setPostTitle(newPostRequest.getPostTitle());
        post.setPostBody(newPostRequest.getPostBody());
        post.setTopic(newPostRequest.getTopic());

        postDao.saveNewPost(post);


        PostModel postModel = new ModelConverter().toPostModel(post);
        return NewPostResult.builder()
                    .withPost(postModel)
                    .build();

    }

}
