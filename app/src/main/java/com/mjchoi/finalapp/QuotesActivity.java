package com.mjchoi.finalapp;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.mjchoi.finalapp.model.Quote;

import java.util.ArrayList;

public class QuotesActivity extends AppCompatActivity {
    private TextView quote;
    private ImageView leftButton;
    private ImageView rightButton;
    private ArrayList<Quote> quoteList;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        quote = findViewById(R.id.quotes);
        leftButton = findViewById(R.id.arrow_left);
        rightButton = findViewById(R.id.arrow_right);

        // Insert quotes from strings resource
        Resources res = getResources();
        String[] quotes = res.getStringArray(R.array.quotes);
        String[] authors = res.getStringArray(R.array.authors);
        quoteList = new ArrayList<>();

        addQuote(quotes, authors);

        final int quoteLength = quoteList.size();
        index = getRandomQuote(quoteLength - 1);
        quote.setText(quoteList.get(index).toString());

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = getRandomQuote(quoteLength - 1);
                quote.setText(quoteList.get(index).toString());
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, quoteList.get(index).toString());
                intent.setType("text/plain");
                startActivity(intent);

            }
        });
    }

    public int getRandomQuote(int length) {
        return (int) (Math.random() * length) + 1;
    }

    public void addQuote(String[] quotes, String[] authors) {
        for (int i = 0; i < quotes.length; i++){
            String quote = quotes[i];
            String author = authors[i];
            Quote q = new Quote(quote, author);
            quoteList.add(q);
        }
    }
}

