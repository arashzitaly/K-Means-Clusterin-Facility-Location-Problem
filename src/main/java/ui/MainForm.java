package main.java.ui;

import main.java.service.*;
import main.java.ui.uiUtil.GeneralListener;
import main.java.ui.uiUtil.UITools;
import main.java.ui.uiUtil.mapUtil.MapFactory;
import main.java.util.DataTimeUtil;
import org.jxmapviewer.JXMapViewer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Date;

public class MainForm extends JFrame {

    private static MainForm instance = new MainForm();

    private JPanel plot = new JPanel();
    private JXMapViewer mapView;
    private JPanel showPanel = new JPanel();
    private JTextField numberOfClustersTextField = new JTextField("5");
    private JTextField numberOfDataTextField = new JTextField("500");
    private JRadioButton sequentialExecutionModeRadioButton = new JRadioButton("Sequential");
    private JRadioButton parallelExecutionModeRadioButton = new JRadioButton("Parallel");
    private JButton reportConstClustersButton = new JButton("Report with constant clusters");
    private JButton reportConstDataButton = new JButton("Report with constant data");
    private JButton clearLogButton = new JButton("Clear log");
    private JButton startButton = new JButton("Start!");
    private JTextArea logArea = new JTextArea();
    private JPanel mainPanel = new JPanel();
    private JPanel controlPanel = new JPanel();
    private JPanel buttonsPanel = new JPanel();
    private JPanel inputPanel = new JPanel();
    private JLabel numberOfClustersTextLabel = new JLabel("Number of clusters:");
    private JLabel numberOfDataLabel = new JLabel("Number of data:");
    private JLabel executionModeLabel = new JLabel("Execution mode:");
    private JPanel executionModePanel = new JPanel();
    private ButtonGroup executionModeRadioButton = new ButtonGroup();
    private JScrollPane scroll = new JScrollPane(logArea);

    private MainForm() {
        init();
    }

    public static MainForm getInstance() {
        return instance;
    }

