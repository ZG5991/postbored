package postbored.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import postbored.Exceptions.UnauthorizedEditException;
import postbored.activity.requests.EditPostBodyRequest;
import postbored.activity.results.EditPostBodyResult;

public class EditPostBodyLambda extends LambdaActivityRunner<EditPostBodyRequest, EditPostBodyResult>
        implements RequestHandler<AuthenticatedLambdaRequest<EditPostBodyRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<EditPostBodyRequest> input, Context context) {

        return super.runActivity(
                () -> {
                    EditPostBodyRequest unauthenticatedRequest = input.fromBody(EditPostBodyRequest.class);
                    return input.fromUserClaims(claims ->
                            EditPostBodyRequest.builder()
                                    .withPostID(unauthenticatedRequest.getPostID())
                                    .withPostBody(unauthenticatedRequest.getPostBody())
                                    .withPosterID(claims.get("email"))
                                    .build());
                },
        (request, serviceComponent) ->
        {
            try {
                return serviceComponent.provideEditPostBodyActivity().handleRequest(request);
            } catch (UnauthorizedEditException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
