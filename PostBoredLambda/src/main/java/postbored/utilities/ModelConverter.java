package postbored.utilities;

import postbored.dynamodb.models.Post;
import postbored.models.PostModel;


public class ModelConverter {

    public PostModel toPostModel(Post post) {

        return PostModel.builder()
                .withPostID(post.getPostID())
                .withTimeSent(post.getDateSent())
                .withPostBody(post.getPostBody())
                .withPosterID(post.getPosterID())
                .withPosterName(post.getPosterName())
                .withLikesCounter(post.getLikesCounter())
                .build();
    }

    public Post toPost(PostModel postModel) {

        LocalDateTimeConverter converter = new LocalDateTimeConverter();
        Post post = new Post();

        post.setPostID(postModel.getPostID());
        post.setDateSent(converter.unconvert(postModel.getTimeSent()));
        post.setPostBody(postModel.getPostBody());
        post.setPosterID(postModel.getPosterID());
        post.setPosterName(postModel.getPosterName());
        post.setLikesCounter(postModel.getLikesCounter());

        return post;
    }

}
