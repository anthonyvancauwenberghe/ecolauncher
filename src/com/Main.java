package com;

import com.frame.GUI;
import com.logging.Logger;
import com.path.Environment;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.skin.RavenSkin;
import org.jvnet.substance.skin.SubstanceCremeLookAndFeel;
import org.jvnet.substance.watermark.SubstanceKatakanaWatermark;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;

/**
 *
 * @author Daniel
 */
public class Main {

    private static Main instance;

    public static Main getInstance() {
        return instance != null ? instance : (instance = new Main());
    }

    public static void main(String[] argv) {
        Main.getInstance();
    }

    private Main() {
        try {
            UIManager.setLookAndFeel(new SubstanceCremeLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.log(Main.class, Level.SEVERE, "Error Setting Look and Feel", ex);
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        JPopupMenu.setDefaultLightWeightPopupEnabled(true);
        SubstanceLookAndFeel.setSkin(new RavenSkin());
        SubstanceLookAndFeel.setCurrentWatermark(new SubstanceKatakanaWatermark());
        initialize();
    }

    private void initialize() {
        Environment.getInstance().create();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                invokable();
            }
        });
    }

    private void invokable() {
        GUI.getInstance().getRefreshButton().doClick();
        GUI.getInstance().setVisible(true);
    }

}
