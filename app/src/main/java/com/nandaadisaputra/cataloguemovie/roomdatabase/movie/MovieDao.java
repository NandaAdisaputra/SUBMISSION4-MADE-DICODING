package com.nandaadisaputra.cataloguemovie.roomdatabase.movie;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nandaadisaputra.cataloguemovie.model.movie.ResultsMovie;

import java.util.List;

@Dao
public interface MovieDao {


    @Query("SELECT * FROM tb_movie")
    List<ResultsMovie> getFavoriteNoCursor();

    @Query("SELECT * FROM tb_movie WHERE id = :id")
    ResultsMovie selectItemNoCursor(String id);


    @Query("SELECT * FROM tb_movie WHERE id = :id")
    Cursor selectItem(long id);

    @Query("SELECT * FROM tb_movie")
    Cursor getFavoriteMovie();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ResultsMovie movie);

    @Query("DELETE FROM tb_movie WHERE  id = :id")
    int deleteById(long id);

}
