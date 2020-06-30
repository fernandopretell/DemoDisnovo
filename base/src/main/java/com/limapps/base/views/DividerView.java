package com.limapps.base.views;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.limapps.base.adapters.GenericAdapterRecyclerView;
import com.limapps.baseui.animations.GeneralAnimationsKt;

import static com.limapps.baseui.animations.GeneralAnimationsKt.LONG_DURATION;
import static com.limapps.baseui.animations.GeneralAnimationsKt.SIZE_STANDARD;


public class DividerView extends RelativeLayout implements GenericAdapterRecyclerView.ItemAnimationView<DividerItem> {

    private boolean withAnimation;
    private int animationSpeed;

    private DividerItem dividerItem;

    public DividerView(Context context) {
        super(context);
        animationSpeed = LONG_DURATION;
        init();
    }

    public DividerView(Context context, boolean withAnimation) {
        super(context);
        this.withAnimation = withAnimation;
        animationSpeed = LONG_DURATION;
        init();
    }

    public DividerView(Context context, boolean withAnimation, int animationSpeed) {
        super(context);
        this.withAnimation = withAnimation;
        this.animationSpeed = animationSpeed;
        init();
    }

    public void init() {
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(marginLayoutParams);
        setGravity(Gravity.CENTER_HORIZONTAL);
    }

    @Override
    public void bind(DividerItem dividerItem, int position) {
        this.dividerItem = dividerItem;
        Resources resources = getResources();

        int marginTop = resources.getDimensionPixelSize(dividerItem.getMarginTop());
        int marginBottom = resources.getDimensionPixelSize(dividerItem.getMarginBottom());
        int marginLeft = resources.getDimensionPixelSize(dividerItem.getMarginLeft());
        int marginRight = resources.getDimensionPixelSize(dividerItem.getMarginRight());
        int thickness = resources.getDimensionPixelSize(dividerItem.getThickness());

        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        marginLayoutParams.topMargin = marginTop;
        marginLayoutParams.bottomMargin = marginBottom;
        marginLayoutParams.leftMargin = marginLeft;
        marginLayoutParams.rightMargin = marginRight;

        setLayoutParams(marginLayoutParams);
        setPadding(thickness, thickness, thickness, thickness);
        setBackgroundResource(dividerItem.getColor());
    }

    @Override
    public void setItemClickListener(final GenericAdapterRecyclerView.OnItemClickListener onItemClickListener) {

    }

    @Override
    public int getIdForClick() {
        return 0;
    }

    @Override
    public void initAnimation() {
        if (withAnimation) {
            GeneralAnimationsKt.appearFromBottomAndFadeIn(this, animationSpeed, SIZE_STANDARD, null);
        }
    }

    @Override
    public void finishAnimation() {
        setVisibility(VISIBLE);
    }

    @Override
    public boolean shouldAnimate() {
        return withAnimation;
    }

    @Override
    public DividerItem getData() {
        return dividerItem;
    }
}