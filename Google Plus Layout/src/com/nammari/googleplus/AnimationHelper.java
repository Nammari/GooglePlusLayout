package com.nammari.googleplus;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Queue;

import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

/**
 * 
 * @author nammari
 */

/**
 * 
 * A utility class that ensure all animation play in serial manner . (one after
 * another)
 * 
 */
public class AnimationHelper implements AnimationListener {
	private Queue<QueueItem> mQueue = new LinkedList<QueueItem>();
	private Handler mHanlder = new Handler();
	private boolean isAnimationRunning = false;

	public AnimationHelper() {

	}

	@Override
	public void onAnimationEnd(Animation animation) {

		mHanlder.post(new Runnable() {

			@Override
			public void run() {
				isAnimationRunning = false;
				startAnimationIfApplicable();

			}
		});

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onAnimationStart(Animation animation) {
		mHanlder.post(new Runnable() {

			@Override
			public void run() {
				isAnimationRunning = true;

			}
		});

	}

	public void playAnimation(View v, Animation animation) {
		if (v == null)
			throw new IllegalArgumentException("view cant be null");
		if (animation == null) {
			throw new IllegalArgumentException("animation cant be null");
		}
		animation.setRepeatCount(0);
		mQueue.add(new QueueItem(new WeakReference<View>(v), animation));
		startAnimationIfApplicable();
	}

	private void startAnimationIfApplicable() {

		mHanlder.post(new Runnable() {

			@Override
			public void run() {
				if (!isAnimationRunning && mQueue != null && !mQueue.isEmpty()) {
					QueueItem item = mQueue.poll();
					if (item != null && item.mView.get() != null) {
						try {
							item.mAnimation
									.setAnimationListener(AnimationHelper.this);
							item.mView.get().startAnimation(item.mAnimation);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} else {
						startAnimationIfApplicable();
					}

				}

			}
		});
	}

	private static class QueueItem {

		public QueueItem(WeakReference<View> mView, Animation mAnimation) {
			super();
			this.mView = mView;
			this.mAnimation = mAnimation;
		}

		WeakReference<View> mView;
		Animation mAnimation;
	}

}
