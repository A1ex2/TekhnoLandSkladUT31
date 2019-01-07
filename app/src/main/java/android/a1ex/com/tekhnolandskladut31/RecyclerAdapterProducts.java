package android.a1ex.com.tekhnolandskladut31;

import android.a1ex.com.tekhnolandskladut31.Directories.Cell;
import android.a1ex.com.tekhnolandskladut31.Directories.Product;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapterProducts extends RecyclerView.Adapter<RecyclerAdapterProducts.GroupViewHolder> {
    private int mResourse;
    private ArrayList<Product> mProducts;
    private LayoutInflater mInflater;

    public RecyclerAdapterProducts(Context context, int resourse, ArrayList<Product> groups) {
        mResourse = resourse;
        mProducts = groups;
        mInflater = LayoutInflater.from(context);
    }

    public interface ActionListener {
        void onClick(Product product);
    }

    private ActionListener mListener;

    public void setActionListener(ActionListener listener) {
        mListener = listener;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(mResourse, null);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder groupViewHolder, int i) {
        final Product product = mProducts.get(i);
        groupViewHolder.set(product);

        if (mListener != null) {
            groupViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(product);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;

        public GroupViewHolder(View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.nameProduct);
        }

        public void set(Product product) {
            mName.setText(product.toString());
        }
    }
}
