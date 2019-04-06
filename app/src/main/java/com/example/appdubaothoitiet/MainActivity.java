package com.example.appdubaothoitiet;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText edtTimkiem;
    Button btnTimkiem, btnThaydoi;
    TextView tvThanhpho, tvQuocgia, tvNhietdo, tvTrangthai, tvDoam, tvMay, tvGio, tvNgay;
    ImageView imgIcon;
    String Thanhpho ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        GetCurrentWeatherData("hanoi");
        btnTimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String thanhpho = edtTimkiem.getText().toString();
                if (thanhpho.equals("")){
                    Thanhpho = "hanoi";
                    GetCurrentWeatherData(Thanhpho);
                }else {
                    Thanhpho = thanhpho;
                    GetCurrentWeatherData(Thanhpho);
                }
                GetCurrentWeatherData(thanhpho);
            }
        });
        btnThaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String thanhpho = edtTimkiem.getText().toString();
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("name",thanhpho);
                startActivity(intent);
            }
        });
    }

    public void GetCurrentWeatherData(String data){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=53fbf527d52d4d773e828243b90c1f8e";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            tvThanhpho.setText("Tên thành phố:"+name);

                            long l = Long.valueOf(day);
                            Date date = new Date(l*1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm:ss");
                            String Day = simpleDateFormat.format(date);

                            tvNgay.setText(Day);
                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObjectWeather.getString("main");
                            String icon = jsonObjectWeather.getString("icon");

                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/"+icon+".png").into(imgIcon);
                            tvTrangthai.setText(status);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObjectMain.getString("temp");
                            String doam = jsonObjectMain.getString("humidity");

                            Double a = Double.valueOf(nhietdo);
                            String Nhietdo = String.valueOf(a.intValue());

                            tvNhietdo.setText(Nhietdo+"°C");
                            tvDoam.setText(doam+"%");

                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String gio = jsonObjectWind.getString("speed");
                            tvGio.setText(gio+"m/s");

                            JSONObject jsonObjectClouds = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectClouds.getString("all");
                            tvMay.setText(may+"%");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String country = jsonObjectSys.getString("country");
                            tvQuocgia.setText("Tên quốc gia:"+country);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
    private void Anhxa() {
        edtTimkiem = (EditText) findViewById(R.id.edtTimkiem);
        btnTimkiem = (Button) findViewById(R.id.btnTimkiem);
        btnThaydoi = (Button) findViewById(R.id.btnThaydoi);
        tvThanhpho = (TextView) findViewById(R.id.tvThanhpho);
        tvQuocgia = (TextView) findViewById(R.id.tvQuocgia);
        tvNhietdo = (TextView) findViewById(R.id.tvNhietdo);
        tvTrangthai = (TextView) findViewById(R.id.tvTrangthai);
        tvDoam = (TextView) findViewById(R.id.tvDoam);
        tvMay = (TextView) findViewById(R.id.tvMay);
        tvGio = (TextView) findViewById(R.id.tvGio);
        tvNgay = (TextView) findViewById(R.id.tvNgay);
        imgIcon = (ImageView) findViewById(R.id.imgIcon);
    }
}
