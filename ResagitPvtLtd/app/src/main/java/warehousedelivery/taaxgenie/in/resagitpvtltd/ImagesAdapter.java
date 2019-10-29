package warehousedelivery.taaxgenie.in.resagitpvtltd;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImagesAdapter extends BaseAdapter
{
    private Context mContext;
    String[] imgaraydata;


    public ImagesAdapter(Context context, String[] imgaraydata)
    {
        mContext = context;
        this.imgaraydata=imgaraydata;
    }

    public int getCount() {
        return imgaraydata.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    // Override this method according to your need
    public View getView(int index, View view, ViewGroup viewGroup)
    {
        // TODO Auto-generated method stub

        ImageView i = new ImageView(mContext);
        i.setImageBitmap(StringToBitMap(imgaraydata[index]));
        i.setLayoutParams(new Gallery.LayoutParams(200, 200));

        i.setScaleType(ImageView.ScaleType.FIT_XY);

        return i;
    }
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }




}
