package com.pharmondev.computercontrol.gui;

import com.fazecast.jSerialComm.SerialPort;
import com.pharmondev.computercontrol.listeners.Sender;
import com.pharmondev.computercontrol.models.Game;
import com.pharmondev.computercontrol.programs.Program;
import com.pharmondev.computercontrol.programs.StartupProgram;
import com.pharmondev.computercontrol.utils.MessageSingleton;
import com.pharmondev.computercontrol.utils.ProgramLoader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

import com.pharmondev.computercontrol.utils.SerialConnector;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class ControlGui {
    private JPanel mainPanel;
    private JPanel ProgramSelector;
    private JPanel InfoAndSettings;
    private JPanel ProgramAndSettingsSection;
    private JPanel currentPropgramInfo;
    private JPanel ConsoleSettingsPanel;
    private JPanel ProgramInfoConsoleAndActions;
    private JPanel Actions;
    private JButton startProgramButton;
    private JButton restoreDefaultsButton;
    private JScrollPane ConsoleVideoAndSettings;
    private JList programsList;
    private JComboBox programComboBox;
    private JPanel console0Panel;
    private JPanel consoleSettingsPanel;
    private JPanel videoPanel;
    private JPanel programSettings;
    private JLabel serialPort;
    private JLabel camera;
    private JLabel audioInput;
    private JLabel audioOutput;
    private JComboBox serialPortBox;
    private JComboBox cameraBox;
    private JComboBox audioInBox;
    private JComboBox audioOutBox;
    private JLabel programConnected;
    private JLabel videoLabel;
    private JLabel fpsLabel;
    private JLabel serialConnectionLabel;
    private JButton resetSerialButton;

    private Map<String, Integer> captureDeviceMap;

    private DefaultListModel programListModel;
    private Thread videoThread;

    public ControlGui() {
        captureDeviceMap = new HashMap<>();
        programListModel = new DefaultListModel();
        programsList.setModel(programListModel);
        populateCaptureDevices();
        populateSerialPorts();
        populateGames();
        cameraBox.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        stopCapture();
                        if (((JComboBox) e.getSource()).getSelectedItem().equals("Select Device")) {

                        } else {
                            startCapture();
                        }
                    }
                });
        programComboBox.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (programListModel != null && !programListModel.isEmpty()) {
                            programListModel.clear();
                            ProgramLoader.getInstance()
                                    .getGames()
                                    .forEach(
                                            game -> {
                                                if (((JComboBox) e.getSource())
                                                        .getSelectedItem()
                                                        .equals(game.getName())) {
                                                    game.getPrograms()
                                                            .forEach(
                                                                    program -> {
                                                                        programListModel.addElement(
                                                                                program.getName());
                                                                    });
                                                }
                                            });
                            programsList.setModel(programListModel);
                        } else {
                            ProgramLoader.getInstance()
                                    .getGames()
                                    .forEach(
                                            game -> {
                                                if (((JComboBox) e.getSource())
                                                        .getSelectedItem()
                                                        .equals(game.getName())) {
                                                    game.getPrograms()
                                                            .forEach(
                                                                    program -> {
                                                                        programListModel.addElement(
                                                                                program.getName());
                                                                    });
                                                }
                                            });
                            // programsList = new JList(programListModel);
                            programsList.setModel(programListModel);
                        }
                    }
                });
        resetSerialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!serialPortBox.getSelectedItem().equals("Select Device")) {
                    SerialConnector.getInstance().resetConnection(serialPortBox.getSelectedItem().toString());
                    if(SerialConnector.getInstance().isConnected()){
                        serialConnectionLabel.setText("Serial Connection: is connected");
                        serialConnectionLabel.setForeground(Color.GREEN);

                        //TODO may need to run "STOP" message first.

                        //TODO send startup program
                        Program startupProgram = new StartupProgram();
                        startupProgram.run();


                    }
                    else {
                        serialConnectionLabel.setText("Serial Connection: is not connected");
                        serialConnectionLabel.setForeground(Color.RED);
                    }
                }
            }
        });
    }

    private void populateGames() {
        programComboBox.addItem("Select Game");
        java.util.List<Game> games = ProgramLoader.getInstance().getGames();
        for (Game game : games) {
            String name = game.getName();
            programComboBox.addItem(name);
        }
    }

    private void populatePrograms() {}

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void populateSerialPorts() {
        SerialPort[] ports = SerialPort.getCommPorts();
        serialPortBox.addItem("Select Serial Device");
        for (SerialPort port : ports) {
            serialPortBox.addItem(port.getSystemPortName());
        }
    }

    private void populateCaptureDevices() {
        // Populate the dropdown menu with capture devices
        int deviceIndex = 0;
        cameraBox.addItem("Select Device");
        while (true) {
            VideoCapture videoCapture = new VideoCapture(deviceIndex);

            if (videoCapture.isOpened()) {
                String deviceName = "Capture Device " + deviceIndex;
                System.out.println("Device name: " + videoCapture.getBackendName());
                captureDeviceMap.put(deviceName, deviceIndex);
                cameraBox.addItem(deviceName);
                deviceIndex++;
                videoCapture.release();
            } else {
                break;
            }
        }
    }

    private void startCapture() {
        System.out.println("Start Capture: ");
        // Get the selected capture device from the dropdown menu
        String selectedCaptureDevice = (String) cameraBox.getSelectedItem();
        int deviceIndex = captureDeviceMap.get(selectedCaptureDevice);

        // Start the video capture
        VideoCapture videoCapture = new VideoCapture(deviceIndex);
        if (videoCapture.isOpened()) {

            Mat frame = new Mat();
            if (videoCapture.read(frame)) {
                // Convert the frame to an image for display
                BufferedImage image = convertMatToBufferedImage(frame);

                // Set the preferred size of the videoLabel based on the frame dimensions
                Dimension labelSize = new Dimension(frame.width(), frame.height());
                Dimension minSize = new Dimension(1080 / 2, 720 / 2);
                Dimension maxSize = new Dimension(1080, 720);

                videoLabel.setPreferredSize(maxSize);
                videoLabel.setMaximumSize(maxSize);
                videoLabel.setMinimumSize(maxSize);

                // Update the video label with the new image
                SwingUtilities.invokeLater(
                        () -> {
                            videoLabel.setIcon(new ImageIcon(image));
                            videoLabel.revalidate();
                            videoLabel.repaint();
                        });

                videoThread =
                        new Thread(
                                () -> {
                                    long startTime = System.currentTimeMillis();
                                    int frameCount = 0;
                                    while (true || !Thread.interrupted()) {
                                        // Read the current frame from the video capture
                                        if (videoCapture.read(frame)) {
                                            // Convert the frame to an image for display
                                            BufferedImage newImage =
                                                    convertMatToBufferedImageScaled(frame);
                                            SwingUtilities.invokeLater(
                                                    () -> {
                                                        // Update the video label with the new image
                                                        videoLabel.setIcon(new ImageIcon(newImage));
                                                        videoLabel.revalidate();
                                                        videoLabel.repaint();
                                                    });
                                        }

                                        // Calculate FPS
                                        frameCount++;
                                        long currentTime = System.currentTimeMillis();
                                        long elapsedTime = currentTime - startTime;
                                        if (elapsedTime >= 1000) {
                                            double fps = frameCount / (elapsedTime / 1000.0);
                                            fpsLabel.setText(String.format("FPS: %.2f", fps));
                                            startTime = currentTime;
                                            frameCount = 0;
                                        }
                                    }
                                });
                videoThread.setName("videoThread");
                videoThread.start();
            }
        } else {
            System.out.println("Error: Failed to open capture device.");
        }
    }

    private void stopCapture() {
        if (videoThread != null) {

            videoThread.interrupt();
        }
        // Release the resources of the video capture
        // (if needed, you can add additional cleanup code here)
    }

    private BufferedImage convertMatToBufferedImage(Mat mat) {
        // Check if the Mat object is empty or has invalid dimensions
        if (mat.empty() || mat.cols() <= 0 || mat.rows() <= 0) {
            return createPlaceholderImage();
        }

        // Convert the Mat object to a BufferedImage
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (mat.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        mat.get(0, 0, ((DataBufferByte) image.getRaster().getDataBuffer()).getData());

        return image;
    }

    private BufferedImage convertMatToBufferedImageScaled(Mat mat) {
        // Check if the Mat object is empty or has invalid dimensions
        if (mat.empty() || mat.cols() <= 0 || mat.rows() <= 0) {
            return createPlaceholderImage();
        }

        // Get the dimensions of the videoLabel
        int labelWidth = videoLabel.getWidth();
        int labelHeight = videoLabel.getHeight();

        // Calculate the scaling factor to fit the label while maintaining the aspect ratio
        double scaleX = (double) labelWidth / mat.cols();
        double scaleY = (double) labelHeight / mat.rows();
        double scale = Math.min(scaleX, scaleY);

        // Check if the scaling factor is valid
        if (scale <= 0) {
            return createPlaceholderImage();
        }

        // Resize the Mat object using the calculated scale
        Mat resizedMat = new Mat();
        Size newSize = new Size(mat.cols() * scale, mat.rows() * scale);
        Imgproc.resize(mat, resizedMat, newSize);

        // Convert the resized Mat object to a BufferedImage
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (resizedMat.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(resizedMat.cols(), resizedMat.rows(), type);
        resizedMat.get(0, 0, ((DataBufferByte) image.getRaster().getDataBuffer()).getData());

        return image;
    }
    private double interpolate(double a, double b, double fraction) {
        return (1 - fraction) * a + fraction * b;
    }

    private BufferedImage createPlaceholderImage() {
        int width = 1;
        int height = 1;

        BufferedImage placeholderImage =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = placeholderImage.createGraphics();
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();

        return placeholderImage;
    }
}

//    private BufferedImage convertMatToBufferedImage(Mat mat) {
//        // Check if the Mat object is empty or has invalid dimensions
//        if (mat.empty() || mat.cols() <= 0 || mat.rows() <= 0) {
//            return createPlaceholderImage();
//        }
//
//        // Get the dimensions of the videoLabel
//        int labelWidth = videoLabel.getWidth();
//        int labelHeight = videoLabel.getHeight();
//
//        // Calculate the scaling factor to fit the label while maintaining the aspect ratio
//        double scaleX = (double) labelWidth / mat.cols();
//        double scaleY = (double) labelHeight / mat.rows();
//        double scale = Math.min(scaleX, scaleY);
//
//        // Check if the scaling factor is valid
//        if (scale <= 0) {
//            return createPlaceholderImage();
//        }
//
//        // Resize the Mat object using the calculated scale
//        Mat resizedMat = new Mat();
//        org.opencv.core.Size newSize =
//                new org.opencv.core.Size(mat.cols() * scale, mat.rows() * scale);
//        resizeMat(mat, resizedMat, newSize);
//
//        // Convert the resized Mat object to a BufferedImage
//        int type = BufferedImage.TYPE_BYTE_GRAY;
//        if (resizedMat.channels() > 1) {
//            type = BufferedImage.TYPE_3BYTE_BGR;
//        }
//        BufferedImage image = new BufferedImage(resizedMat.cols(), resizedMat.rows(), type);
//        resizedMat.get(0, 0, ((DataBufferByte) image.getRaster().getDataBuffer()).getData());
//
//        return image;
//    }