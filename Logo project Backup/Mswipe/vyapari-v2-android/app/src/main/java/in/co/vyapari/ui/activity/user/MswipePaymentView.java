package in.co.vyapari.ui.activity.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mswipetech.wisepad.sdk.MSWisepadController;
import com.mswipetech.wisepad.sdk.data.EMITransactionDetailsResponseData;
import com.mswipetech.wisepad.sdk.data.MSDataStore;
import com.mswipetech.wisepad.sdk.listeners.MSWisepadControllerResponseListener;


import in.co.vyapari.R;
import in.co.vyapari.VyapariApp;


public class MswipePaymentView extends Activity
{
	//fields for card sale amount details screen,
	EditText mTxtUserName = null;
	EditText mTxtPassword = null;
	EditText mTxtCreditAmount = null;
	EditText mTxtPhoneNum = null;
	EditText mTxtReceipt = null;
	EditText mTxtNotes = null;
	EditText mTxtFirstSixDigits = null;
	LinearLayout mLINFirstSixDigits = null;


	VyapariApp applicationData = null;

	boolean isSaleWithCash = false;
	boolean isPreAuth = false;
	boolean isEmiSale = false;

	private String mTotalAmount = "";
	//GsonRequest gsonRequest;

	ProgressDialog mProgressDialog = null;


	private String mEmiPeriod;
	private String mEmiBankCode;
	private String mEmiRate;
	private String mEmiAmount;

    public static final int MSWIPE_CARDSALE_ACTIVITY_REQUEST_CODE = 1003;
    public static final int MSWIPE_CASHATPOS_ACTIVITY_REQUEST_CODE = 1004;
    public static final int MSWIPE_EMISALE_ACTIVITY_REQUEST_CODE = 1005;
    public static final int MSWIPE_PREAUTH_ACTIVITY_REQUEST_CODE = 1006;
	public static final int MSWIPE_CARDSALE_SIGNATURE_REQUEST = 1007;

	public static final int MSWIPE_CARDSALE_DECLINE_REQUEST = 1011;

