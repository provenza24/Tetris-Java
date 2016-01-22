// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JadeKeyListener.java

package jeu.jade.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Referenced classes of package jade.engine:
//            JadeArray

public class JadeKeyListener
    implements KeyListener
{

    public final void keyPressed(KeyEvent keyevent)
    {
        keysPressed.addElement(keyevent);
    }

    public final void keyReleased(KeyEvent keyevent)
    {
        keysReleased.addElement(keyevent);
    }

    public final void keyTyped(KeyEvent keyevent)
    {
        keysTyped.addElement(keyevent);
    }

    public void onKeyPressed(KeyEvent keyevent)
    {
    }

    public void onKeyReleased(KeyEvent keyevent)
    {
    }

    public void onKeyTyped(KeyEvent keyevent)
    {
    }

    public final void emptyKeyQueue()
    {
        keysPressed.removeAllElementsFast();
        keysReleased.removeAllElementsFast();
        keysTyped.removeAllElementsFast();
    }

    public final void callKeyListener()
    {
        for(int i = 0; i < keysPressed.getSize(); i++)
            onKeyPressed((KeyEvent)keysPressed.elementAt(i));

        keysPressed.removeAllElementsFast();
        for(int j = 0; j < keysReleased.getSize(); j++)
            onKeyReleased((KeyEvent)keysReleased.elementAt(j));

        keysReleased.removeAllElementsFast();
        for(int k = 0; k < keysTyped.getSize(); k++)
            onKeyTyped((KeyEvent)keysTyped.elementAt(k));

        keysTyped.removeAllElementsFast();
    }

    public JadeKeyListener()
    {
    }

    final JadeArray keysPressed = new JadeArray();
    final JadeArray keysReleased = new JadeArray();
    final JadeArray keysTyped = new JadeArray();
}
