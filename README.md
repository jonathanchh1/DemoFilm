
                                          NOLLYFILMS

This is a sample Android app that shows movies information from the Open Movie Database API, exposing two different
api endpoints. It Illustrates the usage of MVVM and Clean Architecture in Kotlin with various implementation ofknown and modern libraries for offline first support and state handling.

                                 TECHNOLOGIES/LIBRARIES
RXJAVA
DAGGER 2,
YOUTUBE API,
ROOM DB
PAGING LIBRARY
RETROFIT
LIVEDATA
RXANDROID
GSON,OKHTTP


<img src="https://github.com/jonathanchh1/NewsFeeding/blob/master/Screenshot_1561561253.png" width="300"> <img src="https://github.com/jonathanchh1/NewsFeeding/blob/master/Screenshot_1561559518.png" width="300"><img src="https://github.com/jonathanchh1/NewsFeeding/blob/master/Screenshot_1561561155.png" width="300"><img src="https://github.com/jonathanchh1/NewsFeeding/blob/master/Screenshot_1561561175.png" width="300"><img src="https://github.com/jonathanchh1/NewsFeeding/blob/master/Screenshot_1561561257.png" width="300"><img src="https://github.com/jonathanchh1/NewsFeeding/blob/master/Screenshot_1561559505.png" width="300">

Results are stored on a local database so it works offline. The user can tap on a movie to view more details and bookmark it. Bookmarked movies appear at the top of the list with a bookmark indicator.

Room from Android Architecture Components is used for the local cache. A flowable isused as the source of data, so changes on the database update the list.

RxJava is used as well through the application. Retrofit 2 for accessing the Open Movie Database API.
The API key must be configured on its build.gradle build config field. 


