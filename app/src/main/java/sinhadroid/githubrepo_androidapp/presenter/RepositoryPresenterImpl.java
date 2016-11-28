package sinhadroid.githubrepo_androidapp.presenter;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import sinhadroid.githubrepo_androidapp.Github;
import sinhadroid.githubrepo_androidapp.model.GithubService;
import sinhadroid.githubrepo_androidapp.model.User;
import sinhadroid.githubrepo_androidapp.view.RepositoryView;

/**
 * Created by deepanshu on 28/11/16.
 */

public class RepositoryPresenterImpl implements RepositoryPresenter {

    private RepositoryView mRepositoryView;
    private Subscription subscription;

    public RepositoryPresenterImpl(RepositoryView repositoryView) {
        this.mRepositoryView = repositoryView;
    }

    public static RepositoryPresenterImpl newInstance(RepositoryView repositoryView) {
        return new RepositoryPresenterImpl(repositoryView);
    }

    @Override
    public void loadOwner(String url) {
        Github application = Github.get(mRepositoryView.getContext());
        GithubService githubService = application.getGithubService();
        subscription = githubService.userFromUrl(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        mRepositoryView.showOwner(user);
                    }
                });
    }

    @Override
    public void detachView() {
        this.mRepositoryView = null;
        if (subscription != null) subscription.unsubscribe();
    }
}
