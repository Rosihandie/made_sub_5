package com.rosihandie.moviecatalogue_sub_5.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rosihandie.moviecatalogue_sub_5.Fragment.FavoriteFragment;
import com.rosihandie.moviecatalogue_sub_5.Fragment.MoviesFragment;
import com.rosihandie.moviecatalogue_sub_5.Fragment.TvShowFragment;
import com.rosihandie.moviecatalogue_sub_5.R;

public class SectionPagerAdapter extends FragmentPagerAdapter {

    private final Context mcontext;

    public SectionPagerAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mcontext = context;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_text_1,
            R.string.tab_text_2,
            R.string.tab_text_3
    };

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MoviesFragment();
                break;

            case 1:
                fragment = new TvShowFragment();
                break;

            case 2:
                fragment = new FavoriteFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mcontext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
