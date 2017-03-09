# Facts

This project is created to display facts about Canada from JSON URL feed in Android.

## SDK Requirements
- Target SDK version	: 25
- Compile SDK version	: 25
- Minimum SDK version	: 14
- Build Tools version	: 25.0.2
- Gradle version		: 2.2.3

## External Components
- Volley : HTTP library to make network calls.
- Piccasso : Image downloading and caching library for Android.
- Gson : A Java serialization/deserialization library that can convert Java Objects into JSON and back.
- Butter Knife : To support Field and method binding for Android views.
- Common Lang : Helper utilities for the java.lang API, notably String manipulation methods in this app.
- Roboelectric - unit test framework to test-drive the development of your Android app.

## Usage
- Application displays Splash screen with copyright and logo information.
- Once user is on Facts list screen, application fetches the facts information from configured REST URL with progress indicator and loading message on screen.
- User can scroll throgh all the facts with title, description and image information on invididual list items.
- Image loading process will be lazy loading around list items. Image will also be cached in the app.
- User can select refresh button at top title or user can also use pull to refresh to update the feed information.

## Project Structure
- commons 	: Constant and common utilities
- gui 		: List Adapter and activity classes
- http 		: Singlton classes for network and service call
- model		: Fact and JSON response model classes

## Credits
- Volley (https://developer.android.com/training/volley/index.html)
- Picasso (http://square.github.io/picasso/)
- GSON (https://github.com/google/gson)
- ButterKnife (http://jakewharton.github.io/butterknife/)
- Apache Commons (https://commons.apache.org/proper/commons-lang/)



