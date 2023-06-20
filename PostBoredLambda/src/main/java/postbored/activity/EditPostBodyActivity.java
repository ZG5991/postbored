package postbored.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import postbored.activity.requests.EditPostBodyRequest;
import postbored.activity.results.EditPostBodyResult;
import postbored.dynamodb.PostDao;
import postbored.dynamodb.models.Post;
import postbored.models.PostModel;
import postbored.utilities.ModelConverter;

import javax.inject.Inject;
import java.time.LocalDateTime;

public class EditPostBodyActivity {

    private final Logger log = LogManager.getLogger();
    private final PostDao postDao;

    @Inject
    public EditPostBodyActivity(PostDao postDao) {
        this.postDao = postDao;
    }

    public EditPostBodyResult handleRequest(final EditPostBodyRequest editPostBodyRequest) {

        log.info("Received EditPostBodyRequest {}", editPostBodyRequest);

        if (editPostBodyRequest.getPostID() == null ||
                editPostBodyRequest.getPostBody() == null ||
                editPostBodyRequest.getPosterID() == null ) {
            throw new RuntimeException("One or more inputs was null, cannot complete request.");
        }

        Post post = postDao.getPost(editPostBodyRequest.getPostID());

        if (!post.getPosterID().equals(editPostBodyRequest.getPosterID())) {
            throw new RuntimeException();
        }

        post.setDateSent(LocalDateTime.now());
        post.setPostBody(editPostBodyRequest.getPostBody());

        postDao.savePost(post);

        PostModel postModel = new ModelConverter().toPostModel(post);
        return EditPostBodyResult.builder()
                    .withPost(postModel)
                    .build();

    }

}
