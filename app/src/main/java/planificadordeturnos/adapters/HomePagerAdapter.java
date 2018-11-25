package planificadordeturnos.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import planificadordeturnos.OnClicks;
import planificadordeturnos.views.BusyShiftsFragment;
import planificadordeturnos.views.FreeShiftsFragment;

public class HomePagerAdapter extends FragmentPagerAdapter {

    private OnClicks onClicks;

    public HomePagerAdapter(FragmentManager fm, OnClicks onClicks) {
        super(fm);
        this.onClicks = onClicks;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return FreeShiftsFragment.newInstance(onClicks);
        }else if(position == 1){
            return BusyShiftsFragment.newInstance(onClicks);
        }
        return FreeShiftsFragment.newInstance(onClicks);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
