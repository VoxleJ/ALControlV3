package com.example.luke.alcontrolv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class pickDrink extends AppCompatActivity {
    private Button selectButton;
    private Button selectButton2;
    private Button selectButton3;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_drink);

        setTitle("ALControl");

        // Get the transferred data from source activity.
        Intent intent = getIntent();
        message = intent.getStringExtra("message");
        //TextView textView = findViewById(R.id.SelectDrink);
        //textView.setText(message);

        selectButton = findViewById(R.id.SelectDrink);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrinkTab(3);
            }
        });

        selectButton2 = findViewById(R.id.button);
        selectButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrinkTab(1);
            }
        });

        selectButton3 = findViewById(R.id.button3);
        selectButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrinkTab(2);
            }
        });
    }
    public void openDrinkTab(int drinkchoice) {
        Intent intent = new Intent(this, UserState.class);
        message = message + "/" + drinkchoice + "d";
        intent.putExtra("mymessage", message);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int thesize = message.length();
        String tempmess = message;
        if (message.charAt(thesize-1)=='d') {
            message = tempmess.substring(0,thesize-3);
        }
    }
}
