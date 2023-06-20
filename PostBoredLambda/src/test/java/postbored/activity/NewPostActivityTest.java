package postbored.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import postbored.activity.requests.NewPostRequest;
import postbored.activity.results.NewPostResult;
import postbored.dynamodb.PostDao;
import postbored.dynamodb.models.Post;

import javax.management.InvalidAttributeValueException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class NewPostActivityTest {
    @Mock
    private PostDao postDao;

    private NewPostActivity newPostActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        newPostActivity = new NewPostActivity(postDao);
    }

    @Test
    public void handleRequest_withAllValues_createsAndSavesPostWithRelevantInput() throws InvalidAttributeValueException {
        // GIVEN
        String expectedPostBody = "expectedPostBody";
        String expectedPosterID = "expectedPosterID";
        String expectedPosterName = "expectedPosterName";

        NewPostRequest request = NewPostRequest.builder()
                .withPosterID(expectedPosterID)
                .withPosterName(expectedPosterName)
                .withPostBody(expectedPostBody)
                .build();

        // WHEN
        NewPostResult result = newPostActivity.handleRequest(request);

        // THEN
        verify(postDao).savePost(any(Post.class));

        assertNotNull(result.getPost().getPostID());
        assertEquals(expectedPostBody, result.getPost().getPostBody());
        assertEquals(expectedPosterID, result.getPost().getPosterID());
        assertEquals(expectedPosterName, result.getPost().getPosterName());
    }


    @Test
    public void handleRequest_invalidTitle_throwsInvalidAttributeValueException() {
        // GIVEN
        NewPostRequest request = NewPostRequest.builder()
                                            .withPosterID("poster")
                                            .build();

        // WHEN + THEN
        assertThrows(InvalidAttributeValueException.class, () -> newPostActivity.handleRequest(request));
    }

    @Test
    public void handleRequest_invalidPostBody_throwsInvalidAttributeValueException() {
        // GIVEN
        NewPostRequest request = NewPostRequest.builder()
                .withPostBody("Illegal")
                .withPosterID("poster")
                .build();

        // WHEN + THEN
        assertThrows(InvalidAttributeValueException.class, () -> newPostActivity.handleRequest(request));
    }

    @Test
    public void handleRequest_invalidPosterID_throwsInvalidAttributeValueException() {
        // GIVEN
        NewPostRequest request = NewPostRequest.builder()
                                            .withPosterID("\"illegal\" ID")
                                            .build();

        // WHEN + THEN
        assertThrows(InvalidAttributeValueException.class, () -> newPostActivity.handleRequest(request));
    }
}