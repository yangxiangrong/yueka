package com.bawei;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bawei.fragments.Fragment1;
import com.bawei.fragments.Fragment2;
import com.bawei.fragments.Fragment3;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private DrawerLayout drawerLayout;
    private ViewPager pager;
    private RadioGroup group;
 private List<Fragment> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout=findViewById(R.id.drawerlayout);
        pager=findViewById(R.id.pager);
        group=findViewById(R.id.group);

        //实现侧拉监听
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                Toast.makeText(MainActivity.this,"我被代开了",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                Toast.makeText(MainActivity.this,"我被关闭了",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
        //添加数据
        list.add(new Fragment1());
        list.add(new Fragment2());
        list.add(new Fragment3());
        //适配器
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.but1:
                        pager.setCurrentItem(0);
                        break;
                    case R.id.but2:
                        pager.setCurrentItem(1);
                        break;
                    case R.id.but3:
                        pager.setCurrentItem(2);
                        break;
                }
            }
        });
    }
}
