package planificadordeturnos.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.desafiolatam.planificadordeturnos.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import planificadordeturnos.models.Shift;

public class CreateEditShiftActivity extends AppCompatActivity {

    private static DatabaseReference freeShiftsDB = FirebaseDatabase.getInstance().getReference("Free Shifts");

    public CreateEditShiftActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_shift);

        final EditText dateEt = findViewById(R.id.dateEt);
        final EditText placeEt = findViewById(R.id.placeEt);
        final EditText startHourEt = findViewById(R.id.startHourEt);
        final EditText endHourEt = findViewById(R.id.endHourEt);
        Button saveShiftBtn = findViewById(R.id.saveShiftBtn);

        Intent intent = getIntent();
        final Shift shift = (Shift) getIntent().getSerializableExtra("Shift");


        if(shift != null){
            dateEt.setText(shift.getDate());
            placeEt.setText(shift.getPlace());
            startHourEt.setText(shift.getStartHour());
            endHourEt.setText(shift.getEndHour());
        }

        saveShiftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shift newShift = new Shift();
                newShift.setDate(String.valueOf(dateEt.getText()));
                newShift.setPlace(String.valueOf(placeEt.getText()));
                newShift.setStartHour(String.valueOf(startHourEt.getText()));
                newShift.setEndHour(String.valueOf(endHourEt.getText()));

                if(shift == null) {
                    String shiftKey = freeShiftsDB.push().getKey();
                    newShift.setId(shiftKey);
                    freeShiftsDB.child(shiftKey).setValue(newShift);
                }else{
                    freeShiftsDB.child(shift.getId()).setValue(newShift);
                }
                finish();
            }
        });


    }
}
