// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JadeArray.java

package jeu.jade.engine;


public class JadeArray
{

    public final void addElement(Object obj)
    {    	
        int i = array.length;
        if(used == i)
        {
            Object aobj[] = new Object[i + GRANULARITY];
            System.arraycopy(((Object) (array)), 0, ((Object) (aobj)), 0, i);
            array = aobj;
        }
        array[used++] = obj;
    }

    public final int getSize()
    {
        return used;
    }

    public final void setSize(int i)
    {
        if(i < array.length)
            used = i;
    }

    public final void setCapacity(int i)
    {
        if(i < used)
            i = used;
        Object aobj[] = new Object[i];
        System.arraycopy(((Object) (array)), 0, ((Object) (aobj)), 0, used);
        array = aobj;
    }

    public final Object[] toArray()
    {
        return array;
    }

    public final Object elementAt(int i)
    {
        return array[i];
    }

    public final void removeElementAt(int i)
    {
        int j = used - 1;
        if(i < j)
        {
            array[i] = array[j];
            array[j] = null;
        }
        used--;
    }
        
    public final void removeAllElementsFast()
    {
        used = 0;
    }

    public final void removeAllElements()
    {
        array = new Object[GRANULARITY];
        used = 0;
    }

    public JadeArray()
    {
        array = new Object[GRANULARITY];
        used = 0;
    }

    public JadeArray(int i)
    {
        array = new Object[i];
        used = 0;
    }

    public static final int GRANULARITY = 8;
    private Object array[];
    private int used;
}
