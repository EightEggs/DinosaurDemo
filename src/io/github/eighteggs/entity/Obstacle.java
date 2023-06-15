package io.github.eighteggs.entity;

import io.github.eighteggs.view.BackgroundImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

/**
 * Obstacle entity
 *
 * @variable x, y: x, y coordinate of the obstacle
 * @variable image: the current obstacle image
 * @variable stone, cacti: obstacle image
 * @variable speed: obstacle moving speed
 */
public class Obstacle {
    public int x, y;
    public BufferedImage image;
    public BufferedImage stone;
    public BufferedImage cacti;
    private int speed;

    public Obstacle() {
        try {
            stone = ImageIO.read(new File("resources/stone.png"));
            cacti = ImageIO.read(new File("resources/cacti.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        if (random.nextInt(2) == 0) {
            image = stone;
        } else {
            image = cacti;
        }
        x = 800;
        y = 200-image.getHeight();
        speed = BackgroundImage.SPEED;
    }

    public void move() {
        x -= speed;
    }
}
