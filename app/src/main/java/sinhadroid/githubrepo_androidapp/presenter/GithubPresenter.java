package sinhadroid.githubrepo_androidapp.presenter;

/**
 * Created by deepanshu on 10/10/16.
 */
public interface GithubPresenter {
    void loadRepositories(String repoName);

    void detachView();
}
