package io.github.eighteggs.view;

import io.github.eighteggs.service.ScoreRecorder;
import io.github.eighteggs.service.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static io.github.eighteggs.service.ScoreRecorder.initScoreRecorder;

public class MainFrame extends JFrame {
    public MainFrame() {
        restart();
        setBounds(680, 300, 810, 260);
        setTitle("Dinosaur Game");
        //Sound.play(Sound.SoundType.BACKGROUND);
        initScoreRecorder();
        addListener();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void restart() {
        Container con = getContentPane();
        con.removeAll();
        GamePanel panel = new GamePanel();
        con.add(panel);
        con.addKeyListener(panel);
        con.setFocusable(true);
        con.requestFocus();
        con.validate();
    }

    public void addListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ScoreRecorder.saveScore();
            }
        });
    }
}
