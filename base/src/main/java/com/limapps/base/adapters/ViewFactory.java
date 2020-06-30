package com.limapps.base.adapters;

import android.view.ViewGroup;

import com.limapps.base.adapters.GenericAdapterRecyclerView;

public abstract class ViewFactory <T extends GenericAdapterRecyclerView.ItemView> {

    public abstract T getView(ViewGroup parent, int viewType);

}