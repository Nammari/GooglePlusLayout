package com.nammari.googleplus;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

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
public class HorizontalGooglePlusLayout extends HorizontalScrollView implements
		ScrollHandlerInterface {

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
