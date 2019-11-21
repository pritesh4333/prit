package in.co.vyaparienterprise.ui.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.model.Employee;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bekir.Dursun on 23.10.2017.
 */

public class FirmEmployeeAdapter extends RecyclerView.Adapter<FirmEmployeeAdapter.MyViewHolder> {

    private ArrayList<Employee> employeeList;
    private int iconId;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_icon)
        ImageView icon;
        @BindView(R.id.item_text)
        TextView text;


        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public FirmEmployeeAdapter(ArrayList<Employee> employeeList, int iconId) {
        if (employeeList == null) {
            employeeList = new ArrayList<>();
        }
        this.employeeList = employeeList;
        this.iconId = iconId;
    }

    @Override
    public FirmEmployeeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FirmEmployeeAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_icon_text_oneline, parent, false));
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(final FirmEmployeeAdapter.MyViewHolder holder, final int position) {
        Employee employee = employeeList.get(position);
        holder.icon.setImageResource(iconId);
        holder.text.setText(employee.getFullName());
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }
}