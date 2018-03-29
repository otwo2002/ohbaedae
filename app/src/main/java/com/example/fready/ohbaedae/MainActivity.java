package com.example.fready.ohbaedae;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    InfoFragment infoFragment;      //제품 입력정보
    ResultFragment resultFragment;   //계산결과
    EventFragment eventFragment;     //이벤트
    FeeFragment feeFragment;  //수수료비교
    TaxFragment taxFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null ){
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.action_bar_main);

        }
        infoFragment = new InfoFragment();
        eventFragment = new EventFragment();
        feeFragment = new FeeFragment();
        taxFragment =  new TaxFragment();
        //화면에 프레그먼트 화면 붙혀줌.
        getSupportFragmentManager().beginTransaction().replace(R.id.container, infoFragment).commit();


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //-------------------------------------------------------------------------------------
        //tab에 메뉴정보 입력
        TabLayout tab = (TabLayout)findViewById(R.id.tab);
        tab.addTab(tab.newTab().setText("배송비계산"),0,true);
        tab.addTab(tab.newTab().setText("수수료비교"),1,false);
        tab.addTab(tab.newTab().setText("관부과세"),2,false);
        tab.addTab(tab.newTab().setText("이벤트"),3,false);

        //탭클릭시 fragment변경하기 위해서 호출
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if ( position==0 ) {
                    selected = infoFragment;
                }else if(position==1){
                    selected = feeFragment;
                }else if (position==2){
                    selected =  taxFragment ;
                }else if (position==3){
                    selected = eventFragment;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if ( position==0 ) {
                    selected = infoFragment;
                }else if(position==1){
                    selected = feeFragment;
                }else if (position==2){
                    selected = taxFragment;
                }else if (position==3){
                    selected = eventFragment;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();

            }
        });
    }
    //메인화면에서 뒤로버튼 두번누르면 화면 종료
    private long lastTimeBackPressed;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(System.currentTimeMillis() - lastTimeBackPressed < 2000){
                finish();
                return;
            }
            Toast.makeText(this, "뒤로버튼을 한번더 누르면 어플리케이션이 종료됩니다. ", Toast.LENGTH_SHORT).show();
            lastTimeBackPressed = System.currentTimeMillis();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
       //     return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.keyFind) {
            Intent intent = new Intent(this, KeyActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.excharge) {
            Intent intent = new Intent(this, ExchangeActivity.class);
            this.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void callResult(GoodInfoVO goodInfoVO){

        Bundle bundle = new Bundle();
        bundle.putString("national", goodInfoVO.getNational());
        bundle.putString("goodPrice", goodInfoVO.getGoodPrice());
        bundle.putString("tax", goodInfoVO.getTax());
        bundle.putString("localShpping", goodInfoVO.getLocalShipCharge());
        bundle.putString("goodWidth", goodInfoVO.getGoodWidth());
        bundle.putString("goodHeight", goodInfoVO.getGoodHeight());
        bundle.putString("goodVertical", goodInfoVO.getGoodVertical());
        bundle.putString("goodWeight", goodInfoVO.getGoodWeight());
        //Toast.makeText(this, "MainAcivity.callResult", Toast.LENGTH_LONG).show();
        resultFragment = new ResultFragment();  //결과값 생성
        resultFragment.setArguments(bundle);
        System.out.print("+++++++++++++++++++++bundle+++++++++++++++++++++++");
        System.out.println( goodInfoVO.getGoodWeight());
        //Toast.makeText(this, "MainAcivity.callResult+resultFragment", Toast.LENGTH_LONG).show();
        getSupportFragmentManager().beginTransaction().replace(R.id.calResult, resultFragment).commit();

    }

    //getSupportActionBar().hide();


}
