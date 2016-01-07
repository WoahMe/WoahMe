using System.Linq;
using WoahMe.Data.Models;

namespace WoahMe.Services.Data
{
    public interface IPlacesService
    {
        Place Add(string imageSource,
                  string imageOrientation,
                  string title,
                  string description,
                  double azimuth,
                  double pitch,
                  double roll,
                  string cityName,
                  string creatorId);

        IQueryable<Place> GetAll();

        Place ById(int id);
    }
}