package woahme.teamwork.com.woahme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import woahme.teamwork.com.woahme.Http.SingletonRequestQueue;

public class DetailsActivity extends AppCompatActivity {
    TextView titleView;
    NetworkImageView imageView;
    TextView descriptionView;
    TextView creatorView;
    TextView locationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        this.titleView = (TextView) findViewById(R.id.place_details_title);
        this.imageView = (NetworkImageView) findViewById(R.id.place_details_image);
        this.descriptionView = (TextView) findViewById(R.id.place_details_description);
        this.creatorView = (TextView) findViewById(R.id.place_details_creator);
        this.locationName = (TextView) findViewById(R.id.place_details_location_name);
        Bundle detailsBundle = getIntent().getExtras();

        titleView.setText(detailsBundle.getString("placeTitle"));
        imageView.setImageUrl(detailsBundle.getString("placeImage"), SingletonRequestQueue.getInstance(this).getImageLoader());
        descriptionView.setText("More about the place:\n" + detailsBundle.getString("placeDescription"));
        creatorView.setText("By: " + detailsBundle.getString("placeCreator"));
        locationName.setText("Where: " + detailsBundle.getString("placeLocationName"));

        // WHEN THE USER SWIPES DOWN OR DOES SOME FUNKY GESTURE => FINISH() ACTIVITY!
    }
}
