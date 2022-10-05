const db = require('./db');
const auth = require("./auth");

const fs = require('fs');
const zip = require('adm-zip');
const url = require('url');
const multer = require('multer');

let dir = __dirname;
let documentPath = dir.replace('Admin/router', 'Files/Documents/')
let databasePath = dir.replace('Admin/router', 'Files/Database/')
console.log('**************************************************');
console.log('EKYC Document Storage Path', documentPath);
console.log('EKYC Database Storage Path', databasePath);

exports.encryptResponse = encryptResponse;
function encryptResponse(responseData, res, req="NA", dataToWrite='NA', isBase64="false") {
    console.log(" isBase64 Flag : " + isBase64)
    let buff =new Buffer(responseData);
    let encryData = buff.toString('base64');
    console.log("---------------------------------------------------------------------");
    res.end(encryData.toString());
}

exports.decryptRequest = decryptRequest;
function decryptRequest(req, isEncrypted="false", func) {
    let fullBody = "";
    req.on('data', function(chunk) {
        fullBody += chunk.toString();
    });
    req.on('end', function() {
        if(isEncrypted == "true") {
            if(fullBody.indexOf("{") > -1) 
                func(1,"");
            else {
                let buffRquest = new Buffer(fullBody,'base64');
                let jsonRequest = JSON.parse(buffRquest.toString());
                func(0,jsonRequest);
            } 
        }
        else {
            let jsonRequest = JSON.parse(fullBody.toString());
            func(0,jsonRequest);
        }
    });
}

exports.decryptRequestGet = decryptRequestGet;
function decryptRequestGet(req, isEncrypted="false", func) {    
    if(isEncrypted == "true") {
        let urlString=(req.url).toString();
        let param=urlString.substring(urlString.indexOf("?"));
        let svcName=urlString.substring(urlString.indexOf("/"), urlString.indexOf("?"));   
        let buffRquest = new Buffer(param,'base64');
        let data = Object();
        data = svcName.toString() + "?" +buffRquest.toString();
        func(0,url.parse(data,true));
    }
    else {
        let urlString=(req.url).toString();
        func(0,url.parse(urlString,true));
    }
}

