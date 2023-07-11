import PostBoredClient from '../api/postboredClient';
import BindingClass from "../util/bindingClass";

/**
 * The header component for the website.
 */
export default class Header extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'addHeaderToPage', 'createSiteTitle', 'createUserInfoForHeader',
            'createLoginButton', 'createLoginButton', 'createLogoutButton'
        ];
        this.bindClassMethods(methodsToBind, this);

        this.client = new PostBoredClient();
    }

    /**
     * Add the header to the page.
     */
    async addHeaderToPage() {
        const currentUser = await this.client.getIdentity();

        const siteTitle = this.createSiteTitle();
        const userInfo = this.createUserInfoForHeader(currentUser);

        const header = document.getElementById('header');
        header.appendChild(siteTitle);
        header.appendChild(userInfo);
    }

    createSiteTitle() {
        // Create the home button
        const homeButton = document.createElement('a');
        homeButton.classList.add('header_home');
        homeButton.href = 'javascript:void(0)'; // Set href to prevent page refresh
        homeButton.innerText = 'PostBored';

        // Create the dropdown list
        const dropdownList = document.createElement('ul');
        dropdownList.classList.add('dropdown-list');
        dropdownList.style.display = 'none'; // Hide the dropdown list initially

        // Add dropdown items to the list
        const item1 = this.createButton('button');
        item1.innerText = 'Home';
        item1.addEventListener('click', () => {
            window.location.href = 'index.html'; // Replace 'home.html' with the desired page URL
        });
        dropdownList.appendChild(item1);

        const item2 = this.createButton('button');
        item2.innerText = 'Search';
        item2.addEventListener('click', () => {
            window.location.href = 'search.html'; // Replace 'home.html' with the desired page URL
        });
        dropdownList.appendChild(item2);

        const item3 = this.createButton('button');
        item3.innerText = 'New Post';
        item3.addEventListener('click', () => {
            window.location.href = 'createPlaylist.html'; // Replace 'home.html' with the desired page URL
        });
        dropdownList.appendChild(item3);

        const item4 = this.createButton('button');
        item4.innerText = 'My Posts';
        item4.addEventListener('click', () => {
            window.location.href = 'usersPosts.html'; // Replace 'home.html' with the desired page URL
        });
        dropdownList.appendChild(item4);

        // Toggle dropdown visibility on button click
        homeButton.addEventListener('click', () => {
            dropdownList.style.display = dropdownList.style.display === 'none' ? 'block' : 'none';
        });

        // Create the site title container
        const siteTitle = document.createElement('div');
        siteTitle.classList.add('site-title');
        siteTitle.appendChild(homeButton);
        siteTitle.appendChild(dropdownList); // Append the dropdown list to the site title container

        return siteTitle;
    }


    createUserInfoForHeader(currentUser) {
        const userInfo = document.createElement('div');
        userInfo.classList.add('user');

        const childContent = currentUser
            ? this.createLogoutButton(currentUser)
            : this.createLoginButton();

        userInfo.appendChild(childContent);

        return userInfo;
    }

    createLoginButton() {
        return this.createButton('Login', this.client.login);
    }

    createLogoutButton(currentUser) {
        return this.createButton(`Logout: ${currentUser.name}`, this.client.logout);
    }

    createButton(text, clickHandler) {
        const button = document.createElement('a');
        button.classList.add('button');
        button.href = '#';
        button.innerText = text;

        button.addEventListener('click', async () => {
                    await clickHandler();
                });

        return button;
    }
}
