package com.georgievl.myvacationapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.georgievl.myvacationapp.entities.Excursion;
import com.georgievl.myvacationapp.entities.Vacation;

import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {

    private List<Excursion> mExcursions;
    private final Context context;
    private final LayoutInflater mInflater;

    public ExcursionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class ExcursionViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvExcursionNameAdapter;
        private final TextView tvExcursionDateAdapter;

        public ExcursionViewHolder(@NonNull View itemView) {
            super(itemView);

            tvExcursionNameAdapter = itemView.findViewById(R.id.tv_excursionNameAdapter);
            tvExcursionDateAdapter = itemView.findViewById(R.id.tv_excursionDateAdapter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Excursion currentExcursion = mExcursions.get(position);

                    Intent intent = new Intent(context, ExcursionDetailsActivity.class);
                    intent.putExtra("id", currentExcursion.getExcursionId());
                    intent.putExtra("vacationTitle", currentExcursion.getExcursionTitle());
                    intent.putExtra("vacationId", currentExcursion.getVacationId());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public ExcursionAdapter.ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.excursion, parent, false);
        return new ExcursionAdapter.ExcursionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionAdapter.ExcursionViewHolder holder, int position) {
        if (mExcursions != null) {
            Excursion currentExcursion = mExcursions.get(position);

            String excursionTitle = currentExcursion.getExcursionTitle();
            String excursionDate = currentExcursion.getExcursionDate().toString();

            holder.tvExcursionNameAdapter.setText(excursionTitle);
            holder.tvExcursionDateAdapter.setText(excursionDate);
        }
    }

    @Override
    public int getItemCount() {
        if (mExcursions != null) {
            return mExcursions.size();
        }

        return 0;
    }

    public void setExcursions(List<Excursion> excursions) {
        mExcursions = excursions;
        notifyDataSetChanged();
    }
}
