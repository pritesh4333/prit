const https = require("https");
const express = require("express");
const app = express();
const fs = require('fs');
const bodyParser = require("body-parser");
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
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
const db = require("./router/db");
const config = db.loadConfigFile();

app.set('views', __dirname + '/views');
app.engine('html', require('ejs').renderFile);
app.use(express.static('public'));
require('./router/main')(app);

let port  = config.port == undefined ? '4001' : config.port;
if(config.isSecure == 'true') {
    let options = {
        key: fs.readFileSync(__dirname + '/' + config.sslKey),
        cert: fs.readFileSync(__dirname + '/' + config.sslCert),
    };
    https.createServer(options, app)
    .listen(config.port, (err) => {
        if(err)
        console.log(`Error starting server on port ${port}`);

        else
        console.log('**************************************************');
        console.log(`Secure(https) server started on port ${port}`);
        console.log('**************************************************');
    });
}
else {
    app.listen(config.port, (err)=> {
        if(err)
        console.log(`Error starting server on port ${port}`);

        else
        console.log('**************************************************');
        console.log(`Non-Secure server started on port ${port}`);
        console.log('**************************************************');
    })
}

(function () {
    let dir = __dirname;
    let dateObj = new Date();
    let day = ((dateObj.getDate() / 10) >= 1) ? dateObj.getDate() : "0" + dateObj.getDate();
    let month = (((dateObj.getMonth() + 1) / 10) >= 1) ? parseInt(dateObj.getMonth() + 1) : "0" + parseInt(dateObj.getMonth() + 1);
    let year = dateObj.getFullYear();
    let date = `${day}${month}${year}`
    let filesDir = dir.replace('Admin', 'Files')
    let documentsDir = dir.replace('Admin', 'Files/Documents')
    let databaseDir = dir.replace('Admin', 'Files/Database')
    let dateDir = dir.replace('Admin', `Files/Database/${date}`)
    
    if (!fs.existsSync(filesDir)) {
        fs.mkdirSync(filesDir)
        if (!fs.existsSync(documentsDir)) {
            fs.mkdirSync(documentsDir)
        }
        if (!fs.existsSync(databaseDir)) {
            fs.mkdirSync(databaseDir)
            if (!fs.existsSync(dateDir)) {
                fs.mkdirSync(dateDir)
            }
        }
    }
    else if (fs.existsSync(filesDir)) {
        if (fs.existsSync(databaseDir)) {
            if (!fs.existsSync(dateDir)) {
                fs.mkdirSync(dateDir)
            }
        }
        else if (!fs.existsSync(databaseDir)) {
            fs.mkdirSync(databaseDir)
            if (!fs.existsSync(dateDir)) {
                fs.mkdirSync(dateDir)
            }
        }
    }
})()
