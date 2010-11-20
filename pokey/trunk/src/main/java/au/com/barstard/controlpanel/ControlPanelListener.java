package au.com.barstard.controlpanel;

public interface ControlPanelListener
{
    void spin(int lines, int credits);
    void help();
    void gamble();
    void takeWin();
    void reset();
}
