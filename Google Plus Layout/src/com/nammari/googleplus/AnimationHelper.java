package com.nammari.googleplus;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Queue;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

/*
 * Copyright (C) 2013 Ahmed Nammari
 * 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

	public void playAnimation(View v, int animation_id, int animator_id) {
		if (v == null)
			throw new IllegalArgumentException("view cant be null");
		mQueue.add(new QueueItem(new WeakReference<View>(v), animation_id,
				animator_id));
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
							Animation mAnimation = AnimationUtils
									.loadAnimation(item.mView.get()
											.getContext(), item.animationId);
							mAnimation
									.setAnimationListener(AnimationHelper.this);
							mAnimation.setRepeatCount(0);
							item.mView.get().startAnimation(mAnimation);
							if (Util.isHoneyComb()) {
								ObjectAnimator animator = (ObjectAnimator) AnimatorInflater
										.loadAnimator(item.mView.get()
												.getContext(), item.animatorId);
								animator.setTarget(item.mView.get());
								animator.start();
							}
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

		public QueueItem(WeakReference<View> mView, int animationId,
				int animatorId) {
			super();
			this.mView = mView;
			this.animationId = animationId;
			this.animatorId = animatorId;
		}

		WeakReference<View> mView;
		int animationId;
		int animatorId;
	}

}
