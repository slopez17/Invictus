package co.com.uma.mseei.invictus;

import static android.Manifest.permission.ACTIVITY_RECOGNITION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static co.com.uma.mseei.invictus.R.string.hi;
import static co.com.uma.mseei.invictus.R.string.ok;
import static co.com.uma.mseei.invictus.R.string.permission;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import co.com.uma.mseei.invictus.databinding.ActivityMainBinding;
import co.com.uma.mseei.invictus.model.AppPreferences;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkPermissions();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_historical, R.id.navigation_profile, R.id.navigation_settings)
                .build();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    private void checkPermissions() {
        String[] permissions = { ACTIVITY_RECOGNITION };
        String[] permissionsToRequest = checkPermissionsToRequest(permissions);
        if (permissionsToRequest.length > 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle(this.getApplication().getString(hi))
                    .setMessage(this.getApplication().getString(permission))
                    .setPositiveButton(this.getApplication().getString(ok), (dialog, which) -> {
                        dialog.dismiss();
                        requestMultiplePermissions.launch(permissionsToRequest);
                    })
                    .create();
            alertDialog.show();
        }
    }

    private String[] checkPermissionsToRequest(String[] permissions) {
        List<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, permission)) {
                permissionsToRequest.add(permission);
            }
        }
        return permissionsToRequest.toArray(new String[0]);
    }

    private final ActivityResultLauncher<String[]> requestMultiplePermissions =
            registerForActivityResult(new RequestMultiplePermissions(), permissions -> {
                AppPreferences appPreferences = new AppPreferences(this.getApplication());
                appPreferences.setPermissionGranted(!permissions.containsValue(false));
            });
}