package in.asetalias.alias.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.asetalias.alias.R;
import in.asetalias.alias.dataModels.CommunityModel;

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
        CommunityModel communityItem = communityItemsList.get(i);

        //Setting text view texts from the model class
        communityViewHolder.txtTitleEvent.setText(Html.fromHtml(communityItem.getTitle()));
        communityViewHolder.txtDescriptionEvent.setText(Html.fromHtml(communityItem.getDesc()));
    }

    @Override
    public int getItemCount() {
        return (null != communityItemsList ? communityItemsList.size() : 0);
    }

    class CommunityViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitleEvent;
        TextView txtDescriptionEvent;

        CommunityViewHolder(View view) {
            super(view);
            this.txtTitleEvent = (TextView) view.findViewById(R.id.txt_title_community);
            this.txtDescriptionEvent = (TextView) view.findViewById(R.id.txt_description_community);
        }
    }
}