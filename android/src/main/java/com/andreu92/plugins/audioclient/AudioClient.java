package com.andreu92.plugins.audioclient;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.JSObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@CapacitorPlugin(name = "AudioClient")
public class AudioClient extends Plugin {
    private ExecutorService executorService;
    private PyObject pythonModule;
    private boolean pythonInitialized = false;

    @Override
    public void load() {
        super.load();
        executorService = Executors.newCachedThreadPool();
        initializePython();
    }

    private void initializePython() {
        if (!pythonInitialized) {
            try {
                if (!Python.isStarted()) {
                    Python.start(new AndroidPlatform(getContext()));
                    Python python = Python.getInstance();
                    pythonModule = python.getModule("main");
                }
                pythonInitialized = true;
            } catch (Exception e) {
                Log.e(TAG, "Failed to initialize Python", e);
            }
        }
    }

    @PluginMethod
    public void search(final PluginCall call) {
        final String query = call.getString("query");
        final String nextToken = call.getString("next_token");

        if (query == null || query.isEmpty()) {
            call.reject("Missing query");
            return;
        }

        if (!pythonInitialized) {
            call.reject("Python not initialized");
            return;
        }

        executorService.execute(() -> {
            try {
                PyObject result = pythonModule.callAttr("search", query, nextToken);
                JSONObject search = new JSONObject(result.toString());
                JSObject response = JSObject.fromJSONObject(search);
                call.resolve(response);
            } catch (Exception e) {
                call.reject("Error searching");
            }
        });

    }

    @PluginMethod
    public void get(final PluginCall call) {
        String id = call.getString("id");

        if (id == null || id.isEmpty()) {
            call.reject("ID is required");
            return;
        }

        if (!pythonInitialized) {
            call.reject("Python not initialized");
            return;
        }

        executorService.execute(() -> {
            try {
                PyObject result = pythonModule.callAttr("get", id);
                JSONObject audio = new JSONObject(result.toString());
                JSObject response = JSObject.fromJSONObject(audio);
                call.resolve(response);
            } catch (Exception e) {
                call.reject("Error extracting audio URL");
            }
        });
    }
}
