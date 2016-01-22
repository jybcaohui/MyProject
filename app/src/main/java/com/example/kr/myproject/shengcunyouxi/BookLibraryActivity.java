package com.example.kr.myproject.shengcunyouxi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kr.myproject.BaseActivity;
import com.example.kr.myproject.R;
import com.example.kr.myproject.pulltorefereshandrecycleview.ItemFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BookLibraryActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    @InjectView(R.id.tab1)
    LinearLayout rela1;
    @InjectView(R.id.tab2)
    LinearLayout rela2;
    @InjectView(R.id.tab3)
    LinearLayout rela3;
    @InjectView(R.id.tab4)
    LinearLayout rela4;
    @InjectView(R.id.txt1)
    TextView txt1;
    @InjectView(R.id.txt2)
    TextView txt2;
    @InjectView(R.id.txt3)
    TextView txt3;
    @InjectView(R.id.txt4)
    TextView txt4;
    @InjectView(R.id.dot1)
    View dot1;
    @InjectView(R.id.dot2)
    View dot2;
    @InjectView(R.id.dot3)
    View dot3;
    @InjectView(R.id.dot4)
    View dot4;
    @InjectView(R.id.sort)
    TextView sort;
    @InjectView(R.id.library)
    TextView library;
    private ItemFragmentPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private FragmentLocal fragmenta;
    private FragmentLocal fragmentb;
    private FragmentLocal fragmentc;
    private FragmentLocal fragmentd;
    private int tabIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_book_library);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    public void initData() {
        if (fragmentList == null) {
            fragmentList = new ArrayList<Fragment>();
        } else {
            fragmentList.clear();
        }
        fragmenta = new FragmentLocal();
        fragmentb = new FragmentLocal();
        fragmentc = new FragmentLocal();
        fragmentd = new FragmentLocal();
        fragmentList.add(fragmenta);
        fragmentList.add(fragmentb);
        fragmentList.add(fragmentc);
        fragmentList.add(fragmentd);
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
        rela4.setOnClickListener(this);
        sort.setOnClickListener(this);
        library.setOnClickListener(this);

        setTabSelectState();
        viewpager.setCurrentItem(0);
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
                txt1.setSelected(true);
                txt2.setSelected(false);
                txt3.setSelected(false);
                txt4.setSelected(false);

                dot1.setSelected(true);
                dot2.setSelected(false);
                dot3.setSelected(false);
                dot4.setSelected(false);
                break;
            case 1:
                txt1.setSelected(false);
                txt2.setSelected(true);
                txt3.setSelected(false);
                txt4.setSelected(false);

                dot1.setSelected(false);
                dot2.setSelected(true);
                dot3.setSelected(false);
                dot4.setSelected(false);
                break;
            case 2:
                txt1.setSelected(false);
                txt2.setSelected(false);
                txt3.setSelected(true);
                txt4.setSelected(false);

                dot1.setSelected(false);
                dot2.setSelected(false);
                dot3.setSelected(true);
                dot4.setSelected(false);
                break;
            case 3:
                txt1.setSelected(false);
                txt2.setSelected(false);
                txt3.setSelected(false);
                txt4.setSelected(true);

                dot1.setSelected(false);
                dot2.setSelected(false);
                dot3.setSelected(false);
                dot4.setSelected(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tab1:
                tabIndex = 0;
                viewpager.setCurrentItem(tabIndex);
                break;
            case R.id.tab2:
                tabIndex = 1;
                viewpager.setCurrentItem(tabIndex);
                break;
            case R.id.tab3:
                tabIndex = 2;
                viewpager.setCurrentItem(tabIndex);
                break;
            case R.id.tab4:
                tabIndex = 3;
                viewpager.setCurrentItem(tabIndex);
                break;
            case R.id.library:
                tabIndex = 0;
                viewpager.setCurrentItem(tabIndex);
                break;
            case R.id.sort:
                break;
            default:
                break;
        }
    }
}
