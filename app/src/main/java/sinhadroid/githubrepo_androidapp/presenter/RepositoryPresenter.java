package sinhadroid.githubrepo_androidapp.presenter;

/**
 * Created by deepanshu on 28/11/16.
 */
public interface RepositoryPresenter {
    void loadOwner(String url);

    void detachView();
}
