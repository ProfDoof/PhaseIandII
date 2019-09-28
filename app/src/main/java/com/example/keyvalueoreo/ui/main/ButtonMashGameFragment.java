package com.example.keyvalueoreo.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.keyvalueoreo.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ButtonMashGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ButtonMashGameFragment extends Fragment {

    private View root;
    private boolean timerStarted = false;
    private TextView timerTextView;
    private TextView highScoreTextView;
    private TextView currentScoreTextView;
    private int timeLeft = 0;

    public ButtonMashGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ButtonMashGameFragment.
     */
    public static ButtonMashGameFragment newInstance() {
        ButtonMashGameFragment fragment = new ButtonMashGameFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_button_mash_game, container, false);
        Button button = root.findViewById(R.id.button_mash_button);
        Context context = getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);
        int highScoreValue = sharedPreferences.getInt(getString(R.string.button_mash_high_score_key), 0);
        highScoreTextView = root.findViewById(R.id.button_mash_high_score);
        timerTextView = root.findViewById(R.id.button_mash_timer);
        currentScoreTextView = root.findViewById(R.id.button_mash_current_score);

        highScoreTextView.setText(getString(R.string.button_mash_high_score_text_view)+" "+Integer.toString(highScoreValue));
        timerTextView.setText(getString(R.string.timer_text_view_default)+" "+Integer.toString(timeLeft));
        currentScoreTextView.setText(getString(R.string.button_mash_current_score_text_view)+" "+Integer.toString(0));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClick();
            }
        });
        return root;
    }

    private void buttonClick() {
        int thirtySeconds = 30000;
        int oneSecond = 1000;
        Context context = getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();

        if (!timerStarted)
        {
            timeLeft = 30;
            new CountDownTimer(thirtySeconds, oneSecond) {
                public void onTick(long miilisUntilFinished) {
                    timerTextView.setText(getString(R.string.timer_text_view_default)+" "+Integer.toString(timeLeft--));
                }
                public void onFinish() {
                    timerTextView.setText(getString(R.string.timer_text_view_default)+" "+Integer.toString(timeLeft));
                    checkForNewHighScore();
                }
            }.start();
            timerStarted = true;
        }

        // Get and set the new number of clicks for this round.
        int defaultValue = getResources().getInteger(R.integer.default_value);
        int numClicks = sharedPreferences.getInt(getString(R.string.button_mash_current_score_key), defaultValue);
        sharedPrefEditor.putInt(getString(R.string.button_mash_current_score_key), ++numClicks);
        sharedPrefEditor.commit();

        // Update Current Click counter
        currentScoreTextView.setText(getString(R.string.button_mash_current_score_text_view)+" "+Integer.toString(numClicks));
    }

    private void checkForNewHighScore() {
        Context context = getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();

        // Get and set the new number of clicks.
        int defaultValue = getResources().getInteger(R.integer.default_value);
        int numClicks = sharedPreferences.getInt(getString(R.string.button_mash_current_score_key), defaultValue);
        int highScoreValue = sharedPreferences.getInt(getString(R.string.button_mash_high_score_key), defaultValue);

        if (numClicks > highScoreValue) {
            sharedPrefEditor.putInt(getString(R.string.button_mash_high_score_key), numClicks);

            highScoreTextView.setText(getString(R.string.button_mash_high_score_text_view)+" "+Integer.toString(numClicks));
        }

        sharedPrefEditor.putInt(getString(R.string.button_mash_current_score_key), defaultValue);
        sharedPrefEditor.commit();
        timerStarted = false;
    }
}
