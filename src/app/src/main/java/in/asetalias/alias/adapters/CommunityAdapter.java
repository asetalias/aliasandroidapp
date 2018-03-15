package in.asetalias.alias.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.asetalias.alias.R;
import in.asetalias.alias.dataModels.CommunityModel;

import static in.asetalias.alias.data.DataConstants.urlLogo;

/**
 * Created by Jayant on 2017-11-05.
 */

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder> {
    private static final String TAG = "CommunityAdapter";

    private List<CommunityModel> communityItemsList;
    private Context mContext;

    public CommunityAdapter(Context context, List<CommunityModel> communityItemsList) {
        this.communityItemsList = communityItemsList;
        this.mContext = context;
    }

    @Override
    public CommunityAdapter.CommunityViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_community_layout, viewGroup, false);
        CommunityAdapter.CommunityViewHolder viewHolder = new CommunityAdapter.CommunityViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommunityAdapter.CommunityViewHolder communityViewHolder, int i) {
        final CommunityModel communityItem = communityItemsList.get(i);
        String logo = urlLogo + communityItem.getLogo();
        //Render image using Picasso library
        if (!TextUtils.isEmpty(communityItem.getLogo())) {
            Picasso.with(mContext).load(logo)
                    .error(R.drawable.vc_broken_image_placeholder)
                    .placeholder(R.drawable.vc_image_placeholder)
                    .into(communityViewHolder.imgCommunity);
        }

        //Setting text view texts from the model class
        communityViewHolder.txtTitleEvent.setText(Html.fromHtml(communityItem.getTitle()));
        Markwon.setMarkdown(communityViewHolder.txtDescriptionCommunity, String.valueOf(Html.fromHtml(communityItem.getDesc())));
        communityViewHolder.txtDescriptionCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(communityViewHolder.txtDescriptionCommunity.getMaxLines() != Integer.MAX_VALUE)
                    communityViewHolder.txtDescriptionCommunity.setMaxLines(Integer.MAX_VALUE);
                else
                    communityViewHolder.txtDescriptionCommunity.setMaxLines(4);
            }
        });


        if (!communityItem.getWebsite().isEmpty()) {
            communityViewHolder.btnWebsite.setVisibility(View.VISIBLE);
        }
        if (!communityItem.getMeetup().isEmpty()) {
            communityViewHolder.btnMeetup.setVisibility(View.VISIBLE);
        }
        if (!communityItem.getTelegram().isEmpty()) {
            communityViewHolder.btnTelegram.setVisibility(View.VISIBLE);
        }

        communityViewHolder.btnWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*DevNote:
                 * when referring button click events of some fragment from recyclerview adapter, we do it like this:
                 * view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(communityItem.getWebsite())));
                */
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(communityItem.getWebsite())));
                Log.d(TAG, communityItem.getWebsite());
            }
        });

        communityViewHolder.btnMeetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(communityItem.getMeetup())));
                Log.d(TAG, communityItem.getMeetup());
            }
        });

        communityViewHolder.btnTelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(communityItem.getTelegram())));
                Log.d(TAG, communityItem.getTelegram());
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != communityItemsList ? communityItemsList.size() : 0);
    }

    class CommunityViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCommunity;
        CardView cardViewCommunity;
        TextView txtTitleEvent;
        TextView txtDescriptionCommunity;

        Button btnWebsite;
        Button btnMeetup;
        Button btnTelegram;

        CommunityViewHolder(View view) {
            super(view);
            this.cardViewCommunity = (CardView) view.findViewById(R.id.cardview_community);
            this.imgCommunity = (ImageView) view.findViewById(R.id.img_community);
            this.txtTitleEvent = (TextView) view.findViewById(R.id.txt_title_community);
            this.txtDescriptionCommunity = (TextView) view.findViewById(R.id.txt_description_community);

            this.btnWebsite = (Button) view.findViewById(R.id.btn_website_community);
            this.btnMeetup = (Button) view.findViewById(R.id.btn_meetup_community);
            this.btnTelegram = (Button) view.findViewById(R.id.btn_telegram_community);
        }
    }
}
