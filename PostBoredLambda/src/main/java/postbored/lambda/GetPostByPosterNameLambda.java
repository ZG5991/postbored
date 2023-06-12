package postbored.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import postbored.activity.requests.GetPostByIDRequest;
import postbored.activity.requests.GetPostByPosterNameRequest;
import postbored.activity.results.GetPostByIDResult;
import postbored.activity.results.GetPostByPosterNameResult;

import javax.management.InvalidAttributeValueException;

public class GetPostByPosterNameLambda extends LambdaActivityRunner<GetPostByPosterNameRequest, GetPostByPosterNameResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetPostByPosterNameRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetPostByPosterNameRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    GetPostByPosterNameRequest unauthenticatedRequest = input.fromBody(GetPostByPosterNameRequest.class);
                    return input.fromUserClaims(claims ->
                            GetPostByPosterNameRequest.builder()
                                    .withPosterName((unauthenticatedRequest.getPosterName()))
                                    .build());
                },
        (request, serviceComponent) ->
        {
            try {
                return serviceComponent.provideGetPostByPosterNameActivity().handleRequest(request);
            } catch (InvalidAttributeValueException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
