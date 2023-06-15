package postbored.activity;

import postbored.activity.requests.GetAllPostsRequest;
import postbored.activity.requests.GetPostByIDRequest;
import postbored.activity.results.GetAllPostsResult;
import postbored.activity.results.GetPostByIDResult;
import postbored.dynamodb.PostDao;
import postbored.dynamodb.models.Post;
import postbored.models.PostModel;
import postbored.utilities.ModelConverter;

import javax.inject.Inject;
import javax.management.InvalidAttributeValueException;
import java.util.ArrayList;
import java.util.List;

public class GetAllPostsActivity {

    private final PostDao postDao;

    @Inject
    public GetAllPostsActivity(PostDao postDao) {
        this.postDao = postDao;
    }

    public GetAllPostsResult handleRequest() {

        return GetAllPostsResult.builder().withPosts(new ModelConverter().toPostModelList(postDao.getAllPosts())).build();
    }

}
