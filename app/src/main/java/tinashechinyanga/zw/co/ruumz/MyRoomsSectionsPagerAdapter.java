package tinashechinyanga.zw.co.ruumz;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
public class MyRoomsSectionsPagerAdapter extends FragmentPagerAdapter {
    public MyRoomsSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a HomeFragment (defined as a static inner class below).
        switch(position){
            case 0:
                return new MyRoomsFragment();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 1 total pages.
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return Integer.toString(R.string.title_activity_my_rooms);
            case 1:
                return "SECTION 2";
            case 2:
                return "SECTION 3";
        }
        return null;
    }
}
