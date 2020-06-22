package com.example.projecttwo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecttwo.Model.HomeData;
import com.example.projecttwo.R;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyviewHolder> {
    private List<HomeData> homeData = new ArrayList<>();
    private Context context;

    public RecycleAdapter(Context context,List<HomeData> homeData){
    this.homeData = homeData;
    this.context = context;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recyclerview_lay,parent,false);
        RecycleAdapter.MyviewHolder myviewHolder = new MyviewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        final HomeData data = homeData.get(position);

        holder.title.setText(data.getTitle());
        holder.imageView.setImageResource(data.getImgId());
        holder.description.setText(data.getDescription());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, data.getaClass()));
            }
        });




    }

    @Override
    public int getItemCount() {
        return homeData.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView title;
        private TextView description;
        private ImageView imageView;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            this.cardView = itemView.findViewById(R.id.cardViewz);
            this.title = itemView.findViewById(R.id.re_name);
            this.description = itemView.findViewById(R.id.re_Desc);
            this.imageView = itemView.findViewById(R.id.imageV);
        }
    }
}
