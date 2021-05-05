//S1803445
//Andrew Minja
package org.me.gcu.ui.main;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bon.earthquake.R;
import com.bon.earthquake.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        ViewModelFactory factory = new ViewModelFactory(this);
        MainViewmodel viewmodel = ViewModelProviders.of(this, factory).get(MainViewmodel.class);

        // Navigation setup
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host);
        NavController controller = navHostFragment.getNavController();
        AppBarConfiguration configuration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.detailsFragment).build();
        NavigationUI.setupActionBarWithNavController(this, controller, configuration);

        viewmodel.updateData();
        viewmodel.getLocalData();


        // observe the error
        viewmodel.error.observe(this, error ->{
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
        setContentView(binding.getRoot());
    }

}
