package com.michaelyoon.macrocalcfinal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {

    TextView dietPlanView;
    TextView carbCount;
    TextView proteinCount;
    TextView fatCount;
    EditText targetCaloriesET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI(findViewById(R.id.parent));
        carbCount = (TextView) findViewById(R.id.carbCount);
        proteinCount = (TextView) findViewById(R.id.proteinCount);
        fatCount = (TextView) findViewById(R.id.fatCount);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //--------------PRIVATE CLASS FUNCTIONS---------------
    //====================================================
    private int calculateCarbs(int calories, int ratio){
        double percentRatio = ((double)ratio)/100;
        double temp = percentRatio * calories;
        return (int) (temp/4);
    }
    private int calculateProtein(int calories, int ratio){
        double percentRatio = ((double)ratio)/100;
        double temp = percentRatio * calories;
        return (int) (temp/4);
    }
    private int calculateFat(int calories, int ratio){
        double percentRatio = ((double)ratio)/100;
        double temp = percentRatio * calories;
        return (int) (temp/9);
    }
    private void invalidEntryAlert(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                
        // Set alert title 
        builder.setTitle("Invalid Entry");
        
        // Set the value for the positive reaction from the user
        // You can also set a listener to call when it is pressed
        builder.setPositiveButton("OK", null);
        
        // The message
        builder.setMessage(message);
        
        // Create the alert dialog and display it
        AlertDialog theAlertDialog = builder.create();
        theAlertDialog.show();
    }
    private void closeKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(targetCaloriesET.getWindowToken(), 0);
    }
    //====================================================


    //--------------PUBLIC CLASS FUNCTIONS---------------
    //====================================================
    public void highCarbPlan(View view){
        targetCaloriesET = (EditText) findViewById(R.id.targetCalories);
        String targetCaloriesString = targetCaloriesET.getText().toString();
        if(targetCaloriesString.length() == 0){
            invalidEntryAlert("Please enter a Target Calories amount.");
        }else{
            int targetCalories;
            try{
                targetCalories = Integer.parseInt(targetCaloriesET.getText().toString());
            }catch(Exception e){
                invalidEntryAlert("Please enter a valid Target Calories amount. (1-9000)");
                return;
            }
            if(targetCalories > 0 && targetCalories <= 9000){ 
                dietPlanView = (TextView) findViewById(R.id.dietPlanTV);
                dietPlanView.setText("High Carb Plan");

                carbCount.setText( String.valueOf(calculateCarbs(targetCalories, 60)));
                proteinCount.setText( String.valueOf(calculateProtein(targetCalories, 25)));
                fatCount.setText( String.valueOf(calculateFat(targetCalories, 15)));
            } else {
                invalidEntryAlert("Please enter a valid Target Calories amount. (1-9000)");
            }
        }
        closeKeyboard();
    }
    public void moderatePlan(View view){
        targetCaloriesET = (EditText) findViewById(R.id.targetCalories);
        String targetCaloriesString = targetCaloriesET.getText().toString();
        if(targetCaloriesString.length() == 0){
            invalidEntryAlert("Please enter a Target Calories amount.");
        }else{
            int targetCalories;
            try{
                targetCalories = Integer.parseInt(targetCaloriesET.getText().toString());
            }catch(Exception e){
                invalidEntryAlert("Please enter a valid Target Calories amount. (1-9000)");
                return;
            }
            if(targetCalories > 0 && targetCalories <= 9000){ 
                dietPlanView = (TextView) findViewById(R.id.dietPlanTV);
                dietPlanView.setText("Moderate Plan");

                carbCount.setText( String.valueOf(calculateCarbs(targetCalories, 50)));
                proteinCount.setText( String.valueOf(calculateProtein(targetCalories, 30)));
                fatCount.setText( String.valueOf(calculateFat(targetCalories, 20)));
            } else {
                invalidEntryAlert("Please enter a valid Target Calories amount. (1-9000)");
            }
        }
        closeKeyboard();
    }
    public void zonePlan(View view){
        targetCaloriesET = (EditText) findViewById(R.id.targetCalories);
        String targetCaloriesString = targetCaloriesET.getText().toString();
        if(targetCaloriesString.length() == 0){
            invalidEntryAlert("Please enter a Target Calories amount.");
        }else{
            int targetCalories;
            try{
                targetCalories = Integer.parseInt(targetCaloriesET.getText().toString());
            }catch(Exception e){
                invalidEntryAlert("Please enter a valid Target Calories amount. (1-9000)");
                return;
            }
            if(targetCalories > 0 && targetCalories <= 9000){ 
                dietPlanView = (TextView) findViewById(R.id.dietPlanTV);
                dietPlanView.setText("Zone Plan");

                carbCount.setText( String.valueOf(calculateCarbs(targetCalories, 40)));
                proteinCount.setText( String.valueOf(calculateProtein(targetCalories, 40)));
                fatCount.setText( String.valueOf(calculateFat(targetCalories, 20)));
            } else {
                invalidEntryAlert("Please enter a valid Target Calories amount. (1-9000)");
            }
        }
        closeKeyboard();
    }
    public void lowCarbPlan(View view){
        targetCaloriesET = (EditText) findViewById(R.id.targetCalories);
        String targetCaloriesString = targetCaloriesET.getText().toString();
        if(targetCaloriesString.length() == 0){
            invalidEntryAlert("Please enter a Target Calories amount.");
        }else{
            int targetCalories;
            try{
                targetCalories = Integer.parseInt(targetCaloriesET.getText().toString());
            }catch(Exception e){
                invalidEntryAlert("Please enter a valid Target Calories amount. (1-9000)");
                return;
            }
            if(targetCalories > 0 && targetCalories <= 9000){ 
                dietPlanView = (TextView) findViewById(R.id.dietPlanTV);
                dietPlanView.setText("Low Carb Plan");

                carbCount.setText( String.valueOf(calculateCarbs(targetCalories, 25)));
                proteinCount.setText( String.valueOf(calculateProtein(targetCalories, 45)));
                fatCount.setText( String.valueOf(calculateFat(targetCalories, 30)));
            } else {
                invalidEntryAlert("Please enter a valid Target Calories amount. (1-9000)");
            }
        }
        closeKeyboard();
    }
    //====================================================

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {

    //Set up touch listener for non-text box views to hide keyboard.
    if(!(view instanceof EditText)) {

        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard(MainActivity.this);
                return false;
            }

        });
    }

    //If a layout container, iterate over children and seed recursion.
    if (view instanceof ViewGroup) {

        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

            View innerView = ((ViewGroup) view).getChildAt(i);

            setupUI(innerView);
        }
    }
}
}
