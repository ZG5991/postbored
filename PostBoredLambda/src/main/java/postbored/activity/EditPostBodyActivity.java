package postbored.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import postbored.Exceptions.UnauthorizedEditException;
import postbored.activity.requests.EditPostBodyRequest;
import postbored.activity.requests.NewPostRequest;
import postbored.activity.results.EditPostBodyResult;
import postbored.activity.results.NewPostResult;
import postbored.dynamodb.PostDao;
import postbored.dynamodb.models.Post;
import postbored.models.PostModel;
import postbored.utilities.ModelConverter;

import javax.inject.Inject;
import javax.management.InvalidAttributeValueException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

public class EditPostBodyActivity {

    private final Logger log = LogManager.getLogger();
    private final PostDao postDao;

    @Inject
    public EditPostBodyActivity(PostDao postDao) {
        this.postDao = postDao;
    }

    public EditPostBodyResult handleRequest(final EditPostBodyRequest editPostBodyRequest) throws UnauthorizedEditException{

        log.info("Received EditPostBodyRequest {}", editPostBodyRequest);

        if (editPostBodyRequest.getPostID() == null ||
                editPostBodyRequest.getPostBody() == null ||
                editPostBodyRequest.getPosterID() == null ) {
            throw new UnauthorizedEditException("One or more inputs was null, cannot complete request.");
        }

        Post post = postDao.getPost(editPostBodyRequest.getPostID());

        if (!post.getPosterID().equals(editPostBodyRequest.getPosterID())) {
            throw new UnauthorizedEditException();
        }

        post.setPostBody(editPostBodyRequest.getPostBody());

        postDao.savePost(post);

        PostModel postModel = new ModelConverter().toPostModel(post);
        return EditPostBodyResult.builder()
                    .withPost(postModel)
                    .build();

    }

}
