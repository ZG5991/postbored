package postbored.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import postbored.activity.requests.EditPostBodyRequest;
import postbored.activity.results.EditPostBodyResult;

public class EditPostBodyLambda extends LambdaActivityRunner<EditPostBodyRequest, EditPostBodyResult>
        implements RequestHandler<AuthenticatedLambdaRequest<EditPostBodyRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<EditPostBodyRequest> input, Context context) {

        return super.runActivity(
                () ->  { EditPostBodyRequest editPostBodyRequest = input.fromBody(EditPostBodyRequest.class);
                    return input.fromUserClaims(claims -> input.fromPath(path -> EditPostBodyRequest.builder()
                            .withPostID(path.get("postID"))
                            .withPostBody(editPostBodyRequest.getPostBody())
                            .withPosterID(claims.get("email"))
                            .build()));
                     },
                (request, serviceComponent) ->
                serviceComponent.provideEditPostBodyActivity().handleRequest(request));
    }
}
