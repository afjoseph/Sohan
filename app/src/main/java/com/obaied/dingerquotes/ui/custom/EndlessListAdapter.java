//package com.obaied.dingerquotes.ui.custom;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.obaied.dingerquotes.R;
//import com.obaied.dingerquotes.data.model.Quote;
//
//import java.util.List;
//
///**
// * Created by ab on 23.04.17.
// */
//
//public abstract class EndlessListAdapter<VH extends RecyclerView.ViewHolder>
//        extends RecyclerView.Adapter<VH> {
//    private boolean mIsAppending = false;
//
//    protected static final int VIEW_TYPE_PROGRESS = 333;
//
//    public EndlessListAdapter(List<Quote> dataList) {
//        super();
//    }
//
//    public boolean isAppending() {
//        return mIsAppending;
//    }
//
//    public void setIsAppending(boolean isAppending) {
//        mIsAppending = isAppending;
//    }
//
//    @Override
//    public int getItemCount() {
//        return isAppending() ?
//                getItemCount() + 1 : getItemCount();
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return (isAppending() && position >= getItemCount()) ?
//                VIEW_TYPE_PROGRESS : super.getItemViewType(position);
//    }
//
//    @Override
//    public final VH onCreateViewHolder(ViewGroup parent, int viewType) {
//        RecyclerView.ViewHolder vh;
//        if (viewType == VIEW_TYPE_PROGRESS) {
//            View v = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.layout_progress_bar, parent, false);
//
//            vh = new ProgressViewHolder(v);
//        } else {
//            vh = new (parent, viewType);
//        }
//
//        return (VH) vh;
//    }
//
//    @Override
//    public final void onBindViewHolder(VH holder, int position) {
//        if (holder instanceof ProgressViewHolder) {
//            // do nothing
//        } else {
//            super.onBindViewHolder(holder, position);
//        }
//    }
//
//    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
//        public ProgressViewHolder(View v) {
//            super(v);
//        }
//    }
//
//}
