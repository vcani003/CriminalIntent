package com.bignerdranch.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by veron on 1/17/2018.
 */

//this class is a singleton

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

    public static CrimeLab get(Context context){
        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    //other classes will not be able to create a CrimeLab (priv. constructor)
    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();

        //adds 100 crimes
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i%2 == 0); // every other one will be set
            mCrimes.add(crime);
        }

    }

    //displays list of crimes
    public List<Crime> getCrimes(){
        return mCrimes;
    }

    //returns a crime with its id
    public Crime getCrime(UUID id){
        for(Crime crime: mCrimes){
            if(crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
    }

}
