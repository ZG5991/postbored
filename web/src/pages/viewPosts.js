import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view playlist page of the website.
 */
class ViewPosts extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addPlaylistToPage', 'addPostsToPage', 'addSong'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addPlaylistToPage);
        this.dataStore.addChangeListener(this.addSongsToPage);
        this.header = new Header(this.dataStore);
        console.log("viewpost constructor");
    }

    /**
     * Once the client is loaded, get the playlist metadata and song list.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const playlistId = urlParams.get('id');
        document.getElementById('playlist-name').innerText = "Loading Playlist ...";
        const playlist = await this.client.getPlaylist(playlistId);
        this.dataStore.set('playlist', playlist);
        document.getElementById('songs').innerText = "(loading songs...)";
        const songs = await this.client.getPlaylistSongs(playlistId);
        this.dataStore.set('songs', songs);
    }

    /**
     * Add the header to the page and load the MusicPlaylistClient.
     */
    mount() {
        document.getElementById('get-post').addEventListener('click', this.viewPost);

        this.header.addHeaderToPage();

        this.client = new MusicPlaylistClient();
        this.clientLoaded();
    }

    /**
     * When the playlist is updated in the datastore, update the playlist metadata on the page.
     */
    addPostsToPage() {
        const posts = this.dataStore.get('post-list');
        if (post == null) {
            return;
        }

        posts.forEach(post => {
            const postContainer = document.createElement('div');
            postContainer.classList.add('post-container');

            const postDetails = document.createElement('div');
            postDetails.classList.add('post-details');
            postContainer.appendChild(postDetails);

            const titleElement = document.createElement('p');
            titleElement.classList.add('post-title');
            titleElement.textContent = post.title;
            postDetails.appendChild(titleElement);

            const dateElement = document.createElement('p');
            dateElement.classList.add('post-date');
            dateElement.textContent = post.date;
            postDetails.appendChild(dateElement);

            const authorElement = document.createElement('p');
            authorElement.classList.add('post-author');
            authorElement.textContent = post.author;
            postDetails.appendChild(authorElement);

            const bodyElement = document.createElement('p');
            bodyElement.textContent = post.body;
            postContainer.appendChild(bodyElement);

            postsList.appendChild(postContainer);
          });

        document.getElementById('post-title').innerText = post.title;
        document.getElementById('post-body').innerText = post.body;

//        let tagHtml = '';
//        let tag;
//        for (tag of playlist.tags) {
//            tagHtml += '<div class="tag">' + tag + '</div>';
//        }
//        document.getElementById('tags').innerHTML = tagHtml;
    }

    /**
     * Method to run when the add song playlist submit button is pressed. Call the MusicPlaylistService to add a song to the
     * playlist.
     */
    async addPost() {

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const post = this.dataStore.get('posts');
        if (post == null) {
            return;
        }

        document.getElementById('add-post').innerText = 'Adding...';
        const title = document.getElementById('post-title').value;
        const body = document.getElementById('post-body').value;
        const postID = post.id;

        const postList = await this.client.addPostToPostList(postID, (error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });

        this.dataStore.set('posts', postList);

        document.getElementById('add-post').innerText = 'Add Post';
        document.getElementById("add-post-form").reset();
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewPosts = new ViewPosts();
    viewPosts.mount();
};

window.addEventListener('DOMContentLoaded', main);