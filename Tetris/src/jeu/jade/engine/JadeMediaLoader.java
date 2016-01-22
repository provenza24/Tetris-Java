// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JadeMediaLoader.java

package jeu.jade.engine;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.net.URL;

public class JadeMediaLoader {

	public static final void useWithApplet(Applet applet1) {
		applet = applet1;
	}

	public static final void setPath(String s) {
		searchPath = s;
	}

	public static final AudioClip loadSound(String s) {
		try {
			if (applet == null)
				return Applet.newAudioClip((new File(searchPath + s)).toURL());
			else
				return applet.getAudioClip(new URL(applet.getCodeBase(),
						searchPath + s));
		} catch (Exception exception) {
			System.out.println("Can't load sound " + s + " "
					+ exception.getMessage());
		}
		return null;
	}
	
	public static final Image loadImage(String s) {
		try {
			Image image;
			if (applet != null)
				image = applet.getImage(applet.getCodeBase(), searchPath + s);
			else
				image = Toolkit.getDefaultToolkit().createImage(searchPath + s);
			mt.addImage(image, 0);
			mt.waitForID(0);
			mt.removeImage(image,0);
			if (image.getWidth(null) == -1)
				System.out.println("Failed to load " + searchPath + s);
			return image;
		} catch (Exception exception) {
			System.out.println("Can't load image " + searchPath + s + " "
					+ exception.getMessage());
		}
		return null;
	}

	public static final Font loadFont(String s) {
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File(searchPath
					+ s));
			if (font != null)
				return font;
			else
				System.out.println("Failed to load " + searchPath + s);

		} catch (Exception e) {
			System.out.println("Can't load font " + searchPath + s + " "
					+ e.getMessage());
		}
		return null;
	}

	public static final File loadFile(String s) {

		try {
			File file = new File(searchPath + s);
			if (file != null)
				return file;
			else
				System.out.println("Failed to load " + searchPath + s);

		} catch (Exception e) {
			System.out.println("Can't load file " + searchPath + s + " "
					+ e.getMessage());
		}
		return null;
	}

	public static final Image computePixelsToImage(int ai[], int i) {
		int j = ai.length;
		int k = j / i;
		return Toolkit.getDefaultToolkit().createImage(
				new MemoryImageSource(i, k, ai, 0, i));
	}

	public static final int[] getPixelsFromImage(Image image) {
		int i = image.getWidth(null);
		int j = image.getHeight(null);
		int k = i * j;
		int ai[] = new int[k];
		try {
			(new PixelGrabber(image, 0, 0, i, j, ai, 0, i)).grabPixels();
		} catch (Exception exception) {
			System.out.println("Error during pixels grabbing..");
		}
		return ai;
	}

	private static final int[] flipPixels(int ai[], int i) {
		int j = 0;
		int k = ai.length;
		int l = k / i >> 1;
		k -= i;
		int ai1[] = new int[i];
		for (int i1 = 0; i1 < l; i1++) {
			System.arraycopy(ai, k, ai1, 0, i);
			System.arraycopy(ai, j, ai, k, i);
			System.arraycopy(ai1, 0, ai, j, i);
			j += i;
			k -= i;
		}

		return ai;
	}

	private static final int[] mirrorPixels(int ai[], int i) {
		int j = ai.length / i;
		int k = i >> 1;
		int l = k - 1;
		int i1 = i - k;
		for (int j1 = 0; j1 < j; j1++) {
			for (int k1 = 0; k1 < k; k1++) {
				int l1 = ai[l];
				ai[l] = ai[i1];
				ai[i1] = l1;
				l--;
				i1++;
			}

			i1 += i - k;
			l += i + k;
		}

		return ai;
	}

	private static final int[] rotate90Pixels(int ai[], int i) {
		int j = ai.length;
		int ai1[] = new int[j];
		j /= i;
		int k = 0;
		for (int i1 = 0; i1 < j; i1++) {
			int l = i1;
			for (int j1 = 0; j1 < i; j1++) {
				ai1[l] = ai[k++];
				l += j;
			}

		}

		return ai1;
	}

	public static final Image rotateImage90(Image image) {
		return computePixelsToImage(rotate90Pixels(getPixelsFromImage(image),
				image.getWidth(null)), image.getHeight(null));
	}

	public static final Image flipImage(Image image) {
		int i = image.getWidth(null);
		return computePixelsToImage(flipPixels(getPixelsFromImage(image), i), i);
	}

	public static final Image mirrorImage(Image image) {
		int i = image.getWidth(null);
		return computePixelsToImage(mirrorPixels(getPixelsFromImage(image), i),
				i);
	}

	public static final Image[] parseImage(Image image, int i, int j) {
		if (j == 0 || i == 0)
			return null;
		try {
			int k = image.getWidth(null);
			int l = image.getHeight(null);
			int i1 = k / i;
			int j1 = l / j;
			java.awt.image.ImageProducer imageproducer = image.getSource();
			Image aimage[] = new Image[j * i];
			int k1 = 0;
			for (int l1 = 0; l1 < j; l1++) {
				for (int i2 = 0; i2 < i; i2++) {
					try {
						Thread.sleep(1L);
					} catch (Exception exception1) {
					}
					CropImageFilter cropimagefilter = new CropImageFilter(i2
							* i1, l1 * j1, i1, j1);
					aimage[k1] = fr.createImage(new FilteredImageSource(
							imageproducer, cropimagefilter));
					mt.addImage(aimage[k1], 0);
					k1++;
				}

			}

			mt.waitForAll();			
			for (int z=0; z<k1; z++){
				mt.removeImage(aimage[z], 0);
			}
			return aimage;
		} catch (Exception exception) {
			System.out.println("Error parseImage(..)" + exception.getMessage());
		}
		return null;
	}

	public JadeMediaLoader() {
	}

	private static Frame fr;

	private static MediaTracker mt;

	private static Applet applet = null;

	private static String searchPath = "";

	static {
		fr = new Frame();
		mt = new MediaTracker(fr);
	}
}
