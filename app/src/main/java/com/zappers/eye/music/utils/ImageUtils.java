package com.zappers.eye.music.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Map;

public final class ImageUtils {

    public static final Map<String, String> COLOR_TO_NOTE = Map.of(
            "RED", "REST",
            "ORANGE", "C",
            "YELLOW", "D",
            "LIME", "E",
            "CYAN", "F",
            "BLUE", "G",
            "PURPLE", "A",
            "PINK", "B"
    );

    public static BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bi;
    }

    public static String getColorByDegrees(boolean left, boolean top, double degrees) {
        double modDegrees = degrees;

        // if it's perfectly straight down
        if (degrees == 90 && !top) {
            modDegrees += 180;
            // if it's perfectly to the right
        } else if (degrees == 0 && left) {
            modDegrees += 180;
            // quad 1
        } else if (left && top) {
            modDegrees = 180 - modDegrees;
            // quad 3
        } else if (left && !top) {
            modDegrees += 180;
            // quad 4
        } else if (!left && !top) {
            modDegrees = 360 - modDegrees;
        }
        switch ((int) Math.round(modDegrees / 45)) {
            case 0:
            case 8:
                return "YELLOW";
            case 1:
                return "ORANGE";
            case 2:
                return "RED";
            case 3:
                return "PINK";
            case 4:
                return "PURLE";
            case 5:
                return "BLUE";
            case 6:
                return "CYAN";
            case 7:
                return "LIME";
            default:
                return "NONE";
        }
    }
}
