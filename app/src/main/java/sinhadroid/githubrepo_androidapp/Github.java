package sinhadroid.githubrepo_androidapp;

import android.app.Application;
import android.content.Context;

import rx.Scheduler;
import rx.schedulers.Schedulers;
import sinhadroid.githubrepo_androidapp.model.GithubService;

/**
 * Created by deepanshu on 10/10/16.
 */

public class Github extends Application {

    private GithubService githubService;
    private Scheduler scheduler;

    public static Github get(Context context) {
        return (Github) context.getApplicationContext();
    }

    public GithubService getGithubService() {
        if (null == githubService) {
            githubService = GithubService.Factory.create();
        }
        return githubService;
    }

    //For setting mocks during testing
    public void setGithubService(GithubService githubService) {
        this.githubService = githubService;
    }

    public Scheduler defaultSubscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }
        return scheduler;
    }

    //User to change scheduler from tests
    public void setDefaultSubscribeScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
