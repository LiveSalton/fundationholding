package com.salton123.soulove.common.mvvm.model;

import android.app.Application;

import com.salton123.soulove.common.bean.PlayHistoryBean;
import com.salton123.soulove.common.bean.SearchHistoryBean;
import com.salton123.soulove.common.db.TingAppDatabase;
import com.salton123.soulove.common.net.RxAdapter;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * Author: Thomas.
 * <br/>Date: 2019/7/31 17:27
 * <br/>Email: 1071931588@qq.com
 * <br/>Description:通用Model
 */
public class CommonModel extends BaseModel {

    public CommonModel(Application application) {
        super(application);
    }


    public void deleteAllSearchHistory() {
        TingAppDatabase.getInstance().searchHistoryDao().deleteAll();
    }
    public void deleteAllPlayHistory(){
        TingAppDatabase.getInstance().playHistoryDao().deleteAll();
    }

    public Observable<List<SearchHistoryBean>> listSearchHistory(boolean desc) {
        if (desc) {
            return TingAppDatabase.getInstance().searchHistoryDao().getAllRecordDesc();
        } else {
            return TingAppDatabase.getInstance().searchHistoryDao().getAllRecord();
        }
    }

    public Observable<Long> insertSearchHistory(SearchHistoryBean entity) {
        return Observable.create((ObservableOnSubscribe<Long>) emitter -> {
            try {
                long id = TingAppDatabase.getInstance().searchHistoryDao().insert(entity);
                emitter.onNext(id);
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onComplete();
        }).compose(RxAdapter.schedulersTransformer());
    }

    public Observable<List<PlayHistoryBean>> listPlayHistory(long dataId, String kind) {
        return TingAppDatabase.getInstance().playHistoryDao().listPlayHistory(dataId,kind);
    }

    public Observable<List<PlayHistoryBean>> listPlayHistoryByGroupId(String groupId, String kind) {
        return TingAppDatabase.getInstance().playHistoryDao().listPlayHistoryByGroupId(groupId, kind);
    }
    public Observable<List<PlayHistoryBean>> listDesc() {
        return TingAppDatabase.getInstance().playHistoryDao().listDesc();
    }
}
