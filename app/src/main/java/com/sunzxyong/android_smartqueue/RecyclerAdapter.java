package com.sunzxyong.android_smartqueue;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 晓勇 on 2015/9/11 0011.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder>{
    private List<String> datas;
    public RecyclerAdapter(List<String> datas){
        this.datas = datas;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item, viewGroup, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder myHolder, int i) {
        myHolder.mTextView.setText(datas.get(i));
        myHolder.itemView.setTag(i);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView mTextView;
        public MyHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
