package in.co.vyaparienterprise.ui.generic;

import android.content.Context;

import com.squareup.picasso.Picasso;

import in.co.vyaparienterprise.middleware.ServiceCreator;

/**
 * Created by bekirdursun on 2.01.2018.
 */

public class CustomPicasso {

    private static Picasso sPicasso;

    private CustomPicasso() {
    }

    public static Picasso getImageLoader(final Context context) {
        if (sPicasso == null) {
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(ServiceCreator.getOkClient()));
            sPicasso = builder.build();
        }
        return sPicasso;
    }
}