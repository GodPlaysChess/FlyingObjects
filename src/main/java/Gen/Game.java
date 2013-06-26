package Gen;

import GamePlay.*;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Game implements KeyListener {
    private Screen screen;
    private boolean gameOn = true;
    private Player hero;
    private ArrayList<Enemy> enemies;
    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private final long GAMESPEED = 25; /* duration of one tick in milliseconds */
    private static long TIME;
    private EnemyFactory enemyFactory;


    public Game() {
        screen = new Screen();
        hero = new Player(new Vec(300, 300));
        enemies = new ArrayList<>();
        TIME = System.currentTimeMillis();

        enemies.add(new Enemy(new Vec(100, 100), new Vec(5, 5)));

        screen.addKeyListener(this);
        enemyFactory = new CircleEnemyFactory();
    }

    public static long gameTime() {
        return System.currentTimeMillis() - TIME;
    }

    private void nextTick() {
        /* movement phase */
        hero.moveOn(handleKeyboardInput().normalize());
        for (Enemy enemy : enemies) {
            enemy.moveOn(enemy.getDirection().normalize());
        }
        /* event phase */
        checkCollisions();
        if (!hero.isAlive()) {
            gameStop();
        }
        if (gameTime() % 100 < 2) {
            enemies.add(enemyFactory.getEnemy());
        }
        if (gameTime() > 10000){
            enemyFactory = new TriangleFactory();
        }
    }

    private void checkCollisions() {
        for (Enemy enemy : enemies) {
            if (hero.intersectes(enemy)) {
                hero.destroy();
            }
            for (Enemy enemy1 : enemies) {
                if (!enemy.equals(enemy1)) {
                    if (enemy.intersectes(enemy1)) {
                        enemy.bounce(enemy1);
                    }
                }
            }
        }
    }

    private Vec handleKeyboardInput() {
        Vec shiftVec = new Vec(0, 0);
        if (downPressed) {
            shiftVec.add(0, 1);
        }
        if (upPressed) {
            shiftVec.add(0, -1);
        }
        if (leftPressed) {
            shiftVec.add(-1, 0);
        }
        if (rightPressed) {
            shiftVec.add(1, 0);
        }
        return shiftVec;
    }

    public void run() {
        while (gameOn) {
            nextTick();
            screen.render(enemies, hero);
            try {
                Thread.sleep(GAMESPEED);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!gameOn) {
                screen.render(enemies);
                //String name = JOptionPane.showInputDialog("Enter your name");
                String name = "Gleb";
                saveRecord(name, getGameTime());

            }
        }
        while (!gameOn) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveRecord(String name, String gameTime) {
        try (BufferedWriter bf = new BufferedWriter(new FileWriter("src/records", true))) {
            bf.write(name + "  " + gameTime);
            bf.newLine();
            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static String getGameTime() {
        long milliseconds = gameTime();
        long minutes = milliseconds / 60000;
        long seconds = (milliseconds % 60000) / 1000;
        milliseconds = milliseconds % 1000 / 10;
        return minutes + ":" + seconds + ":" + milliseconds;
    }

    private void gameStop() {
        gameOn = false;
    }

    private void startNewGame() {
        hero = new Player(new Vec(300, 300));
        enemies = new ArrayList<>();
        enemies.add(new Enemy(new Vec(100, 100), new Vec(5, 5)));
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        gameOn = true;
    }


    @Override
    public void keyTyped(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                upPressed = true;
                break;
            case KeyEvent.VK_LEFT:
                leftPressed = true;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = true;
                break;
            case KeyEvent.VK_DOWN:
                downPressed = true;
                break;
            case KeyEvent.VK_N: {
                gameOn = true;
                break;
            }
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                upPressed = false;
                break;
            case KeyEvent.VK_LEFT:
                leftPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                break;
            case KeyEvent.VK_DOWN:
                downPressed = false;
                break;
        }
    }
}
