/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.zappers.eye.music;

import com.zappers.eye.music.actions.IconComponentResizeListener;
import com.zappers.eye.music.actions.MouseActionMovementListener;
import com.zappers.eye.music.actions.SquareResizerListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.zappers.eye.music.utils.ImageUtils;

public class App {

    private static final URL BUTTON_IMAGE = App.class.getClassLoader().getResource("music-buttons.png");

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Music");
        frame.setSize(1000, 1000);
        JPanel panel = buildMainMusicButtons();
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addComponentListener(new SquareResizerListener());
    }

    public static JPanel buildMainMusicButtons() throws IOException {
        JPanel panel = new JPanel(); // the panel is not visible in output
        ImageIcon icon = new ImageIcon(ImageUtils.resize(ImageIO.read(BUTTON_IMAGE.openStream()), 1000, 1000));

        JLabel button = new JLabel(icon);

        button.setVisible(true);

        button.addMouseMotionListener(new MouseActionMovementListener(button));
        panel.setBackground(Color.GRAY);
        panel.add(button); // Components Added using Flow Layout
        panel.addComponentListener(new IconComponentResizeListener(button, ImageIO.read(BUTTON_IMAGE.openStream())));
        return panel;
    }
}
