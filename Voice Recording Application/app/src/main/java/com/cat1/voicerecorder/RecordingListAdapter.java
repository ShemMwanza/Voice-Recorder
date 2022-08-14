package com.cat1.voicerecorder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

public class RecordingListAdapter extends RecyclerView.Adapter<RecordingListAdapter.RecordingListHolder> {
    private File[] files;
    private TimeAgo timeAgo;
    private onItemListClick onItemListClick;
    public RecordingListAdapter(File[] files, onItemListClick onItemListClick){

        this.files = files;
        this.onItemListClick = onItemListClick;
    }
    @NonNull

    @Override
    public RecordingListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent,false);
        timeAgo = new TimeAgo();
        return new RecordingListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  RecordingListAdapter.RecordingListHolder holder, int position) {
        holder.list_title.setText(files[position].getName());
        holder.list_date.setText(timeAgo.getTimeAgo(files[position].lastModified()));
    }

    @Override
    public int getItemCount() {
        return files.length;
    }

    public class RecordingListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView list_image;
        private TextView list_title;
        private TextView list_date;

        public RecordingListHolder(@NonNull View itemView) {
            super(itemView);
            list_image = itemView.findViewById(R.id.list_imageView);
            list_title = itemView.findViewById(R.id.list_name);
            list_date = itemView.findViewById(R.id.list_date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListClick.onClickListener(files[getAdapterPosition()],getAdapterPosition());

        }
    }

    public interface onItemListClick{
        void onClickListener(File file, int position);
    }
}
