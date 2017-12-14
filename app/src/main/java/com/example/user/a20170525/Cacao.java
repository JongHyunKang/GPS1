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

            //kakaoTalkLinkMessageBuilder.addText("테스트 카카오링크");

            String url = "https://kauth.kakao.com/oauth/authorize?client_id=c03758c161b681dd97c2bab13376ce41&redirect_uri=http://172.16.49.123:8080/WearisMomServer/catch&response_type=code                                ";
            //kakaoTalkLinkMessageBuilder.addImage(url, 512, 512 );
            kakaoTalkLinkMessageBuilder.addText(url);
            //kakaoTalkLinkMessageBuilder.addWebLink(url);
            kakaoTalkLinkMessageBuilder.addAppButton("Go to App");

            kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder, this);

        }catch (KakaoParameterException e){
            e.printStackTrace();
        }
    }
}
