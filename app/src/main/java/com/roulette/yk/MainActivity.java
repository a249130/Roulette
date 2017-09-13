package com.roulette.yk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //定义轮盘
    private Roulette roulette;
    //定义指针（箭头）
    private ImageView image;
    //定义箭头转动的动画
    private RotateAnimation anim;
    //轮盘当前所处的位置
    private int CurrentRadiosId;
    //轮盘分割的角度
    private List<Float> listRadios;
    //旋转的总角度
    private List<Float> listImaRadios;
    //产生随机数
    private int round;
    //奖项数组
    String[] text = {"谢谢抽奖", "中奖一元", "中奖二元", "中奖五元", "中奖十元", "中奖二十元", "中奖五十元"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        roulette = (Roulette) findViewById(R.id.roulette);
        image = (ImageView) findViewById(R.id.image1);

        round = text.length;
        roulette.setText(text);
        CurrentRadiosId = 0;
        listRadios = new ArrayList<>();
        listRadios = roulette.getRadioList();
        listImaRadios = new ArrayList<>();
        for (int i = 0; i < listRadios.size(); i++) {
            float rado = listRadios.get(i);
            if (rado > 0 | rado == 0) {
                listImaRadios.add(rado);
            } else {
                listImaRadios.add(360 + rado);
            }
        }
    }

    public void rotate(View view) {
        image.setClickable(false);
        Random random = new Random();
        int sc = random.nextInt(100);
        if (sc >= 0 && sc <= 44) { //45%
            sc = 0;
        } else if (sc >= 45 && sc <= 64) { //20%
            sc = 1;
        } else if (sc >= 65 && sc <= 79) { //15%
            sc = 2;
        } else if (sc >= 80 && sc <= 89) { //10%
            sc = 3;
        } else if (sc >= 90 && sc <= 94) { //5%
            sc = 4;
        } else if (sc >= 95 && sc <= 97) { //3%
            sc = 5;
        } else if (sc >= 98 && sc <= 99) { //2%
            sc = 6;
        }
        rotateTo(CurrentRadiosId, sc);
        CurrentRadiosId = sc;
    }

    private void rotateTo(final int currentId, int rotateId) {
        float rotateRadio = 360 * 4 + listRadios.get(rotateId);
        anim = new RotateAnimation(listImaRadios.get(currentId), rotateRadio,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(1000); //旋转时间
        anim.setFillAfter(true);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                image.setClickable(true);
                Toast.makeText(MainActivity.this, text[CurrentRadiosId], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        image.startAnimation(anim);
    }
}














