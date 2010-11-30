package au.com.barstard.controlpanel;

public interface ControlPanelListener
{
    void spinPressed(int lines, int credits);
    void helpPressed();
    void gamblePressed();
    void takeWinPressed();
    void resetPressed();
}
