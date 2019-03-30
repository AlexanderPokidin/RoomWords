package com.pokidin.a.roomwordssample.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.pokidin.a.roomwordssample.entity.Word;
import com.pokidin.a.roomwordssample.repo.WordRepository;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;
    private LiveData<List<Word>> mAllWords;

    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void insert(Word word) {
        mRepository.insert(word);
    }
}
