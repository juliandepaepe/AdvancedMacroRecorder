package advancedmacrorecorder;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BasicUI {
    private static boolean isActive = false; // Flag to track status
    private static ImageIcon idleIcon;
    private static ImageIcon activeIcon;

    static {
        try {
            Image idleImg = ImageIO.read(new File("resources/idle_icon.png"));
            Image activeImg = ImageIO.read(new File("resources/active_icon.png"));
            idleIcon = new ImageIcon(idleImg);
            activeIcon = new ImageIcon(activeImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        frame.setSize(600, 500);
        frame.setIconImage(idleIcon.getImage());

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
        JLabel loopsLabel = new JLabel("Loops:");
        JSpinner loopsSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        int spinValue = 0;
        // Get the editor from the spinner
        JSpinner.NumberEditor editor = (JSpinner.NumberEditor) loopsSpinner.getEditor();
        JFormattedTextField textField = editor.getTextField();
        // Set preferred size of the text field
        textField.setPreferredSize(new Dimension(60, 20)); // Width x Height
        // Ensure the spinner itself respects this size
        loopsSpinner.setPreferredSize(new Dimension(100, 20)); // Width x Height
        
        mainPanel.add(nameLabel);
        mainPanel.add(nameInput);
        mainPanel.add(descriptionLabel);
        mainPanel.add(descriptionInput);
        System.out.println(getCurrentDate());
        
        JPanel loopsPanel = new JPanel();
        loopsPanel.add(loopsLabel);
        loopsPanel.add(loopsSpinner);
        
        // Create a sub-panel for the text area and add it to the main panel
        JPanel fkeyPanel = new JPanel();
        // Create and add the drop-down list
        JLabel functionRecLabel = new JLabel("Start/stop recording");
        JLabel functionPlayLabel = new JLabel("Start/stop playback");
        JLabel functionSplitLabel = new JLabel("Split recording");
        String[] functionKeys = {"F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12"};
        JComboBox<String> functionRecComboBox = new JComboBox<>(functionKeys);
        JComboBox<String> functionPlayComboBox = new JComboBox<>(functionKeys);
        JComboBox<String> functionSplitComboBox = new JComboBox<>(functionKeys);
        functionRecComboBox.setSelectedItem("F6");
        functionPlayComboBox.setSelectedItem("F7");
        functionSplitComboBox.setSelectedItem("F8");
        fkeyPanel.add(functionRecLabel);
        fkeyPanel.add(functionRecComboBox);
        fkeyPanel.add(functionPlayLabel);
        fkeyPanel.add(functionPlayComboBox);
        fkeyPanel.add(functionSplitLabel);
        fkeyPanel.add(functionSplitComboBox);
        
        JLabel dateLabel = new JLabel(getCurrentDate()); // Add date label
        JPanel datePanel = new JPanel();
        datePanel.add(dateLabel);
        
        JLabel statusLabel = new JLabel("Status: ");
        JLabel stateLabel = new JLabel("IDLE");
        stateLabel.setForeground(java.awt.Color.BLACK); // Ensure initial state is black
        JPanel statusPanel = new JPanel();
        statusPanel.add(statusLabel);
        statusPanel.add(stateLabel);
        
        // Placeholder for total duration
        JLabel totalDurationLabel = new JLabel("Total duration: "); // Add total duration label
        JLabel timeLabel = new JLabel("00:00:00");
        JPanel durationPanel = new JPanel();
        durationPanel.add(totalDurationLabel);
        durationPanel.add(timeLabel);
        // Placeholder for recorded keys with scroll functionality
        JTextArea recordedKeysPlaceholder = new JTextArea(5, 20); // Adjust size as needed
        recordedKeysPlaceholder.setEditable(false);
        recordedKeysPlaceholder.setBackground(java.awt.Color.WHITE); // Set background color to white
        recordedKeysPlaceholder.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK)); // Add a border
        
        // Wrap the text area in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(recordedKeysPlaceholder);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel recordedKeysPanel = new JPanel();
        recordedKeysPanel.add(scrollPane);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(mainPanel);
        topPanel.add(loopsPanel);
        topPanel.add(fkeyPanel);
        
        JPanel centeredPanel = new JPanel();
        centeredPanel.setLayout(new BoxLayout(centeredPanel, BoxLayout.X_AXIS));
        centeredPanel.add(Box.createHorizontalGlue()); // Add left spacer
        centeredPanel.add(dateLabel);
        centeredPanel.add(Box.createHorizontalGlue()); // Add right spacer
        topPanel.add(new JSeparator(JSeparator.HORIZONTAL));
        topPanel.add(centeredPanel);
        topPanel.add(statusPanel);
        topPanel.add(durationPanel); // Add the duration panel
        topPanel.add(recordedKeysPanel); // Add the recorded keys panel
        
        // Add the main panel and text area panel to a new main container panel
        JPanel mainContainerPanel = new JPanel(new BorderLayout());
        mainContainerPanel.add(topPanel, BorderLayout.NORTH);
        
        // Add the main container panel to the tabbed pane
        tabbedPane.addTab("Main", mainContainerPanel);

        // Create the advanced panel
        JPanel advancedPanel = new JPanel();
        advancedPanel.setLayout(new BoxLayout(advancedPanel, BoxLayout.Y_AXIS));
        
        JPanel speedMultiplierPanel = new JPanel();
        JLabel SpeedMultiplierLabel = new JLabel("Speed Multiplier%:");
        JSpinner SpeedMultiplierSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 500, 1));
        SpeedMultiplierSpinner.setPreferredSize(new Dimension(60, 20)); // Adjust size
        speedMultiplierPanel.add(SpeedMultiplierLabel);
        speedMultiplierPanel.add(SpeedMultiplierSpinner);
        
        JPanel randomizePanel = new JPanel();
        JLabel randomizeClickLabel = new JLabel("Randomize click locations ±:");
        JSpinner randomizeClickSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        randomizePanel.add(randomizeClickLabel);
        randomizePanel.add(randomizeClickSpinner);
        
        JPanel extraDelayPanel = new JPanel();
        JLabel extraDelayLabel = new JLabel("Extra delay/Breaks after each playback in seconds:");
        extraDelayPanel.add(extraDelayLabel);
        JPanel minDelayPanel = new JPanel();
        JLabel minDelayLabel = new JLabel("min:");
        minDelayPanel.add(minDelayLabel);
        advancedPanel.add(speedMultiplierPanel);
        advancedPanel.add(randomizePanel);
        advancedPanel.add(extraDelayPanel);
        advancedPanel.add(minDelayPanel);
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
        saveItem.addActionListener(e -> System.out.println("Settings saved"));
        loadItem.addActionListener(e -> System.out.println("Settings loaded"));
        clearItem.addActionListener(e -> nameInput.setText(""));
        clearItem.addActionListener(e -> descriptionInput.setText(""));
        clearItem.addActionListener(e -> loopsSpinner.setValue(spinValue));
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(clearItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
        
        // Add KeyEventDispatcher to capture key events
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    int selectedKeyCode = getKeyCode((String) functionRecComboBox.getSelectedItem());
                    if (e.getKeyCode() == selectedKeyCode) {
                        // Toggle state
                        if (isActive) {
                            stateLabel.setText("IDLE");
                            stateLabel.setForeground(java.awt.Color.BLACK);
                            frame.setIconImage(idleIcon.getImage());
                        } else {
                            stateLabel.setText("RECORDING");
                            stateLabel.setForeground(java.awt.Color.RED);
                            frame.setIconImage(activeIcon.getImage());
                        }
                        isActive = !isActive; // Toggle the flag
                    }
                }
                return false;
            }
        });

        // Display the window
        frame.setVisible(true);
    }
    
    private static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Date format
        return sdf.format(new Date());
    }
    
    private static int getKeyCode(String functionKey) {
        switch (functionKey) {
            case "F1": return KeyEvent.VK_F1;
            case "F2": return KeyEvent.VK_F2;
            case "F3": return KeyEvent.VK_F3;
            case "F4": return KeyEvent.VK_F4;
            case "F5": return KeyEvent.VK_F5;
            case "F6": return KeyEvent.VK_F6;
            case "F7": return KeyEvent.VK_F7;
            case "F8": return KeyEvent.VK_F8;
            case "F9": return KeyEvent.VK_F9;
            case "F10": return KeyEvent.VK_F10;
            case "F11": return KeyEvent.VK_F11;
            case "F12": return KeyEvent.VK_F12;
            default: return -1;
        }
    }
}
