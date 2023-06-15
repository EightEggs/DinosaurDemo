package io.github.eighteggs.service;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;

public class MusicPlayer implements Runnable {
    private final File soundFile;
    private Thread thread;
    private boolean isCircular;

    public MusicPlayer(String fileName, boolean isCircular) throws FileNotFoundException {
        this.isCircular = isCircular;
        soundFile = new File(fileName);
        if (!soundFile.exists()) {
            throw new FileNotFoundException("File not found: " + fileName);
        }
    }

    @Override
    public void run() {
        // audio buffer size: 128K
        byte[] auBuffer = new byte[1024 * 128];
        do {
            SourceDataLine auLine = null;
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                AudioFormat audioFormat = audioInputStream.getFormat();
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                auLine = (SourceDataLine) AudioSystem.getLine(info);
                auLine.open(audioFormat);
                auLine.start();
                int byteCount;
                while ((byteCount = audioInputStream.read(auBuffer, 0, auBuffer.length)) != -1) {
                    if(thread.isInterrupted()) {
                        isCircular = false;
                        break;
                    }
                    auLine.write(auBuffer, 0, byteCount);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (auLine != null) {
                    auLine.drain();
                    auLine.close();
                }
            }
        } while (isCircular);
    }

    public void play() {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }
}
