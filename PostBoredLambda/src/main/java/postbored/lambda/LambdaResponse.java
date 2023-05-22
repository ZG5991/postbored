package postbored.lambda;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class LambdaResponse extends APIGatewayProxyResponseEvent {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private LambdaResponse(int statusCode, String body) {
        super.setStatusCode(statusCode);
        super.setBody(body);
        super.setHeaders(Map.of(
                "Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS",
                "Access-Control-Allow-Headers", "Content-Type,Authorization",
                "Access-Control-Allow-Origin", "*"
        ));
    }

    public static LambdaResponse success(Object payload) {
        try {
            return new LambdaResponse(200, MAPPER.writeValueAsString(payload));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to convert payload to JSON.");
        }
    }

    public static LambdaResponse success() {
        return new LambdaResponse(200, "");
    }

    public static LambdaResponse notFound() {
        return new LambdaResponse(404, "Not Found");
    }

    public static LambdaResponse error(String message) {
        return new LambdaResponse(500,
                String.format("{ \"error_message\": \"%s\" }", message));
    }

    public static LambdaResponse error(Exception e) {
        return error(e.getMessage());
    }
}
