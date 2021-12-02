package model;

import java.util.ArrayList;
import java.util.function.BiFunction;

public class Operations {

    public ArrayList<BiFunction<Double, Double, Double>> op;
    public ArrayList<String> opSymbol;

    public Operations(ArrayList<BiFunction<Double, Double, Double>> op, ArrayList<String> opSymbol) {
        this.op = op;
        this.opSymbol = opSymbol;
    }

}
