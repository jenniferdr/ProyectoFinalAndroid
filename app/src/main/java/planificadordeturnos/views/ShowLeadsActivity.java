package planificadordeturnos.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.desafiolatam.planificadordeturnos.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import planificadordeturnos.adapters.LeadsListAdapter;
import planificadordeturnos.models.Shift;
import planificadordeturnos.models.User;
import planificadordeturnos.onClickLeadList;

public class ShowLeadsActivity extends AppCompatActivity implements onClickLeadList {

    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<User, LeadsListAdapter.LeadViewHolder> mAdapter;
    private Shift shift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_leads_activity);

        RecyclerView.LayoutManager mLayoutManager;

        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        shift = (Shift) getIntent().getSerializableExtra("Shift");

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("leadsByShift")
                .child(shift.getId());

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(query, User.class)
                        .build();

        mAdapter = new LeadsListAdapter(options, (onClickLeadList) this);
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

    @Override
    public void onClickLead(User lead) {

        shift.setAssignedLead(lead.getName());

        FirebaseDatabase.getInstance()
                .getReference()
                .child("Free Shifts").child(shift.getId()).removeValue();

        FirebaseDatabase.getInstance()
                .getReference()
                .child("Busy Shifts").child(shift.getId()).setValue(shift);


    }
}
