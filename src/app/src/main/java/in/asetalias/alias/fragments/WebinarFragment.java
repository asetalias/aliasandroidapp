package in.asetalias.alias.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import in.asetalias.alias.R;
import in.asetalias.alias.adapters.WebinarAdapter;
import in.asetalias.alias.dataModels.WebinarModel;

import static in.asetalias.alias.data.LoadJson.urlWebinar;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebinarFragment extends Fragment {

    private static final String TAG = "WebinarFragment";

    private List<WebinarModel> webinarList;
    private RecyclerView recyclerViewWeb;
    private WebinarAdapter webinarAdapter;
    private ProgressBar progressBarWebinar;
    private WebinarModel webinarItem;

    private SwipeRefreshLayout swipeRefreshLayoutWebinar;

    public WebinarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_webinar, container, false);

        recyclerViewWeb = (RecyclerView) rootView.findViewById(R.id.recycler_view_webinar);
        progressBarWebinar = (ProgressBar) rootView.findViewById(R.id.progress_bar_webinar);
        swipeRefreshLayoutWebinar = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container_webinar);

        swipeRefreshLayoutWebinar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new DownloadTask().execute(urlWebinar);
            }
        });


        recyclerViewWeb.setLayoutManager(new LinearLayoutManager(getActivity()));
        webinarAdapter = new WebinarAdapter(getActivity(), webinarList);
        recyclerViewWeb.setAdapter(webinarAdapter);
        recyclerViewWeb.setItemAnimator(new DefaultItemAnimator());

        new DownloadTask().execute(urlWebinar);

        return rootView;
    }

    public class DownloadTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            progressBarWebinar.setVisibility(View.VISIBLE);
            swipeRefreshLayoutWebinar.setRefreshing(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";

                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressBarWebinar.setVisibility(View.GONE);

            switch (result) {
                case 1:
                    webinarAdapter = new WebinarAdapter(getActivity(), webinarList);
                    recyclerViewWeb.setAdapter(webinarAdapter);
                    break;
                default:
                    Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
                    break;
            }
            swipeRefreshLayoutWebinar.setRefreshing(false);
        }

        private void parseResult(String result) {
            try {
                JSONArray posts = new JSONArray(result);
                webinarList = new ArrayList<>();

                for (int i = 0; i < posts.length(); i++) {
                    JSONObject post = posts.optJSONObject(i);

                    webinarItem = new WebinarModel();
                    webinarItem.setTitle(post.optString("title"));
                    webinarItem.setDescription(post.optString("description"));

                    webinarList.add(webinarItem);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}