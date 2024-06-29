package com.georgievl.myvacationapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.georgievl.myvacationapp.entities.Vacation;

import java.util.List;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {

    private List<Vacation> mVacations;
    private final Context context;
    private final LayoutInflater mInflater;

    public VacationAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class VacationViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvVacationNameAdapter;
        private final TextView tvHotelNameAdapter;
        private final TextView tvStartDateAdapter;
        private final TextView tvEndDateAdapter;
        public VacationViewHolder(@NonNull View itemView) {
            super(itemView);

            tvVacationNameAdapter = itemView.findViewById(R.id.tv_VacationNameAdapter);
            tvHotelNameAdapter = itemView.findViewById(R.id.tv_hotelNameAdapter);
            tvStartDateAdapter = itemView.findViewById(R.id.tv_startDateAdapter);
            tvEndDateAdapter = itemView.findViewById(R.id.tv_endDateAdapter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Vacation currentVacation = mVacations.get(position);

                    Intent intent = new Intent(context, VacationDetailsActivity.class);
                    intent.putExtra("id", currentVacation.getVacationId());
                    intent.putExtra("vacationTitle", currentVacation.getVacationTitle());
                    intent.putExtra("hotelName", currentVacation.getAccommodationPlace());
                    intent.putExtra("startDate", currentVacation.getStartDate());
                    intent.putExtra("endDate", currentVacation.getEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public VacationAdapter.VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.vacation, parent, false);
        return new VacationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VacationAdapter.VacationViewHolder holder, int position) {
        if (mVacations != null) {
            Vacation currentVacation = mVacations.get(position);

            String vacationTitle = currentVacation.getVacationTitle();
            String hotelName = currentVacation.getAccommodationPlace();
            String startDate = currentVacation.getStartDate().toString();
            String endDate = currentVacation.getEndDate().toString();

            holder.tvVacationNameAdapter.setText(vacationTitle);
            holder.tvHotelNameAdapter.setText(hotelName);
            holder.tvStartDateAdapter.setText(startDate);
            holder.tvEndDateAdapter.setText(endDate);
        }
    }

    @Override
    public int getItemCount() {
        if (mVacations != null) {
            return mVacations.size();
        }

        return 0;
    }

    public void setVacations(List<Vacation> vacations) {
        mVacations = vacations;
        notifyDataSetChanged();
    }

}
