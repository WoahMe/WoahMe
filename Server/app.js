'use strict';

let express = require('express'),
  bodyParser = require('body-parser'),
  mongoose = require('mongoose'),
  app = express();

let connectionString = "mongodb://localhost:27017/woahme";

mongoose.connect(connectionString);

app.use(bodyParser.json());

require('./models');
require('./authentication-config');
require('./routers')(app);

app.use(function(err, req, res, next) {
  if (err) {
    res.status(err.status || 500)
      .json({
        message: err.message
      });
    return;
  }
});

let port = 3000;
app.listen(port, () =>
  console.log(`Server running at localhost:${port}`));
