package com.example.fready.ohbaedae;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fready on 2018-02-05.
 * 배송비 결과 Fragment
 */

public class ResultFragment extends Fragment {
    ResultAdapter adapter;
    ViewGroup rootView ;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.print("ResultFragment onCreateView %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        rootView = (ViewGroup) inflater.inflate(R.layout.result_frag, container, false);
        listView=(ListView)rootView.findViewById(R.id.listView);
        TextView resultPoundView=(TextView)rootView.findViewById(R.id.result);

        TextView nationalView = (TextView)rootView.findViewById(R.id.national);
        ImageView flagView = (ImageView)rootView.findViewById(R.id.flag);
        adapter = new ResultAdapter();
        //앞에서 화면에서 입력한 정보를 받아옮.

        //배송비 계산 함수 호출
        ArrayList<CompShppingAgentVO> voList = new ArrayList<CompShppingAgentVO>();
        String national = getArguments().getString("national"); //구입국가
        if(national!=null && national.equals("0")) {
            //{"CA-켈리포니아","DW-델라웨이","NJ-뉴저지", "OR-오레곤"}
            //몰테일 호출 - 켈리포니아, 델라웨어, 뉴저지
            // if(shppingCenter!=null && (shppingCenter.equals("0") || shppingCenter.equals("1")||shppingCenter.equals("2"))){

            voList.add(calDeliveryPrice("malltail", "0", "F"));
            voList.add(calDeliveryPrice("malltail", "1","F"));
            voList.add(calDeliveryPrice("malltail", "2","F"));
            //}

            //뉴욕걸즈 호출 - 뉴저지 , 오레곤, 델라웨어
            //if(shppingCenter!=null && (shppingCenter.equals("1") || shppingCenter.equals("2")||shppingCenter.equals("3"))){
            voList.add(calDeliveryPrice("nygirlz", "1","F"));
            voList.add(calDeliveryPrice("nygirlz", "2","F"));
            voList.add(calDeliveryPrice("nygirlz", "3","F"));
            //}

            //아이포터 호출 - 켈리포니아, 뉴저지, 오레곤

            //if(shppingCenter!=null && (shppingCenter.equals("0") || shppingCenter.equals("2")||shppingCenter.equals("3"))){
            voList.add(calDeliveryPrice("iporter", "0","F"));
            voList.add(calDeliveryPrice("iporter", "2","F"));
            voList.add(calDeliveryPrice("iporter", "3","F"));
            //}

            //요걸루 호출 -켈리포니아
            //if(shppingCenter!=null && shppingCenter.equals("0")){

            voList.add(calDeliveryPrice("yogirloo", "0","S"));
            // }
        }else if(national!=null && national.equals("1")) {//중국
            voList.add(calDeliveryPrice("malltail", "C", "F"));
            voList.add(calDeliveryPrice("malltail", "C", "S"));
            voList.add(calDeliveryPrice("iporter", "I", "F"));
            voList.add(calDeliveryPrice("iporter", "I", "S"));
        }else if(national!=null && national.equals("2")) {//일본
            voList.add(calDeliveryPrice("malltail", "C", "F"));
            voList.add(calDeliveryPrice("malltail", "C", "S"));
            voList.add(calDeliveryPrice("iporter", "I", "F"));
            voList.add(calDeliveryPrice("iporter", "I", "S"));
        }else if(national!=null && national.equals("3")) {//독일
            //voList.add(calDeliveryPrice("malltail", "G", "F"));
            voList.add(calDeliveryPrice("iporter", "G", "F"));
        }else if(national!=null && national.equals("4")) {//영국
            voList.add(calDeliveryPrice("iporter", "E", "F"));
        }
        //배송비가 가장 적은 것부터서 순서대로 정렬한다.
        CompShppingAgentVO tempVo ;
        BigDecimal ship1;
        BigDecimal ship2;
        if(voList!=null && voList.size()>0){
            for (int i=0; i<voList.size(); i++){
                for(int j=0; j<voList.size(); j++) {
                   // System.out.println("voList====>"+voList);
                    ship1 =  new BigDecimal(voList.get(i).getShppingCharge());
                    ship2 = new BigDecimal(voList.get(j).getShppingCharge());
                    if (ship1.compareTo(ship2) < 0) {
                        tempVo = voList.get(j);
                        voList.set(j,voList.get(i) );
                        voList.set(i, tempVo);
                    }
                }
            }
        }

        //////
        //리스트 화면에 붙혀줌.
        calShippingPrice(voList);
        //실무게는 모두 같지만 부피무게랑 적용무게는 업체별로 달라질수있음.
        resultPoundView.setText("실무게:"+voList.get(0).getRealWeight());

        if(national!=null && national.equals("0")) {
            nationalView.setText("미국");
            flagView.setImageResource(R.drawable.if_usa_1);
        }else if(national!=null && national.equals("1")) {
            nationalView.setText("중국");
            flagView.setImageResource(R.drawable.if_china_1);
        }else if(national!=null && national.equals("2")) {
            nationalView.setText("일본");
            flagView.setImageResource(R.drawable.if_japan_1);
        }else if(national!=null && national.equals("3")) {
            nationalView.setText("독일");
            flagView.setImageResource(R.drawable.if_germany_1);
        }else if(national!=null && national.equals("4")) {
            nationalView.setText("영국");
            flagView.setImageResource(R.drawable.if_englend_1);
        }

        System.out.print("national===============?"+national);


        return rootView;
    }
    //데이터를 관리하는 어뎁터
    ////
    class ResultAdapter extends BaseAdapter{
        ArrayList<CompShppingAgentVO> items = new ArrayList<CompShppingAgentVO>();
        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }
        //부분화면을 보내준다.
        @Override
        public View getView(int i, View converView, ViewGroup viewGroup) {
            ResultShipView view= null;
            if (converView==null) {
                view = new ResultShipView(viewGroup.getContext());
            }else{
                view = (ResultShipView)converView;
            }
            CompShppingAgentVO item = items.get(i);
            view.setAgent(item.getAgent());
            //예상국제배송비
            if(item.getShppingCharge().equals("0") ){
                view.setShppingCharge("범위초과-문의바람");
            }else{
                view.setShppingCharge(item.getShppingCharge()+"$");
            }
            //view.setRealWeight(item.getRealWeight()+" lbs");

            view.setApplyWeight(item.getApplyWeight());
            if(item.getVolumeWeight()==null || item.getVolumeWeight().trim().equals("") || item.getVolumeWeight().equals("0")){
                view.setVolumeWeight("   -");
            }else{
                view.setVolumeWeight(item.getVolumeWeight());
            }

            view.setLocalShipCharge(item.getLocalShipChage()); //한국국내배송비
            if(item.getNote()!=null && !item.getNote().trim().equals("")){
                view.setNote("*."+item.getNote());
            }else{
                view.setNote("");
            }
            view.setShippingCenter(item.getShippingCenterName());
            view.setShippingGubun(item.getGubun()); //항공/해상구분
            return view;
        }
        //데이터 넣기
        public void addIem(CompShppingAgentVO item){

            items.add(item);
        }
    }
    //계산된 배송비 정보를 아답터를 이용해 화면에 붙혀줌.
    private void calShippingPrice(ArrayList<CompShppingAgentVO> list) {
        for (int i = 0; i< list.size(); i++){
            adapter.addIem(list.get(i));
        }
        listView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listView);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        System.out.println( "@@@@@@@@@@@@@@@@@@----listAdapter-------------");
        System.out.println(listAdapter);
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        System.out.println( "----listAdapter.getCount()-------------"+listAdapter.getCount());
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            System.out.println(listItem.getMeasuredHeight());
            //totalHeight += listItem.getMeasuredHeight();
            totalHeight += 180;
            System.out.print( "**********"+listItem.getMeasuredHeight()+"-----------------");
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }



    //json파일 공통으로 불러오는 메서드
    private Map<String,String> getMapWeightPrice(String beadeaji, String shppingCenter, String ShipGun, String national, boolean  yogirlooV ) {
        // myJson.json
        JSONParser parser = new JSONParser();
        InputStream inputStream = null;
        if(national!=null && national.equals("0")) {//미국
            if(beadeaji.equals("malltail")){
                inputStream =  getResources().openRawResource(R.raw.malltail);
            }else if(beadeaji.equals("nygirlz")){
                inputStream =  getResources().openRawResource(R.raw.nygirlz);
            }else if(beadeaji.equals("iporter")){
                //{"CA-켈리포니아","DW-델라웨이","NJ-뉴저지", "OR-오레곤"}
                if(shppingCenter!=null && shppingCenter.equals("0")){
                    inputStream =  getResources().openRawResource(R.raw.iporter_ca);
                }else  if(shppingCenter!=null && shppingCenter.equals("2")) {
                    inputStream =  getResources().openRawResource(R.raw.iporter_nj);
                }else  if(shppingCenter!=null && shppingCenter.equals("3")) {
                    inputStream =  getResources().openRawResource(R.raw.iporter_or);
                }
            }else if(beadeaji.equals("yogirloo")){
                inputStream =  getResources().openRawResource(R.raw.yogirloo);
            }
        }else if(national!=null && national.equals("1")) {//중국
            if(beadeaji.equals("malltail")){
                if(ShipGun!=null && ShipGun.equals("F")){
                    inputStream =  getResources().openRawResource(R.raw.malltail_cha_fly);
                }else  if(ShipGun!=null && ShipGun.equals("S")) {
                    inputStream =  getResources().openRawResource(R.raw.malltail_cha_ship);
                }
            }else if(beadeaji.equals("iporter")) {
                if(ShipGun!=null && ShipGun.equals("F")){
                    inputStream =  getResources().openRawResource(R.raw.iporter_cha_fly);
                }else  if(ShipGun!=null && ShipGun.equals("S")) {
                    inputStream =  getResources().openRawResource(R.raw.iporter_cha_ship);
                }
            }
        }else if(national!=null && national.equals("2")) {//일본
            if(beadeaji.equals("malltail")){
                if(ShipGun!=null && ShipGun.equals("F")){
                    inputStream =  getResources().openRawResource(R.raw.malltail_jpy_fly);
                }else  if(ShipGun!=null && ShipGun.equals("S")) {
                    inputStream =  getResources().openRawResource(R.raw.malltail_jpy_ship);
                }
            }else if(beadeaji.equals("iporter")) {
                if(ShipGun!=null && ShipGun.equals("F")){
                    inputStream =  getResources().openRawResource(R.raw.iporter_jpy_fly);
                }else  if(ShipGun!=null && ShipGun.equals("S")) {
                    inputStream =  getResources().openRawResource(R.raw.iporter_jpy_ship);
                }
            }
        }else if(national!=null && national.equals("3")) {//독일
            if(beadeaji.equals("malltail")){
                inputStream =  getResources().openRawResource(R.raw.malltail_gmy);
            }else if(beadeaji.equals("iporter")) {
                inputStream =  getResources().openRawResource(R.raw.iporter_gmy);
            }
        }else if(national!=null && national.equals("4")) {//영국
            if(beadeaji.equals("iporter")) {
                inputStream =  getResources().openRawResource(R.raw.iporter_eng);
            }
        }

        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader buffer = new BufferedReader(reader);
        StringBuffer sb = new StringBuffer();
        String line = null;
        Map<String, String> mp = new HashMap<String, String>();
        try {
            Object obj = parser.parse(reader);
            JSONArray jsonArray =(JSONArray) obj;

            System.out.println("------------------------------------------");
            System.out.println(jsonArray.get(0));
            JSONObject jObj =null;
            Collection<String> values = null;
            int beforePound =0;
            int currPound = 0;
            int currKg = 0;
            String currPrice="";
            for (int i =0; i<jsonArray.size(); i++){

                jObj = (JSONObject)jsonArray.get(i);
                values = jObj.values();
                if(beadeaji.equals("yogirloo")){


                    if(yogirlooV){ //CBM무게표*100
                        currKg = ( new BigDecimal(values.toArray()[2].toString()))
                                .multiply(new BigDecimal("100")).intValue();

                        currPrice = values.toArray()[1].toString().replace("$", "").replace(")", "").trim();
                        mp.put(currKg+"",currPrice );
                    }else{//파운드 무게표
                        currPound = ( new BigDecimal(values.toArray()[0].toString()))
                                .setScale(0, BigDecimal.ROUND_FLOOR)
                                .intValue();

                        currPrice = values.toArray()[1].toString().replace("$", "").replace(")", "").trim();

                        mp.put(currPound+"",currPrice );
                        //만약 요걸루의 경우 범위값으로 되어있으므로 빈범위분은 계산하여 임의로 채워줌.
                        for(int j=beforePound+1; j<currPound ; j++){
                            mp.put(j+"",currPrice );
                        }
                        beforePound =Integer.parseInt( values.toArray()[0].toString());
                    }
                    System.out.println(jObj.values());
                }else{
                    currPound = ( new BigDecimal(values.toArray()[0].toString()))
                            .setScale(0, BigDecimal.ROUND_FLOOR)
                            .intValue();

                    currPrice = values.toArray()[1].toString().replace("$", "").replace(")", "").trim();
                    mp.put(currPound+"",currPrice );
                    System.out.println(jObj.values());

                }
            }
        }catch (Exception e){

        }
        return mp;
    }
    //공통 입력받은 값으로 배송비 계산
    private CompShppingAgentVO calDeliveryPrice(String beadeaji, String shppingCenter, String shipGubun){
        CompShppingAgentVO vo = new CompShppingAgentVO();
        try {

            //부피무게 적용여부 확인
			/*항공화물의 무게비용 당 허용되는 부피를 초과하는 화물의 경우, 중량무게 대신 부피환산무게(이하 부피무게)를 항공화물운임단가로 적용합니다.
			 *  따라서 저희 몰테일도 해당 화물에 대해서는 부피무게로 요금을 청구하게 됩니다.
			 *  부피무게 적용대상은 가로X세로X높이/166 (인치기준)를 계산하였을 때 중량보다 큰 화물에 해당하며 저희 몰테일에서는 고객님의 부담을 덜어드리고자, 타사와는 달리 해당 부피무게의 50%만 적용하여 청구하고 있습니다.
			 *  예를 들어 가로 18인치, 세로 12인치, 높이 10인치에 중량 5lbs인 화물의 경우 부피무게로 14lbs에 해당되나 7lbs의 요금만 청구하고 있습니다.
            */
            //부피무게를 계산하여 부피무게가 더 클경우 부피무게로 산정함.

            String goodPrice = getArguments().getString("goodPrice");//가격
            String goodWidth = getArguments().getString("goodWidth"); //가로
            String goodHeight = getArguments().getString("goodHeight"); //높이
            String goodVertical = getArguments().getString("goodVertical"); //세로
            String goodWeight = getArguments().getString("goodWeight"); //중량
            String national = getArguments().getString("national"); //국가정보
            BigDecimal weight = new BigDecimal(goodWeight); //입력받은 무게
            System.out.println("입력받은값----->"+weight);
            BigDecimal finalWeight= weight.setScale(0, BigDecimal.ROUND_UP)  ; //소스점 자리 반올림하여 무게 산정함.
            ////////////////////////////json파일에서 무게별 금액정보 얻어롬 /////////////////////////////
            Map<String, String> infoMap = getMapWeightPrice(beadeaji, shppingCenter, shipGubun, national, false);
            ///////////////////////////////////////////////////////////////////////////////////////

            String note="";  //노트
           // System.out.println("width=>" + goodWidth);
           // System.out.println("width=>" + goodWidth + "  height==>" + goodHeight + "  vertical==>" + goodVertical);
            //가로, 세로 , 높이 규격이 모두 있을때 부피무게 계산을 해줌.
            BigDecimal volumeWeight = BigDecimal.ZERO;
            BigDecimal width = BigDecimal.ZERO;
            BigDecimal vertical = BigDecimal.ZERO;
            BigDecimal height = BigDecimal.ZERO;
            int maxLbs =0;
            boolean yogirlooV = false;  //요걸루 부피무게 boolean값
            if (goodWidth != null && goodHeight != null && goodVertical != null
                    && !goodWidth.equals("") && !goodHeight.equals("") && !goodVertical.equals("") ) {
                 width = new BigDecimal(goodWidth);  //가로
                 vertical = new BigDecimal(goodVertical);  //세로
                 height =  new BigDecimal(goodHeight);  //높이
                System.out.println("width=>" + width + "  height==>" + height + "  vertical==>" + vertical);
                if(national!=null && national.equals("0")) {//미국일때 계산

                    if(beadeaji.equals("malltail")){
                        maxLbs = 60; //60파운드 이상은 1:1 게시판 문의
                    }else if(beadeaji.equals("nygirlz")){
                        maxLbs=30;
                    }else if(beadeaji.equals("iporter")){
                        maxLbs=2000;
                    }else if(beadeaji.equals("yogirloo")){
                        maxLbs=1837;
                    }
                    if (beadeaji.equals("yogirloo")) {
                        //요걸루의 경우 부피무게 기준이 다름 CBM으로 다시 계산방식 해줘야 함.
                        //CBM계산은 가로(inch) X 세로(inch) X 높이(inch) X 0.000016  *100으로 환산하여 계산해줌.
                        volumeWeight =  width.multiply(height).multiply(vertical).multiply(new BigDecimal( "0.0016"));
                        volumeWeight = volumeWeight.setScale(0,BigDecimal.ROUND_UP);
                        System.out.println("yogirloo -------------->volumeWeight "+volumeWeight);
                        if(volumeWeight.compareTo(finalWeight) > 0 ){
                            finalWeight = volumeWeight;
                            infoMap = getMapWeightPrice(beadeaji, shppingCenter, shipGubun, national, true);
                            maxLbs=99;
                        }
                    } else {
                        volumeWeight = ( width.multiply(height).multiply(vertical) ).divide(new BigDecimal(166), BigDecimal.ROUND_UP);

                        //부피무게계산
                        if (beadeaji.equals("malltail")) {

                            //한변의 길이가 60인치를 초과하는 경우 부피면제 100%적용
                            if (width.compareTo(new BigDecimal("60")) >0 || height.compareTo(new BigDecimal("60")) > 0 || vertical.compareTo(new BigDecimal("60")) > 0) {
                                note = "한변의 길이가 60인치를 초과하는 경우 부피무게100%적용";
                            } else {
                                volumeWeight = volumeWeight.multiply(new BigDecimal("0.5")).setScale(0, BigDecimal.ROUND_UP); //부피 50%
                                note = "부피무게 50%면제 ";
                            }
                            //부피무게와 실무게를 비교하여 실무게와 부피무게 50%중 더 무게가 많은것으러로 책정 몰테일 /뉴욕걸즈 더 많은 것을 택함
                            System.out.println("비교전 volumeWeight==" + volumeWeight + "    finalWeight==" + finalWeight);

                            if (volumeWeight.compareTo(finalWeight) > 0) {

                                finalWeight = volumeWeight; //부피무게가 더 크면 값을 치환해줌
                                System.out.println("비교후 volumeWeight==" + volumeWeight + "    finalWeight==" + finalWeight);
                            }
                        } else if (beadeaji.equals("nygirlz")) {
                            //한변의 길이가 70인치를 초과하는 경우 부피면제 100%적용
                            if (width.compareTo(new BigDecimal("70")) >0 || height.compareTo(new BigDecimal("70")) > 0 || vertical.compareTo(new BigDecimal("70")) > 0) {
                                note = "한변의 길이가 70인치를 초과하는 경우 부피무게100%적용";
                            } else {
                                volumeWeight = volumeWeight.multiply(new BigDecimal("0.5")).setScale(0, BigDecimal.ROUND_UP); //부피 50%
                                note = "부피무게 50%면제 ";
                            }
                            //부피무게와 실무게를 비교하여 실무게와 부피무게 50%중 더 무게가 많은것으러로 책정 몰테일 /뉴욕걸즈 더 많은 것을 택함
                            System.out.println("비교전 volumeWeight==" + volumeWeight + "    finalWeight==" + finalWeight);

                            if (volumeWeight.compareTo(finalWeight) > 0) {

                                finalWeight = volumeWeight; //부피무게가 더 크면 값을 치환해줌
                                System.out.println("비교후 volumeWeight==" + volumeWeight + "    finalWeight==" + finalWeight);
                            }
                        } else if (beadeaji.equals("iporter")) {
                            //CA,NJ : 부피무게와 중량의 차가 30lbs이상인 경우 부피무게 , 30lbs미만시 중량
                            //OR : 중량과 부피무게중 큰 무게 적용
                            //{"CA-켈리포니아","DW-델라웨이","NJ-뉴저지", "OR-오레곤"}
                            //부피무게와 실무게를 비교하여 실무게와 부피무게 50%중 더 무게가 많은것으러로 책정 몰테일 /뉴욕걸즈 더 많은 것을 택함
                            System.out.println("비교전 volumeWeight==" + volumeWeight + "    finalWeight==" + finalWeight);
                            if (shppingCenter != null && shppingCenter.equals("3")) {
                                if (volumeWeight.compareTo(finalWeight) > 0) {

                                    finalWeight = volumeWeight; //부피무게가 더 크면 값을 치환해줌
                                    System.out.println("비교후 volumeWeight==" + volumeWeight + "    finalWeight==" + finalWeight);

                                }
                                note = "OR센터 : 중량과부피부게중 큰무게적용";
                            } else if (shppingCenter != null && (shppingCenter.equals("0") || shppingCenter.equals("2"))) {
                                if ((volumeWeight.subtract(finalWeight)).compareTo(new BigDecimal(30)) > 0) {

                                    finalWeight = volumeWeight; //부피무게가 더 크면 값을 치환해줌
                                    System.out.println("비교후 volumeWeight==" + volumeWeight + "    finalWeight==" + finalWeight);
                                }
                                note = "CA,NJ센터 : 부피무게와 중량의 차가 30lbs이상인 경우 부피무게 , 30lbs미만시 중량";
                            } else {
                                note = "DW센터 : 물류센터 없음.";
                            }
                        }
                    }
                }else   if(national!=null && national.equals("1")) {//중국
                    if(beadeaji.equals("malltail")){
                        maxLbs = 30; //30kg 이상은 1:1 게시판 문의
                    }else if(beadeaji.equals("nygirlz")){
                        maxLbs=30;
                    }else if(beadeaji.equals("iporter")){
                        if(shipGubun!=null && shipGubun.equals("F")) {
                            maxLbs = 700;
                        }else if(shipGubun!=null && shipGubun.equals("S")) {
                            maxLbs = 2000;//해상은 2000
                        }

                    }else if(beadeaji.equals("yogirloo")){
                        maxLbs=1837;
                    }
                    //부피무게계산
                    if (beadeaji.equals("malltail")) {
                        //가로*세로*높이(cm) / 6000  <- 0.5kg단위로 계산됨.
                        //부피무게와 중량무게중 큰것으로 금액을 적용
                        volumeWeight = ( width.multiply(height).multiply(vertical) ).divide(new BigDecimal(6000), BigDecimal.ROUND_HALF_UP );
                        System.out.print(volumeWeight+"<---------volumeWeight");
                        volumeWeight = volumeWeight.setScale(1,BigDecimal.ROUND_HALF_UP);

                        System.out.print(volumeWeight+"<---------volumeWeight ROUND_HALF_UP");
                        if (volumeWeight.compareTo(finalWeight) > 0) {
                            finalWeight = volumeWeight; //부피무게가 더 크면 값을 치환해줌
                        }
                    }else if(beadeaji.equals("iporter")) {
                        volumeWeight = ( width.multiply(height).multiply(vertical) ).divide(new BigDecimal(5000), BigDecimal.ROUND_HALF_UP );
                        System.out.print(volumeWeight+"<---------volumeWeight");
                        volumeWeight = volumeWeight.setScale(1,BigDecimal.ROUND_HALF_UP);
                        if(volumeWeight.compareTo(weight) > 0){
                            finalWeight = volumeWeight;
                        }
                    }
                }else   if(national!=null && national.equals("2")) {//일본
                    if(beadeaji.equals("malltail")){
                        maxLbs = 30; //30kg 이상은 1:1 게시판 문의
                    }else if(beadeaji.equals("nygirlz")){
                        maxLbs=30;
                    }else if(beadeaji.equals("iporter")){
                        if(shipGubun!=null && shipGubun.equals("F")) {
                            maxLbs = 800;
                        }else if(shipGubun!=null && shipGubun.equals("S")) {
                            maxLbs = 400;//해상은 2000
                        }
                    }else if(beadeaji.equals("yogirloo")){
                        maxLbs=1837;
                    }
                    if (beadeaji.equals("malltail")) {
                        //일본의 경우 중량으로 배송비 책정이 기본
                        //단 부피무게공식(가로*세로*높이/5000)의 값이 68KG를 초과 또는 [(가로+세로)*2+높이]값이 330CM를 초과할경우 부피무게 68KG로 적용하여 배송비 책정 둘중 큰값으로 적용
                        volumeWeight = ( width.multiply(height).multiply(vertical) ).divide(new BigDecimal(5000), BigDecimal.ROUND_HALF_UP );
                        System.out.print(volumeWeight+"<---------volumeWeight");
                        volumeWeight = volumeWeight.setScale(1,BigDecimal.ROUND_HALF_UP);

                        System.out.print(volumeWeight+"<---------volumeWeight ROUND_HALF_UP");
                        BigDecimal jWeight=  BigDecimal.ZERO;
                        BigDecimal jLength = ( ( width.add(vertical) ).multiply(new BigDecimal("2")) )
                                           .add(height);
                        if(jLength.compareTo(new BigDecimal("330")) > 0){
                            jWeight = new BigDecimal("68");
                        }

                        if (volumeWeight.compareTo(new BigDecimal("68")) > 0 || jWeight.compareTo(new BigDecimal("68")) == 0) {
                            finalWeight = volumeWeight; //부피무게가 더 크면 값을 치환해줌
                        }
                    }else if(beadeaji.equals("iporter")){
                        volumeWeight = ( width.multiply(height).multiply(vertical) ).divide(new BigDecimal(5000), BigDecimal.ROUND_HALF_UP );
                        System.out.print(volumeWeight+"<---------volumeWeight");
                        volumeWeight = volumeWeight.setScale(1,BigDecimal.ROUND_HALF_UP);
                        //부피무게와 중량이 30Kg 미만인 경우 중량, 부피무게와 중량이 30Kg 이상인 경우 중량과 부피무게 중 큰 무게 적용
                        if(volumeWeight.compareTo(new BigDecimal("30"))< 0 && weight.compareTo(new BigDecimal("30"))<0  ){
                            finalWeight = weight;
                        }else{
                            if(volumeWeight.compareTo(weight) > 0){
                                finalWeight = volumeWeight;
                            }
                        }

                    }
                }else   if(national!=null && national.equals("3")) {//독일
                    if(beadeaji.equals("iporter")){
                        maxLbs = 1000;
                    }
                    //책정된 중량무게와 실제 책정된 부피무게의 50% 할인 무게와 비교 후 부피무게가 더 작은 경우 중량, 부피무게가 더 큰 경우 부피무게
                    if(beadeaji.equals("iporter")) {
                        volumeWeight = ( width.multiply(height).multiply(vertical) ).divide(new BigDecimal(6000), BigDecimal.ROUND_HALF_UP );
                        System.out.print(volumeWeight+"<---------volumeWeight");
                        volumeWeight = volumeWeight.setScale(1,BigDecimal.ROUND_HALF_UP);
                        if( (volumeWeight.multiply(new BigDecimal("0.5"))).compareTo(weight) > 0){
                            finalWeight = volumeWeight;
                        }
                    }
                }else   if(national!=null && national.equals("4")) {//영국
                    if(beadeaji.equals("iporter")){
                        maxLbs = 250;
                    }
                    //중량과 부피무게 중 큰 무게 적용
                    if(beadeaji.equals("iporter")) {
                        volumeWeight = ( width.multiply(height).multiply(vertical) ).divide(new BigDecimal(6000), BigDecimal.ROUND_HALF_UP );
                        System.out.print(volumeWeight+"<---------volumeWeight");
                        volumeWeight = volumeWeight.setScale(1,BigDecimal.ROUND_HALF_UP);
                        if(volumeWeight.compareTo(weight) > 0){
                            finalWeight = volumeWeight;
                        }
                    }
                }
                //입력받은 무게의 값을 조정하여 가격을 산정함.
                System.out.println("부피무게---->" + volumeWeight);
            }
            // System.out.println("lbsJson--"+lbsJson);

            //배대지별 배송요율표 max설정
            BigDecimal shipPrice = null;

            if(finalWeight.intValue() <= maxLbs ){
                    shipPrice = new BigDecimal(infoMap.get(finalWeight.toString()).toString());  //파운드별 배송비
            }else{
                shipPrice = BigDecimal.ZERO;
            }

            System.out.println("배송비 ==>"+shipPrice);
            System.out.println("입력받은 무게==>"+weight);
            System.out.print(shppingCenter);
            System.out.println("계산된 무게==>"+finalWeight);

            if(beadeaji.equals("malltail")){
                vo.setAgent("몰테일");
                vo.setLocalShipChage("-");
            }else if(beadeaji.equals("nygirlz")){
                vo.setAgent("뉴욕걸즈");
                vo.setLocalShipChage("-");
            }else if(beadeaji.equals("iporter")){
                vo.setAgent("아이포터");
                vo.setLocalShipChage("-");
            }else if(beadeaji.equals("yogirloo")){
                vo.setAgent("요걸루");
                vo.setLocalShipChage("2차결제");
            }
            if(shipGubun!=null && shipGubun.equals("F")){
                vo.setGubun("항공");
            }else if(shipGubun!=null && shipGubun.equals("S")){
                vo.setGubun("해상");
            }
            //{"CA-켈리포니아","DW-델라웨이","NJ-뉴저지", "OR-오레곤"}
            if(shppingCenter!=null && shppingCenter.equals("0")) {
                vo.setShippingCenterName("<CA-켈리포니아>");
            }else if(shppingCenter!=null && shppingCenter.equals("1")) {
                vo.setShippingCenterName("<DW-델라웨이>");
            }else if(shppingCenter!=null && shppingCenter.equals("2")) {
                vo.setShippingCenterName("<NJ-뉴저지>");
            }else if(shppingCenter!=null && shppingCenter.equals("3")) {
                vo.setShippingCenterName("<OR-오레곤>");
            }
            //미국이면 lbs 아니면 kg
            String weightUnit = "";
            if(national!=null && national.equals("0")) {
                weightUnit= "lbs";
            }else {
                weightUnit= "kg";
            }
            vo.setRealWeight(goodWeight+weightUnit);
            vo.setVolumeWeight(volumeWeight.toString()+weightUnit);
            vo.setApplyWeight(finalWeight.toString()+weightUnit);
            vo.setShppingCharge(shipPrice.toString());
            vo.setNote(note);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return  vo;
    }
}