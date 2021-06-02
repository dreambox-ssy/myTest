package com.jack.biz_homepage.mvp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.jack.biz_homepage.bean.DetailItem;
import com.jack.biz_homepage.bean.FreeItem;
import com.jack.biz_homepage.bean.RecommendItem;
import com.jack.biz_homepage.intf.IHomePagePresenter;
import com.jack.biz_homepage.intf.IHomePageView;
import com.jack.biz_homepage.utils.DatabaseConvertor;
import com.jack.biz_homepage.utils.JsonParser;
import com.jack.lib_common.network.HttpUtil;
import com.jack.lib_common.network.IResponse;
import com.jack.lib_data.FreeAppDatabaseUtil;
import com.jack.lib_data.bean.FreeAppItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @fileName: HomePagePresenter
 * @author: susy
 * @date: 2021/5/27 17:36
 * @description:
 */
public class HomePagePresenter implements IHomePagePresenter {
    private final String TAG = "HomePagePresenter";
    private final int PAGE_COUNT = 10;
    private final int FREE_APPLICATION_LIST_COUNT = 100;
    private final String URL_GET_RECOMMEND = "https://itunes.apple.com/hk/rss/topgrossingapplications/limit=${limit}/json";
    private final String URL_GET_FREE = "https://itunes.apple.com/hk/rss/topfreeapplications/limit=${limit}/json";
    private final String URL_GET_DETAIL = "https://itunes.apple.com/hk/lookup?id=${id}";
    private IHomePageView mView;
    private int mCurrentFreeCount = 10;

    @Override
    public void loadRecommendApplicationData() {
        HttpUtil.getInstance().doGet(URL_GET_RECOMMEND.replace("${limit}", String.valueOf(PAGE_COUNT)), new IResponse() {
            @Override
            public void onResponse(String result) {
                disposeRecommendResult(result);
            }

            @Override
            public void onFail(String error) {
                Log.i(TAG, "onFail: ");
            }
        });
    }

    @Override
    public void loadFreeApplicationData() {
        HttpUtil.getInstance().doGet(URL_GET_FREE.replace("${limit}", String.valueOf(mCurrentFreeCount)), new IResponse() {
            @Override
            public void onResponse(String result) {
                disposeFreeResult(result);
            }

            @Override
            public void onFail(String error) {
                Log.i(TAG, "onFail: ");
            }
        });
    }

    @Override
    public void loadDetailById(String id) {
        HttpUtil.getInstance().doGet(URL_GET_DETAIL.replace("${id}", id), new IResponse() {
            @Override
            public void onResponse(String result) {
                disposeDetailResult(result);
            }

            @Override
            public void onFail(String error) {
                Log.i(TAG, "onFail: ");
            }
        });
    }

    @Override
    public void searchLocalApplicationData(String keyword) {
        if (TextUtils.isEmpty(keyword)){
            mView.onShowSearchResult(false);
        }else {
            mView.onShowSearchResult(true);
            mView.onUpdateSearchResult(null);
            Observable.create(new ObservableOnSubscribe<List<FreeAppItem>>() {
                @Override
                public void subscribe(@NotNull ObservableEmitter<List<FreeAppItem>> emitter) throws Exception {
                    Log.i(TAG, "subscribe: disposeSearchTextChange");
                    List<FreeAppItem> lsSearch = FreeAppDatabaseUtil.queryKeyword(keyword);
                    emitter.onNext(lsSearch);
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<FreeAppItem>>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {
                            Log.i(TAG, "onSubscribe: disposeSearchTextChange");
                        }

                        @Override
                        public void onNext(@NotNull List<FreeAppItem> freeItems) {
                            Log.i(TAG, "onNext: disposeSearchTextChange");
                            List<FreeItem> lsResult = DatabaseConvertor.freeAppItems2FreeItems(freeItems);
                            if (lsResult != null && lsResult.size() > 0) {
                                mView.onUpdateSearchResult(lsResult);
                            }else {
                                mView.onError("未搜索到相关内容");
                            }
                        }

                        @Override
                        public void onError(@NotNull Throwable e) {
                            Log.i(TAG, "onError: disposeSearchTextChange");
                        }

                        @Override
                        public void onComplete() {
                            Log.i(TAG, "onComplete: disposeSearchTextChange");
                        }
                    });

        }
    }

    @Override
    public void nextPage() {
        if (mCurrentFreeCount >= FREE_APPLICATION_LIST_COUNT) {
            return;
        }
        mCurrentFreeCount += PAGE_COUNT;
        loadFreeApplicationData();
    }

    public void init(Context context, IHomePageView view){
        HttpUtil.getInstance().init(context);
        mView = view;
    }

    public void disposeRecommendResult(String json){
        Observable.create(new ObservableOnSubscribe<List<RecommendItem>>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<List<RecommendItem>> emitter) throws Exception {
                Log.i(TAG, "subscribe: disposeRecommendResult");
                emitter.onNext(JsonParser.parseRecommendData(json));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<RecommendItem>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                        Log.i(TAG, "onSubscribe: disposeRecommendResult");
                    }

                    @Override
                    public void onNext(@NotNull List<RecommendItem> recommendItems) {
                        Log.i(TAG, "onNext: disposeRecommendResult");
                        mView.onUpdateRecommendApplicationData(recommendItems);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Log.i(TAG, "onError: disposeRecommendResult");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: disposeRecommendResult");
                    }
                });
    }

    public void disposeFreeResult(String json){
        Observable.create(new ObservableOnSubscribe<List<FreeItem>>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<List<FreeItem>> emitter) throws Exception {
                Log.i(TAG, "subscribe: disposeFreeResult");
                emitter.onNext(JsonParser.parseFreeData(json));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<FreeItem>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                        Log.i(TAG, "onSubscribe: disposeFreeResult");
                    }

                    @Override
                    public void onNext(@NotNull List<FreeItem> freeItems) {
                        Log.i(TAG, "onNext: disposeFreeResult");
                        List<FreeItem> newList = freeItems.subList(freeItems.size() - 10, freeItems.size());
                        mView.onUpdateFreeApplicationData(newList);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Log.i(TAG, "onError: disposeFreeResult");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: disposeFreeResult");
                    }
                });
    }

    public void disposeDetailResult(String json){
        Observable.create(new ObservableOnSubscribe<DetailItem>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<DetailItem> emitter) throws Exception {
                Log.i(TAG, "subscribe: disposeDetailResult");
                emitter.onNext(JsonParser.parseDetail(json));
            }
            })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DetailItem>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                        Log.i(TAG, "onSubscribe: disposeDetailResult");
                    }

                    @Override
                    public void onNext(@NotNull DetailItem detailItem) {
                        Log.i(TAG, "onNext: disposeDetailResult");
                        mView.onUpdateFreeApplicationDetail(detailItem);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Log.i(TAG, "onError: disposeDetailResult");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: disposeDetailResult");
                    }
                });
    }

    public void addData(List<FreeItem> lsItems){
        if (lsItems != null){
            FreeAppDatabaseUtil.addMultiple(DatabaseConvertor.freeItems2FreeAppItems(lsItems));
        }
    }

    public void startRequestData(){
        loadRecommendApplicationData();
        loadFreeApplicationData();
    }
}
