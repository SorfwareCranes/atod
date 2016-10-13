package software.cranes.com.dota.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.lang.ref.WeakReference;


/**
 * Created by GiangNT - PC on 13/10/2016.
 */

public class ImageRequestCustom implements Response.Listener<Bitmap>, Response.ErrorListener {

    private Context context;
    private String url;
    private int reqWidth, reqHeight, resId;
    private final WeakReference<ImageView> weakReference;
    private Bitmap bitmapRes;
    public ImageRequestCustom(Context context, ImageView imageView, String url, int reqWidth, int reqHeight, int resId, Bitmap bitmapRes) {
        this.context = context;
        this.url = url;
        this.reqWidth = reqWidth;
        this.reqHeight = reqHeight;
        this.resId = resId;
        this.weakReference = new WeakReference<ImageView>(imageView);
        this.bitmapRes = bitmapRes;
    }

    /**
     * Called when a response is received.
     */
    @Override
    public void onResponse(Bitmap response) {
        if (weakReference != null && response != null) {
            ImageView imageView = weakReference.get();
            ImageRequestCustom imageRequestCustom = getImageRequestCustom(imageView);
            if (this == imageRequestCustom && imageView != null) {
                imageView.setImageBitmap(response);
            }
        }
    }

    /**
     * Callback method that an error has been occurred with the provided error code and optional
     * user-readable message.
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        if (weakReference != null) {
            ImageView imageView = weakReference.get();
            if (imageView != null) {
                imageView.setImageResource(resId);
            }
        }
    }

    private ImageRequestCustom getImageRequestCustom(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getImageRequestCustom();
            }
        }
        return null;
    }

    public void execute(ImageView imageView) {
        if (cancelRequest(imageView)) {
            AsyncDrawable asyncDrawable = new AsyncDrawable(context.getResources(), bitmapRes, this);
            imageView.setImageDrawable(asyncDrawable);
            SingletonRequestQueue.getInstance(context).addToRequestImageQueue(new ImageRequest(url, this, reqWidth, reqHeight, ImageView.ScaleType.CENTER_INSIDE, null, this), url);
        }
    }

    private boolean cancelRequest(ImageView imageView) {
        ImageRequestCustom imageRequestCustom = getImageRequestCustom(imageView);
        if (imageRequestCustom != null) {
            String urlData = imageRequestCustom.url;
            if (urlData == null || urlData != this.url) {
                SingletonRequestQueue.getInstance(context).removeRequestImage(urlData);
            } else {
                return false;
            }
        }
        return true;
    }

    class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<ImageRequestCustom> imageRequestCustomWeakReference;

        public AsyncDrawable(Resources resources, Bitmap bitmap, ImageRequestCustom imageRequestCustom) {
            super(resources, bitmap);
            imageRequestCustomWeakReference = new WeakReference<ImageRequestCustom>(imageRequestCustom);
        }

        public ImageRequestCustom getImageRequestCustom() {
            return imageRequestCustomWeakReference.get();
        }
    }
}
