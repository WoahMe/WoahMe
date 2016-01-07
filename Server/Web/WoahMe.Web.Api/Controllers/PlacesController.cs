using AutoMapper.QueryableExtensions;
using Microsoft.AspNet.Identity;
using System.Linq;
using System.Web.Http;
using WoahMe.Data.Models;
using WoahMe.Services.Data;
using WoahMe.Web.Api.Infrastructure.Validation;
using WoahMe.Web.Api.Models.Places;

namespace WoahMe.Web.Api.Controllers
{
    public class PlacesController : ApiController
    {
        private IPlacesService placesService;

        public PlacesController(IPlacesService placesService)
        {
            this.placesService = placesService;
        }
        
        public IHttpActionResult Get()
        {
            var response = this.placesService.GetAll()
                .ProjectTo<GetAllPlacesResponseModel>()
                .ToList();

            return this.Ok(response);
        }
        
        public IHttpActionResult Get(int id)
        {
            var place = this.placesService.ById(id);

            var response = (GetPlaceByIdResponseModel) AutoMapper.Mapper.Map(place, typeof(Place), typeof(GetPlaceByIdResponseModel));

            return this.Ok(response);
        }

        [ValidateModel]
        public IHttpActionResult Post(AddPlaceRequestModel model)
        {
            var newPlace = this.placesService.Add(model.ImageSource,
                                                  model.ImageOrientation,
                                                  model.Title,
                                                  model.Description,
                                                  model.Azimuth,
                                                  model.Pitch,
                                                  model.Roll,
                                                  model.CityName,
                                                  this.User.Identity.GetUserId());

            var response = AutoMapper.Mapper.Map(newPlace, typeof(Place), typeof(AddPlaceResponseModel));

            return this.Created(string.Empty, response);
        }
    }
}
