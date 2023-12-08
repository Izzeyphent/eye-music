package com.zappers.eye.music.actions;

import static com.zappers.eye.music.Configs.BPM;
import com.zappers.eye.music.utils.ImageUtils;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import com.zappers.eye.music.utils.MusicManager;

public class MouseActionMovementListener extends MouseAdapter {

    private static final double DIAMETER_PERCENTAGE = 1000d / 1142d;
    private static final MusicManager MUSIC_MANAGER = new MusicManager(BPM);
    private final JLabel button;
    private int radius = 500;
    private int centerX;
    private int centerY;
    private int posX = -1;
    private int posY = -1;

    public MouseActionMovementListener(JLabel button) {
        this.button = button;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        posX = -1;
        posY = -1;
        super.mouseExited(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        posX = e.getX();
        posY = e.getY();
        System.out.println("We are here: (" + posX + "," + posY + ")");
        super.mouseEntered(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.posX = e.getX();
        this.posY = e.getY();
        updateRadius();
        boolean left = this.posX <= centerX;
        boolean top = this.posY <= centerY; //if grid is on the circle, then the y is positive for top and vice versa. same for x.
        double degrees = getAngleInDegrees(this.posX, this.posY, centerX, centerY);
        String color = ImageUtils.getColorByDegrees(left, top, degrees);
        double distance = Math.sqrt(Math.pow(centerX - posX, 2) + Math.pow(centerY - posY, 2));

        boolean isInCircle = distance <= radius;
        //System.out.println("(" + posX + "," + posY + ")");
        // if it's in the 4th section, it's a whole note so the speed is 1
        if (isInCircle) {
            MUSIC_MANAGER.play(color, (int) Math.abs(Math.floor(distance / (radius / 4D)) -4));
        }
        super.mouseMoved(e);
    }

    public void updateRadius() {
        this.radius = (int) ((DIAMETER_PERCENTAGE * button.getHeight()) / 2d);
        this.centerX = (int) (button.getWidth() / 2d);
        this.centerY = (int) (button.getHeight() / 2d);
    }

    public static double getDistanceFromCenter(int posX, int posY, int centerX, int centerY) {
        return Math.sqrt(Math.pow(centerX - posX, 2) + Math.pow(centerY - posY, 2));
    }

    public static double getAngleInDegrees(int posX, int posY, int centerX, int centerY) {
        var deltaX = Math.abs(posX - centerX);
        var deltaY = Math.abs(posY - centerY);
        var rad = Math.atan2(deltaY, deltaX); // In radians

        return Math.round(100 * (rad * (180 / Math.PI))) / 100;
    }

}
