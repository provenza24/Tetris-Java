// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JadeMath.java

package jeu.jade.engine;

import java.util.Random;

public class JadeMath
{

    public static final int rand(int i)
    {
        return r.nextInt() % i;
    }

    public static final int urand(int i)
    {
        int j = r.nextInt() % i;
        if(j < 0)
            return -j;
        else
            return j;
    }

    public static final int max(int i, int j)
    {
        if(i > j)
            return i;
        else
            return j;
    }

    public static final int min(int i, int j)
    {
        if(i < j)
            return i;
        else
            return j;
    }

    public static final void swap(int i, int j)
    {
        int k = i;
        i = j;
        j = k;
    }

    public JadeMath()
    {
    }

    private static final Random r = new Random();

}
