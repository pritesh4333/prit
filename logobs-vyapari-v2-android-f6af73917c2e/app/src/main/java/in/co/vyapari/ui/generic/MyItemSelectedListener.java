package in.co.vyapari.ui.generic;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Bekir.Dursun on 4.10.2017.
 */

public abstract class MyItemSelectedListener implements AdapterView.OnItemSelectedListener {
    public abstract void onItemSelected(int position);

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        onItemSelected(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
