package com.example.user.stuqexample;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.stuqexample.model.TopUser;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaibingjie on 16/9/1.
 */
public class ImageAndTextAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<TopUser> mUsrs = new ArrayList<>();

    public ImageAndTextAdapter(Context context, List<TopUser> topUsrs) {
        mInflater = LayoutInflater.from(context);
        if (topUsrs != null) {
            mUsrs = topUsrs;
        }
    }

    @Override
    public int getCount() {
        return mUsrs.size();
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.image_text_ll, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.text = (TextView) convertView.findViewById(R.id.id_title);
            holder.content = (TextView) convertView.findViewById(R.id.id_txt);
            holder.draweeView = (SimpleDraweeView) convertView.findViewById(R.id.my_image_view);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
        }
        TopUser tmpUsr = mUsrs.get(position);
        holder.text.setText(tmpUsr.name);
        holder.content.setText(tmpUsr.signature);

        Uri uri = Uri.parse(tmpUsr.avatar);
        holder.draweeView.setImageURI(uri);
        //holder.imageView.setImageURI(tmpUsr.avatar);
        return convertView;
    }

    /**存放控件*/
    private class ViewHolder {
        TextView text, content;
        SimpleDraweeView draweeView;
    }


}
