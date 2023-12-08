/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zappers.eye.music.actions;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import static com.zappers.eye.music.utils.ImageUtils.resize;

/**
 *
 * @author TheCo
 */
public class IconComponentResizeListener implements ComponentListener {

    private final JLabel component;
    private final BufferedImage originalImage;

    public IconComponentResizeListener(JLabel component, BufferedImage originalImage) {
        this.component = component;
        this.originalImage = originalImage;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        component.setIcon(new ImageIcon(resize(originalImage, e.getComponent().getWidth() - 15, e.getComponent().getHeight() - 15)));
        ((MouseActionMovementListener) component.getMouseMotionListeners()[0]).updateRadius();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}
