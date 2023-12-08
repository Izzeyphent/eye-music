package com.zappers.eye.music.sound;

import static com.zappers.eye.music.utils.MusicManager.getNoteType;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

    private final Clip sound;
    private Timer timer;

    public Sound(String type, String note, int length) {
        try {
            URL resource = Sound.class.getResource(getFileName(type, note, length));
            var stream = new BufferedInputStream(resource.openStream());
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(stream);
            this.sound = AudioSystem.getClip();
            this.sound.open(audioInputStream);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            throw new UnsupportedOperationException("Unable to load file " + ex.getMessage());
        }
    }

    public void play() {
        try {
            if (this.sound.isOpen()) {
                if (this.sound.isRunning()) {
                    this.sound.stop();
                    this.sound.flush();
                    this.sound.setFramePosition(0);
                }
                this.sound.open();
            }
        } catch (LineUnavailableException ex) {
            System.out.println("Ahh, failed to play" + ex.getMessage());
        }
    }

    public void playRepeating(int time) {

        if (timer != null) {
            timer.cancel();
        }
        this.timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                play();
            }

        };

        timer.scheduleAtFixedRate(task, 0, time);
    }

    public static String getFileName(String type, String note, int length) {
        return new StringBuilder()
                .append('/')
                .append(type)
                .append('/')
                .append(note)
                .append('-')
                .append(getNoteType(length))
                .append(".wav")
                .toString();
    }
}
