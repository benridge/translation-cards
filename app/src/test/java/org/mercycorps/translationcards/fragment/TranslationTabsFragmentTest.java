package org.mercycorps.translationcards.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mercycorps.translationcards.BuildConfig;
import org.mercycorps.translationcards.R;
import org.mercycorps.translationcards.activity.addTranslation.AddNewTranslationContext;
import org.mercycorps.translationcards.activity.addTranslation.NewTranslation;
import org.mercycorps.translationcards.data.Dictionary;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.robolectric.Shadows.shadowOf;
import static org.robolectric.util.FragmentTestUtil.startFragment;

@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)
public class TranslationTabsFragmentTest {

    public static final String BUNDLE_KEY_DICTIONARIES = "AddNewTranslationContext";
    private TranslationTabsFragment translationTabsFragment;

    @Before
    public void setUp() throws Exception {
        translationTabsFragment = new TranslationTabsFragment();

        Bundle bundle = new Bundle();

        NewTranslation newTranslation = new NewTranslation(new Dictionary("arabic"));
        NewTranslation secondTranslationContext = new NewTranslation(new Dictionary("pashto"));
        List<NewTranslation> newTranslations = Arrays.asList(newTranslation, secondTranslationContext);
        AddNewTranslationContext addNewTranslationContext = new AddNewTranslationContext(newTranslations);
        bundle.putSerializable(BUNDLE_KEY_DICTIONARIES, addNewTranslationContext);

        translationTabsFragment.setArguments(bundle);
        startFragment(translationTabsFragment);
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(translationTabsFragment);
    }

    @Test
    public void shouldInflateFragmentWithLanguagesTab() {
        assertNotNull(getFragmentView());
    }

    @Test
    public void shouldContainHorizontalScrollView() {
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) getFragmentView().findViewById(R.id.languages_scroll);
        assertNotNull(horizontalScrollView);
    }

    @Test
    public void shouldContainArabicInLanguageTabWhenNewTranslationContextLanguageIsArabic() {
        LinearLayout linearLayout = (LinearLayout) getFragmentView().findViewById(R.id.languages_scroll_list);

        assertEquals("ARABIC", ((TextView) linearLayout.getChildAt(0).findViewById(R.id.tab_label_text)).getText());
    }

    @Test
    public void shouldSeeTabBorderUnderneathCurrentLanguageTab() {
        View tabBorder = getFragmentView().findViewById(R.id.tab_border);

        assertEquals(R.color.textColor, shadowOf(tabBorder.getBackground()).getCreatedFromResId());
    }

    @Test
    public void shouldSetLanguageTextToActiveColor() {
        TextView languageTextView = (TextView) getFragmentView().findViewById(R.id.tab_label_text);

        assertEquals(ContextCompat.getColor(languageTextView.getContext(), R.color.textColor), languageTextView.getCurrentTextColor());
    }

    @Test
    public void shouldHaveLanguageTabClickListenerWhenLanguageTabHasBeenCreated() {
        View languageTab = getFragmentView().findViewById(R.id.language_tab);

        assertNotNull(shadowOf(languageTab).getOnClickListener());
    }

    @Test
    public void shouldReturnCurrentTranslationForTab() {
        NewTranslation newTranslation = translationTabsFragment.getCurrentTranslation();

        assertEquals("arabic", newTranslation.getDictionary().getLabel());
    }

    @Test
    public void shouldReturnTranslationForSecondTabAfterSecondTabHasBeenClicked() {
        setEmptyOnTabSelectedListener();
        ((LinearLayout) getFragmentView().findViewById(R.id.languages_scroll_list)).getChildAt(1).performClick();

        NewTranslation newTranslation = translationTabsFragment.getCurrentTranslation();

        assertEquals("pashto", newTranslation.getDictionary().getLabel());
    }

    private void setEmptyOnTabSelectedListener() {
        translationTabsFragment.listener = new TranslationTabsFragment.OnLanguageTabSelectedListener() {
            @Override
            public void onLanguageTabSelected(NewTranslation currentTranslation) {

            }
        };
    }

    private View getFragmentView() {
        return translationTabsFragment.getView();
    }
}
