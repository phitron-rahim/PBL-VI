package com.raisu.pblvi;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.raisu.pblvi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "HomeAutomationPrefs";
    private ActivityMainBinding binding;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Setup all controls and restore their states
        setupControls();
    }

    private void setupControls() {
        // Living Room
        setupSwitch(binding.livingLight1, "Living Room Light 1", "living_light1");
        setupSwitch(binding.livingLight2, "Living Room Light 2", "living_light2");
        setupSwitch(binding.livingFan1, "Living Room Fan 1", "living_fan1");
        setupSwitch(binding.livingFan2, "Living Room Fan 2", "living_fan2");
        setupSwitch(binding.livingAc, "Living Room AC", "living_ac");
        setupSwitch(binding.livingTv, "Living Room TV", "living_tv");
        setupRadio(binding.livingDoor, "Living Room", "living_door");

        // Bedroom
        setupSwitch(binding.bedroomLight1, "Bedroom Light 1", "bedroom_light1");
        setupSwitch(binding.bedroomLight2, "Bedroom Light 2", "bedroom_light2");
        setupSwitch(binding.bedroomFan1, "Bedroom Fan 1", "bedroom_fan1");
        setupSwitch(binding.bedroomFan2, "Bedroom Fan 2", "bedroom_fan2");
        setupSwitch(binding.bedroomAc, "Bedroom AC", "bedroom_ac");
        setupRadio(binding.bedroomDoor, "Bedroom", "bedroom_door");

        // Kitchen
        setupSwitch(binding.kitchenLight1, "Kitchen Light 1", "kitchen_light1");
        setupSwitch(binding.kitchenLight2, "Kitchen Light 2", "kitchen_light2");
        setupSwitch(binding.kitchenFan1, "Kitchen Fan 1", "kitchen_fan1");
        setupSwitch(binding.kitchenFan2, "Kitchen Fan 2", "kitchen_fan2");
        setupSwitch(binding.kitchenAc, "Kitchen AC", "kitchen_ac");
        setupRadio(binding.kitchenDoor, "Kitchen", "kitchen_door");

        // Guest Room
        setupSwitch(binding.guestLight1, "Guest Room Light 1", "guest_light1");
        setupSwitch(binding.guestFan1, "Guest Room Fan 1", "guest_fan1");
        setupSwitch(binding.guestAc, "Guest Room AC", "guest_ac");
        setupRadio(binding.guestDoor, "Guest Room", "guest_door");

        // Office
        setupSwitch(binding.officeLight1, "Office Light 1", "office_light1");
        setupSwitch(binding.officeFan1, "Office Fan 1", "office_fan1");
        setupSwitch(binding.officeAc, "Office AC", "office_ac");
        setupRadio(binding.officeDoor, "Office", "office_door");
    }

    private void setupSwitch(CompoundButton switchBtn, String name, String prefKey) {
        // Restore saved state
        boolean savedState = sharedPreferences.getBoolean(prefKey, false);
        switchBtn.setChecked(savedState);

        // Set up listener to save state when changed
        switchBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save state
            sharedPreferences.edit().putBoolean(prefKey, isChecked).apply();

            // Show toast
            String state = isChecked ? "ON" : "OFF";
            Toast.makeText(this, name + " is " + state, Toast.LENGTH_SHORT).show();
        });
    }

    private void setupRadio(RadioGroup radioGroup, String room, String prefKey) {
        // Restore saved selection
        int savedSelection = sharedPreferences.getInt(prefKey, -1);
        if (savedSelection != -1) {
            RadioButton radioButton = findViewById(savedSelection);
            if (radioButton != null) {
                radioButton.setChecked(true);
            }
        }

        // Set up listener to save selection when changed
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // Save selection
            sharedPreferences.edit().putInt(prefKey, checkedId).apply();

            // Show toast
            String status = "";
            if (checkedId != -1) {
                status = ((RadioButton)findViewById(checkedId)).getText().toString();
            }
            Toast.makeText(this, room + " Door is " + status, Toast.LENGTH_SHORT).show();
        });
    }
}