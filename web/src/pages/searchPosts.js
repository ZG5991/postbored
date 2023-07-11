import PostBoredClient from '../api/postboredClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/*
The code below this comment is equivalent to...
const EMPTY_DATASTORE_STATE = {
    'search-criteria': '',
    'search-results': [],
};

...but uses the "KEY" constants instead of "magic strings".
The "KEY" constants will be reused a few times below.
*/

const SEARCH_CRITERIA_KEY = 'search-criteria';
const SEARCH_RESULTS_KEY = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [SEARCH_CRITERIA_KEY]: '',
    [SEARCH_RESULTS_KEY]: [],
};

/**
 * Logic needed for the view playlist page of the website.
 */
class SearchPosts extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'search', 'displaySearchResults'], this);

        // Create a new datastore with an initial "empty" state.
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        this.dataStore.addChangeListener(this.displaySearchResults);
    }

    /**
     * Add the header to the page and load the MusicPlaylistClient.
     */
    mount() {
        // Wire up the form's 'submit' event and the button's 'click' event to the search method.
        document.getElementById('search-posts-form').addEventListener('submit', this.search);
        document.getElementById('search-btn').addEventListener('click', this.search);

        this.header.addHeaderToPage();

        this.client = new PostBoredClient();
    }

    /**
     * Uses the client to perform the search,
     * then updates the datastore with the criteria and results.
     * @param evt The "event" object representing the user-initiated event that triggered this method.
     */
    async search(evt) {
        // Prevent submitting the form from reloading the page.
        evt.preventDefault();

        const searchCriteria = document.getElementById('search-criteria').value;
        const previousSearchCriteria = this.dataStore.get(SEARCH_CRITERIA_KEY);

        // If the user didn't change the search criteria, do nothing.
        if (previousSearchCriteria === searchCriteria) {
            return;
        }

        if (searchCriteria) {
            try {
                const results = await this.client.getAllPostsForUser(searchCriteria);
                this.dataStore.setState({
                    [SEARCH_CRITERIA_KEY]: searchCriteria,
                    [SEARCH_RESULTS_KEY]: results,
                });
            } catch (error) {
                console.error("Error retrieving posts:", error);
                // Handle the error here
            }
        } else {
            this.dataStore.setState(EMPTY_DATASTORE_STATE);
        }
    }

    /**
     * Pulls search results from the datastore and displays them on the HTML page.
     */
    displaySearchResults() {
        const searchCriteria = this.dataStore.get(SEARCH_CRITERIA_KEY);
        const searchResults = this.dataStore.get(SEARCH_RESULTS_KEY);

        const searchResultsContainer = document.getElementById('search-results-container');
        const searchCriteriaDisplay = document.getElementById('search-criteria-display');
        const searchResultsDisplay = document.getElementById('search-results-display');

        if (searchCriteria === '') {
            searchResultsContainer.classList.add('hidden');
            searchCriteriaDisplay.innerHTML = '';
            searchResultsDisplay.innerHTML = '';
        } else {
            searchResultsContainer.classList.remove('hidden');
            searchCriteriaDisplay.innerHTML = `"${searchCriteria}"`;

            if (searchResults.length === 0) {
                searchResultsDisplay.innerHTML = '<h4>No results found</h4>';
            } else {
                const container = document.createElement('div');
                for (const post of searchResults) {
                    const postContainer = document.createElement('div');
                    postContainer.classList.add('post');

                    const posterInfoContainer = document.createElement('div');
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

                    container.appendChild(postContainer);
                }

                searchResultsDisplay.innerHTML = '';
                searchResultsDisplay.appendChild(container);
            }
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const searchPosts = new SearchPosts();
    searchPosts.mount();
};

window.addEventListener('DOMContentLoaded', main);
