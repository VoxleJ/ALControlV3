package com.example.luke.alcontrolv2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class UserState extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private Button ourButton;
    private Button sessionButton;
    private double weight;
    private int gender;
    private double ABV;
    private TextView tv;
    private TextView warning;
    //private String theFile;
    private int hasRun=1;
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_state);

        setTitle("ALControl");

        Resources res = getResources();
        Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.circle, null);
        final ProgressBar mProgress = findViewById(R.id.progressbar);
        mProgress.setProgress(0);   // Main Progress
        mProgress.setSecondaryProgress(100);
        mProgress.setProgressDrawable(drawable);
        prefs = getSharedPreferences("com.example.luke.alcontrolv2", MODE_PRIVATE);
        tv = findViewById(R.id.drinkPrint);
        warning = findViewById(R.id.textView2);
        tv.setText(0 + "%");

        //theFile = this.getFilesDir().getPath();

/*
        try {
            FileOutputStream fOut = openFileOutput("file.txt",Context.MODE_PRIVATE);
            fOut.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Doesn't exist");
        } catch (IOException ex) {
            System.err.println("Uh Oh");
        }
*/
        Intent intent = getIntent();
        String message = intent.getStringExtra("mymessage");
        String array1[] = message.split("/");
        String genderStr = array1[0];
        String weightStr = array1[1];
        String abvStr = array1[2];
        gender = Integer.parseInt(genderStr);
        weight = Double.parseDouble(weightStr);
        double theDrink = Double.parseDouble(abvStr);
        if (prefs.getBoolean("firstrun",true)) {
            hasRun=0;
        }
        JSetUser(hasRun, gender, weight);
        hasRun=1;
        if (theDrink==1) {
            JSetDrink(hasRun, 4.5,350);
        }
        if (theDrink==2) {
            JSetDrink (hasRun, 12.0,150.0);
        }
        if (theDrink==3) {
            JSetDrink (hasRun, 40.0,50.0);
        }

        ourButton = findViewById(R.id.button2);
        ourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JAddDrink(hasRun);
                double printBAC = Double.parseDouble(JCalcMyBAC(1));
                double progDouble = Math.floor((printBAC*1000)/3.0);
                String thecur = Double.toString(progDouble);
                String theint;
                theint = thecur.substring(0,(thecur.length()-2));
                int progInt = Integer.parseInt(theint);
                if (progInt<=105) {
                    mProgress.setProgress(progInt);
                }
                @SuppressLint("DefaultLocale")
                String curBAC = String.format(Locale.getDefault(), "%.3f", printBAC);
                if (printBAC>0.02 && printBAC<0.039) {
                    warning.setText(getResources().getString(R.string.warning1));
                }
                if (printBAC>0.04 && printBAC<0.059) {
                    warning.setText(getResources().getString(R.string.warning2));
                }
                if (printBAC>0.06 && printBAC<0.099) {
                    warning.setText(getResources().getString(R.string.warning3));
                }
                if (printBAC>0.1 && printBAC<0.129) {
                    warning.setText(getResources().getString(R.string.warning4));
                }
                if (printBAC>0.130 && printBAC<0.159) {
                    warning.setText(getResources().getString(R.string.warning5));
                }
                if (printBAC>0.160 && printBAC<0.199) {
                    warning.setText(getResources().getString(R.string.warning6));
                }
                if (printBAC>0.2 && printBAC<0.249) {
                    warning.setText(getResources().getString(R.string.warning7));
                }
                if (printBAC>0.250 && printBAC<0.399) {
                    warning.setText(getResources().getString(R.string.warning8));
                }
                if (printBAC>0.4) {
                    warning.setText(getResources().getString(R.string.warning9));
                }
                tv.setText(curBAC + "%");
            }
        });

        sessionButton = findViewById(R.id.endSession);
        sessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JNewSession(1);
                tv.setText("0");
                mProgress.setProgress(0);
                openPickDrink();
            }
        });







/*
        try {
            FileInputStream fin = openFileInput("file.txt");
            fin.close();
//            TextView myView = findViewById(R.id.drinkPrint);
//            myView.setText(temp);
        } catch (FileNotFoundException ex) {
            System.err.println("Doesn't exist");
        } catch (IOException ex) {
            System.err.println("Uh Oh");
        }
*/

        /*
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JAddDrink();
                textView.setText(JCalcMyBAC());
            }
        });
        //TextView myView = findViewById(R.id.drinkPrint);
        //myView.setText(JSetUser(gender,weight));
        //String finMessage = message;
        //myView.setText(finMessage);
        */
    }

    @SuppressLint("ApplySharedPref")
    @Override
    protected void onResume() {
        super.onResume();
        if (prefs.getBoolean("firstrun",true)) {
            prefs.edit().putBoolean("firstrun",false).commit();
        }
    }

    public void openPickDrink() {
        super.onBackPressed();
    }
    private native String JSetUser(int hasRun, int genderDat, double weightDat);
    private native String JSetDrink(int hasRun, double MyABV, double MyVol);
    private native String JAddDrink(int hasRun);
    private native String JCalcMyBAC(int hasRun);
    private native String JNewSession(int hasRun);
}