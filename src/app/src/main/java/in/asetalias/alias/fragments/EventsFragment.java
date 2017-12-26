package in.asetalias.alias.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import in.asetalias.alias.adapters.EventAdapter;
import in.asetalias.alias.dataModels.EventModel;

import static in.asetalias.alias.data.DataConstants.urlEvent;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {

    private static final String TAG = "BlankFragment";

    private List<EventModel> eventsList;
    private RecyclerView recyclerViewEvent;
    private EventAdapter eventAdapter;
    private ProgressBar progressBarEvent;

    private FloatingActionButton fabPlus;

    private EventModel eventItem;

    private SwipeRefreshLayout swipeRefreshLayoutEvent;

    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);


        recyclerViewEvent = (RecyclerView) rootView.findViewById(R.id.recycler_view_event);
        progressBarEvent = (ProgressBar) rootView.findViewById(R.id.progress_bar_event);
        fabPlus = (FloatingActionButton) rootView.findViewById(R.id.fab_plus);


//        swipe down to refresh the data
        swipeRefreshLayoutEvent = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container_event);

        swipeRefreshLayoutEvent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new DownloadTask().execute(urlEvent);
            }
        });

        //                        Button to propose a talk/event.
        fabPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                intent to open the google form in any of the installd web browsers
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(eventItem.getCta())));
            }
        });


        recyclerViewEvent.setLayoutManager(new LinearLayoutManager(getActivity()));
        eventAdapter = new EventAdapter(getActivity(), eventsList);
        recyclerViewEvent.setAdapter(eventAdapter);
        recyclerViewEvent.setItemAnimator(new DefaultItemAnimator());

        new DownloadTask().execute(urlEvent);

        return rootView;
    }


    private class DownloadTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
//            progressBarEvent.setVisibility(View.VISIBLE);
            progressBarEvent.setVisibility(View.GONE);
            swipeRefreshLayoutEvent.setRefreshing(true);
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
            progressBarEvent.setVisibility(View.GONE);

            switch (result) {
                case 1:
                    eventAdapter = new EventAdapter(getActivity(), eventsList);
                    recyclerViewEvent.setAdapter(eventAdapter);
                    break;
                default:
                    Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
                    break;
            }
            swipeRefreshLayoutEvent.setRefreshing(false);


        }

        private void parseResult(String result) {
            try {
                JSONArray posts = new JSONArray(result);
                eventsList = new ArrayList<>();

                for (int i = 0; i < posts.length(); i++) {
                    JSONObject post = posts.optJSONObject(i);

                    eventItem = new EventModel();
                    eventItem.setTitle(post.optString("title"));
                    eventItem.setPoster(post.optString("poster"));
                    eventItem.setDescription(post.optString("description"));
                    eventItem.setCta(post.optString("cta"));

                    eventsList.add(eventItem);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}