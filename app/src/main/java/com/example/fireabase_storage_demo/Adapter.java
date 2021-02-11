package com.example.fireabase_storage_demo;

import android.content.Context;
import android.media.Image;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class Adapter extends RecyclerView.Adapter<Adapter.viewAdapter>{

    Context context;
    ArrayList<String>uris;


    Adapter(Context context,ArrayList<String>uris)
    {
        this.context=context;
        this.uris=uris;
    }


    @NonNull
    @Override
    public viewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.sample_view,parent,false);
        return new viewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewAdapter holder, int position) {

        String uri= uris.get(position);
        Picasso.with(context).load(uri).into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return uris.size();
    }

    public class viewAdapter extends RecyclerView.ViewHolder {
        ImageView imageView;
        public viewAdapter(@NonNull View itemView) {
            super(itemView);
             imageView = itemView.findViewById(R.id.image_view);

        }
    }
}
