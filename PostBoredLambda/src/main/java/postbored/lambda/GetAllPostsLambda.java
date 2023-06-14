package postbored.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import postbored.activity.requests.GetAllPostsRequest;
import postbored.activity.results.GetAllPostsResult;

import javax.management.InvalidAttributeValueException;

public class GetAllPostsLambda extends LambdaActivityRunner<GetAllPostsRequest, GetAllPostsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetAllPostsRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetAllPostsRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromUserClaims(claims ->
                        GetAllPostsRequest.builder()
                                .build()),
        (request, serviceComponent) ->
        {
            try {
                return serviceComponent.provideGetAllPostsActivity().handleRequest(request);
            } catch (InvalidAttributeValueException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
