package main.java.ui.uiUtil.mapUtil;

import main.java.service.Cluster;
import main.java.service.Data;
import main.java.util.Util;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.*;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.util.*;
import java.util.List;


public class MapFactory
{

    public static JXMapViewer getMapInstance() {
        final List<TileFactory> factories = new ArrayList<TileFactory>();

        TileFactoryInfo osmInfo = new OSMTileFactoryInfo();
        TileFactoryInfo veInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);

        factories.add(new DefaultTileFactory(osmInfo));
        factories.add(new DefaultTileFactory(veInfo));

        final JXMapViewer mapViewer = new JXMapViewer();
        final JLabel labelAttr = new JLabel();
        mapViewer.setLayout(new BorderLayout());
        mapViewer.add(labelAttr, BorderLayout.SOUTH);

        TileFactory firstFactory = factories.get(0);
        mapViewer.setTileFactory(firstFactory);
        labelAttr.setText(firstFactory.getInfo().getAttribution() + " - " + firstFactory.getInfo().getLicense());

        GeoPosition tehran = new GeoPosition(35.69942368, 51.38044205);

        mapViewer.setZoom(8);
        mapViewer.setAddressLocation(tehran);


        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);

        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));

        return mapViewer;
    }



    public static void addPointByCluster(ArrayList<Cluster> clusterList, ArrayList<Data> list, JXMapViewer mapViewer) {
        ArrayList<ColoredWaypoint> arrays = new ArrayList<>();

        for(Data data : list) {
            arrays.add(new ColoredWaypoint(2+(int)(data.getWeight()/100*5),Util.getColor(data.getClusterId()+1),new GeoPosition(data.getX(), data.getY())));
        }

        for (Cluster cluster: clusterList) {
            arrays.add(new ColoredWaypoint(15,Util.getColor(cluster.getKey()+1),new GeoPosition(cluster.getCenter().getX(), cluster.getCenter().getY())));
        }

        HashSet<ColoredWaypoint> waypoints = new HashSet<>(arrays);

        SwingWaypointOverlayPainter swingWaypointPainter = new SwingWaypointOverlayPainter();
        swingWaypointPainter.setWaypoints(waypoints);

        mapViewer.setOverlayPainter(swingWaypointPainter);

    }
}


