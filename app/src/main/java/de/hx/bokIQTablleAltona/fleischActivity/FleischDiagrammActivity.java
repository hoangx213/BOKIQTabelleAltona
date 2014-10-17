package de.hx.bokIQTablleAltona.fleischActivity;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;

import de.hx.bokIQTablleAltona.R;

public class FleischDiagrammActivity extends Activity {

    PieChart diagrammView;
    private Segment fleischSegment, enteSegment, fischSegment, sushifischSegment, sonstigeSegment;
    private double fleischUmsatz = 0, enteUmsatz = 0, fischUmsatz = 0, sushifischUmsatz = 0, sonstigeUmsatz = 0;
    private double totalUmsatz = 0;
    LinearLayout fleischInfos;
    TextView fleischUmsatzTextView, enteUmsatzTextView, fischUmsatzTextView, sushifischUmsatzTextView, sonstigeUmsatzTextView, totalUmsatzTextView;
    TextView fleischAnteilTextView, enteAnteilTextView, fischAnteilTextView, sushifischAnteilTextView, sonstigeAnteilTextView;
    DecimalFormat prozentZahl = new DecimalFormat("#.#");
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pie_chart);
        diagrammView = (PieChart) findViewById(R.id.diagrammView);
        fleischInfos = (LinearLayout) findViewById(R.id.fleischInfos);
        fleischInfos.setVisibility(View.VISIBLE);
        fleischUmsatzTextView = (TextView) findViewById(R.id.diagrammFleischNettoUmsatz);
        enteUmsatzTextView = (TextView) findViewById(R.id.diagrammEnteNettoUmsatz);
        fischUmsatzTextView = (TextView) findViewById(R.id.diagrammFischNettoUmsatz);
        sushifischUmsatzTextView = (TextView) findViewById(R.id.diagrammSushifischNettoUmsatz);
        sonstigeUmsatzTextView = (TextView) findViewById(R.id.diagrammFleischSonstigeNettoUmsatz);
        totalUmsatzTextView = (TextView) findViewById(R.id.diagrammFleischTotalNettoUmsatz);
        fleischAnteilTextView = (TextView) findViewById(R.id.diagrammFleischNettoUmsatzAnteil);
        enteAnteilTextView = (TextView) findViewById(R.id.diagrammEnteNettoUmsatzAnteil);
        fischAnteilTextView = (TextView) findViewById(R.id.diagrammFischNettoUmsatzAnteil);
        sushifischAnteilTextView = (TextView) findViewById(R.id.diagrammSushifischNettoUmsatzAnteil);
        sonstigeAnteilTextView = (TextView) findViewById(R.id.diagrammFleischSonstigeNettoUmsatzAnteil);

        diagrammView.setTitle("Fleisch Diagramm");
        Intent intent = getIntent();
        fleischUmsatz = intent.getDoubleExtra("fleischUmsatz", 0);
        enteUmsatz = intent.getDoubleExtra("enteUmsatz", 0);
        fischUmsatz = intent.getDoubleExtra("fischUmsatz", 0);
        sushifischUmsatz = intent.getDoubleExtra("sushifischUmsatz", 0);
        sonstigeUmsatz = intent.getDoubleExtra("sonstigeUmsatz", 0);
        totalUmsatz = fleischUmsatz + enteUmsatz + fischUmsatz + sushifischUmsatz + sonstigeUmsatz;

        fleischSegment = new Segment("Fleisch", fleischUmsatz);
        enteSegment = new Segment("Ente", enteUmsatz);
        fischSegment = new Segment("Fisch", fischUmsatz);
        sushifischSegment = new Segment("Sushifisch", sushifischUmsatz);
        sonstigeSegment = new Segment("Sonstige", sonstigeUmsatz);

        EmbossMaskFilter emf = new EmbossMaskFilter(new float[]{1, 1, 1},
                0.4f, 10, 8.2f);

        SegmentFormatter sf1 = new SegmentFormatter();
        sf1.configure(getApplicationContext(), R.xml.pie_segment_fleisch);
        sf1.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf2 = new SegmentFormatter();
        sf2.configure(getApplicationContext(), R.xml.pie_segment_ente);
        sf2.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf3 = new SegmentFormatter();
        sf3.configure(getApplicationContext(), R.xml.pie_segment_fisch);
        sf3.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf4 = new SegmentFormatter();
        sf4.configure(getApplicationContext(), R.xml.pie_segment_wein);
        sf4.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf5 = new SegmentFormatter();
        sf5.configure(getApplicationContext(), R.xml.pie_segment_sonstige);
        sf5.getFillPaint().setMaskFilter(emf);

        if (fleischUmsatz > 0)
            diagrammView.addSeries(fleischSegment, sf1);
        if (enteUmsatz > 0)
            diagrammView.addSeries(enteSegment, sf2);
        if (fischUmsatz > 0)
            diagrammView.addSeries(fischSegment, sf3);
        if (sushifischUmsatz > 0)
            diagrammView.addSeries(sushifischSegment, sf4);
        if (sonstigeUmsatz > 0)
            diagrammView.addSeries(sonstigeSegment, sf5);

        diagrammView.getBorderPaint().setColor(Color.TRANSPARENT);
        diagrammView.getBackgroundPaint().setColor(Color.TRANSPARENT);

        fleischUmsatzTextView.setText(df.format(fleischUmsatz) + "€");
        enteUmsatzTextView.setText(df.format(enteUmsatz) + "€");
        fischUmsatzTextView.setText(df.format(fischUmsatz) + "€");
        sushifischUmsatzTextView.setText(df.format(sushifischUmsatz) + "€");
        sonstigeUmsatzTextView.setText(df.format(sonstigeUmsatz) + "€");
        totalUmsatzTextView.setText(df.format(totalUmsatz) + "€");

        fleischAnteilTextView.setText(prozentZahl.format(100 * fleischUmsatz / totalUmsatz) + "%");
        enteAnteilTextView.setText(prozentZahl.format(100 * enteUmsatz / totalUmsatz) + "%");
        fischAnteilTextView.setText(prozentZahl.format(100 * fischUmsatz / totalUmsatz) + "%");
        sushifischAnteilTextView.setText(prozentZahl.format(100 * sushifischUmsatz / totalUmsatz) + "%");
        sonstigeAnteilTextView.setText(prozentZahl.format(100 * sonstigeUmsatz / totalUmsatz) + "%");
    }

}
