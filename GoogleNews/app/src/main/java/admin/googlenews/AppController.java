package admin.googlenews;

import android.app.Application;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


/*
   Created by Harshit on 5/13/2016.
 */
public class AppController extends Application {

    private static final String TAG = AppController.class.getSimpleName();
    private static final int NO_MAX_RETRIES = 0;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static AppController mInstance;

    //We will store the user data in sharedpreferences
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;


    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }



    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        req.setShouldCache(false);
        req.setRetryPolicy(new DefaultRetryPolicy(
                5*60*1000,
                NO_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        req.setRetryPolicy(new DefaultRetryPolicy(
                2*60*1000,
                NO_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        req.setShouldCache(false);
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }



    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


}
