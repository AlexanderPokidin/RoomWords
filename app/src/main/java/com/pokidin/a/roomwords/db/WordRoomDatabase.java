package com.pokidin.a.roomwords.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.pokidin.a.roomwords.dao.WordDao;
import com.pokidin.a.roomwords.entity.Word;

@Database(entities = {Word.class}, version = 3, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    public abstract WordDao wordDao();

    private static WordRoomDatabase INSTANCE;

    public static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create Database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            // Wipes and rebuilds instead of migrating if no Migration object
                            .fallbackToDestructiveMigration()
                            .addCallback(sCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private final WordDao mWordDao;
        String[] words = {
//                "dolphin", "crocodile", "cobra", "ostrich"
        };

        PopulateDbAsyncTask(WordRoomDatabase db) {
            mWordDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... voids) {

            // If DB has no words, then create the initial list of words
            if (mWordDao.getAnyWord().length < 1) {
                for (int i = 0; i < words.length; i++) {
                    Word word = new Word(words[i]);
                    mWordDao.insert(word);
                }
            }
            return null;
        }
    }
}
