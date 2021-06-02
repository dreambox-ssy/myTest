package com.jack.biz_homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jack.biz_homepage.R;
import com.jack.biz_homepage.bean.FreeItem;
import com.jack.lib_common.widget.RoundCornerImageView;
import com.kaelli.niceratingbar.NiceRatingBar;

import java.util.List;

/**
 * @fileName: RecommendAdapter
 * @author: susy
 * @date: 2021/5/30 21:12
 * @description: 推荐 Adapter
 */
public class FreeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_NETWORK = 100;
    public static final int TYPE_LOCAL = 101;
    private final int ITEM_FOOTER = 0x01;
    private final int ITEM_DATA = 0x02;
    private final int MAX_ITEM = 100;
    private int mCurType = TYPE_NETWORK;
    private List<FreeItem> mData;

    public FreeAdapter(int type) {
        mCurType = type;
    }

    public void setData(List<FreeItem> data){
        mData = data;
    }

    public void addData(List<FreeItem> data){
        if (mData == null){
            mData = data;
        }else {
            mData.addAll(data);
        }
    }

    public List<FreeItem> getData(){
        return mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case ITEM_DATA:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_free_item, parent, false);
                holder = new DataViewHolder(view);
                ((DataViewHolder)holder).context = parent.getContext();
                break;
            case ITEM_FOOTER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_free_list_footer_item, parent, false);
                holder = new FooterViewHolder(view);
                ((FooterViewHolder)holder).context = parent.getContext();
                break;
            default:
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mData == null || mData.size() <= 0) {
            return;
        }
        if (holder instanceof DataViewHolder) {
            FreeItem item = mData.get(position);
            if (item != null) {
                DataViewHolder dataViewHolder = (DataViewHolder)holder;
                Glide.with(dataViewHolder.context).load(item.getIconUrl()).into(dataViewHolder.rciIcon);
                dataViewHolder.tvName.setText(item.getName());
                dataViewHolder.tvCategory.setText(item.getCategory());
                dataViewHolder.tvIndex.setText(String.valueOf(position + 1));
                dataViewHolder.tvDownloadCount.setText("(" + item.getDownloadCount() + ")");
                dataViewHolder.ratingBar.setRating(item.getRate());
                if (position % 2 != 0) {
                    dataViewHolder.rciIcon.setType(RoundCornerImageView.TYPE_CIRCLE);
                }else {
                    dataViewHolder.rciIcon.setType(RoundCornerImageView.TYPE_ROUND);
                }
            }
        }else if (holder instanceof FooterViewHolder){
            FooterViewHolder footerViewHolder = (FooterViewHolder)holder;
            if (position == MAX_ITEM){
                footerViewHolder.pbLoading.setVisibility(View.GONE);
                footerViewHolder.tvInfo.setText(footerViewHolder.context.getResources().getString(R.string.load_no_more));
            }else {
                footerViewHolder.pbLoading.setVisibility(View.VISIBLE);
                footerViewHolder.tvInfo.setText(footerViewHolder.context.getResources().getString(R.string.load_more));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mCurType == TYPE_NETWORK) {
            if (position == getItemCount() - 1) {
                return ITEM_FOOTER;
            }
        }
        return ITEM_DATA;
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        int count = mData.size();
        if (mCurType == TYPE_NETWORK){
            count += 1;
        }
        return count;
    }

    /**
     * 数据区域的 DataViewHolder
     */
    class DataViewHolder extends RecyclerView.ViewHolder {
        Context context;
        TextView tvIndex;
        RoundCornerImageView rciIcon;
        TextView tvName;
        TextView tvCategory;
        NiceRatingBar ratingBar;
        TextView tvDownloadCount;

        public DataViewHolder(View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.tv_index);
            rciIcon = itemView.findViewById(R.id.free_icon);
            tvName = itemView.findViewById(R.id.tv_free_name);
            tvCategory = itemView.findViewById(R.id.tv_free_category);
            ratingBar = itemView.findViewById(R.id.nrb_rating_bar);
            tvDownloadCount = itemView.findViewById(R.id.tv_download_count);
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }
    }

    /**
     * 底部的 FooterViewHolder
     */
    class FooterViewHolder extends RecyclerView.ViewHolder{
        Context context;
        ProgressBar pbLoading;
        TextView tvInfo;

        public FooterViewHolder(View itemView) {
            super(itemView);
            pbLoading = itemView.findViewById(R.id.progressBar);
            tvInfo = itemView.findViewById(R.id.tv_info);
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }
    }
}
