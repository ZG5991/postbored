package postbored.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import postbored.Exceptions.UnauthorizedEditException;
import postbored.activity.requests.DeletePostRequest;
import postbored.activity.requests.GetPostByUserRequest;
import postbored.activity.results.DeletePostResult;
import postbored.activity.results.GetPostByUserResult;
import postbored.dynamodb.PostDao;
import postbored.dynamodb.models.Post;

import javax.management.InvalidAttributeValueException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetPostByUserActivityTest {

    @Mock
    private PostDao postDao;

    @InjectMocks
    private GetPostByUserActivity getPostByUserActivity;

    @BeforeEach
    public void setUp() {
       openMocks(this);
       getPostByUserActivity = new GetPostByUserActivity(postDao);
    }

    @Test
    public void testHandleRequest_ValidPosterId_GetsPost() throws InvalidAttributeValueException {
        // GIVEN

        Post post = new Post();
        post.setPostID("1");
        post.setPostBody("postBody");
        post.setPosterID("posterID");
        post.setPosterName("posterName");
        post.setLikesCounter(0);

        GetPostByUserRequest getPostByUserRequest = new GetPostByUserRequest(post.getPostID(), post.getPosterID());
        when(postDao.getPost(getPostByUserRequest.getPosterID())).thenReturn(post);

        // WHEN
        GetPostByUserResult result = getPostByUserActivity.handleRequest(getPostByUserRequest);

        // THEN
        verify(postDao, times(1)).getPost("posterID");
        assertEquals(post.getPosterID(), result.getPost().getPosterID());
    }

//    @Test
//    public void testHandleRequest_InvalidPostId_UnauthorizedEditException() {
//
//        Post post = new Post();
//        post.setPostID(null);
//        post.setPosterID("def");
//
//        //WHEN
//        DeletePostRequest deletePostRequest = new DeletePostRequest(post.getPostID(), post.getPosterID());
//
//        //THEN
//        assertThrows(UnauthorizedEditException.class,
//                        () -> deletePostActivity.handleRequest(deletePostRequest));
//    }
//
//    @Test
//    public void testHandleRequest_InvalidPosterId_UnauthorizedEditException() {
//
//        Post post = new Post();
//        post.setPostID("abc");
//        post.setPosterID(null);
//
//        //WHEN
//        DeletePostRequest deletePostRequest = new DeletePostRequest(post.getPostID(), post.getPosterID());
//
//        //THEN
//        assertThrows(UnauthorizedEditException.class,
//                () -> deletePostActivity.handleRequest(deletePostRequest));
//    }
//
//    @Test
//    public void handleRequest_posterIdsDoNotMatch_throwsUnauthorizedEditException() {
//        // GIVEN
//        Post post = new Post();
//        post.setPostTitle("postTitle");
//        post.setPostBody("body");
//        post.setPosterID("posterID");
//        post.setPosterName("posterName");
//        post.setComments(Collections.emptyList());
//        post.setTopic("topic");
//        post.setLikesCounter(0);
//
//        DeletePostRequest deletePostRequest = new DeletePostRequest(post.getPostID(), "differentID");
//
//        // WHEN + THEN
//        //throw and catch the exception in activity class from request class
//        assertThrows(UnauthorizedEditException.class, () -> deletePostActivity.handleRequest(deletePostRequest));
//        verify(postDao, times(0)).deletePost(post);
//
//    }
}
