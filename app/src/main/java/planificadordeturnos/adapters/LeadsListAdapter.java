package planificadordeturnos.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.desafiolatam.planificadordeturnos.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import planificadordeturnos.models.User;
import planificadordeturnos.onClickLeadList;

public class LeadsListAdapter extends FirebaseRecyclerAdapter<User, LeadsListAdapter.LeadViewHolder> {

    onClickLeadList onClick;

    public LeadsListAdapter(@NonNull FirebaseRecyclerOptions<User> options, onClickLeadList onClick) {
        super(options);
        this.onClick = onClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull LeadViewHolder holder, int position, @NonNull final User model) {
        holder.userNameTv.setText(model.getName());
        holder.specialityTv.setText("Especialidad: " + model.getSpeciality());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onClick.onClickLead(model);

                }
            });

    }

    @NonNull
    @Override
    public LeadViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lead_list_item, viewGroup, false);

        return new LeadViewHolder(view);
    }

    public class LeadViewHolder extends RecyclerView.ViewHolder {

        TextView userNameTv;
        TextView specialityTv;

        public LeadViewHolder(View itemView) {
            super(itemView);
            userNameTv = itemView.findViewById(R.id.userNameTv);
            specialityTv = itemView.findViewById(R.id.specialityTv);
        }
    }
}
