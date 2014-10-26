package es.flakiness.hellolist;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Created by omo on 10/26/14.
 */
public class HelloListAdapter implements ListAdapter {

    private DataSetObservable mObservable = new DataSetObservable();
    private int mCount = 3;
    private final Context mContext;

    HelloListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        mObservable.registerObserver(dataSetObserver);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        mObservable.unregisterObserver(dataSetObserver);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public Object getItem(int i) {
        return getLabel(i);
    }

    private String getLabel(int i) {
        return "[" + i + "]";
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView theView = view != null ? (TextView)view : new TextView(mContext);
        theView.setText(getLabel(i));
        theView.setTextSize(30.0f);
        return theView;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public boolean incrementIfNeeded() {
        if (20 < mCount)
            return false;
        mCount++;
        mObservable.notifyChanged();
        return true;
    }
}
