package manu.apps.a254memes;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{

    private Context mContext;
    private List<Upload> mUploads;

    public ImageAdapter(Context context, List<Upload> uploads)
    {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.meme_item,viewGroup, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int i) {
        Upload uploadCur = mUploads.get(i);
        imageViewHolder.meme_item_description.setText(uploadCur.getImgName());

        Glide
                .with(mContext)
                .load(uploadCur.getImgUrl())
                .centerCrop()
                .placeholder(R.drawable.imagewait)
                .into(imageViewHolder.item_meme_image_view);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        public TextView meme_item_description;
        public ImageView item_meme_image_view;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            meme_item_description = itemView.findViewById(R.id.memeItemDescription);
            item_meme_image_view = itemView.findViewById(R.id.item_meme_image_view);
        }
    }
}

