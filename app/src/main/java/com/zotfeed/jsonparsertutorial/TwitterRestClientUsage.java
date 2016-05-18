package com.zotfeed.jsonparsertutorial;

/**
 * Created by ali on 5/18/16.
 */
import org.json.*;

import com.loopj.android.http.*;
import android.preference.PreferenceActivity.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class TwitterRestClientUsage {
    public void getPublicTimeline() throws JSONException {
        TwitterRestClient.get("statuses/public_timeline.json", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray

                // Construct the JSONArray here

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) throws JSONException {
                // Pull out the first event on the public timeline
                JSONObject firstEvent = (JSONObject) timeline.get(0);
                String tweetText = firstEvent.getString("text");

                // Do something with the response
                System.out.println(tweetText);
            }
        });
    }
}
