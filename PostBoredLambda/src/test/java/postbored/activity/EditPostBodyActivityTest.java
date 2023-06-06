package postbored.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import postbored.Exceptions.UnauthorizedEditException;
import postbored.activity.requests.DeletePostRequest;
import postbored.activity.requests.EditPostBodyRequest;
import postbored.activity.requests.NewPostRequest;
import postbored.activity.results.DeletePostResult;
import postbored.activity.results.EditPostBodyResult;
import postbored.activity.results.NewPostResult;
import postbored.dynamodb.PostDao;
import postbored.dynamodb.models.Post;
import postbored.models.PostModel;
import postbored.utilities.ModelConverter;

import javax.management.InvalidAttributeValueException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class EditPostBodyActivityTest {
    @Mock
    private PostDao postDao;

    private EditPostBodyActivity editPostBodyActivity;
    private NewPostActivity newPostActivity;
    private ModelConverter converter;
    private NewPostRequest newPostRequest;

    @BeforeEach
    void setUp() {
        openMocks(this);
        editPostBodyActivity = new EditPostBodyActivity(postDao);
        newPostActivity = new NewPostActivity(postDao);
        converter = new ModelConverter();
    }

    @Test
    public void handleRequest_withAllValues_editsAndSavesPostWithRelevantInput() throws UnauthorizedEditException {
        // GIVEN - a valid post and edited value to pass in.
        Post post = new Post();
        post.setPostID("1");
        post.setPostTitle("postTitle");
        post.setPostBody("postBody");
        post.setPosterID("posterID");
        post.setPosterName("posterName");
        post.setComments(Collections.emptyList());
        post.setTopic("topic");
        post.setLikesCounter(0);

        String newBody = "expectedNewPostBody";

        when(postDao.getPost("1")).thenReturn(post);
        assertEquals(post.getPostBody(), "postBody");

        //WHEN + THEN edit request is handed the new value, the change is reflected in the saved post object

        EditPostBodyRequest editPostBodyRequest = new EditPostBodyRequest(post.getPostID(), newBody, post.getPosterID());
        assertEquals(newBody, editPostBodyRequest.getPostBody());

        EditPostBodyResult editPostBodyResult = editPostBodyActivity.handleRequest(editPostBodyRequest);
        assertEquals(newBody, editPostBodyResult.getPost().getPostBody());

        verify(postDao, times(1)).getPost("1");
        verify(postDao, times(1)).savePost(post);
    }


    @Test
    public void handleRequest_invalidBoddy_throwsInvalidAttributeValueException() {
        // GIVEN
        Post post = new Post();
        post.setPostID("1");
        post.setPostTitle("postTitle");
        post.setPostBody(null);
        post.setPosterID("posterID");
        post.setPosterName("posterName");
        post.setComments(Collections.emptyList());
        post.setTopic("topic");
        post.setLikesCounter(0);

        EditPostBodyRequest editPostBodyRequest = new EditPostBodyRequest(post.getPostID(), post.getPostBody(), post.getPosterID());

        // WHEN + THEN
        assertThrows(UnauthorizedEditException.class, () -> editPostBodyActivity.handleRequest(editPostBodyRequest));
    }

    @Test
    public void handleRequest_invalidUserID_throwsInvalidAttributeValueException() {
        // GIVEN
        Post post = new Post();
        post.setPostID("1");
        post.setPostTitle("postTitle");
        post.setPostBody("body");
        post.setPosterID(null);
        post.setPosterName("posterName");
        post.setComments(Collections.emptyList());
        post.setTopic("topic");
        post.setLikesCounter(0);

        EditPostBodyRequest editPostBodyRequest = new EditPostBodyRequest(post.getPostID(), post.getPostBody(), post.getPosterID());

        // WHEN + THEN
        assertThrows(UnauthorizedEditException.class, () -> editPostBodyActivity.handleRequest(editPostBodyRequest));
    }

@Test
public void handleRequest_invalidPostID_throwsInvalidAttributeValueException() {
    // GIVEN
    Post post = new Post();
    post.setPostTitle("postTitle");
    post.setPostBody("body");
    post.setPosterID("Post");
    post.setPosterName("posterName");
    post.setComments(Collections.emptyList());
    post.setTopic("topic");
    post.setLikesCounter(0);

    EditPostBodyRequest editPostBodyRequest = new EditPostBodyRequest(post.getPostID(), post.getPostBody(), post.getPosterID());

    // WHEN + THEN
    assertThrows(UnauthorizedEditException.class, () -> editPostBodyActivity.handleRequest(editPostBodyRequest));
}

    @Test
    public void handleRequest_posterIdsDoNotMatch_throwsInvalidAttributeValueException() {
        // GIVEN
        Post post = new Post();
        post.setPostTitle("postTitle");
        post.setPostBody("body");
        post.setPosterID("posterID");
        post.setPosterName("posterName");
        post.setComments(Collections.emptyList());
        post.setTopic("topic");
        post.setLikesCounter(0);

        EditPostBodyRequest editPostBodyRequest = new EditPostBodyRequest(post.getPostID(), "newBody", "differentID");

        // WHEN + THEN
        //throw and catch the exception in activity class from request class
        assertThrows(UnauthorizedEditException.class, () -> editPostBodyActivity.handleRequest(editPostBodyRequest));
        assertNotEquals("newBody", post.getPostBody());
        verify(postDao, times(0)).savePost(post);

    }
}