package de.hx.bokIQTablleAltona;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import de.hx.bokIQTablleAltona.fleischActivity.FleischZwischenBerichtActivity;
import de.hx.bokIQTablleAltona.getraenkeActivity.GetraenkeZwischenBerichtActivity;
import de.hx.bokIQTablleAltona.models.ArtikelBerichtModel;
import de.hx.bokIQTablleAltona.models.fleisch.FleischBestellungModel;
import de.hx.bokIQTablleAltona.models.fleisch.OneDayFleischBestellungenModel;
import de.hx.bokIQTablleAltona.models.getraenke.GetraenkeBestellungModel;
import de.hx.bokIQTablleAltona.models.getraenke.OneDayGetraenkeBestellungenModel;
import de.hx.bokIQTablleAltona.util.Utils;
import de.hx.bokIQTablleAltona.xml.GemeinsameAnteilXmlWriter;
import de.hx.bokIQTablleAltona.xml.fleisch.FleischXmlParserHelper;
import de.hx.bokIQTablleAltona.xml.getraenke.GetraenkeXmlParserHelper;

public class GesamtZwischenBericht extends FragmentActivity implements OnClickListener {

    private static final int ANTEILID = 1123424556;
    private static final int ARTIKELNAMEID = 221554123;
    TableLayout table;
    TextView nettoUmsatzTotalTextView, fleischNettoUmsatzTextView, getraenkeNettoUmsatzTextView;
    TextView nettoEinkaufTotalTextView, fleischNettoEinkaufTextView, getraenkeNettoEinkaufTextView;
    TextView getraenkeNettoUmsatzAnteilTextView, fleischNettoUmsatzAnteilTextView;
    TextView fleischEinsatzView, getraenkeEinsatzView, totalEinsatzView;
    Button diagrammBtn;
    Button fleischDetailBtn, getraenkeDetailBtn;
    DecimalFormat df = new DecimalFormat("#.##");
    DecimalFormat prozentZahl = new DecimalFormat("#.#");
    FleischXmlParserHelper xmlFPH;
    ArrayList<OneDayFleischBestellungenModel> daysFBList = new ArrayList<OneDayFleischBestellungenModel>();
    ArrayList<ArtikelBerichtModel> fleischBerichtList = new ArrayList<ArtikelBerichtModel>();
    double portionFTotal = 0, nettoFEinkaufTotal = 0, nettoFUmsatzTotal = 0;
    String berichtTyp = "";

    GetraenkeXmlParserHelper xmlGPH;
    ArrayList<OneDayGetraenkeBestellungenModel> daysGBList = new ArrayList<OneDayGetraenkeBestellungenModel>();
    ArrayList<ArtikelBerichtModel> getraenkeBerichtList = new ArrayList<ArtikelBerichtModel>();
    double portionGTotal = 0, nettoGEinkaufTotal = 0, nettoGUmsatzTotal = 0;

    double portionTotal = 0, nettoEinkaufTotal = 0, nettoUmsatzTotal = 0;
    double fleischNettoUmsatzAnteil = 0, getraenkeNettoUmsatzAnteil = 0;
    int vonDaysFrom1970, bisDaysFrom1970;

