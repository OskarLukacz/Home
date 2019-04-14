package com.example.oskar.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubException;
import org.json.JSONException;
import org.json.JSONObject;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    String channel = "hello_world";
    Pubnub pubnub = new Pubnub("pub-c-b6db3020-95a8-4c60-8d16-13345aaf8709", "sub-c-05dce56c-3c2e-11e7-847e-02ee2ddab7fe", false);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            pubnub.subscribe(channel, new Callback() {

                @Override
                        public void successCallback(String channel, Object message) {
                            System.out.println("SUBSCRIBE : " + channel + " : "
                                    + message.getClass() + " : " + message.toString());
                            updateStatus(message.toString());
                        }


                    }
            );
        } catch (PubnubException e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tv1 = findViewById(R.id.textView);
                tv1.setText("Latest Status: " + s);
            }
        });
    }


    public void publish(View view) {

        Button button = (Button) view;

        String hint = (String) button.getHint();

        JSONObject msg = new JSONObject();
        try {

            switch (hint) {

                case "Zero ON"    : msg.put("0", "o"); break;
                case "Zero OFF"   : msg.put("0", "f"); break;
                case "One ON"     : msg.put("1", "o"); break;
                case "One OFF"    : msg.put("1", "f"); break;
                case "Two ON"     : msg.put("2", "o"); break;
                case "Two OFF"    : msg.put("2", "f"); break;
                case "Three ON"   : msg.put("3", "o"); break;
                case "Three OFF"  : msg.put("3", "f"); break;
                case "Toggle All" : msg.put("A", "A"); break;
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        pubnub.publish(channel  , msg , new Callback() {

            public void errorCallback(String channel, Object message) {
                System.out.println("Error");
            }

        });

    }
}
