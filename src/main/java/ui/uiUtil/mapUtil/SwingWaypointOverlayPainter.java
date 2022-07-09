package main.java.ui.uiUtil.mapUtil;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.WaypointPainter;

import java.awt.*;
import java.awt.geom.Point2D;

public class SwingWaypointOverlayPainter extends WaypointPainter<ColoredWaypoint> {


    @Override
    protected void doPaint(Graphics2D g, JXMapViewer jxMapViewer, int width, int height) {

        for (ColoredWaypoint coloredWaypoint : getWaypoints()) {
            Point2D point = jxMapViewer.getTileFactory().geoToPixel(
                    coloredWaypoint.getPosition(), jxMapViewer.getZoom());
            Rectangle rectangle = jxMapViewer.getViewportBounds();
            int buttonX = (int) (point.getX() - rectangle.getX());
            int buttonY = (int) (point.getY() - rectangle.getY());
            if (coloredWaypoint.getSize() != 15) {
                g.setColor(coloredWaypoint.getColor());
                g.fillOval(buttonX, buttonY, coloredWaypoint.getSize(), coloredWaypoint.getSize());
            }
        }

        for (ColoredWaypoint coloredWaypoint : getWaypoints()) {
            Point2D point = jxMapViewer.getTileFactory().geoToPixel(
                    coloredWaypoint.getPosition(), jxMapViewer.getZoom());
            Rectangle rectangle = jxMapViewer.getViewportBounds();
            int buttonX = (int) (point.getX() - rectangle.getX());
            int buttonY = (int) (point.getY() - rectangle.getY());
            if (coloredWaypoint.getSize() == 15) {
                g.setColor(coloredWaypoint.getColor().darker().darker().darker());
                g.drawOval(buttonX, buttonY, coloredWaypoint.getSize(), coloredWaypoint.getSize());
                g.setColor(coloredWaypoint.getColor().darker());
                g.fillOval(buttonX, buttonY, coloredWaypoint.getSize(), coloredWaypoint.getSize());
            }
        }
    }

}