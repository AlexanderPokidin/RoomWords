package com.pokidin.a.roomwords;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewWordActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY_WORD = "extra_replay_word";
    public static final String EXTRA_REPLY_EXAMPLE = "extra_replay_example";
    public static final String EXTRA_REPLY_TRANSLATE = "extra_replay_translate";
    public static final String EXTRA_REPLY_ID = "extra_replay_id";

    private EditText mEditWordView;
    private EditText mEditTranslateView;
    private EditText mEditExampleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        mEditWordView = findViewById(R.id.edit_word);
        mEditExampleView = findViewById(R.id.edit_example);
        mEditTranslateView = findViewById(R.id.edit_translate);

        final Bundle extras = getIntent().getExtras();

        // If we are passed content, fill it in for the user to edit. Otherwise, start with empty fields.
        if (extras != null) {
            String word = extras.getString(MainActivity.EXTRA_DATA_UPDATE_WORD, "");
            if (!word.isEmpty()) {
                mEditWordView.setText(word);
                mEditWordView.setSelection(word.length());
                mEditWordView.requestFocus();

                String example = extras.getString(MainActivity.EXTRA_DATA_UPDATE_EXAMPLE, "");
                if (!example.isEmpty()) {
                    mEditExampleView.setText(example);
                }

                String translate = extras.getString(MainActivity.EXTRA_DATA_UPDATE_TRANSLATE, "");
                if (!translate.isEmpty()) {
                    mEditTranslateView.setText(translate);
                }
            }
        }

        final Button button = findViewById(R.id.button_save);

        // When the user presses the Save button, create a new Intent for the reply.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditWordView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                    Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
                } else {
                    String word = mEditWordView.getText().toString();
                    String example = mEditExampleView.getText().toString();
                    String translate = mEditTranslateView.getText().toString();

                    replyIntent.putExtra(EXTRA_REPLY_WORD, word);
                    replyIntent.putExtra(EXTRA_REPLY_EXAMPLE, example);
                    replyIntent.putExtra(EXTRA_REPLY_TRANSLATE, translate);
                    if (extras != null && extras.containsKey(MainActivity.EXTRA_DATA_ID)) {
                        int id = extras.getInt(MainActivity.EXTRA_DATA_ID, -1);
                        if (id != -1) {
                            replyIntent.putExtra(EXTRA_REPLY_ID, id);
                        }
                    }
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
