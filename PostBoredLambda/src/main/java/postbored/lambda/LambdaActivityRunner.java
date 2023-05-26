package postbored.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import postbored.dependency.DaggerServiceComponent;
import postbored.dependency.ServiceComponent;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class LambdaActivityRunner<Request, Result> {

    ServiceComponent serviceComponent;
    private final Logger log = LogManager.getLogger();

    LambdaResponse runActivity(
            Supplier<Request> requestSupplier,
            BiFunction<Request, ServiceComponent, Result> handleRequest) {

        log.info("runActivity");
        System.out.println("starting runActivity in LambdaActivityRunner");

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
        log.info("getService");
        if (serviceComponent == null) {
            serviceComponent = DaggerServiceComponent.create();
        }
        return serviceComponent;
    }
}
