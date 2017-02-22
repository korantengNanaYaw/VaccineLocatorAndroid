package com.health.vaccinefinder.Utilities;

/**
 * Created by smsgh on 22/02/2017.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.Window;
import android.widget.EditText;

import com.health.vaccinefinder.R;


public class Dialogs {

    Activity context;

    public Dialogs(Activity ctx){

        this.context=ctx;

    }

    public AlertDialog ThreeButtonAlertDialog(String message, String title){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        // alertDialogBuilder.
        // alertDialogBuilder.setTitle(title);

        // set dialog message


        alertDialogBuilder
                .setTitle(title)
                .setIcon(R.drawable.ic_launcher)
                .setMessage(message)
                .setCancelable(true)
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        //
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


        return alertDialog;

    }
    public AlertDialog SimpleWarningAlertDialog(String message, String title) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        // alertDialogBuilder.
        // alertDialogBuilder.setTitle(title);

        // set dialog message


        alertDialogBuilder
                .setTitle(title)
                .setIcon(R.drawable.ic_launcher)
                .setMessage(message)
                .setCancelable(true)
                .setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


        return alertDialog;

    }

    public void inputDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(
                context);
        // Set up the input
        final EditText input = new EditText(context);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("Verify", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //m_Text = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }
}
