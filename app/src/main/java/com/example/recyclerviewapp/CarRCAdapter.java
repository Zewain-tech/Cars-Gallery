package com.example.recyclerviewapp;

import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CarRCAdapter extends RecyclerView.Adapter<CarRCAdapter.CarViewHolder> {

    ArrayList<Car>cars;
    OnRecyclerViewItemClickListner listner;

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }



    public CarRCAdapter(ArrayList<Car> cars , OnRecyclerViewItemClickListner listner) {
        this.cars = cars;
        this.listner=listner;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.car_custom_layout,null,false);
        CarViewHolder viewHolder=new CarViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car c =cars.get(position);
        if (c.getImage()!= null && !c.getImage().isEmpty())
            holder.iv_car.setImageURI(Uri.parse( c.getImage()));
        else {
            holder.iv_car.setImageResource(R.drawable.landscape);
        }
        holder.tv_model.setText(c.getModel());
        holder.tv_color.setText(c.getColor());
        holder.tv_distance.setText(String.valueOf(c.getDistance()));
        try {
            holder.tv_color.setTextColor(Color.parseColor(c.getColor()));

        }catch (Exception e){

        }

        holder.iv_car.setTag(c.getId());

    }

    @Override
    public int getItemCount() {

        return cars.size();
    }

    class CarViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_car;
        TextView tv_model , tv_color , tv_distance;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_car=itemView.findViewById(R.id.custom_car_iv);
            tv_model=itemView.findViewById(R.id.custom_car_tv_model);
            tv_color=itemView.findViewById(R.id.custom_car_tv_color);
            tv_distance=itemView.findViewById(R.id.custom_car_tv_distance);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id=(int)iv_car.getTag();
                    listner.itemClick(id);
                }
            });

        }


    }
}
