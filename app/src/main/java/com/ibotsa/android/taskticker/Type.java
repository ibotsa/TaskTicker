package com.ibotsa.android.taskticker;

/**
 * Created by Tobi__000 on 22.02.2015.
 */
public class Type {

    private Category mCategory;
    private String mName;
    private int mIcon;

    public Type(Category category, String name, int icon) {
        mCategory = category;
        mName = name;
        mIcon = icon;
    }

    public Category getCategory() {
        return mCategory;
    }

    public String getName() {
        return mName;
    }

    public int getIcon() {
        return mIcon;
    }
}
