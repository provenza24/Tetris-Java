// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JadeTileMapBackground.java

package jeu.jade.engine;

import java.awt.Graphics;
import java.awt.Image;

// Referenced classes of package jade.engine:
//            JadeBackground

public class JadeTileMapBackground extends JadeBackground {

	public final void clearRegion(int i, int j, int k, int l, Graphics g) {
		g.translate(sx, sy);
		i -= sx;
		j -= sy;
		g.setClip(i, j, k, l);
		int i1 = i / tileWidth;
		i1 *= tileWidth;
		int j1 = ((i + k) - 1) / tileWidth;
		j1 *= tileWidth;
		int k1 = j / tileHeight;
		k1 *= tileHeight;
		int l1 = ((j + l) - 1) / tileHeight;
		for (l1 *= tileHeight; k1 <= l1; k1 += tileHeight) {
			for (int i2 = i1; i2 <= j1; i2 += tileWidth) {
				int j2 = values[i2 / tileWidth + (k1 / tileHeight) * scanline] - 1;
				if (j2 >= 0)
					g.drawImage(tiles[j2], i2, k1, null);
			}

		}

		g.setClip(0, 0, 10000, 10000);
		g.translate(-sx, -sy);
	}

	public final void drawTileAt(int i, int j, Graphics g) {
		g.translate(sx, sy);
		g.setClip(0, 0, 10000, 10000);
		int k = values[i + j * scanline] - 1;
		if (k >= 0)
			g.drawImage(tiles[k], i * tileWidth, j * tileHeight, null);
		g.translate(-sx, -sy);
	}

	public final void drawTileAtCoordinate(int i, int j, Graphics g) {
		g.translate(sx, sy);
		g.setClip(0, 0, 10000, 10000);
		i = (i - sx) / tileWidth;
		j = (j - sy) / tileHeight;
		int k = values[i + j * scanline] - 1;
		if (k >= 0)
			g.drawImage(tiles[k], i * tileWidth, j * tileHeight, null);
		g.translate(-sx, -sy);
	}

	public final int getValueAtCoordinate(int i, int j) {
		return values[i / tileWidth + (j / tileHeight) * scanline];
	}

	public final void setValueAtCoordinate(int i, int j, int k) {
		values[i / tileWidth + (j / tileHeight) * scanline] = k;
	}

	public final int getColumnAtPixel(int i) {
		return i / tileWidth;
	}

	public final int getRowAtPixel(int i) {
		return i / tileHeight;
	}

	public final int getPixelAtColumn(int i) {
		return i * tileWidth;
	}

	public final int getPixelAtRow(int i) {
		return i * tileHeight;
	}

	public JadeTileMapBackground(Image aimage[], int ai[], int i) {
		tiles = aimage;
		values = ai;
		scanline = i;
		tileWidth = tiles[0].getWidth(null);
		tileHeight = tiles[0].getHeight(null);
		if (ai.length % i != 0)
			throw new Error("Map value array and mapWidth mismatch !");
		else
			return;
	}

	public void setMap(int ai[]) {		
		values = ai;
	}
	
	public void setImage(Image aimage[]){
		tiles = aimage;
	}

	public int values[];

	public int scanline;

	public Image tiles[];

	public int tileWidth;

	public int tileHeight;
}
