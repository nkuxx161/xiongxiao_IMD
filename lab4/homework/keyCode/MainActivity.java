package com.example.lab4_homework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int count = 0;
    private TextView text;
    public static final String EXTRA_MESSAGE = "com.example.lab4_homework.extra.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.count_text);
    }

    public void countUp(View view) {
        count++;
        text.setText(Integer.toString(count));
    }

    public void sayHello(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        String msg = Integer.toString(count);
        intent.putExtra(EXTRA_MESSAGE, msg);
        startActivity(intent);
    }
}