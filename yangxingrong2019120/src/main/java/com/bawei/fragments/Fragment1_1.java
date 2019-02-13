package com.bawei.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.R;
import com.bawei.bean.MyBean;
import com.bawei.dp.Dao;
import com.bawei.utils.NetWork;
import com.bwie.xlistviewlibrary.utils.NetWordUtils;
import com.bwie.xlistviewlibrary.view.XListView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class Fragment1_1 extends Fragment {
    private String url = "http://api.expoon.com/AppNews/getNewsList/type/1/p/1";
    private XListView xListView;
    private int page;
    List<MyBean.DataBean> list = new ArrayList<>();
    private MAdapter mAdapter;
    private Dao dao;
    private ImageLoader imageLoaderInstances;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment1_1,container,false);
        xListView=view.findViewById(R.id.xListView);
        dao=new Dao(getActivity());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //开启上下拉功能
        xListView.setPullLoadEnable(true);
        imageLoaderInstances=ImageLoader.getInstance();
        //配置适配器
        mAdapter = new MAdapter();
        xListView.setAdapter(mAdapter);
        //请求网络数据
        initData(page);
        //实现监听
        xListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page=0;
                list.clear();
                initData(page);
            }

            @Override
            public void onLoadMore() {
                page++;
                initData(page);

            }
        });
    }

    private void initData(int page) {
        boolean connection = NetWork.isConnection(getActivity());
        if (connection){//有网
            String urls = url + page;
            new MAsycnTask().execute(urls);
        }else {
            //没有网
            Toast.makeText(getActivity(),"检查网络",Toast.LENGTH_LONG).show();
            //根据url地址。从数据库中获取数据
            String jsonDtata = dao.queryData(url);
            if(!"".equals(jsonDtata)){
                Gson gson=new Gson();
                MyBean myBean = gson.fromJson(jsonDtata, MyBean.class);
                List<MyBean.DataBean> data = myBean.getData();
                list.addAll(data);
                mAdapter.notifyDataSetChanged();
            }

        }

    }
    private class MAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder=new ViewHolder();
            if (convertView==null){
                convertView=View.inflate(getActivity(),R.layout.list_main,null);
                holder.image=convertView.findViewById(R.id.image);
                holder.text=convertView.findViewById(R.id.text);
                convertView.setTag(holder);
            }else {
                holder=(ViewHolder)convertView.getTag();

            }
            holder.text.setText(list.get(position).getNews_title());
            imageLoaderInstances.displayImage(list.get(position).getPic_url(),holder.image);
            return convertView;
        }
    }
    class  ViewHolder{
    ImageView image;
    TextView text;
    }
    class MAsycnTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return NetWordUtils.getNetjson(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //解析gson
            dao.add(s,url);
            Gson gson=new Gson();
            MyBean goods = gson.fromJson(s, MyBean.class);
            List<MyBean.DataBean> data = goods.getData();
            list.addAll(data);
        mAdapter.notifyDataSetChanged();
            xListView.setRefreshTime("刚刚刷新");
            xListView.stopRefresh();//隐藏头
            xListView.stopLoadMore();//隐藏脚
        }
    }
}
