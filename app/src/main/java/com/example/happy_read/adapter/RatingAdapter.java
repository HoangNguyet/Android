//package com.example.happy_read.adapter;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.happy_read.R;
//import com.example.happy_read.model.Rating;
//import com.example.happy_read.model.Story;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//
//public class RatingAdapter extends BaseAdapter {
//    private Context context;
//    private ArrayList<Story> listStory;
//
//    public RatingAdapter(Context context, ArrayList<Story> listStory) {
//        this.context = context;
//        this.listStory = listStory;
//    }
//
//    // Các phương thức khác...
//
//    @Override
//    public int getCount() {
//        return listStory.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return listStory.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Khởi tạo ViewHolder nếu convertView chưa tồn tại
//        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
//            ViewHolder viewHolder = new ViewHolder();
//            viewHolder.txtTenTruyen = convertView.findViewById(R.id.textviewTentruyen);
//            viewHolder.imgtruyen = convertView.findViewById(R.id.imgNewTruyen);
//            convertView.setTag(viewHolder);
//        }
//
//        // Lấy ViewHolder từ convertView
//        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
//
//        // Lấy đối tượng Story từ listStory tại vị trí position
//        Story story = listStory.get(position);
//
//        // Hiển thị title của Story
//        viewHolder.txtTenTruyen.setText(story.getTitle());
//
//        // Load hình ảnh vào ImageView sử dụng thư viện Picasso
//        Picasso.get().load(story.getImagePathDes()).error(R.drawable.background).into(viewHolder.imgtruyen);
//
//        return convertView;
//    }
//
//    // ViewHolder pattern để tối ưu việc sử dụng lại các View
//    private static class ViewHolder {
//        TextView txtTenTruyen;
//        ImageView imgtruyen;
//    }
//}