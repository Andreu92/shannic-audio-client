package com.example.plugin;

import android.os.Bundle;
import com.getcapacitor.BridgeActivity;
import com.andreu92.plugins.audioclient.AudioClient;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerPlugin(AudioClient.class);
    }
}