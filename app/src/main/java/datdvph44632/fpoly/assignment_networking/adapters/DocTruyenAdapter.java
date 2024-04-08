package datdvph44632.fpoly.assignment_networking.adapters;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import datdvph44632.fpoly.assignment_networking.R;
import datdvph44632.fpoly.assignment_networking.others.Apis;

public class DocTruyenAdapter extends RecyclerView.Adapter<DocTruyenAdapter.DocTruyenHolder>{
    ArrayList<String> listDoc;
    Context mContext;
    String TAG = "dat";

    public DocTruyenAdapter(ArrayList<String> listDoc, Context mContext) {
        this.listDoc = listDoc;
        this.mContext = mContext;
    }

    public void LoadDataDoc(ArrayList<String> newListDoc) {
        listDoc = newListDoc;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DocTruyenHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_doc_truyen, null);
        return new DocTruyenHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocTruyenHolder holder, int position) {
        String urlAnh = listDoc.get(position);

        Log.e(TAG, "urlAnh: " + urlAnh );

        // Sử dụng Picasso để tải ảnh
        Picasso.get().load(Apis.BASE_URL + "/uploads/" + urlAnh).into(holder.imgDocTruyen);

    }

    @Override
    public int getItemCount() {
        if (listDoc != null){
            return listDoc.size();
        }
        return 0;
    }

    public class DocTruyenHolder extends RecyclerView.ViewHolder {
        ImageView imgDocTruyen;
        public DocTruyenHolder(@NonNull View itemView) {
            super(itemView);
            imgDocTruyen = itemView.findViewById(R.id.imgDoc);
        }
    }
}
