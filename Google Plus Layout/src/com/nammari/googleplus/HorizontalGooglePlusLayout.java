package com.nammari.googleplus;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class HorizontalGooglePlusLayout extends HorizontalScrollView
		implements ScrollHandlerInterface {

	ScrollerHandler mScrollerHandler;

	public HorizontalGooglePlusLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public HorizontalGooglePlusLayout(Context context) {
		super(context);
		init();
	}

	private void init() {
		mScrollerHandler = new ScrollerHandler(this, false);
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
