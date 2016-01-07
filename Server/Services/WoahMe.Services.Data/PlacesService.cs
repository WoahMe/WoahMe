using System;
using System.Linq;
using WoahMe.Data.Models;
using WoahMe.Data.Repositories;

namespace WoahMe.Services.Data
{
    public class PlacesService : IPlacesService
    {
        private IRepository<User> usersRepository;
        private IRepository<Place> placesRepository;
        private IRepository<Location> locationsRepository;

        public PlacesService(IRepository<User> usersRepository, IRepository<Place> placesRepository, IRepository<Location> locationsRepository)
        {
            this.usersRepository = usersRepository;
            this.placesRepository = placesRepository;
            this.locationsRepository = locationsRepository;
        }

        public IQueryable<Place> GetAll()
        {
            return this.placesRepository.All();
        }

        public Place ById(int id)
        {
            return this.placesRepository
                .GetById(id);
        }

        public Place Add(string imageSource,
                         string imageOrientation,
                         string title,
                         string description,
                         double azimuth,
                         double pitch,
                         double roll,
                         string cityName,
                         string creatorId)
        {
            var city = new City
            {
                Name = cityName
            };

            var geoOrientation = new GeoOrientation
            {
                Azimuth = azimuth,
                Pitch = pitch,
                Roll = roll
            };

            var location = new Location
            {
                City = city,
                GeoOrientation = geoOrientation
            };

            this.locationsRepository.Add(location);
            this.locationsRepository.SaveChanges();

            var creator = this.usersRepository.GetById(creatorId);

            var place = new Place
            {
                CreatorId = creatorId,
                Description = description,
                ImageOrientation = imageOrientation == "Horizontal" ? ImageOrientation.Horizontal : ImageOrientation.Vertical,
                ImageSource = imageSource,
                LocationId = location.Id,
                Title = title
            };

            this.placesRepository.Add(place);
            this.placesRepository.SaveChanges();

            place.Location = location;

            return place;
        }
    }
}
