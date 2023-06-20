package postbored.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import postbored.activity.requests.LikePostRequest;
import postbored.activity.results.LikePostResult;

public class LikePostLambda extends LambdaActivityRunner<LikePostRequest, LikePostResult>
        implements RequestHandler<AuthenticatedLambdaRequest<LikePostRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<LikePostRequest> input, Context context) {
        return super.runActivity(
                () ->  input.fromPath(path -> LikePostRequest.builder().withPostID(path.get("postID")).build()),
                (request, serviceComponent) ->
         serviceComponent.provideLikePostActivity().handleRequest(request));
    }
}
