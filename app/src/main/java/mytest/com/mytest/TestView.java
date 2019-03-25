package mytest.com.mytest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class TestView extends View {
    Paint mPaint;

    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context,  @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestView(Context context,  @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count = canvas.save();
        Paint paint = new Paint();
//        paint.setColor(Color.BLACK);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        int cx = 500;
        int cy = 500;
        int radius = 100;
        int margin = 10;
        canvas.drawCircle(cx,cy ,radius ,paint );
        Drawable drawable = getResources().getDrawable(R.drawable.circle_drawable );
        int left =  cx - radius + margin;
        int top = cy - radius + margin;
        int right = cx + radius - margin;
        int bottom = cy + radius - margin;
        drawable.setBounds(left, top, right,bottom);
//        paint.setColor(Color.GREEN);
//        canvas.drawRect(left, top, right,bottom ,paint);
//        canvas.clipRect(drawable.getBounds()); //绘制
        drawable.draw(canvas);
//        canvas.restore();
//        canvas.restoreToCount(count);
    }

    private void clipRect(Canvas canvas){
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        Paint paint = new Paint();
        paint.setColor(Color.LTGRAY);

        TextPaint textPaint=new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(24);

        canvas.drawRect(0, 0, width, height, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        canvas.drawRect(50, 50, 300, 300,paint);
        paint.setColor(Color.CYAN);
        canvas.drawRect(250, 250, 500, 500,paint);
        paint.setColor(Color.BLACK);
        canvas.drawRect(50, 50, 500, 500,paint);

        //========================  default & INTERSECT  ==========================
        canvas.save();
        canvas.clipRect(550, 0, 800, 300);
        canvas.clipRect(750, 250, 1000, 500);
        canvas.clipRect(550, 0, 1000, 500);
        canvas.drawColor(Color.YELLOW);
        canvas.restore();

        canvas.drawText("default & INTERSECT",700,550,textPaint);

        //========================  DIFFERENCE  ==========================
        canvas.save();
        canvas.clipRect(50, 600, 300, 900);
        canvas.clipRect(250, 850, 500, 1100, Region.Op.DIFFERENCE);
        canvas.clipRect(50, 600, 500, 1100);
        canvas.drawColor(Color.YELLOW);
        canvas.restore();

        canvas.drawText("DIFFERENCE",200,1150,textPaint);
//
//        //========================  REVERSE_DIFFERENCE  ==========================
//        canvas.save();
//        canvas.clipRect(550, 600, 800, 900);
//        canvas.clipRect(750, 850, 1000, 1100, Region.Op.REVERSE_DIFFERENCE);
//        canvas.clipRect(550, 600, 1000, 1100);
//        canvas.drawColor(Color.YELLOW);
//        canvas.restore();
//
//        canvas.drawText("REVERSE_DIFFERENCE",700,1150,textPaint);
//
//        //========================  REPLACE  ==========================
//        canvas.save();
//        canvas.clipRect(50, 1150, 300, 1450);
//        canvas.clipRect(250, 1400, 500, 1650, Region.Op.REPLACE);
//        canvas.clipRect(50, 1150, 500, 1650);
//        canvas.drawColor(Color.YELLOW);
//        canvas.restore();
//
//        canvas.drawText("REPLACE",200,1700,textPaint);
//
//        //========================  XOR  ==========================
//        canvas.save();
//        canvas.clipRect(550, 1150, 800, 1450);
//        canvas.clipRect(750, 1400, 1000, 1650, Region.Op.XOR);
//        canvas.clipRect(550, 1150, 1000, 1650);
//        canvas.drawColor(Color.YELLOW);
//        canvas.restore();
//
//        canvas.drawText("XOR",700,1700,textPaint);
//
//        //========================  UNION  ==========================
//        canvas.save();
//        canvas.clipRect(50, 1700, 300, 2000);
//        canvas.clipRect(250, 1950, 500, 2200, Region.Op.UNION);
//        canvas.clipRect(50, 1700, 500, 2200);
//        canvas.drawColor(Color.YELLOW);
//        canvas.restore();
//
//        canvas.drawText("UNION",200,2250,textPaint);
    }

    private void drawScene(Canvas canvas) {
        canvas.clipRect(0, 0, 500, 500);
        canvas.drawColor(Color.WHITE);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0, 0, 300, 300, mPaint);

        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(40);
        canvas.drawText("A", 140, 140, mPaint);

        mPaint.setColor(Color.GREEN);
        canvas.drawRect(200, 200, 500, 500, mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawText("B", 350, 350, mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawRect(200, 200, 300, 300, mPaint);

    }

    /**
     * 绘制正方形
     *
     * @param canvas
     */
    private void drawSquare(Canvas canvas) {
        int TOTAL_SQUARE_COUNT = 20;
        int mHalfWidth = 400;
        int mHalfHeight = 400;

        Rect mSquareRect = new Rect(0,0, 2*mHalfWidth, 2*mHalfHeight);
        for (int i = 0; i < TOTAL_SQUARE_COUNT; i++) {
            // 保存画布
            canvas.save();
            float fraction = (float) i / TOTAL_SQUARE_COUNT;
            // 将画布以正方形中心进行缩放
            canvas.scale(fraction, fraction, mHalfWidth, mHalfHeight);
            canvas.drawRect(mSquareRect, mPaint);
            // 画布回滚
            canvas.restore();
        }
    }

}
