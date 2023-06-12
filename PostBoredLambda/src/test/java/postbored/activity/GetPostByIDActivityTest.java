package postbored.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import postbored.activity.requests.GetPostByIDRequest;
import postbored.activity.results.GetPostByIDResult;
import postbored.dynamodb.PostDao;
import postbored.dynamodb.models.Post;

import javax.management.InvalidAttributeValueException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetPostByIDActivityTest {

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

        GetPostByIDRequest getPostByUserRequest = new GetPostByIDRequest(post.getPostID());
        when(postDao.getPost(getPostByUserRequest.getPostID())).thenReturn(post);

        // WHEN
        GetPostByIDResult result = getPostByUserActivity.handleRequest(getPostByUserRequest);

        // THEN
        verify(postDao, times(1)).getPost("1");
        assertEquals(post.getPostID(), result.getPost().getPostID());
    }


}
