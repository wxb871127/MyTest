package mytest.com.mytest;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    PullView pullView;
    besierView besierView;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        besierView = findViewById(R.id.bview);
        pullView = (PullView) findViewById(R.id.pullView);

//        img = findViewById(R.id.img);
//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                ObjectAnimator animator = ObjectAnimator.ofFloat(img, "translationY", 0, 100);
////                animator.setDuration(1000);
////                animator.setInterpolator(new BounceInterpolator());
////                animator.start();
//
//                Path path = new Path();
//                path.cubicTo(0.2f, 0f, 0.1f, 1f, 0.5f, 1f);
//                path.lineTo(1f, 1f);
//                ObjectAnimator animator = ObjectAnimator.ofFloat(img, View.TRANSLATION_X, 500);
//                animator.setInterpolator(PathInterpolatorCompat.create(path));
//                animator.start();
//            }
//        });



    }

    private float mTouchStartY;
    private static final float TOUCH_MOVE_MAX_Y = 300;
    private static final float SLIPPAGE_FACTOR = 0.5f;// 拖动阻力因子 0~1
    @Override public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mTouchStartY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                if (y >= mTouchStartY) {
                    float moveSize = (y - mTouchStartY) * SLIPPAGE_FACTOR;
                    float progress = moveSize >= TOUCH_MOVE_MAX_Y ? 1 : (moveSize / TOUCH_MOVE_MAX_Y);
                    pullView.setProgress(progress);
                }
                return true;
                case MotionEvent.ACTION_UP:
                    pullView.release();
                    return true; default: break;
        }
        return false;
    }

}
