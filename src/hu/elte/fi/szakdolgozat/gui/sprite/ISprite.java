package hu.elte.fi.szakdolgozat.gui.sprite;

import java.awt.*;

public interface ISprite {
    boolean isVisible();

    void setVisible(boolean visible);

    Image getImage();

    int getWidth();

    int getHeight();
}

