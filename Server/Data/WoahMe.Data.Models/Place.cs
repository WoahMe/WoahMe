namespace WoahMe.Data.Models
{
    public class Place
    {
        public int Id { get; set; }

        public string ImageSource { get; set; }
        
        public ImageOrientation ImageOrientation { get; set; }

        public string Title { get; set; }

        public string Description { get; set; }

        public int LocationId { get; set; }

        public virtual Location Location { get; set; }

        public string CreatorId { get; set; }

        public virtual User Creator { get; set; }
    }
}
