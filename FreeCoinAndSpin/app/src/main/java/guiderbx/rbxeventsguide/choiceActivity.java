package guiderbx.rbxeventsguide;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Objects;

public class choiceActivity extends AppCompatActivity {

    Button goToSpinsPosts,goToCoinsPosts,goToPlayStore,goToPrivacyPolicy,showTutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        loadBanner();
        initializeViews();
        setClickListeners();
    }
    private void initializeViews()
    {
        goToCoinsPosts=findViewById(R.id.goToCoinsPage);
        goToSpinsPosts=findViewById(R.id.goToSpinsPage);
        goToPlayStore=findViewById(R.id.rateUs);
        goToPrivacyPolicy=findViewById(R.id.goToPrivacyPolicy);
        showTutorial=findViewById(R.id.showTutorial);
    }
    private void setClickListeners()
    {
        goToPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.requestAndShowNewInterstitial(getApplicationContext());
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                }
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(browserIntent);

            }
        });
        goToPlayStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                popUp.requestAndShowNewInterstitial(getApplicationContext());
                rateUs();
            }
        });
        goToSpinsPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.requestAndShowNewInterstitial(getApplicationContext());
                Intent gotoSpinsIntent=new Intent(choiceActivity.this,showPosts.class);
                gotoSpinsIntent.putExtra("Which","Spins");
                startActivity(gotoSpinsIntent);
            }
        });
        goToCoinsPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.requestAndShowNewInterstitial(getApplicationContext());
                Intent gotoCoinsIntent=new Intent(choiceActivity.this,showPosts.class);
                gotoCoinsIntent.putExtra("Which","Coins");
                startActivity(gotoCoinsIntent);
            }
        });
        showTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.requestAndShowNewInterstitial(getApplicationContext());
                final Dialog tutorial=new Dialog(choiceActivity.this);
                tutorial.requestWindowFeature(Window.FEATURE_NO_TITLE);
                tutorial.setContentView(R.layout.tutorial);
                Objects.requireNonNull(tutorial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                tutorial.setCancelable(true);
                Button close;
                close=tutorial.findViewById(R.id.closetutorial);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(tutorial.isShowing())
                            tutorial.dismiss();
                    }
                });
                tutorial.show();
            }
        });
    }

    @Override
    public void onBackPressed() {

        final Dialog rateusDialoge=new Dialog(this);
        rateusDialoge.requestWindowFeature(Window.FEATURE_NO_TITLE);
        rateusDialoge.setContentView(R.layout.backbuttonpressed);
        Objects.requireNonNull(rateusDialoge.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rateusDialoge.setCancelable(true);
        Button yes,no,rateus;
        yes=rateusDialoge.findViewById(R.id.yesExit);
        no=rateusDialoge.findViewById(R.id.notExit);
        rateus=rateusDialoge.findViewById(R.id.rateusbutton);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.requestAndShowNewInterstitial(getApplicationContext());
                finish();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.requestAndShowNewInterstitial(getApplicationContext());
                if(rateusDialoge.isShowing())
                    rateusDialoge.dismiss();
            }
        });
        rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rateusDialoge.isShowing())
                    rateusDialoge.dismiss();
                rateUs();
            }
        });
        rateusDialoge.show();
    }
    private void rateUs()
    {
        final String appPackageName = getPackageName();
        Uri uri = Uri.parse("market://details?id="+appPackageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appPackageName)));
            }
            catch (android.content.ActivityNotFoundException anfe)
            {
                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id="+appPackageName));
                startActivity(i);
            }
        }
    }
    private void loadBanner()
    {

        AdView mAdView = findViewById(R.id.choiceBanner);
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
