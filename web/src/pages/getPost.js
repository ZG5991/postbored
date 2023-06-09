import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view post page of the website.
 */
class GetPost extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addPostsToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addPostsToPage);
        this.header = new Header(this.dataStore);
        console.log("GetPost constructor");
    }

    /**
     * Once the client is loaded, get the post metadata and content.
     */
    async clientLoaded() {
        const post = this.dataStore.get('post');
        if (!post) {
            return;
        }
        const postId = post.id;
        document.getElementById('post-title').innerText = "Loading Post ...";
        const retrievedPost = await this.client.getPost(postId);
        this.dataStore.set('post', retrievedPost);
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
     * When the post is updated in the datastore, update the post metadata on the page.
     */
    addPostsToPage() {
        const post = this.dataStore.get('post');
        if (post == null) {
            return;
        }

        document.getElementById('post-title').innerText = post.title;
        document.getElementById('post-body').innerText = post.body;
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const getPost = new GetPost();
    getPost.mount();
};

window.addEventListener('DOMContentLoaded', main);
