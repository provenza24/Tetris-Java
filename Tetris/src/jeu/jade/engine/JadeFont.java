// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JadeFont.java

package jeu.jade.engine;

import java.awt.*;

// Referenced classes of package jade.engine:
//            JadeMath

public class JadeFont
{

    public final void drawString(String s, int i, int j, Graphics g, boolean flag, int k, int l)
    {
        int j1 = s.length();
        for(int k1 = 0; k1 < j1; k1++)
        {
            char c = s.charAt(k1);
            int i1 = allChar.indexOf(c);
            if(i1 != -1)
            {
                g.drawImage(allPix[i1], i, j, null);
                if(flag)
                    i += horizontalAdvance[i1];
                else
                    j += verticalAdvance[i1];
            }
            i += k;
            j += l;
        }

    }

    public final Point sizeOfString(String s, boolean flag, int i, int j)
    {       
        int i1 = 0;
        int j1 = 0;
        int k1 = s.length();      
        if(flag)
        {
            for(int l1 = 0; l1 < k1; l1++)
            {
                char c = s.charAt(l1);
                int k = allChar.indexOf(c);
                if(k != -1)
                {
                    i1 += horizontalAdvance[k];
                    j1 = JadeMath.max(j1, verticalAdvance[k]);
                }
                i1 += i;
            }

            return new Point(i1 - i, j1 + (k1 - 1) * j);
        }
        for(int i2 = 0; i2 < k1; i2++)
        {
            char c1 = s.charAt(i2);
            int l = allChar.indexOf(c1);
            if(l != -1)
            {
                j1 += verticalAdvance[l];
                i1 = JadeMath.max(i1, horizontalAdvance[l]);
            }
            j1 += j;
        }

        return new Point(i1 + (k1 - 1) * i, j1 - j);
    }

    public JadeFont(Image aimage[], String s)
    {
        allPix = aimage;
        allChar = s;
        int i = s.length();
        int j = aimage[0].getWidth(null);
        int k = aimage[0].getHeight(null);
        horizontalAdvance = new int[i];
        verticalAdvance = new int[i];
        for(int l = 0; l < i; l++)
        {
            horizontalAdvance[l] = j;
            verticalAdvance[l] = k;
        }

    }

    public JadeFont(Image aimage[], String s, int i, int j)
    {
        allPix = aimage;
        allChar = s;
        int k = s.length();
        horizontalAdvance = new int[k];
        verticalAdvance = new int[k];
        for(int l = 0; l < k; l++)
        {
            horizontalAdvance[l] = i;
            verticalAdvance[l] = j;
        }

    }

    public JadeFont(Image aimage[], String s, int ai[], int ai1[])
    {
        allPix = aimage;
        allChar = s;
        horizontalAdvance = ai;
        verticalAdvance = ai1;
    }

    public static final boolean HORIZONTAL = true;
    public static final boolean VERTICAL = false;
    public int horizontalAdvance[];
    public int verticalAdvance[];
    public Image allPix[];
    public String allChar;
}
