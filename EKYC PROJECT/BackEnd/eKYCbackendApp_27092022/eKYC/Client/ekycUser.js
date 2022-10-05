const express = require('express');
const http = require('http');
const bodyParser = require('body-parser');
const fs = require("fs");
const https = require('https');
const db = require("./router/db");
const config = db.loadConfigFile();
const app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
const router = express.Router();
app.use('/', router);
app.use(function (req, res, next) {
    res.setHeader('Access-control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'GET,POST,OPTIONS,PUT,PATCH,DELETE');
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
    res.setHeader('Access-Control-Allow-Credentials', true);
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Headers', 'Content-Type');
    res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept');
    next();
});
app.set('views', __dirname + '/views');
app.engine('html', require('ejs').renderFile);
app.use(express.static('public'));
require('./router/main')(app);

if (config.isSecure == "true") {
    let https_options = {
        key: fs.readFileSync(__dirname + '/' + config.sslKey),
        cert: fs.readFileSync(__dirname + '/' + config.sslCert),
    };
    https.createServer(https_options, app).listen((config.port == "undefined" || config.port == "") ? 3000 : config.port, function (err) {
        if (err) {
            console.log('**************************************************');
            console.log("Error Creating Server : " + err);
            console.log('**************************************************');
        }
        else {
            let port = (config.port == "undefined" || config.port == "") ? "3000" : config.port;
            console.log('**************************************************');
            console.log("Secure(https) Server Started on PORT : " + port);
            console.log('**************************************************');

            db.createEkycTable(function (err, rows) {
                console.log("create table reponse" + err);
                db.selectIfColumExist(function (err, rows) {
                    if (err == 0 || err == 3) {
                        console.log('New Columns are exist... ');
                    } else {
                        db.alterEkycTable(function (err, rows) {
                            if (err == 0) {
                                console.log('New columns have been added');
                            }
                        });
                    }
                });
            });
        }
    });

} else {
    app.listen(config.port, function (err) {
        if (err) {
            console.log("******************************************************************");
            console.log("Error Creating Server : " + err);
            console.log("******************************************************************");
        }
        else {
            console.log("******************************************************************");
            console.log(`Non-Secure server started on port ${config.port}`);
            console.log("******************************************************************");

            db.createEkycTable(function (err, rows) {
                console.log("create table reponse" + err);
                db.selectIfColumExist(function (err, rows) {
                    if (err == 0 || err == 3) {
                        console.log('New Columns are exist... ');
                    } else {
                        db.alterEkycTable(function (err, rows) {
                            if (err == 0) {
                                console.log('New columns have been added');
                            }
                        });
                    }
                });
            });
        }
    });

}