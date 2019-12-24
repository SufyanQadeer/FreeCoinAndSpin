package slotospins.coinsslot;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class showPosts extends AppCompatActivity {

    RecyclerView Posts;
    List<PostDataModel> PostsList=new ArrayList<>();
    private DatabaseReference databaseReference;
    RecyclerAdapterForPosts PostsAdapter;
    String Choice;
    TextView title;
    ImageView goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_posts);
        loadBanner();
        title=findViewById(R.id.PageTitle);
        goBack=findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Choice=getIntent().getStringExtra("Which");
        if(Choice!=null)
        {
            if(Choice.equals("Spins"))
            {
                title.setText("Spin Links");
                databaseReference = FirebaseDatabase.getInstance().getReference("Spins");
            }
            else {
                databaseReference = FirebaseDatabase.getInstance().getReference("Coins");
                title.setText("Coin Links");

            }
        }
        Posts=findViewById(R.id.RecyclerPosts);//recycler view

        if(Choice.equals("Spins"))
        PostsAdapter=new RecyclerAdapterForPosts(showPosts.this,PostsList,"spins");
        else
        PostsAdapter=new RecyclerAdapterForPosts(showPosts.this,PostsList,"coins");


        Posts.setAdapter(PostsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(showPosts.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        Posts.setLayoutManager(layoutManager);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PostsList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    PostDataModel postDataModel=dataSnapshot1.getValue(PostDataModel.class);
                    PostsList.add(postDataModel);
                    PostsAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
    private void loadBanner()
    {

        AdView mAdView = findViewById(R.id.postsBanner);
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
