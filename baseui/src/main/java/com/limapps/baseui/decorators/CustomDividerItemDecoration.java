package com.limapps.baseui.decorators;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;


public class CustomDividerItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int sides;
    private int spaceTopFirstItem;
    private int spaceBottomLastItem;
    private Drawable divider;

    public CustomDividerItemDecoration(int space) {
        this.space = space;
    }

    public CustomDividerItemDecoration(int space, int spaceTopFirstItem, Drawable divider) {
        this.space = space;
        this.spaceTopFirstItem = spaceTopFirstItem;
        this.divider = divider;
    }

    public CustomDividerItemDecoration(int space, int spaceTopFirstItem) {
        this(space, spaceTopFirstItem, 0, 0);
    }

    public CustomDividerItemDecoration(int space, int spaceTopFirstItem, int sides) {
        this(space, spaceTopFirstItem, sides, 0);
    }

    public CustomDividerItemDecoration(int space, int spaceTopFirstItem, int sides, int spaceBottomLastItem) {
        this.space = space;
        this.sides = sides;
        this.spaceTopFirstItem = spaceTopFirstItem;
        this.spaceBottomLastItem = spaceBottomLastItem;
    }

    public CustomDividerItemDecoration(int space, int spaceTopFirstItem, int sides, int spaceBottomLastItem, Drawable divider) {
        this.space = space;
        this.sides = sides;
        this.spaceTopFirstItem = spaceTopFirstItem;
        this.spaceBottomLastItem = spaceBottomLastItem;
        this.divider = divider;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = sides;
        outRect.right = sides;
        outRect.bottom = space;

        int layoutPosition = parent.getChildLayoutPosition(view);

        if (layoutPosition == 0) {
            outRect.top = spaceTopFirstItem;
            if (layoutPosition == (state.getItemCount() - 1)) {
                outRect.bottom = spaceBottomLastItem;
            }
        } else if (layoutPosition == (state.getItemCount() - 1)) {
            outRect.bottom = spaceBottomLastItem;
        } else {
            outRect.top = space;
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (divider == null) {
            super.onDrawOver(c, parent, state);
        } else {
            drawCustomDivider(c, parent, state);
        }

    }

    private void drawCustomDivider(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();

        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + divider.getIntrinsicHeight();

            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }


}