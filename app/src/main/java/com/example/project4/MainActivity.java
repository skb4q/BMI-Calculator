package com.example.project4;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends AppCompatActivity
{
    // Initialize variables for the EditText inputs and the Calculate button
    EditText weight, heightFt, heightIn;
    Button calculate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Variables receive the values from the corresponding objects in the application
        weight = (EditText)findViewById(R.id.inPounds);
        heightFt = (EditText)findViewById(R.id.inFt);
        heightIn = (EditText)findViewById(R.id.inIn);

        calculate = (Button)findViewById(R.id.btnCalculate);

        // Creates an event for when the user presses the 'Calculate' button
        calculate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Hides the virtual keyboard when the 'Calculate' button is pressed
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(heightIn.getWindowToken(), 0);

                // Goes to the function that calculates the inputted weight and height
                bmiCalculate(v);
            }
        });
    }

    // Performs all of the calculations and displays the output for the user's BMI
    public void bmiCalculate(View view)
    {
        // Initialize string and boolean variables to determine if the inputs for the weight and
        //  height are ONLY numerical values
        String strWeight = weight.getText().toString();
        String strFt = heightFt.getText().toString();
        String strIn = heightIn.getText().toString();

        boolean weightCheck = TextUtils.isDigitsOnly(weight.getText());
        boolean heightFtCheck = TextUtils.isDigitsOnly(heightFt.getText());
        boolean heightInCheck = TextUtils.isDigitsOnly(heightIn.getText());

        // Calculates if all of the
        if (!strWeight.matches("") && !strFt.matches("") && !strIn.matches("") &&
                weightCheck && heightFtCheck && heightInCheck) {
            TextView BMILevel = (TextView) findViewById(R.id.txtBMI);
            TextView BMIStatus = (TextView) findViewById(R.id.txtBMIStatus);

            // Initializes the string input of the weight and height in English measurements as floats
            float weightLbs = Float.parseFloat(strWeight);
            float heightInch = (Float.parseFloat(strFt) * 12) + Float.parseFloat(strIn);

            // Calculates the BMI by multiplying the inputted weight in pounds by 703 before dividing
            //  by the square of the inputted height in inches
            float BMI = ((weightLbs * 703) / (heightInch * heightInch));

            // Calls a function to return a string for the BMI status to explain the user's weight status
            // in more layman terms
            String bmiStatus = interpretBMI(BMI);

            // Edits the TextView objects in the application to display the numerical BMI and the
            //  status of said BMI in layman's terms
            BMILevel.setText("BMI: " + BMI);
            BMIStatus.setText("Status: " + bmiStatus);
        }
    }

    // Returns as string of the status of the person's BMI based on their BMI level
    private String interpretBMI(float bmiValue)
    {
        if (bmiValue < 18.5)
        {
            return "Underweight";
        }
        else if (bmiValue < 25)
        {
            return "Normal";
        }
        else if (bmiValue < 30)
        {
            return "Overweight";
        }
        else
            return "Obese";
    }
}