    private  void  init() {

        // map
        mapView = MapFactory.getMapInstance();


        // plot
        plot.setLayout(new BorderLayout(1,1));
        // add member
        plot.add(new JPanel());


        // Execution radio buttons
        executionModePanel.setLayout(new GridLayout(1,2));
        sequentialExecutionModeRadioButton.setSelected(true);
        executionModeRadioButton.add(parallelExecutionModeRadioButton);
        executionModeRadioButton.add(sequentialExecutionModeRadioButton);

        // radio button panel
        executionModePanel.setLayout(new GridLayout(1,2));
        // add members
        executionModePanel.add(sequentialExecutionModeRadioButton);
        executionModePanel.add(parallelExecutionModeRadioButton);


//      logArea.setEnabled(false);
        logArea.setDisabledTextColor(Color.black);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // input panel
        inputPanel.setLayout(new GridLayout(6,0,0,5));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.setPreferredSize(new Dimension(250,0));
        // add members
        inputPanel.add(numberOfClustersTextLabel);
        inputPanel.add(numberOfClustersTextField);
        inputPanel.add(numberOfDataLabel);
        inputPanel.add(numberOfDataTextField);
        inputPanel.add(executionModeLabel);
        inputPanel.add(executionModePanel);

        // add click listener to buttons
        reportConstDataButton.addMouseListener(new GeneralListener());
        reportConstClustersButton.addMouseListener(new GeneralListener());
        clearLogButton.addMouseListener(new GeneralListener());
        startButton.addMouseListener(new GeneralListener());

        reportConstClustersButton.setFont(new Font("Tahoma", Font.BOLD , 11));

        // botton panel
        buttonsPanel.setPreferredSize(new Dimension(250,0));
        buttonsPanel.setLayout(new GridLayout(4,0,0,15));
        buttonsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // add members
        buttonsPanel.add(reportConstClustersButton);
        buttonsPanel.add(reportConstDataButton);
        buttonsPanel.add(clearLogButton);
        buttonsPanel.add(startButton);

        // show panel
        showPanel.setLayout(new BorderLayout(1,1));
        showPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        // add members
        showPanel.add(mapView);

        // control panel
        controlPanel.setLayout(new BorderLayout());
        controlPanel.setPreferredSize(new Dimension(0,200));
        controlPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        // add members
        controlPanel.add(inputPanel,BorderLayout.WEST);
        controlPanel.add(buttonsPanel,BorderLayout.EAST);
        controlPanel.add(scroll,BorderLayout.CENTER);

        // main panel
        mainPanel.setLayout(new BorderLayout());
        // add members
        mainPanel.add(controlPanel,BorderLayout.NORTH);
        mainPanel.add(showPanel,BorderLayout.CENTER);

        // main frame
        this.setSize(800, 600);
        this.setMinimumSize(new Dimension(800,600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UITools.setFormCenterScreen(this);
        // add members
        this.add(mainPanel,BorderLayout.CENTER);

    }


    public void start() {
        KMeansReport kMeansReport;
        insertLog("K-means calculation started!");

        // execute Sequential
        if (sequentialExecutionModeRadioButton.isSelected()) {
            KMeans kmeans = new KMeans(Integer.valueOf(numberOfClustersTextField.getText().trim()), Integer.valueOf(numberOfDataTextField.getText().trim()));
            kMeansReport = kmeans.run();
            MapFactory.addPointByCluster(kmeans.getClusters(), kmeans.getDataList(), mapView);
        }
        else {
            KMeansMultyThread kMeansMultyThread = new KMeansMultyThread(Integer.valueOf(numberOfClustersTextField.getText().trim()),
                    Integer.valueOf(numberOfDataTextField.getText().trim()),8);
            kMeansReport = kMeansMultyThread.run();
            MapFactory.addPointByCluster(kMeansMultyThread.getClusters(), kMeansMultyThread.getDataList(), mapView);
        }
        // execute Parallel
        insertLog("threadMainExecute Finished in " + kMeansReport.getTime() + "ms with "+ kMeansReport.getRepeatCount() +" repeats."+ " "+  (double)kMeansReport.getTime() / (double)kMeansReport.getRepeatCount());

    }

    public void clearLog() {
        logArea.setText("");
    }

    public JPanel reportConstClusters() {

        JPanel reportPlot;

        insertLog("Generation report with constant number of clusters started!");
        Date startDate = new Date();
        ReportManager reportManager = new ReportManager();
        if (sequentialExecutionModeRadioButton.isSelected()) {
            reportPlot = reportManager.reportWithConstClusterNumber(500, 20, 500, 3, ReportData.runMode.SEQUENTIAL);

        }
        else {
            reportPlot = reportManager.reportWithConstClusterNumber(500, 20, 500, 3, ReportData.runMode.PARALLEL);

        }

        insertLog("Generation report with constant number of clusters finished!");
        Date finishedDate = new Date();
        insertLog("Total execution time: "+ DataTimeUtil.gatDifDate(startDate, finishedDate));
        return reportPlot;
    }


    public JPanel reportConstData() {


        JPanel reportPlot;



        insertLog("Generation report with constant number of data started!");
        Date startDate = new Date();
        ReportManager reportManager = new ReportManager();
        if (sequentialExecutionModeRadioButton.isSelected()) {
            reportPlot = reportManager.reportWithConstDataNumber(30000, 5, 5, 3, ReportData.runMode.SEQUENTIAL);

        }
        else {
            reportPlot = reportManager.reportWithConstDataNumber(30000, 5, 5, 3, ReportData.runMode.PARALLEL);

        }

        insertLog("Generation report with constant number of data finished!");
        Date finishedDate = new Date();
        insertLog("Total execution time: "+ DataTimeUtil.gatDifDate(startDate, finishedDate));

        return reportPlot;
    }

    public void insertLog(String text) {
        logArea.setText(logArea.getText() + text + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }



    public void disableButtons() {
        reportConstClustersButton.setEnabled(false);
        reportConstDataButton.setEnabled(false);
        clearLogButton.setEnabled(false);
        startButton.setEnabled(false);

    }

    public void enableButtons() {
        reportConstClustersButton.setEnabled(true);
        reportConstDataButton.setEnabled(true);
        clearLogButton.setEnabled(true);
        startButton.setEnabled(true);
    }


    public void showPlot(JPanel jPanel) {
        showPanel.remove(0);
        showPanel.add(plot);
        plot.add(jPanel);
        plot.updateUI();
    }


    public void showMap() {
        showPanel.remove(0);
        showPanel.add(mapView);
        showPanel.updateUI();
    }

}
