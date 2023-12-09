package com.zappers.eye.music.actions;

import static com.zappers.eye.music.Configs.BPM;
import com.zappers.eye.music.utils.ImageUtils;
import static com.zappers.eye.music.utils.ImageUtils.COLOR_TO_NOTE;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import com.zappers.eye.music.utils.MusicManager;
import static com.zappers.eye.music.utils.MusicManager.getNoteType;

public class MouseActionMovementListener extends MouseAdapter {

    private static final double DIAMETER_PERCENTAGE = 1000d / 1142d;
    private static final MusicManager MUSIC_MANAGER = new MusicManager(BPM);
    private final JLabel button;
    private String currentMousePosition = "";
    private int radius = 500;
    private int centerX;
    private int centerY;
    private int posX = -1;
    private int posY = -1;

    /**
     * This is to sense when the mouse is moving. That way, we know so that we can do different things while it's moving.
     *
     * @param button The button is interacting with the mouse.
     */
    public MouseActionMovementListener(JLabel button) {
        this.button = button;
    }

    /**
     * This is for when the mouse leaves the button area.
     *
     * @param e This means that the event is triggered when the mouse exits the button area,
     */
    @Override
    public void mouseExited(MouseEvent e) {
        posX = -1;
        posY = -1;
        super.mouseExited(e);
    }

    /**
     * This bit is an event that occurs when the mouse enters the button area. Then, it gets the X and Y coordinates, and prints them out.
     *
     * @param e In this case, e is just an event that is triggered when the mouse enters the button.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        posX = e.getX();
        posY = e.getY();
        System.out.println("We are here: (" + posX + "," + posY + ")");
        super.mouseEntered(e);
    }

    /**
     * This event occurs any time the mouse moves. It gets the x and y coordinates, and then determines whether the mouse is in the top, bottom, left,
     * or right quadrant of the grid. It also determines if it is in the circle, and how far away it is from the grid (in degrees). Then it determines
     * what note the mouse is hovering over.
     *
     * @param e The mouse moving event
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        this.posX = e.getX();
        this.posY = e.getY();
        updateRadius(); //shouldn't be nessesary, but it is :/

        //  determines the quadrant the mouse is placed in.
        boolean left = this.posX <= centerX;
        boolean top = this.posY <= centerY; //if grid is on the circle, then the y is positive for top and vice versa. same for x.

        // this determines the degrees that the mouse has away from the lines of the grid.
        double degrees = getAngleInDegrees(this.posX, this.posY, centerX, centerY);

        // gets colour from img based on quadr. and the degrees from the line. each triangle is 45 degrees
        String color = ImageUtils.getColorByDegrees(left, top, degrees);
        double distance = Math.sqrt(Math.pow(centerX - posX, 2) + Math.pow(centerY - posY, 2));

        // This bit determines which note the mouse is hovering over.
        boolean isInCircle = distance <= radius;
        var type = "piano"; // TODO add other types
        var note = COLOR_TO_NOTE.get(color);
        var speed = (int) Math.abs(Math.floor(distance / (radius / 4D)) - 4);

        // if it's in the 4th section, it's a whole note so the speed is 1 
        /**
         * This is about when the mouse clicks on a note. When the note is clicked on, it continues playing the note it was playing, even when the
         * mouse is removed from the button.
         */
        if (isInCircle) {
            String sound = type + '-' + note + '-' + getNoteType(speed);
            if (!currentMousePosition.equals(sound)) {
                MUSIC_MANAGER.stopNonStuck();
                MUSIC_MANAGER.play(sound, speed, false);
                this.currentMousePosition = sound;
            }
        } else {
            this.currentMousePosition = "";
            MUSIC_MANAGER.stopNonStuck();
        }
        super.mouseMoved(e);
    }

    /**
     * This event determines where the mouse is when the mouse clicks.
     *
     * @param e This event is simply to find the mouse, then figure out if it's stuck or not.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        double distance = Math.sqrt(Math.pow(centerX - posX, 2) + Math.pow(centerY - posY, 2));
        boolean isInCircle = distance <= radius;
        if (!isInCircle) {
            return;
        }
        var speed = (int) Math.abs(Math.floor(distance / (radius / 4D)) - 4);
        if (!MUSIC_MANAGER.unstickIfStuck(currentMousePosition)) {
            MUSIC_MANAGER.play(currentMousePosition, speed, true);
        }
    }

    public void updateRadius() {
        this.radius = (int) ((DIAMETER_PERCENTAGE * button.getHeight()) / 2d);
        this.centerX = (int) (button.getWidth() / 2d);
        this.centerY = (int) (button.getHeight() / 2d);
    }

    /**
     * This is to find the distance from the centre of the circle to the mouse.
     *
     * @param posX The position of x.
     * @param posY The y position.
     * @param centerX The origin of the grid.
     * @param centerY The origin of the grid for the circle.
     * @return the distance from the mouse and the centre of the circle.
     */
    public static double getDistanceFromCenter(int posX, int posY, int centerX, int centerY) {
        return Math.sqrt(Math.pow(centerX - posX, 2) + Math.pow(centerY - posY, 2));
    }

    /**
     * This gets the angle in degrees from where the mouse is relative to the lines of the graph.
     *
     * @param posX The position of x.
     * @param posY The y position.
     * @param centerX The origin of the grid.
     * @param centerY The origin of the grid for the circle.
     * @return Finds the degrees using arc tan.
     */
    public static double getAngleInDegrees(int posX, int posY, int centerX, int centerY) {
        var deltaX = Math.abs(posX - centerX);
        var deltaY = Math.abs(posY - centerY);
        var rad = Math.atan2(deltaY, deltaX); // In radians

        return Math.round(100 * (rad * (180 / Math.PI))) / 100;
    }

}
