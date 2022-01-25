package com.example.ilanvermemobilprojeodevi.db.advert;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ilanvermemobilprojeodevi.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdvertAdapter extends RecyclerView.Adapter<AdvertAdapter.CardViewAdvertObjectHolder> {
    private Context mContext;
    private List<Advert> advertList;
//    private View.OnClickListener clickListener;

    public AdvertAdapter(Context mContext, List<Advert> advertList) {
        this.mContext = mContext;
        this.advertList = advertList;
    }

    @NonNull
    @Override
    public CardViewAdvertObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View iteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_advert_carviewlayout, parent, false);
        return new CardViewAdvertObjectHolder(iteView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewAdvertObjectHolder holder, int position) {
        Advert advert = advertList.get(position);

        holder.imageView.setImageResource(mContext.getResources().
                getIdentifier(advert.getImageString(), "drawable", mContext.getPackageName()));
        holder.title.setText(advert.getTitle());
//        holder.date.setText(advert.getDate());
        holder.description.setText(advert.getDescription());
        holder.itemView.setOnClickListener(advert.getClickListener());
        System.out.println("http://localhost:3000" + advert.getImageString());
        Picasso.get().load("http://10.0.2.2:3000" + advert.getImageString()).into(holder.imageView);
    }

    void setAdvertClickFunction() {
    }

    @Override
    public int getItemCount() {
        return advertList.size();
    }

    public class CardViewAdvertObjectHolder extends RecyclerView.ViewHolder {
        public CardView id;
        public ImageView imageView;
        public TextView title;
        public TextView description;
        public TextView date;
        public View.OnClickListener clickListener;


        public CardViewAdvertObjectHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_ItemAdvert);
            title = itemView.findViewById(R.id.txtTitle_ItemAdvert);
            description = itemView.findViewById(R.id.txtDescription_ItemAdvert);
//            date = itemView.findViewById(R.id.txtDate_ItemAdvert);
            id = itemView.findViewById(R.id.advertCardviewId);
        /*    itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "tiklandi name : " + title, Toast.LENGTH_LONG).show();
                    System.out.println("TIKLANDI TITLE : " + title);
                }
            });*/
        }
    }

    /*public View.OnClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }*/
}
