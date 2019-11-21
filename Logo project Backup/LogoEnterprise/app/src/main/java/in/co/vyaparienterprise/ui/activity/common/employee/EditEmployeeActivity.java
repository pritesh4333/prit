package in.co.vyaparienterprise.ui.activity.common.employee;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.VyapariApp;
import in.co.vyaparienterprise.constant.Constants;
import in.co.vyaparienterprise.middleware.listener.ServiceCall;
import in.co.vyaparienterprise.middleware.service.FirmService;
import in.co.vyaparienterprise.model.Employee;
import in.co.vyaparienterprise.model.response.BaseModel;
import in.co.vyaparienterprise.ui.base.SohoActivity;
import in.co.vyaparienterprise.util.ValidateUtil;

public class EditEmployeeActivity extends SohoActivity {

    @BindView(R.id.toolbar_name_et)
    EditText employeeNameET;
    @BindView(R.id.edit_employee_job)
    EditText jobET;
    @BindView(R.id.edit_employee_email)
    EditText emailET;
    @BindView(R.id.edit_employee_phone)
    EditText phoneET;

    private Context mContext;
    private Employee employee = new Employee();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_firm_employee);
        ButterKnife.bind(this);
        mContext = this;

        //setDetailToolbarConfig(R.string.edit_employee, R.drawable.icon_profile_person, R.string.employee, R.string.name);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            employee = bundle.getParcelable(Constants.EMPLOYEE);

            employeeNameET.setText(employee.getFullName());
            employeeNameET.setSelection(employee.getFullName().length());
            jobET.setText(employee.getJob());
            phoneET.setText(employee.getPhone());
            emailET.setText(employee.getEmail());
        }
    }

    @OnClick(R.id.edit_employee_submit_button)
    public void saveEmployeeClick() {
        boolean isValid = ValidateUtil.validateEditTexts(employeeNameET, phoneET);
        if (!isValid) {
            Toasty.warning(mContext, getString(R.string.empy_warn)).show();
            return;
        }

        if (!VyapariApp.getServiceQueue().isSuccess()) {
            return;
        }

        String employeeName = employeeNameET.getText().toString().trim();
        String job = jobET.getText().toString().trim();
        String email = emailET.getText().toString().trim();
        String phone = phoneET.getText().toString().trim();

        employee.setFullName(employeeName);
        employee.setJob(job);
        employee.setEmail(email);
        employee.setPhone(phone);

        returnResultFinish();
    }

    @OnClick(R.id.edit_employee_delete_button)
    public void deleteEmployeeClick() {
        if (employee.getId() == null) {
            returnDeteleFinish();
        } else {
            deleteEmployeeCall(employee.getId());
        }
    }

    private void deleteEmployeeCall(String id) {
        FirmService.deleteEmployee(id, new ServiceCall<BaseModel<String>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<String> response) {
                if (!response.isError()) {
                    String message = response.getMessage();
                    Toasty.success(mContext, message).show();
                    returnDeteleFinish();
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Toasty.error(mContext, getString(R.string.error)).show();
            }
        });
    }

    private void returnResultFinish() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.ACTION, Constants.ADD);
        returnIntent.putExtra(Constants.EMPLOYEE, employee);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void returnDeteleFinish() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.ACTION, Constants.REMOVE);
        returnIntent.putExtra(Constants.EMPLOYEE, employee);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}