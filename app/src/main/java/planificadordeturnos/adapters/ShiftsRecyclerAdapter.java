package planificadordeturnos.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.desafiolatam.planificadordeturnos.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import planificadordeturnos.OnClicks;
import planificadordeturnos.models.Shift;

public class ShiftsRecyclerAdapter extends FirebaseRecyclerAdapter<Shift, ShiftsRecyclerAdapter.ShiftViewHolder>{

    private OnClicks onClick;

    public ShiftsRecyclerAdapter(@NonNull FirebaseRecyclerOptions<Shift> options, OnClicks onClicks) {
        super(options);
        this.onClick = onClicks;
    }

    @Override
    protected void onBindViewHolder(@NonNull ShiftViewHolder holder, int position, @NonNull final Shift model) {
        holder.dateTv.setText(model.getDate().toString());
        holder.placeTv.setText(model.getPlace());
        holder.hourTv.setText(model.getStartHour() + " - " + model.getEndHour());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClickDetail(model);

            }
        });
    }

    @NonNull
    @Override
    public ShiftViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.shift_list_item, viewGroup, false);

        return new ShiftViewHolder(view);
    }

    public class ShiftViewHolder extends RecyclerView.ViewHolder {

        TextView dateTv;
        TextView placeTv;
        TextView hourTv;

        public ShiftViewHolder(View itemView) {
            super(itemView);

            dateTv = itemView.findViewById(R.id.dateTV);
            placeTv = itemView.findViewById(R.id.placeTV);
            hourTv = itemView.findViewById(R.id.hourTV);

        }
    }
}
