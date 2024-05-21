// ConnectionAsyncTask.java
package com.example.pizza_restaurant_application;

import android.os.AsyncTask;
import java.util.List;

public class ConnectionAsyncTask extends AsyncTask<String, String, String> {

    MainActivity activity;

    public ConnectionAsyncTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        activity.setButtonText("connecting");
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        return HttpManager.getData(params[0]);
    }

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
