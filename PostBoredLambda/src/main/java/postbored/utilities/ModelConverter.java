package postbored.utilities;

import postbored.dynamodb.models.Comment;
import postbored.dynamodb.models.Post;
import postbored.models.CommentModel;
import postbored.models.PostModel;


public class ModelConverter {

    public PostModel toPostModel(Post post) {

        return PostModel.builder()
                .withPostID(post.getPostID())
                .withTimeSent(post.getDateSent())
                .withPostTitle(post.getPostTitle())
                .withPostBody(post.getPostBody())
                .withPosterID(post.getPosterID())
                .withPosterName(post.getPosterName())
                .withTopic(post.getTopic())
                .withComments(post.getComments())
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
