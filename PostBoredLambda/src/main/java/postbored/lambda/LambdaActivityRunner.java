package postbored.lambda;

import postbored.dependency.DaggerServiceComponent;
import postbored.dependency.ServiceComponent;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class LambdaActivityRunner<Request, Result> {

    ServiceComponent serviceComponent;

    LambdaResponse runActivity(
            Supplier<Request> requestSupplier,
            BiFunction<Request, ServiceComponent, Result> handleRequest) {
        try {
            Request request = requestSupplier.get();
            ServiceComponent service = getService();
            Result result = handleRequest.apply(request, service);
            return LambdaResponse.success(result);
        } catch (Exception e) {
            return LambdaResponse.error(e);
        }
    }

    private ServiceComponent getService() {
        if (serviceComponent == null) {
            serviceComponent = DaggerServiceComponent.create();
        }
        return serviceComponent;
    }
}