	/**
	 * Called when the activity is first created.
	 * @param savedInstanceState
	 */

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mswipe_payment_view);

		applicationData = new VyapariApp();
		initViews();
	}


	/**
	 *@description
	 *      All fields intilising here.
	 *
	 */
	private void initViews()
	{

		isSaleWithCash = getIntent().getBooleanExtra("salewithcash", false);
		isPreAuth = getIntent().getBooleanExtra("preauthsale", false);
		isEmiSale = getIntent().getBooleanExtra("emisale", false);

		mTxtUserName = (EditText) findViewById(R.id.payment_TXT_username);
		mTxtPassword = (EditText) findViewById(R.id.payment_TXT_password);
		mTxtCreditAmount = (EditText) findViewById(R.id.payment_TXT_amount);
		mTxtPhoneNum = (EditText) findViewById(R.id.payment_TXT_mobileno);
		mTxtReceipt = (EditText) findViewById(R.id.payment_TXT_receipt);
		mTxtNotes = (EditText) findViewById(R.id.payment_TXT_notes);
		mLINFirstSixDigits = (LinearLayout) findViewById(R.id.payment_LNR_firstsidigits);
		mTxtFirstSixDigits = (EditText) findViewById(R.id.payment_TXT_firstsidigits);
		mTxtUserName.requestFocus();


		TextView txtHeading = ((TextView) findViewById(R.id.topbar_LBL_heading));

		if(isPreAuth){
			txtHeading.setText(getResources().getString(R.string.preauth));
		}
		else if (isSaleWithCash){
			txtHeading.setText(getResources().getString(R.string.cash_at_pos));

		}else if (isEmiSale){
//			txtHeading.setText(getResources().getString(R.string.emi));
//			mLINFirstSixDigits.setVisibility(View.VISIBLE);
		}
		else {
			txtHeading.setText(getResources().getString(R.string.card_sale));
		}

		//The screen are for the amount

		Button btnAmtNext = (Button) findViewById(R.id.payment_BTN_amt_next);
		btnAmtNext.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if(imm != null)
					imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

				double amount = 0;
				try
				{
					if (mTxtCreditAmount.length() > 0)
						amount = Double.parseDouble(removeChar(mTxtCreditAmount.getText().toString(),','));
				}
				catch (Exception ex) {
					amount = 0;
				}

				if (mTxtUserName.getText().toString().trim().startsWith("0")) {

					showDialog("the userid cannot start with 0.");
					mTxtUserName.requestFocus();
					return;
				}
				else if(mTxtUserName.getText().toString().length() < 10)
				{
					showDialog("enter valid userid");
					mTxtUserName.requestFocus();
					return;
				}
				else if(mTxtPassword.getText().toString().length() == 0)
				{
					showDialog("enter a valid password");
					mTxtPassword.requestFocus();
					return;
				}
				else if (amount < 1)
				{
					showDialog("invalid amount! minimum amount should be inr 1.00 to proceed.");
					return;
				}
				else if(isEmiSale && mTxtFirstSixDigits.getText().toString().length() == 0){
						showDialog("enter a valid first six digits");
						mTxtFirstSixDigits.requestFocus();
						return;
				}

				else if(isEmiSale && mTxtFirstSixDigits.getText().toString().length() < 6) {
					showDialog("enter a valid first six digits");
					mTxtFirstSixDigits.requestFocus();
					return;
				}
				else if (mTxtPhoneNum.getText().toString().trim().length() != 10) {

					showDialog("required length of the mobile number is 10 digits.");
					mTxtPhoneNum.requestFocus();
					return;
				}
				else if (mTxtPhoneNum.getText().toString().trim().startsWith("0")) {

					showDialog("the mobile number cannot start with 0.");
					mTxtPhoneNum.requestFocus();
					return;

				}

				processCardSale();

			}
		});

	}

	private void processCardSale() {

		double amt = Double.parseDouble(mTxtCreditAmount.getText().toString().trim());
		String totalAmt = String.format("%.2f", amt);



//        if (isPreAuth) {
//
//			if (ApplicationData.IS_DEBUGGING_ON)
//				Logs.v(ApplicationData.packName, "isPreAuth" + isPreAuth, true, true);
//
//            Intent intent_preauth = new Intent(MswipePaymentView.this, MswipePreauthSaleActivity.class);
//
//            intent_preauth.putExtra("preauthsale", true);
//
//			intent_preauth.putExtra("username", mTxtUserName.getText().toString().trim());
//			intent_preauth.putExtra("password", mTxtPassword.getText().toString().trim());
//			intent_preauth.putExtra("amount", totalAmt);
//			intent_preauth.putExtra("mobileno", mTxtPhoneNum.getText().toString().trim());
//			intent_preauth.putExtra("receiptno", mTxtReceipt.getText().toString().trim());
//			intent_preauth.putExtra("notes", mTxtNotes.getText().toString().trim());
//
//			if (AppSharedPrefrences.getAppSharedPrefrencesInstace().getGatewayEnvironment() == MSWisepadController.GATEWAY_ENVIRONMENT.LABS)
//
//				intent_preauth.putExtra("production", false);
//			else
//				intent_preauth.putExtra("production", true);
//
//			//startActivity(intent_preauth);
//            startActivityForResult(intent_preauth, MSWIPE_PREAUTH_ACTIVITY_REQUEST_CODE);
//			//finish();
//
//
//        } else if (isSaleWithCash) {
//
//            if (ApplicationData.IS_DEBUGGING_ON)
//                Logs.v(ApplicationData.packName, "isSaleWithCash" + isSaleWithCash, true, true);
//
//           	Intent intent_cashAtpos = new Intent(MswipePaymentView.this, MswipeCahAtPosSaleActivity.class);
//           	intent_cashAtpos.putExtra("salewithcash", true);
//
//			intent_cashAtpos.putExtra("username", mTxtUserName.getText().toString().trim());
//			intent_cashAtpos.putExtra("password", mTxtPassword.getText().toString().trim());
//			intent_cashAtpos.putExtra("amount", totalAmt);
//			intent_cashAtpos.putExtra("mobileno", mTxtPhoneNum.getText().toString().trim());
//			intent_cashAtpos.putExtra("receiptno", mTxtReceipt.getText().toString().trim());
//			intent_cashAtpos.putExtra("notes", mTxtNotes.getText().toString().trim());
//
//			if (AppSharedPrefrences.getAppSharedPrefrencesInstace().getGatewayEnvironment() == MSWisepadController.GATEWAY_ENVIRONMENT.LABS)
//
//				intent_cashAtpos.putExtra("production", false);
//			else
//				intent_cashAtpos.putExtra("production", true);
//
//			//startActivity(intent_cashAtpos);
//            startActivityForResult(intent_cashAtpos, MSWIPE_CASHATPOS_ACTIVITY_REQUEST_CODE);
//			//finish();
//
//		}else if (isEmiSale) {
//
//			if (ApplicationData.IS_DEBUGGING_ON)
//				Logs.v(ApplicationData.packName, "isEmiSale" + isEmiSale, true, true);
//
//			getEmiDetails();
//
//		}
//		else {

           Intent intent_cardsale = new Intent(MswipePaymentView.this, MswipeCardSaleActivity.class);
			intent_cardsale.putExtra("cardsale", true);

			intent_cardsale.putExtra("username", mTxtUserName.getText().toString().trim());
			intent_cardsale.putExtra("password", mTxtPassword.getText().toString().trim());
			intent_cardsale.putExtra("amount", totalAmt);
			intent_cardsale.putExtra("mobileno", mTxtPhoneNum.getText().toString().trim());
			intent_cardsale.putExtra("receiptno", mTxtReceipt.getText().toString().trim());
			intent_cardsale.putExtra("notes", mTxtNotes.getText().toString().trim());

			if (AppSharedPrefrences.getAppSharedPrefrencesInstace().getGatewayEnvironment() == MSWisepadController.GATEWAY_ENVIRONMENT.LABS)

				intent_cardsale.putExtra("production", false);
			else
				intent_cardsale.putExtra("production", true);

			//startActivity(intent_cardsale);
            startActivityForResult(intent_cardsale, MSWIPE_CARDSALE_ACTIVITY_REQUEST_CODE);
			//finish();

		}


   // }
	private void showDialog(String message){

		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		builder1.setMessage(message);
		builder1.setCancelable(false);

		builder1.setPositiveButton(
				"Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();

					}
				});

		AlertDialog alert11 = builder1.create();
		alert11.show();


	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == MSWIPE_CARDSALE_ACTIVITY_REQUEST_CODE) {

			boolean status = data.getBooleanExtra("status", false);
			String statusMessage = data.getStringExtra("statusMessage");
			String receiptDetail = data.getStringExtra("receiptDetail");

			if (resultCode == RESULT_OK) {
				if (AppSharedPrefrences.getAppSharedPrefrencesInstace().isSignatureRequired()) {
					Intent intent = new Intent(MswipePaymentView.this, MswipeSignatureActivity.class);
					intent.putExtra("Title", "card sale");
					intent.putExtra("receiptDetail", receiptDetail);
					intent.putExtra("cardSaleResponseData", data.getSerializableExtra("cardSaleResponseData"));
					startActivityForResult(intent, MSWIPE_CARDSALE_SIGNATURE_REQUEST);
				} else {
					showapproveDialog(Boolean.toString(status), data.getExtras().getString("AuthCode"),
							data.getExtras().getString("RRNo"), statusMessage);
				}
			} else {

				Intent intent = new Intent(MswipePaymentView.this, MswipeDeclineActivity.class);
				intent.putExtra("statusMessage", statusMessage);
				intent.putExtra("cardSaleResponseData", data.getSerializableExtra("cardSaleResponseData"));
				intent.putExtra("Title", getResources().getString(R.string.card_sale));
				startActivityForResult(intent, MSWIPE_CARDSALE_DECLINE_REQUEST);
			}
		}
