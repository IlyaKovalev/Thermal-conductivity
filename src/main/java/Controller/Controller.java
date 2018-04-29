package Controller;

import geometry.Point;
import geometry.Rib;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Collections;


public class Controller {

    @FXML Pane pane;
    @FXML TextField Criterion;
    TextField textField;
    double Temperature;

    Point p1;
    Point p2;
    Point start;
    Rib rib;

    Line line = new Line();

    ArrayList<Rib>ribs = new ArrayList<>();
    ArrayList<Double>Area = new ArrayList<>();
    Point [][]Array;
    Point[][]Array2;


    public void initialize(){
        pane.setOnMouseClicked(event -> {
        if (p1==null){
            p1 = new Point(event.getX(),event.getY());
            start = p1;
            line.setStartX(p1.getX());
            line.setStartY(p1.getY());
            pane.getChildren().add(p1);
        }else{
            p2 = new Point(event.getX(),event.getY());
            line.setEndX(p2.getX());
            line.setEndY(p2.getY());
            pane.getChildren().addAll(p2,line);
        }
            textField = new TextField();
            textField.setLayoutX(event.getX());
            textField.setLayoutY(event.getY()-7);
            pane.getChildren().add(textField);

            if (p2!=null && p2.getX()<start.getX()+10 && p2.getX()>start.getX()-10 && p2.getY()<start.getY()+10 && p2.getY()>start.getY()-10){
                p2.setX(start.getX());
                p2.setY(start.getY());
                pane.getChildren().removeAll(textField,p2,line);
                line.setEndX(start.getX());
                line.setEndY(start.getY());
                pane.getChildren().addAll(p2,line);
                rib = new Rib(p1,p2);
                ribs.add(rib);
            }

        });
        pane.setOnKeyPressed(event -> {
            if (event.getCode()==KeyCode.ESCAPE){Calculate();}
            if (event.getCode()== KeyCode.ENTER && textField!=null){
                Temperature =  Double.valueOf(textField.getText());
               if (p2==null){
                   p1.setT(Temperature);
                   start.setT(Temperature);
               } else{
                   p2.setT(Temperature);
                   rib = new Rib(p1,p2);
                   ribs.add(rib);
                   line = new Line();
                   p1 = p2;
                   p2 = null;
                   line.setStartX(p1.getX());
                   line.setStartY(p1.getY());
               }
                pane.getChildren().remove(textField);
                textField = null;
            }
        });
    }

    public boolean check(double x,double y){

        double intersectionX;
        ArrayList<Double>Points = new ArrayList<>();

        for (Rib r :ribs){

            intersectionX = (y-r.getB())/r.getK();
            if ((intersectionX<=r.maxX() && intersectionX>=r.minX() && y>=r.minY() && y<=r.maxY())) Points.add(intersectionX);

        }
        if (Points.size()==2){
            if(x>Points.get(0) && x<Points.get(1) ||(x<Points.get(0) && x>Points.get(1))){
                return true;
            }
        }
        if (Points.size()==1){
            return false;
        }
        if (Points.size()==0){}
        if (Points.size()==3){
            double a= Math.round(Points.get(0));
            double b = Math.round(Points.get(1));
            double c = Math.round(Points.get(2));
            if((Math.round(a)==Math.round(b) || Math.round(a)==Math.round(c) || Math.round(b)==Math.round(c))
                    && (( (x>a && x<b && x<c) || (x<a && x<b && x>c) || (x<a && x>b && x<c)) ||( (x>a && x<b && x>c)
                    || (x<a && x>b && x>c) || (x>a && x>b && x<c)) ) ){
                  return true;
            }
        }
        return false;
    }

    public void createField(){
        double minX=10000;
        double minY=10000;
        double maxX=0;
        double maxY=0;

        for (Rib r :ribs){
            if (maxX<r.maxX())maxX=r.maxX();
            if (minX>r.minX())minX=r.minX();
            if (minY>r.minY())minY=r.minY();
            if (maxY<r.maxY())maxY=r.maxY();
        }
        Area.add(minX);
        Area.add(minY);
        Area.add(maxX);
        Area.add(maxY);
        Array = new Point[(int)(maxY-minY)/2+1][(int)(maxX-minX)/2+1];
    }


