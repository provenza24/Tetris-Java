// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JadeLoadingInterface.java

package jeu.jade.engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

// Referenced classes of package jade.engine:
//            JadeDisplay, JadeMath

public class JadeLoadingInterface
{

	/** Police de caracteres pour le debug */
	private static final Font debugFont = new Font("Dialog ", Font.BOLD, 10);

	
	private static JadeLoadingInterface instance = null;
	
	public static JadeLoadingInterface getLoadingInterface(JadeDisplay display){
		if (instance==null){
			instance = new JadeLoadingInterface(display);
		} 
			return instance	;	
	}
	
    public void draw()
    {
        int i = display.getWidth();
        int j = JadeMath.min((numberDone * 100) / numberToDo, 100);
        Graphics g = display.getDoubleBufferedGraphics();
        g.setFont(debugFont);
        g.setColor(Color.black);
        g.fillRect(0, 0, i, display.getHeight());                        
        //g.setColor(new Color(150, 150, 200));
        g.setColor(Color.white);
        g.drawString(message, ((i >> 1) - 50), 320);
        g.setColor(Color.blue);
        //g.setColor(Color.black);        
        g.drawRect((i >> 1) - 51, 290, 101, 11);
        g.setColor(Color.blue);
        g.fillRect((i >> 1) - 50, 291, j, 10);
        g.setColor(Color.white);
        g.fillRect(((i >> 1) - 50) + j, 291, 100 - j, 10);
        
        int k = (i >> 1) - 105;
        int byte0 = 290;
        g.setColor(new Color(25, 130, 215));        
        g.fillRect(k, byte0, 18, 18);
        g.setColor(new Color(20, 100, 170));
        g.fillRect(k + 2, byte0, 14, 6);
        g.fillRect(k + 2, byte0 + 10, 14, 8);
        g.setColor(Color.white);
        g.fillRect(k + 3, byte0 + 11, 12, 6);
        g.setColor(new Color(230, 230, 230));
        g.drawLine(k + 5, byte0 + 13, k + 13, byte0 + 13);
        g.drawLine(k + 5, byte0 + 15, k + 13, byte0 + 15);
        g.drawLine(k + 3, byte0 + 17, k + 15, byte0 + 17);
        g.fillRect(k + 3, byte0, 3, 6);
        g.fillRect(k + 6, byte0 + 4, 2, 2);
        g.fillRect(k + 8, byte0, 5, 6);
        
        if(j > 50)
            g.setColor(Color.white);
        else
            g.setColor(Color.black);
        g.drawString(j + "%", (i >> 1) - 15, 301);                
        display.swapBuffers();      
    }

    public void setMessage(String s)
    {
        message = s;
        draw();
    }

    public void setProgress(int i)
    {
        numberDone += i;
        draw();
    }

    public void initializeProgress(int i)
    {
        numberToDo = JadeMath.max(i, 1);
        numberDone = 0;
        draw();
    }

    private JadeLoadingInterface(JadeDisplay jadedisplay)
    {
        message = "";
        display = jadedisplay;
        numberToDo = 1;
        numberDone = 0;
    }

    String message;
    int numberToDo;
    int numberDone;
    JadeDisplay display;
}
