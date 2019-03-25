package mytest.com.mytest;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.PathInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexClassLoader;
import expression.ExpressionFuncOpt;
import expression.ExpressionParse;
import mydex.com.mydex.IShowToast;

public class MainActivity extends AppCompatActivity {
    besierView besierView;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        int ssy = 190;
//        String xydj = ssy < 140 ? "" : (ssy > 140 && ssy < 160) ? "3" : (ssy > 160 && ssy < 180) ? "2" : "1";




//        besierView = findViewById(R.id.bview);


//        img = findViewById(R.id.img);
//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                ObjectAnimator animator = ObjectAnimator.ofFloat(img, "translationY", 0, 100);
////                animator.setDuration(1000);
////                animator.setInterpolator(new BounceInterpolator());
////                animator.start();
//
////                Path path = new Path();
////                path.moveTo(0, 1000);
////                ObjectAnimator mAnimator;
////                mAnimator = ObjectAnimator.ofFloat(img, View.X, View.Y, path);
////                mAnimator.setInterpolator(PathInterpolatorCompat.create(0.5f,1f));//以贝塞尔曲线的变化率作为插值器
////                mAnimator.setDuration(2000);
////                mAnimator.start();
//
//                Path path = new Path();
////                path.cubicTo(0.2f, 0f, 0.1f, 1f, 0.5f, 1f);
//                path.quadTo(0.5f, 1f, 1f, 0f);
//                path.lineTo(1f, 1f);
//
//                ObjectAnimator animator = ObjectAnimator.ofFloat(img, View.TRANSLATION_X, 500);
//                animator.setInterpolator(PathInterpolatorCompat.create(path));
//                animator.setDuration(3000);
//                animator.start();
//            }
//        });


    }

    public void ss(String ...a){
        for (String s : a) {

        }
    }

    public void onClick(View view){
        if(view.getId() == R.id.pullView)
            startActivity(new Intent(this, PullActivity.class));
        else if(view.getId() == R.id.dex)
            loadDexFile();
        else if(view.getId() == R.id.expression)
            expression();
    }

    public void expression() {
        Map<String, Object> map = new HashMap<>();
        map.put("tz", "68");
        map.put("sg", "174");
        try {
            String ret = ExpressionParse.parseExpression("tz/Math.pow(sg/ 100 , Math.sqrt(4))+Math.sin(30)", map);
//            List<ExpressionFuncOpt> list = ExpressionFuncOpt.getFunctionExpression("tz/Math.pow(sg/ 100 , Math.sqrt(4))+Math.sin(30)");
//            Log.e("xx","" );
            Toast.makeText(this, ret, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void loadDexFile() {
        File dexOutputDir = getDir("dex1", 0);
        Log.e("xxxxxxx dexOutputDir = ", dexOutputDir.getAbsolutePath());
        String dexPath = Environment.getExternalStorageDirectory() + File.separator + "ishowtoast_dx.jar";
        Log.e("xxxxxxx dexPath = ", dexPath);
        DexClassLoader loader =
                new DexClassLoader(dexPath, dexOutputDir.getAbsolutePath(), null, getClassLoader());
        try {
            Class clz = loader.loadClass("mydex.com.mydex.ShowToastImpl");
            IShowToast impl= (IShowToast) clz.newInstance();//通过该方法得到IShowToast类
            if (impl!=null)
                impl.showToast(this);//调用打开弹窗
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
