package com.example.interviewapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.interviewapplication.R;
import com.example.interviewapplication.models.ContactModel;

import java.util.ArrayList;

public class ContactsAdapter extends BaseAdapter {

  Context context;
  ArrayList<ContactModel> arrayList;

  public ContactsAdapter(Context context, ArrayList<ContactModel> arrayList) {
    this.context = context;
    this.arrayList = arrayList;
  }

  @Override
  public int getCount() {
    return arrayList.size();
  }

  @Override
  public Object getItem(int position) {
    return arrayList.get(position);
  }

  @Override
  public long getItemId(int i) {
    return i;
  }

  @Override
  public View getView(final int i, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(context).inflate(R.layout.contact_list, parent, false);
    }

    TextView contactName;

    contactName = convertView.findViewById(R.id.tvContact);
    contactName.setText(arrayList.get(i).getFirstName() + " " + arrayList.get(i).getLastName());
    return convertView;
  }


}