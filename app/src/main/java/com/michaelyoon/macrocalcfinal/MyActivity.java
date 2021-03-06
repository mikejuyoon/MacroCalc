package com.michaelyoon.macrocalcfinal;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


public class MyActivity extends FragmentActivity {

    private final static String SHARED_PREFERENCES_NAME = "com.michaelyoon.macrocalcfinal.savedData";
    public static SharedPreferences savedDietData;
    private static final int NUM_PAGES = 2;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    // === Calculator Fragment Views ===
    TextView calcDietTypeTV;
    TextView calcCarbTV;
    TextView calcProteinTV;
    TextView calcFatTV;
    EditText inputCaloriesET;

    // === Temporary Variables ===
    String tempDietType;
    int tempCalories;
    int tempCarb;
    int tempProtein;
    int tempFat;

    // === Counting Fragment Variables ===
    // Target Variables
    String targetDietType;
    int targetCalories;
    int targetCarb;
    int targetProtein;
    int targetFat;
    // Current Count Variables
    int currCalories;
    int currCarb;
    int currProtein;
    int currFat;

    // === Counting Fragment Views ===
    TextView targetDietTypeTV;
    TextView targetCaloriesTV;
    TextView targetCarbTV;
    TextView targetProteinTV;
    TextView targetFatTV;
    TextView currCaloriesTV;
    TextView currCarbTV;
    TextView currProteinTV;
    TextView currFatTV;
    EditText addCarbET;
    EditText addProteinET;
    EditText addFatET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Turns off Activity Title Bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        
        savedDietData = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
    }

    @Override
    protected void onStart(){
        super.onStart();
        if( savedDietData.getInt("targetCalories", -1) != -1){
            mPager.setCurrentItem(1);
        }
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Fragment1();
                case 1:
                    return new Fragment2();
            }
            return new Fragment1();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


    //--------------PRIVATE CLASS FUNCTIONS---------------
    //====================================================
    
    private int calculateCarbs(int calories, int ratio) {
        double percentRatio = ((double) ratio) / 100;
        double temp = percentRatio * calories;
        return (int) (temp / 4);
    }

    private int calculateProtein(int calories, int ratio) {
        double percentRatio = ((double) ratio) / 100;
        double temp = percentRatio * calories;
        return (int) (temp / 4);
    }

    private int calculateFat(int calories, int ratio) {
        double percentRatio = ((double) ratio) / 100;
        double temp = percentRatio * calories;
        return (int) (temp / 9);
    }

    // Checks to see if inputed calories amount is in range (100-9000)
    // If not in range, then an error message will be displayed and return 0
    // If in range, the function returns the inputed int value
    private int inputCaloriesIsInRange(){
        String tempCaloriesString = inputCaloriesET.getText().toString();
        if (tempCaloriesString.length() == 0){
            invalidEntryAlert("Please enter a Target Calories amount.");
            return 0;
        }
        int targetCalories;
        try {
            targetCalories = Integer.parseInt(inputCaloriesET.getText().toString());
        } catch(Exception e){
            invalidEntryAlert("Please enter a valid Target Calories amount. (1-9000)");
            return 0;
        }
        if(targetCalories < 100 || targetCalories > 9000){
            invalidEntryAlert("Please enter a valid Target Calories amount. (100-9000)");
            return 0;
        }
        else
            return targetCalories;
    }

    // Alert Message
    private void invalidEntryAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);

        builder.setTitle("Invalid Entry");
        builder.setPositiveButton("OK", null);
        builder.setMessage(message);

        AlertDialog theAlertDialog = builder.create();
        theAlertDialog.show();
    }

    // Closes Keyboard
    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputCaloriesET.getWindowToken(), 0);
    }
    private static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    // Searches View recursively and sets an onTouch Listener to everything
    // that is not an EditText to hide keyboard when touched.
    private void setupKeyboardHide(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(MyActivity.this);
                    return false;
                }
            });
        }
        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupKeyboardHide(innerView);
            }
        }
    }

    // Checks to see if inputed grams of marconutrient is in range
    // range = [-999-999]. Will return 1000 if not in range
    private int updateMacroIsInRange(String inputMacro){
        int tempMacro;
        try{
            tempMacro = Integer.parseInt(inputMacro);
        }catch(Exception e){
            invalidEntryAlert("Input a valid value [-999,999]");
            return 1000;
        }
        if(tempMacro < -999 || tempMacro > 999){
            invalidEntryAlert("Input a valid value [-999,999]");
            return 1000;
        }
        return tempMacro;
    }

    //--------------PUBLIC CLASS FUNCTIONS---------------
    //====================================================

    // Gets saved data from SharedPreferences and sets values on 
    // Counting Fragment
    public void loadDataViewTwo(){
        targetDietTypeTV.setText(savedDietData.getString("targetDietType", null));
        targetCaloriesTV.setText(String.valueOf(savedDietData.getInt("targetCalories", -1)));
        targetCarbTV.setText(String.valueOf(savedDietData.getInt("targetCarb", -1)));
        targetProteinTV.setText(String.valueOf(savedDietData.getInt("targetProtein", -1)));
        targetFatTV.setText(String.valueOf(savedDietData.getInt("targetFat", -1)));

        currCalories = savedDietData.getInt("currCalories",-1);
        currCarb = savedDietData.getInt("currCarb",-1);
        currProtein = savedDietData.getInt("currProtein",-1);
        currFat = savedDietData.getInt("currFat",-1);

        if(currCalories == -1){
            resetCurrValues(findViewById(R.id.screen2));
        }else{
            currCaloriesTV.setText(String.valueOf(currCalories));
            currCarbTV.setText(String.valueOf(currCarb));
            currProteinTV.setText(String.valueOf(currProtein));
            currFatTV.setText(String.valueOf(currFat));
        }
    }

    // Setting ET & TV variables to views on Calculator Fragment
    public void setupViewOne() {
        inputCaloriesET = (EditText) findViewById(R.id.inputCaloriesET);
        calcCarbTV = (TextView) findViewById(R.id.calcCarbTV);
        calcProteinTV = (TextView) findViewById(R.id.calcProteinTV);
        calcFatTV = (TextView) findViewById(R.id.calcFatTV);
        calcDietTypeTV = (TextView) findViewById(R.id.calcDietTypeTV);
    }    

    // Setting ET & TV variables to views on Calculator Fragment
    public void setupViewTwo() {
        addCarbET = (EditText) findViewById(R.id.addCarbET);
        addProteinET = (EditText) findViewById(R.id.addProteinET);
        addFatET = (EditText) findViewById(R.id.addFatET);

        targetDietTypeTV = (TextView) findViewById(R.id.targetDietTypeTV);
        targetCaloriesTV = (TextView) findViewById(R.id.targetCaloriesTV);
        targetCarbTV = (TextView) findViewById(R.id.targetCarbTV);
        targetProteinTV = (TextView) findViewById(R.id.targetProteinTV);
        targetFatTV = (TextView) findViewById(R.id.targetFatTV);

        currCaloriesTV = (TextView) findViewById(R.id.currCaloriesTV);
        currCarbTV = (TextView) findViewById(R.id.currCarbTV);
        currProteinTV = (TextView) findViewById(R.id.currProteinTV);
        currFatTV = (TextView) findViewById(R.id.currFatTV);
    }

    // Calculates a High Carb Plan and sets respective TV's
    public void highCarbPlan(View view) {
        tempCalories = inputCaloriesIsInRange();
        if ( tempCalories != 0 ) {
            tempDietType = "High Carb Plan";
            tempCarb = calculateCarbs(tempCalories, 60);
            tempProtein = calculateProtein(tempCalories, 25);
            tempFat = calculateFat(tempCalories, 15);

            calcDietTypeTV.setText("High Carb Plan");

            calcCarbTV.setText(String.valueOf(tempCarb));
            calcProteinTV.setText(String.valueOf(tempProtein));
            calcFatTV.setText(String.valueOf(tempFat));
        }
        closeKeyboard();
    }

    // Calculates a Moderate Plan and sets respective TV's
    public void moderatePlan(View view) {
        tempCalories = inputCaloriesIsInRange();
        if ( tempCalories != 0 ) {
            tempDietType = "Moderate Plan";
            tempCarb = calculateCarbs(tempCalories, 50);
            tempProtein = calculateProtein(tempCalories, 35);
            tempFat = calculateFat(tempCalories, 20);

            calcDietTypeTV.setText("Moderate Plan");

            calcCarbTV.setText(String.valueOf(tempCarb));
            calcProteinTV.setText(String.valueOf(tempProtein));
            calcFatTV.setText(String.valueOf(tempFat));
        }
        closeKeyboard();
    }

    // Calculates a Zone Plan and sets respective TV's
    public void zonePlan(View view) {
        tempCalories = inputCaloriesIsInRange();
        if ( tempCalories != 0 ) {
            tempDietType = "Zone Plan";
            tempCarb = calculateCarbs(tempCalories, 40);
            tempProtein = calculateProtein(tempCalories, 40);
            tempFat = calculateFat(tempCalories, 20);

            calcDietTypeTV.setText("Zone Plan");

            calcCarbTV.setText(String.valueOf(tempCarb));
            calcProteinTV.setText(String.valueOf(tempProtein));
            calcFatTV.setText(String.valueOf(tempFat));
        }
        closeKeyboard();
    }

    // Calculates a Low Carb Plan and sets respective TV's
    public void lowCarbPlan(View view) {
        tempCalories = inputCaloriesIsInRange();
        if ( tempCalories != 0 ) {
            tempDietType = "Low Carb Plan";
            tempCarb = calculateCarbs(tempCalories, 25);
            tempProtein = calculateProtein(tempCalories, 45);
            tempFat = calculateFat(tempCalories, 30);

            calcDietTypeTV.setText("Low Carb Plan");

            calcCarbTV.setText(String.valueOf(tempCarb));
            calcProteinTV.setText(String.valueOf(tempProtein));
            calcFatTV.setText(String.valueOf(tempFat));
        }
        closeKeyboard();
    }

    // Calls setupKeyboardHide function by Fragment
    public void hideKeyboardViewOne(){
        setupKeyboardHide(findViewById(R.id.screen1));
    }
    public void hideKeyboardViewTwo(){
        setupKeyboardHide(findViewById(R.id.screen2));
    }

    // Saves new selected diet plan from calculator
    public void sendDietType(View view) {
        //DISPLAY ERROR MESSAGE IF NOT VALID ENTRY
        if (calcDietTypeTV.getText().toString().isEmpty()) {
            invalidEntryAlert("Please calculate a diet.");
        }else{
            mPager.setCurrentItem(1);
            //save target diet plan to shared preferences
            //calories / dietType / carbs/ protein /fat
            SharedPreferences.Editor preferencesEditor = savedDietData.edit();
            preferencesEditor.putInt("targetCalories", tempCalories);
            preferencesEditor.putString("targetDietType", tempDietType);
            preferencesEditor.putInt("targetCarb", tempCarb);
            preferencesEditor.putInt("targetProtein", tempProtein);
            preferencesEditor.putInt("targetFat", tempFat);
            preferencesEditor.apply();

            //set values for screen2
            setupViewTwo();

            targetDietTypeTV.setText(savedDietData.getString("targetDietType",null));
            targetCaloriesTV.setText(String.valueOf(savedDietData.getInt("targetCalories",-1)));
            targetCarbTV.setText(String.valueOf(savedDietData.getInt("targetCarb",-1)));
            targetProteinTV.setText(String.valueOf(savedDietData.getInt("targetProtein",-1)));
            targetFatTV.setText(String.valueOf(savedDietData.getInt("targetFat",-1)));
        }
    }

    // Updates new macro input to counted macro and calories
    public void updateCarb(View view){
        // 1000 will be invalid return value
        tempCarb = updateMacroIsInRange(addCarbET.getText().toString());
        if( tempCarb != 1000 ){
            addCarbET.setText("");
            currCarb += tempCarb;
            currCarbTV.setText(String.valueOf(currCarb));

            currCalories += tempCarb*4;
            currCaloriesTV.setText(String.valueOf(currCalories));

            SharedPreferences.Editor preferencesEditor = savedDietData.edit();
            preferencesEditor.putInt("currCarb", currCarb);
            preferencesEditor.putInt("currCalories", currCalories);
            preferencesEditor.apply();
        }
    }
    public void updateProtein(View view){
        tempProtein = updateMacroIsInRange(addProteinET.getText().toString());
        if( tempProtein != 1000 ){
            addProteinET.setText("");
            currProtein += tempProtein;
            currProteinTV.setText(String.valueOf(currProtein));

            currCalories += tempProtein*4;
            currCaloriesTV.setText(String.valueOf(currCalories));

            SharedPreferences.Editor preferencesEditor = savedDietData.edit();
            preferencesEditor.putInt("currProtein", currProtein);
            preferencesEditor.putInt("currCalories", currCalories);
            preferencesEditor.apply();
        }
    }
    public void updateFat(View view){
        tempFat = updateMacroIsInRange(addFatET.getText().toString());
        if( tempFat != 1000 ){
            addFatET.setText("");
            currFat += tempFat;
            currFatTV.setText(String.valueOf(currFat));

            currCalories += tempFat*9;
            currCaloriesTV.setText(String.valueOf(currCalories));

            SharedPreferences.Editor preferencesEditor = savedDietData.edit();
            preferencesEditor.putInt("currFat", currFat);
            preferencesEditor.putInt("currCalories", currCalories);
            preferencesEditor.apply();
        }
    }

    // Resets counted macro count.
    public void resetCurrValues(View view) {
        currCalories = 0;
        currCarb = 0;
        currProtein = 0;
        currFat = 0;

        currCaloriesTV.setText("0");
        currCarbTV.setText("0");
        currProteinTV.setText("0");
        currFatTV.setText("0");

        SharedPreferences.Editor preferencesEditor = savedDietData.edit();
        preferencesEditor.putInt("currCalories", 0);
        preferencesEditor.putInt("currCarb", 0);
        preferencesEditor.putInt("currProtein", 0);
        preferencesEditor.putInt("currFat", 0);
        preferencesEditor.apply();
    }
}

