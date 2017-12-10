package com.example.user.a20170525;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.kakaotalk.response.KakaoTalkProfile;
import com.kakao.util.KakaoParameterException;

public class Cacao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cacao);


    }

    public void shareKakao(View view) {
        try{
            final KakaoLink kakaoLink = KakaoLink.getKakaoLink(this);
            final KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();

            kakaoTalkLinkMessageBuilder.addText("테스트 카카오링크");

            String url = "http://postfiles1.naver.net/MjAxNzEyMDVfODUg/MDAxNTEyNDQ3OTc1NzQw.JprOIr31rpwhUZ_cxvm--H-5g54of2pXqkL_FdGvHW8g.lWxUjjtKBMr3Bdlo_Z5cD7FCsOioaugZAaDIVayOaFwg.PNG.zappyboy3/sample.png?type=w580";
            kakaoTalkLinkMessageBuilder.addImage(url, 512, 512 );

            kakaoTalkLinkMessageBuilder.addAppButton("앱 실행");

            kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder, this);

        }catch (KakaoParameterException e){
            e.printStackTrace();
        }
    }
}
