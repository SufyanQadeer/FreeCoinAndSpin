package guiderbx.rbxeventsguide;

import android.content.Context;
import android.os.Bundle;

import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

class popUp
{
private static com.google.android.gms.ads.InterstitialAd InterstitialAd;
   static void requestAndShowNewInterstitial(final Context context) {


               InterstitialAd = new InterstitialAd(context);
               InterstitialAd.setAdUnitId(context.getResources().getString(R.string.interstitial_ad_unit_id));
           Bundle extras = new Bundle();
      ConsentInformation consentInformation = ConsentInformation.getInstance(context);
       if (consentInformation.getConsentStatus().equals(ConsentStatus.NON_PERSONALIZED)) {
           extras.putString("npa", "1");
       }

           AdRequest adRequest = new AdRequest.Builder()
                   .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                   .build();
           InterstitialAd.loadAd(adRequest);
           InterstitialAd.setAdListener(new AdListener() {
               @Override
               public void onAdLoaded() {
                   super.onAdLoaded();
                   InterstitialAd.show();
               }

               @Override
               public void onAdClosed() {
               }

               @Override
               public void onAdLeftApplication() {

               }

               @Override
               public void onAdFailedToLoad(int i) {
               }
           });
       }

}
