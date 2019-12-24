package guiderbx.rbxeventsguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class playActivity extends AppCompatActivity {

    Button goToChoice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        MobileAds.initialize(this);
        startActivity(new Intent(playActivity.this,SplashScreen.class));
        loadBanner();
        goToChoice=findViewById(R.id.goToChoice);
        goToChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.requestAndShowNewInterstitial(getApplicationContext());
                startActivity(new Intent(playActivity.this,choiceActivity.class));
                finish();
            }
        });
    }
    private void loadBanner()
    {

        AdView mAdView = findViewById(R.id.playBanner);
        Bundle extras = new Bundle();
        ConsentInformation consentInformation = ConsentInformation.getInstance(getApplicationContext());
        if (consentInformation.getConsentStatus().equals(ConsentStatus.NON_PERSONALIZED)) {
            extras.putString("npa", "1");
        }
        AdRequest adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                .build();
        mAdView.loadAd(adRequest);
    }
}
