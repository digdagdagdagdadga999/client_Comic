package datdvph44632.fpoly.assignment_networking.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import datdvph44632.fpoly.assignment_networking.R;
import datdvph44632.fpoly.assignment_networking.models.BinhLuan;
import datdvph44632.fpoly.assignment_networking.models.Truyen;

public class BinhLuanAdapter extends RecyclerView.Adapter<BinhLuanAdapter.BinhLuanHolder> {
    private ArrayList<Truyen> truyenArrayList;
    private ArrayList<BinhLuan> binhLuanArrayList;

    private Context mContext;

    public BinhLuanAdapter(ArrayList<BinhLuan> binhLuanArrayList, Context mContext) {
        this.binhLuanArrayList = binhLuanArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BinhLuanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_binh_luan, null);
        return new BinhLuanHolder(view);
    }

    public void getData(ArrayList<BinhLuan> neLIst) {
        binhLuanArrayList = neLIst;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull BinhLuanHolder holder, int position) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("User", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("id", "");

        BinhLuan binhLuan = binhLuanArrayList.get(position);
        holder.tvNameCmt.setText(binhLuan.getUsername());
        holder.tvCmt.setText(binhLuan.getContent());
        holder.tvCmtDate.setText(binhLuan.getDate());
    }

    @Override
    public int getItemCount() {
        return binhLuanArrayList.size();
    }

    public class BinhLuanHolder extends RecyclerView.ViewHolder {

        TextView tvNameCmt, tvCmt, tvCmtDate;

        public BinhLuanHolder(@NonNull View itemView) {
            super(itemView);
            tvNameCmt = itemView.findViewById(R.id.tvNameCmt);
            tvCmt = itemView.findViewById(R.id.tvCmt);
            tvCmtDate = itemView.findViewById(R.id.tvCmtDate);
        }
    }
}
