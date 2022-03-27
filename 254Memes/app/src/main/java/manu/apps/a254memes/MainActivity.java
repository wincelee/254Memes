package manu.apps.a254memes;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView homeBTNV;
    private ViewPager homeViewPager;
    private AdView mainActivityAdView;

    Tuwekememes tuwekememesFragment;
    Chekimemes chekimemesFragment;
    Privacypolicy privacypolicy;

    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");

        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.KITKAT){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        homeBTNV = findViewById(R.id.homeBTNV);
        homeViewPager = findViewById(R.id.homeViewPager);
        mainActivityAdView = findViewById(R.id.mainActivityAdView);

        AdRequest adRequest = new AdRequest.Builder().build();
        mainActivityAdView.loadAd(adRequest);

        homeBTNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    /**Set the Number of items that you have here and there ID's*/
                    case R.id.ChekiMeme:
                        homeViewPager.setCurrentItem(0);
                        break;
                    case R.id.TuwekeMeme:

                        homeViewPager.setCurrentItem(1);
                        break;
                    case R.id.Privacypolicy:

                        homeViewPager.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });

        homeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    homeBTNV.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                homeBTNV.getMenu().getItem(position).setChecked(true);
                prevMenuItem = homeBTNV.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        setupViewPager(homeViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        chekimemesFragment = new Chekimemes();
        tuwekememesFragment = new Tuwekememes();
        privacypolicy = new Privacypolicy();


        adapter.addFragment(chekimemesFragment);
        adapter.addFragment(tuwekememesFragment);
        adapter.addFragment(privacypolicy);

        viewPager.setAdapter(adapter);
    }
}
