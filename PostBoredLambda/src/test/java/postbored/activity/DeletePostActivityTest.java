package postbored.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import postbored.activity.requests.DeletePostRequest;
import postbored.activity.results.DeletePostResult;
import postbored.dynamodb.PostDao;
import postbored.dynamodb.models.Post;
import postbored.models.PostModel;

import javax.management.InvalidAttributeValueException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class DeletePostActivityTest {

    @Mock
    private PostDao postDao;

    @InjectMocks
    private DeletePostActivity deletePostActivity;

    @BeforeEach
    public void setUp() {
       openMocks(this);
       deletePostActivity = new DeletePostActivity(postDao);
    }

    @Test
    public void testHandleRequest_ValidPostId_DeletesPost() throws InvalidAttributeValueException {
        // GIVEN

        Post post = new Post();
        post.setPostID("abc");
        post.setPosterID("def");

        DeletePostRequest deletePostRequest = new DeletePostRequest(post.getPostID(), post.getPosterID());
        when(postDao.getPost("abc")).thenReturn(post);

        // WHEN
        DeletePostResult result = deletePostActivity.handleRequest(deletePostRequest);

        // THEN
        verify(postDao, times(1)).deletePost(post);
        assertEquals(post.getPostID(), result.getPostID());
    }

    @Test
    public void testHandleRequest_InvalidPostId_ThrowsInvalidAttributeException() {

        Post post = new Post();
        post.setPostID(null);
        post.setPosterID("def");

        //WHEN
        DeletePostRequest deletePostRequest = new DeletePostRequest(post.getPostID(), post.getPosterID());

        //THEN
        assertThrows(InvalidAttributeValueException.class,
                        () -> deletePostActivity.handleRequest(deletePostRequest));
    }

    @Test
    public void testHandleRequest_InvalidPosterId_ThrowsInvalidAttributeException() {

        Post post = new Post();
        post.setPostID("abc");
        post.setPosterID(null);

        //WHEN
        DeletePostRequest deletePostRequest = new DeletePostRequest(post.getPostID(), post.getPosterID());

        //THEN
        assertThrows(InvalidAttributeValueException.class,
                () -> deletePostActivity.handleRequest(deletePostRequest));
    }

    @Test
    public void testHandleRequest_posterIDDoesNotMatch_ThrowsInvalidAttributeException() {

        Post post = new Post();
        post.setPostID("abc");
        post.setPosterID("def");

        //WHEN
        DeletePostRequest deletePostRequest = new DeletePostRequest(post.getPostID(), "ghi");

        //THEN
        assertThrows(InvalidAttributeValueException.class,
                () -> deletePostActivity.handleRequest(deletePostRequest));
    }
}
