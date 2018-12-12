package com.example.luke.alcontrolv2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class Activity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private SeekBar weightSlider;
    private TextView weightVal;
    private TextView temporTex;
    private String textGender;
    private Button subButton;
    private String progString;
    private String mymessage;

    int progress_value;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        setTitle("ALControl");

        mybar();

        Spinner spinner = findViewById(R.id.gender_list);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //mymessage = "j";

        subButton = findViewById(R.id.submitButton);
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPickDrink();
            }
        });
    }

    public void openPickDrink() {
        mymessage = textGender + "/" + progString;
        Intent intent = new Intent(this, pickDrink.class);
        intent.putExtra("message", mymessage);
        startActivity(intent);
    }

    public void mybar() {
        weightSlider = findViewById(R.id.weightRange);
        weightVal = findViewById(R.id.weightText);
        weightVal.setText(weightSlider.getProgress() + " lbs");
        weightSlider.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_value = progress;
                        weightVal.setText(progress + " lbs");
                       // Toast.makeText(Activity2.this,"SeekBar in progress",Toast.LENGTH_LONG).show();
                        String myprog = Integer.toString(progress_value);
                        mypass(myprog);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                       // Toast.makeText(Activity2.this,"SeekBar in progress",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        weightVal.setText(progress_value + " lbs");
                       // Toast.makeText(Activity2.this,"SeekBar in progress",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        textGender = parent.getItemAtPosition(position).toString();
        if (Objects.equals(String.valueOf(textGender.charAt(0)), "M")) {
            textGender = "1";
        } else {
            textGender = "0";
        }
        //Toast.makeText(parent.getContext(), textGender, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void mypass(String myprog) {
        progString = myprog;
//        temporTex = findViewById(R.id.tempText);
//        temporTex.setText(textGender + myprog);
    }

/*    @Override
    protected void onResume() {
        super.onResume();
        int thesize = mymessage.length();
        String tempmess = mymessage;
        mymessage = tempmess.substring(0,1);
    }
    */
}