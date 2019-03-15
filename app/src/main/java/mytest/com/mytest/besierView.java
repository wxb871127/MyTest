package mytest.com.mytest;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class besierView extends View {
    private float x, y;

    public besierView(Context context) {
        this(context, null);
    }

    public besierView(Context context,  AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public besierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setDither(true);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setColor(Color.parseColor("#ffff0000"));
//        paint.setStrokeWidth(4);
//
//        Path path = new Path();
//        path.moveTo(100,100);
//        path.quadTo(x, y, 300, 100);
//        canvas.drawPath(path, paint);
//        paint.setColor(Color.parseColor("#ff00ff00"));
//        paint.setStrokeWidth(10);
//        canvas.drawPoint(x, y, paint);
//        canvas.drawPoint(100, 100, paint);
//        canvas.drawPoint(300, 100, paint);



    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                invalidate();
                break;
        }
        return true;
    }
}
