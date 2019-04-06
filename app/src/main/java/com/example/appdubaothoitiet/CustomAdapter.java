package com.example.appdubaothoitiet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Weather> arrayList;

    public CustomAdapter(Context context, ArrayList<Weather> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dong_listview,null);

        Weather weather = arrayList.get(i);

        TextView txtDay = (TextView) view.findViewById(R.id.txtNgay);
        TextView txtStatus = (TextView) view.findViewById(R.id.txtTrangthai);
        TextView txtMinTemp = (TextView) view.findViewById(R.id.txtMinTemp);
        TextView txtMaxTemp = (TextView) view.findViewById(R.id.txtMaxTemp);
        ImageView imgStatus = (ImageView) view.findViewById(R.id.imageTrangthai);

        txtDay.setText(weather.Ngay);
        txtStatus.setText(weather.Trangthai);
        txtMinTemp.setText(weather.MinTemp+"°C");
        txtMaxTemp.setText(weather.MaxTemp+"°C");

        Picasso.with(context).load("http://openweathermap.org/img/w/"+weather.Hinhanh+".png").into(imgStatus);
        return view;
    }
}
