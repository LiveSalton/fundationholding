package com.salton123.qa.kit;

import android.content.Context;
import android.view.View;

public interface IKit {
    int getCategory();

    View displayItem();

    void onAppInit(Context context);
}
