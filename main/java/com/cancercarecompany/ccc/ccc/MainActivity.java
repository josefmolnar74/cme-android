package com.cancercarecompany.ccc.ccc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends AppCompatActivity implements
        HomeFragment.OnTabSwitchToEventListener,
        JournalDetailsFragment.OnJournalCompletedListener,
        EventsDetailsFragment.OnEventsCompletedListener,
        CareTeamFamilyFragment.OnCareTeamFamilyCompletedListener,
        CareTeamHealthcareFragment.OnCareTeamHealthcareCompletedListener,
        CareTeamPatientFragment.OnCareTeamPatientCompletedListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private CustomViewPager mViewPager;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    public void OnTabSwitchToEvent(int position) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        TabLayout.Tab tab = tabLayout.getTabAt(3);
        tab.select();
    }

    public void onCareTeamFamilyComplete() {

        CareTeamExpListFragment careTeamFragment = (CareTeamExpListFragment)
                getSupportFragmentManager().findFragmentByTag("CareTeamExpListFragment");

        if (careTeamFragment != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content

            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            careTeamFragment.updateExpList();
        }
    }

    public void onCareTeamHealthcareComplete() {

        CareTeamExpListFragment careTeamFragment = (CareTeamExpListFragment)
                getSupportFragmentManager().findFragmentByTag("CareTeamExpListFragment");

        if (careTeamFragment != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content

            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            careTeamFragment.updateExpList();
        }
    }

    public void onCareTeamPatientComplete() {

        CareTeamExpListFragment careTeamFragment = (CareTeamExpListFragment)
                getSupportFragmentManager().findFragmentByTag("CareTeamExpListFragment");

        if (careTeamFragment != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content

            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            careTeamFragment.updateExpList();
        }
    }

    public void onJournalComplete() {
        // The user selected the headline of an article from the HeadlinesFragment
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article

        JournalExpandListFragment journalFragment = (JournalExpandListFragment)
                getSupportFragmentManager().findFragmentByTag("JournalExpListFragment");

        if (journalFragment != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content

            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            journalFragment.updateExpList();
        }
    }

    public void onEventsComplete() {
        // The user selected the headline of an article from the HeadlinesFragment
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article

        EventsExpandListFragment eventsFragment = (EventsExpandListFragment)
                getSupportFragmentManager().findFragmentByTag("EventsExpListFragment");

        if (eventsFragment != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content

            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            eventsFragment.updateExpList();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (CustomViewPager)  findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        setupTabIcons(tabLayout);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case  R.id.action_logout:
                SharedPreferences sharedPref = this.getSharedPreferences("login_settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(getString(R.string.login_auto_login), false);
                editor.commit();
                this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return HomeFragment.newInstance();
                case 1:
                    return CareTeamFragment.newInstance();
                case 2:
                    return JournalFragment.newInstance();
                case 3:
                    return EventsFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Hem";
                case 1:
                    return "Team";
                case 2:
                    return "Dagbok";
                case 3:
                    return "Resa";
            }
            return null;
        }
    }

    private void setupTabIcons(TabLayout tabLayout) {
        int[] tabIcons = {
                R.drawable.ic_tab_home,
                R.drawable.ic_tab_careteam,
                R.drawable.ic_tab_journal,
                R.drawable.ic_tab_journey
        };

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(0).setText(null);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(1).setText(null);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(2).setText(null);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(3).setText(null);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

}

