package com.example.interviewapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class Toolbar extends RelativeLayout implements OnTabChangeListener {

  private final Context context;
  private Button btnLeft;
  private Button btnRight;
  private TextView tvContacts;

  public Toolbar(Context context) {
    super(context);
    this.context = context;
    initViews();
  }

  public Toolbar(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    LayoutInflater.from(context).inflate(R.layout.toolbar, this, true);
    initViews();
  }

  public Toolbar(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    this.context = context;
    initViews();
  }

  private void initViews() {
    btnLeft = findViewById(R.id.btnLeft);
    btnRight = findViewById(R.id.btnRight);
    tvContacts = findViewById(R.id.tvContacts);
  }


  public void setLeftButtonOnClickListener(OnClickListener l) {
    btnLeft.setOnClickListener(l);
  }

  public void setRightButtonOnClickListener(OnClickListener l) {
    btnRight.setOnClickListener(l);
  }

  public void setRightButtonDrawable(int resid) {
    btnRight.setBackgroundResource(resid);
  }

  public void setLeftButtonDrawable(int resid) {
    btnLeft.setBackgroundResource(resid);
  }


  @Override
  public void onTabChanged(String tabId) {
  }
}