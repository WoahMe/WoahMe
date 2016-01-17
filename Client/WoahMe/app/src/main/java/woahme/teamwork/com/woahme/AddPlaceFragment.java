package woahme.teamwork.com.woahme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dd.morphingbutton.MorphingButton;
import com.dd.morphingbutton.impl.IndeterminateProgressButton;
import com.google.android.gms.identity.intents.AddressConstants;

public class AddPlaceFragment extends Fragment implements View.OnClickListener {

    IndeterminateProgressButton button;

    public AddPlaceFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_place, container, false);

        button = (IndeterminateProgressButton) view.findViewById(R.id.add_place_button);
        button.setOnClickListener(this);
        button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        return view;
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.add_place_button) {
            morphToSuccess(button, 1000);
            // send image to imgur
            // get location
            // send request to /api/places
            // notify success
            // redirect to ALL
        }
    }

    private void morphToSuccess(final IndeterminateProgressButton btnMorph, int duration) {
        int height = getResources().getDrawable(R.drawable.ic_added).getIntrinsicHeight();
        int width = getResources().getDrawable(R.drawable.ic_added).getIntrinsicWidth();
        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(duration)
                .cornerRadius(width)
                .width(width)
                .height(height)
                .icon(R.drawable.ic_added);
        btnMorph.morph(circle);
    }
}
