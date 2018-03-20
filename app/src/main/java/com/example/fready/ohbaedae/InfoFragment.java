package com.example.fready.ohbaedae;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by fready on 2018-02-05.
 */

public class InfoFragment extends Fragment{
    MainActivity mainActivity;
    GoodInfoVO goodInfoVO;
    ViewGroup rootView ;
    String goodWeight;
    String goodVertical;
    String goodWidth;
    String goodHeight;
    Spinner nationalSpinner ;
    Spinner centerSpinner ;
    private AdView mAdView;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity=(MainActivity) getActivity();
       // Toast.makeText(context, "onAttach"+mainActivity, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
       // Toast.makeText(rootView.getContext(), "onDetach", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MobileAds.initialize(getContext(), R.string.banner_ad_test_unit_id+"");
        //Toast.makeText(rootView.getContext(), "onActivityCreated", Toast.LENGTH_LONG).show();
        nationalSpinner = (Spinner)rootView.findViewById(R.id.national);
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                rootView.getContext(),android.R.layout.simple_spinner_item, items
        );*/
        ArrayAdapter nationalAdapter= ArrayAdapter.createFromResource(getContext(), R.array.national, android.R.layout.simple_dropdown_item_1line);
        nationalSpinner.setAdapter(nationalAdapter);
        //물류센터
        /*centerSpinner = (Spinner)rootView.findViewById(R.id.shippingCenter);
        ArrayAdapter centerAdapter= ArrayAdapter.createFromResource(getContext(), R.array.usa_center, android.R.layout.simple_dropdown_item_1line);
        centerSpinner.setAdapter(centerAdapter);
        */
        //계산하기 버튼클릭시 배송비 계산하기
        Button calButton = (Button)rootView.findViewById(R.id.button);
        calButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goodWeight= ((TextView) (rootView.findViewById(R.id.goodWeight))).getText().toString();
                //System.out.println("(((((((((((((((((((((((((((("+goodWeight);
                goodVertical= ((TextView) (rootView.findViewById(R.id.goodVertical))).getText().toString();
                goodWidth = ((TextView) (rootView.findViewById(R.id.goodWidth))).getText().toString();
                goodHeight = ((TextView) (rootView.findViewById(R.id.goodHeight))).getText().toString();
                //입력값 체크
                if(goodWeight ==null || goodWeight.trim().equals("") || goodWeight.trim().equals("0")){
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(InfoFragment.this.getActivity());
                    dialog = builder.setMessage("상품무게를 입력해주세요")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                if(goodVertical==null || goodVertical.trim().equals("") || goodVertical.trim().equals("0")
                        || goodHeight ==null || goodHeight.trim().equals("") || goodHeight.trim().equals("0")
                        || goodWidth ==null || goodWidth.trim().equals("") || goodWidth.trim().equals("0")){
                    AlertDialog dialog2;
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(InfoFragment.this.getActivity());
                    dialog2 = builder2.setMessage("규격(가로,세로,높이)중 입력 안되면 실무게로 계산됩니다. ")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog2.show();

                }
                callResult();
            }
        });

        //광고
        // Sample AdMob app ID: ca-app-pub-3940256099942544/6300978111  - 테스트 아이디

        mAdView = rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                //Toast.makeText(rootView.getContext(), "광고로드", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }

    private void callResult(){
        goodInfoVO = new GoodInfoVO(
                nationalSpinner.getSelectedItemPosition()+"",
//금액은 화면에서 입력 안받는 걸로 수정
//                ((TextView) (rootView.findViewById(R.id.goodPrice))).getText().toString(),
//                ((TextView) (rootView.findViewById(R.id.tax))).getText().toString(),
//                ((TextView) (rootView.findViewById(R.id.localShipCharge))).getText().toString(),
                "0",
                "0",
                "0",

                goodWidth,
                goodHeight,
                goodVertical,
                goodWeight
        );
        //Toast.makeText(rootView.getContext(), "spinner.getSelectedItemPosition()-->"+spinner.getSelectedItemPosition(), Toast.LENGTH_LONG).show();
        //Toast.makeText(rootView.getContext(), "callResult", Toast.LENGTH_LONG).show();
        mainActivity.callResult(goodInfoVO);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.info_frag, container, false);
        //Toast.makeText(rootView.getContext(), "onCreateView", Toast.LENGTH_LONG).show();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Toast.makeText(rootView.getContext(), "onStart", Toast.LENGTH_LONG).show();
    }
    //화면에 무게값이 있으면 이전에 입력한 값으로 결과값을 보여준다.
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        goodWeight= ((TextView) (rootView.findViewById(R.id.goodWeight))).getText().toString();
        //System.out.println("^^^^^^^^^^^^^^^^^"+goodWeight);
        if(goodWeight!=null && !goodWeight.trim().equals("") ){
            callResult();
        }
    }
}
