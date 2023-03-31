package com.example.guardterminal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterApplications extends RecyclerView.Adapter<AdapterApplications.ViewHolder> {
    private Context _context;
    private List<Applications> applicationsList;

    public AdapterApplications(Context _context, List<Applications> applicationsList) {
        this._context = _context;
        this.applicationsList = applicationsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(_context).inflate(R.layout.item_applications,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(applicationsList.get(position).getName());
        holder.txtDate.setText(applicationsList.get(position).getDate());
        holder.txtSubdivision.setText(applicationsList.get(position).getSubdivision());
        holder.txtType.setText(applicationsList.get(position).getType());
    }
    @Override
    public int getItemCount() {
        return applicationsList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtDate, txtSubdivision, txtType;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.name_item);
            txtDate = itemView.findViewById(R.id.date_item);
            txtSubdivision = itemView.findViewById(R.id.subdivision_item);
            txtType = itemView.findViewById(R.id.type_item);
        }
    }
}
