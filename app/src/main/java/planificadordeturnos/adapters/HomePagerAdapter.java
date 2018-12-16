package planificadordeturnos.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import planificadordeturnos.OnClick;
import planificadordeturnos.views.BusyShiftsFragment;
import planificadordeturnos.views.FreeShiftsFragment;

public class HomePagerAdapter extends FragmentPagerAdapter {

    private OnClick onClick;

    public HomePagerAdapter(FragmentManager fm, OnClick onClick) {
        super(fm);
        this.onClick = onClick;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return FreeShiftsFragment.newInstance(onClick);
        }else if(position == 1){
            return BusyShiftsFragment.newInstance(onClick);
        }
        return FreeShiftsFragment.newInstance(onClick);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
