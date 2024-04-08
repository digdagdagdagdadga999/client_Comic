package datdvph44632.fpoly.assignment_networking.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import datdvph44632.fpoly.assignment_networking.R;
import datdvph44632.fpoly.assignment_networking.models.Truyen;
import datdvph44632.fpoly.assignment_networking.others.Apis;
import datdvph44632.fpoly.assignment_networking.others.DetailComic;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ComicViewHolder> {

    private Context mContext;
    private ArrayList<Truyen> arrayList;

    String TAG = "dat";

    public ComicAdapter(Context mContext, ArrayList<Truyen> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public ComicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_comic, parent, false);


        return new ComicViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicViewHolder holder, int position) {
//        Truyen truyen = arrayList.get(holder.getAdapterPosition());
        holder.tvTenTruyen.setText(arrayList.get(position).getTen_truyen());
        holder.tvTacGia.setText(arrayList.get(position).getTac_gia());
        Picasso.get().load(Apis.BASE_URL + "/uploads/" + arrayList.get(position).getAnh_bia()).into(holder.imgAnhBia);
        Log.e(TAG, "ảnh: " + Apis.BASE_URL + "/uploads/" + arrayList.get(position).getAnh_bia());

        holder.cardViewTruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), DetailComic.class);
                intent.putExtra("id", arrayList.get(position).get_id());
                intent.putExtra("ten_truyen", arrayList.get(position).getTen_truyen());
                intent.putExtra("tac_gia", arrayList.get(position).getTac_gia());
                intent.putExtra("nam", arrayList.get(position).getNam_xb());
                intent.putExtra("mo_ta", arrayList.get(position).getMo_ta());
                intent.putExtra("anh_bia", arrayList.get(position).getAnh_bia());

                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public static class ComicViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAnhBia;
        private TextView tvTacGia, tvTenTruyen;
        private CardView cardViewTruyen;

        public ComicViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenTruyen = itemView.findViewById(R.id.tvTenTruyen);
            tvTacGia = itemView.findViewById(R.id.tvTacGia);
            imgAnhBia = itemView.findViewById(R.id.imgAnhBia);
            cardViewTruyen = itemView.findViewById(R.id.cardviewTruyen);
        }
    }
}
