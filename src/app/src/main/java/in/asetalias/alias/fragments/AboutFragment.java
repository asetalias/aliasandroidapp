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
import in.asetalias.alias.adapters.AboutAdapter;
import in.asetalias.alias.adapters.WebinarAdapter;
import in.asetalias.alias.dataModels.DataHomeModel;
import in.asetalias.alias.dataModels.WebinarModel;

import static in.asetalias.alias.data.DataConstants.urlDataHome;
import static in.asetalias.alias.data.DataConstants.urlWebinar;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    private static final String TAG = "AboutFragment";

    private List<DataHomeModel> dataHomeList;
    private RecyclerView recyclerViewWeb;
    private AboutAdapter aboutAdapter;
    private ProgressBar progressBarAbout;
    private DataHomeModel dataHomeItem;

    private SwipeRefreshLayout swipeRefreshLayoutAbout;

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        recyclerViewWeb = (RecyclerView) rootView.findViewById(R.id.recycler_view_about);
        progressBarAbout = (ProgressBar) rootView.findViewById(R.id.progress_bar_about);
        swipeRefreshLayoutAbout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container_about);

        swipeRefreshLayoutAbout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new DownloadTask().execute(urlDataHome);
            }
        });


        recyclerViewWeb.setLayoutManager(new LinearLayoutManager(getActivity()));
        aboutAdapter = new AboutAdapter(getActivity(), dataHomeList);
        recyclerViewWeb.setAdapter(aboutAdapter);
        recyclerViewWeb.setItemAnimator(new DefaultItemAnimator());

        new DownloadTask().execute(urlDataHome);

        return rootView;

    }

    public class DownloadTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            progressBarAbout.setVisibility(View.VISIBLE);
            swipeRefreshLayoutAbout.setRefreshing(true);
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
            progressBarAbout.setVisibility(View.GONE);

            switch (result) {
                case 1:
                    aboutAdapter = new AboutAdapter(getActivity(), dataHomeList);
                    recyclerViewWeb.setAdapter(aboutAdapter);
                    break;
                default:
                    Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
                    break;
            }
            swipeRefreshLayoutAbout.setRefreshing(false);
        }

        private void parseResult(String result) {
            try {
                JSONArray posts = new JSONArray(result);
                dataHomeList = new ArrayList<>();

                for (int i = 0; i < posts.length(); i++) {
                    JSONObject post = posts.optJSONObject(i);
                    dataHomeItem = new DataHomeModel();
                    dataHomeItem.setSite_history(post.optString("site_history"));
                    dataHomeList.add(dataHomeItem);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
