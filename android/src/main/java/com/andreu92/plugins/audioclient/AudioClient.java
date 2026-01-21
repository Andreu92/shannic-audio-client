package com.andreu92.plugins.audioclient;

import static android.content.ContentValues.TAG;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.graphics.Bitmap;
import androidx.palette.graphics.Palette;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.JSObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

                JSONArray thumbnails = audio.getJSONArray("thumbnails");
                JSONObject thumbnail = thumbnails.getJSONObject(thumbnails.length() - 1);

                String src = thumbnail.getString("url");

                JSONObject colors = getColors(src);

                audio.put("colors", colors);

                JSObject response = JSObject.fromJSONObject(audio);
                call.resolve(response);
            } catch (Exception e) {
                call.reject("Error extracting audio URL");
            }
        });
    }

    private JSONObject getColors(String src) throws IOException, JSONException {
        URL url = new URL(src);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setDoInput(true);
        connection.connect();
        InputStream stream = connection.getInputStream();
        Bitmap bitmap = BitmapFactory.decodeStream(stream);

        Palette palette = Palette.from(bitmap).generate();

        Palette.Swatch swatch =
            palette.getVibrantSwatch() != null ? palette.getVibrantSwatch() :
            palette.getLightVibrantSwatch() != null ? palette.getLightVibrantSwatch() :
            palette.getDarkVibrantSwatch() != null ? palette.getDarkVibrantSwatch() :
            palette.getMutedSwatch() != null ? palette.getMutedSwatch() :
            palette.getLightMutedSwatch() != null ? palette.getLightMutedSwatch() :
            palette.getDarkMutedSwatch();

        JSONObject colors = new JSONObject();

        if (swatch == null) return null;

        colors.put("background", String.format("#%06X", (0xFFFFFF & swatch.getRgb())));
        colors.put("title", String.format("#%06X", (0xFFFFFF & swatch.getTitleTextColor())));
        colors.put("body", String.format("#%06X", (0xFFFFFF & swatch.getBodyTextColor())));

        return colors;
    }
}
