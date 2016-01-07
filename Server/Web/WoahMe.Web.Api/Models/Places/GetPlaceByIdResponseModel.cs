using System;
using AutoMapper;
using WoahMe.Data.Models;
using WoahMe.Web.Api.Infrastructure.Mappings;

namespace WoahMe.Web.Api.Models.Places
{
    public class GetPlaceByIdResponseModel : IMapFrom<Place>, IHaveCustomMappings
    {
        public int Id { get; set; }

        public string ImageSource { get; set; }

        public string ImageOrientation { get; set; }

        public string Title { get; set; }

        public string Description { get; set; }

        public string CityName { get; set; }

        public string Creator { get; set; }

        public double Azimuth { get; set; }

        public double Pitch { get; set; }

        public double Roll { get; set; }

        public void CreateMappings(IConfiguration configuration)
        {
            configuration.CreateMap<Place, GetPlaceByIdResponseModel>()
                .ForMember(m => m.CityName, opts => opts.MapFrom(p => p.Location.City.Name))
                .ForMember(m => m.Creator, opts => opts.MapFrom(p => p.Creator.Email))
                .ForMember(m => m.CityName, opts => opts.MapFrom(p => p.Location.City.Name))
                .ForMember(m => m.Azimuth, opts => opts.MapFrom(p => p.Location.GeoOrientation.Azimuth))
                .ForMember(m => m.Pitch, opts => opts.MapFrom(p => p.Location.GeoOrientation.Pitch))
                .ForMember(m => m.Roll, opts => opts.MapFrom(p => p.Location.GeoOrientation.Roll));
        }
    }
}