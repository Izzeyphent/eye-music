package com.zappers.eye.music;

import static com.zappers.eye.music.Configs.BPM;
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
import com.zappers.eye.music.utils.MusicManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class App {

    public static final MusicManager MUSIC_MANAGER = new MusicManager(BPM);
    private static final URL BUTTON_IMAGE = App.class.getClassLoader().getResource("music-buttons.png");

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Music");
        frame.setSize(1000, 1000);
        JPanel panel = buildMainMusicButtons();
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addComponentListener(new SquareResizerListener());
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 27) {
                    MUSIC_MANAGER.stopAll();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    /**
     * this method is to create the music buttons. It first creates an (invisible) panel and then uses an image, putting that over the panel then it
     * creates a button out of the image. the button is visible. it also attaches a mouse listener so that it can sense where the mouse is at the
     * screen.
     *
     * @return it returns the panel we just defined.
     * @throws IOException when the button image is NOT loaded; when it fails to do so.
     */
    public static JPanel buildMainMusicButtons() throws IOException {
        JPanel panel = new JPanel(); // the panel is not visible in output
        ImageIcon icon = new ImageIcon(ImageUtils.resize(ImageIO.read(BUTTON_IMAGE.openStream()), 1000, 1000));

        JLabel button = new JLabel(icon);

        button.setVisible(true);

        var mouseListener = new MouseActionMovementListener(button);
        button.addMouseListener(mouseListener);
        button.addMouseMotionListener(mouseListener);
        panel.setBackground(Color.GRAY);
        panel.add(button); // Components Added using Flow Layout
        panel.addComponentListener(new IconComponentResizeListener(button, ImageIO.read(BUTTON_IMAGE.openStream())));
        return panel;
    }
}
