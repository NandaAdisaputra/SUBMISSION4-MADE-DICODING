package com.nandaadisaputra.cataloguemovie.roomdatabase.tv;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nandaadisaputra.cataloguemovie.model.tv.ResultsTv;

import java.util.List;

@Dao
public interface TvDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertTv(ResultsTv resultsTv);

    @Query("DELETE FROM tb_tv WHERE id = :id")
    void deleteTv(long id);

    @Query("SELECT * FROM tb_tv ORDER BY name ASC")
    List<ResultsTv> getFavoriteTv();

    @Query("SELECT * FROM tb_tv WHERE id = :id")
    ResultsTv selectItem(String id);
}
