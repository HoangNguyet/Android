package com.example.happy_read.adapter;

import static com.example.happy_read.until.until.GetImageResId;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.happy_read.R;
import com.example.happy_read.model.Rating;
import com.example.happy_read.until.until;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class RatingAdapter extends BaseAdapter {
    List<Rating> _ratings;
    Context _context;
    LayoutInflater _inflater;
    public  RatingAdapter(Context context,List<Rating> ratings){
        _context = context;
        _ratings = ratings;
        _inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return _ratings.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = _inflater.inflate(R.layout.activity_one_comment,null);
        ImageView imageAvatarComment = convertView.findViewById(R.id.imageAvatarRating);
        TextView textPersonRating = convertView.findViewById(R.id.namePersonRating);
        TextView textContentComment = convertView.findViewById(R.id.contenComent);
        Rating rating = _ratings.get(position);
        if(rating.GetUser().IsImagePathNull()){
            Picasso.get().load(GetImageResId(rating.GetUser().GetImagePath(),_context)).into(imageAvatarComment);
        }
        else{
            Bitmap imageBitMap = BitmapFactory.decodeFile(rating.GetUser().GetImagePath());
            imageAvatarComment.setImageBitmap(imageBitMap);
        }
        textPersonRating.setText(rating.GetUser().GetFullName());
        textContentComment.setText(rating.GetComment());
        return convertView;
    }
}
