package jp.co.ryoto1993.httpledcontroller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ColorSelectorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ColorSelectorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ColorSelectorFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    private OnFragmentInteractionListener mListener;

    SeekBar seekBarRed, seekBarGreen, seekBarBlue;

    public ColorSelectorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ColorSelectorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ColorSelectorFragment newInstance() {
        ColorSelectorFragment fragment = new ColorSelectorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_color_selector, container, false);

        seekBarRed = rootView.findViewById(R.id.seekBarRed);
        seekBarRed.setOnSeekBarChangeListener(this);

        seekBarGreen = rootView.findViewById(R.id.seekBarGreen);
        seekBarGreen.setOnSeekBarChangeListener(this);

        seekBarBlue = rootView.findViewById(R.id.seekBarBlue);
        seekBarBlue.setOnSeekBarChangeListener(this);

        // Inflate the layout for this fragment
        return rootView;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onColorSelectorFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        TextView textView = null;
        switch (seekBar.getId()) {
            case R.id.seekBarRed:
                textView = getActivity().findViewById(R.id.labelRedValue);
                break;
            case R.id.seekBarGreen:
                textView = getActivity().findViewById(R.id.labelGreenValue);
                break;
            case R.id.seekBarBlue:
                textView = getActivity().findViewById(R.id.labelBlueValue);
                break;
        }
        if (textView != null) {
            textView.setText(String.valueOf(i));
        }
        
        mListener.onColorChanged(seekBarRed.getProgress(), seekBarGreen.getProgress(), seekBarBlue.getProgress());
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onColorSelectorFragmentInteraction(Uri uri);

        void onColorChanged(int r, int g, int b);

    }
}
