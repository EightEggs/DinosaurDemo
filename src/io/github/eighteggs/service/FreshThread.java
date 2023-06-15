package io.github.eighteggs.service;

import io.github.eighteggs.view.GamePanel;
import io.github.eighteggs.view.MainFrame;
import io.github.eighteggs.view.ScoreDialog;

import java.awt.*;

public class FreshThread extends Thread {
    public static final int FRESH = 1000 / 60;
    GamePanel gamePanel;

    public FreshThread(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        while (!gamePanel.isGameOver()) {
            gamePanel.repaint();
            try {
                Thread.sleep(FRESH);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        Container con = gamePanel.getParent();
        while (!(con instanceof MainFrame mainFrame)) {
            con = con.getParent();
        }
        new ScoreDialog(mainFrame);
        mainFrame.restart();
    }
}
