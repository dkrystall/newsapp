package com.example.owner.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.owner.newsapp.model.NewsItem;

import java.util.ArrayList;

/**
 * Created by Owner on 6/29/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

    private String mNewsData[];

    private static ArrayList<NewsItem> mData;
    static ItemClickListener mListener;



    public NewsAdapter(ArrayList<NewsItem> data, ItemClickListener listener){
        mData = data;
        mListener = listener;
    }

    public interface ItemClickListener{
        void onItemClick(int clickedItemIndex);
    }

    public NewsAdapter() {

    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //int layoutIdForListWeather = R.layout.activity_list_item;

        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_item, parent, false);
        return new NewsAdapterViewHolder(inflatedView);
        //boolean shouldAttachToParentImmediately = false;
        //View view = inflater.inflate(layoutIdForListWeather, parent, shouldAttachToParentImmediately);
        //return new NewsAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (null == mData) return 0;
        return mData.size();
    }


    public static class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         private TextView mNewsTextView;
         private TextView mNewsTextView1;
         private TextView date;


        public NewsAdapterViewHolder(View itemView) {
            super(itemView);
            mNewsTextView = (TextView) itemView.findViewById(R.id.description);
            mNewsTextView1 = (TextView) itemView.findViewById(R.id.title);
            date = (TextView)itemView.findViewById(R.id.time);
            itemView.setOnClickListener(this);
        }

        public void bind(int pos){
            NewsItem repo = mData.get(pos);
            mNewsTextView.setText(repo.getTitle());
            mNewsTextView1.setText(repo.getDescription());
            date.setText(repo.getPublishedAt());
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            mListener.onItemClick(pos);
        }
    }

}
