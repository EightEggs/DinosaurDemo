package io.github.eighteggs.service;

import java.io.FileNotFoundException;

public class Sound {
    public static final String _DIR = "resources/";
    public static final String _BACKGROUND = _DIR + "background.wav";
    public static final String _JUMP = _DIR + "jump.wav";
    public static final String _HIT = _DIR + "hit.wav";

    public enum SoundType {
        BACKGROUND, JUMP, HIT
    }
    public static void play(SoundType soundType) {
        try {
            switch (soundType) {
                case BACKGROUND -> new MusicPlayer(_BACKGROUND, true).play();
                case JUMP -> new MusicPlayer(_JUMP, false).play();
                case HIT -> new MusicPlayer(_HIT, false).play();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
