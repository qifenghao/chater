package my.qifeng.chater;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;

public class TextClient  extends JFrame {
    private SipLayer sipLayer;
    
    private JTextField fromAddress;
    private JLabel fromLbl;
    private JLabel receivedLbl;
    private JTextArea receivedMessages;
    private JScrollPane receivedScrollPane;
    private JButton sendBtn;
    private JLabel sendLbl;
    private JTextField sendMessages;
    private JTextField toAddress;
    private JLabel toLbl;
	
    public static void main(String[] args) {
        if(args.length != 2) {
            printUsage();
            System.exit(-1);            
        }
        
		try {
		    String username = args[0];
		    int port = Integer.parseInt(args[1]);
		    String ip = InetAddress.getLocalHost().getHostAddress();

			SipLayer sipLayer = new SipLayer(username, ip, port);
		    TextClient tc = new TextClient(sipLayer);
		    sipLayer.setMessageProcessor(new MessageProcessorImpl(tc.receivedMessages));
			
			tc.show();
        } catch (Throwable e) {
            System.out.println("Problem initializing the SIP stack.");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static void printUsage() {
        System.out.println("Syntax:");
        System.out.println("  java -jar textclient.jar <username> <port>");
        System.out.println("where <username> is the nickname of this user");
        System.out.println("and <port> is the port number to use. Usually 5060 if not used by another process.");
        System.out.println("Example:");
        System.out.println("  java -jar chater.jar Alice 5060");
    }

    public TextClient(SipLayer sip) {
        super();
        sipLayer = sip;
        initWindow();
        String from = "sip:" + sip.getUsername() + "@" + sip.getHost() + ":" + sip.getPort();
        this.fromAddress.setText(from);
    }
    
    private void initWindow() {
        receivedLbl = new JLabel();
        sendLbl = new JLabel();
        sendMessages = new JTextField();
        receivedScrollPane = new JScrollPane();
        receivedMessages = new JTextArea();
        fromLbl = new JLabel();
        fromAddress = new JTextField();
        toLbl = new JLabel();
        toAddress = new JTextField();
        sendBtn = new JButton();

        getContentPane().setLayout(null);

        setTitle("TextClient for Chater");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });

        receivedLbl.setText("Received Messages:");
        receivedLbl.setAlignmentY(0.0F);
        receivedLbl.setPreferredSize(new java.awt.Dimension(25, 100));
        getContentPane().add(receivedLbl);
        receivedLbl.setBounds(5, 0, 136, 20);

        sendLbl.setText("Send Message:");
        getContentPane().add(sendLbl);
        // sendLbl.setBounds(5, 150, 90, 20);
        sendLbl.setBounds(5, 340, 120, 20);

        getContentPane().add(sendMessages);
        // sendMessages.setBounds(5, 170, 270, 20);
        sendMessages.setBounds(5, 360, 785, 21);

        receivedMessages.setAlignmentX(0.0F);
        receivedMessages.setEditable(false);
        receivedMessages.setLineWrap(true);
        receivedMessages.setWrapStyleWord(true);
        receivedScrollPane.setViewportView(receivedMessages);
        receivedScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        getContentPane().add(receivedScrollPane);
        // receivedScrollPane.setBounds(5, 20, 270, 130);
        receivedScrollPane.setBounds(5, 20, 785, 315);

        fromLbl.setText("From:");
        getContentPane().add(fromLbl);
        // fromLbl.setBounds(5, 200, 35, 15);
        fromLbl.setBounds(5, 390, 40, 15);

        getContentPane().add(fromAddress);
        // fromAddress.setBounds(40, 200, 235, 20);
        fromAddress.setBounds(50, 390, 740, 20);
        fromAddress.setEditable(false);

        toLbl.setText("To:");
        getContentPane().add(toLbl);
        // toLbl.setBounds(5, 225, 35, 15);
        toLbl.setBounds(5, 415, 40, 15);

        getContentPane().add(toAddress);
        // toAddress.setBounds(40, 225, 235, 21);
        toAddress.setBounds(50, 415, 740, 21);

        sendBtn.setText("Send");
        sendBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                sendBtnActionPerformed(evt);
            }
        });

        getContentPane().add(sendBtn);
        // sendBtn.setBounds(200, 255, 75, 25);
        sendBtn.setBounds(645, 445, 150, 25);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        // setBounds((screenSize.width-288)/2, (screenSize.height-310)/2, 288, 320);
        setBounds((screenSize.width - 800) / 2, (screenSize.height - 500) / 2, 800, 500);
    }

    private void sendBtnActionPerformed(ActionEvent evt) {
        try {
            String to = this.toAddress.getText();
            String message = this.sendMessages.getText();
            sipLayer.sendMessage(to, message);
        } catch (Throwable e) {
            e.printStackTrace();
            this.receivedMessages.append("ERROR sending message: " + e.getMessage() + "\n");
        }
        			
    }
}
