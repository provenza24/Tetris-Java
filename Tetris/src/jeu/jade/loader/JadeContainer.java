// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JadeContainer.java

package jeu.jade.loader;

import java.awt.Component;

public interface JadeContainer
{

    public abstract void start();

    public abstract void setContent(Component component);

    public abstract void exit();
}
