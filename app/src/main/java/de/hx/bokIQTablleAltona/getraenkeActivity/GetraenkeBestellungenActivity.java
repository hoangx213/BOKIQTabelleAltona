package de.hx.bokIQTablleAltona.getraenkeActivity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import de.hx.bokIQTablleAltona.MainActivity;
import de.hx.bokIQTablleAltona.R;
import de.hx.bokIQTablleAltona.models.getraenke.OneDayGetraenkeBestellungenModel;
import de.hx.bokIQTablleAltona.util.DatePickerFragment;
import de.hx.bokIQTablleAltona.util.MonthPickerFragment;
import de.hx.bokIQTablleAltona.util.Utils;
import de.hx.bokIQTablleAltona.xml.getraenke.GetraenkeBestellungenXmlParser;
import de.hx.bokIQTablleAltona.xml.getraenke.GetraenkeXmlParserHelper;

@SuppressLint("SimpleDateFormat")
public class GetraenkeBestellungenActivity extends FragmentActivity implements
        OnClickListener {

    static ArrayList<OneDayGetraenkeBestellungenModel> getraenkeBestelungenDaysList;
    ListView getraenkeBestellungenListView;
    Button vonDatumBtn, bisDatumBtn, addBestBtn, einkaufsberichtBtn,
            zwischenberichtBtn, endberichtBtn, zwischenBerichtOKBtn,
            einkaufBerichtOKBtn, monatEBBtn;
    TextView vonDatumTextView, bisDatumTextView, monatEBTextView;
    LinearLayout einkaufBerichtBereich, zwischenBerichtBereich;
    int vonDaysFrom1970, bisDaysFrom1970, monatForEinkaufBericht,
            jahrForEinkaufBericht;
    static GetraenkeXmlParserHelper xmlPH = new GetraenkeXmlParserHelper();
    static GetraenkeBestelungenListAdapter listViewAdapter;
    static int positionOfFBToRemove = 0;
    static int einkaufOderEndBericht = 0;
    static final int EINKAUFBERICHT = 1;
    static final int ENDBERICHT = 2;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getraenke_bestellungen);
        getraenkeBestellungenListView = (ListView) findViewById(R.id.getraenkeBestellungenListView);
        vonDatumBtn = (Button) findViewById(R.id.vonDatumFBBtn);
        bisDatumBtn = (Button) findViewById(R.id.bisDatumFBBtn);
        vonDatumTextView = (TextView) findViewById(R.id.vonDatumFBTextView);
        bisDatumTextView = (TextView) findViewById(R.id.bisDatumFBTextView);
        monatEBTextView = (TextView) findViewById(R.id.monatEBTextView);
        einkaufsberichtBtn = (Button) findViewById(R.id.einkaufsberichtBtn);
        zwischenberichtBtn = (Button) findViewById(R.id.zwischenberichtBtn);
        einkaufBerichtBereich = (LinearLayout) findViewById(R.id.einkaufBerichtDatePicker);
        zwischenBerichtOKBtn = (Button) findViewById(R.id.zwischenBerichtOKBtn);
        einkaufBerichtOKBtn = (Button) findViewById(R.id.einkaufBerichtOKBtn);
        zwischenBerichtBereich = (LinearLayout) findViewById(R.id.zwischenBerichtDatePicker);
        endberichtBtn = (Button) findViewById(R.id.endberichtBtn);
        monatEBBtn = (Button) findViewById(R.id.monatEBBtn);
        GetraenkeBestellungenXmlParser fbxp = new GetraenkeBestellungenXmlParser(
                this);
        try {
            getraenkeBestelungenDaysList = fbxp.getraenkeParsenSimple();
            Collections.sort(getraenkeBestelungenDaysList,
                    getraenkeBestellungenComparator);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        View footerView = ((LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.getraenke_neue_bestellung_button, null, false);
        getraenkeBestellungenListView.addFooterView(footerView);
        addBestBtn = (Button) footerView.findViewById(R.id.neueFBestellungBtn);
        addBestBtn.setOnClickListener(this);
        vonDatumBtn.setOnClickListener(this);
        bisDatumBtn.setOnClickListener(this);
        einkaufsberichtBtn.setOnClickListener(this);
        zwischenberichtBtn.setOnClickListener(this);
        einkaufBerichtOKBtn.setOnClickListener(this);
        zwischenBerichtOKBtn.setOnClickListener(this);
        monatEBBtn.setOnClickListener(this);
        endberichtBtn.setOnClickListener(this);
        listViewAdapter = new GetraenkeBestelungenListAdapter(this,
                getraenkeBestelungenDaysList);
        getraenkeBestellungenListView.setAdapter(listViewAdapter);
        getraenkeBestellungenListView
                .setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        OneDayGetraenkeBestellungenModel thisDayGetraenkeBestellungen = getraenkeBestelungenDaysList
                                .get(position);
                        String bestellungID = thisDayGetraenkeBestellungen
                                .getBestellungID();
                        Intent intent = new Intent(
                                GetraenkeBestellungenActivity.this,
                                GetraenkeBestellungNachschauActivity.class);
                        intent.putExtra("BestellungID", bestellungID);
                        startActivity(intent);
                    }
                });
        getraenkeBestellungenListView
                .setOnItemLongClickListener(new OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view, int position, long id) {
                        positionOfFBToRemove = position;
                        new RemoveOneDayFBDialogFragment().show(
                                getSupportFragmentManager(), "removeFBDialog");
                        return true;
                    }
                });
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Getraenkebestellungen");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        startActivity(new Intent(this, MainActivity.class));
        return true;
    }

    class GetraenkeBestelungenListAdapter extends
            ArrayAdapter<OneDayGetraenkeBestellungenModel> {

        ArrayList<OneDayGetraenkeBestellungenModel> adapterGetraenkeBestelungenDaysList;
        Context context;

        public GetraenkeBestelungenListAdapter(
                Context context,
                ArrayList<OneDayGetraenkeBestellungenModel> adapterGetraenkeBestelungenDaysList) {
            super(context, R.layout.getraenke_bestellungen_list_item,
                    R.id.vonDatumFBTextView, getraenkeBestelungenDaysList);
            this.adapterGetraenkeBestelungenDaysList = adapterGetraenkeBestelungenDaysList;
            this.context = context;
        }

        class ViewHolder {
            TextView dayTextView;

            ViewHolder(View base) {
                this.dayTextView = (TextView) base
                        .findViewById(R.id.vonDatumFBTextView);
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (GetraenkeBestellungenActivity.this
                        .getLayoutInflater());
                convertView = inflater.inflate(
                        R.layout.getraenke_bestellungen_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            OneDayGetraenkeBestellungenModel thisDay = getraenkeBestelungenDaysList
                    .get(position);
            holder.dayTextView.setText(thisDay.toString());
            return convertView;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.neueFBestellungBtn: {
                Intent intent = new Intent(this, GetraenkeActivity.class);
                startActivityForResult(intent, 1);
                break;
            }
            case R.id.vonDatumFBBtn: {
                DialogFragment newFragment = new DatePickerFragment(
                        vonDatumTextView);
                newFragment.show(getSupportFragmentManager(), "fromDatePicker");
                break;
            }
            case R.id.bisDatumFBBtn: {
                DialogFragment newFragment = new DatePickerFragment(
                        bisDatumTextView);
                newFragment.show(getSupportFragmentManager(), "toDatePicker");
                break;
            }
            case R.id.einkaufsberichtBtn: {
                zwischenBerichtBereich.setVisibility(View.GONE);
                einkaufBerichtBereich.setVisibility(View.VISIBLE);
                einkaufOderEndBericht = EINKAUFBERICHT;
                break;
            }
            case R.id.zwischenberichtBtn: {
                einkaufBerichtBereich.setVisibility(View.GONE);
                zwischenBerichtBereich.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.zwischenBerichtOKBtn: {
                if (getDaysFrom1970()) {
                    Intent intent = new Intent(this,
                            GetraenkeZwischenBerichtActivity.class);
                    intent.putExtra("vonDaysFrom1970", vonDaysFrom1970);
                    intent.putExtra("bisDaysFrom1970", bisDaysFrom1970);
                    intent.putExtra("berichtTyp", "zwischenbericht");
                    startActivity(intent);
                }
                break;
            }
            case R.id.monatEBBtn: {
                DialogFragment newFragment = new MonthPickerFragment(
                        monatEBTextView);
                newFragment.show(getSupportFragmentManager(), "monatPicker");
                break;
            }
            case R.id.einkaufBerichtOKBtn:
                if (getMonatUndJahrForEinkaufBericht()) {
                    switch (einkaufOderEndBericht) {
                        case EINKAUFBERICHT:
                            Intent intentEinkauf = new Intent(this,
                                    GetraenkeEinkaufBerichtActivity.class);
                            intentEinkauf.putExtra("monat", monatForEinkaufBericht);
                            intentEinkauf.putExtra("jahr", jahrForEinkaufBericht);
                            startActivity(intentEinkauf);
                            break;
                        case ENDBERICHT:
                            Intent intentEnd = new Intent(this,
                                    GetraenkeZwischenBerichtActivity.class);
                            Utils util = new Utils();
                            intentEnd.putExtra("vonDaysFrom1970", util
                                    .getVonDaysFrom1970OfFirstDay(
                                            monatForEinkaufBericht,
                                            jahrForEinkaufBericht));
                            intentEnd.putExtra("bisDaysFrom1970", util
                                    .getVonDaysFrom1970OfLastDay(
                                            monatForEinkaufBericht,
                                            jahrForEinkaufBericht));
                            intentEnd.putExtra("berichtTyp", "endbericht");
                            startActivity(intentEnd);
                            break;
                    }
                }
                break;
            case R.id.endberichtBtn:
                zwischenBerichtBereich.setVisibility(View.GONE);
                einkaufBerichtBereich.setVisibility(View.VISIBLE);
                einkaufOderEndBericht = ENDBERICHT;
                break;
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
        if (monatEBTextView.getText().toString().equals("")) {
            Toast.makeText(this, "Bitte Monat auswählen", Toast.LENGTH_LONG)
                    .show();
            return false;
        } else {
            String datum = monatEBTextView.getText().toString();
            SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy");
            Date d = null;
            Calendar calendar = Calendar.getInstance();
            try {
                d = format.parse(datum);
                calendar.setTime(d);
                monatForEinkaufBericht = calendar.get(Calendar.MONTH) + 1;
                jahrForEinkaufBericht = calendar.get(Calendar.YEAR);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            return true;
        }
    }

    public static class RemoveOneDayFBDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Wollen Sie den Bestellungstag entfernen?")
                    .setPositiveButton("Ja",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    OneDayGetraenkeBestellungenModel thisDayGetraenkeBestellung = getraenkeBestelungenDaysList
                                            .get(positionOfFBToRemove);
                                    try {
                                        xmlPH.removeOneDayGBWithBestellungID(thisDayGetraenkeBestellung
                                                .getBestellungID());
                                    } catch (ParserConfigurationException e) {
                                        e.printStackTrace();
                                    } catch (SAXException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (TransformerException e) {
                                        e.printStackTrace();
                                    }
                                    getraenkeBestelungenDaysList
                                            .remove(positionOfFBToRemove);
                                    listViewAdapter.notifyDataSetChanged();
                                }
                            }
                    )
                    .setNegativeButton("Nein",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    // User cancelled the dialog
                                }
                            }
                    );
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    Comparator<OneDayGetraenkeBestellungenModel> getraenkeBestellungenComparator = new Comparator<OneDayGetraenkeBestellungenModel>() {

        @Override
        public int compare(OneDayGetraenkeBestellungenModel lhs,
                           OneDayGetraenkeBestellungenModel rhs) {
            return Integer.valueOf(lhs.getDaysFrom1970()).compareTo(
                    Integer.valueOf(rhs.getDaysFrom1970()));
        }

    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                getraenkeBestelungenDaysList
                        .add(new OneDayGetraenkeBestellungenModel(data
                                .getStringExtra("bestellungID"), data
                                .getStringExtra("datum"), data.getIntExtra("daysFrom1970", 0)));
                listViewAdapter.notifyDataSetChanged();
            }
            if (resultCode == RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }

}
