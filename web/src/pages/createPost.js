import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create playlist page of the website.
 */
class CreatePost extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewPosts'], this);
        this.dataStore = new DataStore();
        console.log(this.redirectToViewPosts);
        this.dataStore.addChangeListener(this.redirectToViewPosts);
        this.header = new Header(this.dataStore);
    }

    /**
     * Add the header to the page and load the MusicPlaylistClient.
     */
    mount() {
        document.getElementById('create').addEventListener('click', this.submit);

        this.header.addHeaderToPage();

        this.client = new MusicPlaylistClient();
    }

    /**
     * Method to run when the create playlist submit button is pressed. Call the MusicPlaylistService to create the
     * playlist.
     */
    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('create');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Loading...';

        const postBody = document.getElementById('post-body').value;

        const post = await this.client.createPost(postBody, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });

        this.dataStore.set('post', post);
    }

    /**
     * When the post is updated in the datastore, redirect to the view playlist page.
     */
    redirectToViewPosts() {
        const post = this.dataStore.get('post');
        if (post != null) {
            window.location.href = `/index.html?postID=${post.postID}`;
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createPost = new CreatePost();
    createPost.mount();
};

window.addEventListener('DOMContentLoaded', main);


