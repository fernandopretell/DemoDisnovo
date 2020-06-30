package com.limapps.baseui.decorators;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;


public class DividerItemSideDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int spaceLeftFirstItem;
    private int spaceRightLastItem;

    public DividerItemSideDecoration(int space) {
        this.space = space;
    }

    public DividerItemSideDecoration(int space, int spaceLeftFirstItem, int spaceRightLastItem) {
        this.space = space;
        this.spaceLeftFirstItem = spaceLeftFirstItem;
        this.spaceRightLastItem = spaceRightLastItem;
    }

    public DividerItemSideDecoration(int space, int spaceFirstItem) {
        this.space = space;
        this.spaceLeftFirstItem = spaceFirstItem;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = 0;
        outRect.bottom = 0;
        outRect.right = space;
        outRect.left = space;

        int layoutPosition = parent.getChildLayoutPosition(view);

        if (layoutPosition == 0) {
            outRect.left = spaceLeftFirstItem;
            if (layoutPosition == (state.getItemCount() - 1)) {
                outRect.right = spaceRightLastItem;
            }
        } else if (layoutPosition == (state.getItemCount() - 1)) {
            outRect.right = spaceRightLastItem;
        } else {
            outRect.left = space;
        }

    }


}