//		}else if(requestCode == MSWIPE_CASHATPOS_ACTIVITY_REQUEST_CODE)
//		{
//
//			boolean status = data.getBooleanExtra("status", false);
//			String statusMessage = data.getStringExtra("statusMessage");
//
//			if(resultCode == RESULT_OK)
//			{
//				if(AppSharedPrefrences.getAppSharedPrefrencesInstace().isSignatureRequired())
//				{
//					Intent intent = new Intent(MswipePaymentView.this, MswipeSignatureActivity.class);
//					intent.putExtra("Title", getResources().getString(R.string.cash_at_pos));
//					intent.putExtra("cardSaleResponseData", data.getSerializableExtra("cardSaleResponseData"));
//					startActivityForResult(intent, MSWIPE_CARDSALE_SIGNATURE_REQUEST);
//				}else
//				{
//					showapproveDialog(Boolean.toString(status),data.getExtras().getString("AuthCode"),
//							data.getExtras().getString("RRNo"),statusMessage);
//				}
//			}
//			else {
//
//				Intent intent = new Intent(MswipePaymentView.this, MswipeDeclineActivity.class);
//				intent.putExtra("statusMessage", statusMessage);
//				intent.putExtra("cardSaleResponseData", data.getSerializableExtra("cardSaleResponseData"));
//				intent.putExtra("Title",  getResources().getString(R.string.cash_at_pos));
//				startActivityForResult(intent, MSWIPE_CARDSALE_DECLINE_REQUEST);
//			}
//		}else if(requestCode == MSWIPE_EMISALE_ACTIVITY_REQUEST_CODE)
//		{
//
//			boolean status = data.getBooleanExtra("status", false);
//			String statusMessage = data.getStringExtra("statusMessage");
//
//			if(resultCode == RESULT_OK)
//			{
//				if(AppSharedPrefrences.getAppSharedPrefrencesInstace().isSignatureRequired())
//				{
//					Intent intent = new Intent(MswipePaymentView.this, MswipeSignatureActivity.class);
//					intent.putExtra("Title", getResources().getString(R.string.emi));
//					intent.putExtra("cardSaleResponseData", data.getSerializableExtra("cardSaleResponseData"));
//					startActivityForResult(intent, MSWIPE_CARDSALE_SIGNATURE_REQUEST);
//				}else
//				{
//					showapproveDialog(Boolean.toString(status),data.getExtras().getString("AuthCode"),
//							data.getExtras().getString("RRNo"),statusMessage);
//				}
//			}
//			else {
//
//				Intent intent = new Intent(MswipePaymentView.this, MswipeDeclineActivity.class);
//				intent.putExtra("statusMessage", statusMessage);
//				intent.putExtra("cardSaleResponseData", data.getSerializableExtra("cardSaleResponseData"));
//				intent.putExtra("Title",getResources().getString(R.string.emi));
//				startActivityForResult(intent, MSWIPE_CARDSALE_DECLINE_REQUEST);
//			}
//		}
//        else if(requestCode == MSWIPE_PREAUTH_ACTIVITY_REQUEST_CODE)
//		{
//
//			boolean status = data.getBooleanExtra("status", false);
//			String statusMessage = data.getStringExtra("statusMessage");
//
//			if(resultCode == RESULT_OK)
//			{
//				if(AppSharedPrefrences.getAppSharedPrefrencesInstace().isSignatureRequired())
//				{
//					Intent intent = new Intent(MswipePaymentView.this, MswipeSignatureActivity.class);
//					intent.putExtra("Title", getResources().getString(R.string.preauth));
//					intent.putExtra("cardSaleResponseData", data.getSerializableExtra("cardSaleResponseData"));
//					startActivityForResult(intent, MSWIPE_CARDSALE_SIGNATURE_REQUEST);
//				}else
//				{
//					showapproveDialog(Boolean.toString(status),data.getExtras().getString("AuthCode"),
//							data.getExtras().getString("RRNo"),statusMessage);
//				}
//			}
//			else {
//
//				Intent intent = new Intent(MswipePaymentView.this, MswipeDeclineActivity.class);
//				intent.putExtra("statusMessage", statusMessage);
//				intent.putExtra("cardSaleResponseData", data.getSerializableExtra("cardSaleResponseData"));
//				intent.putExtra("Title", getResources().getString(R.string.preauth));
//				startActivityForResult(intent, MSWIPE_CARDSALE_DECLINE_REQUEST);
//			}
//		}
	}
	/**
	 * @description
	 *        We are removing specific character form original string.
	 * @param s original string
	 * @param c specific character.
	 * @return
	 */
	public String removeChar(String s, char c) {

		String r = "";

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != c)
				r += s.charAt(i);
		}

		return r;
	}

	public void showapproveDialog(String status, String authcode, String rrno, String reason)
     {

		 final Dialog dialog = new Dialog(MswipePaymentView.this, R.style.styleCustDlg);
		 dialog.setContentView(R.layout.cardsale_status_customdlg);
		 dialog.setCanceledOnTouchOutside(false);
		 dialog.setCancelable(true);

		 TextView txtstatusmsg = (TextView) dialog.findViewById(R.id.customdlg_Txt_status);
		 txtstatusmsg.setText(status);

		 TextView txtauthcode = (TextView) dialog.findViewById(R.id.customdlg_Txt_authcode);
		 txtauthcode.setText(authcode);

		 TextView txtrrno = (TextView) dialog.findViewById(R.id.customdlg_Txt_rrno);
		 txtrrno.setText(rrno);

		 TextView txtreason = (TextView) dialog.findViewById(R.id.customdlg_Txt_reason);
		 txtreason.setText(reason);

		 Button yes = (Button) dialog.findViewById(R.id.customdlg_BTN_yes);
		 yes.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 dialog.dismiss();
				 finish();
				 Intent intent = new Intent(MswipePaymentView.this, MenuView.class);
				 startActivity(intent);
			 }
		 });

		 dialog.show();
	 }

	public void getEmiDetails() {

		try {
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setMessage(getString(R.string.processing));
			mProgressDialog.show();

			MSWisepadController.getSharedMSWisepadController(MswipePaymentView.this,
					AppSharedPrefrences.getAppSharedPrefrencesInstace().getGatewayEnvironment(),
					AppSharedPrefrences.getAppSharedPrefrencesInstace().getNetworkSource(),
					null).getEMISaleTrxDetails(
					AppSharedPrefrences.getAppSharedPrefrencesInstace().getReferenceId(),
					AppSharedPrefrences.getAppSharedPrefrencesInstace().getSessionToken(),
					mTxtFirstSixDigits.getText().toString().trim(),
					mTxtCreditAmount.getText().toString().trim(),
					new MswipePaymentView.MSWisepadControllerResponseListenerObserver()
			);

		} catch (Exception e) {

			showDialog( "data decryption error");
		}
	}

	int selectedPosition = -1;

	class MSWisepadControllerResponseListenerObserver implements MSWisepadControllerResponseListener {


		@Override
		public void onReponseData(MSDataStore mswipeDataStore) {

			if (mswipeDataStore instanceof EMITransactionDetailsResponseData) {

				EMITransactionDetailsResponseData emiTransactionDetailsResponseData = (EMITransactionDetailsResponseData) mswipeDataStore;

				if (emiTransactionDetailsResponseData.getResponseStatus()){
//
//					final ArrayList<EMIData> arrayList = emiTransactionDetailsResponseData.getEmiTxtArrayListData();
//
//					Dialog dialog = showEmiOptionsDialog(MswipePaymentView.this);
//
//					ListView listView = (ListView)dialog.findViewById(R.id.emisale_list_emi_selection);
//
//					final EmiAdapter emiAdapter = new EmiAdapter(MswipePaymentView.this, arrayList);
//					listView.setAdapter(emiAdapter);
//
//					listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//						@Override
//						public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//
//							selectedPosition = position;
//
//							mEmiPeriod = arrayList.get(position).emiTenure;
//							mEmiBankCode = arrayList.get(position).emiBankCode;
//							mEmiRate = arrayList.get(position).emiRate;
//							mEmiAmount = arrayList.get(position).emiAmt;
//
//							emiAdapter.notifyDataSetChanged();
//						}
//					});
//
//					((Button)dialog.findViewById(R.id.customdlg_BTN_yes)).setOnClickListener(new View.OnClickListener() {
//						@Override
//						public void onClick(View view) {
//
//							if(selectedPosition == -1){
//
//								showDialog("please select an option");
//
//								return;
//							}
//
//							double amt = Double.parseDouble(mTxtCreditAmount.getText().toString().trim());
//							String totalAmt = String.format("%.2f", amt);
//
//							Intent intent_emisale = new Intent(MswipePaymentView.this, MswipeEMISaleActivity.class);
//							intent_emisale.putExtra("emisale", true);
//
//							intent_emisale.putExtra("username", mTxtUserName.getText().toString().trim());
//							intent_emisale.putExtra("password", mTxtPassword.getText().toString().trim());
//
//							if (AppSharedPrefrences.getAppSharedPrefrencesInstace().getGatewayEnvironment() == MSWisepadController.GATEWAY_ENVIRONMENT.LABS)
//
//								intent_emisale.putExtra("production", false);
//							else
//								intent_emisale.putExtra("production", true);
//
//							intent_emisale.putExtra("amount", totalAmt);
//							intent_emisale.putExtra("mobileno", mTxtPhoneNum.getText().toString().trim());
//							intent_emisale.putExtra("receiptno", mTxtReceipt.getText().toString().trim());
//							intent_emisale.putExtra("notes", mTxtNotes.getText().toString().trim());
//
//							intent_emisale.putExtra("firstSixDigits",mTxtFirstSixDigits.getText().toString());
//
//							intent_emisale.putExtra("emirate", mEmiRate);
//							intent_emisale.putExtra("emiamount", mEmiAmount);
//							intent_emisale.putExtra("emiperiod", mEmiPeriod);
//							intent_emisale.putExtra("emibankcode", mEmiBankCode);
//							startActivityForResult(intent_emisale, MSWIPE_EMISALE_ACTIVITY_REQUEST_CODE);
//							//finish();
//
//						}
//					});
//
//					dialog.show();

				}
				else {

					AlertDialog.Builder builder1 = new AlertDialog.Builder(MswipePaymentView.this);
					builder1.setMessage(emiTransactionDetailsResponseData.getResponseFailureReason());
					builder1.setCancelable(false);

					builder1.setPositiveButton(
							"Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.dismiss();
									finish();

								}
							});


					AlertDialog alert11 = builder1.create();
					alert11.show();
				}

			}

		}
	}

