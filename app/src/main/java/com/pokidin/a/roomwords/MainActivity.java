package com.pokidin.a.roomwords;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.pokidin.a.roomwords.entity.Word;
import com.pokidin.a.roomwords.viewmodel.WordViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA_DATA_UPDATE_WORD = "extra_data_update_word";
    public static final String EXTRA_DATA_ID = "extra_data_id";
    public static final String EXTRA_DATA_UPDATE_EXAMPLE = "extra_data_update_example";
    public static final String EXTRA_DATA_UPDATE_TRANSLATE = "extra_data_update_translate";

    private WordViewModel mWordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WordListAdapter adapter = new WordListAdapter(this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> words) {
                adapter.setWords(words);
            }
        });

        // Add the functionality to swipe items in the RecyclerView to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Word myWord = adapter.getWordAtPosition(position);
                switch (direction) {
                    case ItemTouchHelper.RIGHT:
                        Toast.makeText(MainActivity.this, "Deleting " + myWord.getWord(), Toast.LENGTH_LONG).show();

                        // Delete the word
                        mWordViewModel.deleteWord(myWord);
                        break;
                    case ItemTouchHelper.LEFT:
                        // Launch word editing activity.
                        launchUpdateWordActivity(myWord);
                        mWordViewModel.updateWord(myWord);
                        break;
                }
            }
        });
        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new WordListAdapter.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Word word = adapter.getWordAtPosition(position);
                launchShowWordActivity(word);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY_WORD),
                    data.getStringExtra(NewWordActivity.EXTRA_REPLY_EXAMPLE),
                    data.getStringExtra(NewWordActivity.EXTRA_REPLY_TRANSLATE));
            // Save the new data to whe DB.
            mWordViewModel.insert(word);
        } else if (requestCode == UPDATE_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String wordData = data.getStringExtra(NewWordActivity.EXTRA_REPLY_WORD);
            String exampleData = data.getStringExtra(NewWordActivity.EXTRA_REPLY_EXAMPLE);
            String translateData = data.getStringExtra(NewWordActivity.EXTRA_REPLY_TRANSLATE);
            int id = data.getIntExtra(NewWordActivity.EXTRA_REPLY_ID, -1);

            if (id != -1) {
                // Update the data to whe DB.
                mWordViewModel.updateWord(new Word(id, wordData, translateData, exampleData));
            } else {
                Toast.makeText(this, R.string.unable_to_update, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, "Clearing the data...", Toast.LENGTH_SHORT).show();

            // Delete the existing data
            mWordViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void launchUpdateWordActivity(Word word) {
        Intent intent = new Intent(this, NewWordActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_WORD, word.getWord());
        intent.putExtra(EXTRA_DATA_UPDATE_EXAMPLE, word.getExample());
        intent.putExtra(EXTRA_DATA_UPDATE_TRANSLATE, word.getTranslate());
        intent.putExtra(EXTRA_DATA_ID, word.getId());
        startActivityForResult(intent, UPDATE_WORD_ACTIVITY_REQUEST_CODE);
    }

    private void launchShowWordActivity(Word word) {
        Intent intent = new Intent(this, ShowWordActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_WORD, word.getWord());
        intent.putExtra(EXTRA_DATA_UPDATE_EXAMPLE, word.getExample());
        intent.putExtra(EXTRA_DATA_UPDATE_TRANSLATE, word.getTranslate());
        intent.putExtra(EXTRA_DATA_ID, word.getId());
        startActivityForResult(intent, UPDATE_WORD_ACTIVITY_REQUEST_CODE);
    }
}
