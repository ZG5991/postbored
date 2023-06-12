package postbored.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import postbored.activity.requests.GetPostByIDRequest;
import postbored.activity.results.GetPostByIDResult;

import javax.management.InvalidAttributeValueException;

public class GetPostByIDLambda extends LambdaActivityRunner<GetPostByIDRequest, GetPostByIDResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetPostByIDRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetPostByIDRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    GetPostByIDRequest unauthenticatedRequest = input.fromBody(GetPostByIDRequest.class);
                    return input.fromUserClaims(claims ->
                            GetPostByIDRequest.builder()
                                    .withPostID(unauthenticatedRequest.getPostID())
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
