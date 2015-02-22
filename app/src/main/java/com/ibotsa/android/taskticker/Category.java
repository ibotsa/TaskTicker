package com.ibotsa.android.taskticker;

/**
 * Created by Tobi__000 on 22.02.2015.
 */
public class Category {

    private String mName;
    private int mIcon;
    private int mIconSelected;

    public Category(String name, int icon, int iconSelected) {
        mName = name;
        mIcon = icon;
        mIconSelected = iconSelected;
    }

    public String getName() {
        return mName;
    }

    public int getIcon() {
        return mIcon;
    }

    public int getIconSelected() {
        return mIconSelected;
    }
}

