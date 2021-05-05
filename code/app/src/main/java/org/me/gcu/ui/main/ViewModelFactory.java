//S1803445
package org.me.gcu.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

// ViewModel Factory to instantiate our viewmodel
public class ViewModelFactory implements ViewModelProvider.Factory{
    private final Context context;

    public ViewModelFactory(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewmodel.class)) {
            return (T) new MainViewmodel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
