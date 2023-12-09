package com.zappers.eye.music.actions;

import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class SquareResizerListener implements ComponentListener {

    /**
     * When the frame is resized, this method is called.
     *
     * @param e Is the event that happens, making the frame resize.
     */
    @Override
    public void componentResized(ComponentEvent e) {
        Rectangle b = e.getComponent().getBounds();
        e.getComponent().setBounds(b.x, b.y, b.width, b.width);
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        //unused
    }

    @Override
    public void componentShown(ComponentEvent e) {
        //uinsused
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        //unsused
    }
}
