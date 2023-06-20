package postbored.activity;

import postbored.activity.results.GetAllPostsResult;
import postbored.dynamodb.PostDao;
import postbored.utilities.ModelConverter;

import javax.inject.Inject;
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
