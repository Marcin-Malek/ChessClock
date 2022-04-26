package com.mbli.chessclock;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SpinnerDialog extends Dialog {
    private Context mContext;
    private NumberPicker spinner1, spinner2, spinner3, spinner4;

    public interface DialogListener {
        void ready(int t, int i);
        void cancelled();
    }

    private DialogListener mReadyListener;

    public SpinnerDialog(Context context, DialogListener readyListener) {
        super(context, R.style.Theme_AppCompat_Light_NoActionBar);
        mReadyListener = readyListener;
        mContext = context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SpinnerDialog.this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CC000000")));

        setContentView(R.layout.spinner_dialog);
        spinner1 = (NumberPicker) findViewById(R.id.spinner1);
        spinner2 = (NumberPicker) findViewById(R.id.spinner2);
        spinner3 = (NumberPicker) findViewById(R.id.spinner3);
        spinner4 = (NumberPicker) findViewById(R.id.spinner4);
        spinner1.setMinValue(0);
        spinner1.setMaxValue(24);
        spinner2.setMinValue(0);
        spinner2.setMaxValue(60);
        spinner2.setValue(10);
        spinner3.setMinValue(0);
        spinner3.setMaxValue(60);
        spinner4.setMinValue(0);
        spinner4.setMaxValue(60);

        TextView buttonOK = (TextView) findViewById(R.id.dialogOK);
        TextView buttonCancel = (TextView) findViewById(R.id.dialogCancel);
        buttonOK.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                int hours = spinner1.getValue();
                int minutes = spinner2.getValue();
                int seconds = spinner3.getValue();
                int t = (seconds*1000) + (minutes*60000) + (hours*3600000);
                int i = (int) spinner4.getValue();
                if (t > 0) {
                mReadyListener.ready(t, i);
                SpinnerDialog.this.dismiss();
                }else{
                    Toast.makeText(mContext, "Invalid time", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonCancel.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                mReadyListener.cancelled();
                SpinnerDialog.this.dismiss();
            }
        });
    }
}
