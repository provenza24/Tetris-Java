// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JadeScene.java

package jeu.jade.engine;


public interface JadeScene
{

    public abstract void begin();

    public abstract void mainLoop();

    public abstract void draw();

    public abstract void end();
}
