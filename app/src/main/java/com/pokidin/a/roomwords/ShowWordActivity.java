package com.pokidin.a.roomwords;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowWordActivity extends AppCompatActivity {

    private TextView mShowWordView;
    private TextView mShowTranslateView;
    private TextView mShowExampleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_word);
        mShowWordView = findViewById(R.id.show_word);
        mShowExampleView = findViewById(R.id.show_example);
        mShowTranslateView = findViewById(R.id.show_translate);
        int id = -1;

        final Bundle extras = getIntent().getExtras();

        // If we are passed content, fill in the fields. Otherwise, start with empty fields.
        if (extras != null) {
            String word = extras.getString(MainActivity.EXTRA_DATA_UPDATE_WORD, "");
            if (!word.isEmpty()) {
                mShowWordView.setText(word);

                String example = extras.getString(MainActivity.EXTRA_DATA_UPDATE_EXAMPLE, "");
                if (!example.isEmpty()) {
                    mShowExampleView.setText(example);
                    mShowExampleView.setVisibility(View.INVISIBLE);
                }

                String translate = extras.getString(MainActivity.EXTRA_DATA_UPDATE_TRANSLATE, "");
                if (!translate.isEmpty()) {
                    mShowTranslateView.setText(translate);
                    mShowTranslateView.setVisibility(View.INVISIBLE);
                }
            }
        }

        final Button button = findViewById(R.id.button_show);

        // At first, the example and translation are invisible. When the user clicks the Show button, make them visible.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShowExampleView.setVisibility(View.VISIBLE);
                mShowTranslateView.setVisibility(View.VISIBLE);
            }
        });
    }

}
