package de.hx.bokIQTablleAltona.fleischActivity;

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
import de.hx.bokIQTablleAltona.models.fleisch.FleischBestellungModel;
import de.hx.bokIQTablleAltona.models.fleisch.OneDayFleischBestellungenModel;
import de.hx.bokIQTablleAltona.util.Utils;
import de.hx.bokIQTablleAltona.xml.fleisch.FleischXmlParserHelper;

public class FleischZwischenBerichtActivity extends Activity implements OnClickListener {

    TableLayout table;
    TextView nettoUmsatzTotalView;
    TextView nettoEinkaufTotalView;
    TextView totalEinsatzView;
    TextView fleischTotal, enteTotal, fischTotal, sushifischTotal, sonstigeTotal;
    Button diagrammBtn;
    DecimalFormat df = new DecimalFormat("#.##");
    DecimalFormat prozentZahl = new DecimalFormat("#.#");
    FleischXmlParserHelper xmlPH;
    int vonDaysFrom1970, bisDaysFrom1970;
    String berichtTyp = "";
    ArrayList<OneDayFleischBestellungenModel> daysFBList = new ArrayList<OneDayFleischBestellungenModel>();
    ArrayList<ArtikelBerichtModel> fleischBerichtList = new ArrayList<ArtikelBerichtModel>();
    double nettoEinkaufTotal = 0, nettoUmsatzTotal = 0, portionTotal = 0;
    double fleischKilo = 0, enteSt = 0, fischKilo = 0, sushifischKilo = 0, sonstigeKilo = 0;
    double fleischUmsatz = 0, enteUmsatz = 0, fischUmsatz = 0, sushifischUmsatz = 0, sonstigeUmsatz = 0;
    Utils utils = new Utils();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fleisch_zwischenbericht);
        table = (TableLayout) findViewById(R.id.tableLayout2);
        nettoUmsatzTotalView = (TextView) findViewById(R.id.berichtNettoUmsatzTotal);
        nettoEinkaufTotalView = (TextView) findViewById(R.id.berichtNettoEinkaufTotal);
        totalEinsatzView = (TextView) findViewById(R.id.berichtTotalEinsatz);
        fleischTotal = (TextView) findViewById(R.id.fleischTotalKilo);
        enteTotal = (TextView) findViewById(R.id.enteTotalSt);
        fischTotal = (TextView) findViewById(R.id.fischTotalKilo);
        sushifischTotal = (TextView) findViewById(R.id.sushifischTotalKilo);
        sonstigeTotal = (TextView) findViewById(R.id.sonstigeTotalKilo);
        diagrammBtn = (Button) findViewById(R.id.fleischDiagrammBtn);

        Intent intent = getIntent();
        vonDaysFrom1970 = intent.getIntExtra("vonDaysFrom1970", 0);
        bisDaysFrom1970 = intent.getIntExtra("bisDaysFrom1970", 0);
        berichtTyp = intent.getStringExtra("berichtTyp");

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis((long) (vonDaysFrom1970 + 1) * (24 * 3600 * 1000));
        String vonDay = (DateFormat.format("dd.MMMM.yyyy ", c.getTime())).toString();
        String monat = (DateFormat.format("MMMM.yyyy ", c.getTime())).toString();
        c.setTimeInMillis((long) (bisDaysFrom1970 + 1) * (24 * 3600 * 1000));
        String bisDay = (DateFormat.format("dd.MMMM.yyyy ", c.getTime())).toString();
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (berichtTyp.equals("endbericht"))
            actionBar.setTitle("Fleischendbericht von " + monat);
        else
            actionBar.setTitle("Fleischzwischenbericht von " + vonDay + " bis " + bisDay);
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
        xmlPH = new FleischXmlParserHelper();

        try {
            daysFBList = xmlPH.getDaysFleischBestellungen(vonDaysFrom1970,
                    bisDaysFrom1970);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (OneDayFleischBestellungenModel thisOneDayFB : daysFBList) {
            nettoEinkaufTotal += thisOneDayFB.getEinkaufssumme();
            nettoUmsatzTotal += thisOneDayFB.getNettoUmsatzssumme();
            portionTotal += thisOneDayFB.getPortionSumme();
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

    void renderBerichtTablle() {
        for (ArtikelBerichtModel i : fleischBerichtList) {
            TableRow tr = new TableRow(this);
            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            tableRowParams.setMargins(2, 2, 2, 2);

            tr.setLayoutParams(tableRowParams);
            tr.setBackgroundResource(R.color.Brown);

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
            portionen.setTextAppearance(this, android.R.style.TextAppearance_Large);
            portionen.setBackgroundResource(R.color.White);
            tr.addView(portionen);

            TextView portionenAnteil = new TextView(this);
            portionenAnteil.setText(prozentZahl.format((i.getPortion() / portionTotal) * 100)
                    + "%");
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

            if (i.getKategorie().equals("Fleisch")) {
                fleischKilo += i.getTotal();
                fleischUmsatz += i.getNettoUmsatz();
            } else if (i.getKategorie().equals("Ente")) {
                enteSt += i.getTotal();
                enteUmsatz += i.getNettoUmsatz();
            } else if (i.getKategorie().equals("Fisch")) {
                fischKilo += i.getTotal();
                fischUmsatz += i.getNettoUmsatz();
            } else if (i.getKategorie().equals("Sushifisch")) {
                sushifischKilo += i.getTotal();
                sushifischUmsatz += i.getNettoUmsatz();
            } else if (i.getKategorie().equals("Sonstige")) {
                sonstigeKilo += i.getTotal();
                sonstigeUmsatz += i.getNettoUmsatz();
            }

        }
        nettoUmsatzTotalView.setText(df.format(nettoUmsatzTotal) + "€");
        nettoEinkaufTotalView.setText(df.format(nettoEinkaufTotal) + "€");
        totalEinsatzView.setText(df.format(100 * nettoEinkaufTotal / nettoUmsatzTotal) + "%");
        fleischTotal.setText(df.format(fleischKilo) + " kg");
        enteTotal.setText(df.format(enteSt) + " St.");
        fischTotal.setText(df.format(fischKilo) + " kg");
        sushifischTotal.setText(df.format(sushifischKilo) + " kg");
        sonstigeTotal.setText(df.format(sonstigeKilo) + " kg");
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(getApplicationContext(), FleischDiagrammActivity.class);
        intent.putExtra("fleischUmsatz", fleischUmsatz);
        intent.putExtra("enteUmsatz", enteUmsatz);
        intent.putExtra("fischUmsatz", fischUmsatz);
        intent.putExtra("sushifischUmsatz", sushifischUmsatz);
        intent.putExtra("sonstigeUmsatz", sonstigeUmsatz);
        startActivity(intent);
    }

}
