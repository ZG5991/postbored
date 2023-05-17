# PostBored


 ## **Problem Statement**

   PostBored is a public message board application for people passionate about sharing ideas.
   The goal is to provide an easy to use, responsive web application to allow users to post thier thoughts freely.
   This application should allow users to create and maintain a personal account,
   post to a public message board, and view posts by other users easily.

 ## **Top Questions to Resolve in Review**

   How can we ensure that the user application is friendly to use while still providing useful functionality?
   What endpoints do we need to create (i.e. PUT, POST, etc.)?

## Use Cases

**as a user, I would like to be able to:**
- create a unique account for myself
- post messages to the main page
- filter all messages by date
- edit messages that I have posted
- delete messages I have posted

**potential stretch goals/ideas:**
- tag messages with specific topics
- filter messages by topic
- let me add users to a friends list
- let me only see posts by friends

## Project Scope

 **In Scope**

- post message to the board
- view all posted messages by date
- edit a posted message
- delete a posted message

**Out of Scope**
- add and remove users from a friend list
- adding topics and filtering by topics
- filter posts by friends

## Proposed Architecture Overview

The initial version will provide the minimum viable product -- posting and viewing messages to the main page.

I will use API Gateway and Lambda to create four endpoints (PostMessage, EditMessage, DeleteMessage, GetMessageHistoryByDate) that will handle the creation, modification, deletion, and retrieval of pertinent information.

We will store completed exercise logs each in a table in DynamoDB.

## API

**User Model**

      User Model
      String user_id
      String friends_list
      String message_history

**Messages Model**

      String poster_user_id
      String message_content
      String message_id
      String time_sent
      String category

## PostMessage Endpoint

    Accepts POST requests to /messageBoardTable
    Accepts data to create a new post on the messageboard with the required fields userID, messageContent, messageId, timeSent, and optional field category.

Client sends create post form to Website New Post page. Website New Post page sends a create request to CreatePostMessageActivity. CreatePostMessageActivity saves updates to the message board database.

## GetMessageHistoryByDate Endpoint

    Accepts GET requests to /messageBoardTable
    Accepts a timeSent and returns the list of message board post objects in chronological order.

Client sends get messages form to Website Message Board page. Website Message Board page sends a get request to getMessagesByDateActivity. getMessagesByDateActivity obtains list of messages from database.
## EditMessage Endpoint

    Accepts PUT requests to /messageBoard/:message_id
    Accepts a messageID and edits existing messageContent for the specified user

## DeleteMessage Endpoint

    Accepts DELETE requests to /workouts/:workout_id
    Accepts a workoutId and deletes existing WorkoutLog for the specified customer

## Tables


   **userTable**

      String userID (Primary Key ) - UUID
      String friendsList (GSI) - GSI containing a list of that users friendsIDs, stored as a String
      String messageHistory (GSI) - GSI containing a user's messageIDs to query, stored as a String

      Indeces -
      friends_list-index //  Partition key user_id
      message_history-index //  Partition key  message_id
      date-index // Partition key date



   **messageBoardTable**
      
      String messageID - HASH key UUID for the specific chatroom
      String timeSent - (Converted from LocalDateTime) - SORT key to view all messages in order
      String messageContent  - main body of the message, limit to x characters.
      String posterID - unique ID of the user who posted this message

      Indeces -
      messageID-index - HASH: messageID
      posterID_timeSent-index - HASH: posterID SORT: timeSent

## Pages


 ![image](https://github.com/ZG5991/postbored/assets/92684029/6ecd90c7-6e2c-431d-a31e-cdc0eb9c1a75)

