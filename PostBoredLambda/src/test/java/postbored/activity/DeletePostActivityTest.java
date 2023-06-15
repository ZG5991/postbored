package postbored.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import postbored.Exceptions.UnauthorizedEditException;
import postbored.activity.requests.DeletePostRequest;
import postbored.activity.results.DeletePostResult;
import postbored.dynamodb.PostDao;
import postbored.dynamodb.models.Post;

import static org.junit.jupiter.api.Assertions.*;
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
    public void testHandleRequest_ValidPostId_DeletesPost() {
        // GIVEN

        Post post = new Post();
        post.setPostID("abc");

        DeletePostRequest deletePostRequest = new DeletePostRequest(post.getPostID());
        when(postDao.getPost("abc")).thenReturn(post);

        // WHEN
        DeletePostResult result = deletePostActivity.handleRequest(deletePostRequest);

        // THEN
        verify(postDao, times(1)).deletePost(post);
        assertEquals(post.getPostID(), result.getPostID());
    }

    @Test
    public void testHandleRequest_InvalidPostId_RuntimeException() {

        Post post = new Post();
        post.setPostID(null);

        //WHEN
        DeletePostRequest deletePostRequest = new DeletePostRequest(post.getPostID());

        //THEN
        assertThrows(RuntimeException.class,
                        () -> deletePostActivity.handleRequest(deletePostRequest));
    }

    @Test
    public void handleRequest_posterIdsDoNotMatch_throwsRuntimeException() {
        // GIVEN
        Post post = new Post();
        post.setPostBody("body");
        post.setPosterID("posterID");
        post.setPosterName("posterName");
        post.setLikesCounter(0);

        DeletePostRequest deletePostRequest = new DeletePostRequest(post.getPostID());

        // WHEN + THEN
        //throw and catch the exception in activity class from request class
        assertThrows(RuntimeException.class, () -> deletePostActivity.handleRequest(deletePostRequest));
        verify(postDao, times(0)).deletePost(post);

    }
}
