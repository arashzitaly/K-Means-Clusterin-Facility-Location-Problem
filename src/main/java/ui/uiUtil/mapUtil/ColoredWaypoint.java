package main.java.ui.uiUtil.mapUtil;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


class ColoredWaypoint extends DefaultWaypoint {
    private final Color color;
    private final int size;
    ColoredWaypoint(int size, Color color, GeoPosition coord) {
        super(coord);
        this.size = size;
        this.color = color;
    }

    Color getColor() {
        return color;
    }

    int getSize() {
        return size;
    }
}
