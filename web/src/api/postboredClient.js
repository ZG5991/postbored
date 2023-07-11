import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the MusicPlaylistService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
  */
export default class PostBoredClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'getPostByID',
         'getAllPosts', 'getAllPostsForUser', 'createPost', 'deletePost', 'likePost', 'editPost'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    async getPostByID(id, errorCallback) {
        try {
            const response = await this.axiosClient.get(`posts/${postID}`);
            return response.data.posts;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async getAllPosts(errorCallback) {

        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create playlists.");
            const response = await this.axiosClient.get(`posts`,
            {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.posts;
        } catch (error) {
            this.handleError(error, errorCallback);
        }

    }

    async getAllPostsForUser(userName, errorCallback) {
      console.log("Client attempting to get posts for user...");

      try {

        const token = await this.getTokenOrThrow("Only authenticated users can create playlists.");
        const response = await this.axiosClient.get(`/author-index/${userName}`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });

        console.log("Client successfully got posts!");

        return response.data.posts; // Access the 'posts' property of the response data
      } catch (error) {
        console.error("Error retrieving posts:", error);
        this.handleError(error, errorCallback); // Call the error handling function or handle the error here
      }
    }

    /**
     * Create a new playlist owned by the current user.
     * @param name The name of the playlist to create.
     * @param tags Metadata tags to associate with a playlist.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The playlist that has been created.
     */
   async createPost(postBody, errorCallback) {
               try {
                   const token = await this.getTokenOrThrow("Only authenticated users can create playlists.");
                   const response = await this.axiosClient.post(`posts`, {
                       postBody: postBody
                   }, {
                       headers: {
                           Authorization: `Bearer ${token}`
                       }
                   });
                   return response.data.post;
               } catch (error) {
                   this.handleError(error, errorCallback)
               }
           }

    async editPost(postID, postBody, errorCallback) {
                                console.log(postID);
                             console.log(postBody);
                  try {
                      const token = await this.getTokenOrThrow("Only authenticated users can edit posts.");
                      const response = await this.axiosClient.put(`posts/${postID}`, {
                          postBody: postBody
                      }, {
                           headers: {
                               Authorization: `Bearer ${token}`
                           }
                       });
                      console.log(response.data.post);
                      return response.data.post;
                  } catch (error) {
                      this.handleError(error, errorCallback)
                  }
              }

   async deletePost(id, errorCallback) {
                  try {
                      const token = await this.getTokenOrThrow("Only authenticated users can delete playlists.");
                      const response = await this.axiosClient.delete(`posts/${id}`,
                      {
                                                 headers: {
                                                     Authorization: `Bearer ${token}`
                                                 }
                                             });
                      return response.data.posts;
                  } catch (error) {
                      this.handleError(error, errorCallback)
                  }
              }

    async likePost(id, errorCallback) {
                     try {
                         const token = await this.getTokenOrThrow("Only authenticated users can delete playlists.");
                         const response = await this.axiosClient.put(`posts/${id}`, {
                           headers: {
                               Authorization: `Bearer ${token}`
                           }
                       });
                         return response.data.posts;
                     } catch (error) {
                         this.handleError(error, errorCallback)
                     }
                 }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}
