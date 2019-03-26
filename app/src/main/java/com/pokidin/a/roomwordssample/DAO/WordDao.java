package com.pokidin.a.roomwordssample.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.pokidin.a.roomwordssample.Entity.Word;

import java.util.List;

@Dao
public interface WordDao {

    @Insert
    void insert(Word word);

    @Query("DELETE from word_table")
    void deleteAll();

    @Query("SELECT * from word_table ORDER BY word ASC")
    LiveData<List<Word>> getAllWords();

}
