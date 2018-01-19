package com.example.khalid.minaret.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.khalid.minaret.R;

import static com.example.khalid.minaret.MainActivity.title;
import static com.example.khalid.minaret.utils.Utils.get;
import static com.example.khalid.minaret.utils.Utils.save;

/**
 * Created by khalid on 1/16/2018.
 */

public class Settings extends Fragment implements View.OnClickListener {


    LinearLayout sound, light;
    TextView soundvalue, lightvalue;
    String chosenRingtone;
    CheckBox vibration;

    public static Settings newInstance() {
        Settings fragment = new Settings();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_settings, container, false);
        initializeViews(view);
        initializeClicks();
        initializeSettings();
        title.setText("الاعدادات");

        return view;
    }

    private void initializeSettings() {
        soundvalue.setText(get(getActivity(), "soundname"));

        if (get(getActivity(), "notification_light").equals("")) {
            save(getActivity(), "notification_light", "1");
        }
        if (get(getActivity(), "notification_vibration").equals("") || get(getActivity(), "notification_vibration").equals("yes")) {
            vibration.setChecked(true);
        } else
            vibration.setChecked(false);


        switch (get(getActivity(), "notification_light")) {

            case "1":
                lightvalue.setText(getString(R.string.red));
                break;
            case "2":
                lightvalue.setText(getString(R.string.white));

                break;
            case "3":
                lightvalue.setText(getString(R.string.green));

                break;
            case "4":
                lightvalue.setText(getString(R.string.yellow));

                break;
            case "5":
                lightvalue.setText(getString(R.string.blue));

                break;
            case "6":
                lightvalue.setText(getString(R.string.cyan));

                break;
        }


        vibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (vibration.isChecked()) {
                    save(getActivity(), "notification_vibration", "yes");
                } else {
                    save(getActivity(), "notification_vibration", "no");

                }
            }
        });
    }

    private void initializeViews(View view) {

        sound = view.findViewById(R.id.soundpopup);
        soundvalue = view.findViewById(R.id.soundvalue);
        light = view.findViewById(R.id.lightpopup);
        lightvalue = view.findViewById(R.id.lightvalue);
        vibration = view.findViewById(R.id.vibration);
    }

    private void initializeClicks() {
        sound.setOnClickListener(this);
        light.setOnClickListener(this);

    }


    private void showChangeLightDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.notification_light, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        final RadioGroup radioGroup = dialogView.findViewById(R.id.group);
        RadioButton red = dialogView.findViewById(R.id.red);
        RadioButton white = dialogView.findViewById(R.id.white);
        RadioButton blue = dialogView.findViewById(R.id.blue);
        RadioButton yellow = dialogView.findViewById(R.id.yellow);
        RadioButton cyan = dialogView.findViewById(R.id.cyan);
        RadioButton green = dialogView.findViewById(R.id.green);

        switch (get(getActivity(), "notification_light")) {

            case "1":
                red.setChecked(true);
                lightvalue.setText(getString(R.string.red));
                break;
            case "2":
                white.setChecked(true);
                lightvalue.setText(getString(R.string.white));

                break;
            case "3":
                green.setChecked(true);
                lightvalue.setText(getString(R.string.green));

                break;
            case "4":
                yellow.setChecked(true);
                lightvalue.setText(getString(R.string.yellow));

                break;
            case "5":
                blue.setChecked(true);
                lightvalue.setText(getString(R.string.blue));

                break;
            case "6":
                cyan.setChecked(true);
                lightvalue.setText(getString(R.string.cyan));

                break;
        }

        dialogBuilder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                switch (radioGroup.getCheckedRadioButtonId()) {

                    case R.id.red:
                        save(getActivity(), "notification_light", "1");
                        lightvalue.setText(getString(R.string.red));
                        break;
                    case R.id.white:
                        save(getActivity(), "notification_light", "2");
                        lightvalue.setText(getString(R.string.white));

                        break;
                    case R.id.green:
                        save(getActivity(), "notification_light", "3");
                        lightvalue.setText(getString(R.string.green));

                        break;
                    case R.id.yellow:
                        save(getActivity(), "notification_light", "4");
                        lightvalue.setText(getString(R.string.yellow));

                        break;
                    case R.id.blue:
                        save(getActivity(), "notification_light", "5");
                        lightvalue.setText(getString(R.string.blue));

                        break;
                    case R.id.cyan:
                        save(getActivity(), "notification_light", "6");
                        lightvalue.setText(getString(R.string.cyan));

                        break;

                }
            }
        });
        dialogBuilder.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        AlertDialog b = dialogBuilder.create();


        b.show();

    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        if (resultCode == Activity.RESULT_OK && requestCode == 5) {
            Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            if (uri != null) {
                this.chosenRingtone = uri.toString();
                Ringtone ringtone = RingtoneManager.getRingtone(getActivity(), uri);
                save(getActivity(), "soundname", ringtone.getTitle(getActivity()));
                save(getActivity(), "notification_sound", chosenRingtone);
                soundvalue.setText(get(getActivity(), "soundname"));

            } else {
                this.chosenRingtone = null;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.soundpopup:
                if (!get(getActivity(), "notification_sound").equals(null)) {
                    Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse(get(getActivity(), "notification_sound")));
                    startActivityForResult(intent, 5);

                } else {
                    Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "أختر نغمة");
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse("content://media/internal/audio/media/30\n"));
                    startActivityForResult(intent, 5);
                }
                break;
            case R.id.lightpopup:
                showChangeLightDialog();
                break;
        }
    }
}
