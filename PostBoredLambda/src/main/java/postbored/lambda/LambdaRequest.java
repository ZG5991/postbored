package postbored.lambda;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static postbored.utilities.NullUtils.ifNull;


public class LambdaRequest<T> extends APIGatewayProxyRequestEvent {

    protected static final ObjectMapper MAPPER = new ObjectMapper();

    public T fromBody(Class<T> requestClass) {
        try {
            return MAPPER.readValue(super.getBody(), requestClass);
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
        Map<String, String> path = ifNull(super.getPathParameters(), Map.of());
        return converter.apply(path);
    }

    public T fromPathAndQuery(BiFunction<Map<String, String>, Map<String, String>, T> converter) {
        Map<String, String> path = ifNull(super.getPathParameters(), Map.of());
        Map<String, String> query = ifNull(super.getQueryStringParameters(), Map.of());
        return converter.apply(path, query);
    }

}
