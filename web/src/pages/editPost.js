import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class EditPost extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewPosts'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewPosts);
        this.header = new Header(this.dataStore);
    }

    mount() {
        document.getElementById('edit').addEventListener('click', this.submit.bind(this)); // Update the event listener

        this.header.addHeaderToPage();

        this.client = new MusicPlaylistClient();
    }

    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const editButton = document.getElementById('edit');
        const origButtonText = editButton.innerText;
        editButton.innerText = 'Loading...';


        const postID = new URLSearchParams(window.location.search).get('postID'); // Get the postID from the URL
        const postBody = document.getElementById('post-body').value;

        console.log(postBody);

        const post = await this.client.editPost(postID, postBody, (error) => {
            editButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });

        this.dataStore.set('post', post);
    }

    redirectToViewPosts() {
        const post = this.dataStore.get('post');
        if (post != null) {
            window.location.href = `/index.html?postID=${post.postID}`;
        }
    }
}

const main = async () => {
    const editPost = new EditPost();
    editPost.mount();
};

window.addEventListener('DOMContentLoaded', main);
