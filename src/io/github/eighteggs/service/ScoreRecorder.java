package io.github.eighteggs.service;

import java.io.*;
import java.util.Arrays;


public class ScoreRecorder {
    private static final String SCORE_FILE = "resources/score.txt";
    private static int[] scores = new int[3]; // top 3 scores

    private ScoreRecorder() {
        throw new IllegalStateException("Utility class");
    }

    public static void initScoreRecorder() {
        File f = new File(SCORE_FILE);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        // try-with-resources (since Java 7)
        try (FileInputStream fis = new FileInputStream(f);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {
            final String line = br.readLine();
            if (line == null) {
                Arrays.fill(scores, 0);
            } else {
                String[] vs = line.split("\\|");
                for (int i = 0; i < vs.length; i++) {
                    scores[i] = Integer.parseInt(vs[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int[] getScores() {
        return scores;
    }

    public static void saveScore() {
        final String value = Arrays.toString(scores).replaceAll("\\[|\\]", "").replaceAll(", ", "|") + '\n';
        try (FileOutputStream fos = new FileOutputStream(SCORE_FILE);
             OutputStreamWriter osw = new OutputStreamWriter(fos);
             BufferedWriter bw = new BufferedWriter(osw)) {
            bw.write(value);
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addScore(int score) {
        int[] tmp = Arrays.copyOf(scores, scores.length + 1);
        tmp[tmp.length - 1] = score;
        Arrays.sort(tmp);
        scores = Arrays.copyOfRange(tmp, tmp.length - scores.length, tmp.length);
    }
}


