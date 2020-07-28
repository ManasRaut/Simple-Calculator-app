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

    // variables to store main info like expression and result
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

    public void operatorClick(View v) {
        String element;
        String previous = "";
        int prevIndex = -1;

        if (gotResult) {
            gotResult = false;
            expArray.clear();
            if ((int) resultOncalc % resultOncalc == 0) {
                expArray.add(Integer.toString((int) resultOncalc));
            }
            else {
                expArray.add(Double.toString(resultOncalc));
            }
            updateDisplay();
        }
        resultDisplay.setText("=0");

        Button b = (Button) v;
        tag = b.getText().toString();
        element = Operations.decideButton(tag);

        if (expArray.size() > 0) {
            prevIndex = expArray.size() - 1;
            previous = expArray.get(prevIndex);

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

    public void equalClick(View v) throws Exception {

        String input = Operations.createString(expArray);
        String output = "";
        int test = 0;

        if (input != "") {
            try {
                Expression expression = new ExpressionBuilder(input).build();
                resultOncalc = expression.evaluate();
                output ="=" + Double.toString(resultOncalc);
                test = (int) resultOncalc;
                if (resultOncalc % test == 0 || resultOncalc  % test == 0.0 ) {
                    output ="=" + Integer.toString(test);
                }
                gotResult = true;
            } catch (Exception e) {
                gotResult = false;
                output = "Error";
            }
        }
        else { gotResult = false;}

        resultDisplay.setText(output);
    }

    public void clearClick(View v) {
        expArray.clear();
        resultDisplay.setText("=0");
        updateDisplay();
        gotResult = false;
    }

    public void deleteClick(View v) {
        if (expArray.size() > 0) {
            expArray.remove(expArray.size()-1);
            updateDisplay();
        }
    }

    public void updateDisplay() {
        String exp;
        exp = Operations.createString(expArray);
        if (exp == "") {
            exp = "0";
        }
        expDisplay.setText(String.valueOf(exp));
    }
}