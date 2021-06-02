package com.jack.biz_homepage.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.jack.biz_homepage.R;
import com.jack.biz_homepage.adapter.FreeAdapter;
import com.jack.biz_homepage.adapter.RecommendAdapter;
import com.jack.biz_homepage.bean.DetailItem;
import com.jack.biz_homepage.bean.FreeItem;
import com.jack.biz_homepage.bean.RecommendItem;
import com.jack.biz_homepage.mvp.HomePagePresenter;
import com.jack.biz_homepage.intf.IHomePageView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @fileName: HomePageFragment
 * @author: susy
 * @date: 2021/5/27 22:35
 * @description: 主页Fragment
 */
public class HomePageFragment extends Fragment implements IHomePageView {
    private RecyclerView mRvRecommendApplication;
    private RecyclerView mRvFreeApplication;
    private RecyclerView mRvSearch;
    private RecommendAdapter mRecommendAdapter;
    private FreeAdapter mFreeAdapter;
    private HomePagePresenter mHomePagePresenter;
    private FreeAdapter mSearchAdapter;
    private int mLastLoadDataItemPosition;

    public HomePageFragment() {

    }

    public static HomePageFragment newInstance(String param1, String param2) {
        HomePageFragment fragment = new HomePageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomePagePresenter = new HomePagePresenter();
        mHomePagePresenter.init(getContext(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);
        initView(rootView);
        mHomePagePresenter.startRequestData();
        return rootView;
    }

    private void initView(View rootView) {
        mRvRecommendApplication = rootView.findViewById(R.id.rv_recommend);
        mRvFreeApplication = rootView.findViewById(R.id.rv_free);
        mRvSearch = rootView.findViewById(R.id.rv_search);
        LinearLayoutManager llmRecommend = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        LinearLayoutManager llmFree = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        LinearLayoutManager llmSearch = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mRvRecommendApplication.setLayoutManager(llmRecommend);
        mRvFreeApplication.setLayoutManager(llmFree);
        mRvSearch.setLayoutManager(llmSearch);
        mRecommendAdapter = new RecommendAdapter();
        mFreeAdapter = new FreeAdapter(FreeAdapter.TYPE_NETWORK);
        mSearchAdapter = new FreeAdapter(FreeAdapter.TYPE_LOCAL);
        mRvRecommendApplication.setAdapter(mRecommendAdapter);
        mRvFreeApplication.setAdapter(mFreeAdapter);
        mRvSearch.setAdapter(mSearchAdapter);

        mRvFreeApplication.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastLoadDataItemPosition == mFreeAdapter.getItemCount()){
                    mHomePagePresenter.nextPage();
                }
                hideKeyboard(recyclerView);
            }

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager){
                    LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
                    int firstVisibleItem = manager.findFirstVisibleItemPosition();
                    int l = manager.findLastCompletelyVisibleItemPosition();
                    mLastLoadDataItemPosition = firstVisibleItem+(l-firstVisibleItem)+1;
                }
            }
        });

        EditText etSearch = rootView.findViewById(R.id.et_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mHomePagePresenter.searchLocalApplicationData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mRvSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                hideKeyboard(recyclerView);
            }
        });
    }

    /**
     * 隐藏键盘
     * @param view
     */
    private void hideKeyboard(View view){
        InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager.isActive()) {
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 更新推荐列表
     * @param lsRecommendApplication 推荐应用数据
     */
    @Override
    public void onUpdateRecommendApplicationData(List<RecommendItem> lsRecommendApplication) {
        mRecommendAdapter.setData(lsRecommendApplication);
        mRecommendAdapter.notifyDataSetChanged();
    }

    /**
     * 更新免费列表
     * @param lsFreeApplication 免费应用数据
     */
    @Override
    public void onUpdateFreeApplicationData(List<FreeItem> lsFreeApplication) {
        mFreeAdapter.addData(lsFreeApplication);
        mFreeAdapter.notifyDataSetChanged();
        for (FreeItem freeItem : lsFreeApplication){
            if (freeItem != null){
                mHomePagePresenter.loadDetailById(freeItem.getId());
            }
        }
    }

    /**
     * 更新免费列表详情数据
     * @param detailItem 免费应用的详情信息
     */
    @Override
    public void onUpdateFreeApplicationDetail(DetailItem detailItem) {
        List<FreeItem> lsData = mFreeAdapter.getData();
        for (int index = 0; index < lsData.size(); index ++){
            FreeItem item = lsData.get(index);
            if (item != null && TextUtils.equals(item.getId(), detailItem.getId())){
                item.setRate(getRating(detailItem.getRating()));
                item.setDownloadCount(detailItem.getRatingCount());
                lsData.set(index, item);
                mFreeAdapter.notifyItemChanged(index);
            }
        }
        mHomePagePresenter.addData(lsData);
    }

    /**
     * 更新搜索结果列表
     * @param lsFreeApplication 搜索结果
     */
    @Override
    public void onUpdateSearchResult(List<FreeItem> lsFreeApplication) {
        mSearchAdapter.setData(lsFreeApplication);
        mSearchAdapter.notifyDataSetChanged();
    }


    @Override
    public void onError(String info) {
        Toast.makeText(getContext(), info, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onShowSearchResult(boolean bShow){
        if (bShow){
            mRvFreeApplication.setVisibility(View.GONE);
            mRvSearch.setVisibility(View.VISIBLE);
        }else {
            mRvFreeApplication.setVisibility(View.VISIBLE);
            mRvSearch.setVisibility(View.GONE);
        }
    }

    private int getRating(String strRating){
        int rating = 0;
        if (strRating.contains("1")){
            rating = 1;
        }else if (strRating.contains("2")){
            rating = 2;
        }else if (strRating.contains("3")){
            rating = 3;
        }else if (strRating.contains("4")){
            rating = 4;
        }else if (strRating.contains("5")){
            rating = 5;
        }
        return rating;
    }
}