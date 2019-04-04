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
    private String mWord;

    @ColumnInfo(name = "translate")
    private String mTranslate;


    @ColumnInfo(name = "example")
    private String mExample;

    public Word(@NonNull String word, String translate, String example) {
        mWord = word;
        mTranslate = translate;
        mExample = example;
    }

    @Ignore
    public Word(@NonNull String word) {
        mWord = word;
    }

    @Ignore
    public Word(int id, @NonNull String word, String translate, String example) {
        mId = id;
        mWord = word;
        mTranslate = translate;
        mExample = example;
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

    public String getTranslate() {
        return mTranslate;
    }

    public String getExample() {
        return mExample;
    }
}
