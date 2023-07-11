import PostBoredClient from '../api/postboredClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view playlist page of the website.
 */
class ViewPostsByUser extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addPostsToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addPostsToPage);
        this.header = new Header(this.dataStore);
        console.log("viewpostByUser constructor");
    }

    /**
     * Once the client is loaded, get the playlist metadata and song list.
     */
    async clientLoaded() {
        console.log("getting user info...");

        const posterName = await this.client.getIdentity();
        console.log(posterName.name);

        console.log("getting user posts...");

        const userposts = await this.client.getAllPostsForUser(posterName.name);
        this.dataStore.set('users-posts', userposts);

        console.log("set userposts to datastore");
    }

    /**
     * Add the header to the page and load the MusicPlaylistClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.client = new PostBoredClient();
        this.clientLoaded();
    }

    /**
     * When the playlist is updated in the datastore, update the playlist metadata on the page.
     */
    addPostsToPage() {
        const posts = this.dataStore.get('users-posts');
        if (posts == null) {
            return;
        }

        const container = document.getElementById('users-posts'); // Assuming there's a container element with the ID 'post-container'
        console.log("created container of user posts.");

        const sortedPosts = posts.sort((a, b) => new Date(b.timeSent) - new Date(a.timeSent));

          sortedPosts.forEach(post => {
            const postContainer = document.createElement('div'); // Create a container for each post
            postContainer.classList.add('post');

            const posterInfoContainer = document.createElement('div'); // Create a container for the poster name and date
            posterInfoContainer.classList.add('poster-info');

            const posterNameElement = document.createElement('h5');
            const dateSentElement = document.createElement('h6');
            const postBodyElement = document.createElement('p');

            posterNameElement.classList.add('username');
            dateSentElement.classList.add('date');
            postBodyElement.classList.add('post-body');

            posterNameElement.textContent = post.posterName;
            dateSentElement.textContent = new Date(post.timeSent).toLocaleDateString(undefined, { dateStyle: 'short' });
            postBodyElement.textContent = post.postBody;

            posterInfoContainer.appendChild(posterNameElement);
            posterInfoContainer.appendChild(dateSentElement);
            postContainer.appendChild(posterInfoContainer);
            postContainer.appendChild(postBodyElement);

            const postButtonsContainer = document.createElement('div');
              postButtonsContainer.classList.add('post-buttons');

              const editButton = document.createElement('button');
              editButton.textContent = 'Edit';

              editButton.addEventListener('click', () => {
              event.preventDefault();
              const postID = post.postID;

              this.dataStore.set('original-body', post.postBody);


              window.location.href = `editPost.html?postID=${postID}&postBody=${post.postBody}`;
              });

              const deleteButton = document.createElement('button');
              deleteButton.textContent = 'Delete';

              deleteButton.addEventListener('click', () => {
              event.preventDefault();

                  // Call the delete endpoint for the specified post's ID
                  this.client.deletePost(post.postID)
                    .then(() => {
                      // Remove the post container from the DOM after successful deletion
                      postContainer.remove();
                    })
                    .catch(error => {
                      console.error('Error deleting post:', error);
                    });
                });

              postButtonsContainer.appendChild(editButton);
              postButtonsContainer.appendChild(deleteButton);
              postContainer.appendChild(postButtonsContainer);

            container.appendChild(postContainer);
          }); // Append the post container to the main container

    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewPostsByUser = new ViewPostsByUser();
    viewPostsByUser.mount();
};

window.addEventListener('DOMContentLoaded', main);
