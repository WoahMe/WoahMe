package woahme.teamwork.com.woahme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dd.morphingbutton.MorphingButton;
import com.dd.morphingbutton.impl.IndeterminateProgressButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import woahme.teamwork.com.woahme.APIs.Imgur.ImgurRequest;
import woahme.teamwork.com.woahme.APIs.Imgur.ImgurResponseModel;
import woahme.teamwork.com.woahme.APIs.LocationManager.GpsLocationManager;
import woahme.teamwork.com.woahme.Http.SingletonRequestQueue;
import woahme.teamwork.com.woahme.Utilities.BitmapUtils;
import woahme.teamwork.com.woahme.Utilities.HttpUtils;
import woahme.teamwork.com.woahme.Utilities.Notificator;
import woahme.teamwork.com.woahme.Utilities.SharedPreferencesManager;

public class AddPlaceFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    IndeterminateProgressButton button;
    ImageView imageView;
    EditText titleView;
    EditText descriptionView;

    public AddPlaceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_place, container, false);

        button = (IndeterminateProgressButton) view.findViewById(R.id.add_place_button);
        button.setOnClickListener(this);
        button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        imageView = (ImageView) view.findViewById(R.id.add_place_image);
        imageView.setOnClickListener(this);

        titleView = (EditText) view.findViewById(R.id.add_place_title);
        descriptionView = (EditText) view.findViewById(R.id.add_place_description);

        return view;
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.add_place_button) {
            morphToSuccess(button, 1000);
            String title = titleView.getText().toString();
            String description = titleView.getText().toString();

            uploadToImgur(((BitmapDrawable)imageView.getDrawable()).getBitmap());

            // send request to /api/places
            // notify success
            // redirect to ALL
        } else if (viewId == R.id.add_place_image) {
            takePhoto();
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

    public void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }

    public void uploadToImgur(Bitmap bitmap) {
        String imageBase64 = BitmapUtils.toBase64(bitmap);
        JSONObject body = ImgurRequest.generateUploadRequestBody(imageBase64);

        SingletonRequestQueue.getInstance(getContext()).addToRequestQueue(
                new ImgurRequest(
                        Request.Method.POST,
                        Endpoints.ImgurUploadEndpoint,
                        body,
                        getPhotoUploadResponseListener(),
                        SingletonRequestQueue.GetDefaultErrorListener()
                )
        );
    }

    public Response.Listener getPhotoUploadResponseListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String success = response.get("success").toString();
                    if (!Boolean.parseBoolean(success)) {
                        // Handle error
                    } else {
                        List<ImgurResponseModel> items =
                            HttpUtils.ParseJsonResponse(
                                response.get("data").toString(),
                                ImgurResponseModel.class);

                        String imgurPhotoUrl = items.get(0).getLink();
                        String cityName = getCityName();
                        String title = titleView.getText().toString();
                        String description = descriptionView.getText().toString();
                        String creator = SharedPreferencesManager.getUsername(getContext());

                        UploadPlace(imgurPhotoUrl, cityName, title, description, creator);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public String getCityName() {
        GpsLocationManager gps = new GpsLocationManager(getContext());

        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            Geocoder gcd = new Geocoder(getContext(), Locale.getDefault());
            try {
                List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1);
                gps.stopUsingGPS();
                return addresses.get(0).getLocality();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            gps.showSettingsAlert();
        }

        return null;
    }

    public void UploadPlace(String url, String cityName, String title, String description, String creator) {
        SingletonRequestQueue.getInstance(getContext()).addToRequestQueue(new JsonObjectRequest(
                Request.Method.POST,
                Endpoints.PlacesEndPoint,
                buildRequestBody(url, cityName, title, description, creator),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Notificator.Notify(getContext(), "WoahMe", "Great job! You did it!");
                    }
                },
                SingletonRequestQueue.GetDefaultErrorListener()
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String,String>();
                headers.put("Authorization", "Bearer " + SharedPreferencesManager.getToken(getContext()));
                return headers;
            }
        });
    }

    public JSONObject buildRequestBody(String url, String cityName, String title, String description, String creator) {
        JSONObject geoOrientation = new JSONObject();
        JSONObject location = new JSONObject();
        JSONObject whole = new JSONObject();

        try {
            geoOrientation.put("azimuth", 0);
            geoOrientation.put("pitch", 0);
            geoOrientation.put("roll", 0);

            location.put("name", cityName);
            location.put("geoOrientation", geoOrientation);

            whole.put("imageSource", url);
            whole.put("imageOrientation", "Horizontal");
            whole.put("title", title);
            whole.put("description", description);
            whole.put("location", location);
            whole.put("creator", creator);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return whole;
    }
}
