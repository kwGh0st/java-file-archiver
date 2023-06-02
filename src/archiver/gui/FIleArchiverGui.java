package archiver.gui;

import archiver.operations.CreateZipOperation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FIleArchiverGui extends JFrame{
    private JPanel panel;
    private JTextField textField1;
    private JTextField textField2;
    private JButton createZip;
    private JLabel label1;
    private JLabel label2;

    FIleArchiverGui() {
        this.setTitle("File Archiver");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(250, 500, 300, 200);
        this.setContentPane(panel);
        createZip.addActionListener(e -> {
            Path destinationPath = Paths.get(textField1.getText());
            Path sourcePath = Paths.get(textField2.getText());
            try {
                new CreateZipOperation(destinationPath).createZip(sourcePath);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        this.setVisible(true);
    }

}
