package com.deb.new_home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.StringTokenizer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
{

    private Button lamp1_button;
    private ImageView lamp1_image;
    private Button strip1_button;
    private ImageView strip1_image;
    private Button tempVall;

    boolean strip1_status = false;
    boolean lamp1_status = true;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lamp1_button = (Button) findViewById(R.id.lamp1);
        lamp1_image = (ImageView) findViewById(R.id.lamp1_image);
        strip1_button = (Button) findViewById(R.id.strip1);
        strip1_image = (ImageView) findViewById(R.id.strip1_image);
        tempVall = (Button) findViewById(R.id.tempVal);

        Toast toast_fail = Toast.makeText(MainActivity.this, "Отправка не выполнена", Toast.LENGTH_LONG);

        mHandler.removeCallbacks(synchr);
        mHandler.postDelayed(synchr, 100);


        //кнопка управления лампой

        lamp1_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (lamp1_status == true)
                {
                    String url = "http://192.168.0.123/1/0";
                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    new OkHttpClient().newCall(request)
                            .enqueue(new Callback()
                    {
                                @Override
                                public void onFailure(final Call call, IOException e)
                                {
                                    e.printStackTrace();
                                    toast_fail.show();
                                }
                                @Override
                                public void onResponse(Call call, final Response response) throws IOException
                                {
                                    String res = response.body().string();
                                    //textView.setText(res);
                                    lamp1_image.setImageResource(R.drawable.off);
                                    lamp1_button.setText("Включить");
                                }
                    });
                }

                    if (lamp1_status == false)
                    {
                        String url = "http://192.168.0.123/1/1";
                        Request request = new Request.Builder()
                                .url(url)
                                .build();

                        new OkHttpClient().newCall(request)
                                .enqueue(new Callback()
                                {
                                    @Override
                                    public void onFailure(final Call call, IOException e)
                                    {
                                        e.printStackTrace();
                                        toast_fail.show();
                                    }
                                    @Override
                                    public void onResponse(Call call, final Response response) throws IOException
                                    {
                                        String res = response.body().string();
                                        lamp1_image.setImageResource(R.drawable.on);
                                        lamp1_button.setText("Выключить");
                                    }
                                });
                    }
                    lamp1_status = !lamp1_status;


            }
        });

//кнопка управления лентой
        strip1_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (strip1_status == true)
                {
                    String url = "http://192.168.0.123/2/0";
                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    new OkHttpClient().newCall(request)
                            .enqueue(new Callback()
                            {
                                @Override
                                public void onFailure(final Call call, IOException e)
                                {
                                    e.printStackTrace();
                                    toast_fail.show();
                                }
                                @Override
                                public void onResponse(Call call, final Response response) throws IOException
                                {
                                    String res = response.body().string();
                                    strip1_image.setImageResource(R.drawable.off);
                                    strip1_button.setText("Включить");
                                }
                            });
                }

                if (strip1_status == false)
                {
                    String url = "http://192.168.0.123/2/1";
                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    new OkHttpClient().newCall(request)
                            .enqueue(new Callback()
                            {
                                @Override
                                public void onFailure(final Call call, IOException e)
                                {
                                    e.printStackTrace();
                                    toast_fail.show();
                                }
                                @Override
                                public void onResponse(Call call, final Response response) throws IOException
                                {
                                    String res = response.body().string();
                                    strip1_image.setImageResource(R.drawable.on);
                                    strip1_button.setText("Выключить");
                                }
                            });
                }
                strip1_status = !strip1_status;
            }
        });
    }

    private Runnable synchr = new Runnable() {
        @Override
        public void run()
        {

            //TextView tempValue = findViewById(R.id.temperatureValue);

            tempVall = (Button) findViewById(R.id.tempVal);
            //Toast toast_fail = Toast.makeText(MainActivity.this, "Сервер не доступен", Toast.LENGTH_LONG);
            String url = "http://192.168.0.123/100";
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            new OkHttpClient().newCall(request)
                    .enqueue(new Callback()
                    {
                        @Override
                        public void onFailure(final Call call, IOException e)
                        {
                            e.printStackTrace();
                            //toast_fail.show();
                        }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException
                        {
                            String res = response.body().string();

                            StringTokenizer st = new StringTokenizer(res, "@");
                            String obj0 = st.nextToken(); //все, что идет до нужной информации
                            String obj1 = st.nextToken();
                            String obj2 = st.nextToken();
                            String obj3 = st.nextToken();
                            String obj4 = st.nextToken(); //все, что после нужной информации
                            StringTokenizer obj1st = new StringTokenizer(obj1, "#");
                            String obj10Stat = obj1st.nextToken();//все, что идет до нужной информации
                            String obj1Stat = obj1st.nextToken();
                            if (obj1Stat.equals("0"))
                            {
                                lamp1_image.setImageResource(R.drawable.off);
                                lamp1_button.setText("Включить");
                                lamp1_status = false;
                            }
                            else if (obj1Stat.equals("1"))
                            {
                                lamp1_image.setImageResource(R.drawable.on);
                                lamp1_button.setText("Выключить");
                                lamp1_status = true;
                            }
                            StringTokenizer obj2st = new StringTokenizer(obj2, "#");
                            String obj20Stat = obj2st.nextToken();//все, что идет до нужной информации
                            //obj20Stat = "";
                            String obj2Stat = obj2st.nextToken();
                            if (obj2Stat.equals("0"))
                            {
                                strip1_image.setImageResource(R.drawable.off);
                                strip1_button.setText("Включить");
                                strip1_status = false;
                            }
                            else if (obj2Stat.equals("1"))
                            {
                                strip1_image.setImageResource(R.drawable.on);
                                strip1_button.setText("Выключить");
                                strip1_status = true;
                            }
                            StringTokenizer obj3st = new StringTokenizer(obj3, "#");
                            String obj30Stat = obj3st.nextToken();//все, что идет до нужной информации
                            //obj20Stat = "";
                            String obj3Stat = obj3st.nextToken();
                            //setTemerature(obj3Stat);
                            //tempVall.setText(obj3Stat);
                            /*if (obj3Stat.equals(24))
                                tempVal.setText("24");
                            if (obj3Stat.equals(22))
                                tempVal.setText("22");*/
                        }
                    });
            mHandler.postDelayed(this, 1000);
        }
    };

    /*void setTemerature(String temerature)
    {
        tempVall.setText(temerature);
    }*/

}