package com.example.keyvalueoreo.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.keyvalueoreo.R;

/**
 * A fragment with a single button to click.
 */
public class ButtonClickerFragment extends Fragment {

    private View root;

    public static ButtonClickerFragment newInstance() {
        ButtonClickerFragment fragment = new ButtonClickerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_button_clicker, container, false);
        Button button = root.findViewById(R.id.click_me_button);
        Context context = getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);
        int numClicks = sharedPreferences.getInt(getString(R.string.number_of_button_clicks_key), 0);
        TextView clickMeClicksTextView = root.findViewById(R.id.click_me_clicks_text);
        clickMeClicksTextView.setText(getString(R.string.num_button_clicks_text_view)+" "+Integer.toString(numClicks));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getActivity();
                SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();

                // Get and set the new number of clicks.
                int defaultValue = getResources().getInteger(R.integer.default_value);
                int numClicks = sharedPreferences.getInt(getString(R.string.number_of_button_clicks_key), defaultValue);
                sharedPrefEditor.putInt(getString(R.string.number_of_button_clicks_key), ++numClicks);
                sharedPrefEditor.commit();

                TextView clickMeClicksTextView = root.findViewById(R.id.click_me_clicks_text);
                clickMeClicksTextView.setText(getString(R.string.num_button_clicks_text_view)+" "+Integer.toString(numClicks));
            }
        });
        return root;
    }
}