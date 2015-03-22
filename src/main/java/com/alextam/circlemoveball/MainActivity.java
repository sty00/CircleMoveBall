package com.alextam.circlemoveball;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

/**
 * @author  Alex Tam
 */
public class MainActivity extends ActionBarActivity {
    private ImageView imv1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imv1= (ImageView)findViewById(R.id.imv_tb_dot1);

        setCircleMoveBall();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setCircleMoveBall()
    {
        ValueAnimator animatorMove = ValueAnimator.ofInt(0,200);
        animatorMove.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                imv1.setTranslationX((Integer) animation.getAnimatedValue());
            }
        });
        animatorMove.setDuration(1000);

        ValueAnimator animatorMoveScale = ValueAnimator.ofFloat(0.0f,100.0f);
        animatorMoveScale.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float)animation.getAnimatedValue();
                if(value<= 50.0f)
                {
                    imv1.setScaleX(1.0f + value/50);
                    imv1.setScaleY(1.0f + value/50);
                }
                else
                {
                    imv1.setScaleX(1.0f - (value - 100.0f)/50);
                    imv1.setScaleY(1.0f - (value - 100.0f)/50);
                }
            }
        });
        animatorMoveScale.setDuration(1000);

        ValueAnimator animatorBackMove = ValueAnimator.ofInt(200,0);
        animatorBackMove.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                imv1.setTranslationX((Integer) animation.getAnimatedValue());
            }
        });
        animatorBackMove.setDuration(2000);

        ValueAnimator animatorBackScale = ValueAnimator.ofFloat(0.0f,-100.0f);
        animatorBackScale.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float)animation.getAnimatedValue();
                float vluA,vluB;

                if( value > -50.0f && value < 0)
                {
                    vluA = 1.1f + value/50;
                    imv1.setAlpha(vluA);
                    imv1.setScaleX(vluA);
                    imv1.setScaleY(vluA);
                }
                else
                {
                    vluB = 0.1f - (value + 50.0f)/50;
                    imv1.setAlpha(vluB);
                    imv1.setScaleX(vluB);
                    imv1.setScaleY(vluB);
                }
            }
        });
        animatorBackScale.setDuration(2000);


        final AnimatorSet set = new AnimatorSet();

        set.play(animatorMove).with(animatorMoveScale);
        set.play(animatorMoveScale).before(animatorBackMove);
        set.play(animatorBackMove).with(animatorBackScale);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                set.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        set.start();
    }

}
