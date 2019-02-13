package com.bawei.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawei.R;
import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
import java.util.List;

public class Fragment1_0 extends Fragment {
    private String url = "http://result.eolinker.com/iYXEPGn4e9c6dafce6e5cdd23287d2bb136ee7e9194d3e9?uri=banner";
    private FlyBanner banner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_0, container, false);
        banner = view.findViewById(R.id.banner);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<String> list=new ArrayList<>();
        list.add("http://image.wufazhuce.com/FuPgOcbGDD9__fyuCdPBXb5pbA1C");
        list.add("http://image.wufazhuce.com/Fu6o0fqKHsI_TjdpPX3N2Kc99vNP");
        list.add("http://image.wufazhuce.com/FmDRnQ1XhReHRHB4jYqAPSx8htsP");
        list.add("http://image.wufazhuce.com/FvVmWbqlle7jlUCTeozoval9NyBH");
        banner.setImagesUrl(list);
    }
}
