package com.rfachrur.ratu.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rfachrur.ratu.R;
import com.rfachrur.ratu.backtracking.BackTrackController;



public class BackTrackActivity extends AppCompatActivity {

    BackTrackController solver;
    ImageView[] queens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backtrack);

        // solve the problem
        solver = new BackTrackController(8);

        // initiation the queens ImageView id
        initialCellsID();

        Log.w("TAG", solver.getNumberOfSolutions()+"");

        // showing the number of solutions
        TextView txt = (TextView) findViewById(R.id.txtState);
        txt.setText(solver.getNumberOfSolutions() + " State(s)");

        Button btn = (Button) findViewById(R.id.btnShowRandomAnswer);
        if (btn != null) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showRandomAnswer();
                }
            });
        }
    }

    void showRandomAnswer() {
        int index = (int) (Math.random() * solver.getNumberOfSolutions());
        int[] selectedSolution = solver.getSolutions()[index];
        ViewGroup.MarginLayoutParams marginParams;
        for (int i = 0; i < 8; i++) {
            if (queens[i] != null) {
                marginParams = (ViewGroup.MarginLayoutParams) queens[i].getLayoutParams();
                DisplayMetrics dm = queens[i].getResources().getDisplayMetrics();
                marginParams.setMargins(
                        convertDpToPx(38 * i, dm),
                        convertDpToPx(38 * (selectedSolution[i] - 1), dm),
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
