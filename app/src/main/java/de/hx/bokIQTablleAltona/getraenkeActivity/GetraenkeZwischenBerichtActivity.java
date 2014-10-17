package de.hx.bokIQTablleAltona.getraenkeActivity;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import de.hx.bokIQTablleAltona.R;
import de.hx.bokIQTablleAltona.models.ArtikelBerichtModel;
import de.hx.bokIQTablleAltona.models.getraenke.GetraenkeBestellungModel;
import de.hx.bokIQTablleAltona.models.getraenke.OneDayGetraenkeBestellungenModel;
import de.hx.bokIQTablleAltona.util.Utils;
import de.hx.bokIQTablleAltona.xml.getraenke.GetraenkeXmlParserHelper;

public class GetraenkeZwischenBerichtActivity extends Activity implements
        OnClickListener {

    TableLayout table;
    TextView premixUndWasserNettoEinkaufTextView, bierNettoEinkaufTextView,
            weinNettoEinkaufTextView, kaffeeUndTeeNettoEinkaufTextView,
            sonstigeNettoEinkaufTextView, kohlenNettoEinkaufTextView,
            totalNettoEinkaufTextView;
    TextView premixUndWasserNettoUmsatzTextView, bierNettoUmsatzTextView,
            weinNettoUmsatzTextView, kaffeeUndTeeNettoUmsatzTextView,
            sonstigeNettoUmsatzTextView, kohlenNettoUmsatzTextView,
            totalNettoUmsatzTextView;
    TextView premixUndWasserPortionenTextView, bierPortionenTextView,
            weinPortionenTextView, kaffeeUndTeePortionenTextView,
            sonstigePortionenTextView, totalPortionenTextView;
    TextView totalEinsatzView;
    Button diagrammBtn;
    DecimalFormat df = new DecimalFormat("#.##");
    DecimalFormat prozentZahl = new DecimalFormat("#.#");
    GetraenkeXmlParserHelper xmlPH;
    int vonDaysFrom1970, bisDaysFrom1970;
    String berichtTyp = "";
    ArrayList<OneDayGetraenkeBestellungenModel> daysFBList = new ArrayList<OneDayGetraenkeBestellungenModel>();
    ArrayList<ArtikelBerichtModel> getraenkeBerichtList = new ArrayList<ArtikelBerichtModel>();
    double portionTotal = 0, nettoEinkaufTotal = 0, nettoUmsatzTotal = 0;
    double premixUndWasserNettoEinkauf, bierNettoEinkauf, weinNettoEinkauf,
            kaffeeUndTeeNettoEinkauf, sonstigeNettoEinkauf, kohlenNettoEinkauf,
            totalNettoEinkauf;
    double premixUndWasserNettoUmsatz, bierNettoUmsatz, weinNettoUmsatz,
            kaffeeUndTeeNettoUmsatz, sonstigeNettoUmsatz, kohlenNettoUmsatz,
            totalNettoUmsatz;
    double premixUndWasserPortionen, bierPortionen, weinPortionen,
            kaffeeUndTeePortionen, sonstigePortionen;
    Utils utils = new Utils();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getraenke_zwischenbericht);
        table = (TableLayout) findViewById(R.id.tableLayout2);
        totalNettoUmsatzTextView = (TextView) findViewById(R.id.totalNettoUmsatz);
        totalNettoEinkaufTextView = (TextView) findViewById(R.id.totalNettoEinkauf);
        totalPortionenTextView = (TextView) findViewById(R.id.totalPortionen);
        premixUndWasserNettoEinkaufTextView = (TextView) findViewById(R.id.premixUndWasserNettoEinkauf);
        premixUndWasserNettoUmsatzTextView = (TextView) findViewById(R.id.premixUndWasserNettoUmsatz);
        premixUndWasserPortionenTextView = (TextView) findViewById(R.id.premixUndWasserPortionen);
        bierNettoEinkaufTextView = (TextView) findViewById(R.id.bierNettoEinkauf);
        bierNettoUmsatzTextView = (TextView) findViewById(R.id.bierNettoUmsatz);
        bierPortionenTextView = (TextView) findViewById(R.id.bierPortionen);
        weinNettoEinkaufTextView = (TextView) findViewById(R.id.weinNettoEinkauf);
        weinNettoUmsatzTextView = (TextView) findViewById(R.id.weinNettoUmsatz);
        weinPortionenTextView = (TextView) findViewById(R.id.weinPortionen);
        kaffeeUndTeeNettoEinkaufTextView = (TextView) findViewById(R.id.kaffeeUndTeeNettoEinkauf);
        kaffeeUndTeeNettoUmsatzTextView = (TextView) findViewById(R.id.kaffeeUndTeeNettoUmsatz);
        kaffeeUndTeePortionenTextView = (TextView) findViewById(R.id.kaffeeUndTeePortionen);
        sonstigeNettoEinkaufTextView = (TextView) findViewById(R.id.sonstigeNettoEinkauf);
        sonstigeNettoUmsatzTextView = (TextView) findViewById(R.id.sonstigeNettoUmsatz);
        sonstigePortionenTextView = (TextView) findViewById(R.id.sonstigePortionen);
        kohlenNettoEinkaufTextView = (TextView) findViewById(R.id.kohlenNettoEinkauf);
        kohlenNettoUmsatzTextView = (TextView) findViewById(R.id.kohlenNettoUmsatz);
        totalEinsatzView = (TextView) findViewById(R.id.totalEinsatz);
        diagrammBtn = (Button) findViewById(R.id.getraenkeDiagrammBtn);

        Intent intent = getIntent();
        vonDaysFrom1970 = intent.getIntExtra("vonDaysFrom1970", 0);
        bisDaysFrom1970 = intent.getIntExtra("bisDaysFrom1970", 0);
        berichtTyp = intent.getStringExtra("berichtTyp");

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis((long) (vonDaysFrom1970 + 1) * (24 * 3600 * 1000));
        String vonDay = (DateFormat.format("dd.MMMM.yyyy ", c.getTime()))
                .toString();
        c.setTimeInMillis((long) (bisDaysFrom1970 + 1) * (24 * 3600 * 1000));
        String bisDay = (DateFormat.format("dd.MMMM.yyyy ", c.getTime()))
                .toString();
        String monat = (DateFormat.format("MMMM.yyyy ", c.getTime()))
                .toString();
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (berichtTyp.equals("endbericht"))
            actionBar.setTitle("Getränkeendbericht von " + monat);
        else
            actionBar.setTitle("Getränkezwischenbericht von " + vonDay
                    + " bis " + bisDay);

        getBerichtList();
        renderBerichtTablle();
        diagrammBtn.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        finish();
        return true;
    }

    void getBerichtList() {
        xmlPH = new GetraenkeXmlParserHelper();

        try {
            daysFBList = xmlPH.getDaysGetraenkeBestellungen(vonDaysFrom1970,
                    bisDaysFrom1970);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (OneDayGetraenkeBestellungenModel thisOneDayFB : daysFBList) {
            nettoEinkaufTotal += thisOneDayFB.getEinkaufssumme();
            nettoUmsatzTotal += thisOneDayFB.getNettoUmsatzssumme();
            portionTotal += thisOneDayFB.getPortionSumme();
            for (GetraenkeBestellungModel thisFB : thisOneDayFB
                    .getGetraenkebestellungen()) {
                String thisArtikelName = thisFB.getGetraenkeModel()
                        .getArtikelName();
                ArtikelBerichtModel thisFAB = utils
                        .getABWithArtikelNameFromList(getraenkeBerichtList,
                                thisArtikelName);
                if (thisFAB == null) {
                    ArtikelBerichtModel newFAB = new ArtikelBerichtModel(
                            thisArtikelName, thisFB.getGetraenkeModel()
                            .getKategorie(), thisFB.getGetraenkeModel()
                            .getOrder(), thisFB.getTotal(),
                            thisFB.getPortion(), thisFB.getNettoEinkauf(),
                            thisFB.getBruttoEinkauf(), thisFB.getNettoUmsatz(),
                            thisFB.getBruttoUmsatz()
                    );
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

    void renderBerichtTablle() {
        for (ArtikelBerichtModel i : getraenkeBerichtList) {
            TableRow tr = new TableRow(this);
            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            tableRowParams.setMargins(2, 2, 2, 2);

            tr.setLayoutParams(tableRowParams);
            tr.setBackgroundResource(R.color.Aqua);

            TextView artikelName = new TextView(this);
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
            portionen.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            portionen.setBackgroundResource(R.color.White);
            tr.addView(portionen);

            TextView portionenAnteil = new TextView(this);
            portionenAnteil.setText(prozentZahl.format(100 * i.getPortion()
                    / portionTotal)
                    + "%");
            portionenAnteil.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
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
                    total, portionen, portionenAnteil, nettoUmsatz,
                    nettoEinkauf, bruttoEinkauf, bruttoUmsatz, wareneinsatz,
                    anteil);

            String thisKategorie = i.getKategorie();
            if (thisKategorie.equals("Premix")
                    || thisKategorie.equals("Wasser")) {
                premixUndWasserNettoEinkauf += i.getNettoEinkauf();
                premixUndWasserNettoUmsatz += i.getNettoUmsatz();
                premixUndWasserPortionen += i.getPortion();
            } else if (thisKategorie.equals("Flaschebier")
                    || thisKategorie.equals("Fassbier")
                    || i.getArtikelName().equals("Bier Tschingtao")
                    || i.getArtikelName().equals("Bier Singha")) {
                bierNettoEinkauf += i.getNettoEinkauf();
                bierNettoUmsatz += i.getNettoUmsatz();
                bierPortionen += i.getPortion();
            } else if (thisKategorie.equals("Schnapps")
                    || thisKategorie.equals("Wein")
                    || i.getArtikelName().equals("Pflaumenwein 5L")
                    || i.getArtikelName().equals("Bambusschnaps 0,5L")
                    || i.getArtikelName().equals("Mekong")
                    || i.getArtikelName().equals("Sake")) {
                weinNettoEinkauf += i.getNettoEinkauf();
                weinNettoUmsatz += i.getNettoUmsatz();
                weinPortionen += i.getPortion();
            } else if (thisKategorie.equals("Asiatische Lieferung")) {
                kaffeeUndTeeNettoEinkauf += i.getNettoEinkauf();
                kaffeeUndTeeNettoUmsatz += i.getNettoUmsatz();
                kaffeeUndTeePortionen += i.getPortion();
            } else if (thisKategorie.equals("Kohlensaeure")) {
                kohlenNettoEinkauf += i.getNettoEinkauf();
                kohlenNettoUmsatz += i.getNettoUmsatz();
            } else {
                sonstigeNettoEinkauf += i.getNettoEinkauf();
                sonstigeNettoUmsatz += i.getNettoUmsatz();
                sonstigePortionen += i.getPortion();
            }
        }
//		portionTotal = premixUndWasserPortionen + bierPortionen + weinPortionen
//				+ kaffeeUndTeePortionen;

        totalNettoUmsatzTextView.setText(df.format(nettoUmsatzTotal) + "€");
        totalNettoEinkaufTextView.setText(df.format(nettoEinkaufTotal) + "€");
        totalPortionenTextView.setText(df.format(portionTotal));
        totalEinsatzView.setText(df.format(100 * nettoEinkaufTotal
                / nettoUmsatzTotal)
                + "%");

        premixUndWasserNettoEinkaufTextView.setText(df
                .format(premixUndWasserNettoEinkauf) + "€");
        premixUndWasserNettoUmsatzTextView.setText(df
                .format(premixUndWasserNettoUmsatz) + "€");
        premixUndWasserPortionenTextView.setText(df
                .format(premixUndWasserPortionen));

        bierNettoEinkaufTextView.setText(df.format(bierNettoEinkauf) + "€");
        bierNettoUmsatzTextView.setText(df.format(bierNettoUmsatz) + "€");
        bierPortionenTextView.setText(df.format(bierPortionen));

        weinNettoEinkaufTextView.setText(df.format(weinNettoEinkauf) + "€");
        weinNettoUmsatzTextView.setText(df.format(weinNettoUmsatz) + "€");
        weinPortionenTextView.setText(df.format(weinPortionen));

        kaffeeUndTeeNettoEinkaufTextView.setText(df
                .format(kaffeeUndTeeNettoEinkauf) + "€");
        kaffeeUndTeeNettoUmsatzTextView.setText(df
                .format(kaffeeUndTeeNettoUmsatz) + "€");
        kaffeeUndTeePortionenTextView.setText(df.format(kaffeeUndTeePortionen));

        kohlenNettoEinkaufTextView.setText(df.format(kohlenNettoEinkauf) + "€");
        kohlenNettoUmsatzTextView.setText(df.format(kohlenNettoUmsatz) + "€");

        sonstigeNettoEinkaufTextView.setText(df.format(sonstigeNettoEinkauf)
                + "€");
        sonstigeNettoUmsatzTextView.setText(df.format(sonstigeNettoUmsatz)
                + "€");
        sonstigePortionenTextView.setText(df.format(sonstigePortionen));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(),
                GetraenkeDiagrammActivity.class);
        intent.putExtra("premixUndWasserNettoUmsatz",
                premixUndWasserNettoUmsatz);
        intent.putExtra("bierNettoUmsatz", bierNettoUmsatz);
        intent.putExtra("weinNettoUmsatz", weinNettoUmsatz);
        intent.putExtra("kaffeeUndTeeNettoUmsatz", kaffeeUndTeeNettoUmsatz);
        intent.putExtra("sonstigeNettoUmsatz", sonstigeNettoUmsatz);
        startActivity(intent);
    }

}
