// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JadeWindowContainer.java

package jeu.jade.loader;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

// Referenced classes of package jade.loader:
//            JadeContainer, JadeLoader

public class JadeWindowContainer extends Frame implements Runnable,
		JadeContainer {
	private static final long serialVersionUID = 42L;

	public final void setContent(Component component) {
		removeAll();
		if (component != null) {
			setBackground(Color.black);
			setLayout(new GridBagLayout());
			add(component); 
			setVisible(true);
			component.requestFocus();
		}
	}

	public final void exit() {
		dispose();
		System.exit(0);
	}

	public final void start() {
		loader = null;
		try {
			loader = (JadeLoader) Class.forName(loaderClassName).newInstance();
		} catch (Exception exception) {
		}
		if (loader == null) {
			System.out.println("Can't load stub game loader class "
					+ loaderClassName + ".class !");
			return;
		} else {
			(new Thread(this)).start();
			return;
		}
	}

	public final void run() {
		try {
			loader.loadAndRun(this);
		} catch (Exception exception1) {
			System.out.println("Game exception ! ( " + exception1.getMessage()
					+ " )");
			exception1.printStackTrace();
		}
	}

	public JadeWindowContainer(int i, int j, String s, String s1) {
		super(s);
		loaderClassName = s1;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int X_SCREEN_SIZE = screenSize.width;
		int Y_SCREEN_SIZE = screenSize.height;

		int posX = (X_SCREEN_SIZE - i) / 2;
		int posY = (Y_SCREEN_SIZE - j) / 2;

		setSize(i, j);
		setLocation(posX, posY);
		//setSize(800, 600);
		setResizable(false);
	}

	JadeLoader loader;

	String loaderClassName;
}
