package mytest.com.mytest;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;


//import org.jiuwo.fastel.Expression;
//import org.jiuwo.fastel.impl.ExpressionImpl;

import com.tests.ObjSerializeAndDeserializeTest;
import com.threadpool.ThreadPool;
import com.threadpool.ThreadPriority;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import dalvik.system.DexClassLoader;
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


        img = findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TranslateAnimation animation = new TranslateAnimation(0, 0,
                        0, 200);
//                ObjectAnimator animator = ObjectAnimator.ofFloat(img, "translationY", 0, 400);
                animation.setDuration(1000);
//                animation.setInterpolator(new BounceInterpolator());
//                animation.start();
//                animation.setRepeatMode();
                img.startAnimation(animation);

//                Path path = new Path();
//                path.moveTo(0, 1000);
//                ObjectAnimator mAnimator;
//                mAnimator = ObjectAnimator.ofFloat(img, View.X, View.Y, path);
//                mAnimator.setInterpolator(PathInterpolatorCompat.create(0.5f,1f));//以贝塞尔曲线的变化率作为插值器
//                mAnimator.setDuration(2000);
//                mAnimator.start();

//                Path path = new Path();
////                path.cubicTo(0.2f, 0f, 0.1f, 1f, 0.5f, 1f);
//                path.quadTo(0.5f, 1f, 1f, 0f);
//                path.lineTo(1f, 1f);
//
//                ObjectAnimator animator = ObjectAnimator.ofFloat(img, View.TRANSLATION_X, 500);
//                animator.setInterpolator(PathInterpolatorCompat.create(path));
//                animator.setDuration(3000);
//                animator.start();
            }
        });


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
        else if(view.getId() == R.id.tab)
            tab();
        else if(view.getId() == R.id.xlh)
            xlh();
        else if(view.getId() == R.id.fxlh)
            fxlh();
        else if(view.getId() == R.id.xcc)
            xcc();
    }

    public void expression() {

        int _a ;
        _a = 2;

        Map<String, Object> map = new HashMap<>();
        map.put("tz", "80");
        map.put("sg", "180");
        try {
//            Expression expression = new ExpressionImpl("2*(3+5)");
//            Object result = expression.evaluate();
//            Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show();
//            Assert.assertEquals(16L, result);
//            AviatorEvaluator.addFunction(new pow());
//            Long result = (Long) AviatorEvaluator.execute("tz/pow(sg/ 100 , 2))", map);
//            String ret = ExpressionParse.parseExpression("tz/Math.pow(sg/ 100 , Math.sqrt(4))+Math.sin(30)", map);

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

    private void tab(){
        startActivity(new Intent(this, TabActivity.class));
    }

    private void xlh(){
        ObjSerializeAndDeserializeTest.SerializePerson();
    }

    private void fxlh(){
        ObjSerializeAndDeserializeTest.DeserializePerson();
    }

    private void xcc(){

//        ThreadPriority threadPriority = new ThreadPriority();
//        threadPriority.test();

        new ThreadPool().test();

    }

}
