package postbored.activity;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import postbored.activity.requests.NewPostRequest;
import postbored.activity.results.NewPostResult;
import postbored.dynamodb.PostDao;
import postbored.dynamodb.models.Post;
import postbored.models.PostModel;
import postbored.utilities.ModelConverter;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NewPostActivity {

    private final PostDao postDao;
    private final ObjectIdGenerators.UUIDGenerator generator = new ObjectIdGenerators.UUIDGenerator();

    @Inject
    public NewPostActivity(PostDao postDao) {
        this.postDao = postDao;
    }

    public NewPostResult handleRequest(final NewPostRequest newPostRequest) {

        //TODO : add logic here to handle null/empty stuff!!
        List<String> postComments;
        if (newPostRequest.getComments() != null && newPostRequest.getComments().isEmpty()) {
            postComments = new ArrayList<>();
        } else postComments = newPostRequest.getComments();

        String uniqueID = UUID.randomUUID().toString();
        Post post = new Post();

        post.setPostID(uniqueID);
        post.setDateSent(newPostRequest.getTimeSent());
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
