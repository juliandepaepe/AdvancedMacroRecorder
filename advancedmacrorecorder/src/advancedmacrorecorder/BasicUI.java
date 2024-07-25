package advancedmacrorecorder;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
//import javax.swing.JScrollPane;
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
     // Create a text area placeholder for recorded actions
        JTextArea nameInput = new JTextArea(1, 10); // Adjusted size
        nameInput.setEditable(true);
        
        JTextArea descriptionInput = new JTextArea(1, 10); // Adjusted size
        descriptionInput.setEditable(true);
        
        
        JLabel nameLabel = new JLabel("Name");
        JLabel descriptionLabel = new JLabel("Description");
        JButton recordButton = new JButton("Record");
        //JButton stopRecordingButton = new JButton("Stop Recording");
        JLabel loopsLabel = new JLabel("Loops:");
        JSpinner loopsSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        mainPanel.add(nameLabel);
        mainPanel.add(nameInput);
        mainPanel.add(descriptionLabel);
        mainPanel.add(descriptionInput);
        System.out.println("test");
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loopsLabel);
        buttonPanel.add(loopsSpinner);
        
        // Create a sub-panel for the text area and add it to the main panel
        JPanel textAreaPanel = new JPanel();
        //textAreaPanel.add(scrollPane);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(mainPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the main panel and text area panel to a new main container panel
        JPanel mainContainerPanel = new JPanel(new BorderLayout());
        mainContainerPanel.add(topPanel, BorderLayout.NORTH);
        
        mainContainerPanel.add(textAreaPanel, BorderLayout.CENTER);

        // Add the main container panel to the tabbed pane
        tabbedPane.addTab("Main", mainContainerPanel);

        // Create the advanced panel
        JPanel advancedPanel = new JPanel();
        JLabel SpeedMultiplierLabel = new JLabel("Speed Multiplier%:");
        JSpinner SpeedMultiplierSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

        

        advancedPanel.add(SpeedMultiplierLabel);
        advancedPanel.add(SpeedMultiplierSpinner);

        // Add the advanced panel to the tabbed pane
        tabbedPane.addTab("Advanced", advancedPanel);

        // Add the tabbed pane to the frame
        frame.add(tabbedPane);

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem loadItem = new JMenuItem("Load");
        JMenuItem clearItem = new JMenuItem("Clear");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(clearItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        // Display the window
        frame.setVisible(true);

        // Add button click listener
        //recordButton.addActionListener(e -> label.setText("BUTTON CLICKED"));
        //stopRecordingButton.addActionListener(e -> label.setText("stop recording"));
    }
}
