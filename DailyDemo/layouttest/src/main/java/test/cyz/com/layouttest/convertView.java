package test.cyz.com.layouttest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by M on 2016/9/6.
 */
public class convertView extends View implements View.OnClickListener{

    private Paint mPaint;
    private Rect mBounds;
    private int mCount;

    public convertView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBounds = new Rect();
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(30);
        String text =  String.valueOf(mCount);
        mPaint.getTextBounds(text, 0, text.length(), mBounds);
        float textWidth = mBounds.width();
        float textheight = mBounds.height();
        canvas.drawText(text, getWidth()/2 - textWidth/2, getHeight()/2 - textheight/2, mPaint);
    }

    @Override
    public void onClick(View view) {
        mCount++;
        invalidate();
    }
}

