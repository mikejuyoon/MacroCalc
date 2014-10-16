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

    private final static String SHARED_PREFERENCES_NAME = "com.michaelyoon.macrocalc.savedData";
    public static SharedPreferences savedDietData;

    TextView calcDietTypeTV;
    TextView calcCarbTV;
    TextView calcProteinTV;
    TextView calcFatTV;
    EditText inputCaloriesET;

    String tempDietType;
    int tempCalories;
    int tempCarb;
    int tempProtein;
    int tempFat;

    String targetDietType;
    int targetCalories;
    int targetCarb;
    int targetProtein;
    int targetFat;

    int currCalories;
    int currCarb;
    int currProtein;
    int currFat;

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

    boolean kbHideSetupOne;
    boolean kbHideSetupTwo;


    private static final int NUM_PAGES = 2;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;


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
        kbHideSetupOne = false;
        kbHideSetupTwo = false;
    }

    @Override
    protected void onStart(){
        super.onStart();
        if( savedDietData.getInt("targetCalories", -1) != -1){
            mPager.setCurrentItem(1);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
    }


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

        currCaloriesTV.setText(String.valueOf(currCalories));
        currCarbTV.setText(String.valueOf(currCarb));
        currProteinTV.setText(String.valueOf(currProtein));
        currFatTV.setText(String.valueOf(currFat));
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

    private void invalidEntryAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);

        builder.setTitle("Invalid Entry");
        builder.setPositiveButton("OK", null);
        builder.setMessage(message);

        AlertDialog theAlertDialog = builder.create();
        theAlertDialog.show();
    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputCaloriesET.getWindowToken(), 0);
    }
    //====================================================


    //--------------PUBLIC CLASS FUNCTIONS---------------
    //====================================================

    public void setupViewOne() {
        inputCaloriesET = (EditText) findViewById(R.id.inputCaloriesET);
        calcCarbTV = (TextView) findViewById(R.id.calcCarbTV);
        calcProteinTV = (TextView) findViewById(R.id.calcProteinTV);
        calcFatTV = (TextView) findViewById(R.id.calcFatTV);
        calcDietTypeTV = (TextView) findViewById(R.id.calcDietTypeTV);
    }    

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
    //====================================================

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void hideKeyboardViewOne(){
        if(!kbHideSetupOne){
            setupKeyboardHide(findViewById(R.id.screen1));
            kbHideSetupOne = true;
        }
    }
    public void hideKeyboardViewTwo(){
        if(!kbHideSetupTwo){
            setupKeyboardHide(findViewById(R.id.screen2));
            kbHideSetupTwo = true;
        }
    }
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

    public void sendDietType(View view) {
        //DISPLAY ERROR MESSAGE IF NOT VALID ENTRY
        if (tempDietType.isEmpty()) {
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

    public void updateCarb(View view){
        try{
            tempCarb = Integer.parseInt(addCarbET.getText().toString());
        }catch(Exception e){
            invalidEntryAlert("Input a valid value [-999,999]");
            return;
        }
        if(tempCarb < -999 || tempCarb > 999){
            invalidEntryAlert("Input a valid value [-999,999]");
            return;
        }
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
    public void updateProtein(View view){
        try{
            tempProtein = Integer.parseInt(addProteinET.getText().toString());
        }catch(Exception e){
            invalidEntryAlert("Input a valid value [-999,999]");
            return;
        }
        if(tempProtein < -999 || tempProtein > 999){
            invalidEntryAlert("Input a valid value [-999,999]");
            return;
        }
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
    public void updateFat(View view){
        try{
            tempFat = Integer.parseInt(addFatET.getText().toString());
        }catch(Exception e){
            invalidEntryAlert("Input a valid value [-999,999]");
            return;
        }
        if(tempFat < -999 || tempFat > 999){
            invalidEntryAlert("Input a valid value [-999,999]");
            return;
        }
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

