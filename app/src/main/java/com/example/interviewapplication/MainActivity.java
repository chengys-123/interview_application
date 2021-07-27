package com.example.interviewapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button btn;
    btn = findViewById(R.id.btn);
    btn.setOnClickListener(view -> Toast.makeText(getApplicationContext(), "Hello World!", Toast.LENGTH_SHORT).show());
  }
}