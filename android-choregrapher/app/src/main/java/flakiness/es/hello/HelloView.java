package flakiness.es.hello;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;


public class HelloView extends View {
    private String mExampleString = "";
    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private int mTouchEventCount = 0;

    private Choreographer.FrameCallback mFrameCallback = new Choreographer.FrameCallback() {
        @Override
        public void doFrame(long l) {
            Choreographer.getInstance().postFrameCallback(this);
            HelloView.this.onTick();
        }
    };

    public HelloView(Context context) {
        super(context);
        init(null, 0);
    }

    public HelloView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public HelloView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        Choreographer.getInstance().postFrameCallback(mFrameCallback);
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.HelloView, defStyle, 0);


        a.recycle();

        mExampleString = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "exampleString");
        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(32.0f);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
        mTextWidth = mTextPaint.measureText(mExampleString);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Log.d(getClass().getName(), "onDraw");

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the text.
        canvas.drawText(mExampleString,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                mTextPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Log.d(getClass().getName(), "onTouchEvent");
        mTouchEventCount++;
        setExampleString("Touches:" + mTouchEventCount);
        this.invalidate();
        //return super.onTouchEvent(event);
        return true;
    }

    public void onTick() {
        //Log.d(getClass().getName(), "onTick");
        mTouchEventCount = 0;
    }

    /**
     * Gets the example string attribute value.
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }
}
