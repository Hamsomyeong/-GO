package com.example.MyRefrigerator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class ActionActivity extends AppCompatActivity {
    //변수 선언
    Handler mHandler;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        LottieAnimationView lottie = (LottieAnimationView) findViewById(R.id.lottie); // 문 열고 닫히는 애니메이션 가져옴
        LottieAnimationView among = (LottieAnimationView) findViewById(R.id.among); // 어몽어스 애니메이션 가져옴
        ImageView imageView = (ImageView) findViewById(R.id.shadow); // 어몽어스가 문에 들어가는 것 처럼 보이게 하기위해 덧댄 이미지 가져옴

        Animation Activitytrance = AnimationUtils.loadAnimation(getApplicationContext(), // 애니메이션 효과를 만들어준 xml을 Activitytrance 변수에 저장
                R.anim.alphascale);

        AnimationSet set = new AnimationSet(true); //
        set.setInterpolator(new AccelerateInterpolator()); //

        Animation an1 = new AlphaAnimation(1.0f, 0.0f); //투명한 애니메이션 효과를 an1 변수에 저장
        an1.setDuration(1900); // 투명한 애니메이션 시간을 1.9초

        Animation an2 = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f, 50, 50.0f, 50, 50.0f); // 화면 안의 내용의 크기를 좌표값을 넣어 키움
        an2.setDuration(1900); //  커지는 시간 1.9초

        set.addAnimation(an1); // set 변수에 투명 애니메이션을 저장
        set.addAnimation(an2); // set 변수에 크기가 커지는 애니메이션 저장

        RelativeLayout a5 = (RelativeLayout) findViewById(R.id.total); // 화면 레이아웃 전체를 변수 a5에 저장

        a5.startAnimation(Activitytrance); // a5 변수에 애니메이션 효과 xml 이 저장되있는 Activity 변수를 저장
        new Handler().postDelayed(new Runnable() { // 핸들러를 사용하여 다음 화면으로 전환효과 구현현
            @Override
            public void run() {
                imageView.setImageResource(R.drawable.shadow); // 3초뒤 실행할 작업
            }
        }, 1300);

        /////////////////////////////////////////////////////수정할 부분
        lottie.playAnimation();
        lottie.loop(true);
        among.playAnimation();
        among.loop(true);
        lottie.bringToFront();
        among.bringToFront();
        imageView.bringToFront();

        Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.walkingamongus);

        among.startAnimation(animSlide);

        mHandler = new Handler(){

            @Override
            public void handleMessage(@NonNull Message msg) {
                if(i == 0) {
                    sendEmptyMessageDelayed(3, 1900);
                    i = 1;
                    startActivity(new Intent(ActionActivity.this, MainActivity2.class));
                }
            }
        };
        mHandler.sendEmptyMessageDelayed(3,1700);
    }



    public void setupLottie(View view){
        LottieAnimationView lottie = view.findViewById(R.id.lottie);
        LottieAnimationView among = view.findViewById(R.id.among);
        among.setAnimation("amongus.json");
        lottie.setAnimation("furtune.json");
        lottie.loop(true);   // or
        lottie.setRepeatCount(10);
        lottie.playAnimation();
        among.loop(true);   // or
        among.setRepeatCount(10);
        among.playAnimation();
    }

}




