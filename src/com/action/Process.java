package com.action;

import com.config.Configuration;
import com.config.Constants;
import com.frame.GUI;
import com.initialize.Starter;
import com.updater.Updater;
import com.updater.Version;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Daniel
 */
public enum Process {

    REFRESH(GUI.getInstance().getRefreshButton()) {
        @Override
        public void action() {
            getComponent().setEnabled(false);
            GUI.getInstance().getClientsBox().setEnabled(false);
            GUI.getInstance().getLaunchButton().setEnabled(false);
            GUI.getInstance().getModel().removeAllElements();
            EventQueue.invokeLater(new Runnable() {
                                       @Override
                                       public void run() {
                                           GUI.getInstance().setList(Updater.getInstance().loadClientList());
                                           if (GUI.getInstance().getList() != null) {
                                               for (Version version : GUI.getInstance().getList()) {
                                                   if (version == null)
                                                       continue;
                                                   GUI.getInstance().getModel().addElement(version);
                                                   GUI.getInstance().getClientsBox().repaint();
                                               }
                                               GUI.getInstance().getClientsBox().setEnabled(true);
                                               GUI.getInstance().getLaunchButton().setEnabled(true);
                                           } else {
                                               GUI.getInstance().getLaunchButton().setEnabled(false);
                                               GUI.getInstance().getModel().addElement("No Clients are Currently Available");
                                               EventQueue.invokeLater(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                       JOptionPane.showMessageDialog(GUI.getInstance(), "Error Refreshing Client Versions", "Version Error", JOptionPane.ERROR_MESSAGE);
                                                   }
                                               });
                                           }
                                       }
                                   });
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        getComponent().setEnabled(true);
                    }
                });
        }
    },
    LAUNCH(GUI.getInstance().getLaunchButton()) {
        @Override
        public void action() {
            final Version version = (Version) GUI.getInstance().getClientsBox().getSelectedItem();
            if (GUI.getInstance().getList() == null || GUI.getInstance().getList().isEmpty() || version == null || !GUI.getInstance().getClientsBox().isEnabled()) {
                GUI.getInstance().getLaunchButton().setEnabled(false);
                EventQueue.invokeLater(new Runnable() {
                                           @Override
                                           public void run() {
                                               JOptionPane.showMessageDialog(GUI.getInstance(), "No Client selected for Launch", "No Client", JOptionPane.ERROR_MESSAGE);
                                           }
                });
            } else {
                final File file = new File(Configuration.CLIENT_DIRECTORY, version.getFile());
                if (!file.exists()) {
                    Updater.getInstance().download(version);
                }
                Starter.getInstance().start(file);
                GUI.getInstance().dispose();
            }
        }
    },
    CLIENT(GUI.getInstance().getClientsBox()) {
        @Override
        public void action() {
            final Version version = (Version) GUI.getInstance().getClientsBox().getSelectedItem();
            GUI.getInstance().setTitle(String.format("%s | %s", Constants.TITLE, version != null ? version.getDisplay() : "None"));
        }
    };

    private final static Map<Component, Process> BY_COMPONENT_VALUE = new HashMap<Component, Process>();

    static {
        for (Process process : values()) {
            BY_COMPONENT_VALUE.put(process.getComponent(), process);
        }
    }

    public static Process getByComponentValue(final Component value) {
        return BY_COMPONENT_VALUE.get(value);
    }

    private final Component component;

    Process(Component component) {
        this.component = component;
    }

    public Component getComponent() {
        return component;
    }

    public abstract void action();

}
