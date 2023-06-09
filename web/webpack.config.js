const path = require('path');
const CopyPlugin = require("copy-webpack-plugin");
const Dotenv = require('dotenv-webpack');

// Get the name of the appropriate environment variable (`.env`) file for this build/run of the app
const dotenvFile = process.env.API_LOCATION ? `.env.${process.env.API_LOCATION}` : '.env';

module.exports = {
  plugins: [
    new CopyPlugin({
      patterns: [
        {
          from: "static_assets", to: "../",
          globOptions: {
            ignore: ["**/.DS_Store"],
          },
        },
      ],
    }),
    new Dotenv({ path: dotenvFile }),
  ],
  optimization: {
    usedExports: true
  },
  entry: {
    createPost: path.resolve(__dirname, 'src', 'pages', 'createPost.js'),
    viewPlaylist: path.resolve(__dirname, 'src', 'pages', 'viewPlaylist.js'),
    searchPosts: path.resolve(__dirname, 'src', 'pages', 'searchPosts.js'),
    viewAllPostsIndex: path.resolve(__dirname, 'src', 'pages', 'viewAllPostsIndex.js'),
    viewPostsByUser: path.resolve(__dirname, 'src', 'pages', 'viewPostsByUser.js'),
    editPost: path.resolve(__dirname, 'src', 'pages', 'editPost.js')
  },
  output: {
    path: path.resolve(__dirname, 'build', 'assets'),
    filename: '[name].js',
    publicPath: '/assets/'
  },
  devServer: {
    static: {
      directory: path.join(__dirname, 'static_assets'),
    },
    port: 8000,
    client: {
      // overlay shows a full-screen overlay in the browser when there are js compiler errors or warnings
      overlay: true,
    },
  }
}
