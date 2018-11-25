package planificadordeturnos.views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.desafiolatam.planificadordeturnos.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import planificadordeturnos.OnClicks;
import planificadordeturnos.adapters.ShiftsRecyclerAdapter;
import planificadordeturnos.models.Shift;

public class FreeShiftsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<Shift, ShiftsRecyclerAdapter.ShiftViewHolder> mAdapter;
    private OnClicks onClickNew;

    public FreeShiftsFragment() {
        // Required empty public constructor
    }

    public void setOnClick(OnClicks onClick){
        this.onClickNew = onClick;
    }

    public static FreeShiftsFragment newInstance(OnClicks onClickNew) {
        FreeShiftsFragment fragment = new FreeShiftsFragment();
        fragment.setOnClick(onClickNew);
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_free_shifts, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.LayoutManager mLayoutManager;

        mRecyclerView = view.findViewById(R.id.recyclerV);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("turnos");

        FirebaseRecyclerOptions<Shift> options =
                new FirebaseRecyclerOptions.Builder<Shift>()
                        .setQuery(query, Shift.class)
                        .build();

        mAdapter = new ShiftsRecyclerAdapter(options);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

}
