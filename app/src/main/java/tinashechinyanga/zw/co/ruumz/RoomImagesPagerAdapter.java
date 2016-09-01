package tinashechinyanga.zw.co.ruumz;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
public class RoomImagesPagerAdapter extends SmartFragmentStatePagerAdapter {
    public RoomImagesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a HomeFragment (defined as a static inner class below).
        switch(position){
            case 0:
                return new RoomImageOneFragment();
            case 1:
                return new RoomImageTwoFragment();
            case 2:
                return new RoomImageThreeFragment();
            case 3:
                return new RoomImageFourFragment();
            case 4:
                return new RoomImageFiveFragment();
            case 5:
                return new RoomImageSixFragment();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 1 total pages.
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Image 1";
            case 1:
                return "Image 2";
            case 2:
                return "Image 3";
            case 3:
                return "Image 4";
            case 4:
                return "Image 5";
            case 5:
                return "Image 6";
        }
        return null;
    }
}
