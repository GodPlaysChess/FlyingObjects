package GamePlay;

import Gen.Screen;

import java.awt.*;

public class FlyingObject implements Renderable {
    protected Vec position;
    protected int size;
    protected double speed;
    protected Vec direction;


    public FlyingObject(Vec position) {
        this.position = position;
        this.size = 20;
    }

    public void moveUp() {
        position.add(0, -speed);
    }

    public void moveLeft() {
        position.add(-speed, 0);
    }

    public void moveDown() {
        position.add(0, speed);
    }

    public void moveRight() {
        position.add(speed, 0);
    }

    public void moveOn(Vec v) {
        position.add(v.multiplied(speed));
    }

    protected void goThrough() {
       /* update of position if go through walls */

        if (position.getX() < Screen.LEFT_BORDER + 20) {
            position.setX(Screen.RIGHT_BORDER - 20);
        }
        if (position.getX() > Screen.RIGHT_BORDER - 20) {
            position.setX(Screen.LEFT_BORDER + 20);
        }
        if (position.getY() < Screen.BOTTOM_BORDER + 20) {
            position.setY(Screen.TOP_BORDER);
        }
        if (position.getY() > Screen.TOP_BORDER) {
            position.setY(Screen.BOTTOM_BORDER + 20);
        }
    }

    public boolean intersectes(FlyingObject flyingObject) {
        return position.distance(flyingObject.position) < (size + flyingObject.getSize())/2;
    }

    public void bounce(FlyingObject flyingObject){
        if (Math.abs(position.getX() - flyingObject.position.getX()) < size +
                flyingObject.getSize()){
            direction.multiply(1,-1);
           // flyingObject.direction.multiply(1,-1);
        } else {
            direction.multiply(-1,1);
           // flyingObject.direction.multiply(-1, 1);
        }

    }

    public String toString() {
        return "X: " + position.getX() + " | Y: " + position.getY();
    }


    @Override
    public void render(Graphics2D g) {

    }

    public int getSize() {
        return size;
    }
}
