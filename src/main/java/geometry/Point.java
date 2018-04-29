package geometry;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Point extends Circle {

    double x;
    double y;
    double t=0;

    boolean edge ;


    public Point(double x, double y){

        this.x=x;
        this.y=y;
        //this.t=T;

        this.setLayoutX(this.x);
        this.setLayoutY(this.y);
        this.setRadius(3);
        this.setFill(Color.RED);
    }

    public void setX(double x) {
        this.x = x;
        this.setLayoutX(x);
    }

    public void setY(double y) {
        this.y = y;
        this.setLayoutY(y);
    }

    public void setEdge(boolean e){
        edge = e;
    }
    public boolean isEdge(){
        return this.edge;
    }

    public void setT(double t) {
        this.t = t;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getT() {
        return t;
    }



}
