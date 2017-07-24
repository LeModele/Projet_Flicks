#Projet *Projet_Flicks*

**Flickster** shows the latest movies currently playing in theaters. The app utilizes the Movie Database API to display images and basic information about these movies to the user.

Time spent: **24** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **scroll through current movies** from the Movie Database API
* [X] Layout is optimized with the [ViewHolder](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView#improving-performance-with-the-viewholder-pattern) pattern.
* [X] For each movie displayed, user can see the following details:
  * [X] Title, Poster Image, Overview (Portrait mode)
  * [X] Title, Backdrop Image, Overview (Landscape mode)

The following **optional** features are implemented:

* [X] Display a nice default [placeholder graphic](http://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library#configuring-picasso) for each image during loading.
* [X] Improved the user interface through styling and coloring.

The following **bonus** features are implemented:

* [X] Allow user to view details of the movie including ratings and popularity within a separate activity or dialog fragment.
* [ ] When viewing a popular movie (i.e. a movie voted for more than 5 stars) the video should show the full backdrop image as the layout.  Uses [Heterogenous ListViews](http://guides.codepath.com/android/Implementing-a-Heterogenous-ListView) or [Heterogenous RecyclerView](http://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView) to show different layouts.
* [X] Allow video trailers to be played in full-screen using the YouTubePlayerView.
    * [X] Overlay a play icon for videos that can be played.
    * [X] More popular movies should start a separate activity that plays the video immediately.
    * [X] Less popular videos rely on the detail page should show ratings and a YouTube preview.
* [X] Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce boilerplate code.
* [X] Apply rounded corners for the poster or background images using [Picasso transformations](https://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library#other-transformations)
* [ ] Replaced android-async-http network client with the popular [OkHttp](http://guides.codepath.com/android/Using-OkHttp) networking libraries.

The following **additional** features are implemented:

* [X] List anything else that you can get done to improve the app functionality!

- [Retrolambda](https://github.com/evant/gradle-retrolambda)

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://imgur.com/8Z90CxZ' title='image Walkthrough' width='' alt='image Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android

## License

    Copyright [2017] [ Charles Dominique - *Le Modele*]

    