package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment5;

/**
 * Created by Dharak on 1/20/2017.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;


public class ControlFragment extends Fragment {
    View done;
    TextView textOutput;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(
                        R.layout.wordfragment_control, container, false);
        View main = rootView.findViewById(R.id.wordbutton_main);
        View restart = rootView.findViewById(R.id.wordbutton_restart);

        View Button=rootView.findViewById(R.id.wordbutton_done);
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GameActivity) getActivity()).Done();
            }
        });
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GameActivity) getActivity()).restartGame();
            }
        });
        return rootView;
    }

}