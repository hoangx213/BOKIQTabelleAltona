package de.hx.bokIQTablleAltona;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;

import de.hx.bokIQTablleAltona.fleischActivity.FleischZwischenBerichtActivity;
import de.hx.bokIQTablleAltona.getraenkeActivity.GetraenkeZwischenBerichtActivity;

public class GesamtDiagrammActivity extends Activity implements OnClickListener {

    PieChart diagrammView;
    private Segment fleischSegment, getraenkeSegment;
    private double fleischUmsatz = 0, getraenkeUmsatz = 0;
    private double totalUmsatz = 0;
    int vonDaysFrom1970, bisDaysFrom1970;
    LinearLayout infos;
    Button fleischDetailBtn, getraenkeDetailBtn;
    TextView fleischGesamtUmsatzTextView, getraenkeGesamtUmsatzTextView, totalUmsatzTextView;
    TextView fleischGesamtUmsatzAnteilTextView, getraenkeGesamtUmsatzAnteilTextView;
    DecimalFormat prozentZahl = new DecimalFormat("#.#");
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pie_chart);
        diagrammView = (PieChart) findViewById(R.id.diagrammView);
        infos = (LinearLayout) findViewById(R.id.gesamtInfos);
        infos.setVisibility(View.VISIBLE);
        fleischGesamtUmsatzTextView = (TextView) findViewById(R.id.diagrammFleischGesamtNettoUmsatz);
        getraenkeGesamtUmsatzTextView = (TextView) findViewById(R.id.diagrammGetraenkeGesamtNettoUmsatz);
        totalUmsatzTextView = (TextView) findViewById(R.id.diagrammTotalGesamtNettoUmsatz);
        fleischGesamtUmsatzAnteilTextView = (TextView) findViewById(R.id.diagrammFleischGesamtNettoUmsatzAnteil);
        getraenkeGesamtUmsatzAnteilTextView = (TextView) findViewById(R.id.diagrammGetraenkeGesamtNettoUmsatzAnteil);
        fleischDetailBtn = (Button) findViewById(R.id.toFleischEndbericht);
        getraenkeDetailBtn = (Button) findViewById(R.id.toGetraenkeEndbericht);

        fleischDetailBtn.setOnClickListener(this);
        getraenkeDetailBtn.setOnClickListener(this);

        diagrammView.setTitle("Gesamte Diagramm");
        Intent intent = getIntent();
        fleischUmsatz = intent.getDoubleExtra("nettoFUmsatzTotal", 0);
        getraenkeUmsatz = intent.getDoubleExtra("nettoGUmsatzTotal", 0);
        vonDaysFrom1970 = intent.getIntExtra("vonDaysFrom1970", 0);
        bisDaysFrom1970 = intent.getIntExtra("bisDaysFrom1970", 0);
        totalUmsatz = fleischUmsatz + getraenkeUmsatz;

        fleischSegment = new Segment("Fleisch", fleischUmsatz);
        getraenkeSegment = new Segment("Getränke", getraenkeUmsatz);


        EmbossMaskFilter emf = new EmbossMaskFilter(new float[]{1, 1, 1},
                0.4f, 10, 8.2f);

        SegmentFormatter sf1 = new SegmentFormatter();
        sf1.configure(getApplicationContext(), R.xml.pie_segment_fleisch);
        sf1.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf2 = new SegmentFormatter();
        sf2.configure(getApplicationContext(), R.xml.pie_segment_premix_und_wasser);
        sf2.getFillPaint().setMaskFilter(emf);


        if (fleischUmsatz > 0)
            diagrammView.addSeries(fleischSegment, sf1);
        if (getraenkeUmsatz > 0)
            diagrammView.addSeries(getraenkeSegment, sf2);

        diagrammView.getBorderPaint().setColor(Color.TRANSPARENT);
        diagrammView.getBackgroundPaint().setColor(Color.TRANSPARENT);

        fleischGesamtUmsatzTextView.setText(df.format(fleischUmsatz) + "€");
        getraenkeGesamtUmsatzTextView.setText(df.format(getraenkeUmsatz) + "€");
        totalUmsatzTextView.setText(df.format(totalUmsatz) + "€");

        fleischGesamtUmsatzAnteilTextView.setText(prozentZahl.format(100 * fleischUmsatz / totalUmsatz) + "%");
        getraenkeGesamtUmsatzAnteilTextView.setText(prozentZahl.format(100 * getraenkeUmsatz / totalUmsatz) + "%");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toFleischEndbericht:
                Intent intentFleisch = new Intent(this,
                        FleischZwischenBerichtActivity.class);
                intentFleisch.putExtra("vonDaysFrom1970", vonDaysFrom1970);
                intentFleisch.putExtra("bisDaysFrom1970", bisDaysFrom1970);
                intentFleisch.putExtra("berichtTyp", "endbericht");
                startActivity(intentFleisch);
                break;
            case R.id.toGetraenkeEndbericht:
                Intent intentGetraenke = new Intent(this,
                        GetraenkeZwischenBerichtActivity.class);
                intentGetraenke.putExtra("vonDaysFrom1970", vonDaysFrom1970);
                intentGetraenke.putExtra("bisDaysFrom1970", bisDaysFrom1970);
                intentGetraenke.putExtra("berichtTyp", "endbericht");
                startActivity(intentGetraenke);
                break;
        }
    }
}
