package warehousedelivery.taaxgenie.in.resagitpvtltd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;

import com.github.chrisbanes.photoview.PhotoView;

public class Imagelist extends AppCompatActivity {
    PhotoView selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagelist);
        Gallery gallery = (Gallery) findViewById(R.id.gallery);
        selectedImage = (PhotoView) findViewById(R.id.imageView);

        gallery.setSpacing(1);
        final ImagesAdapter galleryImageAdapter= new ImagesAdapter(Imagelist.this,  MainActivity.imgaraydata);
        gallery.setAdapter(galleryImageAdapter);


        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // show the selected Image
                selectedImage.setImageBitmap(StringToBitMap(MainActivity.imgaraydata[position]));
            }
        });


    }
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
