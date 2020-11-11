package com.salton123.fundation.db.dao;

import com.salton123.fundation.bean.CodeStocksInnerJoinInfo;
import com.salton123.fundation.bean.SearchHistoryInfo;
import com.salton123.fundation.poller.chicang.FundStock;
import com.salton123.fundation.poller.chicang.FundStockExt;
import com.salton123.fundation.poller.daima.DaiMaData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * User: newSalton@outlook.com
 * Date: 2019/11/24 12:27
 * ModifyTime: 12:27
 * Description:
 */
@Dao
public interface FundDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllFundStocks(List<FundStock> bean);

    @Query("DELETE FROM `FundStock`")
    void deleteAllFundStocks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<DaiMaData> bean);

    @Query("SELECT * FROM DaiMaData LIMIT :startIndex,:limit")
    Observable<List<DaiMaData>> getData(int startIndex, int limit);

    @Query("SELECT * FROM DaiMaData")
    Observable<List<DaiMaData>> getAllData();

    @Query("SELECT COUNT(*) from DaiMaData")
    Observable<Long> getCount();

    @Query("SELECT FCODE,SHORTNAME,GPJC,GPDM FROM DaiMaData JOIN FundStock ON FCODE = CODE WHERE GPJC = :stocksKeyWord or GPDM = :stocksKeyWord")
    Observable<List<CodeStocksInnerJoinInfo>> searchFundHoldingStocks(String stocksKeyWord);

    @Query("SELECT * FROM FundStock where CODE = :keyword")
    Observable<List<FundStock>> searchFundHoldings(String keyword);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Maybe<Long> insertSearchHistory(SearchHistoryInfo bean);

    @Query("SELECT * FROM SearchHistoryInfo ORDER BY `timeStamp` DESC limit :limit")
    Observable<List<SearchHistoryInfo>> searchSearchHistoryInfos(int limit);

    @Query("SELECT *,COUNT(CODE) cut FROM FundStock group by GPDM order by COUNT(FundStock.GPDM) desc")
    Observable<List<FundStockExt>> getPopularFund();

}
