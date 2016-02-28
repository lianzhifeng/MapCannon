
package com.empty.mapcannon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

public class ProvinceAdapter extends AbstractWheelTextAdapter {
    private String[] countries = {
            "安徽省", "北京市", "福建省", "甘肃省", "广东省", "广西壮族自治区", "贵州省", "海南省", "河北省", "河南省", "黑龙江省", "湖北省",
            "湖南省", "吉林省", "江苏省", "江西省", "辽宁省", "内蒙古自治区", "宁夏回族自治区", "青海省", "山东省", "山西省", "陕西省",
            "上海市", "四川省", "天津市", "西藏自治区", "新疆自治区", "云南省", "浙江省", "重庆市"
    };

    public ProvinceAdapter(Context paramContext) {
        super(paramContext, 2130968714, 0);
        setItemTextResource(2131624638);
    }

    public View getItem(int paramInt, View paramView, ViewGroup paramViewGroup) {
        return super.getItem(paramInt, paramView, paramViewGroup);
    }

    protected CharSequence getItemText(int paramInt) {
        return this.countries[paramInt];
    }

    public int getItemsCount() {
        return this.countries.length;
    }
}
