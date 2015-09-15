package com.example.kr.myproject.placechoose;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kr.myproject.BaseActivity;
import com.example.kr.myproject.R;
import com.example.kr.myproject.placechoose.data.CityUnit;
import com.example.kr.myproject.placechoose.data.Province;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PlaceChooseActivity extends BaseActivity {

    @InjectView(R.id.province_listview)
    ListView mListView;
    @InjectView(R.id.city_listview)
    ListView universityListview;
    ProvinceAdapter provinceAdapter;
    UniversityAdapter universityAdapter;
    int currentProvince = -1;
    int currentUniversity = -1;
    List<Province> provinceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_place_choose);
        ButterKnife.inject(this);

        provinceList = PlacePraser.prasePlace(this);

        provinceAdapter = new ProvinceAdapter(provinceList);
        mListView.setAdapter(provinceAdapter);
        provinceAdapter.notifyDataSetChanged();

        //默认选择第一个
//        mListView.setSelection(0);
        universityAdapter = new UniversityAdapter(new ArrayList<CityUnit>());
        universityListview.setAdapter(universityAdapter);
        universityAdapter.notifyDataSetChanged();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                universityAdapter.resetData(provinceList.get(position).getCity());
                currentProvince = position;
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        view.setSelected(true);
                    }
                });
            }
        });
        universityListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                currentUniversity = position;
                final String city = provinceList.get(currentProvince).getCity().get(currentUniversity).getValue();
                final String province = provinceList.get(currentProvince).getProvince();
                toast("省份："+province+"\n"+"城市："+city);
            }
        });
    }

    class UniversityAdapter extends BaseAdapter {
        List<CityUnit> mList;
        private LayoutInflater mInflater;

        UniversityAdapter(List<CityUnit> universities) {
            this.mList = universities;
            mInflater = LayoutInflater.from(PlaceChooseActivity.this);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public CityUnit getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void resetData(List<CityUnit> universities) {
            this.mList.clear();
            this.mList.addAll(universities);
            this.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = mInflater.inflate(R.layout.item_simple, parent, false);


            CityUnit university = mList.get(position);

            TextView textView = (TextView) convertView.findViewById(R.id.text);
            textView.setText(university.getValue());
            return convertView;
        }
    }

    class ProvinceAdapter extends BaseAdapter {
        List<Province> mList;
        private LayoutInflater mInflater;

        ProvinceAdapter(List<Province> provinceUniversitySets) {
            this.mList = provinceUniversitySets;
            mInflater = LayoutInflater.from(PlaceChooseActivity.this);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Province getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = mInflater.inflate(R.layout.item_simple, parent, false);


            Province provinceUniversitySet = mList.get(position);

            TextView textView = (TextView) convertView.findViewById(R.id.text);
            textView.setText(provinceUniversitySet.getProvince());
            return convertView;
        }
    }
}
