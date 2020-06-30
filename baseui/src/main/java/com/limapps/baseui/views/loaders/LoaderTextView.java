package com.limapps.baseui.views.loaders;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.limapps.baseui.R;


public class LoaderTextView extends AppCompatTextView implements LoaderView {

    private LoaderController loaderController;
    private ColorStateList color;
    private boolean shouldAnimate = true;

    public LoaderTextView(Context context) {
        super(context);
        init(null);
    }

    public LoaderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LoaderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LoaderView, 0, 0);
        color = typedArray.getColorStateList(R.styleable.LoaderView_loader_color);
        loaderController = new LoaderController(this);
        setWidthWeight(typedArray.getFloat(R.styleable.LoaderView_width_weight, LoaderController.MAX_WEIGHT));
        setHeightWeight(typedArray.getFloat(R.styleable.LoaderView_height_weight, LoaderController.MAX_WEIGHT));
        loaderController.setUseGradient(typedArray.getBoolean(R.styleable.LoaderView_use_gradient,
                LoaderController.USE_GRADIENT_DEFAULT));
        typedArray.recycle();
    }

    protected void setWidthWeight(float widthWeight) {
        loaderController.setWidthWeight(widthWeight);
    }

    protected void setHeightWeight(float heightWeight) {
        loaderController.setHeightWeight(heightWeight);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        loaderController.onSizeChanged();
    }

    public void resetLoader() {
        if (!TextUtils.isEmpty(getText())) {
            resetLoaderForced();
        }
    }

    public void resetLoaderForced() {
        super.setText(null);
        loaderController.startLoading();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        loaderController.onDraw(canvas);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (loaderController != null) {
            loaderController.stopLoading();
        }
    }

    @Override
    public void setRectColor(Paint rectPaint) {
        Typeface typeface = getTypeface();

        rectPaint.setColor(color != null ? color.getDefaultColor() :
                typeface != null && typeface.getStyle() == Typeface.BOLD ?
                        LoaderController.Companion.getCOLOR_DARKER_GREY() :
                        LoaderController.Companion.getCOLOR_DEFAULT_GREY());
    }

    @Override
    public boolean valueSet() {
        return !TextUtils.isEmpty(getText());
    }

    @Override
    public boolean shouldAnimate() {
        return shouldAnimate;
    }

    @Override
    public void setShouldAnimate(boolean shouldAnimate) {
        this.shouldAnimate = shouldAnimate;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (loaderController != null) {
            loaderController.stopLoading();
        }
    }
}


