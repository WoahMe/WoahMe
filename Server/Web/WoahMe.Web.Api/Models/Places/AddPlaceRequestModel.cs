namespace WoahMe.Web.Api.Models.Places
{
    public class AddPlaceRequestModel
    {
        public string ImageSource { get; set; }

        public string ImageOrientation { get; set; }

        public string Title { get; set; }

        public string Description { get; set; }

        public double Azimuth { get; set; }

        public double Pitch { get; set; }

        public double Roll { get; set; }

        public string CityName { get; set; }
    }
}