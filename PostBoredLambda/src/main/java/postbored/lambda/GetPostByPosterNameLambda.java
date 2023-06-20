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
                () -> input.fromPath(path -> GetPostByPosterNameRequest.builder().withPosterName(path.get("posterName")).build()),
                (request, serviceComponent) ->
           serviceComponent.provideGetPostByPosterNameActivity().handleRequest(request));
    }
}
