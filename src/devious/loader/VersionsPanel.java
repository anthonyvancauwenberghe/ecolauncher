package devious.loader;

import devious.loader.res.Res;
import devious.loader.updater.ClientUpdater;
import devious.loader.updater.ClientVersion;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class VersionsPanel extends JPanel implements ActionListener {

    private final JButton refreshButton;

    private final JComboBox nameBox;
    private final DefaultComboBoxModel nameModel;

    private final JButton playButton;

    private List<ClientVersion> list;

    private final Loader loader;

    public VersionsPanel(final Loader loader) {
        super(new BorderLayout(5, 0));
        setBorder(new TitledBorder("Launch Client"));
        this.loader = loader;

        refreshButton = new JButton(Res.REFRESH_16);
        refreshButton.setPreferredSize(new Dimension(50, 50));
        refreshButton.addActionListener(this);

        nameModel = new DefaultComboBoxModel();
        nameModel.addElement("--- No Clients Available ---");

        nameBox = new JComboBox(nameModel);
        nameBox.setEnabled(false);

        final JLabel nameLabel = new JLabel("Name:", JLabel.RIGHT);
        nameLabel.setPreferredSize(new Dimension(40, 10));

        final JPanel namePanel = new JPanel(new BorderLayout(5, 0));
        namePanel.add(nameLabel, BorderLayout.WEST);
        namePanel.add(nameBox, BorderLayout.CENTER);

        final JPanel versionPanel = new JPanel(new BorderLayout(5, 0));

        final JPanel boxPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        boxPanel.add(namePanel);
        boxPanel.add(versionPanel);

        playButton = new JButton(Res.PLAY_16);
        playButton.setPreferredSize(new Dimension(playButton.getPreferredSize().width, 50));
        playButton.addActionListener(this);

        add(refreshButton, BorderLayout.WEST);
        add(boxPanel, BorderLayout.CENTER);
        add(playButton, BorderLayout.SOUTH);

        refreshButton.doClick();
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final Object source = e.getSource();
        if(source.equals(refreshButton)){
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    refreshButton.setEnabled(false);
                    refreshButton.repaint();
                    nameModel.removeAllElements();
                    nameModel.addElement("--- No Names ---");
                    nameBox.setEnabled(false);
                    nameBox.repaint();
                }
            });
            try{
                list = ClientUpdater.loadRemoteVersions();
                for(int i = 0; i < list.size(); i++){
                    final int fi = i;
                    final ClientVersion v = list.get(i);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            if(fi == 0){
                                nameModel.removeAllElements();
                                nameBox.setEnabled(true);
                            }
                            nameModel.addElement(v.name);
                            nameBox.repaint();
                        }
                    });

                }
            }catch(Exception ex){
                ex.printStackTrace();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JOptionPane.showMessageDialog(null, "Error refreshing versions");
                    }
                });
            }
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    refreshButton.setEnabled(true);
                    refreshButton.repaint();
                }
            });
        }else if(source.equals(playButton)){
            final String name = (String) nameBox.getSelectedItem();
            if(list == null || name == null || !nameBox.isEnabled()){
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JOptionPane.showMessageDialog(null, "No client selected!");
                    }
                });
                return;
            }
            ClientVersion cv = null;
            for(final ClientVersion c : list){
                if(c.name.equals(name)){
                    cv = c;
                    break;
                }
            }
            if(cv == null){
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JOptionPane.showMessageDialog(null, "Invalid client version");
                    }
                });
                return;
            }
            try{
                final File jar = new File(ClientUpdater.CLIENTS_DIR, cv.fileName);
                if(!jar.exists())
                    ClientUpdater.download(cv);
                ClientStarter.start(jar);
                loader.dispose();
            }catch(Exception ex){
                ex.printStackTrace();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JOptionPane.showMessageDialog(null, "Error starting client");
                    }
                });
            }
        }
    }
}
