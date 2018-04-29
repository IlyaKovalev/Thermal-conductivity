package geometry;

public class Rib {

    Point p1;
    Point p2;

    double k,b ;  //   y = kx+b

    double TempFunc;
    double length;

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public Rib(Point p1, Point p2){

        this.p1=p1;
        this.p2=p2;

        this.k = (p1.getY()-p2.getY())/(p1.getX()-p2.getX());
        this.b = p1.getY()-k*p1.getX();
        this.length=Math.sqrt(Math.pow(p2.getX()-p1.getX(),2)+Math.pow(p2.getY()-p1.getY(),2));
        this.TempFunc = (p2.getT()-p1.getT())/this.length;

    }


    public double maxX(){
        if (p1.getX()>p2.getX())
        {
            return p1.getX();
        }else{
            return p2.getX();
        }
    }
    public double minX(){
        if (p1.getX()<p2.getX())
        {
            return p1.getX();
        }else{
            return p2.getX();
        }
    }
    public double maxY(){
        if (p1.getY()>p2.getY())
        {
            return p1.getY();
        }else{
            return p2.getY();
        }
    }

    public double getK() {
        return k;
    }

    public double getB() {
        return b;
    }

    public double minY(){
        if (p1.getY()<p2.getY()) {
            return p1.getY();
        }else{
            return p2.getY();
        }
    }
    public double maxT(){
        if (p1.getT()>p2.getT()){
            return p1.getT();
        }else return p2.getT();
    }


    public double length(double x,double y){
        double l=Math.sqrt(Math.pow(x-p1.getX(),2)+Math.pow(y-p1.getY(),2));
        return l;
    }

    public double TemperatureFunc(double x,double y){
        return length(x,y)*TempFunc+p1.getT();
    }

}