    Utils utils = new Utils();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesamt_zwischenbericht);

        table = (TableLayout) findViewById(R.id.tableLayout2);
        nettoEinkaufTotalTextView = (TextView) findViewById(R.id.totalNettoEinkauf);
        nettoUmsatzTotalTextView = (TextView) findViewById(R.id.totalNettoUmsatz);
        fleischNettoEinkaufTextView = (TextView) findViewById(R.id.fleischNettoEinkauf);
        fleischNettoUmsatzTextView = (TextView) findViewById(R.id.fleischNettoUmsatz);
        getraenkeNettoEinkaufTextView = (TextView) findViewById(R.id.getraenkeNettoEinkauf);
        getraenkeNettoUmsatzTextView = (TextView) findViewById(R.id.getraenkeNettoUmsatz);
        fleischNettoUmsatzAnteilTextView = (TextView) findViewById(R.id.fleischNettoUmsatzAnteil);
        getraenkeNettoUmsatzAnteilTextView = (TextView) findViewById(R.id.getraenkeNettoUmsatzAnteil);
        fleischEinsatzView = (TextView) findViewById(R.id.fleischEinsatz);
        getraenkeEinsatzView = (TextView) findViewById(R.id.getraenkeEinsatz);
        totalEinsatzView = (TextView) findViewById(R.id.totalEinsatz);
        fleischDetailBtn = (Button) findViewById(R.id.fleischDetailBtn);
        getraenkeDetailBtn = (Button) findViewById(R.id.getraenkeDetailBtn);
        diagrammBtn = (Button) findViewById(R.id.diagrammBtn);
        diagrammBtn.setOnClickListener(this);
        fleischDetailBtn.setOnClickListener(this);
        getraenkeDetailBtn.setOnClickListener(this);

        Intent intent = getIntent();
        vonDaysFrom1970 = intent.getIntExtra("vonDaysFrom1970", 0);
        bisDaysFrom1970 = intent.getIntExtra("bisDaysFrom1970", 0);
        berichtTyp = intent.getStringExtra("berichtTyp");

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis((long) (vonDaysFrom1970 + 1) * (24 * 3600 * 1000));
        String vonDay = (DateFormat.format("dd.MMMM.yyyy ", c.getTime())).toString();
        c.setTimeInMillis((long) (bisDaysFrom1970 + 1) * (24 * 3600 * 1000));
        String bisDay = (DateFormat.format("dd.MMMM.yyyy ", c.getTime())).toString();
        String monat = (DateFormat.format("MMMM.yyyy ", c.getTime())).toString();
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (berichtTyp.equals("endbericht"))
            actionBar.setTitle("Gesamtbericht von " + monat);
        else
            actionBar.setTitle("Gesamtzwischenbericht von " + vonDay + " bis " + bisDay);

        getFBerichtList();
        getGBerichtList();

        nettoEinkaufTotal = nettoFEinkaufTotal + nettoGEinkaufTotal;
        nettoUmsatzTotal = nettoFUmsatzTotal + nettoGUmsatzTotal;
        portionTotal = portionFTotal + portionGTotal;

        renderFBerichtTablle();
        renderGBerichtTablle();

        nettoUmsatzTotalTextView.setText(df.format(nettoUmsatzTotal) + "€");
        nettoEinkaufTotalTextView.setText(df.format(nettoEinkaufTotal) + "€");
        totalEinsatzView.setText(df.format(100 * nettoEinkaufTotal / nettoUmsatzTotal) + "%");

        fleischNettoUmsatzAnteil = nettoFUmsatzTotal / nettoUmsatzTotal;
        String gerundeteAnteil = prozentZahl
                .format(fleischNettoUmsatzAnteil * 100);
        fleischNettoUmsatzAnteilTextView.setText(gerundeteAnteil + "%");

        getraenkeNettoUmsatzAnteil = nettoGUmsatzTotal / nettoUmsatzTotal;
        gerundeteAnteil = prozentZahl
                .format(getraenkeNettoUmsatzAnteil * 100);
        getraenkeNettoUmsatzAnteilTextView.setText(gerundeteAnteil + "%");

        if (berichtTyp.equals("endbericht")){
            renderSaveAnteilButton();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        finish();
        return true;
    }

    void getFBerichtList() {
        xmlFPH = new FleischXmlParserHelper();

        try {
            daysFBList = xmlFPH.getDaysFleischBestellungen(vonDaysFrom1970,
                    bisDaysFrom1970);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (OneDayFleischBestellungenModel thisOneDayFB : daysFBList) {
            nettoFEinkaufTotal += thisOneDayFB.getEinkaufssumme();
            nettoFUmsatzTotal += thisOneDayFB.getNettoUmsatzssumme();
            portionFTotal += thisOneDayFB.getPortionSumme();
            for (FleischBestellungModel thisFB : thisOneDayFB
                    .getFleischbestellungen()) {
                String thisArtikelName = thisFB.getFleischModel()
                        .getArtikelName();
                String thisKategorie = thisFB.getFleischModel().getKategorie();
                int thisOrder = thisFB.getFleischModel().getOrder();
                ArtikelBerichtModel thisFAB = utils
                        .getABWithArtikelNameFromList(fleischBerichtList,
                                thisArtikelName);
                if (thisFAB == null) {
                    ArtikelBerichtModel newFAB = new ArtikelBerichtModel(
                            thisArtikelName, thisKategorie, thisOrder, thisFB.getTotal(),
                            thisFB.getPortion(), thisFB.getNettoEinkauf(),
                            thisFB.getBruttoEinkauf(), thisFB.getNettoUmsatz(),
                            thisFB.getBruttoUmsatz());
                    fleischBerichtList.add(newFAB);
                } else {
                    thisFAB.addBruttoEinkauf(thisFB.getBruttoEinkauf());
                    thisFAB.addBruttoUmsatz(thisFB.getBruttoUmsatz());
                    thisFAB.addNettoEinkauf(thisFB.getNettoEinkauf());
                    thisFAB.addNettoUmsatz(thisFB.getNettoUmsatz());
                    thisFAB.addTotal(thisFB.getTotal());
                    thisFAB.addPortion(thisFB.getPortion());
                }
            }
        }
    }

    void getGBerichtList() {
        xmlGPH = new GetraenkeXmlParserHelper();

        try {
            daysGBList = xmlGPH.getDaysGetraenkeBestellungen(vonDaysFrom1970,
                    bisDaysFrom1970);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (OneDayGetraenkeBestellungenModel thisOneDayFB : daysGBList) {
            nettoGEinkaufTotal += thisOneDayFB.getEinkaufssumme();
            nettoGUmsatzTotal += thisOneDayFB.getNettoUmsatzssumme();
            portionGTotal += thisOneDayFB.getPortionSumme();
            for (GetraenkeBestellungModel thisFB : thisOneDayFB
                    .getGetraenkebestellungen()) {
                String thisArtikelName = thisFB.getGetraenkeModel()
                        .getArtikelName();
                ArtikelBerichtModel thisFAB = utils
                        .getABWithArtikelNameFromList(getraenkeBerichtList,
                                thisArtikelName);
                if (thisFAB == null) {
                    ArtikelBerichtModel newFAB = new ArtikelBerichtModel(
                            thisArtikelName, thisFB.getGetraenkeModel().getKategorie(),
                            thisFB.getGetraenkeModel().getOrder(), thisFB.getTotal(),
                            thisFB.getPortion(), thisFB.getNettoEinkauf(),
                            thisFB.getBruttoEinkauf(), thisFB.getNettoUmsatz(),
                            thisFB.getBruttoUmsatz());
                    getraenkeBerichtList.add(newFAB);
                } else {
                    thisFAB.addBruttoEinkauf(thisFB.getBruttoEinkauf());
                    thisFAB.addBruttoUmsatz(thisFB.getBruttoUmsatz());
                    thisFAB.addNettoEinkauf(thisFB.getNettoEinkauf());
                    thisFAB.addNettoUmsatz(thisFB.getNettoUmsatz());
                    thisFAB.addTotal(thisFB.getTotal());
                    thisFAB.addPortion(thisFB.getPortion());
                }
            }
        }
    }

    void renderFBerichtTablle() {
        for (ArtikelBerichtModel i : fleischBerichtList) {
            TableRow tr = new TableRow(this);
            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            tableRowParams.setMargins(2, 2, 2, 2);

            tr.setLayoutParams(tableRowParams);
            tr.setBackgroundResource(R.color.Brown);

            TextView artikelName = new TextView(this);
            artikelName.setId(ARTIKELNAMEID);
            artikelName.setText(i.getArtikelName());
            artikelName.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            artikelName.setBackgroundResource(R.color.White);
            tr.addView(artikelName);

            TextView total = new TextView(this);
            total.setText(String.valueOf(i.getTotal()));
            total.setTextAppearance(this, android.R.style.TextAppearance_Large);
            total.setBackgroundResource(R.color.White);
            tr.addView(total);

            TextView portionen = new TextView(this);
            portionen.setText(df.format(i.getPortion()));
            portionen.setTextAppearance(this, android.R.style.TextAppearance_Large);
            portionen.setBackgroundResource(R.color.White);
            tr.addView(portionen);

            TextView portionenAnteil = new TextView(this);
            portionenAnteil.setText(prozentZahl.format(100 * (i.getPortion() / portionTotal)) + "%");
            portionenAnteil.setTextAppearance(this, android.R.style.TextAppearance_Large);
            portionenAnteil.setBackgroundResource(R.color.White);
            tr.addView(portionenAnteil);

            TextView nettoEinkauf = new TextView(this);
            nettoEinkauf.setText(df.format(i.getNettoEinkauf()) + "€");
            nettoEinkauf.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            nettoEinkauf.setBackgroundResource(R.color.White);
            tr.addView(nettoEinkauf);

            TextView bruttoEinkauf = new TextView(this);
            bruttoEinkauf.setText(df.format(i.getBruttoEinkauf()) + "€");
            bruttoEinkauf.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            bruttoEinkauf.setBackgroundResource(R.color.White);
            tr.addView(bruttoEinkauf);

            TextView nettoUmsatz = new TextView(this);
            nettoUmsatz.setText(df.format(i.getNettoUmsatz()) + "€");
            nettoUmsatz.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            nettoUmsatz.setBackgroundResource(R.color.White);
            tr.addView(nettoUmsatz);

            TextView anteil = new TextView(this);
            anteil.setId(ANTEILID);
            anteil.setText(prozentZahl.format((i.getNettoUmsatz() / nettoUmsatzTotal) * 100)
                    + "%");
            anteil.setTextAppearance(this, android.R.style.TextAppearance_Large);
            anteil.setBackgroundResource(R.color.White);
            tr.addView(anteil);

            TextView bruttoUmsatz = new TextView(this);
            bruttoUmsatz.setText(df.format(i.getBruttoUmsatz()) + "€");
            bruttoUmsatz.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            bruttoUmsatz.setBackgroundResource(R.color.White);
            tr.addView(bruttoUmsatz);

            TextView wareneinsatz = new TextView(this);
            wareneinsatz.setText(df.format(100 * i.getNettoEinkauf()
                    / i.getNettoUmsatz())
                    + "%");
            wareneinsatz.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            wareneinsatz.setBackgroundResource(R.color.White);
            tr.addView(wareneinsatz);


            table.addView(tr);
            utils.setMarginsToViews(Utils.VIEW_WITHOUT_EDIT_TEXT, artikelName,
                    total, portionen, portionenAnteil, nettoUmsatz, nettoEinkauf, bruttoEinkauf,
                    bruttoUmsatz, wareneinsatz, anteil);


        }
        fleischNettoUmsatzTextView.setText(df.format(nettoFUmsatzTotal) + "€");
        fleischNettoEinkaufTextView.setText(df.format(nettoFEinkaufTotal) + "€");
        fleischEinsatzView.setText(df.format(100 * nettoFEinkaufTotal / nettoFUmsatzTotal) + "%");
    }

    void renderGBerichtTablle() {
        for (ArtikelBerichtModel i : getraenkeBerichtList) {
            TableRow tr = new TableRow(this);
            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            tableRowParams.setMargins(2, 2, 2, 2);

            tr.setLayoutParams(tableRowParams);
            tr.setBackgroundResource(R.color.Aqua);

            TextView artikelName = new TextView(this);
            artikelName.setId(ARTIKELNAMEID);
            artikelName.setText(i.getArtikelName());
            artikelName.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            artikelName.setBackgroundResource(R.color.White);
            tr.addView(artikelName);

            TextView total = new TextView(this);
            total.setText(String.valueOf(i.getTotal()));
            total.setTextAppearance(this, android.R.style.TextAppearance_Large);
            total.setBackgroundResource(R.color.White);
            tr.addView(total);

            TextView portionen = new TextView(this);
            portionen.setText(df.format(i.getPortion()));
            portionen.setTextAppearance(this, android.R.style.TextAppearance_Large);
            portionen.setBackgroundResource(R.color.White);
            tr.addView(portionen);

            TextView portionenAnteil = new TextView(this);
            portionenAnteil.setText(prozentZahl.format(100 * (i.getPortion() / portionTotal)) + "%");
            portionenAnteil.setTextAppearance(this, android.R.style.TextAppearance_Large);
            portionenAnteil.setBackgroundResource(R.color.White);
            tr.addView(portionenAnteil);

            TextView nettoEinkauf = new TextView(this);
            nettoEinkauf.setText(df.format(i.getNettoEinkauf()) + "€");
            nettoEinkauf.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            nettoEinkauf.setBackgroundResource(R.color.White);
            tr.addView(nettoEinkauf);

            TextView bruttoEinkauf = new TextView(this);
            bruttoEinkauf.setText(df.format(i.getBruttoEinkauf()) + "€");
            bruttoEinkauf.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            bruttoEinkauf.setBackgroundResource(R.color.White);
            tr.addView(bruttoEinkauf);

            TextView nettoUmsatz = new TextView(this);
            nettoUmsatz.setText(df.format(i.getNettoUmsatz()) + "€");
            nettoUmsatz.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            nettoUmsatz.setBackgroundResource(R.color.White);
            tr.addView(nettoUmsatz);

            TextView anteil = new TextView(this);
            anteil.setId(ANTEILID);
            anteil.setText(prozentZahl.format((i.getNettoUmsatz() / nettoUmsatzTotal) * 100)
                    + "%");
            anteil.setTextAppearance(this, android.R.style.TextAppearance_Large);
            anteil.setBackgroundResource(R.color.White);
            tr.addView(anteil);

            TextView bruttoUmsatz = new TextView(this);
            bruttoUmsatz.setText(df.format(i.getBruttoUmsatz()) + "€");
            bruttoUmsatz.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            bruttoUmsatz.setBackgroundResource(R.color.White);
            tr.addView(bruttoUmsatz);

            TextView wareneinsatz = new TextView(this);
            wareneinsatz.setText(df.format(100 * i.getNettoEinkauf()
                    / i.getNettoUmsatz())
                    + "%");
            wareneinsatz.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            wareneinsatz.setBackgroundResource(R.color.White);
            tr.addView(wareneinsatz);

            table.addView(tr);
            utils.setMarginsToViews(Utils.VIEW_WITHOUT_EDIT_TEXT, artikelName,
                    total, portionen, portionenAnteil, nettoUmsatz, nettoEinkauf, bruttoEinkauf,
                    bruttoUmsatz, wareneinsatz, anteil);
        }
        getraenkeNettoUmsatzTextView.setText(df.format(nettoGUmsatzTotal) + "€");
        getraenkeNettoEinkaufTextView.setText(df.format(nettoGEinkaufTotal) + "€");
        getraenkeEinsatzView.setText(df.format(100 * nettoGEinkaufTotal / nettoGUmsatzTotal) + "%");
    }

    private void renderSaveAnteilButton(){
        TableRow tr = new TableRow(this);
        TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT);

        tableRowParams.setMargins(2, 2, 2, 2);

        tr.setLayoutParams(tableRowParams);

        TextView artikelName = new TextView(this);
        tr.addView(artikelName);

        TextView total = new TextView(this);
        tr.addView(total);

        TextView portionen = new TextView(this);
        tr.addView(portionen);

        TextView portionenAnteil = new TextView(this);
        tr.addView(portionenAnteil);

        TextView nettoEinkauf = new TextView(this);
        tr.addView(nettoEinkauf);

        TextView bruttoEinkauf = new TextView(this);
        tr.addView(bruttoEinkauf);

        TextView nettoUmsatz = new TextView(this);
        tr.addView(nettoUmsatz);

        Button saveAnteil = new Button(this);
        saveAnteil.setText("Speichern");
        saveAnteil.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new SaveAnteilDialogFragment().show(
                        getSupportFragmentManager(), "saveAnteilDialog");
            }
        });
        tr.addView(saveAnteil);
        table.addView(tr);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.diagrammBtn:
                Intent intent = new Intent(this, GesamtDiagrammActivity.class);
                intent.putExtra("nettoFUmsatzTotal", nettoFUmsatzTotal);
                intent.putExtra("nettoGUmsatzTotal", nettoGUmsatzTotal);
                intent.putExtra("vonDaysFrom1970", vonDaysFrom1970);
                intent.putExtra("bisDaysFrom1970", bisDaysFrom1970);
                startActivity(intent);
                break;
            case R.id.fleischDetailBtn:
                Intent intentFleisch = new Intent(this,
                        FleischZwischenBerichtActivity.class);
                intentFleisch.putExtra("vonDaysFrom1970", vonDaysFrom1970);
                intentFleisch.putExtra("bisDaysFrom1970", bisDaysFrom1970);
                intentFleisch.putExtra("berichtTyp", berichtTyp);
                startActivity(intentFleisch);
                break;
            case R.id.getraenkeDetailBtn:
                Intent intentGetraenke = new Intent(this,
                        GetraenkeZwischenBerichtActivity.class);
                intentGetraenke.putExtra("vonDaysFrom1970", vonDaysFrom1970);
                intentGetraenke.putExtra("bisDaysFrom1970", bisDaysFrom1970);
                intentGetraenke.putExtra("berichtTyp", berichtTyp);
                startActivity(intentGetraenke);
                break;
        }

    }

    @SuppressLint("ValidFragment")
    public class SaveAnteilDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Wollen Sie die Anteile von Artikeln in diesen Tabelle wirklich speichern? " +
                    "Die alte Anteilsdaten werden überschrieben.")
                    .setPositiveButton("Ja",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    Map<String, String> fleischAnteilMap = new HashMap<String, String>();
                                    Map<String, String> getraenkeAnteilMap = new HashMap<String, String>();

                                    for(int j=1;j<table.getChildCount()-1;j++) {
                                        TableRow thisTr = (TableRow) table.getChildAt(j);
                                        TextView thisAnteilView = (TextView) thisTr
                                                .findViewById(ANTEILID);
                                        String thisAnteil = String.valueOf(Double.valueOf(thisAnteilView.getText().toString().replace("%", "").replace(",",".")) / 100);
                                        TextView thisArtikelNameView = (TextView) thisTr.findViewById(ARTIKELNAMEID);
                                        String thisArtikelName = thisArtikelNameView.getText().toString();
                                        if(j<=fleischBerichtList.size()){
                                            fleischAnteilMap.put(thisArtikelName, thisAnteil);
                                        } else {
                                            getraenkeAnteilMap.put(thisArtikelName, thisAnteil);
                                        }
                                    }
                                    GemeinsameAnteilXmlWriter gAXW = new GemeinsameAnteilXmlWriter();
                                    try {
                                        gAXW.writeFleischAnteil(fleischAnteilMap);
                                        gAXW.writeGetraenkeAnteil(getraenkeAnteilMap);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (SAXException e) {
                                        e.printStackTrace();
                                    } catch (ParserConfigurationException e) {
                                        e.printStackTrace();
                                    } catch (TransformerException e) {
                                        e.printStackTrace();
                                    }
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
}
