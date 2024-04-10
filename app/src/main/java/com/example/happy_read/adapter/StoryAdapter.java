
package com.example.happy_read.adapter;

import static com.example.happy_read.until.until.SwitchPage;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.happy_read.R;
import com.example.happy_read.activity.MainComment.MainComment;
import com.example.happy_read.model.Story;
import com.example.happy_read.until.until;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StoryAdapter extends BaseAdapter {
    private Context context;
    AppCompatActivity activity;
    private ArrayList<Story> listTruyen;

    public StoryAdapter(Context context, ArrayList<Story> listTruyen,AppCompatActivity activity) {
        this.context = context;
        this.listTruyen = listTruyen;
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return listTruyen.size();
    }

    @Override
    public Object getItem(int position) {
        return listTruyen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //filter
    public void filterList(ArrayList<Story> filteredList) {
        listTruyen = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder{
        TextView txtTenTruyen;

        ImageView imgtruyen;
    }

    //lay ra ten va hinh anh truyen
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        viewHolder = new ViewHolder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item_layout, null);
        viewHolder.txtTenTruyen = convertView.findViewById(R.id.textviewTentruyen);
        viewHolder.imgtruyen = convertView.findViewById(R.id.imgNewTruyen);

        convertView.setTag(viewHolder);

        Story truyen = (Story)getItem(position);
        viewHolder.txtTenTruyen.setText(truyen.getTitle());

        int imageResId = until.GetImageResId(truyen.getImagePathDes(),context);
        Log.d("adadas",String.valueOf(imageResId));

        if (imageResId != 0) {
            Picasso.get().load(imageResId).error(R.drawable.background).into(viewHolder.imgtruyen);
        } else {
//            Log.e("Error Image", "Không tìm thấy ảnh trong tài nguyên");
            viewHolder.imgtruyen.setImageBitmap(BitmapFactory.decodeFile(truyen.getImagePathDes()));
        }
        //Event after click each story
        convertView.setOnClickListener(v -> {
            SwitchPage(activity, MainComment.class,truyen.getId(),"storyId");
        });
        return convertView;
    }

}
