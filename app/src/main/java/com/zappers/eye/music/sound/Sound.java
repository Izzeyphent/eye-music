package com.zappers.eye.music.sound;

import static com.zappers.eye.music.utils.MusicManager.getNoteType;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

    private final Clip sound;
    private CompletableFuture future;
    private boolean stick = false;
    private final double frameLength;

    /**
     * Gets the file, then tries to play the sound.
     *
     * @param type Type is the type of sound.
     * @param note The note is the note it plays.
     * @param length An int value for the amount of time it plays.
     */
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
        frameLength = this.sound.getFrameLength() / (double) this.sound.getMicrosecondLength();
    }

    /**
     *
     * @param time
     */
    public void play(int time) {
        if (this.sound.isRunning()) {
            this.sound.stop();
            sound.flush();
        }

        this.sound.setFramePosition(0);
        this.sound.setLoopPoints(0, (int) Math.floor(time * 1000 * frameLength));
        this.sound.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void playRepeating(int time, boolean setStick) {
        this.stick = setStick;
        if (future != null) {
            future.cancel(true);
        }
        future = CompletableFuture.runAsync(() -> {
            play(time);
        });
    }

    public void stop() {
        if (this.sound.isRunning()) {
            this.sound.stop();
            sound.flush();
        }
        future.cancel(true);
    }

    public boolean isStuck() {
        return this.stick;
    }

    /**
     *
     * @param type Type is the type of sound.
     * @param note The note is the note it plays.
     * @param length An int value for the amount of time it plays.
     * @return Returns the note: type, note, and length .wav.
     */
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
