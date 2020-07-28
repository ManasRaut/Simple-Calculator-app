package com.manas.Calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


import java.security.cert.TrustAnchor;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText expDisplay;
    private EditText resultDisplay;

    private ArrayList<String> expArray = new ArrayList<>();
    private String result = "0";
    private String tag = "";

    private boolean gotResult = false;
    private double resultOncalc = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expDisplay = (EditText) findViewById(R.id.expressions_display);
        resultDisplay = (EditText) findViewById(R.id.result_display);

    }

    // on called adds number to expArray directly
    public void buttonClick(View v) {
        if (gotResult) {
            expArray.clear();
            updateDisplay();
            gotResult = false;
        }
        resultDisplay.setText("=0");
        Button b = (Button) v;
        tag = b.getText().toString();

        expArray.add(tag);

        updateDisplay();
    }

    // adds operator in expArray[] by performing neccessary checks
    public void operatorClick(View v) {
        String element;
        String previous = "";
        int prevIndex = -1;

        // checks is result is calculated
        if (gotResult) {
            gotResult = false;
            expArray.clear(); // clears Arraylist
            if ((int) resultOncalc % resultOncalc == 0) {
                expArray.add(Integer.toString((int) resultOncalc));
            }
            else {
                expArray.add(Double.toString(resultOncalc));
            }
            updateDisplay();
        }
        resultDisplay.setText("=0"); // resets resultDisplay

        Button b = (Button) v;
        tag = b.getText().toString();
        element = Operations.decideButton(tag); // gets the operator for corresponding clicked button

        if (expArray.size() > 0) {
            prevIndex = expArray.size() - 1;
            previous = expArray.get(prevIndex); // gets previous element

            // performs neccessary checks and modefies the expArray[]
            if (previous == "+" || previous == "-" || previous == "*" || previous == "/") {
                if (element == "(" || element == ")") {
                    expArray.add(element);
                }
                else {
                    expArray.remove(prevIndex);
                    expArray.add(element);
                }
            } else {
                expArray.add(element);
            }

        } else {
            expArray.add(element);
        }

         updateDisplay();
    }

    // calculates the result
    public void equalClick(View v) throws Exception {

        String input = Operations.createString(expArray); // gets String expression of the expArray[]
        String output = "";
        int test = 0;

        if (input != "") {

            // try catch block to avoid any exceptions
            try {
                Expression expression = new ExpressionBuilder(input).build();
                resultOncalc = expression.evaluate();
                output ="=" + Double.toString(resultOncalc);
                test = (int) resultOncalc;

                // if result is an integer then dislays integer else displays double
                if (resultOncalc % test == 0 || resultOncalc  % test == 0.0 ) {
                    output ="=" + Integer.toString(test);
                }
                gotResult = true;
            } catch (Exception e) {
                gotResult = false;
                output = "Error"; // catches exceptions and displays Error in resultDisplay
            }
        }
        else { gotResult = false;}

        resultDisplay.setText(output); // sets text of resultDisplay
    }

    // clears expArray[] and also clears display
    public void clearClick(View v) {
        expArray.clear();
        resultDisplay.setText("=0");
        updateDisplay();
        gotResult = false;
    }

    // removes last element of the Array[]
    public void deleteClick(View v) {
        if (expArray.size() > 0) {
            expArray.remove(expArray.size()-1);
            updateDisplay();
        }
    }

    // method used by other methods to display passed parameter as test in display
    public void updateDisplay() {
        String exp;
        exp = Operations.createString(expArray);
        if (exp == "") {
            exp = "0";
        }
        expDisplay.setText(String.valueOf(exp));
    }
}