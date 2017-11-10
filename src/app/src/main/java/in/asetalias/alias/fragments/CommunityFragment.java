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
import in.asetalias.alias.adapters.CommunityAdapter;
import in.asetalias.alias.dataModels.CommunityModel;

import static in.asetalias.alias.data.LoadJson.urlCommunity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityFragment extends Fragment {

    private static final String TAG = "WebinarFragment";

    private List<CommunityModel> communityList;
    private RecyclerView recyclerViewCommunity;
    private CommunityAdapter communityAdapter;
    private ProgressBar progressBarCommunity;
    private CommunityModel communityItem;

    private SwipeRefreshLayout swipeRefreshLayoutCommunity;


    public CommunityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_community, container, false);

        recyclerViewCommunity = (RecyclerView) rootView.findViewById(R.id.recycler_view_community);
        progressBarCommunity = (ProgressBar) rootView.findViewById(R.id.progress_bar_community);
        swipeRefreshLayoutCommunity = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container_community);

        swipeRefreshLayoutCommunity.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new DownloadTask().execute(urlCommunity);
            }
        });


        recyclerViewCommunity.setLayoutManager(new LinearLayoutManager(getActivity()));
        communityAdapter = new CommunityAdapter(getActivity(), communityList);
        recyclerViewCommunity.setAdapter(communityAdapter);
        recyclerViewCommunity.setItemAnimator(new DefaultItemAnimator());

        new DownloadTask().execute(urlCommunity);


        return rootView;
    }


    private class DownloadTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            progressBarCommunity.setVisibility(View.VISIBLE);
            swipeRefreshLayoutCommunity.setRefreshing(true);
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
            progressBarCommunity.setVisibility(View.GONE);

            switch (result) {
                case 1:
                    communityAdapter = new CommunityAdapter(getActivity(), communityList);
                    recyclerViewCommunity.setAdapter(communityAdapter);
                    break;
                default:
                    Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
                    break;
            }
            swipeRefreshLayoutCommunity.setRefreshing(false);
        }

        private void parseResult(String result) {
            try {
                JSONArray posts = new JSONArray(result);
                communityList = new ArrayList<>();

                for (int i = 0; i < posts.length(); i++) {
                    //this method simple returns null if no value is found, so better to use this one rather than getJSONObject
                    JSONObject post = posts.optJSONObject(i);

                    communityItem = new CommunityModel();
                    communityItem.setTitle(post.optString("title"));
                    communityItem.setDesc(post.optString("desc"));

                    communityList.add(communityItem);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}