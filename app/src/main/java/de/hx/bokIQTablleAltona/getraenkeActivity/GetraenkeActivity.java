package de.hx.bokIQTablleAltona.getraenkeActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import de.hx.bokIQTablleAltona.R;
import de.hx.bokIQTablleAltona.fleischActivity.FleischActivity;
import de.hx.bokIQTablleAltona.models.getraenke.GetraenkeBestellungModel;
import de.hx.bokIQTablleAltona.models.getraenke.GetraenkeModel;
import de.hx.bokIQTablleAltona.util.DatePickerFragment;
import de.hx.bokIQTablleAltona.util.Utils;
import de.hx.bokIQTablleAltona.xml.getraenke.GetraenkeBestellungenXmlWriter;
import de.hx.bokIQTablleAltona.xml.getraenke.GetraenkeEinkaufPreisXmlWriter;
import de.hx.bokIQTablleAltona.xml.getraenke.GetraenkeModelXmlParser;

@SuppressLint("SimpleDateFormat")
public class GetraenkeActivity extends FragmentActivity implements
        OnClickListener {

    ArrayList<GetraenkeModel> getraenkeList;
    ArrayList<GetraenkeBestellungModel> getraenkeBestellungenList;
    Map<String, Double> getraenkeEinkaufPreisMap;
    HashSet<GetraenkeBestellungModel> getraenkeBestellungenSet;
    HashSet<Integer> indexList;
    GetraenkeModelXmlParser getraenkeXP;
    TableLayout table;
    TextView datumGetraenkeBestellungView;
    Button datumBtn, zuFleischBtn;
    static final int PROBESTELLUNGID = 1111;
    static final int TOTALID = 2222;
    static final int NETTOUMSATZID = 3333;
    static final int NETTOEINKAUFID = 4444;
    static final int BRUTTOEINKAUFID = 5555;
    static final int BRUTTOUMSATZID = 6666;
    static final int WARENEINSATZID = 7777;
    static final int ANTEILID = 8888;
    static final int BESTELLUNGENID = 9999;
    static final int EINKAUFSPREISID = 1234;
    static final int VERKAUFSPREISID = 4321;
    DecimalFormat df = new DecimalFormat("#.##");
    DecimalFormat prozentZahl = new DecimalFormat("#.#");
    Button getraenkeSaveBtn;
    String bestellungID = "";
    double nettoUmsatzsumme = 0;
    double nettoEinkaufssumme = 0;
    double portionSumme = 0;
    int daysFrom1970;
    static Calendar calendar;
    String bestellungsdatum;
    Utils utils = new Utils();
    String aktuelleKategorie = "";
    int aktuelleFarbe = 0;
    int farbe1 = R.color.White;
    int farbe2 = R.color.LightCyan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getraenke_bestellung);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBar.setTitle("Getränkebestellung");

        getraenkeSaveBtn = (Button) findViewById(R.id.getraenkeSaveBtn);
        zuFleischBtn = (Button) findViewById(R.id.zuFleischButton);
        getraenkeSaveBtn.setOnClickListener(this);
        table = (TableLayout) findViewById(R.id.TableLayout1);
        datumGetraenkeBestellungView = (TextView) findViewById(R.id.datumGetraenkeBestellungTextView);
        datumBtn = (Button) findViewById(R.id.datumBtn);
        getraenkeList = new ArrayList<GetraenkeModel>();
        getraenkeBestellungenSet = new HashSet<GetraenkeBestellungModel>();
        getraenkeEinkaufPreisMap = new HashMap<String, Double>();
        indexList = new HashSet<Integer>();
        getraenkeXP = new GetraenkeModelXmlParser(getApplicationContext());

        try {
            getraenkeList = getraenkeXP.getraenkeParsen();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        datumBtn.setOnClickListener(this);
        zuFleischBtn.setOnClickListener(this);

        for (GetraenkeModel i : getraenkeList) {
            String thisKategorie = i.getKategorie();
            if (!thisKategorie.equals(aktuelleKategorie)) {
                aktuelleKategorie = thisKategorie;
                aktuelleFarbe = (aktuelleFarbe == farbe1 ? farbe2 : farbe1);
            }
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
            artikelName.setBackgroundResource(aktuelleFarbe);
            tr.addView(artikelName);

            TextView proBestell = new TextView(this);
            proBestell.setText(df.format(i.getEinheitProBestellung()) + " "
                    + i.getEinheit());
            proBestell.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            proBestell.setBackgroundResource(aktuelleFarbe);
            proBestell.setId(PROBESTELLUNGID);
            tr.addView(proBestell);

            EditText bestellungen = new EditText(this);
            bestellungen.setInputType(InputType.TYPE_CLASS_NUMBER);
            bestellungen.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            bestellungen.setBackgroundResource(R.color.WaterBlue);
            bestellungen.setId(BESTELLUNGENID);
            tr.addView(bestellungen);

            TextView total = new TextView(this);
            total.setTextAppearance(this, android.R.style.TextAppearance_Large);
            total.setBackgroundResource(aktuelleFarbe);
            total.setId(TOTALID);
            tr.addView(total);

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
            nettoEinkauf.setBackgroundResource(aktuelleFarbe);
            nettoEinkauf.setId(NETTOEINKAUFID);
            tr.addView(nettoEinkauf);

            TextView bruttoEinkauf = new TextView(this);
            bruttoEinkauf.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            bruttoEinkauf.setBackgroundResource(aktuelleFarbe);
            bruttoEinkauf.setId(BRUTTOEINKAUFID);
            tr.addView(bruttoEinkauf);

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
            nettoUmsatz.setBackgroundResource(aktuelleFarbe);
            nettoUmsatz.setId(NETTOUMSATZID);
            tr.addView(nettoUmsatz);

            TextView anteil = new TextView(this);
            anteil.setTextAppearance(this, android.R.style.TextAppearance_Large);
            anteil.setBackgroundResource(aktuelleFarbe);
            anteil.setId(ANTEILID);
            tr.addView(anteil);

            TextView bruttoUmsatz = new TextView(this);
            bruttoUmsatz.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            bruttoUmsatz.setBackgroundResource(aktuelleFarbe);
            bruttoUmsatz.setId(BRUTTOUMSATZID);
            tr.addView(bruttoUmsatz);

            TextView wareneinsatz = new TextView(this);
            wareneinsatz.setTextAppearance(this,
                    android.R.style.TextAppearance_Large);
            wareneinsatz.setBackgroundResource(aktuelleFarbe);
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
                    proBestell, bestellungen, total, verkaufspreis,
                    nettoUmsatz, einkaufspreis, nettoEinkauf, bruttoEinkauf,
                    bruttoUmsatz, wareneinsatz, anteil);

            bestellungen.addTextChangedListener(new MyTextWatcher(bestellungen,
                    getraenkeList.indexOf(i)));
            einkaufspreis.addTextChangedListener(new MyTextWatcher(
                    einkaufspreis, getraenkeList.indexOf(i)));
            verkaufspreis.addTextChangedListener(new MyTextWatcher(
                    verkaufspreis, getraenkeList.indexOf(i)));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Wollen Sie den Tabelle wirklich verlassen?")
                .setMessage("Die Daten werden nicht gespeichert.")
                .setNegativeButton("Nein", null)
                .setPositiveButton("JA", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        GetraenkeActivity.super.onBackPressed();
                    }

                }).create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    class MyTextWatcher implements TextWatcher {

        EditText view;
        int index;
        int bestellungBefore;

        public MyTextWatcher(EditText view, int index) {
            this.view = view;
            this.index = index;
        }

        @Override
        public void afterTextChanged(Editable s) {
            TableRow tr = (TableRow) view.getParent();
            EditText bestellungenView = (EditText) tr
                    .findViewById(BESTELLUNGENID);
            EditText einkaufspreisView = (EditText) tr
                    .findViewById(EINKAUFSPREISID);
            EditText verkaufspreisView = (EditText) tr
                    .findViewById(VERKAUFSPREISID);
            int bestellungen = 0;
            double einkaufspreis = 0, verkaufspreis = 0;
            bestellungen = ((bestellungenView.getText().toString()).equals("") ? 0
                    : Integer.valueOf(bestellungenView.getText().toString()));
            einkaufspreis = ((einkaufspreisView.getText().toString())
                    .equals("") ? 0 : Double.valueOf(einkaufspreisView
                    .getText().toString()));
            verkaufspreis = ((verkaufspreisView.getText().toString())
                    .equals("") ? 0 : Double.valueOf(verkaufspreisView
                    .getText().toString()));

            // Khi Bestellung leer thi cac Felder khac ko can phai thay doi
            if (!bestellungenView.getText().toString().equals("")
                    || view.getId() == BESTELLUNGENID) {

                if (view.getId() == BESTELLUNGENID
                        && !bestellungenView.getText().toString().equals(""))
                    indexList.add(index);

                TextView totalView = (TextView) tr.findViewById(TOTALID);
                TextView nettoUmsatzView = (TextView) tr
                        .findViewById(NETTOUMSATZID);
                TextView nettoEinkaufView = (TextView) tr
                        .findViewById(NETTOEINKAUFID);
                TextView bruttoEinkaufView = (TextView) tr
                        .findViewById(BRUTTOEINKAUFID);
                TextView bruttoUmsatzView = (TextView) tr
                        .findViewById(BRUTTOUMSATZID);
                TextView wareneinsatzView = (TextView) tr
                        .findViewById(WARENEINSATZID);
                TextView nettoUmsatzssummeView = (TextView) findViewById(R.id.nettoUmsatzSumme);
                TextView einkaufssummeView = (TextView) findViewById(R.id.nettoEinkaufssumme);

                GetraenkeModel getraenke = getraenkeList.get(index);

                double totalBestellung = (bestellungen * getraenke
                        .getEinheitProBestellung());
                totalView.setText(String.valueOf(totalBestellung));

                double verkaufsmenge = totalBestellung
                        - (totalBestellung * getraenke.getSchwund());

                double portion = (getraenke.getEinheitProGlas() > 0 ? verkaufsmenge
                        / getraenke.getEinheitProGlas()
                        : 0);

                double nettoUmsatz;
                if (getraenke.getEinheitProGlas() == 0.0)
                    nettoUmsatz = 0.0;
                else {
                    nettoUmsatz = portion * verkaufspreis;
                }
                nettoUmsatzView.setText(df.format(nettoUmsatz));

                double nettoEinkauf = bestellungen * einkaufspreis;
                nettoEinkaufView.setText(df.format(nettoEinkauf));

                double bruttoEinkauf = nettoEinkauf * 1.19;
                bruttoEinkaufView.setText(df.format(bruttoEinkauf));

                double bruttoUmsatz = nettoUmsatz * 1.19;
                bruttoUmsatzView.setText(df.format(bruttoUmsatz));

                double wareneinsatz = nettoEinkauf / nettoUmsatz;
                wareneinsatzView.setText(df.format(wareneinsatz * 100) + "%");

                TableLayout tl = (TableLayout) tr.getParent();
                nettoUmsatzsumme = 0;
                nettoEinkaufssumme = 0;
                for (int j : indexList) {
                    TableRow thisTr = (TableRow) tl.getChildAt(j + 1);
                    if (((TextView) thisTr.findViewById(NETTOUMSATZID))
                            .getText() != "") {
                        double thisNettoUmsatz = Double
                                .valueOf(((String) ((TextView) thisTr
                                        .findViewById(NETTOUMSATZID)).getText())
                                        .replace(",", "."));
                        nettoUmsatzsumme += thisNettoUmsatz;
                    }
                    if (((TextView) thisTr.findViewById(NETTOEINKAUFID))
                            .getText() != "") {
                        double thisEinkaufTotal = Double
                                .valueOf(((String) ((TextView) thisTr
                                        .findViewById(NETTOEINKAUFID))
                                        .getText()).replace(",", "."));
                        nettoEinkaufssumme += thisEinkaufTotal;
                    }
                }

                for (int j : indexList) {
                    TableRow thisTr = (TableRow) tl.getChildAt(j + 1);
                    TextView thisAnteilView = (TextView) thisTr
                            .findViewById(ANTEILID);
                    if (((TextView) thisTr.findViewById(NETTOUMSATZID))
                            .getText() != "") {
                        double thisNettoUmsatz = Double
                                .valueOf(((String) ((TextView) thisTr
                                        .findViewById(NETTOUMSATZID)).getText())
                                        .replace(",", "."));

                        double thisAnteil;
                        if (thisNettoUmsatz == 0)
                            thisAnteil = 0.0;
                        else
                            thisAnteil = thisNettoUmsatz / nettoUmsatzsumme;
                        String gerundeteAnteil = prozentZahl
                                .format(thisAnteil * 100);
                        thisAnteilView.setText(gerundeteAnteil + "%");
                    }
                }

                if (this.bestellungBefore != 0) {
                    double totalBestellungBefore = (bestellungBefore * getraenke
                            .getEinheitProBestellung());

                    double verkaufsmengeBefore = totalBestellungBefore
                            - (totalBestellungBefore * getraenke.getSchwund());

                    double portionBefore = verkaufsmengeBefore
                            / getraenke.getEinheitProGlas();
                    portionSumme -= portionBefore;
                }
                portionSumme += portion;

                nettoUmsatzssummeView
                        .setText(df.format(nettoUmsatzsumme) + "€");
                einkaufssummeView.setText(df.format(nettoEinkaufssumme) + "€");
                String proBestellung = ((TextView) tr
                        .findViewById(PROBESTELLUNGID)).getText().toString();
                GetraenkeBestellungModel fb = new GetraenkeBestellungModel(
                        getraenke, proBestellung, bestellungen,
                        totalBestellung, portion, einkaufspreis, nettoEinkauf,
                        bruttoEinkauf, verkaufspreis, nettoUmsatz,
                        bruttoUmsatz, wareneinsatz);

                if (!getraenkeBestellungenSet.add(fb)) {
                    getraenkeBestellungenSet.remove(fb);
                    getraenkeBestellungenSet.add(fb);
                    indexList.remove(index);
                }
            }
            // Khi Bestellung bi xoa thanh leer thi xoa luon fb trong Set
            if (bestellungenView.getText().toString().equals("")
                    && view.getId() == BESTELLUNGENID) {
                GetraenkeModel getraenke = getraenkeList.get(index);
                GetraenkeBestellungModel fb = new GetraenkeBestellungModel(
                        getraenke);
                getraenkeBestellungenSet.remove(fb);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            if (this.view.getId() == BESTELLUNGENID) {
                if (!s.toString().equals("")) {
                    this.bestellungBefore = Integer.valueOf(s.toString());
                } else {
                    this.bestellungBefore = 0;
                }
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getraenkeSaveBtn: {
                if (datumGetraenkeBestellungView.getText().equals("")) {
                    Toast.makeText(this, "Bitte Datum auswählen", Toast.LENGTH_LONG)
                            .show();
                } else {
                    bestellungsdatum = datumGetraenkeBestellungView.getText()
                            .toString();
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                    Date d = null;
                    try {
                        d = format.parse(bestellungsdatum);
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    calendar = Calendar.getInstance();
                    calendar.setTime(d);
                    daysFrom1970 = (int) (calendar.getTimeInMillis() / (1000 * 3600 * 24));

                    getraenkeBestellungenList = new ArrayList<GetraenkeBestellungModel>(
                            getraenkeBestellungenSet);
                    Collections
                            .sort(getraenkeBestellungenList, getraenkeComparator);

                    if (!getraenkeBestellungenList.isEmpty()) {
                        new AlertDialog.Builder(this)
                                .setTitle("Wollen Sie die Daten speichern und den Tabelle verlassen?")
                                .setNegativeButton("Nein", null)
                                .setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        saveData();
                                        Intent returnIntent = new Intent();
                                        returnIntent.putExtra("bestellungID", bestellungID);
                                        returnIntent.putExtra("datum", bestellungsdatum);
                                        returnIntent.putExtra("daysFrom1970", daysFrom1970);
                                        setResult(RESULT_OK, returnIntent);
                                        finish();
                                    }

                                }).create().show();



                    } else {
                        Toast.makeText(this, "Nichts zu speichern",
                                Toast.LENGTH_LONG).show();
                    }
                }
                break;
            }
            case R.id.datumBtn: {
                DialogFragment newFragment = new DatePickerFragment(
                        datumGetraenkeBestellungView);
                newFragment.show(getSupportFragmentManager(), "datePicker");
                break;
            }
            case R.id.zuFleischButton: {
                new AlertDialog.Builder(this)
                        .setTitle("Wollen Sie den Tabelle wirklich verlassen?")
                        .setMessage("Die Daten werden nicht gespeichert.")
                        .setNegativeButton("Nein", null)
                        .setPositiveButton("JA", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                startActivity(new Intent(getApplicationContext(), FleischActivity.class));
                            }

                        }).create().show();
            }
            default:
                break;
        }
    }

    private void saveData(){
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        bestellungID = UUID.randomUUID().toString();
        for (GetraenkeBestellungModel i : getraenkeBestellungenList) {
            getraenkeEinkaufPreisMap.put(i.getGetraenkeModel()
                    .getArtikelName(), i.getEinkaufspreis());
        }
        GetraenkeEinkaufPreisXmlWriter fepw = new GetraenkeEinkaufPreisXmlWriter();
        GetraenkeBestellungenXmlWriter fbw = new GetraenkeBestellungenXmlWriter();
        try {
            fbw.writeGetraenkeBestellungenXml(
                    getraenkeBestellungenList, bestellungID,
                    portionSumme, nettoUmsatzsumme,
                    nettoEinkaufssumme, bestellungsdatum,
                    daysFrom1970);
            fepw.writeGetraenkeEinkaufpreisXml(
                    getraenkeEinkaufPreisMap, month, year);
        } catch (TransformerFactoryConfigurationError e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Comparator<GetraenkeBestellungModel> getraenkeComparator = new Comparator<GetraenkeBestellungModel>() {

        @Override
        public int compare(GetraenkeBestellungModel lhs,
                           GetraenkeBestellungModel rhs) {
            return Integer
                    .valueOf(lhs.getGetraenkeModel().getOrder())
                    .compareTo(
                            Integer.valueOf(rhs.getGetraenkeModel().getOrder()));
        }
    };

}
