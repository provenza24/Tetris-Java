// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JadeSpriteEnumerator.java

package jeu.jade.engine;


// Referenced classes of package jade.engine:
//            JadeFamily, JadeSprite

public class JadeSpriteEnumerator
{

    public final void enumerate(JadeFamily jadefamily)
    {
        enumeratedFamily = jadefamily;
        enumeratedIndex = 0;
    }

    public final JadeSprite getNext()
    {
        if(enumeratedIndex < enumeratedFamily.getSize())
            return (JadeSprite)enumeratedFamily.toArray()[enumeratedIndex++];
        else
            return null;
    }

    public final void remove()
    {
        enumeratedFamily.removeElementAt(--enumeratedIndex);
    }

    public JadeSpriteEnumerator()
    {
        enumeratedIndex = 0;
    }

    public JadeSpriteEnumerator(JadeFamily jadefamily)
    {
        enumerate(jadefamily);
    }

    private JadeFamily enumeratedFamily;
    private int enumeratedIndex;
}
