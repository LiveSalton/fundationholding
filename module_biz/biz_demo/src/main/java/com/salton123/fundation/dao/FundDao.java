package com.salton123.fundation.dao;

import com.salton123.fundation.bean.CodeStocksInnerJoinInfo;
import com.salton123.fundation.chicang.FundStock;
import com.salton123.fundation.daima.DaiMaData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
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

    @Query("SELECT COUNT(*) from DaiMaData")
    Observable<Long> getCount();

    @Query("SELECT FCODE,SHORTNAME,GPJC,GPDM FROM DaiMaData JOIN FundStock ON FCODE = CODE WHERE GPJC = :stocksKeyWord or GPDM = :stocksKeyWord")
    Observable<List<CodeStocksInnerJoinInfo>> searchFundHoldingStocks(String stocksKeyWord);

    //查询哪些基金持有中兴通讯
    // SELECT DaiMaData.FCODE,DaiMaData.SHORTNAME,FundStock.GPJC FROM com.salton123.fundation.daima.DaiMaData JOIN FundStock ON  DaiMaData.FCODE = FundStock.CODE WHERE FundStock.GPJC = "中兴通讯"

    // @Query("DELETE FROM User")
    // void deleteAll();
}
