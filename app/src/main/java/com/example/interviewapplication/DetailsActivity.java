package com.example.interviewapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.interviewapplication.models.ContactModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
  private static final String TAG = "log";
  DetailsToolbar toolbar;
  EditText fname, lname, etemail, etphone;
  String intentid, first_name, last_name, email, phone;
  ArrayList<ContactModel> arrayList;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_details);

    fname = findViewById(R.id.etFirstName);
    lname = findViewById(R.id.etLastName);
    etemail = findViewById(R.id.etEmail);
    etphone = findViewById(R.id.etPhone);

    toolbar = findViewById(R.id.detailstoolbar);
    toolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });

    toolbar.setRightButtonOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (fname.getText().toString().isEmpty() || lname.getText().toString().isEmpty()) {
          Toast.makeText(getApplicationContext(), "First Name and Last Name cannot be empty.", Toast.LENGTH_SHORT).show();
        } else {
          writeJSON();
          finish();
        }
      }
    });
    Intent intent = getIntent();
    intentid = intent.getStringExtra("id");

    new readJson().execute();
  }

  public void writeJSON() {
    JSONObject object = new JSONObject();
    try {
      object.put("firstName", fname.getText().toString());
      object.put("lastName", lname.getText().toString());
      object.put("email", etemail.getText().toString());
      object.put("phone", etphone.getText().toString());
    } catch (JSONException e) {
      e.printStackTrace();
    }
    System.out.println(object);
  }

  public class readJson extends AsyncTask<String, Void, String> {
    @Override
    protected void onPreExecute() {
      super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... voids) {
      String json = null;
      try {
        File JSONfile = new File(getExternalFilesDir(null).getPath(), "data.json");
        FileReader fileReader = new FileReader(JSONfile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null) {
          stringBuilder.append(line).append("\n");
          line = bufferedReader.readLine();
        }
        bufferedReader.close();

        json = stringBuilder.toString();
      } catch (IOException e) {
        e.printStackTrace();
        return json;
      }
      return json;
    }

    @Override
    protected void onPostExecute(String s) {
      super.onPostExecute(s);

      arrayList = new ArrayList<>();
      try {
        JSONObject object = new JSONObject(s);
        JSONArray array = object.getJSONArray("data");

        for (int i = 0; i < array.length(); i++) {

          JSONObject jsonObject = array.getJSONObject(i);

          String id = jsonObject.getString("id");
          if (id.equals(intentid)) {
            first_name = jsonObject.getString("firstName");
            last_name = jsonObject.getString("lastName");
            email = jsonObject.getString("email");
            phone = jsonObject.getString("phone");

            fname.setText(first_name);
            lname.setText(last_name);
            etemail.setText(email);
            etphone.setText(phone);
          }
        }
      } catch (JSONException e) {
        fname.setText(first_name);
        lname.setText(last_name);
        etemail.setText("No email address.");
        etphone.setText("No phone number.");
      }

    }
  }

}