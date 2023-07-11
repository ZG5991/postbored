import PostBoredClient from '../api/postboredClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view playlist page of the website.
 */
class ViewAllPostsIndex extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addPostsToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addPostsToPage);
        this.header = new Header(this.dataStore);
        console.log("viewAllPosts constructor");
    }

    /**
     * Once the client is loaded, get the playlist metadata and song list.
     */
    async clientLoaded() {
        const posts = await this.client.getAllPosts();
        this.dataStore.set('all-posts', posts);
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
      const posts = this.dataStore.get('all-posts');
      if (posts == null) {
        return;
      }

      const container = document.getElementById('all-posts'); // Assuming there's a container element with the ID 'post-container'

      const sortedPosts = posts.sort((a, b) => new Date(b.timeSent) - new Date(a.timeSent));

      const fragment = document.createDocumentFragment(); // Create a document fragment

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

        fragment.appendChild(postContainer); // Append the post container to the document fragment
      });

      container.appendChild(fragment); // Append the document fragment to the main container
    }

}
/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewAllPostsIndex = new ViewAllPostsIndex();
    viewAllPostsIndex.mount();
};

window.addEventListener('DOMContentLoaded', main);
