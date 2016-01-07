using AutoMapper;
using WoahMe.Data.Models;
using WoahMe.Web.Api.Infrastructure.Mappings;

namespace WoahMe.Web.Api.Models.Places
{
    public class AddPlaceResponseModel : IMapFrom<Place>, IHaveCustomMappings
    {
        public int Id { get; set; }

        public string ImageSource { get; set; }

        public string ImageOrientation { get; set; }

        public string Title { get; set; }

        public string Description { get; set; }

        public double Azimuth { get; set; }

        public double Pitch { get; set; }

        public double Roll { get; set; }

        public void CreateMappings(IConfiguration configuration)
        {
            configuration.CreateMap<Place, AddPlaceResponseModel>()
                .ForMember(m => m.ImageOrientation, opts => opts.MapFrom(p =>
                    p.ImageOrientation == Data.Models.ImageOrientation.Horizontal ? "Horizonal" : "Vertical"))
                .ForMember(m => m.Azimuth, opts => opts.MapFrom(p => p.Location.GeoOrientation.Azimuth))
                .ForMember(m => m.Pitch, opts => opts.MapFrom(p => p.Location.GeoOrientation.Pitch))
                .ForMember(m => m.Roll, opts => opts.MapFrom(p => p.Location.GeoOrientation.Roll));
        }
    }
}