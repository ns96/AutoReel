package autoreel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

/**
 * The UI for the the autoreel program which controls R2R tape decks
 * GX77/Teac X7R/X10R Remote Commands:
   STOP - 0
   PLAY - 1
   REW - 2
   FF - 3
   PAUSE - 4
   REC - 5
   REVERSE - 6
 * 
 * @author Nathan Stevens
 */
public class AutoReelFrame extends javax.swing.JFrame {
    private AutoReel autoReel;
    private int connectIndex1 = -1;
    private int connectIndex2 = -1;
    private int connectIndex3 = -1;
    private int connectIndex4 = -1;
    private int connectIndex5 = -1;
    private int connectIndex6 = -1;
    
    // the names of the r2r decks
    private String deckName1 = "R2R #1";
    private String deckName2 = "R2R #2";
    private String deckName3 = "R2R #3";
    private String deckName4 = "R2R #4";
    private String deckName5 = "R2R #5";
    private String deckName6 = "R2R #6";
    
    // the timers for stoping the timer
    private Timer timer1;
    private Timer timer2;
    private Timer timer3;
    private Timer timer4;
    private Timer timer5;
    private Timer timer6;
    
    // String to keep track of direction we are playing
    private String prefix1 = "+";
    private String prefix2 = "+";
    private String prefix3 = "+";
    private String prefix4 = "+";
    private String prefix5 = "+";
    private String prefix6 = "+";
    
    // keep track of if we are to stop auto play
    private boolean stopAutoPlay1 = false;
    private boolean stopAutoPlay2 = false;
    private boolean stopAutoPlay3 = false;
    private boolean stopAutoPlay4 = false;
    private boolean stopAutoPlay5 = false;
    private boolean stopAutoPlay6 = false;
    
    /**
     * Creates new form AutoReelFrame
     */
    public AutoReelFrame() {
        initComponents();
        
        // check os and indicate if we runnning on windows to prevent very long
        // loading times
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {
            System.out.println("Running on Windows ...");
        }
    }
    
    /**
     * Set the auto reel object used to communicate with the serial ports
     * 
     * @param autoReel 
     */
    public void setAutoReel(AutoReel autoReel) {
        this.autoReel = autoReel;
        
        // set the comm ports
        setCommPorts(autoReel.getPorts(false));
        
        // update the UI with values from the properties files
        updateDeckCommPort();
        updateDeckParameters();
    }
    
    /**
     * Set the comm port for the decks
     */
    private void updateDeckCommPort() {
        // update the comm port for the particular deck
        String key = "deck1.comm";
        Object commPort = autoReel.properties.get(key);
        if(commPort != null)
            commPortComboBox1.setSelectedItem(commPort);
        
        key = "deck2.comm";
        commPort = autoReel.properties.get(key);
        if(commPort != null)
            commPortComboBox2.setSelectedItem(commPort);
        
        key = "deck3.comm";
        commPort = autoReel.properties.get(key);
        if(commPort != null)
            commPortComboBox3.setSelectedItem(commPort);
        
        key = "deck4.comm";
        commPort = autoReel.properties.get(key);
        if(commPort != null)
            commPortComboBox4.setSelectedItem(commPort);
        
        key = "deck5.comm";
        commPort = autoReel.properties.get(key);
        if(commPort != null)
            commPortComboBox5.setSelectedItem(commPort);
        
        key = "deck6.comm";
        commPort = autoReel.properties.get(key);
        if(commPort != null)
            commPortComboBox6.setSelectedItem(commPort);
    }
    
    /**
     * Update the R2R/Tape Deck Panels with name and values.
     * 
     * TO-DO 2/9/2023 Place this code is for loop to make it easier to add new decks
     */
    private void updateDeckParameters() {
        // values for deck 1
        TitledBorder border = (TitledBorder)deck1Panel.getBorder();
        String key = "deck1.aname";
        Object value = autoReel.properties.get(key);
        if(value != null) {
            deckName1 = value.toString();
            border.setTitle(deckName1);
            reel1CheckBox.setText(deckName1);
        }
        
        key = "deck1.time.start";
        value = autoReel.properties.get(key);
        if(value != null)
            startTimeTextField1.setText(value.toString());
        
        key = "deck1.time.end";
        value = autoReel.properties.get(key);
        if(value != null)
            endTimeTextField1.setText(value.toString());
        
        key = "deck1.time.rewind";
        value = autoReel.properties.get(key);
        if(value != null)
            rewindTimeTextField1.setText(value.toString());
        
        // values for deck 2
        border = (TitledBorder)deck2Panel.getBorder();
        key = "deck2.aname";
        value = autoReel.properties.get(key);
        if(value != null) {
            deckName2 = value.toString();
            border.setTitle(deckName2);
            reel2CheckBox.setText(deckName2);
        }
        
        key = "deck2.time.start";
        value = autoReel.properties.get(key);
        if(value != null)
            startTimeTextField2.setText(value.toString());
        
        key = "deck2.time.end";
        value = autoReel.properties.get(key);
        if(value != null)
            endTimeTextField2.setText(value.toString());
        
        key = "deck2.time.rewind";
        value = autoReel.properties.get(key);
        if(value != null)
            rewindTimeTextField2.setText(value.toString());
        
        // values for deck 3
        border = (TitledBorder)deck3Panel.getBorder();
        key = "deck3.aname";
        value = autoReel.properties.get(key);
        if(value != null) {
            deckName3 = value.toString();
            border.setTitle(deckName3);
            reel3CheckBox.setText(deckName3);
        }
        
        border = (TitledBorder)deck3Panel.getBorder();
        key = "deck3.aname";
        value = autoReel.properties.get(key);
        if(value != null)
            border.setTitle(value.toString());
        
        key = "deck3.time.start";
        value = autoReel.properties.get(key);
        if(value != null)
            startTimeTextField3.setText(value.toString());
        
        key = "deck3.time.end";
        value = autoReel.properties.get(key);
        if(value != null)
            endTimeTextField3.setText(value.toString());
        
        key = "deck3.time.rewind";
        value = autoReel.properties.get(key);
        if(value != null)
            rewindTimeTextField3.setText(value.toString());
        
        // values for deck 4
        border = (TitledBorder)deck4Panel.getBorder();
        key = "deck4.aname";
        value = autoReel.properties.get(key);
        if(value != null) {
            deckName4 = value.toString();
            border.setTitle(deckName4);
            reel4CheckBox.setText(deckName4);
        }
        
        key = "deck4.time.start";
        value = autoReel.properties.get(key);
        if(value != null)
            startTimeTextField4.setText(value.toString());
        
        key = "deck4.time.end";
        value = autoReel.properties.get(key);
        if(value != null)
            endTimeTextField4.setText(value.toString());
        
        key = "deck4.time.rewind";
        value = autoReel.properties.get(key);
        if(value != null)
            rewindTimeTextField4.setText(value.toString());
        
        // values for deck 5
        border = (TitledBorder)deck5Panel.getBorder();
        key = "deck5.aname";
        value = autoReel.properties.get(key);
        if(value != null) {
            deckName5 = value.toString();
            border.setTitle(deckName5);
            reel5CheckBox.setText(deckName5);
        }
        
        key = "deck5.time.start";
        value = autoReel.properties.get(key);
        if(value != null)
            startTimeTextField5.setText(value.toString());
        
        key = "deck5.time.end";
        value = autoReel.properties.get(key);
        if(value != null)
            endTimeTextField5.setText(value.toString());
        
        key = "deck5.time.rewind";
        value = autoReel.properties.get(key);
        if(value != null)
            rewindTimeTextField5.setText(value.toString());
        
        // values for deck 6
        border = (TitledBorder)deck6Panel.getBorder();
        key = "deck6.aname";
        value = autoReel.properties.get(key);
        if(value != null) {
            deckName6 = value.toString();
            border.setTitle(deckName6);
            reel6CheckBox.setText(deckName6);
        }
        
        key = "deck6.time.start";
        value = autoReel.properties.get(key);
        if(value != null)
            startTimeTextField6.setText(value.toString());
        
        key = "deck6.time.end";
        value = autoReel.properties.get(key);
        if(value != null)
            endTimeTextField6.setText(value.toString());
        
        key = "deck6.time.rewind";
        value = autoReel.properties.get(key);
        if(value != null)
            rewindTimeTextField6.setText(value.toString());
    }
    
