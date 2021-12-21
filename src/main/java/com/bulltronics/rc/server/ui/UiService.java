package com.bulltronics.rc.server.ui;

import com.bulltronics.rc.server.util.Util;
import com.bulltronics.rc.server.service.ConfigService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class UiService {
    boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

    static JFrame frame;
    static JLabel label;
    static JButton button;
    static JTable jTable;

    public static void frameInit() {
        frame = new JFrame();
        if (frame.isAlwaysOnTopSupported()) {
            frame.setAlwaysOnTop(true);
        }
        frame.setTitle("Remote Control Server");
        frame.setSize(300, 300);
        frame.setLayout(new BorderLayout(20, 15));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to close this window?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public static void renderUI(ActionListener actionListener) {
        frameInit();

        label = new JLabel(ConfigService.getServerName().getData().get("name").getAsString());
        label.setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("");
        model.addColumn("");
        model.addRow(new Object[]{"Hostnames", ""});
        for (String hostAddress : Util.getHostAddresses()) {
            model.addRow(new Object[]{"", hostAddress});
        }
        jTable = new JTable(model);
        jTable.setDefaultEditor(Object.class, null);
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        button = new JButton("Start Server");
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.addActionListener(actionListener);

        frame.add(label, BorderLayout.NORTH);
        frame.add(jTable, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public static void setStatusLabel(String labelStr) {
        label.setText(labelStr);
        label.paintImmediately(label.getVisibleRect());
    }

    public static void setActionButtonLabel(String labelStr) {
        button.setText(labelStr);
    }

    public static void setActionButtonListener(ActionListener actionListener) {
        for (ActionListener al : button.getActionListeners()) {
            button.removeActionListener(al);
        }

        button.addActionListener(actionListener);
    }
}
