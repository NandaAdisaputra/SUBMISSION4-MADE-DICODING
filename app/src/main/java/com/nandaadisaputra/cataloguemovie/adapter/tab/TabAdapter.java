package com.nandaadisaputra.cataloguemovie.adapter.tab;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.nandaadisaputra.cataloguemovie.fragment.MovieFragment;
import com.nandaadisaputra.cataloguemovie.fragment.TvFragment;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter extends FragmentPagerAdapter {
    private FragmentManager fragmentManager;
    private final List<Fragment> mfragment = new ArrayList<>();
    private final List<String> mFragmentList = new ArrayList<>();

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new MovieFragment();
        }
        return new TvFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return "Movie";
        }
        return "Tv";
    }

    @Override
    public int getCount() {
        return 2;
    }
    public void addFragment(Fragment fragment, String title) {
        mfragment.add(fragment);
        mFragmentList.add(title);
    }
}
