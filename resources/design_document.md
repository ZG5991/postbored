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
- post messages to the main page, a message can have one topic. e.g. "food, music, general, etc."
- view all messages on the main page chronologically by date
- view all messages on the main page by selected topic
- view my messages by date on a "my posts" page
- edit messages that I have posted
- delete messages I have posted

**potential stretch goals/ideas:**
- let me add users to a friends list
- let me only see posts by friends

## Project Scope

 **In Scope**

- post message to the main page board
- view all posted messages by date and topic on a main page
- view my own posted messages on a separate page
- edit my posted message
- delete my posted message

**Out of Scope**
- add and remove users from a friend list
- filter posts by friends

## Proposed Architecture Overview

The initial version will provide the minimum viable product -- posting and viewing messages to the main page.

I will use API Gateway and Lambda to create four endpoints (PostMessage, EditMessage, DeleteMessage, GetMessageHistoryByDate) that will handle the creation, modification, deletion, and retrieval of pertinent information.

We will store completed exercise logs each in a table in DynamoDB.

## API

**User Model**

      User Model
      String userEmail - email of the logged in user
      String friendsList - list of this specific user's friends Emails
      String messageHistory - a list of Message objects stored by their IDs

**Messages Model**

      String userEmail - see above
      String timeSent - LocalDateTime of when the message was originally posted or edited
      String messageTitle - title of the message, entered when creating or editing 
      String messageContent - main body of the message, entered when creating or editing
      String messageID - Unique ID of the specific message generated when the message is created
      String topic - topic ENUM to be selected when creating a new message, default to GENERAL

## PostMessage Endpoint

    Accepts POST requests to /messages
    From the main page
    Accepts data to create a new post on the messageboard with the required fields userID, messageContent, messageId, timeSent, and optional field category.

Client sends create post form to Website New Post page. Website New Post page sends a create request to CreatePostMessageActivity. CreatePostMessageActivity saves updates to the message board database.

## GetMessageHistoryByDate Endpoint

    Accepts GET requests to /messages
    From the main page
    Accepts startTime + endTime and returns the list of message board post objects in ascending order by date/time.

Client sends get messages form to Website Message Board page. Website Message Board page sends a get request to getMessagesByDateActivity. getMessagesByDateActivity obtains list of messages from database.
## EditMessage Endpoint

    Accepts PUT requests to /messages/:message_id
    From the user posts page, allows the logged in user to edit a message by
      messageID, changing existing messageContent for the specified post

## DeleteMessage Endpoint

    Accepts DELETE requests to /messages/:message_id
    Reads userID of current user, then accepts the messageID of the selected message on the user posts page
      and deletes that message, removing it from the table

## Tables


   **userTable**

      String userID (Primary Key ) - user's email
      String friendsList -  list of that users friendsIDs, stored as a String
      String messageHistory (GSI) - GSI containing a user's messageIDs to query the message table

      Indeces -
      message_history-index //  Partition key  message_id, list of the poster's messages by id to query from user post page

   **messageBoardTable**
      
      String messageID - HASH key UUID for the specific chatroom
      String timeSent - (Converted from LocalDateTime) - SORT key to view all messages in order
      String messageTitle - title of the message, limited to x chars
      String messageContent  - main body of the message, limit to x characters.
      String posterID - unique ID of the user who posted this message
      String topic - topic of the specific message

      Indeces -
      topic-index - HASH: topics by which to query from the main page

## Pages wireframes

![image](https://github.com/ZG5991/postbored/assets/92684029/010d25de-e4dc-4366-ba00-eca5d4a99e8b)
![image](https://github.com/ZG5991/postbored/assets/92684029/7f00a3d1-a436-4e4a-94bc-9076b1bfae42)
![image](https://github.com/ZG5991/postbored/assets/92684029/d20b8fcd-7340-48bd-98af-be0a907e8d8e)



 
