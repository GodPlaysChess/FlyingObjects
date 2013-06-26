package GamePlay;

import Gen.Screen;

import java.awt.*;
import java.util.Random;

public class Enemy extends FlyingObject {

    Random random = new Random();

    public Enemy(Vec position, Vec direction) {
        super(position);
        this.direction = direction;
        speed = 5;
    }

    public Enemy() {
        super(Vec.getRandomVec(Screen.RIGHT_BORDER - Screen.LEFT_BORDER,
                Screen.TOP_BORDER - Screen.BOTTOM_BORDER));
        goThrough();
        direction = Vec.getRandomVec(10, 10);
        speed = random.nextInt(10);
    }

    public Enemy getEnemy() {
        return new Enemy();
    }

    public Vec getDirection() {
        return direction;
    }

    public void moveOn(Vec v) {
        super.moveOn(v);
        bounce();
    }

    protected void bounce() {
       /* bouncing out of walls behavior pattern */

        if (position.getX() < Screen.LEFT_BORDER + 20 ||
                position.getX() > Screen.RIGHT_BORDER - 20) {
            direction.multiply(-1, 1);
        }
        if (position.getY() < Screen.BOTTOM_BORDER + 20 ||
                position.getY() > Screen.TOP_BORDER) {
            direction.multiply(1, -1);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.red);
        g.fillOval((int) position.getX(), (int) position.getY(), size, size);
    }

}
