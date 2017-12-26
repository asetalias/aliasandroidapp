package in.asetalias.alias.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.asetalias.alias.R;
import in.asetalias.alias.dataModels.WebinarModel;

import static in.asetalias.alias.data.DataConstants.thumbHq;
import static in.asetalias.alias.data.DataConstants.thumbMax;
import static in.asetalias.alias.data.DataConstants.urlYoutubeApp;
import static in.asetalias.alias.data.DataConstants.urlYoutubeThumb;

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
//        WebinarAdapter.WebinarViewHolder webinarViewHolder = new WebinarAdapter.WebinarViewHolder(view);
//        return webinarViewHolder;
        return new WebinarAdapter.WebinarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WebinarAdapter.WebinarViewHolder webinarViewHolder, int i) {
        WebinarModel webinarItem = webinarItemsList.get(i);

        String thumbUrl = urlYoutubeThumb + webinarItem.getVideoId() + thumbMax;

        //Render image using Picasso library
        if (!TextUtils.isEmpty(webinarItem.getVideoId())) {
            Picasso.with(mContext).load(thumbUrl)
                    .error(R.drawable.vc_broken_image_placeholder)
                    .placeholder(R.drawable.vc_image_placeholder)
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

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 12;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
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