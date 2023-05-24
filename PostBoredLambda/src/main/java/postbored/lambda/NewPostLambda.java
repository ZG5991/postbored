package postbored.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import postbored.activity.requests.NewPostRequest;
import postbored.activity.results.NewPostResult;

import javax.management.InvalidAttributeValueException;

public class NewPostLambda extends LambdaActivityRunner<NewPostRequest, NewPostResult>
        implements RequestHandler<AuthenticatedLambdaRequest<NewPostRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<NewPostRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    NewPostRequest unauthenticatedRequest = input.fromBody(NewPostRequest.class);
                    return input.fromUserClaims(claims ->
                            NewPostRequest.builder()
                                    .withPostID(unauthenticatedRequest.getPostID())
                                    .withTimeSent(unauthenticatedRequest.getTimeSent())
                                    .withPosterID(claims.get("email"))
                                    .withPostTitle(unauthenticatedRequest.getPostTitle())
                                    .withPostBody(unauthenticatedRequest.getPostBody())
                                    .withPosterName(claims.get("name"))
                                    .withComments(unauthenticatedRequest.getComments())
                                    .withTopic(unauthenticatedRequest.getTopic())
                                    .withLikesCounter(unauthenticatedRequest.getLikesCounter())
                                    .build());
                },
        (request, serviceComponent) ->
                serviceComponent.provideNewPostActivity().handleRequest(request));
    }
}
