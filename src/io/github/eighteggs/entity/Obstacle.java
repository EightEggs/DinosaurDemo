package io.github.eighteggs.entity;

import io.github.eighteggs.view.BackgroundImage;

import javax.imageio.ImageIO;
import java.awt.*;
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
public final class Obstacle {
    public int x, y;
    public BufferedImage image;
    public BufferedImage stone;
    public BufferedImage cacti;
    private final int speed;

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
        y = 200 - image.getHeight();
        speed = BackgroundImage.SPEED;
    }

    public void move() {
        x -= speed;
    }

    public boolean isLive() {
        return x > -image.getWidth();
    }

    public Rectangle getBounds() {
        return image == stone ?
                new Rectangle(x + 5, y + 4, 23, 21) :
                new Rectangle(x + 7, y, 15, image.getHeight());
    }
}
