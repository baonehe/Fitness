package com.google.uddd_project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.google.uddd_project.R;

import java.util.List;

public class AllDayAdapter extends Adapter<AllDayAdapter.ViewHolder> {

    public List<WorkoutData> list;
    public Context mContext;
    public int position = -1;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public NumberProgressBar numberProgressBar;
        public CardView cardView;
        public ImageView imageView;
        public RelativeLayout rel1, rel2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.row_day);
            this.cardView = (CardView) itemView.findViewById(R.id.cardviewone);
            this.imageView = (ImageView) itemView.findViewById(R.id.restImageView);
            this.numberProgressBar = (NumberProgressBar) itemView.findViewById(R.id.progressbar);
            this.rel1 = (RelativeLayout) itemView.findViewById(R.id.rel1);
            this.rel2 = (RelativeLayout) itemView.findViewById(R.id.rel2);
        }
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.all_days_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.numberProgressBar.setMax(100);
        if ((i + 1) % 4 != 0 || i > 27) {
            holder.imageView.setVisibility(View.GONE);
            holder.rel2.setVisibility(View.GONE);


            holder.textView.setText(((WorkoutData) this.list.get(i)).getDay());
            holder.numberProgressBar.setVisibility(View.VISIBLE);
            holder.rel1.setVisibility(View.VISIBLE);
            if (((int) ((WorkoutData) this.list.get(i)).getProgress()) >= 99) {
                holder.numberProgressBar.setProgress(100);
            } else {
                holder.numberProgressBar.setProgress((int) ((WorkoutData) this.list.get(i)).getProgress());
            }
        } else {
            holder.numberProgressBar.setVisibility(View.GONE);
            holder.rel1.setVisibility(View.GONE);
            holder.textView.setText("Rest Day");
            holder.imageView.setVisibility(View.VISIBLE);
            holder.rel2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        List<WorkoutData> list = this.list;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public AllDayAdapter(Context context, List<WorkoutData> list) {
        this.mContext = context;
        this.list = list;
    }

    public long getItemId(int i) {
        return i;
    }

    public int getItemViewType(int i) {
        return i;
    }

}

