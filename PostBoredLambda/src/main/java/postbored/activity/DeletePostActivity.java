package postbored.activity;

import postbored.activity.requests.DeletePostRequest;
import postbored.activity.results.DeletePostResult;
import postbored.dynamodb.PostDao;
import postbored.dynamodb.models.Post;

import javax.inject.Inject;
import javax.management.InvalidAttributeValueException;

public class DeletePostActivity {

    private final PostDao postDao;

    @Inject
    public DeletePostActivity(PostDao postDao) {
        this.postDao = postDao;
    }

    public DeletePostResult handleRequest(final DeletePostRequest deletePostRequest) throws InvalidAttributeValueException {
        String postID = deletePostRequest.getPostID();
        Post post = postDao.getPost(postID);

        if (post == null) {
            throw new InvalidAttributeValueException("Post with ID " + deletePostRequest.getPostID() + " not found.");
        }

        if (!post.getPosterID().equals(deletePostRequest.getPosterID())) {
            throw new IllegalArgumentException("User ID ["
                    +post.getPosterID()+
                    "] does not match the ID ["
                    +deletePostRequest.getPosterID()+
                    "] of original poster!");
        }

        try {
            postDao.deletePost(post);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return DeletePostResult.builder()
                .withPostID(deletePostRequest.getPostID())
                .build();

    }

}
