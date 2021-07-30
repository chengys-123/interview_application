package com.example.interviewapplication;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.interviewapplication.adapter.ContactsAdapter;
import com.example.interviewapplication.models.ContactModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "log";
  Toolbar toolbar;
  ListView listView;
  SwipeRefreshLayout swipeRefreshLayout;
  TextView tvContacts, etSearch;

  ArrayList<ContactModel> arrayList;
  ContactsAdapter adapter;
  AdapterView.OnItemClickListener mOnItemClick = new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
      Intent i = new Intent(getApplicationContext(), DetailsActivity.class);
      i.putExtra("id", arrayList.get(position).getId());
      startActivity(i);
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    copyAssets();

    toolbar = this.findViewById(R.id.toolbar);
    listView = findViewById(R.id.contactListView);
    swipeRefreshLayout = findViewById(R.id.swiperefresh);
    tvContacts = findViewById(R.id.tvContacts);
    etSearch = findViewById(R.id.etSearch);

    this.toolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        tvContacts.setVisibility(View.GONE);
        etSearch.setVisibility(View.VISIBLE);
        toolbar.setRightButtonDrawable(R.drawable.ic_back);
        toolbar.setRightButtonOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            tvContacts.setVisibility(View.VISIBLE);
            etSearch.setVisibility(View.GONE);
            toolbar.setRightButtonDrawable(R.drawable.ic_add);
          }
        });
      }
    });

    etSearch.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    });

    new readJson().execute();
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        new readJson().execute();
      }
    });
  }

  private void copyFile(InputStream in, OutputStream out) throws IOException {
    byte[] buffer = new byte[1024];
    int read;
    while ((read = in.read(buffer)) != -1) {
      out.write(buffer, 0, read);
    }
  }

  private void copyAssets() {
    AssetManager assetManager = getAssets();
    String[] files = null;
    try {
      files = assetManager.list("");
    } catch (IOException e) {
      Log.e("tag", "Failed to get asset file list.", e);
    }
    if (files != null) for (String filename : files) {
      InputStream in = null;
      OutputStream out = null;
      try {
        in = assetManager.open(filename);
        File outFile = new File(getExternalFilesDir(null), filename);
        out = new FileOutputStream(outFile);
        copyFile(in, out);
      } catch (IOException e) {
        Log.e("tag", "Failed to copy asset file: " + filename, e);
      } finally {
        if (in != null) {
          try {
            in.close();
          } catch (IOException e) {
            // NOOP
          }
        }
        if (out != null) {
          try {
            out.close();
          } catch (IOException e) {
            // NOOP
          }
        }
      }
    }


  }

  public class readJson extends AsyncTask<String, Void, String> {
    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      swipeRefreshLayout.setRefreshing(true);
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
      swipeRefreshLayout.setRefreshing(false);

      arrayList = new ArrayList<>();
      try {
        JSONObject object = new JSONObject(s);
        JSONArray array = object.getJSONArray("data");

        for (int i = 0; i < array.length(); i++) {

          JSONObject jsonObject = array.getJSONObject(i);

          String id = jsonObject.getString("id");
          String first_name = jsonObject.getString("firstName");
          String last_name = jsonObject.getString("lastName");


          ContactModel model = new ContactModel();
          model.setId(id);
          model.setFirstName(first_name);
          model.setLastName(last_name);

          arrayList.add(model);
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }

      adapter = new ContactsAdapter(getApplicationContext(), arrayList);
      listView.setAdapter(adapter);
      listView.setOnItemClickListener(mOnItemClick);
      listView.setTextFilterEnabled(true);

    }
  }
}