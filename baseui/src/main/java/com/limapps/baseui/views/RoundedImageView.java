package com.limapps.baseui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.limapps.baseui.R;

public class RoundedImageView extends AppCompatImageView implements Cloneable {

    private static final float DEFAULT_RADIUS = 15.0f;

    private float imageViewRadius = DEFAULT_RADIUS;
    private Path clipPath = new Path();
    private Context context;
    private RectF rect;

    public RoundedImageView(Context context) {
        super(context);
        rect = new RectF(0, 0, getWidth(), getHeight());
    }

    public RoundedImageView(Context context, float imageViewRadius) {
        super(context);
        this.imageViewRadius = imageViewRadius;
        rect = new RectF(0, 0, getWidth(), getHeight());
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(attrs);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.rounded_imageView);

        if (typedArray != null) {
            final int attributesCount = typedArray.getIndexCount();
            for (int i = 0; i < attributesCount; ++i) {
                int attr = typedArray.getIndex(i);
                if (attr == R.styleable.rounded_imageView_radius) {
                    imageViewRadius = typedArray.getDimension(attr, 0);
                }
            }
            typedArray.recycle();
        }
        rect = new RectF(0, 0, getWidth(), getHeight());
    }

    @Override
    public RoundedImageView clone() {
        RoundedImageView roundedImageView = new RoundedImageView(context);
        roundedImageView.setLayoutParams(getLayoutParams());
        roundedImageView.setImageDrawable(getDrawable());
        roundedImageView.setScaleType(getScaleType());
        roundedImageView.setId((int) (Math.random() * 100));

        return roundedImageView;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        if (width > 0 && height > 0) {
            rect.set(0, 0, width, height);
            clipPath.addRoundRect(rect, imageViewRadius, imageViewRadius, Path.Direction.CW);
            canvas.clipPath(clipPath);
        }
        super.onDraw(canvas);
    }
}
