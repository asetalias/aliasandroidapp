package in.asetalias.alias.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import in.asetalias.alias.dataModels.WebinarModel;

import static in.asetalias.alias.data.LoadJson.thumbHq;
import static in.asetalias.alias.data.LoadJson.urlYoutubeApp;
import static in.asetalias.alias.data.LoadJson.urlYoutubeThumb;

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
    public void onBindViewHolder(final WebinarAdapter.WebinarViewHolder webinarViewHolder, int i) {
        WebinarModel webinarItem = webinarItemsList.get(i);

        String thumbUrl = urlYoutubeThumb + webinarItem.getVideoId() + thumbHq;

        //Render image using Picasso library
        if (!TextUtils.isEmpty(webinarItem.getVideoId())) {
            Picasso.with(mContext).load(thumbUrl)
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(webinarViewHolder.imgWebinar);
        }

        //Setting text view texts from the model class
        webinarViewHolder.txtTitlewebinar.setText(Html.fromHtml(webinarItem.getTitle()));
        webinarViewHolder.txtDescriptionWebinar.setText(Html.fromHtml(webinarItem.getDescription()));
        final String id = webinarItem.getVideoId();

        webinarViewHolder.imgWebinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlYoutubeApp + id)));
            }
        });
    }


    @Override
    public int getItemCount() {
        return (null != webinarItemsList ? webinarItemsList.size() : 0);
    }

    class WebinarViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitlewebinar;
        TextView txtDescriptionWebinar;
        ImageView imgWebinar;


        WebinarViewHolder(View view) {
            super(view);
            this.txtTitlewebinar = (TextView) view.findViewById(R.id.txt_title_webinar);
            this.txtDescriptionWebinar = (TextView) view.findViewById(R.id.txt_description_webinar);
            this.imgWebinar = (ImageView) view.findViewById(R.id.img_webinar);
        }
    }
}