package de.hx.bokIQTablleAltona;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import de.hx.bokIQTablleAltona.fleischActivity.FleischBestellungenActivity;
import de.hx.bokIQTablleAltona.getraenkeActivity.GetraenkeBestellungenActivity;
import de.hx.bokIQTablleAltona.util.DatePickerFragment;
import de.hx.bokIQTablleAltona.util.MonthPickerFragment;
import de.hx.bokIQTablleAltona.util.Utils;

public class MainActivity extends FragmentActivity implements OnClickListener {

    Button vonDatumBtn, bisDatumBtn, fleischBtn, getraenkeBtn,
            zwischenBerichtBtn, zwischenBerichtOKBtn, endBerichtBtn,
            monatEndberichtBtn, endBerichtOKBtn, bestellungsvorBtn;
    LinearLayout zwischenBerichtBereich, endBerichtBereich;
    TextView vonDatumTextView, bisDatumTextView, monatTextView;
    int vonDaysFrom1970, bisDaysFrom1970, monatForEndbericht,
            jahrForEndbericht;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        // isExternalStorageWritable();
        copyFileToExternalStorage("fleisch.xml");
        copyFileToExternalStorage("fleisch_bestellungen.xml");
        copyFileToExternalStorage("fleisch_einkauf_sammlung.xml");
        copyFileToExternalStorage("getraenke.xml");
        copyFileToExternalStorage("getraenke_bestellungen.xml");
        copyFileToExternalStorage("getraenke_einkauf_sammlung.xml");
        fleischBtn = (Button) findViewById(R.id.fleischBtn);
        getraenkeBtn = (Button) findViewById(R.id.getraenkeBtn);
        zwischenBerichtBtn = (Button) findViewById(R.id.zwischenBerichtBtn);
        zwischenBerichtBereich = (LinearLayout) findViewById(R.id.zwischenBerichtDatePicker);
        vonDatumTextView = (TextView) findViewById(R.id.vonDatumTextView);
        bisDatumTextView = (TextView) findViewById(R.id.bisDatumTextView);
        vonDatumBtn = (Button) findViewById(R.id.vonDatumBtn);
        bisDatumBtn = (Button) findViewById(R.id.bisDatumBtn);
        zwischenBerichtOKBtn = (Button) findViewById(R.id.gesamtZwischenBerichtOKBtn);
        endBerichtBtn = (Button) findViewById(R.id.gesamtEndberichtBtn);
        endBerichtOKBtn = (Button) findViewById(R.id.gesamtEBOKBtn);
        bestellungsvorBtn = (Button) findViewById(R.id.bestellungsvorschlageBtn);
        monatTextView = (TextView) findViewById(R.id.monatGesamtEBTextView);
        endBerichtBereich = (LinearLayout) findViewById(R.id.gesamtEndberichtBereich);
        monatEndberichtBtn = (Button) findViewById(R.id.monatGesamtEBBtn);
        fleischBtn.setOnClickListener(this);
        getraenkeBtn.setOnClickListener(this);
        zwischenBerichtBtn.setOnClickListener(this);
        vonDatumBtn.setOnClickListener(this);
        bisDatumBtn.setOnClickListener(this);
        zwischenBerichtOKBtn.setOnClickListener(this);
        endBerichtBtn.setOnClickListener(this);
        endBerichtOKBtn.setOnClickListener(this);
        monatEndberichtBtn.setOnClickListener(this);
        bestellungsvorBtn.setOnClickListener(this);
    }

    public void copyFileToExternalStorage(String filename) {
        File folder = new File(Environment.getExternalStorageDirectory()
                + "/BOK/Altona");
        if (!folder.exists())
            folder.mkdir();
        File file = new File(
                Environment.getExternalStorageDirectory() + "/BOK/Altona", filename);
        if (!file.exists()) {
            AssetManager assetManager = getResources().getAssets();
            InputStream is = null;
            try {
                is = assetManager.open(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
            OutputStream os = null;
            try {
                os = new FileOutputStream(file, true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            final int buffer_size = 1024 * 1024;
            try {
                byte[] bytes = new byte[buffer_size];
                for (; ; ) {
                    int count = is.read(bytes, 0, buffer_size);
                    if (count == -1)
                        break;
                    os.write(bytes, 0, count);
                }
                is.close();
                os.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.fleischBtn:
                intent = new Intent(MainActivity.this,
                        FleischBestellungenActivity.class);
                startActivity(intent);
                break;
            case R.id.getraenkeBtn:
                intent = new Intent(MainActivity.this,
                        GetraenkeBestellungenActivity.class);
                startActivity(intent);
                break;
            case R.id.zwischenBerichtBtn:
                endBerichtBereich.setVisibility(View.GONE);
                zwischenBerichtBereich.setVisibility(View.VISIBLE);
                break;
            case R.id.vonDatumBtn: {
                DialogFragment newFragment = new DatePickerFragment(
                        vonDatumTextView);
                newFragment.show(getSupportFragmentManager(), "fromDatePicker");
                break;
            }
            case R.id.bisDatumBtn: {
                DialogFragment newFragment = new DatePickerFragment(
                        bisDatumTextView);
                newFragment.show(getSupportFragmentManager(), "toDatePicker");
                break;
            }
            case R.id.gesamtZwischenBerichtOKBtn:
                if (getDaysFrom1970()) {
                    intent = new Intent(MainActivity.this,
                            GesamtZwischenBericht.class);
                    intent.putExtra("vonDaysFrom1970", vonDaysFrom1970);
                    intent.putExtra("bisDaysFrom1970", bisDaysFrom1970);
                    intent.putExtra("berichtTyp", "zwischenbericht");
                    startActivity(intent);
                }
                break;
            case R.id.gesamtEndberichtBtn:
                zwischenBerichtBereich.setVisibility(View.GONE);
                endBerichtBereich.setVisibility(View.VISIBLE);
                break;

            case R.id.monatGesamtEBBtn:
                DialogFragment newFragment = new MonthPickerFragment(monatTextView);
                newFragment.show(getSupportFragmentManager(), "monatPicker");
                break;
            case R.id.gesamtEBOKBtn:
                if (getMonatUndJahrForEinkaufBericht()) {
                    intent = new Intent(MainActivity.this,
                            GesamtZwischenBericht.class);
                    Utils util = new Utils();
                    intent.putExtra("vonDaysFrom1970", util
                            .getVonDaysFrom1970OfFirstDay(monatForEndbericht,
                                    jahrForEndbericht));
                    intent.putExtra("bisDaysFrom1970", util
                            .getVonDaysFrom1970OfLastDay(monatForEndbericht,
                                    jahrForEndbericht));
                    intent.putExtra("berichtTyp", "endbericht");
                    startActivity(intent);
                }
                break;
            case R.id.bestellungsvorschlageBtn:
                Intent bestVorIntent = new Intent(this, BestellungsvorschlageActivity.class);
                startActivity(bestVorIntent);
            default:
                break;
        }
    }

    boolean getDaysFrom1970() {
        if (vonDatumTextView.getText().toString().equals("")
                || bisDatumTextView.getText().toString().equals("")) {
            Toast.makeText(this, "Bitte von und bis Datum auswählen",
                    Toast.LENGTH_LONG).show();
            return false;
        } else {
            String von = vonDatumTextView.getText().toString();
            String bis = bisDatumTextView.getText().toString();
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Date d = null;
            Calendar calendar = Calendar.getInstance();
            try {
                d = format.parse(von);
                calendar.setTime(d);
                vonDaysFrom1970 = (int) (calendar.getTimeInMillis() / (1000 * 3600 * 24));
                d = format.parse(bis);
                calendar.setTime(d);
                bisDaysFrom1970 = (int) (calendar.getTimeInMillis() / (1000 * 3600 * 24));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            return true;
        }
    }

    boolean getMonatUndJahrForEinkaufBericht() {
        if (monatTextView.getText().toString().equals("")) {
            Toast.makeText(this, "Bitte Monat auswählen", Toast.LENGTH_LONG)
                    .show();
            return false;
        } else {
            String datum = monatTextView.getText().toString();
            SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy");
            Date d = null;
            Calendar calendar = Calendar.getInstance();
            try {
                d = format.parse(datum);
                calendar.setTime(d);
                monatForEndbericht = calendar.get(Calendar.MONTH) + 1;
                jahrForEndbericht = calendar.get(Calendar.YEAR);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            return true;
        }
    }
}
