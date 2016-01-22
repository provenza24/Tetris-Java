// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JadeSprite.java

package jeu.jade.engine;

import java.awt.Image;


// Referenced classes of package jade.engine:
//            JadeFamily, JadeAnimation

public class JadeSprite {

	public final void convertTo(JadeFamily jadefamily, boolean flag) {
		setDimensions(jadefamily.width, jadefamily.height);
		if (jadefamily.animation != null)
			setRepresentation(jadefamily.animation);
		else
			setRepresentation(jadefamily.representation);
		if (!flag && jadefamily.data != null) {
			data = new int[jadefamily.data.length];
			System.arraycopy(jadefamily.data, 0, data, 0,
					jadefamily.data.length);
		}
	}

	public final void returnToOldPosition() {
		x = oldx;
		y = oldy;
	}

	public final void setPosition(int i, int j) {
		x = i;
		y = j;
	}

	public final void moveOf(int i, int j) {
		x += i;
		y += j;
	}

	public final void setDimensions(int i, int j) {
		width = i;
		height = j;
		semiWidth = i/2;
		semiHeigh = j/2;
	}

	public final boolean isAboveToKill(JadeSprite jadesprite){
		int xdiff = Math.abs(x+semiWidth - (jadesprite.x+semiWidth));
		int ydiff = y + height - jadesprite.y;
		return  xdiff<width && ydiff>0 && ydiff<10;   		
	}
	
	public final boolean isAbove(JadeSprite jadesprite) {
		return y + (height >> 1) < jadesprite.y + (jadesprite.height >> 1);
	}

	public final boolean isUnder(JadeSprite jadesprite) {
		return y + (height >> 1) > jadesprite.y + (jadesprite.height >> 1);
	}

	public final boolean isAtLeft(JadeSprite jadesprite) {
		return x + (width >> 1) < jadesprite.x + (jadesprite.width >> 1);
	}

	public final boolean isAtRight(JadeSprite jadesprite) {
		return x + (width >> 1) > jadesprite.x + (jadesprite.width >> 1);
	}

	public final void glueToLeft(JadeSprite jadesprite) {
		x = jadesprite.x - width;
	}

	public final void glueToRight(JadeSprite jadesprite) {
		x = jadesprite.x + jadesprite.width;
	}

	public final void glueToTop(JadeSprite jadesprite) {
		y = jadesprite.y - height;
	}

	public final void glueToBottom(JadeSprite jadesprite) {
		y = jadesprite.y + jadesprite.height;
	}

	public final boolean collideWith(JadeSprite jadesprite) {
		return (jadesprite.x + jadesprite.width <= x
				|| jadesprite.y + jadesprite.height <= y
				|| jadesprite.x >= x + width || jadesprite.y >= y + height) ^ true;
	}

	public final void setRepresentation(Image image) {
		stopAnimation();
		representation = image;
		animation = null;
	}

	public final void setRepresentation(JadeAnimation jadeanimation) {
		stopAnimation();
		representation = null;
		animation = jadeanimation;
		loop = jadeanimation.loop;
		pingpong = jadeanimation.pingpong;
		rewardAnimation();
	}

	public final void playAnimation() {		
		played = true;		
	}
	
	public final void stopAnimation() {
		played = false;
	}

	public final void rewardAnimation() {
		frame = 0;
		representation = animation.images[animation.frames[0]];
	}

	public final void reverseAnimation() {
		playMode = -playMode;
	}

	public final void setAnimationFrame(int i) {
		frame = i;
		representation = animation.images[frame];
	}

	public final int getAnimationFrame() {
		return frame;
	}

	final boolean animate() {
		if (played) {
			if ((pingpong || loop) && animation.delays.length < 2)
				throw new Error(
						"Animation must have at least 2 frames to be played in loop or ping-pong mode !");
			
			if (System.currentTimeMillis()-deb >= animation.delays[frame]) {								
				frame += playMode;
				if (frame >= animation.frames.length) {
					if (pingpong) {
						playMode = -playMode;
						frame -= 2;
					} else if (loop) {
						frame = 0;
					} else {
						frame--;
						played = false;
						return false;
					}
				} else if (frame < 0)
					if (loop) {
						if (pingpong) {
							playMode = -playMode;
							frame += 2;
						} else {
							frame = animation.frames.length;
						}
					} else {
						frame = 0;
						played = false;
						return false;
					}
				representation = animation.images[animation.frames[frame]];					
				deb = System.currentTimeMillis();
				return true;
			}
		}
		return false;
	}
	
	public JadeSprite(int i, int j, JadeFamily jadefamily) {		
		draw = true;
		played = false;
		playMode = 1;
		data = null;
		x = i;
		y = j;
		convertTo(jadefamily, false);
		deb = System.currentTimeMillis();
	}

	public Image representation;

	public int x;

	public int y;

	public int oldx;

	public int oldy;

	public int width;

	public int height;
	
	public int semiWidth;
	
	public int semiHeigh;

	public JadeAnimation animation;

	public boolean played;

	private int frame;

	public int playMode;

	public boolean loop;

	public boolean pingpong;

	public int data[];
	
	public long deb;
	
	public boolean draw;
}
