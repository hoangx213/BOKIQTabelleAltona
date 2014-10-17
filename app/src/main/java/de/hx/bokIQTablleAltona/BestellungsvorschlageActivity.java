package de.hx.bokIQTablleAltona;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import de.hx.bokIQTablleAltona.models.fleisch.FleischModel;
import de.hx.bokIQTablleAltona.models.getraenke.GetraenkeModel;
import de.hx.bokIQTablleAltona.util.Utils;
import de.hx.bokIQTablleAltona.xml.fleisch.FleischModelXmlParser;
import de.hx.bokIQTablleAltona.xml.getraenke.GetraenkeModelXmlParser;

public class BestellungsvorschlageActivity extends Activity implements
        OnClickListener {

    ArrayList<FleischModel> fleischList;
    ArrayList<GetraenkeModel> getraenkeList;
    FleischModelXmlParser fleischXP;
    TableLayout table;
    GetraenkeModelXmlParser getraenkeXP;
    EditText monatUmsatzEditText;
    Button berechnenBtn;
    static final int TOTALID = 2222;
    static final int NETTOUMSATZID = 3333;
    static final int TOTALNETTOUMSATZID = 33333333;
    static final int NETTOEINKAUFID = 4444;
    static final int TOTALNETTOEINKAUFID = 44444444;
    static final int WARENEINSATZID = 7777;
    static final int TOTALWARENEINSATZID = 77777777;
    static final int ANTEILID = 8888;
    static final int TOTALANTEILID = 88888888;
    static final int PORTIONID = 4412;
    static final int PORTIONPROTAGID = 6321;
    static final int BESTELLUNGENID = 9999;
    static final int EINKAUFSPREISID = 1234;
    static final int VERKAUFSPREISID = 4321;
    DecimalFormat df = new DecimalFormat("#.##");
    DecimalFormat prozentZahl = new DecimalFormat("#.#");
    static double monatUmsatz = 0.0;
    static double totalArtikel = 0.0;
    Utils utils = new Utils();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bestellung_vorschlage);
        findView();
        berechnenBtn.setOnClickListener(this);
        getFleischList();
        getGetraenkeList();
        totalArtikel = fleischList.size() + getraenkeList.size();
        renderFleischTable();
        renderGetraenkeTable();
        renderTotalZeile();
    }

    private void findView() {
        table = (TableLayout) findViewById(R.id.bestellungsvorschlagTable);
        monatUmsatzEditText = (EditText) findViewById(R.id.monatUmsatzEditText);
        berechnenBtn = (Button) findViewById(R.id.berechnenBtn);
    }

    private void getFleischList() {
        fleischXP = new FleischModelXmlParser(getApplicationContext());
        try {
            fleischList = fleischXP.fleischWithAnteilParsen();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getGetraenkeList() {
        getraenkeXP = new GetraenkeModelXmlParser(getApplicationContext());
        try {
            getraenkeList = getraenkeXP.getraenkeWithAnteilParsen();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void renderFleischTable() {
        for (FleischModel i : fleischList) {
            TableRow tr = new TableRow(this);
            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            tr.setTag("fleisch");

            tableRowParams.setMargins(2, 2, 2, 2);

            tr.setLayoutParams(tableRowParams);
            tr.setBackgroundResource(R.color.Brown);

            TextView artikelName = new TextView(this);
            artikelName.setText(i.getArtikelName());
            artikelName.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            artikelName.setBackgroundResource(R.color.White);
            tr.addView(artikelName);

            TextView bestellungen = new TextView(this);
            bestellungen.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            bestellungen.setBackgroundResource(R.color.White);
            bestellungen.setId(BESTELLUNGENID);
            tr.addView(bestellungen);

            TextView total = new TextView(this);
            total.setTextAppearance(this, android.R.style.TextAppearance_Large);
            total.setBackgroundResource(R.color.White);
            total.setId(TOTALID);
            tr.addView(total);

            TextView portion = new TextView(this);
            portion.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            portion.setBackgroundResource(R.color.White);
            portion.setId(PORTIONID);
            tr.addView(portion);

            TextView portionProTag = new TextView(this);
            portionProTag.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            portionProTag.setBackgroundResource(R.color.White);
            portionProTag.setId(PORTIONPROTAGID);
            tr.addView(portionProTag);

            EditText einkaufspreis = new EditText(this);
            einkaufspreis.setInputType(InputType.TYPE_CLASS_PHONE);
            einkaufspreis.setText(String.valueOf(i.getEinkaufspreis()));
            einkaufspreis.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            einkaufspreis.setBackgroundResource(R.color.AntiqueWhite);
            einkaufspreis.setId(EINKAUFSPREISID);
            tr.addView(einkaufspreis);

            TextView nettoEinkauf = new TextView(this);
            nettoEinkauf.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            nettoEinkauf.setBackgroundResource(R.color.White);
            nettoEinkauf.setId(NETTOEINKAUFID);
            tr.addView(nettoEinkauf);

            EditText verkaufspreis = new EditText(this);
            verkaufspreis.setInputType(InputType.TYPE_CLASS_PHONE);
            verkaufspreis.setText(String.valueOf(i.getNettoPreis()));
            verkaufspreis.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            verkaufspreis.setBackgroundResource(R.color.AntiqueWhite);
            verkaufspreis.setId(VERKAUFSPREISID);
            tr.addView(verkaufspreis);

            TextView nettoUmsatz = new TextView(this);
            nettoUmsatz.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            nettoUmsatz.setBackgroundResource(R.color.White);
            nettoUmsatz.setId(NETTOUMSATZID);
            tr.addView(nettoUmsatz);

            EditText anteil = new EditText(this);
            anteil.setInputType(InputType.TYPE_CLASS_PHONE);
            anteil.setText(df.format(i.getAnteil() * 100));
            anteil.setTextAppearance(this, android.R.style.TextAppearance_Large);
            anteil.setBackgroundResource(R.color.AntiqueWhite);
            anteil.setId(ANTEILID);
            tr.addView(anteil);

            TextView wareneinsatz = new TextView(this);
            wareneinsatz.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            wareneinsatz.setBackgroundResource(R.color.White);
            wareneinsatz.setId(WARENEINSATZID);
            tr.addView(wareneinsatz);

            table.addView(tr);
            TableLayout.LayoutParams tlp = (TableLayout.LayoutParams) tr
                    .getLayoutParams();
            tlp.topMargin = 5;
            tlp.bottomMargin = 5;
            tlp.leftMargin = 2;
            tlp.rightMargin = 2;

            utils.setMarginsToViews(Utils.VIEW_WITH_EDIT_TEXT, artikelName,
                    bestellungen, total, portion, portionProTag, verkaufspreis,
                    nettoUmsatz, einkaufspreis, nettoEinkauf, wareneinsatz,
                    anteil);
        }
    }

    private void renderGetraenkeTable() {
        for (GetraenkeModel i : getraenkeList) {
            TableRow tr = new TableRow(this);
            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            tr.setTag("getraenke");

            tableRowParams.setMargins(2, 2, 2, 2);

            tr.setLayoutParams(tableRowParams);
            tr.setBackgroundResource(R.color.Aqua);

            TextView artikelName = new TextView(this);
            artikelName.setText(i.getArtikelName());
            artikelName.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            artikelName.setBackgroundResource(R.color.White);
            tr.addView(artikelName);

            TextView bestellungen = new TextView(this);
            bestellungen.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            bestellungen.setBackgroundResource(R.color.White);
            bestellungen.setId(BESTELLUNGENID);
            tr.addView(bestellungen);

            TextView total = new TextView(this);
            total.setTextAppearance(this, android.R.style.TextAppearance_Large);
            total.setBackgroundResource(R.color.White);
            total.setId(TOTALID);
            tr.addView(total);

            TextView portion = new TextView(this);
            portion.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            portion.setBackgroundResource(R.color.White);
            portion.setId(PORTIONID);
            tr.addView(portion);

            TextView portionProTag = new TextView(this);
            portionProTag.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            portionProTag.setBackgroundResource(R.color.White);
            portionProTag.setId(PORTIONPROTAGID);
            tr.addView(portionProTag);

            EditText einkaufspreis = new EditText(this);
            einkaufspreis.setInputType(InputType.TYPE_CLASS_PHONE);
            einkaufspreis.setText(String.valueOf(i.getEinkaufspreis()));
            einkaufspreis.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            einkaufspreis.setBackgroundResource(R.color.WaterBlue);
            einkaufspreis.setId(EINKAUFSPREISID);
            tr.addView(einkaufspreis);

            TextView nettoEinkauf = new TextView(this);
            nettoEinkauf.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            nettoEinkauf.setBackgroundResource(R.color.White);
            nettoEinkauf.setId(NETTOEINKAUFID);
            tr.addView(nettoEinkauf);

            EditText verkaufspreis = new EditText(this);
            verkaufspreis.setInputType(InputType.TYPE_CLASS_PHONE);
            verkaufspreis.setText(String.valueOf(i.getVerkaufspreis()));
            verkaufspreis.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            verkaufspreis.setBackgroundResource(R.color.WaterBlue);
            verkaufspreis.setId(VERKAUFSPREISID);
            tr.addView(verkaufspreis);

            TextView nettoUmsatz = new TextView(this);
            nettoUmsatz.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            nettoUmsatz.setBackgroundResource(R.color.White);
            nettoUmsatz.setId(NETTOUMSATZID);
            tr.addView(nettoUmsatz);

            EditText anteil = new EditText(this);
            anteil.setInputType(InputType.TYPE_CLASS_PHONE);
            anteil.setText(df.format(i.getAnteil() * 100));
            anteil.setTextAppearance(this, android.R.style.TextAppearance_Large);
            anteil.setBackgroundResource(R.color.WaterBlue);
            anteil.setId(ANTEILID);
            tr.addView(anteil);

            TextView wareneinsatz = new TextView(this);
            wareneinsatz.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            wareneinsatz.setBackgroundResource(R.color.White);
            wareneinsatz.setId(WARENEINSATZID);
            tr.addView(wareneinsatz);

            table.addView(tr);
            TableLayout.LayoutParams tlp = (TableLayout.LayoutParams) tr
                    .getLayoutParams();
            tlp.topMargin = 5;
            tlp.bottomMargin = 5;
            tlp.leftMargin = 2;
            tlp.rightMargin = 2;

            utils.setMarginsToViews(Utils.VIEW_WITH_EDIT_TEXT, artikelName,
                    bestellungen, total, portion, portionProTag, verkaufspreis,
                    nettoUmsatz, einkaufspreis, nettoEinkauf, wareneinsatz,
                    anteil);
        }
    }

    private void renderTotalZeile() {
        TableRow tr = new TableRow(this);
        TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT);

        tableRowParams.setMargins(2, 2, 2, 2);

        tr.setLayoutParams(tableRowParams);
        // tr.setBackgroundResource(R.color.Black);

        TextView artikelName = new TextView(this);
        // artikelName.setText(i.getArtikelName());
        artikelName.setTextAppearance(this,
                android.R.style.TextAppearance_Large);
        artikelName.setBackgroundResource(R.color.OrangePutty);
        tr.addView(artikelName);

        TextView bestellungen = new TextView(this);
        bestellungen.setTextAppearance(this,
                android.R.style.TextAppearance_Large);
        bestellungen.setBackgroundResource(R.color.OrangePutty);
        bestellungen.setId(BESTELLUNGENID);
        tr.addView(bestellungen);

        TextView total = new TextView(this);
        total.setTextAppearance(this, android.R.style.TextAppearance_Large);
        total.setBackgroundResource(R.color.OrangePutty);
        total.setId(TOTALID);
        tr.addView(total);

        TextView portion = new TextView(this);
        portion.setTextAppearance(this, android.R.style.TextAppearance_Large);
        portion.setBackgroundResource(R.color.OrangePutty);
        portion.setId(PORTIONID);
        tr.addView(portion);

        TextView portionProTag = new TextView(this);
        portionProTag.setTextAppearance(this,
                android.R.style.TextAppearance_Large);
        portionProTag.setBackgroundResource(R.color.OrangePutty);
        portionProTag.setId(PORTIONPROTAGID);
        tr.addView(portionProTag);

        TextView einkaufspreis = new TextView(this);
        einkaufspreis.setInputType(InputType.TYPE_CLASS_PHONE);
        // einkaufspreis.setText(String.valueOf(i.getEinkaufspreis()));
        einkaufspreis.setTextAppearance(this,
                android.R.style.TextAppearance_Large);
        einkaufspreis.setBackgroundResource(R.color.OrangePutty);
        einkaufspreis.setId(EINKAUFSPREISID);
        tr.addView(einkaufspreis);

        TextView nettoEinkauf = new TextView(this);
        nettoEinkauf.setTextAppearance(this,
                android.R.style.TextAppearance_Large);
        nettoEinkauf.setBackgroundResource(R.color.White);
        nettoEinkauf.setId(TOTALNETTOEINKAUFID);
        tr.addView(nettoEinkauf);

        TextView verkaufspreis = new TextView(this);
        verkaufspreis.setInputType(InputType.TYPE_CLASS_PHONE);
        // verkaufspreis.setText(String.valueOf(i.getVerkaufspreis()));
        verkaufspreis.setTextAppearance(this,
                android.R.style.TextAppearance_Large);
        verkaufspreis.setBackgroundResource(R.color.OrangePutty);
        verkaufspreis.setId(VERKAUFSPREISID);
        tr.addView(verkaufspreis);

        TextView nettoUmsatz = new TextView(this);
        nettoUmsatz.setTextAppearance(this,
                android.R.style.TextAppearance_Large);
        nettoUmsatz.setBackgroundResource(R.color.White);
        nettoUmsatz.setId(TOTALNETTOUMSATZID);
        tr.addView(nettoUmsatz);

        TextView anteil = new TextView(this);
        anteil.setInputType(InputType.TYPE_CLASS_PHONE);
        // anteil.setText(String.valueOf(100.0/totalArtikel));
        anteil.setTextAppearance(this, android.R.style.TextAppearance_Large);
        anteil.setBackgroundResource(R.color.White);
        anteil.setId(TOTALANTEILID);
        tr.addView(anteil);

        TextView wareneinsatz = new TextView(this);
        wareneinsatz.setTextAppearance(this,
                android.R.style.TextAppearance_Large);
        wareneinsatz.setBackgroundResource(R.color.White);
        wareneinsatz.setId(TOTALWARENEINSATZID);
        tr.addView(wareneinsatz);

        table.addView(tr);
        TableLayout.LayoutParams tlp = (TableLayout.LayoutParams) tr
                .getLayoutParams();
        tlp.topMargin = 5;
        tlp.bottomMargin = 5;
        tlp.leftMargin = 2;
        tlp.rightMargin = 2;

        utils.setMarginsToViews(Utils.VIEW_WITH_EDIT_TEXT, artikelName,
                bestellungen, total, portion, portionProTag, verkaufspreis,
                nettoUmsatz, einkaufspreis, nettoEinkauf, wareneinsatz, anteil);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.berechnenBtn:
                double testMonatUmsatz = 0.0,
                        totalWareneinsatz = 0.0,
                        monatEinkauf = 0.0,
                        totalAnteil = 0.0;
                monatUmsatz = Double.valueOf(monatUmsatzEditText.getText()
                        .toString());
                for (int i = 1; i < table.getChildCount() - 1; i++) {
                    TableRow thisTableRow = (TableRow) table.getChildAt(i);
                    EditText thisAnteilEditText = (EditText) thisTableRow
                            .findViewById(ANTEILID);
                    EditText thisEinkaufspreisEditText = (EditText) thisTableRow
                            .findViewById(EINKAUFSPREISID);
                    EditText thisVerkaufspreisEditText = (EditText) thisTableRow
                            .findViewById(VERKAUFSPREISID);
                    TextView thisBestellungTextView = (TextView) thisTableRow
                            .findViewById(BESTELLUNGENID);
                    TextView thisTotalTextView = (TextView) thisTableRow
                            .findViewById(TOTALID);
                    TextView thisPortionTextView = (TextView) thisTableRow
                            .findViewById(PORTIONID);
                    TextView thisPortionProTagTextView = (TextView) thisTableRow
                            .findViewById(PORTIONPROTAGID);
                    TextView thisNettoEinkaufTextView = (TextView) thisTableRow
                            .findViewById(NETTOEINKAUFID);
                    TextView thisNettoUmsatzTextView = (TextView) thisTableRow
                            .findViewById(NETTOUMSATZID);
                    TextView thisWareneinsatzTextView = (TextView) thisTableRow
                            .findViewById(WARENEINSATZID);

                    double thisAnteil = Double.valueOf(thisAnteilEditText.getText()
                            .toString().replace(",","."));
                    double thisEinkaufspreis = Double
                            .valueOf(thisEinkaufspreisEditText.getText().toString());
                    double thisVerkaufspreis = Double
                            .valueOf(thisVerkaufspreisEditText.getText().toString());

                    double thisNettoUmsatz = (monatUmsatz * thisAnteil) / 100;
                    double thisPortion = (thisVerkaufspreis == 0 ? 0
                            : thisNettoUmsatz / thisVerkaufspreis);
                    double thisPortionProTag = thisPortion / 30;
                    double thisVerkaufsmenge = 0.0;
                    double thisTotal = 0.0;
                    double thisBestellung = 0.0;
                    double thisNettoEinkauf = 0.0;
                    if (thisTableRow.getTag().equals("fleisch")) {
                        FleischModel thisFleisch = fleischList.get(i - 1);
                        thisVerkaufsmenge = thisPortion
                                / thisFleisch.getVerkaufsfaktor();
                        thisTotal = thisVerkaufsmenge
                                / (1 - thisFleisch.getSchwund());
                        thisBestellung = thisTotal
                                / thisFleisch.getEinheitProBestellung();
                        thisNettoEinkauf = thisTotal * thisEinkaufspreis;
                    } else if (thisTableRow.getTag().equals("getraenke")) {
                        GetraenkeModel thisGetraenke = getraenkeList.get(i - 1
                                - fleischList.size());
                        thisVerkaufsmenge = thisPortion
                                * thisGetraenke.getEinheitProGlas();
                        thisTotal = thisVerkaufsmenge
                                / (1 - thisGetraenke.getSchwund());
                        thisBestellung = thisTotal
                                / thisGetraenke.getEinheitProBestellung();
                        thisNettoEinkauf = thisBestellung * thisEinkaufspreis;
                    }
                    double thisWareneinsatz = 100 * thisNettoEinkauf
                            / thisNettoUmsatz;

                    thisBestellungTextView.setText(df.format(thisBestellung));
                    thisTotalTextView.setText(df.format(thisTotal));
                    thisPortionTextView.setText(df.format(thisPortion));
                    thisPortionProTagTextView.setText(df.format(thisPortionProTag));
                    thisNettoEinkaufTextView.setText(df.format(thisNettoEinkauf)
                            + "€");
                    thisNettoUmsatzTextView.setText(df.format(thisNettoUmsatz)
                            + "€");
                    thisWareneinsatzTextView.setText(df.format(thisWareneinsatz)
                            + "%");

                    testMonatUmsatz += thisNettoUmsatz;
                    monatEinkauf += thisNettoEinkauf;
                    totalAnteil += thisAnteil;
                    totalWareneinsatz = 100 * monatEinkauf / testMonatUmsatz;
                }
                TextView totalNettoEinkaufTextView = (TextView) findViewById(TOTALNETTOEINKAUFID);
                TextView totalNettoUmsatzTextView = (TextView) findViewById(TOTALNETTOUMSATZID);
                TextView totalAnteilTextView = (TextView) findViewById(TOTALANTEILID);
                TextView totalWareneinsatzTextView = (TextView) findViewById(TOTALWARENEINSATZID);

                totalNettoEinkaufTextView.setText(df.format(monatEinkauf) + "€");
                totalNettoUmsatzTextView.setText(df.format(testMonatUmsatz) + "€");
                totalAnteilTextView.setText(df.format(totalAnteil) + "%");
                totalWareneinsatzTextView.setText(df.format(totalWareneinsatz)
                        + "%");

        }
    }

}
