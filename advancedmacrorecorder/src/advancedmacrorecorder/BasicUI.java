package advancedmacrorecorder;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

public class BasicUI {
    public static void main(String[] args) {
        // Ensure the UI is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        // Create the frame
        JFrame frame = new JFrame("Advanced Macro Recorder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        // Create a panel to hold components
        JPanel panel = new JPanel();

        // Create a label
        JLabel label = new JLabel("Ready to record...");

        // Create a record button
        JButton recordButton = new JButton("Record");
        JButton stoprecording = new JButton("Stop Recording");
        // Add the label and button to the panel
        panel.add(label);
        panel.add(recordButton);
        panel.add(label);
        panel.add(stoprecording);
        // Create a text area placeholder for recorded actions
        JTextArea recordedActionsArea = new JTextArea(5, 25); // Adjusted size
        recordedActionsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(recordedActionsArea);

        // Create a sub-panel for the text area and add it to the main panel
        JPanel textAreaPanel = new JPanel();
        textAreaPanel.add(scrollPane);
        	
        // Add the panel and text area panel to the frame
        frame.add(panel, BorderLayout.NORTH);
        frame.add(textAreaPanel, BorderLayout.CENTER);

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        // Display the window
        frame.setVisible(true);

        // Add button click listener
        recordButton.addActionListener(e -> label.setText("BUTTON CLICKED"));
    }
}
