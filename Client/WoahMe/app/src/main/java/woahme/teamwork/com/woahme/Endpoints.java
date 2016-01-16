package woahme.teamwork.com.woahme;

/**
 * Created by newmast on 1/16/2016.
 */
public class Endpoints {
    private static final String BaseUrl = "http://192.168.0.195:15334";

    public static final String LoginEndpoint = BaseUrl + "/token";

    public static final String RegisterEndPoint = BaseUrl + "/api/Account/Register";

    public static final String PlacesEndPoint = BaseUrl + "/api/places";
}
