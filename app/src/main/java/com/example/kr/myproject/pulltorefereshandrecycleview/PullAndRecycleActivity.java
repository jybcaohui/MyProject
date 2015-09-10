package com.example.kr.myproject.pulltorefereshandrecycleview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kr.myproject.BaseActivity;
import com.example.kr.myproject.R;
import com.example.kr.myproject.pulltorefereshandrecycleview.recycleview.Fragmentc;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PullAndRecycleActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    @InjectView(R.id.tab1)
    RelativeLayout rela1;
    @InjectView(R.id.tab2)
    RelativeLayout rela2;
    @InjectView(R.id.tab3)
    RelativeLayout rela3;
    @InjectView(R.id.img1)
    ImageView img1;
    @InjectView(R.id.img2)
    ImageView img2;
    @InjectView(R.id.img3)
    ImageView img3;
    @InjectView(R.id.txt1)
    TextView txt1;
    @InjectView(R.id.txt2)
    TextView txt2;
    @InjectView(R.id.txt3)
    TextView txt3;
    private ItemFragmentPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private Fragmenta fragmenta;
    private Fragmentb fragmentb;
    private Fragmentc fragmentc;
    private int tabIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pull_and_recycle);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    public void initData(){
        if (fragmentList == null) {
            fragmentList = new ArrayList<Fragment>();
        } else {
            fragmentList.clear();
        }

        fragmenta = new Fragmenta();
        fragmentb = new Fragmentb();
        fragmentc = new Fragmentc();
        //dynamicFragment = new DynamicFragment();
        fragmentList.add(fragmenta);
        fragmentList.add(fragmentb);
        fragmentList.add(fragmentc);
    }

    public void initView() {
        if (adapter == null) {
            adapter = new ItemFragmentPagerAdapter(this.getSupportFragmentManager(), fragmentList);
            viewpager.setOffscreenPageLimit(4);
            viewpager.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        rela1.setOnClickListener(this);
        rela2.setOnClickListener(this);
        rela3.setOnClickListener(this);

        setTabSelectState();
        viewpager.setCurrentItem(0);
        title.setText("下拉刷新");
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                tabIndex = i;
                setTabSelectState();

            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }



    private void setTabSelectState() {
        switch (tabIndex) {
            case 0:
                title.setText("下拉刷新");
                txt1.setSelected(true);
                txt2.setSelected(false);
                txt3.setSelected(false);

                img1.setSelected(true);
                img2.setSelected(false);
                img3.setSelected(false);
                break;
            case 1:
                title.setText("双列下拉刷新");
                txt1.setSelected(false);
                txt2.setSelected(true);
                txt3.setSelected(false);

                img1.setSelected(false);
                img2.setSelected(true);
                img3.setSelected(false);
                break;
            case 2:
                title.setText("瀑布流下拉刷新");
                txt1.setSelected(false);
                txt2.setSelected(false);
                txt3.setSelected(true);

                img1.setSelected(false);
                img2.setSelected(false);
                img3.setSelected(true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab1:
                tabIndex = 0;
                break;
            case R.id.tab2:
                tabIndex = 1;
                break;
            case R.id.tab3:
                tabIndex = 2;
                break;
            default:
                break;
        }
        viewpager.setCurrentItem(tabIndex);
        //setTabSelectState();
    }
}
