package in.co.vyapari.ui.activity.common.employee;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.co.vyapari.R;
import in.co.vyapari.constant.Constants;
import in.co.vyapari.model.Employee;
import in.co.vyapari.ui.adapter.FirmEmployeeAdapter;
import in.co.vyapari.ui.base.SohoActivity;
import in.co.vyapari.ui.generic.DividerItemDecoration;
import in.co.vyapari.ui.listener.ClickListener;
import in.co.vyapari.ui.listener.RecyclerViewClickListener;

public class EmployeesActivity extends SohoActivity {

    @BindView(R.id.employees_rv)
    RecyclerView employeesRV;

    private Context mContext;
    private FirmEmployeeAdapter firmEmployeeAdapter;
    private ArrayList<Employee> employeeList = new ArrayList<>();
    private Employee clickedEmployee;

    private boolean isRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firm_employees);
        ButterKnife.bind(this);
        setToolbarConfig(R.string.employees);
        mContext = this;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            employeeList = bundle.getParcelableArrayList(Constants.EMPLOYEE_LIST);
        }

        config();

        firmEmployeeAdapter = new FirmEmployeeAdapter(employeeList, R.drawable.icon_profile_person);
        employeesRV.setAdapter(firmEmployeeAdapter);

        if (employeeList.size() == 0) {
            fabClick();
        }
    }

    private void config() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 1);
        employeesRV.setItemAnimator(new DefaultItemAnimator());
        employeesRV.setHasFixedSize(true);
        employeesRV.setNestedScrollingEnabled(false);
        employeesRV.setLayoutManager(mLayoutManager);
        employeesRV.addItemDecoration(new DividerItemDecoration(mContext));
        employeesRV.addOnItemTouchListener(new RecyclerViewClickListener(mContext, employeesRV, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                clickedEmployee = employeeList.get(position);
                Bundle bundle = new Bundle();
                Intent employeeIntent = new Intent(mContext, EditEmployeeActivity.class);
                bundle.putParcelable(Constants.EMPLOYEE, clickedEmployee);
                employeeIntent.putExtras(bundle);
                startActivityForResult(employeeIntent, Constants.REFRESH_CODE);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @OnClick(R.id.fab)
    public void fabClick() {
        startActivityForResult(new Intent(mContext, CreateEmployeeActivity.class), Constants.REFRESH_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REFRESH_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                int action = data.getIntExtra(Constants.ACTION, 0);
                Employee result = data.getParcelableExtra(Constants.EMPLOYEE);
                if (result != null) {
                    isRefresh = true;
                    if (action == Constants.ADD) {
                        employeeList.remove(clickedEmployee);
                        employeeList.add(0, result);
                        Toasty.info(mContext, getString(R.string.add_employee_warn)).show();
                    } else if (action == Constants.REMOVE) {
                        employeeList.remove(clickedEmployee);
                    }
                    firmEmployeeAdapter = new FirmEmployeeAdapter(employeeList, R.drawable.icon_profile_person);
                    employeesRV.setAdapter(firmEmployeeAdapter);
                }
            }
        }

        if (employeeList.size() == 0) {
            returnResultFinish();
        }
    }

    private void returnResultFinish() {
        Intent returnIntent = new Intent();
        returnIntent.putParcelableArrayListExtra(Constants.EMPLOYEE_LIST, employeeList);
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