module.exports = function(app)
{
    app.get("/", function (req, res) {
        res.render('index.html');
    });

    app.get("/index.html", function (req, res) {
        res.render('index.html');
    });
    

    app.post('/eKycAdminLogin',function(req,res) {
        console.log(" ******************** :: eKycAdminLogin Request :: ************************");
        console.log("Login Request : " + (req.connection.remoteAddress).split(':')[3]);
        let time = db.getCurrTime().getHours() + ":" + db.getCurrTime().getMinutes() + ":" + db.getCurrTime().getSeconds() + ":" + db.getCurrTime().getMilliseconds();
        console.log(": Login Request Recieved Time : "  + time);

        decryptRequest(req, "true", function(err, requestData) {
            let dataIn = requestData.request.svcName + "," + time + "," + JSON.stringify(requestData);
            let deviceType = requestData.request.data.deviceType;
            let deviceId = requestData.request.data.deviceId;
            let deviceDetails = requestData.request.data.deviceDetails;
            let gscid = requestData.request.data.gscid;
            let pass = requestData.request.data.pass;
            switch(undefined) {
                case deviceType : {
                    deviceType = 0
                    console.log("Device Type : " + deviceType);
                }
                case deviceId : {
                    deviceId = 0
                    console.log("Device Id : " + deviceId);
                }
                case deviceDetails : {
                    deviceDetails = 0
                    console.log("Device Details : " + deviceDetails);
                }
                break;
            }
            console.log("Password :   " + pass);
            db.getUserInfoDB(gscid, function(err, gcid, userType, status, category, mfClient, panNo, dob, clientName, pwdAttempts) {
                if(err) {
                    let jsonResult = {
                        "response": {
                            "svcName":requestData.request.svcName,
                            "svcGroup":requestData.request.svcGroup,
                            "sessionId":null,
                            "ErrorCode":authResult.loginNotAllowed,
                            "data":{
                                "ClientCode" : null,
                                "ErrorCode" : authResult.userOrPasswordIncorrect,
                                "ErrMessage": 'Error getting User Info'
                            }
                        }
                    };
                    let json = JSON.stringify(jsonResult,null,'\t');
                    console.log(json);
                    encryptResponse(json,res,req,dataIn);
                }
                else {
                    if(gcid > 1) {
                        console.log("gcid : " + gcid + " userType : " + userType + " status : " + status);
                        auth.authenticateWithSession(gscid, pass, gcid, userType, status, category, pwdAttempts, deviceId, deviceType, deviceDetails, function(ret, err, gcid, __sessionId, device_id) {
                            console.log("Request : " + deviceId);
                            if(err) {
                                let jsonResult = {
                                    "response": {
                                        "svcName":requestData.request.svcName,
                                        "svcGroup":requestData.request.svcGroup,
                                        "sessionId":null,
                                        "ErrorCode":err,
                                        "data": {
                                            "ClientCode" : null,
                                            "Executioncode" : +ret,
                                            "ErrorCode" : +err,
                                            "mandateId": null,
                                            "userType": mfClient,
                                            "gscid": gscid,
                                            "clientName": clientName,
                                            "IsSameDevice": (device_id == deviceId) ? "true" : "false",
                                        }
                                    }
                                };
                                let json = JSON.stringify(jsonResult,null,'\t');
                                console.log(json);
                                encryptResponse(json,res,req,dataIn,"true");
                            }
                            else {
                                let jsonResult = {
                                    "response": {
                                        "svcName":requestData.request.svcName,
                                        "svcGroup":requestData.request.svcGroup,
                                        "sessionId":__sessionId,
                                        "ErrorCode":err,
                                        "data": {
                                            "ClientCode" : gcid,
                                            "Executioncode" : +ret,
                                            "ErrorCode" : +err,
                                            "cCategory":category,
                                            "userType":mfClient,
                                            "panNo":panNo,
                                            "dob":dob,
                                            "gscid":gscid,
                                            "clientName":clientName,
                                            "IsSameDevice":(device_id == deviceId) ? "true":"false",
                                        }
                                    },
                                };
                                let json = JSON.stringify(jsonResult,null,'\t');
                                console.log(json);
                                encryptResponse(json,res,req,dataIn,"true");
                            }
                        });
                    }
                    else {
                        let jsonResult = {
                            "response": {
                                "svcName":requestData.request.svcName,
                                "svcGroup":requestData.request.svcGroup,
                                "sessionId":null,
                                "ErrorCode":authResult.loginNotAllowed,
                                "data":{
                                    "ClientCode" : null,
                                    "Executioncode" : 0,
                                    "ErrorCode" : authResult.loginNotAllowed
                                }
                            }
                        };
                        let json = JSON.stringify(jsonResult,null,'\t');
                        console.log(json);
                        encryptResponse(json,res,req,dataIn);
                    }
                }
            });
        });
    });


    app.post('/adminChangePassword',function(req,res) {
        console.log("***************** adminChangePassword Request **************");
		req.serverTiming.from('API');
        decryptRequest(req, iniString.isBASE64, function(err, requestData) {
            let gscid = requestData.request.data.gscid.toString().trim();
            let oldPassword = requestData.request.data.oldPassword;
            let changeNewPassword = requestData.request.data.changeNewPassword;
            let time = dal.getCurrTime().getHours() + ":" + dal.getCurrTime().getMinutes() + ":" + dal.getCurrTime().getSeconds() + ":" + dal.getCurrTime().getMilliseconds();
			let dataIn = requestData.request.svcName+","+ time +","+JSON.stringify(requestData);
			
			db.getUserInfoDB(gscid, function(err, gcid, userType, status, category, mfClient, panNo, KYCStatus, dob, email, mobile) {
                if (err) {
                    let jsonResult = {
                        "response": {
                            "svcName":requestData.request.svcName,                                        
                            "svcGroup":requestData.request.svcGroup,
                            "ErrorCode":err,
                            "data":{
                                "ErrorCode" : auth.authResult.userOrPasswordIncorrect,
                                "msg": "Error getting user info"
                            }
                        },
                    };
                    let json = JSON.stringify(jsonResult,null,'\t');
                    encryptResponse(json,res,req,dataIn);
                }
                else {
                    if(gcid > 1) {
                        console.log("gscid   :" + gscid);
                        auth.changePassword(gscid, oldPassword, changeNewPassword, gcid, status, category, function(err) {
                            let jsonResult = {
                                "response": {
                                    "svcName":requestData.request.svcName,
                                    "svcGroup":requestData.request.svcGroup,
                                    "ErrorCode":err,
                                    "data":{
                                        "ErrorCode" : +err
                                    }
                                },
                            };
                            let json = JSON.stringify(jsonResult,null,'\t');
                            encryptResponse(json,res,req,dataIn);
                        });
                    }
                }
                
            });
        });
    });


    app.post('/getAllStats', function(req, res) {
        console.log("****************** getAllStats ********************");
        decryptRequest(req,'true',function(err,requestData) {
            console.log('Encrypt');
            let dataIn = requestData.request.svcName+","+db.getCurrTime().getHours() + ":" + db.getCurrTime().getMinutes() + ":" + db.getCurrTime().getSeconds() + ":" + db.getCurrTime().getMilliseconds()+","+JSON.stringify(requestData);
            db.getAllStats(function(err, rows) {
                if(err) {
                    console.log('Requested data not found, Please check parameters');
                    let jsonResult = {
                        "response": {
                            "svcName":requestData.request.svcName,
                            "svcGroup":requestData.request.svcGroup,
                            "error":err,
                            "data":{
                                "stats":{},
                            }                            
                        },
                    };
                    let json = JSON.stringify(jsonResult,null,'\t');
                    console.log(json);
                    encryptResponse(json,res,req,dataIn);
                } else {
                    console.log('Else');
                    let jsonResult = {
                        "response": {
                            "svcName":requestData.request.svcName,
                            "svcGroup":requestData.request.svcGroup,
                            "error":err,
                            "data":{
                                "stats":rows,
                            }
                        },
                    };
                    let json = JSON.stringify(jsonResult,null,'\t');
                    console.log(json);
                    encryptResponse(json,res,req,dataIn);
                }
            });
        });
    });


    app.post('/getReportData', function(req, res) {
        console.log("****************** getReportData ********************");
        decryptRequest(req,'true',function(err,requestData) {
            console.log('Encrypt');
            let dataIn = requestData.request.svcName+","+db.getCurrTime().getHours() + ":" + db.getCurrTime().getMinutes() + ":" + db.getCurrTime().getSeconds() + ":" + db.getCurrTime().getMilliseconds()+","+JSON.stringify(requestData);
            db.getReportData(requestData.request.data.stage,function(err, rows) {
                if(err) {
                    let jsonResult = {
                        "response": {
                            "svcName":requestData.request.svcName,
                            "svcGroup":requestData.request.svcGroup,
                            "error":err,
                            "data":{
                                "reportdata":{},
                            }
                        },
                    };
                    let json = JSON.stringify(jsonResult,null,'\t');
                    console.log(json);
                    encryptResponse(json,res,req,dataIn);
                }
                else {
                    console.log('Else');
                    let jsonResult = {
                        "response": {
                            "svcName":requestData.request.svcName,
                            "svcGroup":requestData.request.svcGroup,
                            "error":err,
                            "data":{
                                "reportdata":rows,
                            }
                        },
                    };
                    let json = JSON.stringify(jsonResult,null,'\t');
                    console.log(json);
                    encryptResponse(json,res,req,dataIn);
                }
            });
        });
    });


    app.post('/getUserDetails', function(req, res) {
        console.log("****************** getUserDetails ********************");
        decryptRequest(req,'true',function(err,requestData) {
            console.log('Encrypt');
            let dataIn = requestData.request.svcName+","+db.getCurrTime().getHours() + ":" + db.getCurrTime().getMinutes() + ":" + db.getCurrTime().getSeconds() + ":" + db.getCurrTime().getMilliseconds()+","+JSON.stringify(requestData);
            db.getUserDetails(requestData.request.data.uniqueid,function(err, rows) {
                if(err) {
                    let jsonResult = {
                        "response": {
                            "svcName":requestData.request.svcName,
                            "svcGroup":requestData.request.svcGroup,
                            "error":err,
                            "data":{
                                "reportdata":{},
                            }
                        },
                    };
                    let json = JSON.stringify(jsonResult,null,'\t');
                    console.log(json);
                    encryptResponse(json,res,req,dataIn);
                }
                else {
                    console.log('Else');
                    let jsonResult = {
                        "response": {
                            "svcName":requestData.request.svcName,
                            "svcGroup":requestData.request.svcGroup,
                            "error":err,
                            "data":{
                                "reportdata":rows,
                            }
                        },
                    };
                    let json = JSON.stringify(jsonResult,null,'\t');
                    console.log(json);
                    encryptResponse(json,res,req,dataIn);
                }
            });
        });
    });


    app.post('/getPennyDropReport', function(req, res) {
        console.log("****************** getPennyDropReport ********************");
        decryptRequest(req,'true',function(err,requestData) {
            console.log('Encrypt');
            let dataIn = requestData.request.svcName+","+db.getCurrTime().getHours() + ":" + db.getCurrTime().getMinutes() + ":" + db.getCurrTime().getSeconds() + ":" + db.getCurrTime().getMilliseconds()+","+JSON.stringify(requestData);
            db.getPennyDropReport(function(err, rows) {
                if(err) {
                    let jsonResult = {
                        "response": {
                            "svcName":requestData.request.svcName,
                            "svcGroup":requestData.request.svcGroup,
                            "error":err,
                            "data":{
                                "reportdata":{},
                            }
                        },
                    };
                    let json = JSON.stringify(jsonResult,null,'\t');
                    console.log(json);
                    encryptResponse(json,res,req,dataIn);
                }
                else {
                    console.log('Else');
                    let jsonResult = {
                        "response": {
                            "svcName":requestData.request.svcName,
                            "svcGroup":requestData.request.svcGroup,
                            "error":err,
                            "data":{
                                "reportdata":rows,
                            }
                        },
                    };
                    let json = JSON.stringify(jsonResult,null,'\t');
                    console.log(json);
                    encryptResponse(json,res,req,dataIn);
                }
            });
        });
    });


    app.post('/getStatusReport', function(req, res) {
        console.log("****************** getStatusReport ********************");
        decryptRequest(req,'true',function(err,requestData) {
            console.log('Encrypt');
            let dataIn = requestData.request.svcName+","+db.getCurrTime().getHours() + ":" + db.getCurrTime().getMinutes() + ":" + db.getCurrTime().getSeconds() + ":" + db.getCurrTime().getMilliseconds()+","+JSON.stringify(requestData);        
            db.getStatusReport(function(err, rows) {
                if(err) {
                    let jsonResult = {
                        "response": {
                            "svcName":requestData.request.svcName,
                            "svcGroup":requestData.request.svcGroup,
                            "error":err,
                            "data":{
                                "reportdata":{},
                            }
                        },
                    };
                    let json = JSON.stringify(jsonResult,null,'\t');
                    console.log(json);
                    encryptResponse(json,res,req,dataIn);
                }
                else {
                    console.log('Else');
                    let jsonResult = {
                        "response": {
                            "svcName":requestData.request.svcName,
                            "svcGroup":requestData.request.svcGroup,
                            "error":err,
                            "data":{
                                "reportdata":rows,
                            }
                        },
                    };
                    let json = JSON.stringify(jsonResult,null,'\t');
                    console.log(json);
                    encryptResponse(json,res,req,dataIn);
                }
            });
        });
    });


    app.post('/updateEkycFromAdmin', function(req, res) {
        console.log("****************** updateEkycFromAdmin ********************");
        decryptRequest(req,'true',function(err,requestData) {
            console.log('Encrypt');
            let dataIn = requestData.request.svcName+","+db.getCurrTime().getHours() + ":" + db.getCurrTime().getMinutes() + ":" + db.getCurrTime().getSeconds() + ":" + db.getCurrTime().getMilliseconds()+","+JSON.stringify(requestData);
            let data  = requestData.request.data;
            console.log('Data', data);

            db.getUserInfo(requestData.request.params.email_id, requestData.request.params.mobile_no, requestData.request.params.pan,function(err, rows) {
                if(err) {
                    let jsonResult = {
                        "response": {
                            "svcName":requestData.request.svcName,
                            "svcGroup":requestData.request.svcGroup,
                            "error":err,
                            "data":{
                                "reportdata":{},
                            }
                        },
                    };
                    let json = JSON.stringify(jsonResult,null,'\t');
                    console.log(json);
                    encryptResponse(json,res,req,dataIn);
                }
                else {
                    Object.keys(data).forEach(key => {
                        db.updatePersonalDetails(key, data[key], requestData.request.params.email_id, requestData.request.params.mobile_no, requestData.request.params.pan, function(err) {
                            if (err)  {
                                let jsonResult = {
                                    "response": {
                                        "svcName":requestData.request.svcName,
                                        "svcGroup":requestData.request.svcGroup,
                                        "error":err,
                                        "data":{
                                            "reportdata":'Error Updating Data',
                                        }
                                    },
                                };
                                let json = JSON.stringify(jsonResult,null,'\t');
                                console.log(json);
                                encryptResponse(json,res,req,dataIn);
                            }  
                            else {
                                let jsonResult = {
                                    "response": {
                                        "svcName":requestData.request.svcName,
                                        "svcGroup":requestData.request.svcGroup,
                                        "error":err,
                                        "data":{
                                            "reportdata":'Rows Updated',
                                        }
                                    },
                                };
                                let json = JSON.stringify(jsonResult,null,'\t');
                                console.log(json);
                                encryptResponse(json,res,req,dataIn);
                            }
                        });
                    })
                }
            });
        });
    });

    
    app.get('/downloadZipBackup', function(req, res) {
        console.log("****************** downloadZipBackup ********************");
        let dateObj = new Date();
        let day = ((dateObj.getDate() / 10) >= 1) ? dateObj.getDate() : "0" + dateObj.getDate();
        let getMonth = (((dateObj.getMonth() + 1) / 10) >= 1) ? parseInt(dateObj.getMonth() + 1) : "0" + parseInt(dateObj.getMonth() + 1);
        let date = day + '' + getMonth + '' + dateObj.getFullYear();
        let backupPath = databasePath ;
        console.log('backupPath', backupPath);
        let to_zip = fs.readdirSync(backupPath)
        let zp = new zip();
        for(let k = 0 ; k < to_zip.length; k++) {
            zp.addLocalFolder(backupPath + to_zip[k])
        }
        const data = zp.toBuffer()
        let fileName = date;
        res.set('Content-Type','application/octet-stream');
        res.set('Content-Disposition',`attachment; filename=${fileName}.zip`);
        res.set('Content-Length',data.length);
        res.send(data);
    });


    app.get('/downloadUserDoc', function(req, res) {
        console.log("****************** downloadUserDoc file ********************");
        let pan = req.query.pan;
        let downloadType = req.query.downloadType;

        let ipvPath  = documentPath + pan + '/' + pan + 'IPV.mp4';
        let pdfPath = documentPath + pan + '/' + pan + 'FinalEKycDocument.pdf'
        console.log(pdfPath);
        console.log(ipvPath);

        if(downloadType == 'document') {
            if(fs.existsSync(pdfPath))
            res.download (pdfPath, pan + '_FinalEKycDocument.pdf', () => {
                db.writeToLogFile('Download', 'File Downloaded')
            })
            else {
                let jsonResult = {
                    "response": {
                        "error":err,
                        "data":{
                            "msg":'FinalEKycDocument Not Found',
                        }
                    },
                };
                let json = JSON.stringify(jsonResult,null,'\t');
                console.log(json);
                res.end(json)
            }        
        }
        else if (downloadType == 'ipv') {
            if(fs.existsSync(ipvPath))
            res.download (ipvPath, pan + '_IPV.mp4')

            else {
                let jsonResult = {
                    "response": {
                        "error":err,
                        "data":{
                            "msg":'IPV Not Found',
                        }
                    },
                };
                let json = JSON.stringify(jsonResult,null,'\t');
                console.log(json);
                res.end(json)
            } 
        }
        else {
            let jsonResult = {
                "response": {
                    "error":err,
                    "data":{
                        "msg":'DownloadType not defined',
                    }
                },
            };
            let json = JSON.stringify(jsonResult,null,'\t');
            console.log(json);
            res.end(json)
        }
    });


    app.get("/downloadFileVideo", function (req, res) {
        console.log("********************* downloadFileVideo **********************");
        let path = documentPath + req.query.pan+"/" + req.query.pan + 'IPV_WTS.mp4';
        fs.readFile(path, function (err, data) {
            if (err) {
                path = documentPath + req.query.pan+"/" + req.query.pan + 'IPV.mp4';
                console.log('IPV path', path);
                fs.readFile(path, function (err, data) {
                    if (err) {
                        console.log("Err downloadFileVideo");
                        res.send("Err downloadFileVideo");
                    }
                    else {
                        res.contentType("video/mp4");
                        res.send(data);
                    }
                });
            }
            else {
                console.log('IPV_WTS path', path);
                res.contentType("video/mp4");
                res.send(data);
            }
        });
    });


    app.get("/viewDocuments", function (req, res) {
        console.log("********************* viewDocuments **********************");
        let email_id = req.query.email_id;
        let mobile_no = req.query.mobile_no;
        let type = req.query.type;
        let pan = req.query.pan;
        db.getDocuments(email_id, mobile_no, type, function (err, rows) {
            let rowsdata;
            switch(type) {
                case 'pancard' : rowsdata = rows[0].pancard;
                break;
                case 'signature' : rowsdata = rows[0].signature;
                break;
                case 'bankproof' : rowsdata = rows[0].bankproof;
                break;
                case 'addressproof' : rowsdata = rows[0].addressproof;
                break;
                case 'incomeproof' : rowsdata = rows[0].incomeproof;
                break;
                case 'photograph' : rowsdata = rows[0].photograph;
                break;
                case 'nominee' : rowsdata = rows[0].nominee_identity_proof;
                break;
            }
            if (err) {
                console.log("error send image");
                let obj = db.responseModelObject("1", "failed");
                res.setHeader("Content-Type", "application/json");
                res.end(JSON.stringify(obj));
            }
            else {
                if(type == 'pancard' || type == 'signature' || type == 'addressproof' || type == 'photograph' || type == 'nominee') {
                    fs.readFile(rowsdata, function (err, data) {
                        if (!err) {
                            res.contentType("image/png");
                            res.send(data);
                        }
                        else {
                            fs.readFile(documentPath + pan + "/" + rowsdata, function (err1, data1) {
                                res.contentType("image/png");
                                res.send(data1);
                            });
                        }
    
                    });
                }
                else if (type == 'bankproof' || type == 'incomeproof') {
                    fs.readFile(rowsdata, function (err, data) {

                        if (!err) {
                            res.contentType("application/pdf");
                            res.send(data);
                        }
                        else {
                            fs.readFile(documentPath + pan + "/" + rowsdata, function (err1, data1) {
                                res.contentType("application/pdf");
                                res.send(data1);
                            });
                        }
    
                    });
                }
            }
        });
    });


    
    const imageStorage = multer.diskStorage({
        destination: function (req, file, cb) {
            console.log("body print " + req.body.pan);
            cb(null, documentPath + req.body.pan)
        },
        filename: (req, file, cb) => {
            cb(null, req.body.imageName + req.body.extension)
        }
    });
    const pdfStorage = multer.diskStorage({
        destination: function (req, file, cb) {
            console.log("body print " + req.body.pan);
            cb(null, documentPath + req.body.pan)
        },
        filename: (req, file, cb) => {
            cb(null, req.body.imageName + ".pdf")
        }
    });
    const imageUpload = multer({
        storage: imageStorage,
        fileFilter(req, file, cb) {
            console.log("body print " + req.body.imageName);
            if (!file.originalname.match(/\.(png|jpg|jpeg|PNG|JPG|JPEG|pdf)$/)) {
                return cb(new Error('Please upload a Image'))
            }
            cb(undefined, true)
        }
    })


    app.post('/uploadDocuments', imageUpload.single('image_path'), (req, res) => {
        console.log("******  uploadDocuments POST *******");
        console.log(req.body);
        let email_id = req.body.email_id;
        let mobile_no = req.body.mobile_no;
        let type = req.body.type;
        let prooftype = req.body.prooftype;
        let imageName = req.body.imageName;
        let extension = req.body.extension;
        let imagePath = imageName + extension;
        let pan = req.body.pan;
        let uploadType = req.body.uploadType;
        if(uploadType == 'self') {
            db.saveDocumentDetails(email_id, mobile_no, type, imageName, imagePath, prooftype, pan, function (err, rows) {
                if (err == 0) {
                    obj = db.responseModelObject("0", "success");
                } else if (err == 1) {
                    obj = db.responseModelObject("1", "failed");
                }
                res.setHeader("Content-Type", "application/json");
                res.end(JSON.stringify(obj));
            });
        }
        else if (uploadType == 'admin'){
            obj = db.responseModelObject("0", "success");
            res.setHeader("Content-Type", "application/json");
            res.end(JSON.stringify(obj));
        }
    }, (error, req, res, next) => {
        res.status(400).send({ error: error.message })
    })


    
}