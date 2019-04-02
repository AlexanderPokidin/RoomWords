package com.pokidin.a.roomwords.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey(autoGenerate = true)
    private int mId;

    @NonNull
    @ColumnInfo(name = "word")
    String mWord;

    public Word(@NonNull String word) {
        mWord = word;
    }

    @Ignore
    public Word(int id, @NonNull String word) {
        mId = id;
        mWord = word;
    }

    public String getWord() {
        return mWord;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
