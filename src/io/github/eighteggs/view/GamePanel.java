package io.github.eighteggs.view;

import io.github.eighteggs.entity.Dinosaur;
import io.github.eighteggs.entity.Obstacle;
import io.github.eighteggs.service.FreshThread;
import io.github.eighteggs.service.ScoreRecorder;
import io.github.eighteggs.service.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GamePanel extends JPanel implements KeyListener {
    private static final int FRESH = FreshThread.FRESH;
    private final transient BufferedImage image;
    private final transient BackgroundImage backgroundImage;
    private final transient Dinosaur dinosaur;
    private final transient Graphics2D g2;
    int score = 0;
    int scoreTimer = 0;
    private int addObstacleTimer = 0;
    private boolean gameOver = false;
    private transient List<Obstacle> obstacles = new ArrayList<>();

    public GamePanel() {
        image = new BufferedImage(800, 300, BufferedImage.TYPE_INT_RGB);
        g2 = image.createGraphics();
        backgroundImage = new BackgroundImage();
        dinosaur = new Dinosaur();
        obstacles.add(new Obstacle());
        FreshThread t = new FreshThread(this);
        t.start();
    }

    public void paintImage() {
        backgroundImage.roll();
        dinosaur.move();
        g2.drawImage(backgroundImage.getImage(), 0, 0, this);
        g2.drawImage(dinosaur.image, dinosaur.x, dinosaur.y, this);
        if (addObstacleTimer == FRESH * 60) {
            if (Math.random() > 0.4514D) {
                obstacles.add(new Obstacle());
            }
            addObstacleTimer = 0;
        }
        Iterator<Obstacle> iter = obstacles.iterator();
        while (iter.hasNext()) {
            Obstacle o = iter.next();
            if (o.isLive()) {
                o.move();
                g2.drawImage(o.image, o.x, o.y, this);
                if (o.getBounds().intersects(dinosaur.getHeadBounds()) ||
                        o.getBounds().intersects(dinosaur.getFootBounds())) {
                    Sound.play(Sound.SoundType.HIT);
                    gameOver();
                }
            } else iter.remove();
        }
        if (scoreTimer >= 500) {
            score += 10;
            scoreTimer = 0;
        }
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("Score: " + score, 620, 28);
        scoreTimer += FRESH;
        addObstacleTimer += FRESH;
    }

    @Override
    public void paint(Graphics g) {
        paintImage();
        g.drawImage(image, 0, 0, this);
    }

    public void gameOver() {
        ScoreRecorder.addScore(score);
        gameOver = true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) dinosaur.jump();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) dinosaur.setJump(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // This method is not used
    }
}
