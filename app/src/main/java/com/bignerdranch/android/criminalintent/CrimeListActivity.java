package com.bignerdranch.android.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by veron on 1/17/2018.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();

    }
}
