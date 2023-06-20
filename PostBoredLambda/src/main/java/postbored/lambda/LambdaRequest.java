package postbored.lambda;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static postbored.utilities.NullUtils.ifNull;


public class LambdaRequest<T> extends APIGatewayProxyRequestEvent {

    protected static final ObjectMapper MAPPER = new ObjectMapper();
    protected final Logger log = LogManager.getLogger();

    public T fromBody(Class<T> requestClass) {

        log.info("fromBody");
        System.out.println("retrieving request info from request body");

        try {
            System.out.println("reading mapper value from request superclass body");
            return MAPPER.readValue(super.getBody(), requestClass); //bad request after calling this method.
        } catch (JsonProcessingException e) {
            throw new RuntimeException(
                    String.format("Unable to deserialize %s from request body", requestClass.getSimpleName()),
                    e);
        }
    }

    public T fromQuery(Function<Map<String, String>, T> converter) {

        Map<String, String> query = ifNull(super.getQueryStringParameters(), Map.of());
        return converter.apply(query);
    }

    public T fromPath(Function<Map<String, String>, T> converter) {
        log.info("fromPath");
        Map<String, String> path = ifNull(super.getPathParameters(), Map.of());
        return converter.apply(path);
    }

    public T fromPathAndQuery(BiFunction<Map<String, String>, Map<String, String>, T> converter) {
        log.info("fromPathAndQuery");
        Map<String, String> path = ifNull(super.getPathParameters(), Map.of());
        Map<String, String> query = ifNull(super.getQueryStringParameters(), Map.of());
        return converter.apply(path, query);
    }

}
