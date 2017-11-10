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
import in.asetalias.alias.dataModels.EventModel;


/**
 * Created by Jayant on 2017-10-28.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private static final String TAG = "EventAdapter";

    private List<EventModel> eventItemsList;
    private Context mContext;

    public EventAdapter(Context context, List<EventModel> eventItemsList) {
        this.eventItemsList = eventItemsList;
        this.mContext = context;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_event_layout, viewGroup, false);
        EventViewHolder viewHolder = new EventViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventViewHolder eventViewHolder, int i) {
        EventModel eventItem = eventItemsList.get(i);

        //Render image using Picasso library
        if (!TextUtils.isEmpty(eventItem.getPoster())) {
            Picasso.with(mContext).load(eventItem.getPoster())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(eventViewHolder.imgPosterEvent);
        }

        //Setting text view texts from the model class
        eventViewHolder.txtTitleEvent.setText(Html.fromHtml(eventItem.getTitle()));
        eventViewHolder.txtDescriptionEvent.setText(Html.fromHtml(eventItem.getDescription()));
    }

    @Override
    public int getItemCount() {
        return (null != eventItemsList ? eventItemsList.size() : 0);
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPosterEvent;
        TextView txtTitleEvent;
        TextView txtDescriptionEvent;

        EventViewHolder(View view) {
            super(view);
            this.imgPosterEvent = (ImageView) view.findViewById(R.id.img_poster_event);
            this.txtTitleEvent = (TextView) view.findViewById(R.id.txt_title_event);
            this.txtDescriptionEvent = (TextView) view.findViewById(R.id.txt_description_event);
        }
    }
}