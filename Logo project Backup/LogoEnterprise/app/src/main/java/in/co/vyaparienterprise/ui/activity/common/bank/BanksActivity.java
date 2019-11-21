package in.co.vyaparienterprise.ui.activity.common.bank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.constant.Constants;
import in.co.vyaparienterprise.model.Bank;
import in.co.vyaparienterprise.ui.adapter.FirmBanksAdapter;
import in.co.vyaparienterprise.ui.base.SohoActivity;
import in.co.vyaparienterprise.ui.generic.DividerItemDecoration;
import in.co.vyaparienterprise.ui.listener.ClickListener;
import in.co.vyaparienterprise.ui.listener.RecyclerViewClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BanksActivity extends SohoActivity {

    @BindView(R.id.banks_rv)
    RecyclerView banksRV;

    private Context mContext;
    private FirmBanksAdapter firmBanksAdapter;
    private ArrayList<Bank> bankList = new ArrayList<>();
    private Bank clickedBank;

    private boolean isRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firm_banks);
        ButterKnife.bind(this);
        setToolbarConfig(R.string.bank_accounts);
        mContext = this;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            bankList = bundle.getParcelableArrayList(Constants.BANK_LIST);
        }

        config();

        firmBanksAdapter = new FirmBanksAdapter(bankList, R.drawable.icon_profile_person);
        banksRV.setAdapter(firmBanksAdapter);

        if (bankList.size() == 0) {
            fabClick();
        }
    }

    private void config() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 1);
        banksRV.setItemAnimator(new DefaultItemAnimator());
        banksRV.setHasFixedSize(true);
        banksRV.setNestedScrollingEnabled(false);
        banksRV.setLayoutManager(mLayoutManager);
        banksRV.addItemDecoration(new DividerItemDecoration(mContext));
        banksRV.addOnItemTouchListener(new RecyclerViewClickListener(mContext, banksRV, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                clickedBank = bankList.get(position);
                Bundle bundle = new Bundle();
                Intent bankIntent = new Intent(mContext, EditBankActivity.class);
                bundle.putParcelable(Constants.BANK, clickedBank);
                bankIntent.putExtras(bundle);
                startActivityForResult(bankIntent, Constants.REFRESH_CODE);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @OnClick(R.id.fab)
    public void fabClick() {
        startActivityForResult(new Intent(mContext, CreateBankActivity.class), Constants.REFRESH_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REFRESH_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                int action = data.getIntExtra(Constants.ACTION, 0);
                Bank result = data.getParcelableExtra(Constants.BANK);
                if (result != null) {
                    isRefresh = true;
                    if (action == Constants.ADD) {
                        bankList.remove(clickedBank);
                        bankList.add(0, result);
                    } else if (action == Constants.REMOVE) {
                        bankList.remove(clickedBank);
                    } //TODO: iconu değiştir.
                    firmBanksAdapter = new FirmBanksAdapter(bankList, R.drawable.icon_profile_person);
                    banksRV.setAdapter(firmBanksAdapter);
                }
            }
        }

        if (bankList.size() == 0) {
            finish();
        }
    }

    private void returnResultFinish() {
        Intent returnIntent = new Intent();
        returnIntent.putParcelableArrayListExtra(Constants.BANK_LIST, bankList);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (isRefresh) {
            returnResultFinish();
        } else {
            super.onBackPressed();
        }
    }
}