package com.frame;

import com.action.Process;
import com.config.Constants;
import com.updater.Version;
import com.resource.Res;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class GUI extends JFrame {

    private static GUI instance;

    public static GUI getInstance() {
        return instance != null ? instance : (instance = new GUI());
    }

    private JButton launch, refresh;
    private JComboBox clients;
    private DefaultComboBoxModel model;
    private List<Version> list;

    public GUI() {
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        refresh = new JButton();
        model = new DefaultComboBoxModel();
        getModel().addElement("No Clients are Currently Available");
        clients = new JComboBox(model);
        launch = new JButton();
        JLabel copyright = new JLabel();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(Constants.TITLE);
        setAlwaysOnTop(true);
        setIconImage(Res.ICON.getImage());
        setMaximumSize(new Dimension(354, 148));
        setMinimumSize(new Dimension(354, 148));
        setUndecorated(true);
        setResizable(false);
        panel.setDoubleBuffered(false);
        label.setIcon(Res.BANNER);
        label.setToolTipText(String.format("%s - The Number One Economy Server", Constants.NAME));
        refresh.setIcon(new ImageIcon(getClass().getResource("/com/resource/refresh16.png")));
        refresh.setToolTipText("Refresh Clients");
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action(e);
            }
        });
        clients.setToolTipText("Clients List");
        clients.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action(e);
            }
        });
        clients.setEnabled(false);
        launch.setIcon(Res.LAUNCH_16);
        launch.setToolTipText("Launch Client");
        launch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action(e);
            }
        });
        launch.setEnabled(false);
        copyright.setFont(new Font("Dialog", 0, 5));
        copyright.setText(String.format("Copyright © 2016 By the Developers of %s All Rights Reserved", Constants.NAME));
        GroupLayout PanelLayout = new GroupLayout(panel);
        panel.setLayout(PanelLayout);
        PanelLayout.setHorizontalGroup(
                PanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(PanelLayout.createSequentialGroup()
                        .addGroup(PanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(PanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(PanelLayout.createSequentialGroup()
                                                .addComponent(refresh, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(clients, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(launch, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(copyright))
                        .addGap(0, 0, Short.MAX_VALUE))
        );
        PanelLayout.setVerticalGroup(
                PanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(PanelLayout.createSequentialGroup()
                        .addComponent(label)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(refresh, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(clients))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(launch, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(copyright))
        );
        panel.add(new JLabel(Res.BANNER), BorderLayout.CENTER);
        getContentPane().add(panel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    public JButton getRefreshButton() {
        return refresh;
    }

    public JButton getLaunchButton() {
        return launch;
    }

    public JComboBox getClientsBox() {
        return clients;
    }

    public DefaultComboBoxModel getModel() {
        return model;
    }

    public List<Version> getList() {
        return list;
    }

    public void setList(final List<Version> value) {
        list = value;
    }

    public void action(ActionEvent event) {
        final Process process = Process.getByComponentValue((Component) event.getSource());
        if (process != null) {
            process.action();
        }
    }
}
