package simulator;



public class Position {
    double x;
    double y;

    


public double getX(){

    return x;
}

public double getY(){

    return y;
}

public void moveTo(double x1, double y1){
    x = x1;
    y = y1;
}
public void display()
    {System.out.println("("+x+","+y+")");
    }


public void translateX(double x1){

    x =  x + x1;
}
public void translateY(double y1){

    y =  y + y1;
}

public void projX(){
    y = 0;

}
public void projY(){
    x = 0;

}

public Position() {
    x = -1;
    y = -1;
}
public Position(double x0,double y0){
    x = x0;
    y = y0;
}

}



