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
import in.asetalias.alias.dataModels.WebinarModel;

/**
 * Created by Jayant on 2017-11-05.
 */

public class WebinarAdapter extends RecyclerView.Adapter<WebinarAdapter.WebinarViewHolder> {
    private static final String TAG = "EventAdapter";

    private List<WebinarModel> webinarItemsList;
    private Context mContext;

    public WebinarAdapter(Context context, List<WebinarModel> webinarItemsList) {
        this.webinarItemsList = webinarItemsList;
        this.mContext = context;
    }

    @Override
    public WebinarAdapter.WebinarViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_webinar_layout, viewGroup, false);
        WebinarAdapter.WebinarViewHolder viewHolder = new WebinarAdapter.WebinarViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WebinarAdapter.WebinarViewHolder webinarViewHolder, int i) {
        WebinarModel webinarItem = webinarItemsList.get(i);

        //Setting text view texts from the model class
        webinarViewHolder.txtTitlewebinar.setText(Html.fromHtml(webinarItem.getTitle()));
        webinarViewHolder.txtDescriptionWebinar.setText(Html.fromHtml(webinarItem.getDescription()));
    }

    @Override
    public int getItemCount() {
        return (null != webinarItemsList ? webinarItemsList.size() : 0);
    }

    class WebinarViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitlewebinar;
        TextView txtDescriptionWebinar;

        WebinarViewHolder(View view) {
            super(view);
            this.txtTitlewebinar = (TextView) view.findViewById(R.id.txt_title_webinar);
            this.txtDescriptionWebinar = (TextView) view.findViewById(R.id.txt_description_webinar);
        }
    }
}