//	public class EmiAdapter extends BaseAdapter {
//		ArrayList<EMIData> listData = null;
//		Context context;
//
//		public EmiAdapter(Context context, ArrayList<EMIData> listData) {
//			this.listData = listData;
//			this.context = context;
//		}
//
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return listData.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//
//		@Override
//		public View getView(final int position, View convertView, ViewGroup parent) {
//			// TODO Auto-generated method stub
//			if (convertView == null) {
//				LayoutInflater inflater = LayoutInflater.from(context);
//				convertView = inflater.inflate(R.layout.emi_list_row, null);
//			}
//
//			EMIData emiData = listData.get(position);
//
//			TextView txt_month = (TextView) convertView.findViewById(R.id.emi_list_txt_month);
//			TextView txt_month_intallment = (TextView) convertView.findViewById(R.id.emi_list_txt_monthly_installment);
//			TextView txt_rate = (TextView) convertView.findViewById(R.id.emi_list_txt_rate);
//			TextView txt_cashbank_amt = (TextView) convertView.findViewById(R.id.emi_list_txt_cashbackamt);
//			TextView txt_cashback_rate = (TextView) convertView.findViewById(R.id.emi_list_txt_cashbackrate);
//			final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.emi_list_checkbox);
//
//			if(selectedPosition == position)
//			{
//				checkBox.setChecked(true);
//			}
//			else{
//				checkBox.setChecked(false);
//			}
//
//			if (listData.get(position) != null){
//				txt_month.setText(emiData.emiTenure+" months");
//				txt_rate.setText(emiData.emiRate+" %");
//				txt_month_intallment.setText(ApplicationData.mCurrency +" "+emiData.emiAmt);
//				txt_cashback_rate.setText(emiData.cashBackRate+" %");
//				txt_cashbank_amt.setText(ApplicationData.mCurrency +" "+emiData.cashBackAmt);
//			}
//
//			return convertView;
//		}
//	}

//	public static Dialog showEmiOptionsDialog(Context context) {
//
//		Dialog dialog = new Dialog(context);
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		dialog.setContentView(R.layout.emi_options_dialog);
//		dialog.setCanceledOnTouchOutside(false);
//
//		dialog.setCancelable(true);
//
//		return dialog;
//
//	}



}