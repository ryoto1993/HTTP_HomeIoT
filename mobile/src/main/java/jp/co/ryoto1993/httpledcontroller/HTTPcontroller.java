package jp.co.ryoto1993.httpledcontroller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPcontroller extends AsyncTask<String, Void, String> {

    private Listener listener;

    /**
     *
     * @param params: [0] URL, [1] Method [2] POST data
     * @return
     */
    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        String data = params[2];
        String result = null;

        try {
            // Make URL
            URL url = new URL(params[0]);

            // Generate HttpURLConnection object
            connection = (HttpURLConnection)url.openConnection();

            // Set request method
            connection.setRequestMethod(params[1]);

            // Disable auto redirect mode
            connection.setInstanceFollowRedirects(false);

            // Enable get response data
            connection.setDoInput(true);

            // Enable post data with request
            connection.setDoOutput(true);

            // Establishing connection
            connection.connect();

            // Send POST data
            try (OutputStream out = connection.getOutputStream()) {
                out.write(data.getBytes("UTF-8"));
                out.flush();
                Log.d("debug", "flush");
            } catch (IOException e) {
                e.printStackTrace();
                result = "Failed to send POST request.";
            }

            // Get response code
            final int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK)
                result = "200: HTTP_OK";
            else
                result = String.valueOf(result) + "Failed.";


        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    // After finishing asynchronous process, return result
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (listener != null) {
            listener.onSuccess(result);
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    interface Listener {
        void onSuccess(String result);
    }
}
