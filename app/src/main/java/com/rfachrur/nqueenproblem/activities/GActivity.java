package com.rfachrur.nqueenproblem.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rfachrur.nqueenproblem.R;
import com.rfachrur.nqueenproblem.geneticsalgorithm.Chromosome;
import com.rfachrur.nqueenproblem.geneticsalgorithm.GAController;
import com.rfachrur.nqueenproblem.geneticsalgorithm.ObjectReference;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by FACHRUR on 10/6/2016.
 *
 */

public class GActivity extends AppCompatActivity {

    GAController geneticAlgo;
    ImageView[] queens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ga);
        initialCellsID();
        TextView txt = (TextView) findViewById(R.id.txtState);
        assert txt != null;
        txt.setText("Using Genetic Algorithm");
        Button btn = (Button) findViewById(R.id.btnShowRandomAnswer);
        assert btn != null;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                geneticAlgo = new GAController();
                // setting the genetic algorithm parameters
                int population = 10;
                int generations = 100;
                double crossoverProbability = 0.70;
                double mutationProbability = 0.01;

                ArrayList<Chromosome> initPopulation = getInitialPopulation(population);
                ObjectReference<ArrayList<Chromosome>> tempRef_initPopulation = new ObjectReference<>(initPopulation);
                geneticAlgo.DoMating(tempRef_initPopulation, generations, crossoverProbability, mutationProbability);
                initPopulation = tempRef_initPopulation.value;
                while (initPopulation.get(0).fitness != GAController.MAX_FIT) {
                    geneticAlgo.DoMating(tempRef_initPopulation, generations, crossoverProbability, mutationProbability);
                    initPopulation = tempRef_initPopulation.value;
                }
                showRandomAnswer(initPopulation.get(0));
            }
        });
    }

    private ArrayList<Chromosome> getInitialPopulation(int population) {
        ArrayList<Chromosome> initPop = new ArrayList<>();
        GAController RandomGen = new GAController();
        for (int i = 0; i < population; i++) {
            ArrayList<Integer> genes = new ArrayList<>(Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7}));
            Chromosome chromosome = new Chromosome();
            chromosome.genes = new int[8];
            for (int j = 0; j < 8; j++) {
                int geneIndex = (int) (RandomGen.GetRandomVal(0, genes.size() - 1) + 0.5);
                chromosome.genes[j] = genes.get(geneIndex);
                genes.remove(geneIndex);
            }
            initPop.add(chromosome.clone());
        }
        return initPop;
    }


    void showRandomAnswer(Chromosome chrome) {
        ViewGroup.MarginLayoutParams marginParams;
        for (int i = 0; i < 8; i++) {
            if (queens[i] != null) {
                marginParams = (ViewGroup.MarginLayoutParams) queens[i].getLayoutParams();
                DisplayMetrics dm = queens[i].getResources().getDisplayMetrics();
                marginParams.setMargins(
                        convertDpToPx(38 * i, dm),
                        convertDpToPx(38 * chrome.genes[i], dm),
                        0, 0);
                queens[i].setLayoutParams(marginParams);
                queens[i].setLayoutParams(marginParams);
            }
        }
    }

    void initialCellsID() {
        queens = new ImageView[8];
        queens[0] = (ImageView) findViewById(R.id.queen0);
        queens[1] = (ImageView) findViewById(R.id.queen1);
        queens[2] = (ImageView) findViewById(R.id.queen2);
        queens[3] = (ImageView) findViewById(R.id.queen3);
        queens[4] = (ImageView) findViewById(R.id.queen4);
        queens[5] = (ImageView) findViewById(R.id.queen5);
        queens[6] = (ImageView) findViewById(R.id.queen6);
        queens[7] = (ImageView) findViewById(R.id.queen7);
    }

    private int convertDpToPx(int dp, DisplayMetrics displayMetrics) {
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
        return Math.round(pixels);
    }

}
