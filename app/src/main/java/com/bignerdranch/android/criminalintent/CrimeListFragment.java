package com.bignerdranch.android.criminalintent;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by veron on 1/17/2018.
 */

public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container,false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();//sets up the crimeListFragments UI

        return view;
    }


    //inflates the layout for the list items in the recyclerView
    //implements onClickListener for the ViewHolder
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Button mSeriousButton;
        private ImageView mSolvedImageView;

        private Crime mCrime;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent, int view){

            super(inflater.inflate(view, parent, false));
            if(view == R.layout.list_item_crime) {
                itemView.setOnClickListener(this); // override onClick to make it do w.e you want
                mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
                mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
                mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);

            }
            else{
                itemView.setOnClickListener(this); // override onClick to make it do w.e you want
                mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
                mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
                mSeriousButton = (Button) itemView.findViewById(R.id.serious_button);
                mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
            }

        }

        @Override
        public void onClick(View view){
            // in this case, it will display a toast of the row info
//            Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!",
//                    Toast.LENGTH_SHORT).show();

            //Intent intent = new Intent(getActivity(), CrimeActivity.class);

            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }

        //bind textViews to show a particular crime
        public void bind(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            DateFormat df = new DateFormat();
            mDateTextView.setText(df.format("EEEE, MMM d, yyyy", mCrime.getDate()));
            mSolvedImageView.setVisibility(crime.isSolved()? View.VISIBLE : View.GONE);//if the crime is solved, view will be shown : else it wont be
            if(mSeriousButton != null) {
                mSeriousButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "Police Contacted.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
    }

    //then set up adapter to set up list of crimes for the CrimeHolder ViewHolder
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }

        //implement these methods

        //onCreateViewHolder is called by the RecyclerView when it needs a new ViewHolder to
        // display an item with. In this method, you create a LayoutInflater and use it
        // to construct a new CrimeHolder.
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            int view;
            if(viewType == 1){ //1 == serious crime layout
                view = R.layout.serious_list_item_crime;
                return new CrimeHolder(layoutInflater, parent, view);
            }
            else {
                view = R.layout.list_item_crime;
                return new CrimeHolder(layoutInflater, parent, view);
            }
        }

        @Override
        //calls the bind() method
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        @Override
        public int getItemViewType(int position) {
            if(mCrimes.get(position).isRequiresPolice()){
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }


    private void updateUI(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        //add adapter to recyclerView
        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }
}
