package com.javaxian.cleanarchitecture.customviewexample.presentation.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.javaxian.cleanarchitecture.customviewexample.R;

public class CompassView extends View {

    private final int DEFAULT_NORTH_COLOR = Color.RED;
    private final int DEFAULT_SOUTH_COLOR = Color.BLUE;
    private final int DEFAULT_EAST_COLOR = Color.YELLOW;
    private final int DEFAULT_WEST_COLOR = Color.GREEN;
    private final int DEFAULT_COMPASS_COLOR = Color.CYAN;
    private final int DEFAULT_BORDER_COLOR = Color.BLACK;
    private final float DEFAULT_BORDER_WIDTH = 1.0f;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int borderColor = DEFAULT_BORDER_COLOR;
    private float borderWidth = DEFAULT_BORDER_WIDTH;
    private int size;
    private int northColor = DEFAULT_NORTH_COLOR;
    private int southColor = DEFAULT_SOUTH_COLOR;
    private int eastColor = DEFAULT_EAST_COLOR;
    private int westColor = DEFAULT_WEST_COLOR;
    private int compassColor = DEFAULT_COMPASS_COLOR;


    private float radius;

    public CompassView(Context context) {
        super(context);

    }

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupAttributes(attrs);
    }

    public CompassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCompassBackground(canvas);
        drawCardinalPoints(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(size,size);
    }

    private void setupAttributes(AttributeSet attributeSet){

        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attributeSet, R.styleable.CompassView,
                0,0);

        compassColor = typedArray.getColor(R.styleable.CompassView_backgroundColor, DEFAULT_COMPASS_COLOR);
        borderColor = typedArray.getColor(R.styleable.CompassView_borderColor, DEFAULT_BORDER_COLOR);
        borderWidth = typedArray.getDimension(R.styleable.CompassView_borderWidth, DEFAULT_BORDER_WIDTH);

        northColor = typedArray.getColor(R.styleable.CompassView_northColor, DEFAULT_NORTH_COLOR);
        southColor = typedArray.getColor(R.styleable.CompassView_southColor, DEFAULT_SOUTH_COLOR);
        eastColor = typedArray.getColor(R.styleable.CompassView_eastColor, DEFAULT_EAST_COLOR);
        westColor = typedArray.getColor(R.styleable.CompassView_westColor, DEFAULT_WEST_COLOR);

        typedArray.recycle();
    }

    private void drawCompassBackground(Canvas canvas){

        paint.setColor(compassColor);
        paint.setStyle(Paint.Style.FILL);

         radius = size/2f;

        //Draw main circle
        canvas.drawCircle(size/2f,size/2f, radius, paint);

        paint.setColor(borderColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);

        //Draw border circle
        canvas.drawCircle(size/2f,size/2f,radius-borderWidth / 2f, paint);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth/2);

        //Draw central circle
        canvas.drawCircle(size/2f,size/2f,radius/2f,paint);

    }

    private void drawCardinalPoints(Canvas canvas){


        final float textSize = size/10;
        paint.setTextSize(textSize);

        float halfX = size/2f;
        float halfY = size/2f;

        Rect bounds = new Rect();
        String text = "N";
        Path path = new Path();

        //Draw North text and arrow
        paint.getTextBounds(text, 0, text.length(), bounds);
        paint.setColor(northColor);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawText(text, (halfX)-(bounds.width()/2f)-(bounds.left),(bounds.height())+(borderWidth), paint);

        path.moveTo((halfX),  (bounds.height()*2f)); // Top
        path.lineTo((halfX) - (bounds.height()/2f), (bounds.height()*2f) + bounds.height()); // Bottom left
        path.lineTo((halfX) + (bounds.height()/2f), (bounds.height()*2f) + bounds.height()); // Bottom right
        path.lineTo((halfX),  (bounds.height()*2f)); // Back to Top
        path.close();

        canvas.drawPath(path, paint);

        //Draw South text and arrow
        text = "S";
        paint.getTextBounds(text, 0, text.length(), bounds);
        paint.setColor(southColor);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawText(text, (halfX)-(bounds.width()/2f)-(bounds.left),(size)-(borderWidth), paint);

        path.reset();
        path.moveTo((halfX),  (size)-(bounds.height()*2f)); // Top
        path.lineTo((halfX) - (bounds.height()/2f), (size)-(bounds.height()*2f) - bounds.height()); // Bottom left
        path.lineTo((halfX) + (bounds.height()/2f), (size)-(bounds.height()*2f) - bounds.height()); // Bottom right
        path.lineTo((halfX),  (size)-(bounds.height()*2f)); // Back to Top
        path.close();

        canvas.drawPath(path, paint);

        //Draw West text and arrow
        text = "W";
        paint.getTextBounds(text, 0, text.length(), bounds);
        paint.setColor(westColor);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawText(text, (borderWidth)+(bounds.left),(halfY)+(bounds.height()/2f), paint);

        path.reset();
        path.moveTo((bounds.height()*2f),  (halfY)); // Top
        path.lineTo((bounds.height()*2f) + (bounds.height()), halfY - bounds.height()/2f); // Bottom left
        path.lineTo((bounds.height()*2f) + (bounds.height()), halfY + bounds.height()/2f); // Bottom right
        path.lineTo((bounds.height()*2f),  (halfY)); // Back to Top
        path.close();

        canvas.drawPath(path, paint);

        //Draw East text and arrow
        text = "E";
        paint.getTextBounds(text, 0, text.length(), bounds);
        paint.setColor(eastColor);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawText(text, (size-bounds.width())-(borderWidth)-(bounds.left),(halfY)+(bounds.height()/2f), paint);

        path.reset();
        path.moveTo(((size)-(bounds.height()*2f)),  (halfY)); // Top
        path.lineTo(((size)-(bounds.height()*2f)) - (bounds.height()), halfY - bounds.height()/2f); // Bottom left
        path.lineTo(((size)-(bounds.height()*2f)) - (bounds.height()), halfY + bounds.height()/2f); // Bottom right
        path.lineTo(((size)-(bounds.height()*2f)),  (halfY)); // Back to Top
        path.close();

        canvas.drawPath(path, paint);

        paint.setColor(Color.BLACK);


    }

}
