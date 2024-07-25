package advancedmacrorecorder;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
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

        // Create a tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Create the main panel
        JPanel mainPanel = new JPanel();
        JLabel label = new JLabel("Ready to record...");
        JButton recordButton = new JButton("Record");
        JButton stopRecordingButton = new JButton("Stop Recording");
        mainPanel.add(recordButton);
        mainPanel.add(label);
        mainPanel.add(stopRecordingButton);

        // Create a text area placeholder for recorded actions
        JTextArea recordedActionsArea = new JTextArea(5, 25); // Adjusted size
        recordedActionsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(recordedActionsArea);

        // Create a sub-panel for the text area and add it to the main panel
        JPanel textAreaPanel = new JPanel();
        textAreaPanel.add(scrollPane);

        // Add the main panel and text area panel to a new main container panel
        JPanel mainContainerPanel = new JPanel(new BorderLayout());
        mainContainerPanel.add(mainPanel, BorderLayout.NORTH);
        mainContainerPanel.add(textAreaPanel, BorderLayout.CENTER);

        // Add the main container panel to the tabbed pane
        tabbedPane.addTab("Main", mainContainerPanel);

        // Create the advanced panel
        JPanel advancedPanel = new JPanel();
        JLabel loopsLabel = new JLabel("Loops:");
        JSpinner loopsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        advancedPanel.add(loopsLabel);
        advancedPanel.add(loopsSpinner);

        // Add the advanced panel to the tabbed pane
        tabbedPane.addTab("Advanced", advancedPanel);

        // Add the tabbed pane to the frame
        frame.add(tabbedPane);

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
