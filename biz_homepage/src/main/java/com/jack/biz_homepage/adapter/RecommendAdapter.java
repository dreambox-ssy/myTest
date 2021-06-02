package com.jack.biz_homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jack.biz_homepage.R;
import com.jack.biz_homepage.bean.RecommendItem;
import com.jack.lib_common.widget.RoundCornerImageView;

import java.util.List;

/**
 * @fileName: RecommendAdapter
 * @author: susy
 * @date: 2021/5/30 21:22
 * @description: 推荐 Adapter
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendHolder> {
    private List<RecommendItem> mData;

    public RecommendAdapter() {
    }

    public void setData(List<RecommendItem> data){
        mData = data;
    }

    @Override
    public RecommendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recommend_item, parent, false);
        RecommendHolder holder = new RecommendHolder(view);
        holder.context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(RecommendHolder holder, int position) {
        if (mData == null || mData.size() <= 0) {
            return;
        }
        Glide.with(holder.context).load(mData.get(position).getIconUrl()).into(holder.rciIcon);
        holder.tvName.setText(mData.get(position).getName());
        holder.tvCategory.setText(mData.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    /**
     * 自定义的ViewHolder
     */
    class RecommendHolder extends RecyclerView.ViewHolder {
        Context context;
        RoundCornerImageView rciIcon;
        TextView tvName;
        TextView tvCategory;
        public RecommendHolder(View itemView) {
            super(itemView);
            rciIcon = itemView.findViewById(R.id.recommend_icon);
            tvName = itemView.findViewById(R.id.tv_recommend_name);
            tvCategory = itemView.findViewById(R.id.tv_recommend_category);
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }
    }
}
