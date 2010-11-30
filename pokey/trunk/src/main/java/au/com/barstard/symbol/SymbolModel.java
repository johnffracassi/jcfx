package au.com.barstard.symbol;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.net.URL;

import javax.swing.ImageIcon;

public class SymbolModel implements ImageObserver
{
    public static final int WIDTH = 111;
    public static final int HEIGHT = 111;
    
    private String name;
    private String description;
    private String label;
    private String imageLocation;
    
    private ImageIcon image = null;

    public SymbolModel()
    {
    }
    
    public void paint(Image i)
    {
        Graphics2D g = (Graphics2D) i.getGraphics();

        if (image != null)
        {
            g.drawImage(image.getImage(), 0, 0, WIDTH, HEIGHT, Color.white, this);
        }
        else
        {
            int width = (int) g.getDeviceConfiguration().getBounds().getWidth();
            int height = (int) g.getDeviceConfiguration().getBounds().getHeight();

            Font f = new Font("Arial", Font.BOLD, 36);
            FontRenderContext frc = new FontRenderContext(null, false, false);
            Rectangle2D bounds = f.getStringBounds(getDisplay(), frc);

            int x = (width / 2) - (int) (bounds.getWidth() / 2) - (int) (bounds.getMinX());
            int y = (height / 2) - (int) (bounds.getHeight() / 2) - (int) (bounds.getMinY());

            g.setFont(f);
            g.setColor(Color.orange);
            g.drawString(getDisplay(), x, y);
        }
    }

    public void setName(String new_name)
    {
        name = new_name;
    }

    public String getName()
    {
        return name;
    }

    public String getDisplay()
    {
        return label;
    }

    public String toString()
    {
        return getDisplay();
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height)
    {
        return false;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getImageLocation()
    {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation)
    {
        this.imageLocation = imageLocation;
        URL imgURL = getClass().getResource(imageLocation);
        image = new ImageIcon(imgURL, description);
    }
}