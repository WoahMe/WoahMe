namespace WoahMe.Data.Models
{
    public class Location
    {
        public int Id { get; set; }

        public int CityId { get; set; }

        public virtual City City { get; set; }

        public int GeoOrientationId { get; set; }

        public virtual GeoOrientation GeoOrientation { get; set; }
    }
}