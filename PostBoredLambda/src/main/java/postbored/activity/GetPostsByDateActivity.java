package postbored.activity;

import postbored.activity.requests.GetPostsByDateRequest;
import postbored.activity.results.GetPostsByDateResult;
import postbored.dynamodb.PostDao;
import postbored.dynamodb.models.Post;
import postbored.models.PostModel;
import postbored.utilities.ModelConverter;

import javax.inject.Inject;
import javax.management.InvalidAttributeValueException;
import java.util.ArrayList;
import java.util.List;

public class GetPostsByDateActivity {

    private final PostDao postDao;

    @Inject
    public GetPostsByDateActivity(PostDao postDao) {
        this.postDao = postDao;
    }

    public GetPostsByDateResult handleRequest(final GetPostsByDateRequest getPostByDateRequest) throws InvalidAttributeValueException {

        if (getPostByDateRequest.getPostID() == null) {
            throw new InvalidAttributeValueException("UserID [" +
                    getPostByDateRequest.getPostID() + "] is invalid!");
        }

        List<Post> postList = postDao.getPostsBetweenDates(getPostByDateRequest.getStartDate(), getPostByDateRequest.getEndDate());
        List<PostModel> postModelList = new ArrayList<>();
        for(Post p : postList) {
            postModelList.add(new ModelConverter().toPostModel(p));
        }

        return GetPostsByDateResult.builder()
                .withPosts(postModelList).build();
    }

}
