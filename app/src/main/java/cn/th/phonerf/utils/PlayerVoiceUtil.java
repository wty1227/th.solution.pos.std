package cn.th.phonerf.utils;

import android.media.MediaPlayer;

import java.io.IOException;
import android.content.Context;
import android.util.Log;

public class PlayerVoiceUtil {
    private static MediaPlayer player = null;

    public static void playerVoice(Context content,int resid) {

        //if (player == null)
        {
            player = MediaPlayer.create(content, resid);
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player.start();
                }
            });
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.d("onCompletion", mp.toString());
                    mp.stop();
                    mp.reset();
                }
            });
        }

       /* try {
            if (player.isPlaying()) {
                player.stop();
                player.reset();
            }

        } catch (Exception e) {
            player.stop();
            player.reset();
        }


        try {
            //player.setDataSource(jsonObject.getString("fullPath"));
            player.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
