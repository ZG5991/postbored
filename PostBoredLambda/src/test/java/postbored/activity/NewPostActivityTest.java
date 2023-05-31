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
        String expectedPostTitle = "expectedPostTitle";
        String expectedPostBody = "expectedPostBody";
        String expectedPosterID = "expectedPosterID";
        String expectedPosterName = "expectedPosterName";
        String topic = "topic";

        NewPostRequest request = NewPostRequest.builder()
                .withPosterID(expectedPosterID)
                .withPosterName(expectedPosterName)
                .withPostTitle(expectedPostTitle)
                .withPostBody(expectedPostBody)
                .withTopic(topic)
                .build();

        // WHEN
        NewPostResult result = newPostActivity.handleRequest(request);

        // THEN
        verify(postDao).savePost(any(Post.class));

        assertNotNull(result.getPost().getPostID());
        assertEquals(expectedPostTitle, result.getPost().getPostTitle());
        assertEquals(expectedPostBody, result.getPost().getPostBody());
        assertEquals(expectedPosterID, result.getPost().getPosterID());
        assertEquals(expectedPosterName, result.getPost().getPosterName());
        assertEquals(topic, result.getPost().getTopic());
    }


    @Test
    public void handleRequest_invalidTitle_throwsInvalidAttributeValueException() {
        // GIVEN
        NewPostRequest request = NewPostRequest.builder()
                                            .withPostTitle("I'm illegal")
                                            .withPosterID("poster")
                                            .build();

        // WHEN + THEN
        assertThrows(InvalidAttributeValueException.class, () -> newPostActivity.handleRequest(request));
    }

    @Test
    public void handleRequest_invalidPostBody_throwsInvalidAttributeValueException() {
        // GIVEN
        NewPostRequest request = NewPostRequest.builder()
                .withPostTitle("Valid Title")
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
                                            .withPostTitle("Valid Title")
                                            .withPosterID("\"illegal\" ID")
                                            .build();

        // WHEN + THEN
        assertThrows(InvalidAttributeValueException.class, () -> newPostActivity.handleRequest(request));
    }
}