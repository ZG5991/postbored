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
                () -> input.fromPath(path -> GetPostByIDRequest.builder().withPostID(path.get("posterID")).build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetPostByIDActivity().handleRequest(request));

    }
}
