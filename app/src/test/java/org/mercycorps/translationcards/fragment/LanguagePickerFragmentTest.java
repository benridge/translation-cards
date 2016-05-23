package org.mercycorps.translationcards.fragment;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mercycorps.translationcards.BuildConfig;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;


@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)
public class LanguagePickerFragmentTest {

    @Test
    public void shouldNotBeNull() {
        LanguagePickerFragment languagePickerFragment = new LanguagePickerFragment();
        assertNotNull(languagePickerFragment.getView());
    }
}