package io.github.eighteggs.entity;

import io.github.eighteggs.service.FreshThread;
import io.github.eighteggs.service.Sound;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Dinosaur entity
 *
 * @variable image: the current dinosaur image
 * @variable x, y: x, y coordinate of the dinosaur
 * @variable image1, image2, image3: dinosaur image
 * @variable isJumping: whether the dinosaur is jumping
 * @variable jumpValue: jump value
 * @variable stepTimer: step timer
 * @constant FRESH: fresh time
 * @constant JUMP_HEIGHT: jump height
 * @constant LOWEST_Y: lowest y
 */
public final class Dinosaur {
    private static final int FRESH = FreshThread.FRESH;
    private static final int JUMP_HEIGHT = 100;
    private static final int LOWEST_Y = 120;
    public BufferedImage image;
    public int x, y;
    private BufferedImage image1, image2, image3;
    private boolean isJumping;
    private int jumpValue = 0;
    private int stepTimer = 0;

    public Dinosaur() {
        x = 50;
        y = LOWEST_Y;
        try {
            image1 = ImageIO.read(new File("resources/dinosaur1.png"));
            image2 = ImageIO.read(new File("resources/dinosaur2.png"));
            image3 = ImageIO.read(new File("resources/dinosaur3.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void step() {
        switch (stepTimer / 250 % 3) {
            case 0 -> image = image1;
            case 1 -> image = image2;
            case 2 -> image = image3;
            default -> throw new IllegalStateException("Unexpected value: " + stepTimer / 250 % 3);
        }
    }

    public void jump() {
        if (!isJumping) {
            Sound.play();
        }
        isJumping = true;
    }

    public void move() {
        step();
        if (isJumping) {
            if (y >= LOWEST_Y) jumpValue = -4;
            if (y <= LOWEST_Y - JUMP_HEIGHT) jumpValue = 4;
            y += jumpValue;
            if (y >= LOWEST_Y) {
                y = LOWEST_Y;
                isJumping = false;
            }
        }
    }

    public Rectangle getHeadBounds() {
        return new Rectangle(x + 10, y, 30, 20);
    }

    public Rectangle getFootBounds() {
        return new Rectangle(x, y + 20, 50, 30);
    }
}
