package com.zappers.eye.music.utils;

import com.zappers.eye.music.sound.Sound;
import static com.zappers.eye.music.utils.ImageUtils.COLOR_TO_NOTE;
import java.util.Map;
import java.util.Timer;

public class MusicManager {
    
    private static final Map<String, Sound> SOUNDS = loadSounds();
    private final int rate;
    private final Timer timer = new Timer();
    
    public MusicManager(int BPM) {
        this.rate = BPM;
    }
    
    public void play(String color, int speed) {
        var type = "piano"; // TODO add other types
        var note = COLOR_TO_NOTE.get(color);
        
        var sound = SOUNDS.get(type + '-' + note + '-' + getNoteType(speed));
        
        if (sound != null) {
            var beatsPerSecond = rate / 60d;
            var time = 1000;
            var mod = 1D;
            switch (speed) {
                case 1:
                    mod = beatsPerSecond;
                    break;
                case 2:
                    mod = beatsPerSecond/2;
                    break;
                case 3:
                    mod = beatsPerSecond/4;
                    break;
                case 4:
                    mod = beatsPerSecond/8;
                    break;
                default:
                    break;
            }
            sound.playRepeating((int) Math.round(time * mod));
        }
    }
    
    public static String getNoteType(int speed) {
        
        switch (speed) {
            case 1:
                return "whole";
            case 2:
                return "half";
            case 3:
                return "quarter";
            case 4:
                return "eighth";
            default:
                throw new UnsupportedOperationException("Unsupported note type " + speed);
        }
    }
    
    public static Map<String, Sound> loadSounds() {
        return Map.of(
                //                "piano-a-whole", new Sound("piano", "a", 1),
                //                "piano-a-half", new Sound("piano", "a", 2),
                //                "piano-a-quarter", new Sound("piano", "a", 3),
                //                "piano-a-eighth", new Sound("piano", "a", 4),
                //                "piano-b-whole", new Sound("piano", "b", 1),
                //                "piano-b-half", new Sound("piano", "b", 2),
                //                "piano-b-quarter", new Sound("piano", "b", 3),
                //                "piano-b-eighth", new Sound("piano", "b", 4),
                //                
                //                "piano-c-whole", new Sound("piano", "c", 1),
                //                "piano-c-half", new Sound("piano", "c", 2),
                //                "piano-c-quarter", new Sound("piano", "c", 3),
                //                "piano-c-eighth", new Sound("piano", "c", 4),

                "piano-D-whole", new Sound("piano", "d", 1),
                //                "piano-d-half", new Sound("piano", "d", 2),
                //                "piano-d-quarter", new Sound("piano", "d", 3),
                //                "piano-d-eighth", new Sound("piano", "d", 4),

                "piano-E-whole", new Sound("piano", "e", 1),
                //                "piano-e-half", new Sound("piano", "e", 2),
                //                "piano-e-quarter", new Sound("piano", "e", 3),
                //                "piano-e-eighth", new Sound("piano", "e", 4),

                "piano-F-whole", new Sound("piano", "f", 1),
                //                "piano-f-half", new Sound("piano", "f", 2),
                //                "piano-f-quarter", new Sound("piano", "f", 3),
                //                "piano-f-eighth", new Sound("piano", "f", 4),

                "piano-G-whole", new Sound("piano", "g", 1)
        //                "piano-g-half", new Sound("piano", "g", 2),
        //                "piano-g-quarter", new Sound("piano", "g", 3),
        //                "piano-g-eighth", new Sound("piano", "g", 4)                
        );
    }
}
