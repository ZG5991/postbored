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
                                    .withPosterID(claims.get("email"))
                                    .withPostBody(unauthenticatedRequest.getPostBody())
                                    .withPosterName(claims.get("name"))
                                    .build());
                },
        (request, serviceComponent) ->
        {
            try {
                return serviceComponent.provideNewPostActivity().handleRequest(request);
            } catch (InvalidAttributeValueException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
