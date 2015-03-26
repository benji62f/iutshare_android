package com.example.lefebvrb.iutshare;

import java.util.Locale;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {

    public static final String URL = "http://bouleau20.iut-infobio.priv.univ-lille1.fr:8080/";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
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
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_profile){
            return true;
        }

        if(id == R.id.action_connect){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        if(id == R.id.action_suscribe){
            Intent intent = new Intent(MainActivity.this, SuscribeActivity.class);
            startActivity(intent);
            return true;
        }

        if(id == R.id.action_add_lesson){
            Intent intent = new Intent(MainActivity.this, AddLesson.class);
            startActivity(intent);
            return true;
        }

        if(id == R.id.action_add_ad){
            Intent intent = new Intent(MainActivity.this, AddAd.class);
            startActivity(intent);
            return true;
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
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position) {
                case 0 :
                    return AccueilFragment.newInstance(position + 1);
                case 1 :
                    return CoursFragment.newInstance(position + 1);
                case 2 :
                    return AnnoncesFragment.newInstance(position + 1);
            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class AccueilFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static AccueilFragment newInstance(int sectionNumber) {
            AccueilFragment fragment = new AccueilFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public AccueilFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_accueil, container, false);

            String[] actus = new String[20];
            for(int i=0 ; i<actus.length ; i++)
                actus[i] = "Actualité "+(i+1);

            ListView actusLV = (ListView) rootView.findViewById(R.id.listview_accueil);
            ArrayAdapter<String> adapter = new ArrayAdapter(rootView.getContext(), android.R.layout.simple_list_item_1, actus);
            actusLV.setAdapter(adapter);

            return rootView;
        }
    }

    public static class CoursFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static CoursFragment newInstance(int sectionNumber) {
            CoursFragment fragment = new CoursFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public CoursFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_cours, container, false);

            String[] formations = getResources().getStringArray(R.array.formations);

            ListView cours = (ListView) rootView.findViewById(R.id.listview_cours);
            ArrayAdapter<String> adapter = new ArrayAdapter(rootView.getContext(), android.R.layout.simple_list_item_1, formations);
            cours.setAdapter(adapter);

            return rootView;
        }
    }

    public static class AnnoncesFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static AnnoncesFragment newInstance(int sectionNumber) {
            AnnoncesFragment fragment = new AnnoncesFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public AnnoncesFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_annonces, container, false);

            String[] annonces = new String[20];
            for(int i=0 ; i<annonces.length ; i++)
                annonces[i] = "Annonce "+(i+1);

            ListView annoncesLV = (ListView) rootView.findViewById(R.id.listview_annonces);
            ArrayAdapter<String> adapter = new ArrayAdapter(rootView.getContext(), android.R.layout.simple_list_item_1, annonces);
            annoncesLV.setAdapter(adapter);

            return rootView;
        }
    }

}
