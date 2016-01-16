'use strict';

let mongoose = require('mongoose');

let schema = new mongoose.Schema({
  imageSource: String,
  imageOrientation: String,
  title: String,
  description: String,
  location: {
    name: String,
    geoOrientation: {
      azimuth: Number,
      pitch: Number,
      roll: Number
    }
  },
  creator: String
});

mongoose.model('Place', schema);
