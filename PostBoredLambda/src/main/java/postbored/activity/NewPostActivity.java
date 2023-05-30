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

public class NewPostActivity {

    private final Logger log = LogManager.getLogger();
    private final PostDao postDao;

    @Inject
    public NewPostActivity(PostDao postDao) {
        this.postDao = postDao;
    }

    public NewPostResult handleRequest(final NewPostRequest newPostRequest) {

        log.info("Received NewPostRequest {}", newPostRequest);
        System.out.printf("Received NewPostRequest %s", newPostRequest);

        if (newPostRequest.getPostTitle().isEmpty() || newPostRequest.getPostTitle() == null) {
            System.out.println("Post Title cannot be empty.");
        }

        if (newPostRequest.getPostBody().isEmpty() || newPostRequest.getPostBody() == null) {
            System.out.println("Post Body cannot be empty.");
        }


        Post post = new Post();

        post.setPosterID(newPostRequest.getPosterID());
        post.setPosterName(newPostRequest.getPosterName());
        post.setPostTitle(newPostRequest.getPostTitle());
        post.setPostBody(newPostRequest.getPostBody());
        post.setTopic(newPostRequest.getTopic());

        postDao.savePost(post);


        try {
            PostModel postModel = new ModelConverter().toPostModel(post);
            System.out.println(postModel.toString());
            return NewPostResult.builder()
                    .withPost(postModel)
                    .build();
        } catch (Exception e) {
            System.out.println("ZACH" + e.toString());
            throw e;
        }

    }

}
