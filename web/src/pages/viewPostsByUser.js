import MusicPlaylistClient from '../api/musicPlaylistClient';
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
        const posterName = await this.client.getIdentity();
        const userposts = await this.client.getAllPostsForUser(posterName.name);
        this.dataStore.set('users-posts', userposts);
    }

    /**
     * Add the header to the page and load the MusicPlaylistClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.client = new MusicPlaylistClient();
        this.clientLoaded();
    }

    /**
     * When the playlist is updated in the datastore, update the playlist metadata on the page.
     */
    addPostsToPage() {
        const playlist = this.dataStore.get('users-posts');
        if (playlist == null) {
            return;
        }

        const container = document.getElementById('users-posts'); // Assuming there's a container element with the ID 'post-container'

                 posts.forEach(post => {
                    const postElement = document.createElement('post-container');
                    postElement.textContent = post.postBody;

                    container.appendChild(postElement);
                });
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
