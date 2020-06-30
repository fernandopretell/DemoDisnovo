package com.limapps.baseui.utils;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class ToolbarUtils {

    private static final String NAVIGATION_ICON = "navigationIcon";

    public static void toggleToolbarIconColor(Toolbar toolbar, @ColorInt int color) {
        Drawable drawable = toolbar.getNavigationIcon();
        tintDrawable(color, drawable);
        Menu menu = toolbar.getMenu();
        if (menu != null) {
            tintMenuItemIconWithColor(color, menu.getItem(0));
        }
    }

    public static void tintMenuItemIconWithResource(int color, MenuItem item) {
        tintMenuItemIcon(color, item);
    }

    public static void tintMenuItemIconWithColor(@ColorInt int color, MenuItem item) {
        tintMenuItemIcon(color, item);
    }

    public static void tintMenuItemIcon(@ColorInt int color, MenuItem item) {
        if (item != null) {
            Drawable drawable = item.getIcon();
            tintDrawable(color, drawable);
        }
    }

    public static void tintDrawable(@ColorInt int color, Drawable drawable) {
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    public static View getToolbarNavigationIcon(Toolbar toolbar) {
        CharSequence navigationContentDescription = toolbar.getNavigationContentDescription();
        //check if contentDescription previously was set
        boolean hadContentDescription = TextUtils.isEmpty(navigationContentDescription);
        String contentDescription = !hadContentDescription ? String.valueOf(navigationContentDescription) : NAVIGATION_ICON;
        toolbar.setNavigationContentDescription(contentDescription);
        ArrayList<View> potentialViews = new ArrayList<>();
        //find the view based on it's content description, set programatically or with android:contentDescription
        toolbar.findViewsWithText(potentialViews, contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        //Nav icon is always instantiated at this point because calling setNavigationContentDescription ensures its existence
        View navIcon = null;
        if (!potentialViews.isEmpty()) {
            navIcon = potentialViews.get(0); //navigation icon is ImageButton
        }
        //Clear content description if not previously present
        if (hadContentDescription) {
            toolbar.setNavigationContentDescription(null);
        }
        return navIcon;
    }

    public static void clearMenu(Toolbar toolbar) {
        Menu menu = toolbar.getMenu();
        if (menu != null) {
            menu.clear();
        }
    }
}
