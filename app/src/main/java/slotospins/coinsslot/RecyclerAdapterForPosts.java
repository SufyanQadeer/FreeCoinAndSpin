package slotospins.coinsslot;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

public class RecyclerAdapterForPosts extends RecyclerView.Adapter<RecyclerAdapterForPosts.ViewHolder> {

    private List<PostDataModel> postsList;
    private Context context;
    private String CoinsOrSpins;

    RecyclerAdapterForPosts(Context context, List<PostDataModel> Posts, String coinsOrSpins)
    {
        this.context = context;
        this.postsList =Posts;
        this.CoinsOrSpins=coinsOrSpins;
    }
    @NonNull
    @Override
    public RecyclerAdapterForPosts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new RecyclerAdapterForPosts.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
    {
        PostDataModel postDataModel=postsList.get(position);
        holder.Title.setText(postDataModel.getTitle());
        holder.DateTime.setText(postDataModel.getDate() + "   " + postDataModel.getTime());
            if(CoinsOrSpins.equals("coins"))
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.dot.setBackground(context.getDrawable(R.drawable.yellowdot));
                }
            }
            else
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.dot.setBackground(context.getDrawable(R.drawable.greendot));
                }
            }
        holder.Title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.requestAndShowNewInterstitial(context);
            }
        });
            holder.DateTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popUp.requestAndShowNewInterstitial(context);
                }
            });
        holder.Open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showOpenOrShare(position);
            }
        });

    }
    @Override
    public int getItemCount() {
        return postsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView Title,DateTime, dot;
        Button Open;
        ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            Title=mView.findViewById(R.id.postTitle);
            DateTime=mView.findViewById(R.id.DateAndTime);
            dot =mView.findViewById(R.id.Dot);
            Open=mView.findViewById(R.id.Open);
        }

    }
    private void showOpenOrShare(final int position)
    {
        TextView postTitle;
        Button openReward, shareReward;
        final Dialog openOrShare=new Dialog(context);
        openOrShare.requestWindowFeature(Window.FEATURE_NO_TITLE);
        openOrShare.setContentView(R.layout.openorshare);
        Objects.requireNonNull(openOrShare.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        openOrShare.setCancelable(true);
        postTitle=openOrShare.findViewById(R.id.postTitlee);
        postTitle.setText(postsList.get(position).getTitle());
        openReward=openOrShare.findViewById(R.id.openReward);
        shareReward=openOrShare.findViewById(R.id.shareReward);
        openReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.requestAndShowNewInterstitial(context);
                Button yes, no;
                final Dialog yesORno=new Dialog(context);
                yesORno.requestWindowFeature(Window.FEATURE_NO_TITLE);
                yesORno.setContentView(R.layout.openlinkconfirmation);
                Objects.requireNonNull(yesORno.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                yesORno.setCancelable(true);
                yes=yesORno.findViewById(R.id.yes);
                no=yesORno.findViewById(R.id.no);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popUp.requestAndShowNewInterstitial(context);
                        if (yesORno.isShowing())
                            yesORno.dismiss();
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+postsList.get(position).getLink()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                        }
                        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        context.startActivity(browserIntent);
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popUp.requestAndShowNewInterstitial(context);
                        if (yesORno.isShowing())
                            yesORno.dismiss();
                    }
                });
                yesORno.show();
            }
        });
        shareReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                popUp.requestAndShowNewInterstitial(context);
                if(openOrShare.isShowing())
                    openOrShare.dismiss();
                Intent sharingIntent=new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String msg="Share this App with your Friends to get More Daily Free Coins and Spin Rewards.";
                msg+="\nhttps://play.google.com/store/apps/details?id="+context.getPackageName()+"\n";
                msg+="===============\n";
                msg+="*"+postsList.get(position).getTitle() +"*\n";
                msg+=postsList.get(position).getLink()+"\n";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT,"Free Coins and Spin Rewards");
                sharingIntent.putExtra(Intent.EXTRA_TEXT,msg);
                context.startActivity(Intent.createChooser(sharingIntent,"Share Via"));
            }
        });
        openOrShare.show();
    }

}