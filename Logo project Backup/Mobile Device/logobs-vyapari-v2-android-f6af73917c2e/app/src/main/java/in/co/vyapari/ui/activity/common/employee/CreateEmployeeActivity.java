package in.co.vyapari.ui.activity.common.employee;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.co.vyapari.R;
import in.co.vyapari.VyapariApp;
import in.co.vyapari.constant.Constants;
import in.co.vyapari.model.Employee;
import in.co.vyapari.ui.base.SohoActivity;
import in.co.vyapari.util.ValidateUtil;

public class CreateEmployeeActivity extends SohoActivity {

    @BindView(R.id.toolbar_name_et)
    EditText employeeNameET;
    @BindView(R.id.create_employee_job)
    EditText jobET;
    @BindView(R.id.create_employee_email)
    EditText emailET;
    @BindView(R.id.create_employee_phone)
    EditText phoneET;

    private Context mContext;
    private Employee employee = new Employee();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_firm_employee);
        ButterKnife.bind(this);
        mContext = this;

        //setDetailToolbarConfig(R.string.add_employee, R.drawable.icon_profile_person, R.string.employee, R.string.name);
    }

    @OnClick(R.id.create_employee_submit_button)
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

    private void returnResultFinish() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.ACTION, Constants.ADD);
        returnIntent.putExtra(Constants.EMPLOYEE, employee);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}