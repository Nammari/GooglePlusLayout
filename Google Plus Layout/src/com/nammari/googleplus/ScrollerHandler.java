/**
 * 
 */
package com.nammari.googleplus;

import com.nammari.googleplus.R;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

/**
 * @author nammari
 * 
 */

class ScrollerHandler implements OnGlobalLayoutListener, ScrollHandlerInterface {

	private ViewGroup rootView;
	private int[] mutuableInteger = new int[] { INVALID_CHILD_INDEX };// this
																		// variable
																		// to
																		// keep
	// tracking the
	// animation on
	// each child so we
	// won't
	// play it twice on the
	// same
	// child . Recall , the
	// animation happened in
	// a
	// serial order and only once .

	// this is a helper class that guarantee that only one animation run at a
	// time on it queue
	private AnimationHelper mAnimationHelper = new AnimationHelper();

	// flag
	private final boolean isVerticalScrollView;

	/**
	 * 
	 */
	public ScrollerHandler(View rootView, boolean isVerticalScrollView) {
		if (rootView == null) {
			throw new IllegalArgumentException("Root view can't be null!!");
		}
		if (!(rootView instanceof HorizontalScrollView)
				&& !(rootView instanceof ScrollView)) {
			throw new IllegalArgumentException(
					"what are you sending to us ??? this should be a scoll view !!!");
		}
		this.rootView = (ViewGroup) rootView;
		this.isVerticalScrollView = isVerticalScrollView;
		registerGlobalLayoutListenerOnRootView();
	}

	private void registerGlobalLayoutListenerOnRootView() {
		rootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onGlobalLayout() {

		if (rootView.getChildCount() == 0) {// this to handle the case if the
											// Child is
			// loaded at runtime .
			return;
		}
		ViewGroup directChild = (ViewGroup) rootView.getChildAt(0);
		final int childCount = ((ViewGroup) directChild).getChildCount();

		if (childCount == 0) {
			// we would like to keep listening ,perhaps the views will be added
			// at runtime of the child child
			return;
		}
		// now we don't want to keep listening because childs of child is
		// added now so we can start the initial animation .
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
		} else {
			rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
		}

		// fake scroll
		// this to start the animation on the first visible item(s) in the
		// scroll view i.e will cause the onScrollChange to be invoked
		// (Initially needed)
		if (isVerticalScrollView) {
			rootView.scrollTo(0, 1);// scroll the view in the y-axis
		} else {
			// horizontal
			rootView.scrollTo(1, 0); // scroll in the x axis
		}
	}

	/*
	 * package visible only to prevent clients from calling it and bring bugs to
	 * their life
	 */
	void onScrollChange() {
		// this will
		// loop all children
		// if the child top is less or equal the bottom of the this view (
		// scroll view) and didnt played the animation
		// play it
		if (mutuableInteger[0] < 0) {
			mutuableInteger[0] = 0;
		}
		View child;
		boolean flipAnimation = false;
		final int[] location = new int[2];
		final ViewGroup directChild = (ViewGroup) rootView.getChildAt(0);
		final int childCount = ((ViewGroup) directChild).getChildCount();
		for (int i = mutuableInteger[0]; i < childCount; ++i) {
			child = directChild.getChildAt(i);
			if (child != null) {
				child.getLocationOnScreen(location);
				if (animationNeedToBeRunOnChild(location)) {
					++mutuableInteger[0];
					child.setVisibility(View.INVISIBLE);// hide child and don't
														// worry animation have
														// fillAfter = true
					playAnimation(child, flipAnimation);

				} else {
					// no need to continue checking child
					break;
				}
			}
			flipAnimation = !flipAnimation;
		}

	}

	private boolean animationNeedToBeRunOnChild(int[] childLocationOnScreen) {
		boolean result = false;
		if (isVerticalScrollView) {
			if (childLocationOnScreen[1] <= rootView.getBottom()) {
				result = true;
			}
		} else {
			if (childLocationOnScreen[0] <= rootView.getRight()) {
				result = true;
			}
		}

		return result;

	}

	private void playAnimation(View child, boolean flipAnimation) {

		mAnimationHelper.playAnimation(child, AnimationUtils.loadAnimation(
				rootView.getContext(), flipAnimation ? R.anim.slide_up_right
						: R.anim.slide_up_left));
	}

	// use only when you want to save state for animation/ be careful with this
	// can be very
	// dangerous
	// public int getMutuableIntegerVaue() {
	// return mutuableInteger[0];
	// }
	//
	// public void setMutuableIntegerValue(int val) {
	// mutuableInteger[0] = val;
	// }

	// use only when you want to save state for animation
	public void saveState(Bundle args) {
		if (args != null) {

			args.putInt("__mutable_integer__", mutuableInteger[0]);
		}
	}

	public void restoreState(Bundle args) {
		if (args != null) {
			mutuableInteger[0] = args.getInt(BUNDLE_KEY_MUTABLE_INTEGER,
					INVALID_CHILD_INDEX);
		}
	}

	private static final int INVALID_CHILD_INDEX = -1;
	private static final String BUNDLE_KEY_MUTABLE_INTEGER = "__mutable_integer__";

	public boolean didAniamtionPlayedOnChild(int position) {
		boolean result = false;
		Log.d("position", "" + position);
		Log.d("mutuableInteger", mutuableInteger[0] + "");
		if (mutuableInteger[0] >= position) {
			result = true;
		}

		return result;
	}

}
