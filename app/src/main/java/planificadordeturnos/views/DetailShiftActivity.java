package planificadordeturnos.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.desafiolatam.planificadordeturnos.R;
import com.google.firebase.database.FirebaseDatabase;

import planificadordeturnos.models.Shift;
import planificadordeturnos.models.User;

public class DetailShiftActivity extends AppCompatActivity {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_shift);

        TextView dateDetailTv = findViewById(R.id.dateDetailTv);
        TextView placeDetailTv = findViewById(R.id.placeDetailTv);
        TextView hoursDetailTv = findViewById(R.id.hoursDetailTv);
        TextView assignedToDetailTv = findViewById(R.id.assignedToDetailTv);
        Button showLeads = findViewById(R.id.showLeadsBtn);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        Intent intent = getIntent();
        final Shift shift = (Shift) getIntent().getSerializableExtra("Shift");

        dateDetailTv.setText(shift.getDate());
        placeDetailTv.setText(shift.getPlace());
        hoursDetailTv.setText(shift.getStartHour() + " - " + shift.getEndHour());
        assignedToDetailTv.setText(shift.getAssignedLead());

        final String idShift = shift.getId();

        showLeads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ShowLeadsActivity.class);
                intent.putExtra("shiftID",shift.getId());
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Postular
                User user = new User();
                user.setId("xccvgdft");
                user.setName("Jennifer");
                user.setSpeciality("Paciencia");

                // Set user to the shift
                database.getReference("candidatosPorTurno").child(shift.getId()).child(user.getId())
                        .setValue(user);

                Snackbar.make(view, "Te acabas de postular a este turno", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

}
