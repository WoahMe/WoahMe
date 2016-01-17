package woahme.teamwork.com.woahme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.dd.morphingbutton.MorphingButton;
import com.dd.morphingbutton.impl.IndeterminateProgressButton;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

import woahme.teamwork.com.woahme.APIs.Imgur.ImgurRequest;
import woahme.teamwork.com.woahme.APIs.Imgur.ImgurResponseModel;
import woahme.teamwork.com.woahme.Http.SingletonRequestQueue;
import woahme.teamwork.com.woahme.Utilities.BitmapUtils;
import woahme.teamwork.com.woahme.Utilities.HttpUtils;

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


            // send image to imgur
            // get location
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

            uploadToImgur(imageBitmap);
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
                        Log.d("New image url:", imgurPhotoUrl);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
