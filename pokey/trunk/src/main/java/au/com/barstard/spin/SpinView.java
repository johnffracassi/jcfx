package au.com.barstard.spin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.List;

import javax.swing.JPanel;

import au.com.barstard.symbol.SymbolModel;

public class SpinView extends JPanel implements ImageObserver
{
    private SpinPanelListener listener = null;
    private List<SymbolModel> symbols = null;
    private BufferedImage image = null;

    private int pixelOffset = 0;
    private final int reelSize;
    private final int targetPixelOffset;

    public static final int symbolGap = 5;
    public static final int symbolTotalWidth = SymbolModel.WIDTH + symbolGap;
    public static final int symbolTotalHeight = SymbolModel.HEIGHT + symbolGap;
    public static final int initialSpeed = 15;
    
    
    public SpinView(SpinPanelListener listener, int spinCount, int initialSymbolIdx, List<SymbolModel> symbols)
    {
        setBackground(new Color(46, 139, 87));

        this.targetPixelOffset = spinCount * symbolTotalHeight;
        this.reelSize = symbols.size();
        this.pixelOffset = initialSymbolIdx * symbolTotalHeight;

        this.symbols = symbols;
        this.listener = listener;

        image = new BufferedImage(symbolTotalWidth, (reelSize + 2) * symbolTotalHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // render the image of the entire reel
        BufferedImage[] image = new BufferedImage[reelSize + 2];
        for (int i = 0; i < reelSize + 2; i++)
        {
            image[i] = new BufferedImage(SymbolModel.WIDTH, SymbolModel.HEIGHT, BufferedImage.TYPE_INT_RGB);
            getSymbol(i % reelSize).paint(image[i]);
            g2d.drawImage(image[i], null, 3, (i * symbolTotalWidth) + 3);
            g2d.drawRect(2, (symbolTotalHeight * i) + 2, symbolTotalWidth, symbolTotalHeight);
        }
    }

    public void startSpinning()
    {
        new Thread() {

            private final int localTargetPixelOffset = targetPixelOffset + ((int)(0.5 + Math.random()) * symbolTotalHeight);
            private int localPixelOffset = 0;
            private int speed = initialSpeed;
            
            public void run() {

                while(localPixelOffset < localTargetPixelOffset)
                {
                    if(localTargetPixelOffset - localPixelOffset < speed)
                    {
                        speed = 1;
                    } 
                    
                    localPixelOffset += speed;
                    pixelOffset += speed;
                    
                    repaint();

                    try { sleep(10); } catch (Exception e) {}
                    
                    if(localPixelOffset == localTargetPixelOffset)
                    {
                        stopSpinning();
                    }
                }
            }
        }.start();
    }

    public void stopSpinning()
    {
        listener.spinComplete(this);
    }

    public void paint(Graphics g)
    {
        g.drawImage(image, 0, 0 - (pixelOffset % (reelSize * symbolTotalWidth)), this);
    }

    public SymbolModel[] getActiveSymbols()
    {
        int symbolIdx = (pixelOffset / symbolTotalHeight);

        SymbolModel[] activeSymbols = new SymbolModel[3];
        activeSymbols[0] = getSymbol((symbolIdx + 0) % reelSize);
        activeSymbols[1] = getSymbol((symbolIdx + 1) % reelSize);
        activeSymbols[2] = getSymbol((symbolIdx + 2) % reelSize);

        return activeSymbols;
    }

    private SymbolModel getSymbol(int index)
    {
        return (SymbolModel) symbols.get(index);
    }
}
