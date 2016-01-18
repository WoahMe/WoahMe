package woahme.teamwork.com.woahme;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import woahme.teamwork.com.woahme.Http.SingletonRequestQueue;
import woahme.teamwork.com.woahme.Storage.PlaceDbHelper;

public class DetailsActivity extends AppCompatActivity
    implements Button.OnClickListener{
    TextView titleView;
    NetworkImageView imageView;
    TextView descriptionView;
    TextView creatorView;
    TextView locationName;
    Button markVisited;

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

        this.markVisited = (Button) findViewById(R.id.mark_visited_button);
        markVisited.setOnClickListener(this);

        titleView.setText(detailsBundle.getString("placeTitle"));
        imageView.setImageUrl(detailsBundle.getString("placeImage"), SingletonRequestQueue.getInstance(this).getImageLoader());
        descriptionView.setText("More about the place:\n" + detailsBundle.getString("placeDescription"));
        creatorView.setText("By: " + detailsBundle.getString("placeCreator"));
        locationName.setText("Where: " + detailsBundle.getString("placeLocationName"));

        // WHEN THE USER SWIPES DOWN OR DOES SOME FUNKY GESTURE => FINISH() ACTIVITY!
    }

    @Override
    public void onClick(View v) {
        PlaceDbHelper helper = new PlaceDbHelper(this);
        helper.add(
                this.titleView.getText().toString(),
                "",
                this.imageView.getImageURL(),
                this.descriptionView.getText().toString(),
                this.creatorView.getText().toString(),
                this.locationName.getText().toString(),
                "",
                "",
                ""
                );

        playAlertTone(this);
    }

    public  void playAlertTone(final Context context){
        Thread thread = new Thread(){
            public void run(){
                MediaPlayer player = null;
                int countBeep = 0;
                while(countBeep<1){
                    player = MediaPlayer.create(context,R.raw.beep);
                    player.start();
                    countBeep+=1;
                    try {

                        // 100 milisecond is duration gap between two beep
                        Thread.sleep(player.getDuration()+100);
                        player.release();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                }
            }
        };

        thread.start();

    }
}
