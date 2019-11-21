package in.co.vyapari.ui.activity.common.bank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.co.vyapari.R;
import in.co.vyapari.VyapariApp;
import in.co.vyapari.constant.Constants;
import in.co.vyapari.model.Bank;
import in.co.vyapari.model.Currency;
import in.co.vyapari.ui.base.SohoActivity;
import in.co.vyapari.util.ValidateUtil;

public class EditBankActivity extends SohoActivity {

    @BindView(R.id.toolbar_name_et)
    EditText bankNameET;
    @BindView(R.id.edit_bank_branch)
    EditText branchET;
    @BindView(R.id.edit_bank_branch_code)
    EditText branchCodeET;
    //@BindView(R.id.edit_bank_currency_sp)
    //MySpinner currencySP;
    @BindView(R.id.edit_bank_account_number)
    EditText accountNumberET;
    @BindView(R.id.edit_bank_iban)
    EditText ibanET;

    private Context mContext;
    private ArrayList<Currency> currencyList;

    private Bank bank = new Bank();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_firm_bank);
        ButterKnife.bind(this);
        mContext = this;

        //setDetailToolbarConfig(R.string.edit_bank_account, R.drawable.icon_profile_person, R.string.bank_account, R.string.bank_header);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            bank = bundle.getParcelable(Constants.BANK);

            bankNameET.setText(bank.getName());
            branchET.setText(bank.getBranch());
            branchCodeET.setText(bank.getBranchCode());
            accountNumberET.setText(bank.getAccountNumber());
            ibanET.setText(bank.getIban());
        }

        /*CurrencyService.getCurrencyCodes(new ActionListener<ArrayList<Currency>>() {
            @Override
            public void start() {
                lockSpinner(currencySP);
            }

            @Override
            public void results(boolean isPreloaded, ArrayList<Currency> data) {
                unlockSpinner(currencySP);
                currencyList = data;
                setCurrencyConfig();
            }
        });*/
    }

    /*
    private void setCurrencyConfig() {
        currencySP.setAdapter(new CurrencySpinnerAdapter(mContext, currencyList));

        for (int i = 0; i < currencyList.size(); i++) {
            if (Utils.equalsKeyValue(currencyList.get(i),bank.getCurrency())) {
                currencySP.setSelection(i);
                break;
            }
        }
    }*/

    @OnClick(R.id.edit_bank_submit_button)
    public void saveBankClick() {
        boolean isValidAccountNumber = ValidateUtil.validateEditTexts(bankNameET, branchET, branchCodeET, accountNumberET);
        boolean isNotNullIBAN = ValidateUtil.validateEditTexts(bankNameET, ibanET);
        if (!isValidAccountNumber && !isNotNullIBAN) {
            Toasty.warning(mContext, getString(R.string.empy_warn)).show();
            return;
        }

        if (!VyapariApp.getServiceQueue().isSuccess()) {
            return;
        }

        String bankName = bankNameET.getText().toString().trim();
        String branch = branchET.getText().toString().trim();
        String branchCode = branchCodeET.getText().toString().trim();
        //Currency currency = currencyList.get(currencySP.getSelectedItemPosition());
        String accountNumber = accountNumberET.getText().toString().trim();
        String iban = ibanET.getText().toString().trim();

        if (!iban.isEmpty()) {
            if (!ValidateUtil.validateIBAN(iban)) {
                Toasty.warning(mContext, getString(R.string.iban_valid_warn)).show();
                return;
            }
        }

        bank.setName(bankName);
        bank.setBranch(branch);
        bank.setBranchCode(branchCode);
        //bank.setCurrency(currency);
        bank.setAccountNumber(accountNumber);
        bank.setIban(iban);

        returnResultFinish();
    }

    @OnClick(R.id.edit_bank_delete_button)
    public void deleteBankClick() {
        returnDeteleFinish();
    }

    private void returnResultFinish() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.ACTION, Constants.ADD);
        returnIntent.putExtra(Constants.BANK, bank);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void returnDeteleFinish() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.ACTION, Constants.REMOVE);
        returnIntent.putExtra(Constants.BANK, bank);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}