package woahme.teamwork.com.woahme;

public class Endpoints {
    private static final String BaseUrl = "https://ancient-temple-4432.herokuapp.com";

    public static final String LoginEndpoint = BaseUrl + "/api/users/token";

    public static final String RegisterEndPoint = BaseUrl + "/api/users/register";

    public static final String PlacesEndPoint = BaseUrl + "/api/places";
}
