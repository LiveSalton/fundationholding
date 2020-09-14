// package com.salton123.fundation.dao;
//
// import com.salton123.fundation.daima.DaiMaData;
//
// import java.util.List;
//
// import androidx.room.Dao;
// import androidx.room.Insert;
// import androidx.room.OnConflictStrategy;
// import androidx.room.Query;
// import io.reactivex.Observable;
//
// /**
//  * User: wujinsheng1@yy.com
//  * Date: 2020/9/10 15:41
//  * ModifyTime: 15:41
//  * Description:
//  */
// @Dao
// public interface DaiMaDao {
//     @Insert(onConflict = OnConflictStrategy.REPLACE)
//     void insert(List<DaiMaData> bean);
//
//     @Query("SELECT * FROM DaiMaData LIMIT :startIndex,:limit")
//     Observable<List<DaiMaData>> getData(int startIndex, int limit);
//
//     @Query("SELECT COUNT(*) from DaiMaData")
//     Observable<Long> getCount();
// }
