package com.markupartist.iglaset.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageUtilities {
    private static String TAG = "ImageUtilities";
    private static final HashMap<Integer, SoftReference<Bitmap>> sImageCache =
        new HashMap<Integer, SoftReference<Bitmap>>();

    public static Bitmap load(int id, String imageUrl) {
        Bitmap bitmap = null;
        Log.d(TAG, "Loading image: " + imageUrl);
        try {
            if (sImageCache.containsKey(id)) {
                bitmap = loadFromCache(id);
            } else {
                URL endpoint = new URL("http://api.iglaset.se/resizely/crop/50x50/?url=" + imageUrl);
                InputStream in = endpoint.openStream();

                bitmap = BitmapFactory.decodeStream(in);

                storeInCache(id, bitmap);
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap loadFromCache(int id) {
        Log.d(TAG, "Loading " + id + " from the cache");
        SoftReference<Bitmap> sf = sImageCache.get(id);
        return sf.get();
    }

    public static void storeInCache(int id, Bitmap bitmap) {
        Log.d(TAG, "Storing " + id + " in the cache");
        sImageCache.put(id, new SoftReference<Bitmap>(bitmap));
    }
}