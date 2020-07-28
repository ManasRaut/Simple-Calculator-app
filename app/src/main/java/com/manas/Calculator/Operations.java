package com.manas.Calculator;

import android.widget.Switch;

import java.util.ArrayList;

public class Operations {

    // returns a string by join all elements of given ArrayList<>
    public static String createString(ArrayList<String> expression) {
        int i;
        String answer = "";

        for (i=0; i<expression.size(); i++) {
            String element;
            element = expression.get(i);
            answer += element;
        }
        return answer;
    }

    // decides the operator to be added in Arraylist<> when an operator button is clicked
    public static String decideButton(String tag) {
        String button = "";

        switch (tag) {
            case "+":
                button = "+";
                break;
            case "-":
                button = "-";
                break;
            case "x":
                button = "*";
                break;
            case "/":
                button = "/";
                break;
            case "(":
                button = "(";
                break;
            case ")" :
                button = ")";
                break;
            default:
                button = "";
                break;
        }
        return button;
    }

}
