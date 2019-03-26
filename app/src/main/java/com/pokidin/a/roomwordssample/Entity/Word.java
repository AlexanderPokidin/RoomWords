package com.pokidin.a.roomwordssample.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    String mWord;

    public Word(@NonNull String word) {
        mWord = word;
    }

    public String getWord() {
        return mWord;
    }
}
