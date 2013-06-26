package Gen;

import GamePlay.Renderable;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;

public class Screen extends JFrame {
    public static final int D_WIDTH = 1000;
    public static final int D_HEIGHT = 700;
    public static final int TOP_BORDER = D_HEIGHT - 60;
    public static final int LEFT_BORDER = 20;
    public static final int RIGHT_BORDER = D_WIDTH - 240;
    public static final int BOTTOM_BORDER = 40;
    public SortedMap<String, String> records;
    private ArrayList<String> highScores;


    public Screen() {
        super();
        setTitle("Flying Objects");
        setSize(D_WIDTH, D_HEIGHT);
        setLocation(200, 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        createBufferStrategy(2);

        highScores = new ArrayList<>();
        loadScores();
    }

    private void loadScores() {
        File file = new File("src/records");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                highScores.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void renderClock(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.BOLD, 120));
        long milliseconds = Game.gameTime();
        long minutes = milliseconds / 60000;
        long seconds = (milliseconds % 60000) / 1000;
        milliseconds = milliseconds % 1000 / 10;

        System.out.println(Game.gameTime());
        g.drawString(minutes + " : " + seconds + " : " + milliseconds, D_WIDTH / 6, D_HEIGHT / 2);
    }

    public void render(ArrayList<? extends Renderable> list, Renderable... renderable) {
        BufferStrategy bf = this.getBufferStrategy();
        Graphics2D g = null;

        try {
            g = (Graphics2D) bf.getDrawGraphics();
            g.setColor(Color.BLACK);
            g.fillRect(LEFT_BORDER, BOTTOM_BORDER, RIGHT_BORDER, TOP_BORDER);
            g.clearRect(LEFT_BORDER + 20, BOTTOM_BORDER + 20, RIGHT_BORDER - 40, TOP_BORDER - 40);
            showRecords(g);

            if (renderable.length > 0) {
                renderClock(g);
                for (Renderable object : renderable) {
                    object.render(g);
                }
                for (Renderable object : list) {
                    object.render(g);
                }
            } else {
                showGamover(g);
            }

        } finally {
            if (g != null)
                g.dispose();
        }

        bf.show();
        Toolkit.getDefaultToolkit().sync();
    }

    public void showGamover(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Arial", Font.BOLD, 90));
        g.drawString("GAME OVER", D_WIDTH / 5, D_HEIGHT / 4);
        g.drawString("your time is", D_WIDTH / 5, 2 * D_HEIGHT / 5);
        String time = Game.getGameTime();
        g.drawString(time, D_WIDTH / 5, 3 * D_HEIGHT / 5);
    }

    private void showRecords(Graphics2D g) {
        int shift = 0;
        g.setColor(Color.BLACK);
        g.setFont(new Font("MONOSPACED", Font.BOLD, 20));
        for (String recrod : highScores) {
            g.drawString(recrod, 4 * D_WIDTH / 5, D_HEIGHT / 5 + shift);
            shift += 40;
        }
    }




    private void showRecordsOLD(Graphics2D g) {
        int shift = 0;
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        for (Map.Entry<String, String> entry : records.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            g.drawString(key + "  " + value, 4 * D_WIDTH / 5, 4 * D_HEIGHT / 5 + shift);
            shift += 50;
        }
    }


}

