package sinhadroid.githubrepo_androidapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import sinhadroid.githubrepo_androidapp.R;
import sinhadroid.githubrepo_androidapp.model.Repository;
import sinhadroid.githubrepo_androidapp.model.User;
import sinhadroid.githubrepo_androidapp.presenter.RepositoryPresenter;
import sinhadroid.githubrepo_androidapp.presenter.RepositoryPresenterImpl;

/**
 * Created by deepanshu on 28/11/16.
 */
public class RepositoryActivity extends AppCompatActivity implements RepositoryView {

    private static final String EXTRA_REPOSITORY = "EXTRA_REPOSITORY";

    private Toolbar toolbar;
    private TextView descriptionText;
    private TextView homepageText;
    private TextView languageText;
    private TextView forkText;
    private TextView ownerNameText;
    private TextView ownerEmailText;
    private TextView ownerLocationText;
    private ImageView ownerImage;
    private View ownerLayout;

    private RepositoryPresenter mRepositoryPresenter;

    public static Intent newIntent(Context context, Repository repository) {
        Intent intent = new Intent(context, RepositoryActivity.class);
        intent.putExtra(EXTRA_REPOSITORY, repository);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_repository);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        descriptionText = (TextView) findViewById(R.id.text_repo_description);
        homepageText = (TextView) findViewById(R.id.text_homepage);
        languageText = (TextView) findViewById(R.id.text_language);
        forkText = (TextView) findViewById(R.id.text_fork);
        ownerNameText = (TextView) findViewById(R.id.text_owner_name);
        ownerEmailText = (TextView) findViewById(R.id.text_owner_email);
        ownerLocationText = (TextView) findViewById(R.id.text_owner_location);
        ownerImage = (ImageView) findViewById(R.id.image_owner);
        ownerLayout = findViewById(R.id.layout_owner);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Repository repository = getIntent().getParcelableExtra(EXTRA_REPOSITORY);
        bindRepositoryData(repository);

        this.mRepositoryPresenter = RepositoryPresenterImpl.newInstance(this);
        mRepositoryPresenter.loadOwner(repository.owner.url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRepositoryPresenter.detachView();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showOwner(final User owner) {
        ownerNameText.setText(owner.name);
        ownerEmailText.setText(owner.email);
        ownerEmailText.setVisibility(owner.hasEmail() ? View.VISIBLE : View.GONE);
        ownerLocationText.setText(owner.location);
        ownerLocationText.setVisibility(owner.hasLocation() ? View.VISIBLE : View.GONE);
        ownerLayout.setVisibility(View.VISIBLE);
    }

    private void bindRepositoryData(final Repository repository) {
        setTitle(repository.name);
        descriptionText.setText(repository.description);
        homepageText.setText(repository.homepage);
        homepageText.setVisibility(repository.hasHomepage() ? View.VISIBLE : View.GONE);
        languageText.setText(getString(R.string.text_language, repository.language));
        languageText.setVisibility(repository.hasLanguage() ? View.VISIBLE : View.GONE);
        forkText.setVisibility(repository.isFork() ? View.VISIBLE : View.GONE);
        //Preload image for user because we already have it before loading the full user
        Picasso.with(this)
                .load(repository.owner.avatarUrl)
                .placeholder(R.drawable.placeholder)
                .into(ownerImage);
    }
}
