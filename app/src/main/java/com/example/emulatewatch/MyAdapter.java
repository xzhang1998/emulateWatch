package com.example.emulatewatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.emulatewatch.helper.Applications;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<Applications> imageList = new ArrayList<>();
    private Context context;

    public MyAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.rounded_image_view
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,imageList.get(position).getAppName(),Toast.LENGTH_SHORT).show();
            }
        });

//        System.out.println(imageList.get(position).getImageUrl());
//
//        holder.rounded_image_view.startAnimation(animation);
        Glide.with(context)
                .load(imageList.get(position).getImageUrl())
                .into(holder.rounded_image_view);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public void setImageList(List<Applications> imageList) {
        this.imageList = imageList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView rounded_image_view;
        private CardView parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rounded_image_view = itemView.findViewById(R.id.rounded_image_view);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
