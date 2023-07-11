// Filename: editPost.js

import PostBoredClient from '../api/postboredClient';
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

        this.client = new PostBoredClient();

        // Fetch the post-body from the datastore and set it as default text in the textarea
        const urlParams = new URLSearchParams(window.location.search);
        const postBody = urlParams.get('postBody');
        const textarea = document.getElementById('post-body');
        textarea.value = postBody;

        console.log(postBody);
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
            window.location.href = `/index.html`;
        }
    }
}

const main = async () => {
    const editPost = new EditPost();
    editPost.mount();
};

window.addEventListener('DOMContentLoaded', main);
