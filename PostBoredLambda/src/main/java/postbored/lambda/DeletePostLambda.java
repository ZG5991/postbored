package postbored.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import postbored.Exceptions.UnauthorizedEditException;
import postbored.activity.requests.DeletePostRequest;
import postbored.activity.results.DeletePostResult;

import javax.management.InvalidAttributeValueException;

public class DeletePostLambda extends LambdaActivityRunner<DeletePostRequest, DeletePostResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeletePostRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeletePostRequest> input, Context context) {

        return super.runActivity(
                () -> {
                    DeletePostRequest unauthenticatedRequest = input.fromBody(DeletePostRequest.class);
                    return input.fromUserClaims(claims ->
                     DeletePostRequest.builder()
                                .withPostID(unauthenticatedRequest.getPostID())
                                .withPosterID(claims.get("email"))
                                .build()
                    );
                },
        (request, serviceComponent) ->

        {
            try {
                return serviceComponent.provideDeletePostActivity().handleRequest(request);
            } catch (UnauthorizedEditException e) {
                throw new RuntimeException(e);
            }
        }
        );

    }
}
