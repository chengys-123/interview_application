package com.example.interviewapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class DetailsToolbar extends RelativeLayout implements OnTabChangeListener {

  private final Context context;
  private TextView txtLeft;
  private TextView txtRight;
  private TextView tvContacts;

  public DetailsToolbar(Context context) {
    super(context);
    this.context = context;
    initViews();
  }

  public DetailsToolbar(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    LayoutInflater.from(context).inflate(R.layout.details_toolbar, this, true);
    initViews();
  }

  public DetailsToolbar(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    this.context = context;
    initViews();
  }

  private void initViews() {
    txtLeft = findViewById(R.id.textLeft);
    txtRight = findViewById(R.id.textRight);
    tvContacts = findViewById(R.id.tvContacts);
  }


  public void setLeftButtonOnClickListener(OnClickListener l) {
    txtLeft.setOnClickListener(l);
  }

  public void setRightButtonOnClickListener(OnClickListener l) {
    txtRight.setOnClickListener(l);
  }


  @Override
  public void onTabChanged(String tabId) {
  }
}