    public void Calculate(){
        createField();
        int axeX=0;
        int axeY=0;
        double k=Double.parseDouble(Criterion.getText());

        for (double i =Area.get(1);i<Area.get(3);i+=2){
            for (double j=Area.get(0);j<Area.get(2);j+=2){
                if (check(j,i)){
                    Point p = new Point(j,i);
                    p.setRadius(1);
                    p.setFill(Color.BLUE);
                    pane.getChildren().add(p);
                    Array[axeY][axeX]=p;
                    axeX++;
                }else{
                    Array[axeY][axeX]=null;
                    axeX++;
                }
            }
            axeX=0;
            axeY++;
        }

        for (int i=0;i<Array.length;i++){
            if (Array[i][0]!=null){Array[i][0].setEdge(true);}
            if (Array[i][Array[i].length-1]!=null){Array[i][Array[i].length-1].setEdge(true);}
            for (int j=1;j<Array[0].length;j++){
                if (Array[i][j-1]==null && Array[i][j]!=null){Array[i][j].setEdge(true);}
                if (Array[i][j-1]!=null && Array[i][j]==null){Array[i][j-1].setEdge(true);}
            }
        }

        for (int i=0;i<Array[0].length;i++){
            if (Array[0][i]!=null){Array[0][i].setEdge(true);}
            if (Array[Array.length-1][i]!=null){Array[Array.length-1][i].setEdge(true);}
            for (int j=1;j<Array.length;j++){
                if (Array[j-1][i]==null && Array[j][i]!=null){Array[j][i].setEdge(true);}
                if (Array[j-1][i]!=null && Array[j][i]==null){Array[j-1][i].setEdge(true);}
            }
        }

        for (int i=0;i<Array.length;i++){
            for (int j=0;j<Array[0].length;j++){
                if (Array[i][j]!=null){
                    if (Array[i][j].isEdge()){
                    setTemperature(Array[i][j]);
                   if(maxT<Array[i][j].getT()){maxT=Array[i][j].getT();}
                   if(minT>Array[i][j].getT()){minT=Array[i][j].getT();}
                    }
                }
            }
        }
        Array2=copy(Array);

        do {
            differ=0;
            for (int i=1;i<Array.length-1;i++){
                for (int j=1;j<Array[0].length-1;j++){
                    F(i,j);
                    if (Array[i][j]!=null){
                        if (differ <Math.abs(Array2[i][j].getT()-Array[i][j].getT())){
                            differ = Math.abs(Array2[i][j].getT()-Array[i][j].getT());
                        }
                    }
                }
            }
            Array=copy(Array2);
        }while (!(differ<k));
        setFill();
    }


    public void setFill(){
        if (Math.abs(maxT)<Math.abs(minT))maxT=Math.abs(minT);
        for (int i=0;i<Array.length;i++){
            for (int j =0;j<Array[0].length;j++){
                if (Array[i][j]!=null){
                    pane.getChildren().remove(Array[i][j]);
                    Array[i][j].setFill(Color.color((Math.abs(Array[i][j].getT()/maxT)),0,0));
                    pane.getChildren().add(Array[i][j]);
                }
            }
        }
    }

    public void setTemperature(Point point){
        double ShortestDistance=100000;
        Rib toRib = null;
        double X=0;
        double Y=0;
        for (Rib r:ribs){
            double distance = (r.getK()*point.getX()-point.getY()+r.getB())/Math.sqrt(Math.pow(r.getK(),2)+1);
            if (ShortestDistance>Math.abs(distance)){
                toRib=r;
                double k1 = toRib.getK();
                double b1=toRib.getB();
                double x = (point.getY()+point.getX()/k1-b1)/(k1+1/k1);
                 double y = k1*x+b1;
                if ((x>toRib.minX() && x<toRib.maxX() && y>toRib.minY() && y<toRib.maxY())){
                    ShortestDistance=Math.abs(distance);
                    X=x;
                    Y=y;
                }

            }
        }
        point.setT(toRib.TemperatureFunc(X,Y));
    }

    double maxT=-10000;
    double minT=10000;
    double differ=0;
    public void F(int i,int j){
        double Temp;
        if (Array[i][j]!=null && Array[i+1][j]!=null && Array[i-1][j]!=null && Array[i][j+1]!=null &&Array[i][j-1]!=null) {
            Temp = Array[i][j].getT() + 1 * ((Array[i + 1][j].getT() - 2* Array[i][j].getT() + Array[i - 1][j].getT()) / 4 +
                    (Array[i][j + 1].getT() - 2*Array[i][j].getT() + Array[i][j - 1].getT()) / 4);
            Array2[i][j].setT(Temp);
            if (maxT<Temp){
                maxT=Temp;
                System.out.println("maxT "+maxT);
            }
            if (minT>Temp){
                minT=Temp;
                System.out.println("minT "+minT);
            }
        }
    }
    public Point[][] copy(Point[][] points){

        Point[][] p = new Point[points.length][points[0].length];
        for (int i=0;i<p.length;i++){
            for (int j=0;j<p[0].length;j++){
                if (points[i][j]!=null){
                    p[i][j]=new Point(points[i][j].getX(),points[i][j].getY());
                    p[i][j].setT(points[i][j].getT());
                    p[i][j].setEdge(points[i][j].isEdge());
                    p[i][j].setRadius(points[i][j].getRadius());
                }
            }
        }
        return p;
    }

    public void clear(){
        p1=null;
        p2=null;
        start=null;
        rib=null;
        differ = 0;
         line = new Line();
        pane.getChildren().clear();
        ribs = new ArrayList<>();
        Area = new ArrayList<>();
        Array=null;
        Array2=null;
        maxT=-10000;
        minT=10000;
    }

}