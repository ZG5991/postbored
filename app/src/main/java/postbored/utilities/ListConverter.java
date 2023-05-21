package postbored.utilities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.List;

public class ListConverter implements DynamoDBTypeConverter<String, List> {

    private static final Gson GSON = new Gson();

    @Override
    public String convert(List list) {
        return GSON.toJson(list);
    }

    @Override
    public List unconvert(String ddbRep) {
        return GSON.fromJson(ddbRep, new TypeToken<List<String>>() {} .getType());
    }
}
