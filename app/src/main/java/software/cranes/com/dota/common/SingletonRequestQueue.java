package software.cranes.com.dota.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import software.cranes.com.dota.interfa.Constant;

import static android.R.attr.tag;

/**
 * Created by GiangNT - PC on 23/09/2016.
 */

public class SingletonRequestQueue {
    private static SingletonRequestQueue mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Context mContext;
    private DefaultRetryPolicy mDefaultRetryPolicy;

    private SingletonRequestQueue(Context mContext) {
        this.mContext = mContext;
        mRequestQueue = getRequestQueue();
        mDefaultRetryPolicy = new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static synchronized SingletonRequestQueue getInstance(Context mContext) {
        if (mInstance == null) {
            mInstance = new SingletonRequestQueue(mContext);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        request.setRetryPolicy(mDefaultRetryPolicy);
        request.setTag(Constant.TAG);
        getRequestQueue().add(request);
    }
    public <T> void addToRequestImageQueue(Request<T> request, String tag) {
        request.setRetryPolicy(mDefaultRetryPolicy);
        request.setTag(tag);
        getRequestQueue().add(request);
    }
    public void removeRequestImage(String tag) {
        getRequestQueue().cancelAll(tag);
    }
    public ImageLoader getmImageLoader() {
        return mImageLoader;
    }

    public void destroy() {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(Constant.TAG);
        }
        SingletonRequestQueue mInstance = null;
        RequestQueue mRequestQueue = null;
        ImageLoader mImageLoader = null;
        Context mContext = null;
        DefaultRetryPolicy mDefaultRetryPolicy = null;
    }

    public void cancelAll() {
        getRequestQueue().cancelAll(Constant.TAG);
    }
}
