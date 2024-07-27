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
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Dimension;
//import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
//import java.awt.Desktop;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BasicUI {
    private static boolean isActive = false; // Flag to track recording status
    private static boolean isPlaybackActive = false; // Flag to track playback status
    private static ImageIcon idleIcon;
    private static ImageIcon activeIcon;
    private static ImageIcon playbackIcon;
    private static JTextArea logTextArea = new JTextArea(10, 50); // Text area for logs

    static {
        try {
            Image idleImg = ImageIO.read(new File("resources/idle_icon.png"));
            Image activeImg = ImageIO.read(new File("resources/active_icon.png"));
            Image playbackImg = ImageIO.read(new File("resources/playback_icon.png"));
            idleIcon = new ImageIcon(idleImg);
            activeIcon = new ImageIcon(activeImg);
            playbackIcon = new ImageIcon(playbackImg);
        } catch (IOException e) {
            logInternal("Error loading icons: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        logInternal("Application started.");
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
        JSpinner loopsSpinner = createIntegerSpinner(0, 0, Integer.MAX_VALUE, 1);
        int spinValue = 0; // Used to reset the spinner

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

        // Add the log text area with scroll pane
        JScrollPane logScrollPane = new JScrollPane(logTextArea);
        logTextArea.setEditable(false);
        JPanel logPanel = new JPanel();
        logPanel.setLayout(new BorderLayout());
        logPanel.add(logScrollPane, BorderLayout.CENTER);
        
        // Create the "SHOW LOG FILE" button and center it
        JButton showLogButton = new JButton("SHOW LOG FILE");
        showLogButton.addActionListener(e -> {
            logInternal("Log file shown.");
            JFrame logFrame = new JFrame("Log");
            logFrame.setSize(600, 300);
            logFrame.add(logPanel);
            logFrame.setVisible(true);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(showLogButton);
        buttonPanel.add(Box.createHorizontalGlue());

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
        topPanel.add(buttonPanel); // Add the centered button panel

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
        JSpinner SpeedMultiplierSpinner = new JSpinner(new SpinnerNumberModel(100, 1, 500, 1));
        SpeedMultiplierSpinner.setPreferredSize(new Dimension(60, 20)); // Adjust size
        speedMultiplierPanel.add(SpeedMultiplierLabel);
        speedMultiplierPanel.add(SpeedMultiplierSpinner);
        
        JPanel randomizePanel = new JPanel();
        JLabel randomizeClickLabel = new JLabel("Randomize click locations ±:");
        JSpinner randomizeClickSpinner = createIntegerSpinner(1, 1, 20, 1);
        randomizePanel.add(randomizeClickLabel);
        randomizePanel.add(randomizeClickSpinner);
        
        JPanel extraDelayPanel = new JPanel();
        JLabel extraDelayLabel = new JLabel("Extra delay/Breaks after each playback in seconds:");
        extraDelayPanel.add(extraDelayLabel);

        JPanel minDelayPanel = new JPanel();
        JLabel minDelayLabel = new JLabel("Min:");
        JSpinner minDelaySpinner = createIntegerSpinner(0, 0, 9999, 1);
        minDelayPanel.add(minDelayLabel);
        minDelayPanel.add(minDelaySpinner);
        
        JPanel maxDelayPanel = new JPanel();
        JLabel maxDelayLabel = new JLabel("Max:");
        JSpinner maxDelaySpinner = createIntegerSpinner(0, 0, 9999, 1);
        maxDelayPanel.add(maxDelayLabel);
        maxDelayPanel.add(maxDelaySpinner);
        
        JPanel targetDelayPanel = new JPanel();
        JLabel targetDelayLabel = new JLabel("Target:");
        JSpinner targetDelaySpinner = createIntegerSpinner(0, 0, 9999, 1);
        targetDelayPanel.add(targetDelayLabel);
        targetDelayPanel.add(targetDelaySpinner);
        
        JPanel focusDelayPanel = new JPanel();
        JLabel focusDelayLabel = new JLabel("Focus:");
        JSpinner focusDelaySpinner = createIntegerSpinner(0, 0, 9999, 1);
        focusDelayPanel.add(focusDelayLabel);
        focusDelayPanel.add(focusDelaySpinner);

        advancedPanel.add(speedMultiplierPanel);
        advancedPanel.add(randomizePanel);
        advancedPanel.add(extraDelayPanel);
        advancedPanel.add(minDelayPanel);
        advancedPanel.add(maxDelayPanel);
        advancedPanel.add(targetDelayPanel);
        advancedPanel.add(focusDelayPanel);
        
        // Add the advanced panel to the tabbed pane
        tabbedPane.addTab("Advanced", advancedPanel);

        // Create the break panel
        JPanel breakPanel = new JPanel();
        breakPanel.setLayout(new BoxLayout(breakPanel, BoxLayout.Y_AXIS));
        
        JPanel breakMinPanel = new JPanel();
        JLabel breakMinLabel = new JLabel("Min:");
        JSpinner breakMinSpinner = createIntegerSpinner(0, 0, 9999, 1);
        breakMinPanel.add(breakMinLabel);
        breakMinPanel.add(breakMinSpinner);
        
        JPanel breakMaxPanel = new JPanel();
        JLabel breakMaxLabel = new JLabel("Max:");
        JSpinner breakMaxSpinner = createIntegerSpinner(0, 0, 9999, 1);
        breakMaxPanel.add(breakMaxLabel);
        breakMaxPanel.add(breakMaxSpinner);
        
        JPanel breakTargetPanel = new JPanel();
        JLabel breakTargetLabel = new JLabel("Target:");
        JSpinner breakTargetSpinner = createIntegerSpinner(0, 0, 9999, 1);
        breakTargetPanel.add(breakTargetLabel);
        breakTargetPanel.add(breakTargetSpinner);
        
        JPanel breakFocusPanel = new JPanel();
        JLabel breakFocusLabel = new JLabel("Focus:");
        JSpinner breakFocusSpinner = createIntegerSpinner(0, 0, 9999, 1);
        breakFocusPanel.add(breakFocusLabel);
        breakFocusPanel.add(breakFocusSpinner);
        
        JButton addBreakButton = new JButton("ADD BREAK");
        addBreakButton.addActionListener(e -> logInternal("Break added."));

        breakPanel.add(breakMinPanel);
        breakPanel.add(breakMaxPanel);
        breakPanel.add(breakTargetPanel);
        breakPanel.add(breakFocusPanel);
        breakPanel.add(addBreakButton);
        
        // Add the break panel to the tabbed pane
        tabbedPane.addTab("Break", breakPanel);

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
        saveItem.addActionListener(e -> logInternal("Settings saved"));
        loadItem.addActionListener(e -> logInternal("Settings loaded"));
        clearItem.addActionListener(e -> {
            nameInput.setText("");
            descriptionInput.setText("");
            loopsSpinner.setValue(spinValue);
            functionRecComboBox.setSelectedItem("F6");
            functionPlayComboBox.setSelectedItem("F7");
            functionSplitComboBox.setSelectedItem("F8");
            stateLabel.setText("IDLE");
            stateLabel.setForeground(java.awt.Color.BLACK);
            frame.setIconImage(idleIcon.getImage());
            recordedKeysPlaceholder.setText("");
            isActive = false;
            isPlaybackActive = false;
            logInternal("Application cleared.");
        });
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
                    int selectedRecKeyCode = getKeyCode((String) functionRecComboBox.getSelectedItem());
                    int selectedPlayKeyCode = getKeyCode((String) functionPlayComboBox.getSelectedItem());
                    if (e.getKeyCode() == selectedRecKeyCode) {
                        // Toggle recording state
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
                        logInternal("Recording state changed: " + (isActive ? "RECORDING" : "IDLE"));
                    } else if (e.getKeyCode() == selectedPlayKeyCode) {
                        // Toggle playback state
                        if (isPlaybackActive) {
                            stateLabel.setText("IDLE");
                            stateLabel.setForeground(java.awt.Color.BLACK);
                            frame.setIconImage(idleIcon.getImage());
                        } else {
                            stateLabel.setText("PLAYBACK");
                            stateLabel.setForeground(java.awt.Color.GREEN);
                            frame.setIconImage(playbackIcon.getImage());
                        }
                        isPlaybackActive = !isPlaybackActive; // Toggle the flag
                        logInternal("Playback state changed: " + (isPlaybackActive ? "PLAYBACK" : "IDLE"));
                    }
                }
                return false;
            }
        });

        // Display the window
        frame.setVisible(true);
    }

    // Method to create integer spinner without decimal points
    private static JSpinner createIntegerSpinner(int value, int min, int max, int step) {
        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
        JSpinner spinner = new JSpinner(model);
        JFormattedTextField textField = ((JSpinner.NumberEditor) spinner.getEditor()).getTextField();
        textField.setColumns(4); // Adjust the number of columns to fit your layout
        textField.setEditable(true);
        return spinner;
    }

    // Method to log messages internally
    private static void logInternal(String message) {
        logTextArea.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " - " + message + "\n");
        logTextArea.setCaretPosition(logTextArea.getDocument().getLength()); // Scroll to the bottom
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
