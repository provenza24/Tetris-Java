// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JadeSpriteLayer.java

package jeu.jade.engine;

import java.awt.Graphics;

// Referenced classes of package jade.engine:
//            JadeBackground, JadeArray, JadeFamily, JadeSprite, 
//            JadeDisplay

public class JadeSpriteLayer {

	public void setGraphics(Graphics g) {
		gc = g;
	}

	public void setBackground(JadeBackground jadebackground) {
		bg = jadebackground;
	}

	public void setForeground(JadeBackground jadebackground) {
		fg = jadebackground;
	}

	public void setDisplayMode(int i, int j) {
		if (i > 2) {
			System.out.println("Wrong layer display mode (clear)!");
			i = 0;
		}
		if (j >= 2) {
			System.out.println("Wrong layer display mode (draw)!");
			j = 0;
		}
		clear = i;
		draw = j;
	}

	public void animateAndDraw() {
		if (clear == 0 && bg != null)
			bg.clearRegion(0, 0, width, height, gc);
		Object aobj[] = families.toArray();
		int i = families.getSize();
		if (clear == 1 && bg != null) {
			for (int j = 0; j < i; j++) {
				JadeFamily jadefamily = (JadeFamily) aobj[j];
				Object aobj1[] = jadefamily.toArray();
				int l = jadefamily.getSize();
				for (int j1 = 0; j1 < l; j1++) {
					JadeSprite jadesprite = (JadeSprite) aobj1[j1];
					bg.clearRegion(jadesprite.oldx, jadesprite.oldy,
							jadesprite.width, jadesprite.height, gc);
				}

			}

		}
		for (int k = 0; k < i; k++) {
			JadeFamily jadefamily1 = (JadeFamily) aobj[k];
			Object aobj2[] = jadefamily1.toArray();
			int i1 = jadefamily1.getSize();
			for (int k1 = 0; k1 < i1; k1++) {
				JadeSprite jadesprite1 = (JadeSprite) aobj2[k1];

				if (jadesprite1.draw && (jadesprite1.animate() || draw == 0
						|| jadesprite1.oldx != jadesprite1.x
						|| jadesprite1.oldy != jadesprite1.y)) {
					gc.drawImage(jadesprite1.representation, jadesprite1.x,
							jadesprite1.y, null);
					if (clear != 0 && fg != null) {
						int l1;
						int j2;
						if (jadesprite1.x > jadesprite1.oldx) {
							l1 = jadesprite1.oldx;
							j2 = (jadesprite1.width + jadesprite1.x)
									- jadesprite1.oldx;
						} else {
							l1 = jadesprite1.x;
							j2 = (jadesprite1.width + jadesprite1.oldx)
									- jadesprite1.x;
						}
						int i2;
						int k2;
						if (jadesprite1.y > jadesprite1.oldy) {
							i2 = jadesprite1.oldy;
							k2 = (jadesprite1.height + jadesprite1.y)
									- jadesprite1.oldy;
						} else {
							i2 = jadesprite1.y;
							k2 = (jadesprite1.height + jadesprite1.oldy)
									- jadesprite1.y;
						}
						fg.clearRegion(l1, i2, j2, k2, gc);
					}
				}
				jadesprite1.oldx = jadesprite1.x;
				jadesprite1.oldy = jadesprite1.y;
			}

		}

		if (clear == 0 && fg != null)
			fg.clearRegion(0, 0, width, height, gc);
	}

	public final void drawAll() {
		if (bg != null)
			bg.clearRegion(0, 0, width, height, gc);
		Object aobj[] = families.toArray();
		int i = families.getSize();
		for (int j = 0; j < i; j++) {
			JadeFamily jadefamily = (JadeFamily) aobj[j];
			Object aobj1[] = jadefamily.toArray();
			int k = jadefamily.getSize();
			for (int l = 0; l < k; l++) {
				JadeSprite jadesprite = (JadeSprite) aobj1[l];
				if (jadesprite.draw)
					gc.drawImage(jadesprite.representation, jadesprite.x,
							jadesprite.y, null);
			}
		}

		if (fg != null)
			fg.clearRegion(0, 0, width, height, gc);
	}

	public JadeSpriteLayer(JadeDisplay jadedisplay,
			JadeBackground jadebackground, JadeBackground jadebackground1) {
		clear = 0;
		draw = 0;
		bg = null;
		fg = null;
		families = new JadeArray();
		width = jadedisplay.getWidth();
		height = jadedisplay.getHeight();
		setGraphics(jadedisplay.getDoubleBufferedGraphics());
		setBackground(jadebackground);
		setForeground(jadebackground1);
	}

	public JadeSpriteLayer(int i, int j, Graphics g,
			JadeBackground jadebackground, JadeBackground jadebackground1) {
		clear = 0;
		draw = 0;
		bg = null;
		fg = null;
		families = new JadeArray();
		width = i;
		height = j;
		setGraphics(g);
		setBackground(jadebackground);
		setForeground(jadebackground1);
	}

	public static final int ALL = 0;

	public static final int NEEDED = 1;

	public static final int NONE = 2;

	private int clear;

	private int draw;

	public Graphics gc;

	public int width;

	public int height;

	public JadeBackground bg;

	public JadeBackground fg;

	public JadeArray families;
}
