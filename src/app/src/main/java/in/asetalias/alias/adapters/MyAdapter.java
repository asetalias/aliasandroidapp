package in.asetalias.alias.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.asetalias.alias.R;
import in.asetalias.alias.dataModels.CommunityModel;
import in.asetalias.alias.dataModels.EventModel;
import in.asetalias.alias.dataModels.SiteMetaModel;
import in.asetalias.alias.dataModels.WebinarModel;

/**
 * Created by Jayant on 2017-11-03.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int TYPE_EVENT = 1;
    private final static int TYPE_WEBINAR = 2;
    private final static int TYPE_COMMUNITY = 3;
    private final static int TYPE_ABOUT = 4;

    private List<Object> feedItemsList;
    private Context context;

    public MyAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getItemViewType(int position) {

        if (feedItemsList.get(position) instanceof EventModel) {
            return TYPE_EVENT;
        } else if (feedItemsList.get(position) instanceof WebinarModel) {
            return TYPE_WEBINAR;
        } else if (feedItemsList.get(position) instanceof CommunityModel) {
            return TYPE_COMMUNITY;
        } else if (feedItemsList.get(position) instanceof SiteMetaModel) {
            return TYPE_ABOUT;
        }

        return -1;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        switch (viewType) {
            case TYPE_EVENT:
                EventModel eventModel = (EventModel) feedItemsList.get(position);
                ((EventViewHolder) holder).showEventDetails(eventModel);
                break;
            case TYPE_COMMUNITY:
                WebinarModel webinarModel = (WebinarModel) feedItemsList.get(position);
                ((WebinarViewHolder) holder).showWebinarDetails(webinarModel);
                break;
            case TYPE_WEBINAR:
                CommunityModel communityModel = (CommunityModel) feedItemsList.get(position);
                ((CommunityViewHolder) holder).showCommunityDetails(communityModel);
                break;
            case TYPE_ABOUT:
                SiteMetaModel siteMetaModel = (SiteMetaModel) feedItemsList.get(position);
                ((AboutViewHolder) holder).showAboutDetails(siteMetaModel);
                break;
        }
    }

    @Override
//    public int getItemCount() {    return feedItemsList.size();    }
    public int getItemCount() {
        return (null != feedItemsList ? feedItemsList.size() : 0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Attach layout for single cell
        int layout = 0;
        RecyclerView.ViewHolder viewHolder;

        switch (viewType) {

            case TYPE_EVENT:
                layout = R.layout.card_event_layout;
                View eventView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(layout, parent, false);
                viewHolder = new EventViewHolder(eventView);
                break;

            case TYPE_WEBINAR:
                layout = R.layout.card_webinar_layout;
                View webinarView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(layout, parent, false);
                viewHolder = new WebinarViewHolder(webinarView);
                break;

            case TYPE_COMMUNITY:
                layout = R.layout.card_community_layout;
                View communityView = LayoutInflater
                        .from(parent.getContext())
                        .inflate(layout, parent, false);
                viewHolder = new WebinarViewHolder(communityView);
                break;

            case TYPE_ABOUT:
                viewHolder = null;
                break;

            default:
                viewHolder = null;
                break;
        }
        return viewHolder;
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPosterEvent;
        TextView txtTitleEvent;
        TextView txtDescriptionEvent;

        public EventViewHolder(View itemView) {
            super(itemView);
            this.imgPosterEvent = (ImageView) itemView.findViewById(R.id.img_poster_event);
            this.txtTitleEvent = (TextView) itemView.findViewById(R.id.txt_title_event);
            this.txtDescriptionEvent = (TextView) itemView.findViewById(R.id.txt_description_event);
        }

        void showEventDetails(EventModel eventModel) {
            //Render image using Picasso library
            if (!TextUtils.isEmpty(eventModel.getPoster())) {
                Picasso.with(context).load(eventModel.getPoster())
                        .error(R.drawable.placeholder)
                        .placeholder(R.drawable.placeholder)
                        .into(this.imgPosterEvent);
            }

            //Setting text view texts from the model class
            txtTitleEvent.setText(Html.fromHtml(eventModel.getTitle()));
            txtDescriptionEvent.setText(Html.fromHtml(eventModel.getDescription()));
        }
    }

    public class WebinarViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitleWebinar;
        private TextView txtDescriptionWebinar;

        public WebinarViewHolder(View itemView) {
            super(itemView);
            this.txtTitleWebinar = (TextView) itemView.findViewById(R.id.txt_title_webinar);
            this.txtDescriptionWebinar = (TextView) itemView.findViewById(R.id.txt_description_webinar);
        }

        void showWebinarDetails(WebinarModel webinarModel) {
            txtTitleWebinar.setText(Html.fromHtml(webinarModel.getTitle()));
            txtDescriptionWebinar.setText(Html.fromHtml(webinarModel.getDescription()));
        }
    }

    public class CommunityViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitleComunity;
        private TextView txtDescriptionCommunity;

        public CommunityViewHolder(View itemView) {
            super(itemView);
            this.txtTitleComunity = (TextView) itemView.findViewById(R.id.txt_title_community);
            this.txtDescriptionCommunity = (TextView) itemView.findViewById(R.id.txt_description_community);
        }

        void showCommunityDetails(CommunityModel communityModel) {
            txtTitleComunity.setText(Html.fromHtml(communityModel.getTitle()));
            txtDescriptionCommunity.setText(Html.fromHtml(communityModel.getDesc()));
        }
    }

    public class AboutViewHolder extends RecyclerView.ViewHolder {

        public AboutViewHolder(View itemView) {
            super(itemView);
        }

        void showAboutDetails(SiteMetaModel siteMetaModel) {
//            code to show data from site-meta.json

        }
    }
}