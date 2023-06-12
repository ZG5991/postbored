package postbored.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import postbored.Exceptions.UnauthorizedEditException;
import postbored.activity.requests.EditPostBodyRequest;
import postbored.activity.results.EditPostBodyResult;
import postbored.dynamodb.PostDao;
import postbored.dynamodb.models.Post;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class EditPostBodyActivityTest {
    @Mock
    private PostDao postDao;

    private EditPostBodyActivity editPostBodyActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        editPostBodyActivity = new EditPostBodyActivity(postDao);
    }

    @Test
    public void handleRequest_withAllValues_editsAndSavesPostWithRelevantInput() throws UnauthorizedEditException {
        // GIVEN - a valid post and edited value to pass in.
        Post post = new Post();
        post.setPostID("1");
        post.setPostBody("postBody");
        post.setPosterID("posterID");
        post.setPosterName("posterName");
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
        post.setPostBody(null);
        post.setPosterID("posterID");
        post.setPosterName("posterName");
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
        post.setPostBody("body");
        post.setPosterID(null);
        post.setPosterName("posterName");
        post.setLikesCounter(0);

        EditPostBodyRequest editPostBodyRequest = new EditPostBodyRequest(post.getPostID(), post.getPostBody(), post.getPosterID());

        // WHEN + THEN
        assertThrows(UnauthorizedEditException.class, () -> editPostBodyActivity.handleRequest(editPostBodyRequest));
    }

@Test
public void handleRequest_invalidPostID_throwsInvalidAttributeValueException() {
    // GIVEN
    Post post = new Post();
    post.setPostBody("body");
    post.setPosterID("Post");
    post.setPosterName("posterName");
    post.setLikesCounter(0);

    EditPostBodyRequest editPostBodyRequest = new EditPostBodyRequest(post.getPostID(), post.getPostBody(), post.getPosterID());

    // WHEN + THEN
    assertThrows(UnauthorizedEditException.class, () -> editPostBodyActivity.handleRequest(editPostBodyRequest));
}

    @Test
    public void handleRequest_posterIdsDoNotMatch_throwsInvalidAttributeValueException() {
        // GIVEN
        Post post = new Post();
        post.setPostBody("body");
        post.setPosterID("posterID");
        post.setPosterName("posterName");
        post.setLikesCounter(0);

        EditPostBodyRequest editPostBodyRequest = new EditPostBodyRequest(post.getPostID(), "newBody", "differentID");

        // WHEN + THEN
        //throw and catch the exception in activity class from request class
        assertThrows(UnauthorizedEditException.class, () -> editPostBodyActivity.handleRequest(editPostBodyRequest));
        assertNotEquals("newBody", post.getPostBody());
        verify(postDao, times(0)).savePost(post);

    }
}