package postbored.activity;

import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import postbored.activity.requests.GetPostByIDRequest;
import postbored.activity.requests.GetPostByPosterNameRequest;
import postbored.activity.results.GetPostByIDResult;
import postbored.activity.results.GetPostByPosterNameResult;
import postbored.dynamodb.PostDao;
import postbored.dynamodb.models.Post;
import postbored.models.PostModel;
import postbored.utilities.ModelConverter;

import javax.management.InvalidAttributeValueException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetPostByPosterNameActivityTest {

    @Mock
    private PostDao postDao;

    @InjectMocks
    private GetPostByPosterNameActivity getPostByPosterNameActivity;

    @BeforeEach
    public void setUp() {
       openMocks(this);
       getPostByPosterNameActivity = new GetPostByPosterNameActivity(postDao);
    }

    @Test
    public void testHandleRequest_ValidPosterId_GetsPost() {
        // GIVEN

        Post post1 = new Post();
        post1.setPostID("1");
        post1.setPostBody("postBody");
        post1.setPosterID("posterID");
        post1.setPosterName("posterName");
        post1.setLikesCounter(0);

        Post post2 = new Post();
        post2.setPostID("2");
        post2.setPostBody("postBody2");
        post2.setPosterID("posterID");
        post2.setPosterName("posterName");
        post2.setLikesCounter(1);

        GetPostByPosterNameRequest getPostByUserRequest = new GetPostByPosterNameRequest("posterName");
        when(postDao.getPostsByPosterName("posterName"))
                .thenReturn(List.of(post1, post2));

        // WHEN
        GetPostByPosterNameResult result = getPostByPosterNameActivity.handleRequest(getPostByUserRequest);
        List<PostModel> modelList = new ArrayList<>();
        modelList.add(new ModelConverter().toPostModel(post1));
        modelList.add(new ModelConverter().toPostModel(post2));
        // THEN
        verify(postDao, times(1)).getPostsByPosterName("posterName");
        assertEquals(result.getPosts(), modelList);
        System.out.println(result);
    }


}
