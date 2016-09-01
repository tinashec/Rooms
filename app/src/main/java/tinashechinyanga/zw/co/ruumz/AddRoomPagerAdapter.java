package tinashechinyanga.zw.co.ruumz;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
public class AddRoomPagerAdapter extends SmartFragmentStatePagerAdapter {
    public AddRoomPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a HomeFragment (defined as a static inner class below).
        switch(position){
            case 0:
                return new AddRoomInfoFragment();
            case 1:
                return new AddRoomDetailFragment();
            case 2:
                return new AddLocationFragment();
            case 3:
                return new AddPhotosFragment();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show total pages.
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Room Infor.";
            case 1:
                return "Price & Availability";
            case 2:
                return "Location";
            case 3:
                return "Photos";
        }
        return null;
    }
}
