package com.acumengroup.mobile;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.constants.ActionCode;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.data.DataBuffer;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.parser.GreekConfig;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.login.UserKycValidation;
import com.acumengroup.mobile.menu.MenuGetter;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.GreekDialog.Action;
import com.acumengroup.ui.GreekDialog.DialogListener;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.logger.GreekLog;
import com.acumengroup.greekmain.util.operation.GreekHashtable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class GreekBaseFragment extends Fragment implements GreekConstants, GreekUIServiceHandler {
    public StreamingController streamController;
    public OrderStreamingController orderStreamingController;
    public ServiceResponseHandler serviceResponseHandler;
    public GreekBaseFragment previousFragment;
    private FragmentActivity context;
    private FrameLayout containerView;

    public GreekBaseFragment() {
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View commonView = inflater.inflate(R.layout.fragment_commonbase, container, false);

        if (!AccountDetails.flagDynamicHeight) {
            AccountDetails.flagDynamicHeight = true;
            commonView.post(new Runnable() {
                @Override
                public void run() {

                    AccountDetails.setFragHeight(commonView.getMeasuredHeight());


                }
            });

        }
        containerView = commonView.findViewById(R.id.commonContainerView);
        streamController = new StreamingController();
        orderStreamingController = new OrderStreamingController();
        serviceResponseHandler = new ServiceResponseHandler(getMainActivity(), this);

        return commonView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = (AppCompatActivity) activity;
    }

    public void clearStreamingList() {
        ((GreekBaseActivity) getMainActivity()).clearStreamingList();
    }

    public void addToStreamingList(String type, ArrayList<String> data) {
        ((GreekBaseActivity) getMainActivity()).addToStreamingList(type, data);
    }

    public void setAppTitle(String fragment, String title) {
        ((GreekBaseActivity) getMainActivity()).setAppTitle(fragment, title);
    }

    public void hideAppTitle() {
        ((GreekBaseActivity) getMainActivity()).hideAppTitle();
    }

    public void goBackOnce() {
        ((GreekBaseActivity) getMainActivity()).goBackOnce();
    }

    public AppCompatActivity getMainActivity() {
        if (context != null) {
            return (AppCompatActivity) context;
        } else {
            return (AppCompatActivity) super.getActivity();
        }
    }

    protected View attachLayout(int layoutId) {
        View view = null;
        try {
            view = LayoutInflater.from(getMainActivity()).inflate(layoutId, null, false);
            view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            containerView.addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }


    public void navigateTo(int id, Bundle bundle, final boolean addStack) {

        InputMethodManager inputManager = (InputMethodManager) getMainActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager.isAcceptingText() && getMainActivity().getCurrentFocus() != null)
            inputManager.hideSoftInputFromWindow(getMainActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        GreekBaseFragment fragment = MenuGetter.getFragmentById(id);

        if (fragment != null) {
            fragment.previousFragment = this;

        }
        if (fragment != null && bundle != null) {
            fragment.setArguments(bundle);
        }
        addFragment(R.id.activityFrameLayout, fragment, addStack);
    }

    private void addFragment(int containerViewId, Fragment fragment, boolean addStack) {
        ((GreekBaseActivity) getMainActivity()).addFragment(containerViewId, fragment, addStack);
    }

    public void showProgress() {
        if (getMainActivity() != null) {
            if (getMainActivity().getClass().getSimpleName().equalsIgnoreCase("Userkycvalidation")) {
                ((UserKycValidation) getMainActivity()).showProgress();
            } else {
                ((GreekBaseActivity) getMainActivity()).showProgress();
            }
        }
    }

    public void hideProgress() {
        if (getMainActivity() != null) {
            if (getMainActivity().getClass().getSimpleName().equalsIgnoreCase("Userkycvalidation")) {
                ((UserKycValidation) getMainActivity()).hideProgress();
            } else {
                ((GreekBaseActivity) getMainActivity()).hideProgress();
            }
        }
    }

    public String getString(String key) {
        try {
            Hashtable label = (Hashtable) GreekConfig.getParam(getMainActivity(), GreekConfig.LABEL, GreekConfig.CONFIG);
            if (label != null && label.containsKey(key)) {
                // Read from Label Config
                // MSFLog.msg("Value from Label Config - Key = "+key+" value = "+value);
                return (String) label.get(key);
            } else {
                // Read from internal Strings.xml
                if (DataBuffer.getInstance(getMainActivity()).get(RFILE_REFLECTION_DATA) == null) {
                    Class c = Class.forName(getMainActivity().getPackageName() + ".R$string");
                    GreekHashtable rFileReflectionData = new GreekHashtable();
                    for (Field field : c.getDeclaredFields()) {
                        rFileReflectionData.put(field.getName(), field.get(field.getName()));
                    }
                    DataBuffer.getInstance(getMainActivity()).put(RFILE_REFLECTION_DATA, rFileReflectionData);
                }

                GreekHashtable rFileReflectionData = (GreekHashtable) DataBuffer.getInstance(getMainActivity()).get(RFILE_REFLECTION_DATA);
                Integer rId = (Integer) rFileReflectionData.get(key);
                String value = "";
                if (rId != null) value = getString(rId);
                if (value.equals("")) value = GREEK;
                return value;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void process(Object response) {
    }

    @Override
    public void handleResponse(Object response) {
        if (getMainActivity() != null) {
            ((GreekBaseActivity) getMainActivity()).hideMessageOnScreenManually();
        }
    }

    @Override
    public void handleError(int errorCode, String message, Object error, ServiceRequest serviceRequest) {
        hideProgress();
        GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), message, "OK", true, null);
    }

    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {
        hideProgress();
        if (action == ActionCode.ACT_CODE_OK.value) {
            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), msg, "OK", true, null);
        }
    }

    @Override
    public void handleInvalidSession(String msg, int actionCode, JSONResponse jsonResponse) {
        if (actionCode == ActionCode.ACT_CODE_SESSION_TIMEOUT.value) {
            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), msg, "OK", true, new DialogListener() {

                @Override
                public void alertDialogAction(Action action, Object... data) {
                    ((GreekBaseActivity) getMainActivity()).onSessionOut();
                }
            });
        }
    }

    @Override
    public void onDetach() {
        if (previousFragment != null) previousFragment.onFragmentResult(null);
        super.onDetach();
    }

    public void onFragmentResult(Object data) {

    }

    public void onFragmentResume() {

        Hashtable<String, ArrayList<String>> streamingSymbolList = ((GreekBaseActivity) getMainActivity()).getStreamingList();

        if (streamController != null) {
            if (streamingSymbolList.containsKey("ltpinfo")) {
                ArrayList<String> symbols = streamingSymbolList.get("ltpinfo");
                streamController.sendStreamingRequest(getMainActivity(), symbols, "ltpinfo", null, null, true);
            }
            if (streamingSymbolList.containsKey("touchline")) {
                ArrayList<String> symbols = streamingSymbolList.get("touchline");
                streamController.sendStreamingRequest(getMainActivity(), symbols, "touchline", null, null, true);
            }
            if (streamingSymbolList.containsKey("ohlc")) {
                ArrayList<String> symbols = streamingSymbolList.get("ohlc");
                streamController.sendStreamingRequest(getMainActivity(), symbols, "ohlc", null, null, true);
            }
            if (streamingSymbolList.containsKey("marketpicture")) {
                ArrayList<String> symbols = streamingSymbolList.get("marketpicture");
                streamController.sendStreamingRequest(getMainActivity(), symbols, "marketpicture", null, null, true);
            }
            if (streamingSymbolList.containsKey("index")) {
                ArrayList<String> symbols = streamingSymbolList.get("index");
                streamController.sendStreamingRequest(getMainActivity(), symbols, "index", null, null, true);
            }
        }
    }

    public void onFragmentPause() {

        Hashtable<String, ArrayList<String>> streamingSymbolList = ((GreekBaseActivity) getMainActivity()).getStreamingList();
        if (streamingSymbolList.size() > 0) {
            if (streamController != null) {
                if (streamingSymbolList.containsKey("ltpinfo")) {
                    streamController.pauseStreaming(getMainActivity(), "ltpinfo", streamingSymbolList.get("ltpinfo"));
                    streamingSymbolList.remove("ltpinfo");
                }
                if (streamingSymbolList.containsKey("touchline")) {
                    streamController.pauseStreaming(getMainActivity(), "touchline", streamingSymbolList.get("touchline"));
                    streamingSymbolList.remove("touchline");
                }
                if (streamingSymbolList.containsKey("ohlc")) {
                    streamController.pauseStreaming(getMainActivity(), "ohlc", streamingSymbolList.get("ohlc"));
                    streamingSymbolList.remove("ohlc");
                }
                if (streamingSymbolList.containsKey("marketpicture")) {
                    streamController.pauseStreaming(getMainActivity(), "marketpicture", streamingSymbolList.get("marketpicture"));
                    streamingSymbolList.remove("marketpicture");
                }
               /* if (streamingSymbolList.containsKey("index")) {
                    streamController.pauseStreaming(getMainActivity(), "index", streamingSymbolList.get("index"));
                    streamingSymbolList.remove("index");
                }*/
            }
        }
        DataBuffer.getInstance(getMainActivity()).persist();
    }

    @Override
    public void showMsgOnScreen(int actionx, String msg, JSONResponse jsonResponse) {
        hideProgress();
        try {
            RelativeLayout showmsgOnScreenLayout = getView().findViewById(R.id.showmsgLayout);
            showmsgOnScreenLayout.setVisibility(View.VISIBLE);

            GreekTextView showmsgOnScreenTxt = getView().findViewById(R.id.errorHeader);
            showmsgOnScreenTxt.setVisibility(View.VISIBLE);
            showmsgOnScreenTxt.setText(Html.fromHtml(msg));
        } catch (Exception e) {
            GreekLog.error("Show Message on Screen - Not Handled");
            GreekDialog.alertDialogOnly(getMainActivity(), GreekBaseActivity.GREEK, msg, getResources().getString(R.string.GREEK_OK));
            e.printStackTrace();
        }
    }

    @Override
    public void infoDialogOK(int action, String message, final JSONResponse jsonResponse) {
        hideProgress();
        if (action == ActionCode.ACT_CODE_OK_DIALOG.value) {

            GreekDialog.alertDialog(getMainActivity(), 0, getString(GREEK), message, "OK", true, new DialogListener() {
                @Override
                public void alertDialogAction(Action action, Object... data) {
                    if (jsonResponse.getResponseData().length() != 0) handleResponse(jsonResponse);
                }
            });
        }
    }

    public ArrayList<String> getStreamingSymbolList(String type) {
        Hashtable<String, ArrayList<String>> streamingSymbolList = ((GreekBaseActivity) getMainActivity()).getStreamingList();
        if (streamingSymbolList != null) {
            return streamingSymbolList.get(type);
        }
        return null;
    }


    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}