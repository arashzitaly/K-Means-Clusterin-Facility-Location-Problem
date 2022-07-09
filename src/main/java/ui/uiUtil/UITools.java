package main.java.ui.uiUtil;

import javax.swing.*;
import java.awt.*;


public class UITools {
    public static void setFormCenterScreen(Window window) {
        int frameMaxWidth;
        int frameMaxHigh;
        int frameWidth;
        int frameHight;
        int xLocation;
        int yLocation;

        frameMaxWidth = (int) window.getGraphicsConfiguration().getBounds().getMaxX();
        frameMaxHigh = (int) window.getGraphicsConfiguration().getBounds().getMaxY();

        frameWidth=(int) window.getSize().getWidth();
        frameHight=(int) window.getSize().getHeight();

        xLocation=(int) ((frameMaxWidth - frameWidth) / 2);
        yLocation=(int) ((frameMaxHigh - frameHight) / 2);

        window.setLocation(xLocation,yLocation);
    }

}
