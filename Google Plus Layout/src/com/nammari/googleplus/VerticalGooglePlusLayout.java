package com.nammari.googleplus;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.ScrollView;

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
public class VerticalGooglePlusLayout extends ScrollView implements
		ScrollHandlerInterface {

	ScrollerHandler mScrollerHandler;

	public VerticalGooglePlusLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();

	}

	public VerticalGooglePlusLayout(Context context) {
		super(context);
		init();
	}

	private void init() {
		mScrollerHandler = new ScrollerHandler(this, true);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);

		if (mScrollerHandler != null) {
			mScrollerHandler.onScrollChange();
		}
	}

	@Override
	public void saveState(Bundle args) {
		// delegate to scrollHandler object
		if (mScrollerHandler != null) {
			mScrollerHandler.saveState(args);
		}

	}

	@Override
	public void restoreState(Bundle args) {
		// delegate to scrollHandler object
		if (mScrollerHandler != null) {
			mScrollerHandler.restoreState(args);
		}

	}

	@Override
	public boolean didAniamtionPlayedOnChild(int position) {
		// delegate to scrollHandler object
		if (mScrollerHandler != null) {
			return mScrollerHandler.didAniamtionPlayedOnChild(position);
		}
		return false;
	}
}

/**
 * 
 * 
 * package com.pmak8.pagelayout;
 * 
 * import java.util.ArrayList; import java.util.List;
 * 
 * import android.content.Context; import android.os.Build; import
 * android.util.AttributeSet; import android.view.View; import
 * android.view.ViewGroup; import
 * android.view.ViewTreeObserver.OnGlobalLayoutListener; import
 * android.view.animation.AnimationUtils; import android.widget.ScrollView;
 * 
 * import com.pmak8.pagelayout.R;
 * 
 * public class VerticalGooglePlusPageLayout extends ScrollView implements
 * OnGlobalLayoutListener { public VerticalGooglePlusPageLayout(Context context,
 * AttributeSet attrs) { super(context, attrs); initLayoutObserver();
 * 
 * }
 * 
 * // this is a simulation for a mutable integer since there is no mutable //
 * integer object and i dont want to create a java class // purpose : this will
 * keep tracking the child position that played the // animation // so each
 * child view play the animation only once private final int[]
 * currentChildAnimatedPosition = new int[] { -1 };
 * 
 * // This is a helper class that guarantee the serialization for child //
 * animation private AnimationHelper mAnimationHelper = new AnimationHelper();
 * 
 * // private List<BooleanWrapper> childPoisitionsAnimationPlayed;
 * 
 * public VerticalGooglePlusPageLayout(Context context) { super(context);
 * initLayoutObserver(); }
 * 
 * private void initLayoutObserver() { // listing to layout change
 * getViewTreeObserver().addOnGlobalLayoutListener(this); }
 * 
 * @Override public void onGlobalLayout() { if (getChildCount() == 0) { return;
 *           } if ( ((ViewGroup) getChildAt(0)).getChildCount() == 0) { return;
 *           } if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
 *           getViewTreeObserver().removeOnGlobalLayoutListener(this); } else {
 *           getViewTreeObserver().removeGlobalOnLayoutListener(this); } // if
 *           (childPoisitionsAnimationPlayed == null) { //
 *           childPoisitionsAnimationPlayed = new ArrayList<BooleanWrapper>( //
 *           childCount); // for (int i = 0; i < childCount; ++i) { //
 *           childPoisitionsAnimationPlayed.add(new BooleanWrapper(false)); // }
 *           // }
 * 
 * 
 *           // simulate a scroll to start the animation on initially visible
 *           child // inside the scroll view . Actually this will cause the
 *           onScrollChange // callback to be invoked scrollTo(0, 1);
 * 
 *           }
 * @Override protected void onScrollChanged(int l, int t, int oldl, int oldt) {
 *           super.onScrollChanged(l, t, oldl, oldt); // loop all children // if
 *           the child top is less or equal the bottom of the this view ( //
 *           scroll view) and didnt played the animation // play it View child;
 *           boolean animationInverse = false; int[] location = new int[2];
 *           ViewGroup directChild = (ViewGroup) getChildAt(0); final int
 *           childCount = ((ViewGroup) directChild).getChildCount(); for (int i
 *           = 0; i < childCount; ++i) { child = directChild.getChildAt(i); if
 *           (child != null) { child.getLocationOnScreen(location); if
 *           (location[1] <= getBottom()) { if (i >) {
 *           childPoisitionsAnimationPlayed.get(i).setAnimated(true); if
 *           (animationInverse) { child.setVisibility(View.INVISIBLE);
 *           mAnimationHelper.playAnimation(child,
 *           AnimationUtils.loadAnimation(getContext(), R.anim.slide_up_right));
 *           } else { child.setVisibility(View.INVISIBLE);
 *           mAnimationHelper.playAnimation(child,
 *           AnimationUtils.loadAnimation(getContext(), R.anim.slide_up_left));
 * 
 *           } } } } animationInverse = !animationInverse; }
 * 
 *           }
 * 
 *           public List<BooleanWrapper> getChildPoisitionsAnimationPlayed() {
 *           return childPoisitionsAnimationPlayed; }
 * 
 *           public void setChildPoisitionsAnimationPlayed( List<BooleanWrapper>
 *           childPoisitionsAnimationPlayed) {
 *           this.childPoisitionsAnimationPlayed =
 *           childPoisitionsAnimationPlayed; }
 * 
 *           }
 * 
 * 
 */
