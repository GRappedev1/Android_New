package com.android.facebookslider;

import java.util.ArrayList;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.vinfotech.adapter.HomePageAdapter;
import com.vinfotech.adapter.HomeScreenAdapter;
import com.vinfotech.model.GetAllData;
import com.vinfotech.model.ProjectBean;
import com.vinfotech.model.ProjectCategoriesBean;
import com.vinfotech.utility.KeyboardUtil;
import com.vinfotech.utility.Utility;
import com.vinfotech.widgets.listner.SelectedItemInterface;

/* Helper for a HSV that should be scrolled by a menu View's width. */
public class ClickListenerForScrolling implements OnClickListener,
		OnItemClickListener {
	FacebookSlideView scrollView;
	View menu;
	boolean menuOut = false;
	boolean isMenuDrawer, isChat;

	EditText editText;
	private Context mContext;
	/**
	 * Menu must NOT be out/shown to start with.
	 */

	private SelectedItemInterface itemClickListner;


	public ClickListenerForScrolling() {
		super();
	}

	public ClickListenerForScrolling(FacebookSlideView scrollView, View menu,
			boolean menuOut, boolean isMenuDrawer) {
		super();
		this.isMenuDrawer = isMenuDrawer;
		this.scrollView = scrollView;
		this.menu = menu;
		this.menuOut = menuOut;
		this.isChat = false;

	}

	public ClickListenerForScrolling(FacebookSlideView scrollView, View menu,
			boolean menuOut, boolean isMenuDrawer, final EditText editText) {
		super();
		// editText.clearFocus();

		this.editText = editText;

		this.isMenuDrawer = isMenuDrawer;
		this.scrollView = scrollView;
		this.menu = menu;
		this.menuOut = menuOut;
		this.isChat = false;
		// ChatDrawer.patientId = 0;
	}

	public ClickListenerForScrolling(FacebookSlideView scrollView, View menu,
			boolean menuOut, boolean isMenuDrawer, SelectedItemInterface itemClickListner) {
		super();
		this.isMenuDrawer = isMenuDrawer;
		this.scrollView = scrollView;
		this.menu = menu;
		this.menuOut = menuOut;
		this.isChat = false;
		this.itemClickListner = itemClickListner;

	}

	public ClickListenerForScrolling(FacebookSlideView scrollView, View menu,
			boolean menuOut, boolean isMenuDrawer, boolean isChat) {
		super();

		this.editText = null;

		this.isMenuDrawer = isMenuDrawer;
		this.scrollView = scrollView;
		this.menu = menu;
		this.menuOut = menuOut;
		this.isChat = isChat;
	}

	public ClickListenerForScrolling(FacebookSlideView scrollView, View menu,
			boolean menuOut, boolean isMenuDrawer, boolean isChat, int patientID) {
		super();

		this.editText = null;

		this.isMenuDrawer = isMenuDrawer;
		this.scrollView = scrollView;
		this.menu = menu;
		this.menuOut = menuOut;
		this.isChat = isChat;
	}

	@Override
	public void onClick(final View menuView) {
		// Context context = menu.getContext();

		final int leftSpace = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 65, menuView.getContext()
						.getResources().getDisplayMetrics());
		final int menuWidth = menu.getMeasuredWidth();

		// Ensure menu is visible
		menu.setVisibility(View.VISIBLE);

		if (!menuOut) {
			// Scroll to 0 to reveal menu, Open the Menu Drawer
			if (editText != null)
				editText.requestFocus();
			final int right;
			final int left;
			if (isChat) {
				right = 0;
				left = menuWidth + (menuWidth - (leftSpace * 2));
				// ((com.vinfotech.dfa.chat.ChatDrawer) menu).onResume();
			} else {
				right = 0;
				left = 20;
			}

			scrollView.smoothScrollTo(left, right);

		} else {
			// Scroll to menuWidth so menu isn't on screen, Close Menu Drawer
			int left = menuWidth;
			if (isChat) {
				left = menuWidth - leftSpace;
			}

			scrollView.smoothScrollTo(left, 0);
		}
		if (isMenuDrawer)
			menuOut = true;
		else
			menuOut = !menuOut;
		final View wrapperView = scrollView.getWrapperView();
		if (null != scrollView.getWrapperView()) {
			wrapperView.setVisibility(menuOut ? View.VISIBLE : View.GONE);
			wrapperView.setOnClickListener(!menuOut ? null
					: new OnClickListener() {
						@Override
						public void onClick(View v) {
							// Log.v("ClickListenerForScrolling",
							// "onClick - menuWidth=" + menuWidth +
							// ", leftSpace=" + leftSpace + ", menuOut=" +
							// menuOut
							// + ", isChat=" + isChat);
							// Utility.hideSoftKeyboard(menuView);
							scrollView.postDelayed(new Runnable() {
								@Override
								public void run() {
									int left = menuWidth;
									if (isChat) {
										left = menuWidth - leftSpace;
									}

									scrollView.smoothScrollTo(left, 0);
									wrapperView.setVisibility(View.GONE);
									if (isMenuDrawer) {
										menuOut = true;
									} else {
										menuOut = !menuOut;
									}
								}
							}, 150);
						}
					});
		}
	}

	public boolean isMenuOut() {
		return menuOut;
	}

	public boolean isChat() {
		return isChat;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View menuView, int position,
			long id) {
		// TODO Auto-generated method stub

		// Context context = menu.getContext();
		int categoryId = GetAllData.getInstance().getCategories().get(position).CategoriesID;

		
		
		itemClickListner.onCategorySelect(categoryId);
		
		
		final int leftSpace = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 65, menuView.getContext()
						.getResources().getDisplayMetrics());
		final int menuWidth = menu.getMeasuredWidth();

		// Ensure menu is visible
		menu.setVisibility(View.VISIBLE);

		if (!menuOut) {
			// Scroll to 0 to reveal menu, Open the Menu Drawer
			if (editText != null)
				editText.requestFocus();
			final int right;
			final int left;
			if (isChat) {
				right = 0;
				left = menuWidth + (menuWidth - (leftSpace * 2));
				// ((com.vinfotech.dfa.chat.ChatDrawer) menu).onResume();
			} else {
				right = 0;
				left = 20;
			}

			scrollView.smoothScrollTo(left, right);

		} else {
			// Scroll to menuWidth so menu isn't on screen, Close Menu Drawer
			int left = menuWidth;
			if (isChat) {
				left = menuWidth - leftSpace;
			}

			scrollView.smoothScrollTo(left, 0);
		}
		if (isMenuDrawer)
			menuOut = true;
		else
			menuOut = !menuOut;
		final View wrapperView = scrollView.getWrapperView();
		if (null != scrollView.getWrapperView()) {
			wrapperView.setVisibility(menuOut ? View.VISIBLE : View.GONE);
			wrapperView.setOnClickListener(!menuOut ? null
					: new OnClickListener() {
						@Override
						public void onClick(View v) {

							scrollView.postDelayed(new Runnable() {
								@Override
								public void run() {
									int left = menuWidth;
									if (isChat) {
										left = menuWidth - leftSpace;
									}

									scrollView.smoothScrollTo(left, 0);
									wrapperView.setVisibility(View.GONE);
									if (isMenuDrawer) {
										menuOut = true;
									} else {
										menuOut = !menuOut;
									}
								}
							}, 150);

						}
					});
		}

	}

}
