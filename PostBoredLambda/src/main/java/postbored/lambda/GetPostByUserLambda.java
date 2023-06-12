package postbored.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import postbored.activity.requests.GetPostByUserRequest;
import postbored.activity.results.GetPostByUserResult;

import javax.management.InvalidAttributeValueException;

public class GetPostByUserLambda extends LambdaActivityRunner<GetPostByUserRequest, GetPostByUserResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetPostByUserRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetPostByUserRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    GetPostByUserRequest unauthenticatedRequest = input.fromBody(GetPostByUserRequest.class);
                    return input.fromUserClaims(claims ->
                            GetPostByUserRequest.builder()
                                    .withPosterID(unauthenticatedRequest.getPosterID())
                                    .build());
                },
        (request, serviceComponent) ->
        {
            try {
                return serviceComponent.provideGetPostByIDActivity().handleRequest(request);
            } catch (InvalidAttributeValueException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
