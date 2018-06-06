package jp.co.ryoto1993.httpledcontroller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.cengalabs.flatui.FlatUI;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements
        ModeSelectorFragment.OnFragmentInteractionListener,
        ColorSelectorFragment.OnFragmentInteractionListener {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selected_fragment = null;
            TransitionSet ts = new TransitionSet();
            ts.addTransition(new Fade());

            switch (item.getItemId()) {
                case R.id.navigation_mode:
                    selected_fragment = ModeSelectorFragment.newInstance();
                    break;
                case R.id.navigation_color:
                    selected_fragment = ColorSelectorFragment.newInstance();
                    break;
                case R.id.navigation_notifications:

                    break;
            }
            if (selected_fragment != null)
                selected_fragment.setEnterTransition(ts);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.navigation_frame, selected_fragment);
            transaction.commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting navigation listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Setting custom toolbar as a SupportActionBar, to do this, inflating menu
        // will be called as onCreateOptionsMenu of AppCompatActivity
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        // Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.navigation_frame, ModeSelectorFragment.newInstance());
        transaction.commit();

        // Converts the default values (radius, size, border) to dp to be compatible with different
        // screen sizes. If you skip this there may be problem with different screen densities
        FlatUI.initDefaultValues(this);

        // Setting default theme to avoid to add the attribute "theme" to XML
        // and to be able to change the whole theme at once
        FlatUI.setDefaultTheme(FlatUI.BLOOD);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_setting:
                Intent intent = new Intent(getApplication(), SettingsActivity.class);
                startActivity(intent);
                break;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public void onModeSelectorFragmentInteraction(Uri uri) {

    }

    @Override
    public void onColorSelectorFragmentInteraction(Uri uri) {

    }

    public void sendTrigger(View view) {
        String mode = null;
        String data = null;
        JSONObject json = null;

        switch (view.getId()) {
            case R.id.btn_on:
                mode = "on";
                break;
            case R.id.btn_off:
                mode = "off";
                break;
        }

        json = new JSONObject();
        try {
            json.put("mode", mode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        send("POST", json.toString());
    }

    private void send(String method, String json) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String url = "http://"
                + sp.getString(getString(R.string.network_lan_ip), "localhost")
                + ":8080";

        HTTPcontroller http = new HTTPcontroller();
        http.setListener(new HTTPcontroller.Listener() {
            @Override
            public void onSuccess(String result) {

            }
        });
        http.execute(url, method, json);
    }
}
