package io.github.eighteggs.view;

import io.github.eighteggs.service.ScoreRecorder;

import javax.swing.*;
import java.awt.*;

public class ScoreDialog extends JDialog{
    public ScoreDialog(JFrame frame) {
        super(frame, true);
        final int[] scores = ScoreRecorder.getScores();

        JPanel panel = new JPanel();
        JLabel title = new JLabel("Score Board", SwingConstants.CENTER);
        panel.setBackground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.RED);
        panel.add(title);

        JLabel[] scoreList = new JLabel[scores.length];
        for (int i = 0; i < scores.length; i++) {
            scoreList[i] = new JLabel((i + 1) + ". " + scores[scores.length - i - 1]);
            scoreList[i].setFont(new Font("Arial", Font.BOLD, 20));
            panel.add(scoreList[i]);
        }

        JButton restartBtn = new JButton("Restart");
        restartBtn.addActionListener(e -> dispose());
        Container con = getContentPane();
        con.setLayout(new BorderLayout());
        con.add(panel, BorderLayout.CENTER);
        con.add(restartBtn, BorderLayout.SOUTH);

        setTitle("Game Over");
        int width = 200;
        int height = 200;
        int x = frame.getX() + (frame.getWidth() - width) / 2;
        int y = frame.getY() + (frame.getHeight() - height) / 2;
        setBounds(x, y, width, height);
        setVisible(true);
    }
}
