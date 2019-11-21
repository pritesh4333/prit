package in.co.vyapari.ui.listener;

import android.view.View;

/**
 * Created by bekir on 06/02/2017.
 */

public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}