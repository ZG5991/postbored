# postbored
A social application to allow users to share their thoughts through simple text.
This version contains the features I consider to be the minimum functional requirements to operate the application.
Currently the app allows a user to: 

    create and log in to an account.
    create a new post.
    view all posts by date on the home page.
    edit posts.
    delete posts.
    and search other user's posts.

This application uses aws Lambda functions for it's API endpoints called from the remote front end app. 
All post and poster data is handled through DynamoDB and Cognito authentication.

Currently, all critical bugs have been addressed and tested for.
Some files may be left from a template used to build this app, and may reference "music playlist", these files have been modified or replaced, and any reference to existing material is in name only, and will be removed when time permits.

Some lamdba functions and API endpoints were left on the cutting room floor due to time constraints, these could be re-added in a later version with little modification to existing functionality.
These features include: 
    
    commenting on posts
    editing comments
    deleting comments
    'liking' and 'disliking' posts
    adding topic data to posts
    searching by post topic
    searching by post likes (most liked, least liked)

A DynamoDB GSI is used to handle post authors info for the User's Posts and search by UserName endpoints.
A similar GSI can be made to handle commenting under specified posts, as each post uses a unique serialized ID string for easy identification.
