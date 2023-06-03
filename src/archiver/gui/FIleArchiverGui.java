package archiver.gui;

import archiver.operations.AddZipOperation;
import archiver.operations.CreateZipOperation;

import javax.swing.*;
import java.nio.file.Path;


public class FIleArchiverGui extends JFrame{
    private JPanel panel;
    private JButton createZipButton;
    private JButton addButton;
    private JFileChooser fileChooser;


    FIleArchiverGui() {
        this.setTitle("File Archiver");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(250, 500, 300, 200);
        this.setContentPane(panel);
        this.setResizable(false);

        createZipButton.addActionListener(e -> {
            fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.showOpenDialog(this);
            Path destination = fileChooser.getSelectedFile().toPath();
            fileChooser.showOpenDialog(this);
            Path source = fileChooser.getSelectedFile().toPath();
            try {
                new CreateZipOperation(destination).createZip(source);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        addButton.addActionListener(e -> {
            fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.showOpenDialog(this);
            Path destination = fileChooser.getSelectedFile().toPath();
            fileChooser.showOpenDialog(this);
            Path source = fileChooser.getSelectedFile().toPath();
            try {
                new AddZipOperation(destination).addFile(source);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        this.setVisible(true);
    }

}
