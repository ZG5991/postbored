package postbored.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.xspec.S;
import postbored.dynamodb.models.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Accesses data for a user using {@link User} to represent the model in DynamoDB.
 */
@Singleton
public class UserDao {
    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates an AlbumTrackDao object.
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the album_track table
     */
    @Inject
    public UserDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     * Retrieves an AlbumTrack by ASIN and track number.
     *
     * If not found, throws AlbumTrackNotFoundException.
     *
     * @param userID The id to look up
     *
     * @return The corresponding user if found
     */
    public User getUser(String userID) {
        User user = dynamoDbMapper.load(User.class, userID);
        if (null == user) {
            throw new IllegalArgumentException(
                String.format("Could not find user with userID '%s'", userID));
        }

        return user;
    }

    public List<String> getUserFriendsList(String userID) {
        User user = dynamoDbMapper.load(User.class, userID);
        if (null == user) {
            throw new IllegalArgumentException(
                    String.format("Could not find user with userID '%s'", userID));
        }

        return List.of(user.getFriendsList().split(","));
    }

    public List<String> getUserMessageHistory(String userID) {
        User user = dynamoDbMapper.load(User.class, userID);
        if (null == user) {
            throw new IllegalArgumentException(
                    String.format("Could not find user with userID '%s'", userID));
        }

        return List.of(user.getMessageHistory().split(","));
    }
}
