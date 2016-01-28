package org.mercycorps.translationcards;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.net.Uri;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.robolectric.Shadows.shadowOf;

@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)
public class MainActivityTest {

    private MainActivity mainActivity;

    @Before
    public void setUp() throws Exception {
        mainActivity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void onCreate_shouldCreateCollectionsActivity() {
        assertNotNull(mainActivity);

        ListView cardCollections = (ListView) mainActivity.findViewById(R.id.collections_list);
        assertThat(cardCollections, is(notNullValue()));
    }

    @Test
    public void initFeedbackButton_shouldShowFeedbackButton() {
        Button feedbackButton = (Button) mainActivity.findViewById(R.id.feedback_button);

        assertThat(feedbackButton.getText().toString(), is("GIVE FEEDBACK"));
    }

    @Test
    public void initFeedbackButton_shouldLaunchFeedbackForm() {
        mainActivity.findViewById(R.id.feedback_button).performClick();

        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/forms/d/1p8nJlpFSv03MXWf67pjh_fHyOfjbK9LJgF8hORNcvNM/viewform?entry.1158658650=0.2.1"));
        assertThat(shadowOf(mainActivity).getNextStartedActivity(), is(expectedIntent));
    }

    @Test
    public void initCollectionsList_shouldHaveDefaultCollection() {
        ListView cardCollections = (ListView) mainActivity.findViewById(R.id.collections_list);

        assertThat(cardCollections.getAdapter().getCount(), is(2));
        assertThat((String) cardCollections.getAdapter().getItem(0), is("Default"));
    }

    @Test
    public void initCollectionsList_shouldPopulateCollectionNameTextView() {
        ListView cardCollections = (ListView) mainActivity.findViewById(R.id.collections_list);
        View cardCollectionsListItem = cardCollections.getAdapter().getView(0, null, cardCollections);

        TextView collectionName = (TextView) cardCollectionsListItem.findViewById(R.id.collection_name);
        assertThat(collectionName, is(notNullValue()));
        assertThat(collectionName.getText().toString(), is("Default"));
    }

    @Test
    public void initCollectionsList_shouldOpenMainActivityWhenItemClicked() {
        ListView cardCollections = (ListView) mainActivity.findViewById(R.id.collections_list);
        View cardCollectionsListItem = cardCollections.getAdapter().getView(0, null, cardCollections);

        TextView collectionName = (TextView) cardCollectionsListItem.findViewById(R.id.collection_name);
        collectionName.performClick();

        Intent expectedIntent = new Intent(mainActivity, TranslationsActivity.class);
        assertThat(shadowOf(mainActivity).getNextStartedActivity(), is(expectedIntent));
    }
}
