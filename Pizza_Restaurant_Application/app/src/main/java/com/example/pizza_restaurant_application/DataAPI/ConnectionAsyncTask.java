package com.example.pizza_restaurant_application.DataAPI;

import android.os.AsyncTask;

import java.util.List;

public class ConnectionAsyncTask extends AsyncTask<String, String, String> {

    MainActivity activity;

    // Constructor to initialize the activity instance
    public ConnectionAsyncTask(MainActivity activity) {
        this.activity = activity;
    }

    // Runs on the UI thread before doInBackground
    @Override
    protected void onPreExecute() {
        activity.setButtonText("connecting");
        super.onPreExecute();
    }

    // Background task to fetch data from the provided URL
    @Override
    protected String doInBackground(String... params) {
        return HttpManager.getData(params[0]);
    }

    // Runs on the UI thread after doInBackground completes
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s != null) {
            activity.setButtonText("connected");
            List<Pizza> pizza = PizzaJsonParser.parsePizzaData(s);
            activity.fillPizzas(pizza);
            activity.goToLoginOrSignUp();
        } else {
            activity.showErrorToast();
        }
    }
}
