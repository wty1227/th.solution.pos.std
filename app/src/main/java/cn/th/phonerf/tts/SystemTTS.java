package cn.th.phonerf.tts;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.RequiresApi;

import java.util.Locale;

public class SystemTTS implements TextToSpeech.OnUtteranceCompletedListener {
    private Context mContext;
    private static SystemTTS mSystemTTS;
    private TextToSpeech mTextToSpeech;

    public static SystemTTS getInstance(Context context) {
        if (mSystemTTS == null) {
            synchronized (SystemTTS.class) {
                if (mSystemTTS == null) {
                    mSystemTTS = new SystemTTS(context);
                }
            }
        }
        return mSystemTTS;
    }

    private SystemTTS(Context context) {
        mContext = context.getApplicationContext();
        mTextToSpeech = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTextToSpeech.setLanguage(Locale.US);
                    if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE && result != TextToSpeech.LANG_AVAILABLE) {
                    }else{
                        mTextToSpeech.setPitch(1.0f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                        mTextToSpeech.setSpeechRate(1.0f);
                        mTextToSpeech.setOnUtteranceProgressListener(new TTSUtteranceListener());
                        mTextToSpeech.setOnUtteranceCompletedListener(SystemTTS.this);//已经过时
                    }
                }
            }
        });
    }

    //此方法已经过时
    @Override
    public void onUtteranceCompleted(String utteranceId) {
    }

    private class TTSUtteranceListener extends UtteranceProgressListener {
        @Override
        public void onStart(String utteranceId) {
        }

        @Override
        public void onDone(String utteranceId) {
            if(mISpeechComplete != null){
                mISpeechComplete.speechComplete();
            }
        }
        @Override
        public void onError(String utteranceId) {
            if(mISpeechComplete != null){
                mISpeechComplete.speechError();
            }
        }
    }

    public void playText(String playText) {
        if (mTextToSpeech != null) {
            //依次播放MSG,不会打断当前播放内容
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mTextToSpeech.speak(playText,TextToSpeech.QUEUE_FLUSH,null,"UniqueID");//注意设置ID
            }
            //依次播放MSG,如果有新的播放内容时,则取消上一次正在播放的内容,播放现在内容
            //mTextToSpeech.speak(playText,TextToSpeech.QUEUE_FLUSH,null,"UniqueID");
        }
    }

    public void stopSpeak() {
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
        }
    }

    private ISpeechComplete mISpeechComplete;
    public void setSpeechComplete(ISpeechComplete speechComplete){
        mISpeechComplete = speechComplete;
    }
    public interface ISpeechComplete{
        void speechComplete();
        void speechError();
    }
}
