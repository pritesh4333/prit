package com.acumengroup.mobile.model;

import com.acumengroup.mobile.R;

import java.util.ArrayList;
import java.util.List;

public class Category {
    public long id;
    public int iconRes;
    public String category;

    public Category(long id, int iconRes, String category){
        super();
        this.id = id;
        this.iconRes = iconRes;
        this.category = category;
    }

    public static List generateCategoryList(){
        List categories = new ArrayList<>();
        String[] programming = {"C++", "JAVA", "JAVASCRIPT", "C#", "Objective C", "SWIFT",
                "C++", "JAVA", "JAVASCRIPT", "C#", "Objective C", "SWIFT"};

        for(int i = 0; i < programming.length; i++){
            categories.add(new Category(i, R.drawable.icon_finder, programming[i]));
        }
        return categories;
    }
}