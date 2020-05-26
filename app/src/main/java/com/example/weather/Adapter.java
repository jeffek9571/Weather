package com.example.weather;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.core.content.ContextCompat.startActivity;

public class Adapter extends RecyclerView.Adapter<Adapter.AdViewHolder> {
    Context mcontext;
    ArrayList<Post> mpost;

    public Adapter(Context context, ArrayList<Post> posts){
        this.mcontext=context;
        this.mpost=posts;
        Log.d("find", "2");
    }
    @NonNull
    @Override
    public AdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.recyclerview,parent,false);
        AdViewHolder av=new AdViewHolder(view);
        Log.d("find", "5");
        return av;
    }

    @Override
    public void onBindViewHolder(@NonNull AdViewHolder holder, int position) {
        Post post=mpost.get(position);
        final String total=post.getTotal();
//        int width=post.getWidth();
//        int height=post.getHeight();
        holder.tv1.setText(total);
        holder.iv1.setImageResource(R.mipmap.ic_launcher);
        Log.d("find", "6");

        holder.tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(mcontext,intent.class);
                Bundle bd=new Bundle();
                bd.putString("total",total);
                i.putExtras(bd);


                mcontext.startActivity(i,bd);

//                Intent i = new Intent();
//                i.putExtra("total",total);
//                startActivity();
            }

        });

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent ie = new Intent(Intent.ACTION_VIEW, Uri.parse(download_url));
//                mcontext.startActivity(ie);
//            }
//        });
    }


    @Override
    public int getItemCount() {
        Log.d("find", "4");
        return mpost.size();

    }

    public class AdViewHolder extends RecyclerView.ViewHolder{

        TextView tv1;
        ImageView iv1;
        public AdViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1=itemView.findViewById(R.id.tv1);
            iv1=itemView.findViewById(R.id.iv1);
            Log.d("find", "3");
        }
    }
}
