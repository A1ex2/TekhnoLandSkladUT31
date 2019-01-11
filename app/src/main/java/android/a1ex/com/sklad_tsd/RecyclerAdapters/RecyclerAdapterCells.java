package android.a1ex.com.sklad_tsd.RecyclerAdapters;

import android.a1ex.com.sklad_tsd.Directories.Cell;
import android.a1ex.com.sklad_tsd.R;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapterCells extends RecyclerView.Adapter<RecyclerAdapterCells.GroupViewHolder> {
    private int mResourse;
    private ArrayList<Cell> mCells;
    private LayoutInflater mInflater;

    public RecyclerAdapterCells(Context context, int resourse, ArrayList<Cell> cells){
        mResourse = resourse;
        mCells = cells;
        mInflater = LayoutInflater.from(context);
    }

    public interface ActionListener {
        void onClick(Cell cell);
    }

    private ActionListener mListener;

    public void setActionListener(ActionListener listener){
        mListener = listener;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(mResourse, null);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder groupViewHolder, int i) {
        final Cell cell = mCells.get(i);
        groupViewHolder.set(cell);

        if (mListener != null){
            groupViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(cell);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCells.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;

        public GroupViewHolder( View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.nameCell);
        }

        public void set(Cell cell){
            mName.setText(cell.name);
        }
    }
}
