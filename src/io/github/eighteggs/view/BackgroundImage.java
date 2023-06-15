package io.github.eighteggs.view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BackgroundImage {
    public static final int SPEED = 4;
    private final BufferedImage image;
    public int x1;
    public int x2;
    private BufferedImage image1;
    private BufferedImage image2;
    private Graphics2D g2;

    public BackgroundImage() {
        try {
            image1 = ImageIO.read(new File("resources/background1.png"));
            image2 = ImageIO.read(new File("resources/background2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        image = new BufferedImage(800, 300, BufferedImage.TYPE_INT_RGB);
        g2 = image.createGraphics();
        x1 = 0;
        x2 = 800;
        g2.drawImage(image1, x1, 0, null);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void roll() {
        x1 -= SPEED;
        x2 -= SPEED;
        if (x1 <= -800) x1 = 800;
        if (x2 <= -800) x2 = 800;

        g2.drawImage(image2, x1, 0, null);
        g2.drawImage(image1, x2, 0, null);
    }
}
