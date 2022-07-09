package main.java.ui.uiUtil;

import main.java.ui.MainForm;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GeneralListener implements MouseListener {




    @Override
    public void mouseClicked(final MouseEvent mouseEvent) {

        new Thread() {
            public void run() {
                String clickedText = ((JButton)mouseEvent.getComponent()).getText();
                MainForm.getInstance().disableButtons();
                if (clickedText.equals("Start!")) {
                    MainForm.getInstance().showMap();
                    MainForm.getInstance().start();
                }
                if (clickedText.equals("Report with constant clusters")) {
                    JPanel plot = MainForm.getInstance().reportConstClusters();
                    MainForm.getInstance().showPlot(plot);
                }
                if (clickedText.equals("Report with constant data")) {
                    JPanel plot = MainForm.getInstance().reportConstData();
                    MainForm.getInstance().showPlot(plot);
                }
                if (clickedText.equals("Clear log")) {
                    MainForm.getInstance().clearLog();
                }
                MainForm.getInstance().enableButtons();
            }

        }.start();

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
