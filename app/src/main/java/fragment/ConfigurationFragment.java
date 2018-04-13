package fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import learn.shafi.mqttclient.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigurationFragment extends Fragment {

    WebView espWV;

    public ConfigurationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_configuration, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        espWV =  view.findViewById(R.id.espWV);
        espWV.getSettings().setJavaScriptEnabled(true);
        espWV.setWebViewClient(new WebViewClient());
        espWV.loadUrl("http://192.168.4.1");
    }
}
