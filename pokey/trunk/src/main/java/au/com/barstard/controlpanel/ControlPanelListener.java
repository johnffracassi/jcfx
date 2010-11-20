package au.com.barstard.controlpanel;

public interface ControlPanelListener
{
    void spin(int lines, int credits);
    void reserve();
    void gamble();
    void takeWin();
    void collect();
}
