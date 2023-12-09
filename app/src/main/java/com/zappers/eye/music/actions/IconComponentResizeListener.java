package com.zappers.eye.music.actions;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import static com.zappers.eye.music.utils.ImageUtils.resize;

public class IconComponentResizeListener implements ComponentListener {

    private final JLabel component;
    private final BufferedImage originalImage;

    /**
     * Constructor is a resize listener. these two get passed in.
     *
     * @param component The label that we're resizing.
     * @param originalImage The image to be resized.
     */
    public IconComponentResizeListener(JLabel component, BufferedImage originalImage) {
        this.component = component;
        this.originalImage = originalImage;
    }

    /**
     * This makes the button and everything resize. It does so by resizing the image and keeping it a square, and .
     *
     * @param event The event that occurs when the button/label resizes.
     */
    @Override
    public void componentResized(ComponentEvent event) {
        component.setIcon(new ImageIcon(resize(originalImage, event.getComponent().getWidth() - 15, event.getComponent().getHeight() - 15)));
        ((MouseActionMovementListener) component.getMouseMotionListeners()[0]).updateRadius();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        //unused
    }

    @Override
    public void componentShown(ComponentEvent e) {
        //unused
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        //unsused
    }
}
