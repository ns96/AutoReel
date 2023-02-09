package autoreel;

import gnu.io.NRSerialPort;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * A small program for the control of reel to reel tape decks over Bluetooth
 * connections. In windows 10 the Bluetooth connection appears like a plain 
 * RS232 comm port so only the RXTX library is needed.
 * https://github.com/NeuronRobotics/nrjavaserial
 * 
 * @author Nathan
 * @version 0.1 09/27/2022
 */
public class AutoReel {
    // store program properties
    private final String PROPERTIES_FILENAME = "autoreel.properties";
    public Properties properties;
    
    private final boolean testMode = false;
    private final List<NRSerialPort> serialList = new ArrayList<>();
    private final List<DataInputStream> insList = new ArrayList<>();;
    private final List<DataOutputStream> outsList = new ArrayList<>();
    
    public AutoReel() {
        properties = getOrderedProperties();   
    }
    
    /**
     * Return a property that sorts the key values when saved
     * https://stackoverflow.com/questions/17011108/how-can-i-write-java-properties-in-a-defined-order
     * 
     * @return A properties object which sorts keys 
     */
    private Properties getOrderedProperties() {
        return new Properties() {
            @Override
            public synchronized Set<Map.Entry<Object, Object>> entrySet() {
                return Collections.synchronizedSet(
                        super.entrySet()
                                .stream()
                                .sorted(Comparator.comparing(e -> e.getKey().toString()))
                                .collect(Collectors.toCollection(LinkedHashSet::new)));
            }
        };
    }
                
    /**
     * Load the default properties
     */
    public void loadProperties() {
        // try loading the properties if it 
        try (FileReader fileReader = new FileReader(PROPERTIES_FILENAME)) {
            properties.load(fileReader);
            System.out.println("Properties File Loaded ...");
        } catch (IOException e) {
            System.out.println("Error Loading Properties File ...");
        }
    }
    