    /**
     * Set the comm ports that will display in the UI
     * 
     * @param ports 
     */
    public void setCommPorts(Set<String> ports) {
        commPortComboBox1.removeAllItems();
        commPortComboBox2.removeAllItems();
        commPortComboBox3.removeAllItems();
        commPortComboBox4.removeAllItems();
        commPortComboBox5.removeAllItems();
        commPortComboBox6.removeAllItems();
        
        for(String port: ports) {
            commPortComboBox1.addItem(port);
            commPortComboBox2.addItem(port);
            commPortComboBox3.addItem(port);
            commPortComboBox4.addItem(port);
            commPortComboBox5.addItem(port);
            commPortComboBox6.addItem(port);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        closeButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        consoleTextArea = new javax.swing.JTextArea();
        clearConsoleButton = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        deck1Panel = new javax.swing.JPanel();
        connectButton1 = new javax.swing.JButton();
        commPortComboBox1 = new javax.swing.JComboBox<>();
        closePortButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        rewindButton1 = new javax.swing.JButton();
        stopButton1 = new javax.swing.JButton();
        forwardButton1 = new javax.swing.JButton();
        reversePlayButton1 = new javax.swing.JButton();
        timerLabel1 = new javax.swing.JLabel();
        forwardPlayButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        startTimeTextField1 = new javax.swing.JTextField();
        endTimeTextField1 = new javax.swing.JTextField();
        rewindTimeTextField1 = new javax.swing.JTextField();
        autoPlayButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        autoPlayEndComboBox1 = new javax.swing.JComboBox<>();
        reverseModeCheckBox1 = new javax.swing.JCheckBox();
        jLabel18 = new javax.swing.JLabel();
        recordToggleButton1 = new javax.swing.JToggleButton();
        deck2Panel = new javax.swing.JPanel();
        connectButton2 = new javax.swing.JButton();
        commPortComboBox2 = new javax.swing.JComboBox<>();
        closePortButton2 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        rewindButton2 = new javax.swing.JButton();
        stopButton2 = new javax.swing.JButton();
        forwardButton2 = new javax.swing.JButton();
        reversePlayButton2 = new javax.swing.JButton();
        timerLabel2 = new javax.swing.JLabel();
        forwardPlayButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        startTimeTextField2 = new javax.swing.JTextField();
        endTimeTextField2 = new javax.swing.JTextField();
        rewindTimeTextField2 = new javax.swing.JTextField();
        autoPlayButton2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        autoPlayEndComboBox2 = new javax.swing.JComboBox<>();
        reverseModeCheckBox2 = new javax.swing.JCheckBox();
        jLabel19 = new javax.swing.JLabel();
        recordToggleButton2 = new javax.swing.JToggleButton();
        jPanel7 = new javax.swing.JPanel();
        deck3Panel = new javax.swing.JPanel();
        connectButton3 = new javax.swing.JButton();
        commPortComboBox3 = new javax.swing.JComboBox<>();
        closePortButton3 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        rewindButton3 = new javax.swing.JButton();
        stopButton3 = new javax.swing.JButton();
        forwardButton3 = new javax.swing.JButton();
        reversePlayButton3 = new javax.swing.JButton();
        timerLabel3 = new javax.swing.JLabel();
        forwardPlayButton3 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        startTimeTextField3 = new javax.swing.JTextField();
        endTimeTextField3 = new javax.swing.JTextField();
        rewindTimeTextField3 = new javax.swing.JTextField();
        autoPlayButton3 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        autoPlayEndComboBox3 = new javax.swing.JComboBox<>();
        reverseModeCheckBox3 = new javax.swing.JCheckBox();
        jLabel20 = new javax.swing.JLabel();
        recordToggleButton3 = new javax.swing.JToggleButton();
        deck4Panel = new javax.swing.JPanel();
        connectButton4 = new javax.swing.JButton();
        commPortComboBox4 = new javax.swing.JComboBox<>();
        closePortButton4 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        rewindButton4 = new javax.swing.JButton();
        stopButton4 = new javax.swing.JButton();
        forwardButton4 = new javax.swing.JButton();
        reversePlayButton4 = new javax.swing.JButton();
        timerLabel4 = new javax.swing.JLabel();
        forwardPlayButton4 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        startTimeTextField4 = new javax.swing.JTextField();
        endTimeTextField4 = new javax.swing.JTextField();
        rewindTimeTextField4 = new javax.swing.JTextField();
        autoPlayButton4 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        autoPlayEndComboBox4 = new javax.swing.JComboBox<>();
        reverseModeCheckBox4 = new javax.swing.JCheckBox();
        jLabel21 = new javax.swing.JLabel();
        recordToggleButton4 = new javax.swing.JToggleButton();
        jPanel8 = new javax.swing.JPanel();
        deck5Panel = new javax.swing.JPanel();
        connectButton5 = new javax.swing.JButton();
        commPortComboBox5 = new javax.swing.JComboBox<>();
        closePortButton5 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        rewindButton5 = new javax.swing.JButton();
        stopButton5 = new javax.swing.JButton();
        forwardButton5 = new javax.swing.JButton();
        reversePlayButton5 = new javax.swing.JButton();
        timerLabel5 = new javax.swing.JLabel();
        forwardPlayButton5 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        startTimeTextField5 = new javax.swing.JTextField();
        endTimeTextField5 = new javax.swing.JTextField();
        rewindTimeTextField5 = new javax.swing.JTextField();
        autoPlayButton5 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        autoPlayEndComboBox5 = new javax.swing.JComboBox<>();
        reverseModeCheckBox5 = new javax.swing.JCheckBox();
        jLabel26 = new javax.swing.JLabel();
        recordToggleButton5 = new javax.swing.JToggleButton();
        deck6Panel = new javax.swing.JPanel();
        connectButton6 = new javax.swing.JButton();
        commPortComboBox6 = new javax.swing.JComboBox<>();
        closePortButton6 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        rewindButton6 = new javax.swing.JButton();
        stopButton6 = new javax.swing.JButton();
        forwardButton6 = new javax.swing.JButton();
        reversePlayButton6 = new javax.swing.JButton();
        timerLabel6 = new javax.swing.JLabel();
        forwardPlayButton6 = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        startTimeTextField6 = new javax.swing.JTextField();
        endTimeTextField6 = new javax.swing.JTextField();
        rewindTimeTextField6 = new javax.swing.JTextField();
        autoPlayButton6 = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        autoPlayEndComboBox6 = new javax.swing.JComboBox<>();
        reverseModeCheckBox6 = new javax.swing.JCheckBox();
        jLabel31 = new javax.swing.JLabel();
        recordToggleButton6 = new javax.swing.JToggleButton();
        jPanel11 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        reel1CheckBox = new javax.swing.JCheckBox();
        reel2CheckBox = new javax.swing.JCheckBox();
        reel3CheckBox = new javax.swing.JCheckBox();
        stopAllButton = new javax.swing.JButton();
        autoPlayAllButton = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        startDelayTextField = new javax.swing.JTextField();
        reel4CheckBox = new javax.swing.JCheckBox();
        reel5CheckBox = new javax.swing.JCheckBox();
        reel6CheckBox = new javax.swing.JCheckBox();
        setupButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AutoReel Controller v0.6.1 (03/06/2023)");

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Ouput Console"));

        consoleTextArea.setColumns(20);
        consoleTextArea.setRows(5);
        jScrollPane1.setViewportView(consoleTextArea);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        clearConsoleButton.setText("Clear Console");
        clearConsoleButton.setActionCommand("");
        clearConsoleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearConsoleButtonActionPerformed(evt);
            }
        });

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jPanel6.setLayout(new java.awt.GridLayout(2, 1, 0, 5));

        deck1Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("R2R #1"));

        connectButton1.setText("CONNECT TO COMM PORT");
        connectButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButton1ActionPerformed(evt);
            }
        });

        commPortComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "COMM PORT 1", "COMM PORT 2" }));

        closePortButton1.setText("CLOSE PORT");
        closePortButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closePortButton1ActionPerformed(evt);
            }
        });

        jPanel3.setLayout(new java.awt.GridLayout(6, 0));

        rewindButton1.setText("<<<");
        rewindButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rewindButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(rewindButton1);

        stopButton1.setText("STOP");
        stopButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(stopButton1);

        forwardButton1.setText(">>>");
        forwardButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(forwardButton1);

        reversePlayButton1.setText("< PLAY");
        reversePlayButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reversePlayButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(reversePlayButton1);

        timerLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        timerLabel1.setForeground(java.awt.Color.blue);
        timerLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timerLabel1.setText("00:00:00");
        jPanel3.add(timerLabel1);

        forwardPlayButton1.setText("PLAY >");
        forwardPlayButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardPlayButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(forwardPlayButton1);

        jLabel2.setText("START TIME");
        jPanel3.add(jLabel2);

        jLabel3.setText("END TIME");
        jPanel3.add(jLabel3);

        jLabel4.setText("REWIND TIME (seconds)");
        jPanel3.add(jLabel4);

        startTimeTextField1.setText("0");
        jPanel3.add(startTimeTextField1);

        endTimeTextField1.setText("3600");
        jPanel3.add(endTimeTextField1);

        rewindTimeTextField1.setText("126");
        rewindTimeTextField1.setToolTipText("");
        jPanel3.add(rewindTimeTextField1);

        autoPlayButton1.setText("START AUTO PLAY");
        autoPlayButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoPlayButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(autoPlayButton1);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("AT END OF PLAY: ");
        jPanel3.add(jLabel1);

        autoPlayEndComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "STOP", "REWIND", "PLAY REVERSE" }));
        jPanel3.add(autoPlayEndComboBox1);

        reverseModeCheckBox1.setText("Reverse Mode");
        reverseModeCheckBox1.setEnabled(false);
        jPanel3.add(reverseModeCheckBox1);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("SET RECORD MODE: ");
        jPanel3.add(jLabel18);

        recordToggleButton1.setText("RECORD");
        recordToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordToggleButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(recordToggleButton1);

        javax.swing.GroupLayout deck1PanelLayout = new javax.swing.GroupLayout(deck1Panel);
        deck1Panel.setLayout(deck1PanelLayout);
        deck1PanelLayout.setHorizontalGroup(
            deck1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deck1PanelLayout.createSequentialGroup()
                .addComponent(connectButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commPortComboBox1, 0, 1, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closePortButton1))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        deck1PanelLayout.setVerticalGroup(
            deck1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deck1PanelLayout.createSequentialGroup()
                .addGroup(deck1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connectButton1)
                    .addComponent(commPortComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closePortButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel6.add(deck1Panel);

        deck2Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("R2R #2"));

        connectButton2.setText("CONNECT TO COMM PORT");
        connectButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButton2ActionPerformed(evt);
            }
        });

        commPortComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "COMM PORT 1", "COMM PORT 2" }));

        closePortButton2.setText("CLOSE PORT");
        closePortButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closePortButton2ActionPerformed(evt);
            }
        });

        jPanel5.setLayout(new java.awt.GridLayout(6, 0));

        rewindButton2.setText("<<<");
        rewindButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rewindButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(rewindButton2);

        stopButton2.setText("STOP");
        stopButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(stopButton2);

        forwardButton2.setText(">>>");
        forwardButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(forwardButton2);

        reversePlayButton2.setText("< PLAY");
        reversePlayButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reversePlayButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(reversePlayButton2);

        timerLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        timerLabel2.setForeground(java.awt.Color.blue);
        timerLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timerLabel2.setText("00:00:00");
        jPanel5.add(timerLabel2);

        forwardPlayButton2.setText("PLAY >");
        forwardPlayButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardPlayButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(forwardPlayButton2);

        jLabel5.setText("START TIME");
        jPanel5.add(jLabel5);

        jLabel6.setText("END TIME");
        jPanel5.add(jLabel6);

        jLabel7.setText("REWIND TIME (seconds)");
        jPanel5.add(jLabel7);

        startTimeTextField2.setText("0");
        jPanel5.add(startTimeTextField2);

        endTimeTextField2.setText("3600");
        jPanel5.add(endTimeTextField2);

        rewindTimeTextField2.setText("105");
        jPanel5.add(rewindTimeTextField2);

        autoPlayButton2.setText("START AUTO PLAY");
        autoPlayButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoPlayButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(autoPlayButton2);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("AT END OF PLAY: ");
        jPanel5.add(jLabel8);

        autoPlayEndComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "STOP", "REWIND", "PLAY REVERSE" }));
        jPanel5.add(autoPlayEndComboBox2);

        reverseModeCheckBox2.setText("Reverse Mode");
        jPanel5.add(reverseModeCheckBox2);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("SET RECORD MODE: ");
        jPanel5.add(jLabel19);

        recordToggleButton2.setText("RECORD");
        recordToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordToggleButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(recordToggleButton2);

        javax.swing.GroupLayout deck2PanelLayout = new javax.swing.GroupLayout(deck2Panel);
        deck2Panel.setLayout(deck2PanelLayout);
        deck2PanelLayout.setHorizontalGroup(
            deck2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deck2PanelLayout.createSequentialGroup()
                .addComponent(connectButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commPortComboBox2, 0, 1, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closePortButton2))
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        deck2PanelLayout.setVerticalGroup(
            deck2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deck2PanelLayout.createSequentialGroup()
                .addGroup(deck2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connectButton2)
                    .addComponent(commPortComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closePortButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel6.add(deck2Panel);

        jTabbedPane1.addTab("Reel To Reels", jPanel6);

        jPanel7.setLayout(new java.awt.GridLayout(2, 1, 0, 5));

        deck3Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("R2R #3"));

        connectButton3.setText("CONNECT TO COMM PORT");
        connectButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButton3ActionPerformed(evt);
            }
        });

        commPortComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "COMM PORT 1", "COMM PORT 2" }));

        closePortButton3.setText("CLOSE PORT");
        closePortButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closePortButton3ActionPerformed(evt);
            }
        });

        jPanel9.setLayout(new java.awt.GridLayout(6, 0));

        rewindButton3.setText("<<<");
        rewindButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rewindButton3ActionPerformed(evt);
            }
        });
        jPanel9.add(rewindButton3);

        stopButton3.setText("STOP");
        stopButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButton3ActionPerformed(evt);
            }
        });
        jPanel9.add(stopButton3);

        forwardButton3.setText(">>>");
        forwardButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardButton3ActionPerformed(evt);
            }
        });
        jPanel9.add(forwardButton3);

        reversePlayButton3.setText("< PLAY");
        reversePlayButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reversePlayButton3ActionPerformed(evt);
            }
        });
        jPanel9.add(reversePlayButton3);

        timerLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        timerLabel3.setForeground(java.awt.Color.blue);
        timerLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timerLabel3.setText("00:00:00");
        jPanel9.add(timerLabel3);

        forwardPlayButton3.setText("PLAY >");
        forwardPlayButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardPlayButton3ActionPerformed(evt);
            }
        });
        jPanel9.add(forwardPlayButton3);

        jLabel9.setText("START TIME");
        jPanel9.add(jLabel9);

        jLabel10.setText("END TIME");
        jPanel9.add(jLabel10);

        jLabel11.setText("REWIND TIME (seconds)");
        jPanel9.add(jLabel11);

        startTimeTextField3.setText("0");
        startTimeTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startTimeTextField3ActionPerformed(evt);
            }
        });
        jPanel9.add(startTimeTextField3);

        endTimeTextField3.setText("4500");
        endTimeTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endTimeTextField3ActionPerformed(evt);
            }
        });
        jPanel9.add(endTimeTextField3);

        rewindTimeTextField3.setText("90");
        rewindTimeTextField3.setToolTipText("");
        jPanel9.add(rewindTimeTextField3);

        autoPlayButton3.setText("START AUTO PLAY");
        autoPlayButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoPlayButton3ActionPerformed(evt);
            }
        });
        jPanel9.add(autoPlayButton3);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("AT END OF PLAY: ");
        jPanel9.add(jLabel12);

        autoPlayEndComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "STOP", "REWIND", "PLAY REVERSE" }));
        jPanel9.add(autoPlayEndComboBox3);

        reverseModeCheckBox3.setText("Reverse Mode");
        jPanel9.add(reverseModeCheckBox3);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("SET RECORD MODE: ");
        jPanel9.add(jLabel20);

        recordToggleButton3.setText("RECORD");
        recordToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordToggleButton3ActionPerformed(evt);
            }
        });
        jPanel9.add(recordToggleButton3);

        javax.swing.GroupLayout deck3PanelLayout = new javax.swing.GroupLayout(deck3Panel);
        deck3Panel.setLayout(deck3PanelLayout);
        deck3PanelLayout.setHorizontalGroup(
            deck3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deck3PanelLayout.createSequentialGroup()
                .addComponent(connectButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commPortComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closePortButton3))
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        deck3PanelLayout.setVerticalGroup(
            deck3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deck3PanelLayout.createSequentialGroup()
                .addGroup(deck3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connectButton3)
                    .addComponent(commPortComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closePortButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        jPanel7.add(deck3Panel);

        deck4Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("R2R #4"));

        connectButton4.setText("CONNECT TO COMM PORT");
        connectButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButton4ActionPerformed(evt);
            }
        });

        commPortComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "COMM PORT 1", "COMM PORT 2" }));

        closePortButton4.setText("CLOSE PORT");
        closePortButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closePortButton4ActionPerformed(evt);
            }
        });

        jPanel13.setLayout(new java.awt.GridLayout(6, 0));

        rewindButton4.setText("<<<");
        rewindButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rewindButton4ActionPerformed(evt);
            }
        });
        jPanel13.add(rewindButton4);

        stopButton4.setText("STOP");
        stopButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButton4ActionPerformed(evt);
            }
        });
        jPanel13.add(stopButton4);

        forwardButton4.setText(">>>");
        forwardButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardButton4ActionPerformed(evt);
            }
        });
        jPanel13.add(forwardButton4);

        reversePlayButton4.setText("< PLAY");
        reversePlayButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reversePlayButton4ActionPerformed(evt);
            }
        });
        jPanel13.add(reversePlayButton4);

        timerLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        timerLabel4.setForeground(java.awt.Color.blue);
        timerLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timerLabel4.setText("00:00:00");
        jPanel13.add(timerLabel4);

        forwardPlayButton4.setText("PLAY >");
        forwardPlayButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardPlayButton4ActionPerformed(evt);
            }
        });
        jPanel13.add(forwardPlayButton4);

        jLabel14.setText("START TIME");
        jPanel13.add(jLabel14);

        jLabel15.setText("END TIME");
        jPanel13.add(jLabel15);

        jLabel16.setText("REWIND TIME (seconds)");
        jPanel13.add(jLabel16);

        startTimeTextField4.setText("0");
        jPanel13.add(startTimeTextField4);

        endTimeTextField4.setText("3600");
        endTimeTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endTimeTextField4ActionPerformed(evt);
            }
        });
        jPanel13.add(endTimeTextField4);

        rewindTimeTextField4.setText("75");
        rewindTimeTextField4.setToolTipText("");
        rewindTimeTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rewindTimeTextField4ActionPerformed(evt);
            }
        });
        jPanel13.add(rewindTimeTextField4);

        autoPlayButton4.setText("START AUTO PLAY");
        autoPlayButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoPlayButton4ActionPerformed(evt);
            }
        });
        jPanel13.add(autoPlayButton4);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("AT END OF PLAY: ");
        jPanel13.add(jLabel17);

        autoPlayEndComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "STOP", "REWIND", "PLAY REVERSE" }));
        jPanel13.add(autoPlayEndComboBox4);

        reverseModeCheckBox4.setText("Reverse Mode");
        jPanel13.add(reverseModeCheckBox4);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("SET RECORD MODE: ");
        jPanel13.add(jLabel21);

        recordToggleButton4.setText("RECORD");
        recordToggleButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordToggleButton4ActionPerformed(evt);
            }
        });
        jPanel13.add(recordToggleButton4);

        javax.swing.GroupLayout deck4PanelLayout = new javax.swing.GroupLayout(deck4Panel);
        deck4Panel.setLayout(deck4PanelLayout);
        deck4PanelLayout.setHorizontalGroup(
            deck4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deck4PanelLayout.createSequentialGroup()
                .addComponent(connectButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commPortComboBox4, 0, 234, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closePortButton4))
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        deck4PanelLayout.setVerticalGroup(
            deck4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deck4PanelLayout.createSequentialGroup()
                .addGroup(deck4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connectButton4)
                    .addComponent(commPortComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closePortButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel7.add(deck4Panel);

        jTabbedPane1.addTab("Reel To Reels", jPanel7);

        jPanel8.setLayout(new java.awt.GridLayout(2, 1, 0, 5));

        deck5Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("R2R #5"));

        connectButton5.setText("CONNECT TO COMM PORT");
        connectButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButton5ActionPerformed(evt);
            }
        });

        commPortComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "COMM PORT 1", "COMM PORT 2" }));

        closePortButton5.setText("CLOSE PORT");
        closePortButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closePortButton5ActionPerformed(evt);
            }
        });

        jPanel12.setLayout(new java.awt.GridLayout(6, 0));

        rewindButton5.setText("<<<");
        rewindButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rewindButton5ActionPerformed(evt);
            }
        });
        jPanel12.add(rewindButton5);

        stopButton5.setText("STOP");
        stopButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButton5ActionPerformed(evt);
            }
        });
        jPanel12.add(stopButton5);

        forwardButton5.setText(">>>");
        forwardButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardButton5ActionPerformed(evt);
            }
        });
        jPanel12.add(forwardButton5);

        reversePlayButton5.setText("< PLAY");
        reversePlayButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reversePlayButton5ActionPerformed(evt);
            }
        });
        jPanel12.add(reversePlayButton5);

        timerLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        timerLabel5.setForeground(java.awt.Color.blue);
        timerLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timerLabel5.setText("00:00:00");
        jPanel12.add(timerLabel5);

        forwardPlayButton5.setText("PLAY >");
        forwardPlayButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardPlayButton5ActionPerformed(evt);
            }
        });
        jPanel12.add(forwardPlayButton5);

        jLabel22.setText("START TIME");
        jPanel12.add(jLabel22);

        jLabel23.setText("END TIME");
        jPanel12.add(jLabel23);

        jLabel24.setText("REWIND TIME (seconds)");
        jPanel12.add(jLabel24);

        startTimeTextField5.setText("0");
        startTimeTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startTimeTextField5ActionPerformed(evt);
            }
        });
        jPanel12.add(startTimeTextField5);

        endTimeTextField5.setText("4500");
        endTimeTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endTimeTextField5ActionPerformed(evt);
            }
        });
        jPanel12.add(endTimeTextField5);

        rewindTimeTextField5.setText("90");
        rewindTimeTextField5.setToolTipText("");
        jPanel12.add(rewindTimeTextField5);

        autoPlayButton5.setText("START AUTO PLAY");
        autoPlayButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoPlayButton5ActionPerformed(evt);
            }
        });
        jPanel12.add(autoPlayButton5);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("AT END OF PLAY: ");
        jPanel12.add(jLabel25);

        autoPlayEndComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "STOP", "REWIND", "PLAY REVERSE" }));
        jPanel12.add(autoPlayEndComboBox5);

        reverseModeCheckBox5.setText("Reverse Mode");
        jPanel12.add(reverseModeCheckBox5);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("SET RECORD MODE: ");
        jPanel12.add(jLabel26);

        recordToggleButton5.setText("RECORD");
        recordToggleButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordToggleButton5ActionPerformed(evt);
            }
        });
        jPanel12.add(recordToggleButton5);

        javax.swing.GroupLayout deck5PanelLayout = new javax.swing.GroupLayout(deck5Panel);
        deck5Panel.setLayout(deck5PanelLayout);
        deck5PanelLayout.setHorizontalGroup(
            deck5PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deck5PanelLayout.createSequentialGroup()
                .addComponent(connectButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commPortComboBox5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closePortButton5))
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        deck5PanelLayout.setVerticalGroup(
            deck5PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deck5PanelLayout.createSequentialGroup()
                .addGroup(deck5PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connectButton5)
                    .addComponent(commPortComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closePortButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        jPanel8.add(deck5Panel);

        deck6Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("R2R #6"));

        connectButton6.setText("CONNECT TO COMM PORT");
        connectButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButton6ActionPerformed(evt);
            }
        });

        commPortComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "COMM PORT 1", "COMM PORT 2" }));

        closePortButton6.setText("CLOSE PORT");
        closePortButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closePortButton6ActionPerformed(evt);
            }
        });

        jPanel14.setLayout(new java.awt.GridLayout(6, 0));

        rewindButton6.setText("<<<");
        rewindButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rewindButton6ActionPerformed(evt);
            }
        });
        jPanel14.add(rewindButton6);

        stopButton6.setText("STOP");
        stopButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButton6ActionPerformed(evt);
            }
        });
        jPanel14.add(stopButton6);

        forwardButton6.setText(">>>");
        forwardButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardButton6ActionPerformed(evt);
            }
        });
        jPanel14.add(forwardButton6);

        reversePlayButton6.setText("< PLAY");
        reversePlayButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reversePlayButton6ActionPerformed(evt);
            }
        });
        jPanel14.add(reversePlayButton6);

        timerLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        timerLabel6.setForeground(java.awt.Color.blue);
        timerLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timerLabel6.setText("00:00:00");
        jPanel14.add(timerLabel6);

        forwardPlayButton6.setText("PLAY >");
        forwardPlayButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardPlayButton6ActionPerformed(evt);
            }
        });
        jPanel14.add(forwardPlayButton6);

        jLabel27.setText("START TIME");
        jPanel14.add(jLabel27);

        jLabel28.setText("END TIME");
        jPanel14.add(jLabel28);

        jLabel29.setText("REWIND TIME (seconds)");
        jPanel14.add(jLabel29);

        startTimeTextField6.setText("0");
        jPanel14.add(startTimeTextField6);

        endTimeTextField6.setText("3600");
        endTimeTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endTimeTextField6ActionPerformed(evt);
            }
        });
        jPanel14.add(endTimeTextField6);

        rewindTimeTextField6.setText("75");
        rewindTimeTextField6.setToolTipText("");
        rewindTimeTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rewindTimeTextField6ActionPerformed(evt);
            }
        });
        jPanel14.add(rewindTimeTextField6);

        autoPlayButton6.setText("START AUTO PLAY");
        autoPlayButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoPlayButton6ActionPerformed(evt);
            }
        });
        jPanel14.add(autoPlayButton6);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel30.setText("AT END OF PLAY: ");
        jPanel14.add(jLabel30);

        autoPlayEndComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "STOP", "REWIND", "PLAY REVERSE" }));
        jPanel14.add(autoPlayEndComboBox6);

        reverseModeCheckBox6.setText("Reverse Mode");
        jPanel14.add(reverseModeCheckBox6);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel31.setText("SET RECORD MODE: ");
        jPanel14.add(jLabel31);

        recordToggleButton6.setText("RECORD");
        recordToggleButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordToggleButton6ActionPerformed(evt);
            }
        });
        jPanel14.add(recordToggleButton6);

        javax.swing.GroupLayout deck6PanelLayout = new javax.swing.GroupLayout(deck6Panel);
        deck6Panel.setLayout(deck6PanelLayout);
        deck6PanelLayout.setHorizontalGroup(
            deck6PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deck6PanelLayout.createSequentialGroup()
                .addComponent(connectButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commPortComboBox6, 0, 234, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closePortButton6))
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        deck6PanelLayout.setVerticalGroup(
            deck6PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deck6PanelLayout.createSequentialGroup()
                .addGroup(deck6PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connectButton6)
                    .addComponent(commPortComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closePortButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel8.add(deck6Panel);

        jTabbedPane1.addTab("Reel To Reels", jPanel8);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Synchro Play"));

        reel1CheckBox.setSelected(true);
        reel1CheckBox.setText("TEAC 2300SD");

        reel2CheckBox.setSelected(true);
        reel2CheckBox.setText("GX77");

        reel3CheckBox.setSelected(true);
        reel3CheckBox.setText("TEAC X10R");

        stopAllButton.setText("STOP ALL");
        stopAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopAllButtonActionPerformed(evt);
            }
        });

        autoPlayAllButton.setText("START ALL");
        autoPlayAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoPlayAllButtonActionPerformed(evt);
            }
        });

        jLabel13.setText("DELAY (seconds): ");

        startDelayTextField.setText("0");

        reel4CheckBox.setSelected(true);
        reel4CheckBox.setText("TEAC X7R");

        reel5CheckBox.setSelected(true);
        reel5CheckBox.setText("REEL 2 REEL #5");

        reel6CheckBox.setSelected(true);
        reel6CheckBox.setText("REEL 2 REEL #6");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(autoPlayAllButton, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(reel4CheckBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(reel1CheckBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(startDelayTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                        .addComponent(stopAllButton, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(reel5CheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(reel2CheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(reel3CheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(reel6CheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reel1CheckBox)
                    .addComponent(reel2CheckBox)
                    .addComponent(reel3CheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reel4CheckBox)
                    .addComponent(reel5CheckBox)
                    .addComponent(reel6CheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stopAllButton)
                    .addComponent(autoPlayAllButton)
                    .addComponent(jLabel13)
                    .addComponent(startDelayTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(309, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Synchro Play", jPanel11);

        setupButton.setText("Setup");
        setupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setupButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(clearConsoleButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(setupButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(closeButton))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(429, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeButton)
                    .addComponent(clearConsoleButton)
                    .addComponent(setupButton)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 185, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closePortButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closePortButton1ActionPerformed
        if(connectIndex1 != -1) {
            autoReel.disconnect(connectIndex1);
            connectButton1.setBackground(Color.YELLOW);
            connectButton1.setEnabled(true);
        }
    }//GEN-LAST:event_closePortButton1ActionPerformed

    private void closePortButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closePortButton2ActionPerformed
        if(connectIndex2 != -1) {
            autoReel.disconnect(connectIndex2);
            connectButton2.setBackground(Color.YELLOW);
            connectButton2.setEnabled(true);
        }
    }//GEN-LAST:event_closePortButton2ActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        setVisible(false);
        System.exit(0);
    }//GEN-LAST:event_closeButtonActionPerformed

    private void connectButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButton1ActionPerformed
        String port = commPortComboBox1.getSelectedItem().toString();
        consoleTextArea.append("Connecting to port: " + port + "\n");
        connectIndex1 = autoReel.connect(port);
        
        if(connectIndex1 != -1) {
            connectButton1.setBackground(Color.GREEN);
            connectButton1.setEnabled(false);
            consoleTextArea.append(deckName1 + " Connected ...\n");
        }
    }//GEN-LAST:event_connectButton1ActionPerformed

    private void forwardPlayButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardPlayButton1ActionPerformed
        consoleTextArea.append("Send PLAY (1) ...\n");
        autoReel.sendForwardPlay(connectIndex1, 1);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_forwardPlayButton1ActionPerformed

    private void stopButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButton1ActionPerformed
        consoleTextArea.append("Send STOP (1) ...\n");
        
        synchronized(this) { // make sure only one thread at a time calls this
            autoReel.sendStop(connectIndex1, 1);
        }
        
        consoleTextArea.append("Done ...\n");
        
        stopAutoPlay1 = true;
    }//GEN-LAST:event_stopButton1ActionPerformed

    private void rewindButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rewindButton1ActionPerformed
        consoleTextArea.append("Send REWIND (1) ...\n");
        autoReel.sendRewind(connectIndex1, 1);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_rewindButton1ActionPerformed

    private void forwardButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardButton1ActionPerformed
        consoleTextArea.append("Send F.FORWARD (1) ...\n");
        autoReel.sendFastForward(connectIndex1, 1);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_forwardButton1ActionPerformed

    private void reversePlayButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reversePlayButton1ActionPerformed
        consoleTextArea.append("Send REVERSE PLAY (1) ...\n");
        autoReel.sendReversePlay(connectIndex1, 1);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_reversePlayButton1ActionPerformed

    private void autoPlayButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoPlayButton1ActionPerformed
        try {
            stopAutoPlay1 = false;
            
            // start the timer
            timer1 = new Timer(1000, new ActionListener() {
                // start and end need to be converted to seconds
                int start = Integer.parseInt(startTimeTextField1.getText());
                int end = Integer.parseInt(endTimeTextField1.getText());
                int rewindEnd = Integer.parseInt(rewindTimeTextField1.getText());
                int rewindTime = 0;
                String timeString;
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    start += 1;
                    
                    if(start > end || stopAutoPlay1) {
                        String stopMode = autoPlayEndComboBox1.getSelectedItem().toString();
                        
                        if(stopAutoPlay1) {
                            autoPlayEndComboBox1.setSelectedIndex(0);
                            stopMode = "STOP";
                            
                            // set the start to the current time value
                            startTimeTextField1.setText("" + start);
                        } else {
                            startTimeTextField1.setText("0");
                        }
                        
                        // stop the timer and also send stop command
                        //System.out.println("Play Stopped now: " + stopMode);
                        
                        if(stopMode.equals("STOP")) {
                            if(!stopAutoPlay1) {
                                stopButton1.doClick();
                            }
                            autoPlayButton1.setEnabled(true);
                            timerLabel1.setText("00:00:00");
                            consoleTextArea.append("AutoPlay 1 Stopped ...\n");
                            
                            timer1.stop();
                        } else if(stopMode.equals("PLAY REVERSE")) {
                            start = 0; // reset start value
                            prefix1 = "-";
                            autoPlayEndComboBox1.setSelectedIndex(0); // set it stop so we stop at the beginning
                            reversePlayButton1.doClick();
                            consoleTextArea.append("AutoPlay 1 Reverse Play ...\n");
                        } else {// must be rewind so we need to 
                            // we now can start rewind
                            if (rewindTime == 0) {
                                prefix1 = "-";
                                rewindButton1.doClick();
                            }

                            rewindTime++;
                            int diff = rewindEnd - rewindTime;
                            timeString = autoReel.getTimeString(diff) + " | (" + diff + ")";
                            timerLabel1.setText(prefix1 + timeString);
                            
                            if (rewindTime > rewindEnd || stopAutoPlay1) {
                                autoPlayEndComboBox1.setSelectedIndex(0);
                            } 
                        }
                    } else {
                        timeString = autoReel.getTimeString(start) + " | (" + start + ")";
                        timerLabel1.setText(prefix1 + timeString);
                    }
                }
            });
            timer1.start();
            
            // disable the button
            consoleTextArea.append("Starting AutoPlay 1 ...\n");
            autoPlayButton1.setEnabled(false);
            
            if(!reverseModeCheckBox1.isSelected()) {
                prefix1 = "+";
                forwardPlayButton1.doClick();
            } else {
                prefix1 = "-";
                reversePlayButton1.doClick();
            }
        } catch(NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }//GEN-LAST:event_autoPlayButton1ActionPerformed

    private void clearConsoleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearConsoleButtonActionPerformed
        consoleTextArea.setText("");
    }//GEN-LAST:event_clearConsoleButtonActionPerformed

    private void autoPlayButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoPlayButton2ActionPerformed
        try {
            stopAutoPlay2 = false;
            
            // start the timer
            timer2 = new Timer(1000, new ActionListener() {
                // start and end need to be ocnverted to seconds
                int start = Integer.parseInt(startTimeTextField2.getText());
                int end = Integer.parseInt(endTimeTextField2.getText());
                int rewindEnd = Integer.parseInt(rewindTimeTextField2.getText());
                int rewindTime = 0;
                String timeString;
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    start += 1;
                    
                    if(start > end || stopAutoPlay2) {
                        String stopMode = autoPlayEndComboBox2.getSelectedItem().toString();
                        
                        if(stopAutoPlay2) {
                            autoPlayEndComboBox2.setSelectedIndex(0);
                            stopMode = "STOP";
                            
                            // set the start to the current time value
                            startTimeTextField2.setText("" + start);
                        } else {
                            startTimeTextField2.setText("0");
                        }
                        
                        // stop the timer and also send stop command
                        //System.out.println("Play Stopped now: " + stopMode);
                        
                        if(stopMode.equals("STOP")) {
                            if(!stopAutoPlay2) {
                                stopButton2.doClick();
                            }
                            autoPlayButton2.setEnabled(true);
                            timerLabel2.setText("00:00:00");
                            consoleTextArea.append("AutoPlay 2 Stopped ...\n");
                            
                            timer2.stop();
                        } else if(stopMode.equals("PLAY REVERSE")) {
                            start = 0; // reset start value
                            prefix2 = "-";
                            autoPlayEndComboBox2.setSelectedIndex(0); // set it stop so we stop at the beginning
                            reversePlayButton2.doClick();
                            consoleTextArea.append("AutoPlay 2 Reverse Play ...\n");
                        } else {// must be rewind so we need to 
                            // we now can start rewind
                            if (rewindTime == 0) {
                                prefix2 = "-";
                                rewindButton2.doClick();
                            }

                            rewindTime++;
                            int diff = rewindEnd - rewindTime;
                            timeString = autoReel.getTimeString(diff) + " | (" + diff + ")";
                            timerLabel2.setText(prefix2 + timeString);
                            
                            if (rewindTime > rewindEnd || stopAutoPlay2) {
                                autoPlayEndComboBox2.setSelectedIndex(0);
                            } 
                        }
                    } else {
                        timeString = autoReel.getTimeString(start) + " | (" + start + ")";
                        timerLabel2.setText(prefix2 + timeString);
                    }
                }
            });
            timer2.start();
            
            // disable the button
            consoleTextArea.append("Starting AutoPlay 2 ...\n");
            autoPlayButton2.setEnabled(false);
            
            if(!reverseModeCheckBox2.isSelected()) {
                prefix2 = "+";
                forwardPlayButton2.doClick();
            } else {
                prefix2 = "-";
                reversePlayButton2.doClick();
            }
        } catch(NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }//GEN-LAST:event_autoPlayButton2ActionPerformed

    private void connectButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButton2ActionPerformed
        String port = commPortComboBox2.getSelectedItem().toString();
        consoleTextArea.append("Connecting to port: " + port + "\n");
        connectIndex2 = autoReel.connect(port);
        
        if(connectIndex2 != -1) {
            connectButton2.setBackground(Color.GREEN);
            connectButton2.setEnabled(false);
            consoleTextArea.append(deckName2 + " Connected ...\n");
        }
    }//GEN-LAST:event_connectButton2ActionPerformed

    private void rewindButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rewindButton2ActionPerformed
        consoleTextArea.append("Send REWIND (2) ...\n");
        autoReel.sendRewind(connectIndex2, 2);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_rewindButton2ActionPerformed

    private void stopButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButton2ActionPerformed
        consoleTextArea.append("Send STOP (2) ...\n");
        synchronized(this) { // make sure only one thread at a time calls this
            autoReel.sendStop(connectIndex2, 2);
        }
        consoleTextArea.append("Done ...\n");
        
        stopAutoPlay2 = true;
    }//GEN-LAST:event_stopButton2ActionPerformed

    private void forwardButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardButton2ActionPerformed
        consoleTextArea.append("Send F.FORWARD (2) ...\n");
        autoReel.sendFastForward(connectIndex2, 2);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_forwardButton2ActionPerformed

    private void reversePlayButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reversePlayButton2ActionPerformed
        consoleTextArea.append("Send REVERSE PLAY (2) ...\n");
        autoReel.sendReversePlay(connectIndex2, 2);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_reversePlayButton2ActionPerformed

    private void forwardPlayButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardPlayButton2ActionPerformed
        consoleTextArea.append("Send PLAY (2) ...\n");
        autoReel.sendForwardPlay(connectIndex2, 2);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_forwardPlayButton2ActionPerformed

    private void connectButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButton3ActionPerformed
        String port = commPortComboBox3.getSelectedItem().toString();
        consoleTextArea.append("Connecting to port: " + port + "\n");
        connectIndex3 = autoReel.connect(port);
        
        if(connectIndex3 != -1) {
            connectButton3.setBackground(Color.GREEN);
            connectButton3.setEnabled(false);
            consoleTextArea.append(deckName3 + " Connected ...\n");
        }
    }//GEN-LAST:event_connectButton3ActionPerformed

    private void closePortButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closePortButton3ActionPerformed
        if(connectIndex3 != -1) {
            autoReel.disconnect(connectIndex3);
            connectButton3.setBackground(Color.YELLOW);
            connectButton3.setEnabled(true);
        }
    }//GEN-LAST:event_closePortButton3ActionPerformed

    private void rewindButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rewindButton3ActionPerformed
        consoleTextArea.append("Send REWIND (3) ...\n");
        autoReel.sendRewind(connectIndex3, 3);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_rewindButton3ActionPerformed

    private void stopButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButton3ActionPerformed
        consoleTextArea.append("Send STOP (3) ...\n");
        synchronized(this) { // make sure only one thread at a time calls this
            autoReel.sendStop(connectIndex3, 3);
        }
        consoleTextArea.append("Done ...\n");
        
        stopAutoPlay3 = true;
    }//GEN-LAST:event_stopButton3ActionPerformed

    private void forwardButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardButton3ActionPerformed
        consoleTextArea.append("Send F.FORWARD (3) ...\n");
        autoReel.sendFastForward(connectIndex3, 3);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_forwardButton3ActionPerformed

    private void reversePlayButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reversePlayButton3ActionPerformed
        consoleTextArea.append("Send REVERSE PLAY (3) ...\n");
        autoReel.sendReversePlay(connectIndex3, 3);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_reversePlayButton3ActionPerformed

    private void forwardPlayButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardPlayButton3ActionPerformed
        consoleTextArea.append("Send PLAY (3) ...\n");
        autoReel.sendForwardPlay(connectIndex3, 3);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_forwardPlayButton3ActionPerformed

    private void autoPlayButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoPlayButton3ActionPerformed
        try {
            stopAutoPlay3 = false;
            
            // start the timer
            timer3 = new Timer(1000, new ActionListener() {
                // start and end need to be ocnverted to seconds
                int start = Integer.parseInt(startTimeTextField3.getText());
                int end = Integer.parseInt(endTimeTextField3.getText());
                int rewindEnd = Integer.parseInt(rewindTimeTextField3.getText());
                int rewindTime = 0;
                String timeString;
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    start += 1;
                    
                    if(start > end || stopAutoPlay3) {
                        String stopMode = autoPlayEndComboBox3.getSelectedItem().toString();
                        
                        if(stopAutoPlay3) {
                            autoPlayEndComboBox3.setSelectedIndex(0);
                            stopMode = "STOP";
                            
                            // set the start to the current time value
                            startTimeTextField3.setText("" + start);
                        } else {
                            startTimeTextField3.setText("0");
                        }
                        
                        if(stopMode.equals("STOP")) {
                            if(!stopAutoPlay3) {
                                stopButton3.doClick();
                            }
                            autoPlayButton3.setEnabled(true);
                            timerLabel3.setText("00:00:00");
                            consoleTextArea.append("AutoPlay 3 Stopped ...\n");
                            
                            timer3.stop();
                        } else if(stopMode.equals("PLAY REVERSE")) {
                            start = 0; // reset start value
                            prefix3 = "-";
                            autoPlayEndComboBox3.setSelectedIndex(0); // set it stop so we stop at the beginning
                            reversePlayButton3.doClick();
                            consoleTextArea.append("AutoPlay 3 Reverse Play ...\n");
                        } else {// must be rewind so we need to 
                            // we now can start rewind
                            if (rewindTime == 0) {
                                prefix3 = "-";
                                rewindButton3.doClick();
                            }

                            rewindTime++;
                            int diff = rewindEnd - rewindTime;
                            timeString = autoReel.getTimeString(diff) + " | (" + diff + ")";
                            timerLabel3.setText(prefix3 + timeString);
                            
                            if (rewindTime > rewindEnd || stopAutoPlay3) {
                                autoPlayEndComboBox3.setSelectedIndex(0);
                            } 
                        }
                    } else {
                        timeString = autoReel.getTimeString(start) + " | (" + start + ")";
                        timerLabel3.setText(prefix3 + timeString);
                    }
                }
            });
            timer3.start();
            
            // disable the button
            consoleTextArea.append("Starting AutoPlay 3 ...\n");
            autoPlayButton3.setEnabled(false);
            
            if(!reverseModeCheckBox3.isSelected()) {
                prefix3 = "+";
                forwardPlayButton3.doClick();
            } else {
                prefix3 = "-";
                reversePlayButton3.doClick();
            }
        } catch(NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }//GEN-LAST:event_autoPlayButton3ActionPerformed

    private void stopAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopAllButtonActionPerformed
        if(reel1CheckBox.isSelected()) {
            stopButton1.doClick();
        }
        
        if(reel2CheckBox.isSelected()) {
            stopButton2.doClick();
        }
        
        if(reel3CheckBox.isSelected()) {
            stopButton3.doClick();
        }
        
        if(reel4CheckBox.isSelected()) {
            stopButton4.doClick();
        }
        
        consoleTextArea.append("\nSynchro Play STOPPED ...\n");
        autoPlayAllButton.setEnabled(true);
    }//GEN-LAST:event_stopAllButtonActionPerformed
    
    private void startAutoPlay() {
        if(reel1CheckBox.isSelected()) {
            autoPlayButton1.doClick();
        }
        
        if(reel2CheckBox.isSelected()) {
            autoPlayButton2.doClick();
        }
        
        if(reel3CheckBox.isSelected()) {
            autoPlayButton3.doClick();
        }
        
        if(reel4CheckBox.isSelected()) {
            autoPlayButton4.doClick();
        }
        
        if(reel5CheckBox.isSelected()) {
            autoPlayButton5.doClick();
        }
        
        if(reel6CheckBox.isSelected()) {
            autoPlayButton6.doClick();
        }
        
        consoleTextArea.append("\nSynchro Play STARTED ...\n\n");
    }
    
    private void autoPlayAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoPlayAllButtonActionPerformed
        autoPlayAllButton.setEnabled(false);
        try {
            // see if to delay starting auto play
            int delay = Integer.parseInt(startDelayTextField.getText());
            
            consoleTextArea.append("\nSynchro Play Delay ...\n");
            
            if(delay > 0) {
                delay = delay*1000;
                
                ActionListener taskPerformer = (ActionEvent evt1) -> {
                    startAutoPlay();
                };
                
                Timer timer = new Timer(delay, taskPerformer);
                timer.setRepeats(false);
                timer.start();
            } else {
                startAutoPlay();
            }
        } catch(NumberFormatException nfe) {
            startAutoPlay();
        }
    }//GEN-LAST:event_autoPlayAllButtonActionPerformed

    private void connectButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButton4ActionPerformed
        String port = commPortComboBox4.getSelectedItem().toString();
        consoleTextArea.append("Connecting to port: " + port + "\n");
        connectIndex4 = autoReel.connect(port);
        
        if(connectIndex4 != -1) {
            connectButton4.setBackground(Color.GREEN);
            connectButton4.setEnabled(false);
            consoleTextArea.append(deckName4 + " Connected ...\n");
        }
    }//GEN-LAST:event_connectButton4ActionPerformed

    private void closePortButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closePortButton4ActionPerformed
        if(connectIndex4 != -1) {
            autoReel.disconnect(connectIndex4);
            connectButton4.setBackground(Color.YELLOW);
            connectButton4.setEnabled(true);
        }
    }//GEN-LAST:event_closePortButton4ActionPerformed

    private void rewindButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rewindButton4ActionPerformed
        consoleTextArea.append("Send REWIND (4) ...\n");
        autoReel.sendRewind(connectIndex4, 4);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_rewindButton4ActionPerformed

    private void stopButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButton4ActionPerformed
        consoleTextArea.append("Send STOP (4) ...\n");
        synchronized(this) { // make sure only one thread at a time calls this
            autoReel.sendStop(connectIndex4, 4);
        }
        consoleTextArea.append("Done ...\n");
        
        stopAutoPlay4 = true;
    }//GEN-LAST:event_stopButton4ActionPerformed

    private void forwardButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardButton4ActionPerformed
        consoleTextArea.append("Send F.FORWARD (4) ...\n");
        autoReel.sendFastForward(connectIndex4, 4);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_forwardButton4ActionPerformed

    private void reversePlayButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reversePlayButton4ActionPerformed
        consoleTextArea.append("Send REVERSE PLAY (4) ...\n");
        autoReel.sendReversePlay(connectIndex4, 4);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_reversePlayButton4ActionPerformed

    private void forwardPlayButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardPlayButton4ActionPerformed
        consoleTextArea.append("Send PLAY (4) ...\n");
        autoReel.sendForwardPlay(connectIndex4, 4);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_forwardPlayButton4ActionPerformed

    private void autoPlayButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoPlayButton4ActionPerformed
        try {
            stopAutoPlay4 = false;
            
            // start the timer
            timer4 = new Timer(1000, new ActionListener() {
                // start and end need to be ocnverted to seconds
                int start = Integer.parseInt(startTimeTextField4.getText());
                int end = Integer.parseInt(endTimeTextField4.getText());
                int rewindEnd = Integer.parseInt(rewindTimeTextField4.getText());
                int rewindTime = 0;
                String timeString;
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    start += 1;
                    
                    if(start > end || stopAutoPlay4) {
                        String stopMode = autoPlayEndComboBox4.getSelectedItem().toString();
                        
                        if(stopAutoPlay4) {
                            autoPlayEndComboBox4.setSelectedIndex(0);
                            stopMode = "STOP";
                            
                            // set the start to the current time value
                            startTimeTextField4.setText("" + start);
                        } else {
                            startTimeTextField4.setText("0");
                        }
                        
                        if(stopMode.equals("STOP")) {
                            if(!stopAutoPlay4) {
                                stopButton4.doClick();
                            }
                            autoPlayButton4.setEnabled(true);
                            timerLabel4.setText("00:00:00");
                            consoleTextArea.append("AutoPlay 4 Stopped ...\n");
                            
                            timer4.stop();
                        } else if(stopMode.equals("PLAY REVERSE")) {
                            start = 0; // reset start value
                            prefix4 = "-";
                            autoPlayEndComboBox4.setSelectedIndex(0); // set it stop so we stop at the beginning
                            reversePlayButton4.doClick();
                            consoleTextArea.append("AutoPlay 4 Reverse Play ...\n");
                        } else {// must be rewind so we need to 
                            // we now can start rewind
                            if (rewindTime == 0) {
                                prefix4 = "-";
                                rewindButton4.doClick();
                            }

                            rewindTime++;
                            int diff = rewindEnd - rewindTime;
                            timeString = autoReel.getTimeString(diff) + " | (" + diff + ")";
                            timerLabel4.setText(prefix4 + timeString);
                            
                            if (rewindTime > rewindEnd || stopAutoPlay4) {
                                autoPlayEndComboBox4.setSelectedIndex(0);
                            } 
                        }
                    } else {
                        timeString = autoReel.getTimeString(start) + " | (" + start + ")";
                        timerLabel4.setText(prefix4 + timeString);
                    }
                }
            });
            timer4.start();
            
            // disable the button
            consoleTextArea.append("Starting AutoPlay 4 ...\n");
            autoPlayButton4.setEnabled(false);
            
            if(!reverseModeCheckBox4.isSelected()) {
                prefix4 = "+";
                forwardPlayButton4.doClick();
            } else {
                prefix4 = "-";
                reversePlayButton4.doClick();
            }
        } catch(NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }//GEN-LAST:event_autoPlayButton4ActionPerformed

    private void endTimeTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endTimeTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_endTimeTextField3ActionPerformed

    private void rewindTimeTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rewindTimeTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rewindTimeTextField4ActionPerformed

    private void startTimeTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startTimeTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_startTimeTextField3ActionPerformed

    private void endTimeTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endTimeTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_endTimeTextField4ActionPerformed

    private void recordToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordToggleButton1ActionPerformed
        if(recordToggleButton1.isSelected()) {
            consoleTextArea.append("Send REC Command (1) ...\n");
            autoReel.sendRecord(connectIndex1, 1);
            consoleTextArea.append("Done ...\n");
        } else {
            stopButton1.doClick();
        }
    }//GEN-LAST:event_recordToggleButton1ActionPerformed

    private void recordToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordToggleButton2ActionPerformed
        if(recordToggleButton2.isSelected()) {
            consoleTextArea.append("Send REC Command (2) ...\n");
            autoReel.sendRecord(connectIndex2, 2);
            consoleTextArea.append("Done ...\n");
        } else {
            stopButton2.doClick();
        }
    }//GEN-LAST:event_recordToggleButton2ActionPerformed

    private void recordToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordToggleButton3ActionPerformed
        if(recordToggleButton3.isSelected()) {
            consoleTextArea.append("Send REC Command (3) ...\n");
            autoReel.sendRecord(connectIndex3, 3);
            consoleTextArea.append("Done ...\n");
        } else {
            stopButton3.doClick();
        }
    }//GEN-LAST:event_recordToggleButton3ActionPerformed

    private void recordToggleButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordToggleButton4ActionPerformed
        if(recordToggleButton4.isSelected()) {
            consoleTextArea.append("Send REC Command (4) ...\n");
            autoReel.sendRecord(connectIndex4, 4);
            consoleTextArea.append("Done ...\n");
        } else {
            stopButton4.doClick();
        }
    }//GEN-LAST:event_recordToggleButton4ActionPerformed

    private void setupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setupButtonActionPerformed
        SetupDialog setupDialog = new SetupDialog(this, true);
        setupDialog.setTitle("AutoReel Setup");
        setupDialog.setAutoReel(autoReel);
        setupDialog.setVisible(true);
    }//GEN-LAST:event_setupButtonActionPerformed

    private void connectButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButton5ActionPerformed
        String port = commPortComboBox5.getSelectedItem().toString();
        consoleTextArea.append("Connecting to port: " + port + "\n");
        connectIndex5 = autoReel.connect(port);
        
        if(connectIndex5 != -1) {
            connectButton5.setBackground(Color.GREEN);
            connectButton5.setEnabled(false);
            consoleTextArea.append(deckName5 + " Connected ...\n");
        }
    }//GEN-LAST:event_connectButton5ActionPerformed

    private void closePortButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closePortButton5ActionPerformed
        if(connectIndex5 != -1) {
            autoReel.disconnect(connectIndex5);
            connectButton5.setBackground(Color.YELLOW);
            connectButton5.setEnabled(true);
        }
    }//GEN-LAST:event_closePortButton5ActionPerformed

    private void rewindButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rewindButton5ActionPerformed
        consoleTextArea.append("Send REWIND (5) ...\n");
        autoReel.sendRewind(connectIndex5, 5);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_rewindButton5ActionPerformed

    private void stopButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButton5ActionPerformed
        consoleTextArea.append("Send STOP (5) ...\n");
        synchronized(this) { // make sure only one thread at a time calls this
            autoReel.sendStop(connectIndex5, 5);
        }
        consoleTextArea.append("Done ...\n");
        
        stopAutoPlay5 = true;
    }//GEN-LAST:event_stopButton5ActionPerformed

    private void forwardButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardButton5ActionPerformed
        consoleTextArea.append("Send F.FORWARD (5) ...\n");
        autoReel.sendFastForward(connectIndex5, 5);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_forwardButton5ActionPerformed

    private void reversePlayButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reversePlayButton5ActionPerformed
        consoleTextArea.append("Send REVERSE PLAY (5) ...\n");
        autoReel.sendReversePlay(connectIndex5, 5);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_reversePlayButton5ActionPerformed

    private void forwardPlayButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardPlayButton5ActionPerformed
        consoleTextArea.append("Send PLAY (5) ...\n");
        autoReel.sendForwardPlay(connectIndex5, 5);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_forwardPlayButton5ActionPerformed

    private void startTimeTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startTimeTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_startTimeTextField5ActionPerformed

    private void endTimeTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endTimeTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_endTimeTextField5ActionPerformed

    private void autoPlayButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoPlayButton5ActionPerformed
        try {
            stopAutoPlay5 = false;
            
            // start the timer
            timer5 = new Timer(1000, new ActionListener() {
                // start and end need to be ocnverted to seconds
                int start = Integer.parseInt(startTimeTextField5.getText());
                int end = Integer.parseInt(endTimeTextField5.getText());
                int rewindEnd = Integer.parseInt(rewindTimeTextField5.getText());
                int rewindTime = 0;
                String timeString;
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    start += 1;
                    
                    if(start > end || stopAutoPlay5) {
                        String stopMode = autoPlayEndComboBox5.getSelectedItem().toString();
                        
                        if(stopAutoPlay5) {
                            autoPlayEndComboBox5.setSelectedIndex(0);
                            stopMode = "STOP";
                            
                            // set the start to the current time value
                            startTimeTextField5.setText("" + start);
                        } else {
                            startTimeTextField5.setText("0");
                        }
                        
                        if(stopMode.equals("STOP")) {
                            if(!stopAutoPlay5) {
                                stopButton5.doClick();
                            }
                            autoPlayButton5.setEnabled(true);
                            timerLabel5.setText("00:00:00");
                            consoleTextArea.append("AutoPlay 5 Stopped ...\n");
                            
                            timer5.stop();
                        } else if(stopMode.equals("PLAY REVERSE")) {
                            start = 0; // reset start value
                            prefix5 = "-";
                            autoPlayEndComboBox5.setSelectedIndex(0); // set it stop so we stop at the beginning
                            reversePlayButton5.doClick();
                            consoleTextArea.append("AutoPlay 5 Reverse Play ...\n");
                        } else {// must be rewind so we need to 
                            // we now can start rewind
                            if (rewindTime == 0) {
                                prefix5 = "-";
                                rewindButton5.doClick();
                            }

                            rewindTime++;
                            int diff = rewindEnd - rewindTime;
                            timeString = autoReel.getTimeString(diff) + " | (" + diff + ")";
                            timerLabel5.setText(prefix5 + timeString);
                            
                            if (rewindTime > rewindEnd || stopAutoPlay5) {
                                autoPlayEndComboBox5.setSelectedIndex(0);
                            } 
                        }
                    } else {
                        timeString = autoReel.getTimeString(start) + " | (" + start + ")";
                        timerLabel5.setText(prefix5 + timeString);
                    }
                }
            });
            timer5.start();
            
            // disable the button
            consoleTextArea.append("Starting AutoPlay 5 ...\n");
            autoPlayButton5.setEnabled(false);
            
            if(!reverseModeCheckBox5.isSelected()) {
                prefix5 = "+";
                forwardPlayButton5.doClick();
            } else {
                prefix5 = "-";
                reversePlayButton5.doClick();
            }
        } catch(NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }//GEN-LAST:event_autoPlayButton5ActionPerformed

    private void recordToggleButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordToggleButton5ActionPerformed
        if(recordToggleButton5.isSelected()) {
            consoleTextArea.append("Send REC Command (5) ...\n");
            autoReel.sendRecord(connectIndex5, 5);
            consoleTextArea.append("Done ...\n");
        } else {
            stopButton5.doClick();
        }
    }//GEN-LAST:event_recordToggleButton5ActionPerformed

    private void connectButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButton6ActionPerformed
        String port = commPortComboBox6.getSelectedItem().toString();
        consoleTextArea.append("Connecting to port: " + port + "\n");
        connectIndex6 = autoReel.connect(port);
        
        if(connectIndex6 != -1) {
            connectButton6.setBackground(Color.GREEN);
            connectButton6.setEnabled(false);
            consoleTextArea.append(deckName6 + " Connected ...\n");
        }
    }//GEN-LAST:event_connectButton6ActionPerformed

    private void closePortButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closePortButton6ActionPerformed
        if(connectIndex6 != -1) {
            autoReel.disconnect(connectIndex6);
            connectButton6.setBackground(Color.YELLOW);
            connectButton6.setEnabled(true);
        }
    }//GEN-LAST:event_closePortButton6ActionPerformed

    private void rewindButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rewindButton6ActionPerformed
        consoleTextArea.append("Send REWIND (6) ...\n");
        autoReel.sendRewind(connectIndex6, 6);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_rewindButton6ActionPerformed

    private void stopButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButton6ActionPerformed
        consoleTextArea.append("Send STOP (6) ...\n");
        synchronized(this) { // make sure only one thread at a time calls this
            autoReel.sendStop(connectIndex6, 6);
        }
        consoleTextArea.append("Done ...\n");
        
        stopAutoPlay6 = true;
    }//GEN-LAST:event_stopButton6ActionPerformed

    private void forwardButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardButton6ActionPerformed
        consoleTextArea.append("Send F.FORWARD (6) ...\n");
        autoReel.sendFastForward(connectIndex6, 6);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_forwardButton6ActionPerformed

    private void reversePlayButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reversePlayButton6ActionPerformed
        consoleTextArea.append("Send REVERSE PLAY (6) ...\n");
        autoReel.sendReversePlay(connectIndex6, 6);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_reversePlayButton6ActionPerformed

    private void forwardPlayButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardPlayButton6ActionPerformed
        consoleTextArea.append("Send PLAY (6) ...\n");
        autoReel.sendForwardPlay(connectIndex6, 6);
        consoleTextArea.append("Done ...\n");
    }//GEN-LAST:event_forwardPlayButton6ActionPerformed

    private void endTimeTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endTimeTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_endTimeTextField6ActionPerformed

    private void rewindTimeTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rewindTimeTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rewindTimeTextField6ActionPerformed

    private void autoPlayButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoPlayButton6ActionPerformed
        try {
            stopAutoPlay6 = false;
            
            // start the timer
            timer6 = new Timer(1000, new ActionListener() {
                // start and end need to be ocnverted to seconds
                int start = Integer.parseInt(startTimeTextField6.getText());
                int end = Integer.parseInt(endTimeTextField6.getText());
                int rewindEnd = Integer.parseInt(rewindTimeTextField6.getText());
                int rewindTime = 0;
                String timeString;
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    start += 1;
                    
                    if(start > end || stopAutoPlay6) {
                        String stopMode = autoPlayEndComboBox6.getSelectedItem().toString();
                        
                        if(stopAutoPlay6) {
                            autoPlayEndComboBox6.setSelectedIndex(0);
                            stopMode = "STOP";
                            
                            // set the start to the current time value
                            startTimeTextField6.setText("" + start);
                        } else {
                            startTimeTextField6.setText("0");
                        }
                        
                        if(stopMode.equals("STOP")) {
                            if(!stopAutoPlay6) {
                                stopButton6.doClick();
                            }
                            autoPlayButton6.setEnabled(true);
                            timerLabel6.setText("00:00:00");
                            consoleTextArea.append("AutoPlay 6 Stopped ...\n");
                            
                            timer6.stop();
                        } else if(stopMode.equals("PLAY REVERSE")) {
                            start = 0; // reset start value
                            prefix6 = "-";
                            autoPlayEndComboBox6.setSelectedIndex(0); // set it stop so we stop at the beginning
                            reversePlayButton6.doClick();
                            consoleTextArea.append("AutoPlay 6 Reverse Play ...\n");
                        } else {// must be rewind so we need to 
                            // we now can start rewind
                            if (rewindTime == 0) {
                                prefix6 = "-";
                                rewindButton6.doClick();
                            }

                            rewindTime++;
                            int diff = rewindEnd - rewindTime;
                            timeString = autoReel.getTimeString(diff) + " | (" + diff + ")";
                            timerLabel6.setText(prefix6 + timeString);
                            
                            if (rewindTime > rewindEnd || stopAutoPlay6) {
                                autoPlayEndComboBox6.setSelectedIndex(0);
                            } 
                        }
                    } else {
                        timeString = autoReel.getTimeString(start) + " | (" + start + ")";
                        timerLabel6.setText(prefix6 + timeString);
                    }
                }
            });
            timer6.start();
            
            // disable the button
            consoleTextArea.append("Starting AutoPlay 6 ...\n");
            autoPlayButton6.setEnabled(false);
            
            if(!reverseModeCheckBox6.isSelected()) {
                prefix6 = "+";
                forwardPlayButton6.doClick();
            } else {
                prefix6 = "-";
                reversePlayButton6.doClick();
            }
        } catch(NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }//GEN-LAST:event_autoPlayButton6ActionPerformed

    private void recordToggleButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordToggleButton6ActionPerformed
        if(recordToggleButton6.isSelected()) {
            consoleTextArea.append("Send REC Command (6) ...\n");
            autoReel.sendRecord(connectIndex6, 6);
            consoleTextArea.append("Done ...\n");
        } else {
            stopButton6.doClick();
        }
    }//GEN-LAST:event_recordToggleButton6ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton autoPlayAllButton;
    private javax.swing.JButton autoPlayButton1;
    private javax.swing.JButton autoPlayButton2;
    private javax.swing.JButton autoPlayButton3;
    private javax.swing.JButton autoPlayButton4;
    private javax.swing.JButton autoPlayButton5;
    private javax.swing.JButton autoPlayButton6;
    private javax.swing.JComboBox<String> autoPlayEndComboBox1;
    private javax.swing.JComboBox<String> autoPlayEndComboBox2;
    private javax.swing.JComboBox<String> autoPlayEndComboBox3;
    private javax.swing.JComboBox<String> autoPlayEndComboBox4;
    private javax.swing.JComboBox<String> autoPlayEndComboBox5;
    private javax.swing.JComboBox<String> autoPlayEndComboBox6;
    private javax.swing.JButton clearConsoleButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton closePortButton1;
    private javax.swing.JButton closePortButton2;
    private javax.swing.JButton closePortButton3;
    private javax.swing.JButton closePortButton4;
    private javax.swing.JButton closePortButton5;
    private javax.swing.JButton closePortButton6;
    private javax.swing.JComboBox<String> commPortComboBox1;
    private javax.swing.JComboBox<String> commPortComboBox2;
    private javax.swing.JComboBox<String> commPortComboBox3;
    private javax.swing.JComboBox<String> commPortComboBox4;
    private javax.swing.JComboBox<String> commPortComboBox5;
    private javax.swing.JComboBox<String> commPortComboBox6;
    private javax.swing.JButton connectButton1;
    private javax.swing.JButton connectButton2;
    private javax.swing.JButton connectButton3;
    private javax.swing.JButton connectButton4;
    private javax.swing.JButton connectButton5;
    private javax.swing.JButton connectButton6;
    private javax.swing.JTextArea consoleTextArea;
    private javax.swing.JPanel deck1Panel;
    private javax.swing.JPanel deck2Panel;
    private javax.swing.JPanel deck3Panel;
    private javax.swing.JPanel deck4Panel;
    private javax.swing.JPanel deck5Panel;
    private javax.swing.JPanel deck6Panel;
    private javax.swing.JTextField endTimeTextField1;
    private javax.swing.JTextField endTimeTextField2;
    private javax.swing.JTextField endTimeTextField3;
    private javax.swing.JTextField endTimeTextField4;
    private javax.swing.JTextField endTimeTextField5;
    private javax.swing.JTextField endTimeTextField6;
    private javax.swing.JButton forwardButton1;
    private javax.swing.JButton forwardButton2;
    private javax.swing.JButton forwardButton3;
    private javax.swing.JButton forwardButton4;
    private javax.swing.JButton forwardButton5;
    private javax.swing.JButton forwardButton6;
    private javax.swing.JButton forwardPlayButton1;
    private javax.swing.JButton forwardPlayButton2;
    private javax.swing.JButton forwardPlayButton3;
    private javax.swing.JButton forwardPlayButton4;
    private javax.swing.JButton forwardPlayButton5;
    private javax.swing.JButton forwardPlayButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToggleButton recordToggleButton1;
    private javax.swing.JToggleButton recordToggleButton2;
    private javax.swing.JToggleButton recordToggleButton3;
    private javax.swing.JToggleButton recordToggleButton4;
    private javax.swing.JToggleButton recordToggleButton5;
    private javax.swing.JToggleButton recordToggleButton6;
    private javax.swing.JCheckBox reel1CheckBox;
    private javax.swing.JCheckBox reel2CheckBox;
    private javax.swing.JCheckBox reel3CheckBox;
    private javax.swing.JCheckBox reel4CheckBox;
    private javax.swing.JCheckBox reel5CheckBox;
    private javax.swing.JCheckBox reel6CheckBox;
    private javax.swing.JCheckBox reverseModeCheckBox1;
    private javax.swing.JCheckBox reverseModeCheckBox2;
    private javax.swing.JCheckBox reverseModeCheckBox3;
    private javax.swing.JCheckBox reverseModeCheckBox4;
    private javax.swing.JCheckBox reverseModeCheckBox5;
    private javax.swing.JCheckBox reverseModeCheckBox6;
    private javax.swing.JButton reversePlayButton1;
    private javax.swing.JButton reversePlayButton2;
    private javax.swing.JButton reversePlayButton3;
    private javax.swing.JButton reversePlayButton4;
    private javax.swing.JButton reversePlayButton5;
    private javax.swing.JButton reversePlayButton6;
    private javax.swing.JButton rewindButton1;
    private javax.swing.JButton rewindButton2;
    private javax.swing.JButton rewindButton3;
    private javax.swing.JButton rewindButton4;
    private javax.swing.JButton rewindButton5;
    private javax.swing.JButton rewindButton6;
    private javax.swing.JTextField rewindTimeTextField1;
    private javax.swing.JTextField rewindTimeTextField2;
    private javax.swing.JTextField rewindTimeTextField3;
    private javax.swing.JTextField rewindTimeTextField4;
    private javax.swing.JTextField rewindTimeTextField5;
    private javax.swing.JTextField rewindTimeTextField6;
    private javax.swing.JButton setupButton;
    private javax.swing.JTextField startDelayTextField;
    private javax.swing.JTextField startTimeTextField1;
    private javax.swing.JTextField startTimeTextField2;
    private javax.swing.JTextField startTimeTextField3;
    private javax.swing.JTextField startTimeTextField4;
    private javax.swing.JTextField startTimeTextField5;
    private javax.swing.JTextField startTimeTextField6;
    private javax.swing.JButton stopAllButton;
    private javax.swing.JButton stopButton1;
    private javax.swing.JButton stopButton2;
    private javax.swing.JButton stopButton3;
    private javax.swing.JButton stopButton4;
    private javax.swing.JButton stopButton5;
    private javax.swing.JButton stopButton6;
    private javax.swing.JLabel timerLabel1;
    private javax.swing.JLabel timerLabel2;
    private javax.swing.JLabel timerLabel3;
    private javax.swing.JLabel timerLabel4;
    private javax.swing.JLabel timerLabel5;
    private javax.swing.JLabel timerLabel6;
    // End of variables declaration//GEN-END:variables
}
