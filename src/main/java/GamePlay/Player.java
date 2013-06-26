package GamePlay;


import java.awt.*;

public class Player extends FlyingObject implements Renderable {
    private boolean alive = true;
    private String name;

    public Player(Vec position) {
        super(position);
        //this.name = name;
        speed = 5;
    }

    public void moveOn(Vec v) {
        if (alive) {
            super.moveOn(v);
            goThrough();
        }
    }

    public void destroy() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public void render(Graphics2D g) {
        if (alive) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.GRAY);
        }
        g.fillOval((int) position.getX(), (int) position.getY(), size, size);
    }


    public String getName() {
        return name;
    }
}
