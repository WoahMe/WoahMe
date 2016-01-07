using System;
using AutoMapper;
using WoahMe.Data.Models;
using WoahMe.Web.Api.Infrastructure.Mappings;

namespace WoahMe.Web.Api.Models.Places
{
    public class GetAllPlacesResponseModel : IMapFrom<Place>, IHaveCustomMappings
    {
        public int Id { get; set; }

        public string Title { get; set; }

        public string ImageSource { get; set; }

        public string ImageOrientation { get; set; }

        public void CreateMappings(IConfiguration configuration)
        {
            configuration.CreateMap<Place, GetAllPlacesResponseModel>()
                .ForMember(m => m.ImageOrientation, opts => opts.MapFrom(p =>
                    p.ImageOrientation == Data.Models.ImageOrientation.Horizontal ? "Horizonal" : "Vertical"));
        }
    }
}