package postbored.utilities;

import postbored.dynamodb.models.Comment;
import postbored.dynamodb.models.Post;
import postbored.models.CommentModel;
import postbored.models.PostModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModelConverter {

    public PostModel toPostModel(Post post) {

        //creates a list for a new postModel if comments already exist in the request.
        List<String> comments = null;
        if (post.getComments() != null) {
            comments = new ArrayList<>(post.getComments());
        }

        return PostModel.builder()
                .withPostID(post.getPostID())
                .withTimeSent(post.getDateSent())
                .withPostTitle(post.getPostTitle())
                .withPostBody(post.getPostBody())
                .withPosterID(post.getPosterID())
                .withPosterName(post.getPosterName())
                .withTopic(post.getTopic())
                .withComments(comments)
                .withLikesCounter(post.getLikesCounter())
                .build();
    }

    public CommentModel toCommentModel(Comment comment) {
        return CommentModel.builder()
                .withCommentID(comment.getCommentID())
                .withTimeSent(comment.getTimeSent())
                .withCommentContent(comment.getCommentContent())
                .withCommenterID(comment.getCommenterID())
                .withPostID(comment.getPostID())
                .build();
    }
}
