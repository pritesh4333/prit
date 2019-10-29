package in.co.vyapari.ui.listener;

/**
 * Created by bekir on 01/11/2017.
 */

public interface FirmClickListener {
    void onItemClick(int position);

    void onItemLongClick(int position);

    void doPhoneCall(int position);
}