package com.sourtimestudios.www.emotimail.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sourtimestudios.www.emotimail.R;

import tabs.SlidingTabLayout;

/**
 * Created by user on 04/10/15.
 */
public class SlidingTabsFragment extends Fragment {


    static final String TAG = "SlidingTabsFragment";


    private SlidingTabLayout mSlidingTabLayout;

    /**
     * A {@link android.support.v4.view.ViewPager} which will be used in conjunction with the {@link SlidingTabLayout} above.
     */
    private ViewPager mViewPager;

    private FragmentManager fragmentManager;

    /**
     * Inflates the {@link android.view.View} which will be displayed by this {@link Fragment}, from the app's
     * resources.
     */

//    public SlidingTabsBasicFragment(FragmentManager fm){
//        fragmentManager = fm;
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tabsandpager, container, false);
    }

    // BEGIN_INCLUDE (fragment_onviewcreated)

    /**
     * This is called after the {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} has finished.
     * Here we can pick out the {@link View}s we need to configure from the content view.
     * <p/>
     * We set the {@link ViewPager}'s adapter to be an instance of {@link SamplePagerAdapter}. The
     * {@link SlidingTabLayout} is then given the {@link ViewPager} so that it can populate itself.
     *
     * @param view View created in {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // BEGIN_INCLUDE (setup_viewpager)
        // Get the ViewPager and set it's PagerAdapter so that it can display items
//        getActivity().getActionBar().setTitle("Restaurant");
        fragmentManager = getActivity().getSupportFragmentManager();
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter(fragmentManager));
        // END_INCLUDE (setup_viewpager)

        // BEGIN_INCLUDE (setup_slidingtablayout)
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.primaryColor));
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.accentColor);
            }
        });

        mSlidingTabLayout.setViewPager(mViewPager);
        // END_INCLUDE (setup_slidingtablayout)
    }
    // END_INCLUDE (fragment_onviewcreated)

    /**
     * The {@link android.support.v4.view.PagerAdapter} used to display pages in this sample.
     * The individual pages are simple and just display two lines of text. The important section of
     * this class is the {@link #getPageTitle(int)} method which controls what is displayed in the
     * {@link SlidingTabLayout}.
     */
    class SamplePagerAdapter extends FragmentStatePagerAdapter {

        public SamplePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * @return the number of pages to display
         */
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int i) {
//            CharSequence pagerFrag = "PagerFragment" + i;
//            final Fragment fragment;
            Log.i(TAG, "" + i);

            if (i == 0) {
                EmotisFragment fragment = new EmotisFragment();
                return fragment;

            } else if (i == 1) {
                RecentEmotisFragment fragment = new RecentEmotisFragment();
                return fragment;

            } else {
                LogFragment fragment = new LogFragment();
                return fragment;

            }

        }

        @Override
        public CharSequence getPageTitle(int position) {

            String[] titleArray = {"Emoticons","Popular","Logs"};
            return titleArray[position];
        }
    }
}
