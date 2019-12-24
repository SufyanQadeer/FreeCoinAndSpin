package guiderbx.rbxeventsguide;

import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity implements Animation.AnimationListener
{

    ProgressBar showProgress;
    Animation rotationAnimation;
    ImageView logoImage;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoImage =findViewById(R.id.iconrotate);

        // load the animation
        rotationAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);

        // set animation listener
        rotationAnimation.setAnimationListener(this);

        logoImage.startAnimation(rotationAnimation);
        showProgress =findViewById(R.id.progressBar);
        Window window = SplashScreen.this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(ContextCompat.getColor(SplashScreen.this,R.color.colorPrimaryDark));
        }
        new CountDownTimer(3000, 40) { //30000 milli seconds is total time, 1000 milli seconds is tick time interval

            public void onTick(long millisUntilFinished) {
                showProgress.setProgress(showProgress.getProgress()+1);
            }
            public void onFinish() {
                showProgress.setProgress(100);
                finish();

            }
        }.start();
    }
    @Override
    public void onBackPressed() {

    }
    @Override
    public void onAnimationEnd(Animation animation) {
        // Take any action after completing the animation


    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub

    }
}
