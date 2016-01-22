// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JadeMenu.java

package jeu.jade.engine;

import java.awt.Graphics;
import java.awt.Point;

// Referenced classes of package jade.engine:
//            JadeFont, JadeBackground

public class JadeMenu
{

    public final void draw(int i, int j, Graphics g, boolean flag, int k, int l, int i1, 
            boolean flag1, int j1, int k1)
    {
        if(flag)
        {
            for(int l1 = 0; l1 < menuItems.length; l1++)
            {
                JadeFont jadefont;
                if(selectedItem == l1)
                    jadefont = selectedFont;
                else
                    jadefont = unselectedFont;
                Point point = jadefont.sizeOfString(menuItems[l1], flag1, j1, k1);
                int j2 = j;
                if(k == 1)
                    j2 -= point.y / 2;
                else
                if(k == 0)
                    j2 -= point.y;
                if(background != null)
                    background.clearRegion(i, j2, point.x, point.y, g);
                if(selectedItem == l1)
                    highlight(i, j2, point.x, point.y, g);
                jadefont.drawString(menuItems[l1], i, j2, g, flag1, j1, k1);
                i += point.x + l;
                j += i1;
            }

        } else
        {
            for(int i2 = 0; i2 < menuItems.length; i2++)
            {
                JadeFont jadefont1;
                if(selectedItem == i2)
                    jadefont1 = selectedFont;
                else
                    jadefont1 = unselectedFont;
                Point point1 = jadefont1.sizeOfString(menuItems[i2], flag1, j1, k1);
                int k2 = i;
                if(k == 1)
                    k2 -= point1.x / 2;
                else
                if(k == 0)
                    k2 -= point1.x;
                if(background != null)
                    background.clearRegion(k2, j, point1.x, point1.y, g);
                if(selectedItem == i2)
                    highlight(k2, j, point1.x, point1.y, g);
                jadefont1.drawString(menuItems[i2], k2, j, g, flag1, j1, k1);
                i += l;
                j += point1.y + i1;
            }

        }
    }

    public void highlight(int i, int j, int k, int l, Graphics g)
    {
    }

    public final void selectionUp()
    {
        selectItem(selectedItem - 1);
    }

    public final void selectionDown()
    {
        selectItem(selectedItem + 1);
    }

    public final void selectItem(int i)
    {
        if(i < 0)
            i += menuItems.length;
        selectedItem = i % menuItems.length;
    }

    public final int getSelectedItemIndex()
    {
        return selectedItem;
    }

    public final String getSelectedItemString()
    {
        return menuItems[selectedItem];
    }

    public JadeMenu(String as[], JadeFont jadefont, JadeFont jadefont1, JadeBackground jadebackground)
    {
        menuItems = as;
        unselectedFont = jadefont;
        selectedFont = jadefont1;
        background = jadebackground;
        selectedItem = 0;
    }

    public static final int LEFT = 0;
    public static final int CENTERED = 1;
    public static final int RIGHT = 2;
    public static final int TOP = 0;
    public static final int BOTTOM = 2;
    public static final boolean HORIZONTAL = true;
    public static final boolean VERTICAL = false;
    public JadeFont selectedFont;
    public JadeFont unselectedFont;
    public String menuItems[];
    public JadeBackground background;
    private int selectedItem;
}
