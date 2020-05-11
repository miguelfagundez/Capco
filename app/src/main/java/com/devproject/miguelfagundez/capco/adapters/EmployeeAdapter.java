package com.devproject.miguelfagundez.capco.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devproject.miguelfagundez.capco.R;
import com.devproject.miguelfagundez.capco.listeners.EmployeeListener;
import com.devproject.miguelfagundez.capco.model.pojo.Employee;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/********************************************
 * Class- EmployeeAdapter
 * This class implements the RecyclerView.Adapter
 * this data is connected with layout_employee_item
 * @author: Miguel Fagundez
 * @date: May 09th, 2020
 * @version: 1.0
 * *******************************************/
public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder>{

    //******************************************
    // Employee List
    //******************************************
    private List<Employee> list;
    private EmployeeListener listener;

    public EmployeeAdapter(EmployeeListener listener) {
        //******************************************
        // Connecting with EmployeeListActivity
        //******************************************
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_employee_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvId.setText(String.valueOf(list.get(position).getId()));
        holder.tvName.setText(list.get(position).getLastName() + ", " + list.get(position).getFirstName());
        holder.tvRole.setText(list.get(position).getRole());

        //******************************************
        // Connecting with EmployeeListActivity
        //******************************************
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.employeeClick(list.get(position).getId(),
                        list.get(position).getFirstName(),
                        list.get(position).getLastName(),
                        list.get(position).getRole());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }

    //******************************************
    // Notify data changes
    //******************************************
    public void setEmployeeInfo(List<Employee> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //******************************************
        // Employee members (Name, role, and id)
        //******************************************
        TextView tvName;
        TextView tvRole;
        TextView tvId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvEmployeeName);
            tvRole = itemView.findViewById(R.id.tvEmployeeRole);
            tvId = itemView.findViewById(R.id.tvEmployeeId);
        }
    }
}

