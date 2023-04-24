package fourteam.fantastic.btl;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPageAdapter extends FragmentStatePagerAdapter {


    public ViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new HomeListFragment();
            case 1: return new WishlistFragment();
            case 2: return new OrderFragment();
            case 3: return new MyCardFragment();
            default: return new HomeListFragment();
        }
    }


    @Override
    public int getCount() {
        return 4;
    }
}