package au.com.barstard.controlpanel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ControlPanelTest
{

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable() {
            public void run()
            {
                try
                {
                    ControlPanelTest window = new ControlPanelTest();
                    window.frame.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public ControlPanelTest()
    {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ControlPanelController controller = new ControlPanelController();
        
        frame.getContentPane().add(new JLabel("hello!"), BorderLayout.CENTER);
        controller.show(frame);
    }

}
