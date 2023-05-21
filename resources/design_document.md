# PostBored


 ## **Problem Statement**

   PostBored is a public post board application for people passionate about sharing ideas.
   The goal is to provide an easy to use, responsive web application to allow users to post thier thoughts freely.
   This application should allow users to create and maintain a personal account,
   post to a public post board, and view posts by other users easily.

 ## **Top Questions to Resolve in Review**

   How can we ensure that the user application is friendly to use while still providing useful functionality?
   What endpoints do we need to create (i.e. PUT, POST, etc.)?

## Use Cases

**as a user, I would like to be able to:**
- create a unique account for myself
- post messages to the main page, a post can have one topic. e.g. "food, music, general, etc."
- view all messages on the main page chronologically by date
- view all messages on the main page by selected topic
- view my messages by date on a "my posts" page
- edit messages that I have posted
- delete messages I have posted
- comment on other user's posts
- view my comment history
- edit my comments
- delete my comments
- search posts, comments, or users by keyword(stretch goal)
- Like and dislike posts (stretch goal)

## Project Scope

 **In Scope**

- post post to the main page board
- view all posted messages by date, ascending or descending
- filter all posts by topic on a main page
- view my own posted messages and comments on a separate page
- edit my posted post
- delete my posted post
- comment on posts
- edit my comments
- delete my comments

 **Out of Scope**

- search by keyword
- like/dislike button

## Proposed Architecture Overview

The initial version will provide the minimum viable product -- posting and viewing messages to the main page.

I will use API Gateway and Lambda to create four endpoints (PostMessage, EditMessage, DeleteMessage, GetMessageHistoryByDate) that will handle the creation, modification, deletion, and retrieval of pertinent information.

We will store completed exercise logs each in a table in DynamoDB.

## API

**Post Model**

      String posterID - cognito email of the poster
      String posterName - display name of the poster
      LocalDateTime datePosted - LocalDateTime of when the post was originally posted or edited
      String postTitle - title of the post, entered when creating or editing 
      String postContent - main body of the post, entered when creating or editing
      String postID - Unique ID of the specific post generated when the post is created
      String topic - topic ENUM to be selected when creating a new post, default to GENERAL
      Integer likesCounter - number of 'likes' on the post
      List<String> - comments - list of commentIDs tied to this post


**Comment Model**

      String commentID - Unique ID of the specific comment generated when the post is created
      String commenterID - cognito email of the commenter
      String commenterName - display name of the commenter 
      String commentContent - main body of the comment, entered when creating or editing
      LocalDateTime timePosted - LocalDateTime of when the comment was originally posted or edited
     

## NewPost Endpoint

    Accepts POST requests to /posts
    From the main page
    Accepts data to create a new post on the messageboard with the required fields userID, messageContent, messageId, timeSent, and optional field category.

Client sends create post form to Website New Post page. Website New Post page sends a create request to CreatePostMessageActivity. CreatePostMessageActivity saves updates to the post board database.

## GetPostByDate Endpoint

    Accepts GET requests to /postHistory-index
    From the main page
    Accepts startDate + endDate and returns the list of post board post objects in ascending order by date.
      Client sends get messages form to Website Message Board page. Website Message Board page sends a get request to getMessagesByDateActivity. getMessagesByDateActivity obtains list of messages from database.

## GetPostByTopic Endpoint

    Accepts GET requests to /topic-index
    From the main page
    Accepts a :topic and returns the list of post board post objects in ascending order by relevant topic.


## EditPost Endpoint

    Accepts PUT requests to /posts/:postID
    From the user content page, allows the logged in user to edit a post by
      messageID, changing existing messageContent for the specified post

## DeletePost Endpoint

    Accepts DELETE requests to /posts/:postID
    Reads userID of current user, then accepts the postID of the selected post on the user content page
      and deletes that post, removing it from the table

## NewComment Endpoint

    Accepts POST requests to /comments/
    From the main page
    Accepts data to create a new post on the messageboard with the required fields userID, messageContent, messageId, timeSent, and optional field category.

## GetPostComments Endpoint

    Accepts GET requests to /posts/comments
    From the main page
    Accepts postID and returns the list of comments associated in order by time under the relevant post.
    the User should be seeing the comments oldest first.

## EditComment Endpoint

    Accepts PUT requests to /comments/:commentContent
    From the user content page, allows the logged in user to edit a comment by
      commentID, changing existing body for the specified post

## DeleteComment Endpoint

    Accepts DELETE requests to /comments/:commentID
    Reads userID of current user, then accepts the commentID of the selected comment on the user content page
      and deletes that post, removing it from the table


## Tables

   **posts**
      
      String postID - HASH key UUID for the specific post
      String timeSent - (Converted from LocalDateTime) - sort key
      String postTitle - title of the post, limited to x chars
      String postBody  - main body of the post, limit to x characters.
      String posterID - email of the poster
      String posterName - display name of poster
      String topic - topic of the specific post
      List<String> comments - a list of commentIDs for the specific post
      Integer likesCounter - number of users who liked the post, dislikes lower the score

      Indeces -
      post-time-index - HASH postID, SORT TimeSent - list of the user's posts by id to query from user post page
      topic-index - HASH topic, SORT TimeSent
      post-comments-index - HASH comments, SORT timeSent - query the comments of a specific post

   **comments**
   
      String commentID - HASH key UUID for the specific post
      String timeSent - (Converted from LocalDateTime), use the Time over Date when sorting in order
      String commentContent  - main body of the comment, limit to x characters.
      String commenterID - email of the user who is commenting
      String postID - the specific post being commented on
     
      

## Pages wireframes

![image](https://github.com/ZG5991/postbored/assets/92684029/010d25de-e4dc-4366-ba00-eca5d4a99e8b)
![image](https://github.com/ZG5991/postbored/assets/92684029/7f00a3d1-a436-4e4a-94bc-9076b1bfae42)
![image](https://github.com/ZG5991/postbored/assets/92684029/d20b8fcd-7340-48bd-98af-be0a907e8d8e)

//diagram to be inserted upon completion
 