    /**
     * Save the default properties file
     */
    public void saveProperties() {
        try (FileWriter output = new FileWriter(PROPERTIES_FILENAME)) {            
            properties.store(output, "AutoReel Defaults");
            System.out.println("\nSaved Properties ...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Return the list of properties for deck
     * @param deck The deck to return the properties for
     * @return 
     */
    public String[] getDeckProperties(String deck) {
        TreeSet<String> deckProperties = new TreeSet<>();
        
        for(String key: properties.stringPropertyNames()) {
            if(key.contains(deck)) {
                deckProperties.add(key);
            }
        }
        
        String[] a = new String[deckProperties.size()];
        return deckProperties.toArray(a);
    }
    
    /**
     * Return the Serial ports on the system
     * @param skip Skip the loading of actual serial ports, since this take a long time
     * on windows
     * 
     * @return 
     */
    public Set<String> getPorts(boolean skip) {
        Set<String> ports = new TreeSet<>();
        
        if(skip) {
            for(int i = 0; i < 10; i++) {
                ports.add("COM" + i);
            }
        } else {
            System.out.println("Loading Serial ports. May take while ...");
            ports = NRSerialPort.getAvailableSerialPorts();
            System.out.println("Done Loading Serial ports ...");
        }
        
        return ports;
    }
    
    /**
     * Method to connect to the serial port
     *
     * @param portName
     * @return Return the index of the serial object
     */
    public int connect(String portName) {
        if(testMode) return -1;

        NRSerialPort serial = new NRSerialPort(portName, 9200);
        if(serial.connect()) {
            DataInputStream ins = new DataInputStream(serial.getInputStream());
            DataOutputStream outs = new DataOutputStream(serial.getOutputStream());
            serialList.add(serial);
            insList.add(ins);
            outsList.add(outs);
            
            int index = serialList.indexOf(serial);
            
            return index;
        } else {
            return -1;
        }
    }
    
    /**
     * send the rewind command for a particular deck
     * @param index The connected index
     * @param deck The deck number
     */
    public void sendRewind(int index, int deck) {
        String command = properties.getProperty("deck" + deck + ".rev");
        sendCommand(index, command, false);
    }
    
    /**
     * send the stop command for a particular deck
     * @param index The connected index
     * @param deck The deck number
     */
    public void sendStop(int index, int deck) {
        String command = properties.getProperty("deck" + deck + ".stop");
        sendCommand(index, command, false);
    }
    
    /**
     * send the fast forward command for a particular deck
     * @param index The connected index
     * @param deck The deck number
     */
    public void sendFastForward(int index, int deck) {
        String command = properties.getProperty("deck" + deck + ".ff");
        sendCommand(index, command, false);
    }
    
    /**
     * send the reverse play command for a particular deck
     * @param index The connected index
     * @param deck The deck number
     */
    public void sendReversePlay(int index, int deck) {
        String command = properties.getProperty("deck" + deck + ".play.rev");
        sendCommand(index, command, false);
    }
    
    /**
     * send the forward play command for a particular deck
     * @param index The connected index
     * @param deck The deck number
     */
    public void sendForwardPlay(int index, int deck) {
        String command = properties.getProperty("deck" + deck + ".play.for");
        sendCommand(index, command, false);
    }
    
    /**
     * send the record command for a particular deck
     * @param index The connected index
     * @param deck The deck number
     */
    public void sendRecord(int index, int deck) {
        String command = properties.getProperty("deck" + deck + ".rec");
        sendCommand(index, command, false);
    }
    
    /**
     * send the record mute command for a particular deck
     * @param index The connected index
     * @param deck The deck number
     */
    public void sendRecordMute(int index, int deck) {
        String command = properties.getProperty("deck" + deck + ".rec.mute");
        sendCommand(index, command, false);
    }
    
    /**
     * Send the command string to MiM board
     *
     * @param index The location of the serial object
     * @param command
     * @return
     */
    public String sendCommand(int index, String command) {
        return sendCommand(index, command, true);
    }

    /**
     * Method to send a command to the RS232 module
     *
     * @param index Index of the outs object
     * @param command
     * @param wfr wait for response
     * @return
     */
    public synchronized String sendCommand(int index, String command, boolean wfr) {
        if(testMode || index == -1) return "OK";

        try {
            command += "\r\n";
            outsList.get(index).writeBytes(command);
            if(wfr) {
                return readResponse(index);
            } else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method to read the results after a command has been sent
     *
     * @param index The index of the input stream object
     * @return
     */
    public String readResponse(int index) {
        if(testMode || index == -1) return "TESTMODE";

        try {
            // wait 0.100 second so data can arrive from source
            Thread.sleep(100);

            StringBuilder sb = new StringBuilder(); //ins.readUTF();
            byte[] buffer = new byte[128];
            int len;

            while ((len = insList.get(index).read(buffer)) > 0 ) {
                sb.append(new String(buffer,0,len));
            }

            String response = sb.toString().trim();
            System.out.println("Response: " + response);
            return response;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return "ERROR";
    }
    
    /**
     * Close the serial port
     * @param index 
     */
    public void disconnect(int index) {
        serialList.get(index).disconnect();
    } 
    
    /**
     * Method to get the time in seconds as a string
     * 
     * @param totalSecs
     * @return 
     */
    public String getTimeString(int totalSecs) {
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;
        
        String time = "0" + hours + ":";
        
        if(minutes < 10) {
            time  += "0" + minutes; 
        } else {
            time  += minutes; 
        }
        
        if(seconds < 10) {
            time  += ":0" + seconds; 
        } else {
            time  += ":" + seconds; 
        }
        
        return time;
    }
    
    /**
     * Pad a string with left zeros
     * @param inputString
     * @param length
     * @return 
     */
    public String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }
    
    /**
     * Pad an integer with a certain number of zeroes
     * @param num
     * @param pad
     * @return 
     */
    public String intPadLeftZeroes(int num, int pad) {
        String result = String.valueOf(num);
        int length = result.length();
        if (length >= pad) {
            return result;
        }
        char[] z = new char[pad - length];
        for (int i = 0; i < z.length; i++) {
            z[i] = '0';
        }
        return "" + new String(z) + Integer.toString(num);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            AutoReel autoReel = new AutoReel();
            autoReel.loadProperties();
            
            AutoReelFrame autoReelFrame = new AutoReelFrame();
            autoReelFrame.setAutoReel(autoReel);
            autoReelFrame.setVisible(true);
        });
    }
}
