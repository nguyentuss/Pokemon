package com.gdx.pokemon.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/**
 * Copy of Animation that allows for Textures to be used in Animations.
 */
public class TextureAnimation {

	/** Defines possible playback modes for an {@link Animation}. */
	public enum PlayMode {
		NORMAL,
		REVERSED,
		LOOP,
		LOOP_REVERSED,
		LOOP_PINGPONG,
		LOOP_RANDOM,
	}

	final Texture[] keyFrames;
	private float frameDuration;
	private float animationDuration;
	private int lastFrameNumber;
	private float lastStateTime;

	private PlayMode playMode = PlayMode.NORMAL;

	/** Constructor, storing the frame duration and key frames.
	 * 
	 * @param frameDuration the time between frames in seconds.
	 * @param keyFrames the {@link TextureRegion}s representing the frames. */
	public TextureAnimation(float frameDuration, Array<? extends Texture> keyFrames) {
		this.frameDuration = frameDuration;
		this.animationDuration = keyFrames.size * frameDuration;
		this.keyFrames = new Texture[keyFrames.size];
		for (int i = 0, n = keyFrames.size; i < n; i++) {
			this.keyFrames[i] = keyFrames.get(i);
		}

		this.playMode = PlayMode.NORMAL;
	}

	/** Constructor, storing the frame duration, key frames and play type.
	 * 
	 * @param frameDuration the time between frames in seconds.
	 * @param keyFrames the {@link TextureRegion}s representing the frames.
	 * @param playMode the animation playback mode. */
	public TextureAnimation(float frameDuration, Array<? extends Texture> keyFrames, PlayMode playMode) {

		this.frameDuration = frameDuration;
		this.animationDuration = keyFrames.size * frameDuration;
		this.keyFrames = new Texture[keyFrames.size];
		for (int i = 0, n = keyFrames.size; i < n; i++) {
			this.keyFrames[i] = keyFrames.get(i);
		}

		this.playMode = playMode;
	}

	/** Constructor, storing the frame duration and key frames.
	 * 
	 * @param frameDuration the time between frames in seconds.
	 * @param keyFrames the {@link TextureRegion}s representing the frames. */
	public TextureAnimation(float frameDuration, Texture... keyFrames) {
		this.frameDuration = frameDuration;
		this.animationDuration = keyFrames.length * frameDuration;
		this.keyFrames = keyFrames;
		this.playMode = PlayMode.NORMAL;
	}

	/** Returns a {@link TextureRegion} based on the so called state time. This is the amount of seconds an object has spent in the
	 * state this Animation instance represents, e.g. running, jumping and so on. The mode specifies whether the animation is
	 * looping or not.
	 * 
	 * @param stateTime the time spent in the state represented by this animation.
	 * @param looping whether the animation is looping or not.
	 * @return the TextureRegion representing the frame of animation for the given state time. */
	public Texture getKeyFrame (float stateTime, boolean looping) {
		// we set the play mode by overriding the previous mode based on looping
		// parameter value
		PlayMode oldPlayMode = playMode;
		if (looping && (playMode == PlayMode.NORMAL || playMode == PlayMode.REVERSED)) {
			if (playMode == PlayMode.NORMAL)
				playMode = PlayMode.LOOP;
			else
				playMode = PlayMode.LOOP_REVERSED;
		} else if (!looping && !(playMode == PlayMode.NORMAL || playMode == PlayMode.REVERSED)) {
			if (playMode == PlayMode.LOOP_REVERSED)
				playMode = PlayMode.REVERSED;
			else
				playMode = PlayMode.LOOP;
		}

		Texture frame = getKeyFrame(stateTime);
		playMode = oldPlayMode;
		return frame;
	}

	/** Returns a {@link TextureRegion} based on the so called state time. This is the amount of seconds an object has spent in the
	 * state this Animation instance represents, e.g. running, jumping and so on using the mode specified by
	 * {@link #setPlayMode(PlayMode)} method.
	 * 
	 * @param stateTime
	 * @return the TextureRegion representing the frame of animation for the given state time. */
	public Texture getKeyFrame (float stateTime) {
		int frameNumber = getKeyFrameIndex(stateTime);
		return keyFrames[frameNumber];
	}

	/** Returns the current frame number.
	 * @param stateTime
	 * @return current frame number */
	public int getKeyFrameIndex (float stateTime) {
		if (keyFrames.length == 1) return 0;

		int frameNumber = (int)(stateTime / frameDuration);
		switch (playMode) {
		case NORMAL:
			frameNumber = Math.min(keyFrames.length - 1, frameNumber);
			break;
		case LOOP:
			frameNumber = frameNumber % keyFrames.length;
			break;
		case LOOP_PINGPONG:
			frameNumber = frameNumber % ((keyFrames.length * 2) - 2);
			if (frameNumber >= keyFrames.length) frameNumber = keyFrames.length - 2 - (frameNumber - keyFrames.length);
			break;
		case LOOP_RANDOM:
			int lastFrameNumber = (int) ((lastStateTime) / frameDuration);
			if (lastFrameNumber != frameNumber) {
				frameNumber = MathUtils.random(keyFrames.length - 1);
			} else {
				frameNumber = this.lastFrameNumber;
			}
			break;
		case REVERSED:
			frameNumber = Math.max(keyFrames.length - frameNumber - 1, 0);
			break;
		case LOOP_REVERSED:
			frameNumber = frameNumber % keyFrames.length;
			frameNumber = keyFrames.length - frameNumber - 1;
			break;
		}

		lastFrameNumber = frameNumber;
		lastStateTime = stateTime;

		return frameNumber;
	}

	/** Returns the keyFrames[] array where all the TextureRegions of the animation are stored.
	 * @return keyFrames[] field */
	public Texture[] getKeyFrames () {
		return keyFrames;
	}

	/** Returns the animation play mode. */
	public PlayMode getPlayMode () {
		return playMode;
	}

	/** Sets the animation play mode.
	 * 
	 * @param playMode The animation {@link PlayMode} to use. */
	public void setPlayMode (PlayMode playMode) {
		this.playMode = playMode;
	}

	/** Whether the animation would be finished if played without looping (PlayMode#NORMAL), given the state time.
	 * @param stateTime
	 * @return whether the animation is finished. */
	public boolean isAnimationFinished (float stateTime) {
		int frameNumber = (int)(stateTime / frameDuration);
		return keyFrames.length - 1 < frameNumber;
	}

	/** Sets duration a frame will be displayed.
	 * @param frameDuration in seconds */
	public void setFrameDuration (float frameDuration) {
		this.frameDuration = frameDuration;
		this.animationDuration = keyFrames.length * frameDuration;
	}

	/** @return the duration of a frame in seconds */
	public float getFrameDuration () {
		return frameDuration;
	}

	/** @return the duration of the entire animation, number of frames times frame duration, in seconds */
	public float getAnimationDuration () {
		return animationDuration;
	}
}
