package com.dawidkudzia.pricelessbitcoin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";

    TextView mPriceTextView;

    /**
     * Creates views, listeners, array adapter using the string array and a spinner layout
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = findViewById(R.id.priceLabel);
        Spinner spinner = findViewById(R.id.currency_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.d("Bitcoin", "" + parent.getItemAtPosition(position));
                String newURL = BASE_URL + parent.getItemAtPosition(position);
                Log.d("Bitcoin", "New URL: " + newURL);

                letsDoSomeNetworking(newURL);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Log.d("Bitcoin", "Nothing selected");

            }
        });
    }

    /**
     * Connects with bitcoin url and updates bitcoin price for a chosen currency
     * @param url website link to bitcoin price for a chosen currency
     */
    private void letsDoSomeNetworking(String url) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Bitcoin", "JSON: " + response.toString());
                try {
                    mPriceTextView.setText(response.getString("last"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }


}
