package com.acumengroup.mobile.tablefixheader.adapters;

import com.acumengroup.mobile.tablefixheader.custom.CustomDataSetObservable;
import com.acumengroup.mobile.tablefixheader.custom.CustomDataSetObserver;

/**
 * Created by Arcadia
 */

public abstract class BaseTableAdapter implements TableAdapter {
	private final CustomDataSetObservable mDataSetObservable = new CustomDataSetObservable();

	@Override
	public void registerDataSetObserver(CustomDataSetObserver observer) {
		mDataSetObservable.registerObserver(observer);
	}

	@Override
	public void unregisterDataSetObserver(CustomDataSetObserver observer) {
		mDataSetObservable.unregisterObserver(observer);
	}



	/**
	 * Notifies the attached observers that the underlying data has been changed
	 * and any View reflecting the data set should refresh itself.
	 */
	public void notifyDataSetChanged() {
		mDataSetObservable.notifyChanged();
	}

	public void notifyItemChanged(int row) {
		mDataSetObservable.notifyChangedAt(row);
	}

	public void updateList(){
		notifyDataSetChanged();
	}
	/**
	 * Notifies the attached observers that the underlying data is no longer
	 * valid or available. Once invoked this adapter is no longer valid and
	 * should not report further data set changes.
	 */
	public void notifyDataSetInvalidated() {
		mDataSetObservable.notifyInvalidated();
	}
}
