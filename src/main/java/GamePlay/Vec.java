package GamePlay;

import java.util.Random;

public class Vec {
    private double x;
    private double y;

    public Vec() {
        x = 0;
        y = 0;
    }

    public Vec(Vec v) {
        setV(v);
    }

    public Vec(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double a) {
        x = a;
    }

    public void setY(double b) {
        y = b;
    }

    public int compareTo(Vec v) {
        if (x > v.getX())
            return 1;
        else if (x < getX())
            return -1;
        else return 0;
    }

    public void setV(Vec v) {
        x = v.getX();
        y = v.getY();
    }

    public void setV(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public double distance(Vec v) {
        return Math.sqrt((v.getX() - x) * (v.getX() - x) + (v.getY() - y) * (v.getY() - y));
    }

    public double distance(Vec v1, Vec v2) {
        double s1, s2, s3;
        s1 = this.subReturn(v1).length();
        s2 = this.subReturn(v2).length();
        s3 = v2.subReturn(v1).length();

        return Math.sqrt(s2 * s2 - (s2 * s2 - s3 * s3 - s1 * s1) *
                (s2 * s2 - s3 * s3 - s1 * s1) / (4 * s3 * s3));
    }

    public void add(double a) {
        x = getX() + a;
        y = getY() + a;
    }

    public void add(double a, double b) {
        x = getX() + a;
        y = getY() + b;
    }

    public void add(Vec v) {
        x = getX() + v.getX();
        y = getY() + v.getY();
    }

    public Vec addReturn(Vec v) {
        return new Vec(getX() + v.getX(), getY() + v.getY());
    }

    public Vec addReturn(double a, double b) {
        return new Vec(getX() + a, getY() + b);
    }

    public Vec subReturn(Vec v) {
        return new Vec(getX() - v.getX(), getY() - v.getY());
    }

    public void multiply(double a) {
        x = getX() * a;
        y = getY() * a;
    }

    public Vec multiplied(double a) {
        return new Vec(getX() * a, getY() * a);
    }

    //Angle between OX and given V in Rads
    public double getAngle() {
        if (getX() > 0) return (-Math.atan(getY() / getX()));
        else return (-Math.atan(getY() / getX()) + Math.PI);
    }

    public double getAngle(Vec v1) {
        return this.addReturn(v1).getAngle();
    }

    public Vec normalize() {
        if (length() == 0) {
            return this;
        } else {
            return new Vec(getX() / length(), getY() / length());
        }
    }

    /**
     * rotation in degrees
     */
    public void turn(double a) {
        double alpha = Math.toRadians(a);
        x = getX() * Math.cos(alpha) + getY() * Math.sin(alpha);
        y = -getX() * Math.sin(alpha) + getY() * Math.cos(alpha);

    }

    boolean OutOfBounds(double borderX, double borderY, double sizeX, double sizeY) {
        if (getX() < sizeX && getY() < sizeY && getX() > borderX && getY() > borderY)
            return false;
        else return true;
    }

    public String toString() {
        return (getX() + " , " + getY());
    }

    public static boolean linesIntersect(Vec a1, Vec a2, Vec b1, Vec b2) {

        double a = (a1.getX() * b1.getY() - b1.getX() * a1.getY() - a1.getX() * b2.getY()
                + b2.getX() * a1.getY() + b1.getX() * b2.getY() - b2.getX() * b1.getY()) /
                (a1.getX() * b1.getY() - b1.getX() * a1.getY() - a1.getX() * b2.getY() -
                        a2.getX() * b1.getY() + b1.getX() * a2.getY() + b2.getX() *
                        a1.getY() + a2.getX() * b2.getY() - b2.getX() * a2.getY());
        double b = -(a1.getX() * a2.getY() - a2.getX() * a1.getY() - a1.getX()
                * b1.getY() + b1.getX() * a1.getY() + a2.getX() * b1.getY() - b1.getX()
                * a2.getY()) / (a1.getX() * b1.getY() - b1.getX() * a1.getY() - a1.getX()
                * b2.getY() - a2.getX() * b1.getY() + b1.getX() * a2.getY() + b2.getX()
                * a1.getY() + a2.getX() * b2.getY() - b2.getX() * a2.getY());

        return (a > 0 && a < 1 && b > 0 && b < 1);
    }

    public Vec[] buildParralel(Vec start, Vec end, double shift) {
        double alpha = start.getAngle(end);
        Vec[] result = new Vec[2];
        result[0] = start.addReturn(Math.cos(alpha), shift * Math.sin(alpha));
        result[1] = end.addReturn(Math.cos(alpha), shift * Math.sin(alpha));
        return result;
    }

    public static Vec findIntersection(Vec a1, Vec a2, Vec a3, Vec a4) {
        return a1; //!!!!!!!!!!!!!!
    }

    public boolean insideCircle(double x, double y, double radii) {
        return ((getX() - x) * (getX() - x) + (getY() - y) * (getY() - y) < radii * radii);
    }

    public void multiply(double a, double b) {
        x = getX() * a;
        y = getY() * b;
    }

    public static Vec getRandomVec(int maxX, int maxY) {
        Random random = new Random();
        return new Vec(random.nextInt(maxX), random.nextInt(maxY));
    }
}
