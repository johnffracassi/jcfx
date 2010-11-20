package au.com.barstard.doubler;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class DoubleTest
{
    private DoublingController controller = new DoublingController();
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
                    DoubleTest window = new DoubleTest();
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
    public DoubleTest()
    {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 500);
        frame.setLayout(new BorderLayout());
        frame.add(controller.getView(), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        controller.setBalance(25);
    }
}
