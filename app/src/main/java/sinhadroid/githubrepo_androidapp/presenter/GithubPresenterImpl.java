package sinhadroid.githubrepo_androidapp.presenter;

import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import sinhadroid.githubrepo_androidapp.Github;
import sinhadroid.githubrepo_androidapp.R;
import sinhadroid.githubrepo_androidapp.model.GithubService;
import sinhadroid.githubrepo_androidapp.model.Repository;
import sinhadroid.githubrepo_androidapp.view.GithubView;

/**
 * Created by deepanshu on 10/10/16.
 */

public class GithubPresenterImpl implements GithubPresenter {

    private GithubView mGithubView;
    private Subscription subscription;
    private List<Repository> repositories;

    // Attach a view
    public GithubPresenterImpl(GithubView githubView) {
        this.mGithubView = githubView;
    }

    public static GithubPresenterImpl newInstance(GithubView githubView) {
        return new GithubPresenterImpl(githubView);
    }

    // Detach a view
    @Override
    public void detachView() {
        this.mGithubView = null;
        if (subscription != null) subscription.unsubscribe();
    }

    @Override
    public void loadRepositories(String repoName) {
        String username = repoName.trim();
        if (username.isEmpty()) return;

        mGithubView.showProgressIndicator();
        if (subscription != null) subscription.unsubscribe();

        Github application = Github.get(mGithubView.getContext());
        GithubService githubService = application.getGithubService();

        subscription = githubService.publicRepositories(username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<List<Repository>>() {
                    @Override
                    public void onCompleted() {
                        if (!repositories.isEmpty()) {
                            mGithubView.showRepositories(repositories);
                        } else {
                            mGithubView.showMessage(R.string.text_empty_repos);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isHttp404(e)) {
                            mGithubView.showMessage(R.string.error_username_not_found);
                        } else {
                            mGithubView.showMessage(R.string.error_loading_repos);
                        }
                    }

                    @Override
                    public void onNext(List<Repository> repositories) {
                        GithubPresenterImpl.this.repositories = repositories;
                    }
                });
    }

    private static boolean isHttp404(Throwable error) {
        return error instanceof HttpException && ((HttpException) error).code() == 404;
    }

}
