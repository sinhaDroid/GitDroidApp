# Github-Repo -> Android-MVP

The sample app displays a list of GitHub public repositories for a given username. Tapping on one of them will open a repository details screen, where more information about the repo can be found. This screen also shows information about the owner of the repository.

![alt-text-1](https://github.com/sinhaDroid/GithubRepo-Android-MVP/blob/master/Screenshot_1480329510.png?raw=true "MAIN SCREEN") ![alt-text-2](https://github.com/sinhaDroid/GithubRepo-Android-MVP/blob/master/Screenshot_1480329515.png?raw=true "USER SCREEN")

# Libraries used
*AppCompat, CardView and RecyclerView
*RxJava & RxAndroid
*Retrofit 2
*Picasso
*Mockito
*Robolectric

# MVP - Model View Presenter
In this project you will find the sample app implemented following this pattern. When using mvp, Activities and Fragments become part of the view layer and they delegate most of the work to presenters. Each Activity has a matching presenter that handles accessing the model via the GithubService. They also notify the Activities when the data is ready to display. Unit testing presenters becomes very easy by mocking the view layer (Activities).
