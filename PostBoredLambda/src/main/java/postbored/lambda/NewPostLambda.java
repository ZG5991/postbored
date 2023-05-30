package postbored.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import postbored.activity.requests.NewPostRequest;
import postbored.activity.results.NewPostResult;

import java.util.logging.Logger;

public class NewPostLambda extends LambdaActivityRunner<NewPostRequest, NewPostResult>
        implements RequestHandler<AuthenticatedLambdaRequest<NewPostRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<NewPostRequest> input, Context context) {

        System.out.println("initializing runActivity in NewPostLambda");

        return super.runActivity(
                () -> {

                    System.out.println("initializing newPostRequest with input from request body in NewPostLambda");

                    NewPostRequest unauthenticatedRequest = input.fromBody(NewPostRequest.class);
                    //check print on line 24 of LambdaRequest

                    System.out.println("running input from user claims");

                    return input.fromUserClaims(claims ->
                            NewPostRequest.builder()
                                    .withPosterID(claims.get("email"))
                                    .withPostTitle(unauthenticatedRequest.getPostTitle())
                                    .withPostBody(unauthenticatedRequest.getPostBody())
                                    .withPosterName(claims.get("name"))
                                    .withTopic(unauthenticatedRequest.getTopic())
                                    .build());
                },

        (request, serviceComponent) -> {
                    try{
               return serviceComponent.provideNewPostActivity().handleRequest(request);
                    } catch (Exception e) {
                        System.out.println("ZACH" + e.toString());
                        throw e;
                    }
        });

    }
}
