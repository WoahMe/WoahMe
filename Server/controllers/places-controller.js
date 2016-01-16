'use strict';

let mongoose = require('mongoose'),
  Place = mongoose.model('Place');

var controller = {
  all: function(req, res) {
    let filter = {};

    if (req.query.type) {
      filter.type = req.query.type
    }

    if (req.query.userId) {
      filter.user = req.query.userId
    }

    Place.find(filter, function(err, places) {
      if (err) {
        throw err;
      }

      res.json({
        result: places
      });
    });
  },
  add: function(req, res, next) {
    let place = req.body;
    place.type = place.type || 'uncategorized';
    let user = req.user;
    place.date = new Date();
    place.user = user._id;

    var dbPlace = new Place(place);
    dbPlace.save(function(err) {
      if (err) {
        next(err);
        return;
      }

      res.status(201)
        .json({
          result: dbPlace
        });
    });
  }
};

module.exports = controller;
