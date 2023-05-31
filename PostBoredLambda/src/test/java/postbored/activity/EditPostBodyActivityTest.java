package postbored.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import postbored.activity.requests.EditPostBodyRequest;
import postbored.activity.requests.NewPostRequest;
import postbored.activity.results.EditPostBodyResult;
import postbored.activity.results.NewPostResult;
import postbored.dynamodb.PostDao;
import postbored.dynamodb.models.Post;

import javax.management.InvalidAttributeValueException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
    public void handleRequest_withAllValues_editsAndSavesPostWithRelevantInput() throws InvalidAttributeValueException {
        // GIVEN

        String expectedPosterID = "expectedPosterID";
        String expecttedPostID = "expectedPostID";

        Post post = new Post();
        post.setPostID(expecttedPostID);
        post.setPosterID(expectedPosterID);
        post.setPostBody("someBody");

        System.out.println(post.getPosterID());
        System.out.println(post.getPostID());
        System.out.println(post.getPostBody());

        postDao.savePost(post);

        System.out.println(postDao.getPost(post.getPostID()).toString());

        EditPostBodyRequest request = EditPostBodyRequest.builder()
                .withPosterID(expectedPosterID)
                .withPostBody("newPostBody")
                .withPostID(expecttedPostID)
                .build();

        // WHEN
        EditPostBodyResult result = editPostBodyActivity.handleRequest(request);
        System.out.println(postDao.getPost(post.getPostID()));
        // THEN

        assertNotNull(result.getPost().getPostID());
        assertEquals("newPostBody", result.getPost().getPostBody());
        assertEquals(expectedPosterID, result.getPost().getPosterID());
        assertEquals(expecttedPostID, result.getPost().getPostID());
    }


//    @Test
//    public void handleRequest_invalidTitle_throwsInvalidAttributeValueException() {
//        // GIVEN
//        NewPostRequest request = NewPostRequest.builder()
//                                            .withPostTitle("I'm illegal")
//                                            .withPosterID("poster")
//                                            .build();
//
//        // WHEN + THEN
//        assertThrows(InvalidAttributeValueException.class, () -> newPostActivity.handleRequest(request));
//    }
//
//    @Test
//    public void handleRequest_invalidPostBody_throwsInvalidAttributeValueException() {
//        // GIVEN
//        NewPostRequest request = NewPostRequest.builder()
//                .withPostTitle("Valid Title")
//                .withPostBody("Illegal")
//                .withPosterID("poster")
//                .build();
//
//        // WHEN + THEN
//        assertThrows(InvalidAttributeValueException.class, () -> newPostActivity.handleRequest(request));
//    }
//
//    @Test
//    public void handleRequest_invalidPosterID_throwsInvalidAttributeValueException() {
//        // GIVEN
//        NewPostRequest request = NewPostRequest.builder()
//                                            .withPostTitle("Valid Title")
//                                            .withPosterID("\"illegal\" ID")
//                                            .build();
//
//        // WHEN + THEN
//        assertThrows(InvalidAttributeValueException.class, () -> newPostActivity.handleRequest(request));
//    }
}