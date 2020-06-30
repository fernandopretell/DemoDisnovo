package com.limapps.baseui.decorators;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacingTop;
    private int spacingBottom;
    private int spacingLeft;
    private int spacingRight;
    private int spacingFirstTop = 0;
    private boolean includeEdge;

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        spacingTop = spacing;
        spacingBottom = spacing;
        spacingLeft = spacing;
        spacingRight = spacing;
        spacingFirstTop = spacing;
        this.includeEdge = includeEdge;
    }

    public GridSpacingItemDecoration(int spanCount, int spacingLeft, int spacingRight, int spacingTop, int spacingBottom, int spacingFirstTop, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacingTop = spacingTop;
        this.spacingBottom = spacingBottom;
        this.spacingLeft = spacingLeft;
        this.spacingRight = spacingRight;
        this.spacingFirstTop = spacingFirstTop;
        this.includeEdge = includeEdge;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int column = position % spanCount;

        if (includeEdge) {
            outRect.left = spacingLeft - column * spacingLeft / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacingRight / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacingTop;
            }
            outRect.bottom = spacingBottom; // item bottom
        } else {
            outRect.left = column * spacingLeft / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacingRight - (column + 1) * spacingRight / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacingTop; // item top
            }
        }

    }
}