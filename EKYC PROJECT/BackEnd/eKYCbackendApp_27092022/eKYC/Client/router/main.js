const fs = require('fs');
const url = require("url");
const multer = require('multer');
const ffmpegPath = require('@ffmpeg-installer/ffmpeg').path;
const ffmpeg = require('fluent-ffmpeg');
ffmpeg.setFfmpegPath(ffmpegPath);

const db = require('./db');
const config = db.loadConfigFile();
const digioDevGateway = 'https://ext.digio.in/#/gateway/login/';//dev
const digioProdGateway = 'https://app.digio.in/#/gateway/login/';//prod

let dir = __dirname;
let documentPath = dir.replace('Client/router', 'Files/Documents/')
console.log('**************************************************');
console.log('EKYC Document Storage Path', documentPath);

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
function decryptRequestGet(req,isEncrypted="false",func) {    
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

module.exports = function (app) {
    app.get("/index.html", function (req, res) {
        res.render('index.html');
    });

    app.get("/", function (req, res) {
        res.render('index.html');
    });


    app.get("/pdfMerger", function (req, res) {
        console.log("****** Request Called pdfMerger GET *******");
        let bank_proof = req.query.bank_proof;
        let income_proof = req.query.income_proof;
        let pan = req.query.pan;
        db.pdfmerger(bank_proof, income_proof, pan, documentPath, function (err, rows) {
            if (err == 0) {
                obj = db.responseModelObject("0", "success");
            } else if (err == 1) {
                obj = db.responseModelObject("1", "failed");
            }
            res.setHeader("Content-Type", "application/json");
            res.end(JSON.stringify(obj));
        });
    });


    app.get("/getTokenDetails", function (req, res) {
        console.log("****** Request Called getTokenDetails GET *******");
        let token = req.query.token;
        db.getContractsInfo(token, function (err, rows) {
            if (err == 0)
                res.end(JSON.stringify(rows));
            else
                res.end("Error Occured ");
        });

    });


    app.post("/postTokenDetails", function (req, res) {
        console.log("****** Request Called postTokenDetails POST TOEKM *******");
        console.log(req.body);
        let token = req.body.token;

        db.getContractsInfo(token, function (err, rows) {
            res.end(JSON.stringify(rows));
        });
    });


    app.get("/getEkycUserDetails", function (req, res) {
        console.log("****** Request Called getEkycUserDetails GET *******");
        let email_id = req.query.email_id;
        let mobile_no = req.query.mobile_no;
        let full_name = req.query.full_name;
        let latitude = req.query.latitude;
        let longitude = req.query.longitude;
        db.getEkycUserDetails(email_id, mobile_no, function (err, rows) {
            console.log('getEkycUserDetails');
            console.log(rows + ' EKYC ERROR CODE ' + err);

            let obj;
            if (err == 0) {

                obj = db.responseUserDetailsModelObject("0", rows);
                if (latitude != '' && longitude != '') {
                    console.log('latitude !=  && longitude != ');
                    db.insertLatLongData(email_id, mobile_no, latitude, longitude, function (err, rows) {
                        if (err == 0) {
                            console.log('Lat Long has been updated', err);
                            res.setHeader("Content-Type", "application/json");
                            console.log('getEkycUserDetails JSON', JSON.stringify(obj));
                            res.end(JSON.stringify(obj));
                        }
                        else {
                            console.log('Err, rows', err, rows);
                            res.setHeader("Content-Type", "application/json");
                            console.log('getEkycUserDetails JSON', JSON.stringify(obj));
                            res.end(JSON.stringify(obj));
                        }

                    });
                }

            } else if (err == 1) {
                obj = db.responseUserDetailsModelObject("1", rows);
                db.insertUsersData(email_id, mobile_no, full_name, latitude, longitude, function (err, rows) {
                    res.setHeader("Content-Type", "application/json");
                    console.log('getEkycUserDetails JSON', JSON.stringify(obj));
                    res.end(JSON.stringify(obj));

                });
            } else if (err == 2) {
                obj = db.responseUserDetailsModelObject("2", rows);
                db.insertUsersData(email_id, mobile_no, full_name, latitude, longitude, function (err, rows) {
                    res.setHeader("Content-Type", "application/json");
                    console.log('getEkycUserDetails JSON', JSON.stringify(obj));
                    res.end(JSON.stringify(obj));

                });
            }
            else if (err == 3) {
                obj = db.responseUserDetailsModelObject("3", rows);
                db.insertUsersData(email_id, mobile_no, full_name, latitude, longitude, function (err, rows) {
                    console.log(" insert successfull");
                    res.setHeader("Content-Type", "application/json");
                    console.log('getEkycUserDetails JSON', JSON.stringify(obj));
                    res.end(JSON.stringify(obj));
                });
            }
            // res.setHeader("Content-Type", "application/json");
            // console.log('getEkycUserDetails JSON', JSON.stringify(obj));
            // res.end(JSON.stringify(obj));
        });

    });


    app.post("/SaveEkycLoginDetails", function (req, res) {
        console.log("****** Request Called SaveEkycLoginDetails POST *******");
        console.log(req.body);
        let datat1 = req.body.data;
        let full_name = datat1.full_name;
        let email_id = datat1.email_id;
        let mobile_no = datat1.mobile_no;
        let client_code = datat1.client_code;
        let mobile_otp = datat1.mobile_otp;
        let email_otp = datat1.email_otp;
        let is_otp_verified = datat1.is_otp_verified;

        db.saveEkycLoginDetails(full_name, email_id, mobile_no, client_code, mobile_otp, email_otp, is_otp_verified, function (err, rows) {
            let obj;
            if (err == 0) {
                obj = db.responseModelObject("0", "success");
            } else if (err == 1) {

                obj = db.responseModelObject("1", "failed");
            }
            res.setHeader("Content-Type", "application/json");
            res.end(JSON.stringify(obj));
        });
    });


    app.post("/SavePanDetails", function (req, res) {
        console.log("****** Request Called SavePanDetails POST *******");
        console.log(req.body);
        let datat1 = req.body.data;
        let email_id = datat1.email_id;
        let mobile_no = datat1.mobile_no;
        let pan = datat1.pan;
        let panfullname = datat1.panfullname;
        let dob = datat1.dob;
        db.savePanDetails(email_id, mobile_no, pan, panfullname, dob, function (err, rows) {
            let obj;
            if (err == 0) {
                try {
                    let dir = documentPath + pan;

                    if (!fs.existsSync(dir)) {
                        fs.mkdirSync(dir);
                        console.log(" Folder Created. ");
                    }
                } catch (e) {
                    console.log(" Error while creating Folder " + e);
                }
                obj = db.responseModelObject("0", "success");
            } else if (err == 1) {

                obj = db.responseModelObject("1", "INVALID PAN NUMBER");
            } else if (err == 2) {

                obj = db.responseModelObject("2", "INVALID PAN NAME");

            } else if (err == 3) {

                obj = db.responseModelObject("3", "INVALID PAN DOB");

            }
            else if (err == 4) {

                obj = db.responseModelObject("4", "NO DATA FOUND");
            }
            res.setHeader("Content-Type", "application/json");
            res.end(JSON.stringify(obj));
        });
    });


    app.post("/SaveBankDetails", function (req, res) {
        console.log("****** Request Called SaveBankDetails POST *******");
        console.log(req.body);
        let datat1 = req.body.data;
        let email_id = datat1.email_id;
        let mobile_no = datat1.mobile_no;
        let ifsccode = datat1.ifsccode;
        let accountnumber = datat1.accountnumber;
        let bankname = datat1.bankname;
        let banknames = datat1.banknames;
        let address = datat1.address;
        let micr = datat1.micr;
        let bank_address_state = datat1.bank_address_state;
        let bank_address_city = datat1.bank_address_city;
        let bank_address_pincode = datat1.bank_address_pincode;
        let bank_address_contactno = datat1.bank_address_contactno;
        let bank_branch = datat1.bank_branch;

        db.saveBankDetails(email_id, mobile_no, ifsccode, accountnumber, bankname, banknames, address, micr, bank_address_state, bank_address_city, bank_address_pincode, bank_address_contactno, bank_branch, function (err, rows) {
            if (rows != null && err != 0) {
                if (err != 2) {
                    let objData = JSON.parse(rows);
                }
            }
            let obj;
            if (err == 0) {
                obj = db.responseBankDetailsObject("0", "success", "", "", "");
                res.setHeader("Content-Type", "application/json");
                res.end(JSON.stringify(obj));

            } else if (err == 1) {

                obj = db.responseBankDetailsObject("1", objData.error_msg, objData.details, objData.code, objData.error_msg);
                res.setHeader("Content-Type", "application/json");
                res.end(JSON.stringify(obj));
            } else if (err == 2) {

                obj = db.responseBankDetailsObject("2", objData.error_msg, objData.details, objData.code, objData.error_msg);
                res.setHeader("Content-Type", "application/json");
                res.end(JSON.stringify(obj));
            }
        });
    });


    app.post("/saveEKYCPersonalDetails", function (req, res) {
        console.log("****** Request Called saveEKYCPersonalDetails POST *******");

        console.log(req.body);
        let data1 = req.body.data;
        let email_id = data1.email_id;
        let mobile_no = data1.mobile_no;
        let nseCash = data1.nse_cash;
        let nsefo = data1.nse_fo;
        let nseCurrency = data1.nse_currency;
        let bseCash = data1.bse_cash;
        let bsefo = data1.bse_fo;
        let bseCurrency = data1.bse_currency;
        let mcxCommodity = data1.mcx_commodity;
        let ncdexCommodity = data1.ncdex_commodity;
        let res_addr_1 = data1.res_addr_1;
        let res_addr_2 = data1.res_addr_2;
        let res_addr_state = data1.res_addr_state;
        let res_addr_city = data1.res_addr_city;
        let res_addr_pincode = data1.res_addr_pincode;
        let parm_addr_1 = data1.parm_addr_1;
        let parm_addr_2 = data1.parm_addr_2;
        let parm_addr_state = data1.parm_addr_state;
        let parm_addr_city = data1.parm_addr_city;
        let parm_addr_pincode = data1.parm_addr_pincode;
        let nationality = data1.nationality;
        let gender = data1.gender;
        let firstname1 = data1.firstname1;
        let middlename1 = data1.middlename1;
        let lastname1 = data1.lastname1;
        let maritalstatus = data1.maritalstatus;
        let fatherspouse = data1.fatherspouse;
        let firstname2 = data1.firstname2;
        let middlename2 = data1.middlename2;
        let lastname2 = data1.lastname2;
        let incomerange = data1.incomerange;
        let occupation = data1.occupation;
        let action = data1.action;
        let perextra1 = data1.perextra1;
        let perextra2 = data1.perextra2;
        let past_regulatory_action_details = data1.past_regulatory_action_details;
        let aadhar = data1.aadhar;
        let nominee_name = data1.nominee_name;
        let nominee_relation = data1.nominee_relation;
        let nominee_email = data1.nominee_email;
        let nom_addr_1 = data1.nom_addr_1;
        let nom_addr_2 = data1.nom_addr_2;
        let nom_addr_state = data1.nom_addr_state;
        let nom_addr_city = data1.nom_addr_city;
        let nom_addr_pincode = data1.nom_addr_pincode;
        let firstname_mother = data1.firstname_mother;
        let middlename_mother = data1.middlename_mother;
        let lastname_mother = data1.lastname_mother;

        db.saveEKYCPersonalDetails(email_id, mobile_no, nseCash,
            nsefo,
            nseCurrency,
            bseCash,
            bsefo,
            bseCurrency,
            mcxCommodity,
            ncdexCommodity,
            res_addr_1,
            res_addr_2,
            res_addr_state,
            res_addr_city,
            res_addr_pincode,
            parm_addr_1,
            parm_addr_2,
            parm_addr_state,
            parm_addr_city,
            parm_addr_pincode,
            nationality,
            gender,
            firstname1,
            middlename1,
            lastname1,
            maritalstatus,
            fatherspouse,
            firstname2,
            middlename2,
            lastname2,
            incomerange,
            occupation,
            action,
            perextra1,
            perextra2,
            past_regulatory_action_details,
            aadhar,
            nominee_name,
            nominee_relation,
            nominee_email,
            nom_addr_1,
            nom_addr_2,
            nom_addr_state,
            nom_addr_city,
            nom_addr_pincode,
            firstname_mother,
            middlename_mother,
            lastname_mother,

            function (err, rows) {
                let obj;
                if (err == 0) {

                    obj = db.responseModelObject("0", "success");
                } else if (err == 1) {

                    obj = db.responseModelObject("1", "failed");
                }
                res.setHeader("Content-Type", "application/json");
                res.end(JSON.stringify(obj));
            }
        );

    });


    app.post("/SavePaymentDetails", function (req, res) {
        console.log("****** Request Called SavePaymentDetails POST *******");
        console.log(req.body);
        let datat1 = req.body.data;
        let email_id = datat1.email_id;
        let mobile_no = datat1.mobile_no;
        let pack = datat1.pack;
        db.savePaymentDetails(email_id, mobile_no, pack, function (err, rows) {
            if (err == 0) {
                obj = db.responseModelObject("0", "success");
            } else if (err == 1) {

                obj = db.responseModelObject("1", "failed");
            }
            res.setHeader("Content-Type", "application/json");
            res.end(JSON.stringify(obj));
        });
    });


    app.post("/SaveDocumentDetails", function (req, res) {
        console.log("****** Request Called SaveDocumentDetails POST *******");
        console.log(req.body);
        let datat1 = req.body.data;
        let email_id = datat1.email_id;
        let mobile_no = datat1.mobile_no;
        db.saveDocumDetails(email_id, mobile_no, function (err, rows) {
            if (err == 0) {
                obj = db.responseModelObject("0", "success");
            } else if (err == 1) {

                obj = db.responseModelObject("1", "failed");
            }
            res.setHeader("Content-Type", "application/json");
            res.end(JSON.stringify(obj));
        });
    });


    app.post("/SaveIpvDetails", function (req, res) {
        console.log("****** Request Called SaveIpvDetails POST *******");
        console.log(req.body);
        let datat1 = req.body.data;
        let email_id = datat1.email_id;
        let mobile_no = datat1.mobile_no;
        db.saveIpvDetails(email_id, mobile_no, function (err, rows) {
            if (err == 0) {

                obj = db.responseModelObject("0", "success");
            } else if (err == 1) {

                obj = db.responseModelObject("1", "failed");
            }
            res.setHeader("Content-Type", "application/json");
            res.end(JSON.stringify(obj));
        });
    });


    app.post("/SaveEsignDetails", function (req, res) {
        console.log("****** Request Called SaveEsignDetails POST *******");
        console.log(req.body);
        let datat1 = req.body.data;
        let email_id = datat1.email_id;
        let mobile_no = datat1.mobile_no;
        let esign = datat1.esign;

        db.saveEsignDetails(email_id, mobile_no, esign, function (err, rows) {
            if (err == 0) {
                obj = db.responseModelObject("0", "success");
            } else if (err == 1) {

                obj = db.responseModelObject("1", "failed");
            }
            res.setHeader("Content-Type", "application/json");
            res.end(JSON.stringify(obj));
        });
    });


    app.post("/SendEmailOtp", function (req, res) {
        console.log("****** Request Called SendEmailOtp POST *******");
        console.log(req.body);
        let datat1 = req.body.data;
        let email_id = datat1.email_id;
        let mobile_no = datat1.mobile_no;
        let is_otp_verified = datat1.is_otp_verified;
        db.sendEmailOtp(email_id, mobile_no, is_otp_verified, function (err, email_otp) {
            console.log(err);
            if (err == 0) {
                console.log("Email OTP Send and OTP is :-" + email_otp);
                obj = db.responseModelObject("0", "" + email_otp);
                res.setHeader("Content-Type", "application/json");
                res.end(JSON.stringify(obj));
            } else {
                obj = db.responseModelObject("1", "failed");
                res.setHeader("Content-Type", "application/json");
                res.end(JSON.stringify(obj));
            }
        });
    });


    app.post("/SendDatatoCDSLKYC", function (req, res) {
        console.log("****** Request Called SendDatatoCDSLKYC POST *******");
        console.log(req.body.response.data.message[0]);

        let full_name = req.body.response.data.message[0].full_name;
        let email_id = req.body.response.data.message[0].email_id;
        let mobile_no = req.body.response.data.message[0].mobile_no;
        let pan = req.body.response.data.message[0].pan;
        let panfullname = req.body.response.data.message[0].panfullname;
        let dob = req.body.response.data.message[0].dob;
        let nationality = req.body.response.data.message[0].nationality;
        let gender = req.body.response.data.message[0].gender;
        let firstname1 = req.body.response.data.message[0].firstname1;
        let middlename1 = req.body.response.data.message[0].middlename1;
        let lastname1 = req.body.response.data.message[0].lastname1;
        let maritalstatus = req.body.response.data.message[0].maritalstatus;
        let fatherspouse = req.body.response.data.message[0].fatherspouse;
        let firstname2 = req.body.response.data.message[0].firstname2;
        let middlename2 = req.body.response.data.message[0].middlename2;
        let lastname2 = req.body.response.data.message[0].lastname2;
        let incomerange = req.body.response.data.message[0].incomerange;
        let occupation = req.body.response.data.message[0].occupation;
        let action = req.body.response.data.message[0].action;
        let res_addr_1 = req.body.response.data.message[0].res_addr_1;
        let res_addr_2 = req.body.response.data.message[0].res_addr_2;
        let res_addr_state = req.body.response.data.message[0].res_addr_state;
        let res_addr_city = req.body.response.data.message[0].res_addr_city;
        let res_addr_pincode = req.body.response.data.message[0].res_addr_pincode;
        let parm_addr_1 = req.body.response.data.message[0].parm_addr_1;
        let parm_addr_2 = req.body.response.data.message[0].parm_addr_2;
        let parm_addr_state = req.body.response.data.message[0].parm_addr_state;
        let parm_addr_city = req.body.response.data.message[0].parm_addr_city;
        let parm_addr_pincode = req.body.response.data.message[0].parm_addr_pincode;
        let nominee_relation = req.body.response.data.message[0].nominee_relation;
        let nominee_name = req.body.response.data.message[0].nominee_name;

        db.sendDatatoCDSLKYC(full_name, email_id, mobile_no, pan, panfullname, dob, nationality, gender, firstname1, middlename1, lastname1,
            maritalstatus, fatherspouse, firstname2, middlename2, lastname2, incomerange, occupation, action, res_addr_1, res_addr_2, res_addr_state,
            res_addr_city, res_addr_pincode, parm_addr_1, parm_addr_2, parm_addr_state, parm_addr_city, parm_addr_pincode, nominee_relation, nominee_name,
            function (err, response) {
                obj = db.responseModelObject("" + err, "" + response);
                res.setHeader("Content-Type", "application/json");
                res.end(JSON.stringify(obj));
        });
    });

    app.post("/SendMobileOtp", function (req, res) {
        console.log("****** Request Called SendMobileOtp POST *******");
        console.log(req.body);
        let datat1 = req.body.data;
        let email_id = datat1.email_id;
        let mobile_no = datat1.mobile_no;
        let is_otp_verified = datat1.is_otp_verified;
        db.sendMobileOtp(email_id, mobile_no, is_otp_verified, function (err, mobile_otp) {
            if (err == 0) {
                console.log("Mobile OTP Send and OTP is :-" + mobile_otp);
                obj = db.responseModelObject("0", "" + mobile_otp);
                res.setHeader("Content-Type", "application/json");
                res.end(JSON.stringify(obj));
            } else {
                obj = db.responseModelObject("1", "failed");
                res.setHeader("Content-Type", "application/json");
                res.end(JSON.stringify(obj));
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
    const pdfUpload = multer({
        storage: pdfStorage,
        fileFilter(req, file, cb) {
            console.log("body print " + req.body.imageName);
            if (!file.originalname.match(/\.(png|jpg|jpeg|PNG|JPG|JPEG|pdf|PDF)$/)) {
                return cb(new Error('Please upload a PDF'))
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


    app.post("/uploadpdftodigio", function (req, res) {
        console.log("****** Request Called uploadpdftodigio POST *******");
        console.log(req.body);
        let datat1 = req.body.data;
        let email_id = datat1.email_id;
        let mobile_no = datat1.mobile_no;
        let name = datat1.name;
        let pdfpath = datat1.pdfpath;
        let imagePath = documentPath + name + "/" + pdfpath;
        db.pdfupload(email_id, mobile_no, imagePath, name, function (err, response) {
            let obj;
            if (err == 0) {
                let gatwayurl;
                if (config.isProduction == 'false') {
                    gatwayurl = digioDevGateway;
                } else {
                    gatwayurl = digioProdGateway
                }
                obj = db.digiouploadresponse(err, response, gatwayurl);
            } else if (err == 1) {
                obj = db.digiouploadresponse(err, "", "");
            }
            res.end(JSON.stringify(obj));
        });

    });


    app.post('/uploadKYCPdFFile', pdfUpload.single('pdf_path'), (req, res) => {
        console.log(req.body);
        let email_id = req.body.email_id;
        let mobile_no = req.body.mobile_no;
        let imageName = req.body.imageName;
        let pan = req.body.pan;
        let imagePath = documentPath + pan + "/" + imageName + ".pdf";
        db.saveKycPdfFilePath(email_id, mobile_no, imagePath, function (err, response) {
            let obj;
            if (err == 0) {
                obj = db.responseModelObject("0", "success");
            } else if (err == 1) {
                obj = db.responseModelObject("1", "failed");
            }
            res.setHeader("Content-Type", "application/json");
            res.end(JSON.stringify(obj));
        });
    }, (error, req, res, next) => {
        res.status(400).send({ error: error.message })
    })


    app.get("/digioReturResponse", function (req, res) {
        console.log("************ digioReturResponse **************");
        if (req.query.message == 'Signing Success' || req.query.message == 'SIGNING SUCCESS'
            || req.query.message == 'Signed Successfully' || req.query.message == 'SIGNED SCUCCESSFULLY') {
            console.log("************ digioReturResponse  SUCCESS**************");
            let email = req.query.email_id.split("?");
            db.digioReturResponse("success", email[0], req.query.digio_doc_id, email[1], documentPath, function (err, rows) {
                if (err == 0) {
                    res.render('RetunResponsePage.html');
                }
                else {
                    res.render('RetunResponsePageFail.html');
                }
            });
        } else {
            console.log("************ digioReturResponse  Failed**************");
            res.render('RetunResponsePageFail.html');
        }
    });

    const download = (url, dest, cb) => {
        const file = fs.createWriteStream(dest);
        let options = {
            'method': 'GET',
            'url': 'https://api.digio.in/v2/client/document/download?document_id=DID220302123734746YMZXTCIQ4GVIKP',
            'headers': {
                'Authorization': 'Basic QUlJVkM0QzQ3MVNBMkM3Qk9aUUxaRUlYTDRRRlQyNzM6SVFKMUFOV1VFUjJFT0Y0WUgzSVpBSDJTMTMyNVVTNE0='
            }
        };
        let axios = axios(options, (response) => {
            // check if response is success
            if (response.statusCode !== 200) {
                return cb('Response status was ' + response.statusCode);
            }
            response.pipe(file);
        });
        file.on('finish', () => file.close(cb));
        axios.on('error', (err) => {
            fs.unlink(dest, () => cb(err.message));
        });
        file.on('error', (err) => { 
            fs.unlink(dest, () => cb(err.message));
        });
    };
    

    app.post('/uploadBulkImage', imageUpload.array('images', 4), (req, res) => {
        res.send(req.files)
    }, (error, req, res, next) => {
        res.status(400).send({ error: error.message })
    })

    const videoStorage = multer.diskStorage({
        destination: function (req, file, cb) {
            console.log("body print " + req.body.pan);
            cb(null, documentPath + req.body.pan)
        },
        filename: (req, file, cb) => {
            console.log("ipv body print " + req.body.imageName);
            cb(null, req.body.imageName + "_WTS.mp4")
        }
    });

    const videoUpload = multer({
        storage: videoStorage,
        fileFilter(req, file, cb) {
            if (!file.originalname.match(/\.(mp4|MPEG-4|mkv)$/)) {
                return cb(new Error('Please upload a video'))
            }
            cb(undefined, true)
        }
    })


    app.post('/uploadIpvVideo', videoUpload.single('video'), (req, res) => {
        console.log("********************* uploadIpvVideo **********************");
        console.log(req.body);
        let email_id = req.body.email_id;
        let mobile_no = req.body.mobile_no;
        let imageName = req.body.imageName;
        let pan = req.body.pan;
        let ipvPath = documentPath + pan+ '/' + pan + 'IPV_WTS.mp4';
        console.log('ipvPath', ipvPath);
        let date = db.getDate();
        let location = req.body.latitude + ' - '+ req.body.longitude;
        console.log('Stamp');
        console.log(date + '\n' + location);
        let obj;
        let proc = ffmpeg()
            .videoFilters({
                filter: 'drawtext',
                options: {
                    fontfile: 'font.ttf',
                    text: date +'\n\n' + location,
                    fontsize: 14,
                    fontcolor: 'yellow',
                    x: 20,
                    y: 435,
                }
            })
            .input(ipvPath)
            .on('end', function () {
                console.log(pan + 'IPV file has been converted succesfully');
                fs.unlink(documentPath + pan + '/' + pan+ 'IPV_WTS.mp4', err => {
                    if (err) throw err;
                    else console.log(pan + 'WTS File deleted');
                })
            })
            .on('error', function (err) {
                console.log('an error happened: ' + err.message);
            })
            .save(documentPath + pan + '/' + pan+ 'IPV.mp4');
        db.saveIpvDetails(email_id, mobile_no, imageName, function (err, rows) {
            if (err == 0) {
                obj = db.responseModelObject("0", "success");
            } else if (err == 1) {
                obj = db.responseModelObject("1", "failed");
            }
            res.setHeader("Content-Type", "application/json");
            res.end(JSON.stringify(obj));
        });
    }, (error, req, res, next) => {
        console.log("error.message :- " + error.message);
        res.status(400).send({ error: error.message })
    })


    app.get("/viewDocuments", function (req, res) {
        console.log("********************* viewDocuments **********************");
        let email_id = req.query.email_id;
        let mobile_no = req.query.mobile_no;
        let type = req.query.type;
        let pan = req.query.pan;
        db.getDocuments(email_id, mobile_no, type, function (err, rows) {
            let rowsdata;
            if (type == "pancard") {
                rowsdata = rows[0].pancard;
            } else if (type == "signature") {
                rowsdata = rows[0].signature;
            } else if (type == "bankproof") {
                rowsdata = rows[0].bankproof;
            } else if (type == "addressproof") {
                rowsdata = rows[0].addressproof;
            } else if (type == "incomeproof") {
                rowsdata = rows[0].incomeproof;
            } else if (type == "photograph") {
                rowsdata = rows[0].photograph;
            } else if (type == "nominee") {
                rowsdata = rows[0].nominee_identity_proof;
            }
            let obj;
            if (err == 0) {

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
            } else if (err == 1) {
                console.log("error send image");
                obj = db.responseModelObject("1", "failed");
                res.setHeader("Content-Type", "application/json");
                res.end(JSON.stringify(obj));
            }
        });
    });

    
    app.get("/downloadFileVideo", function (req, res) {
        console.log("********************* downloadFileVideo **********************");
        let path = documentPath + req.query.pan+"/" + req.query.pan + 'IPV_WTS.mp4';
        fs.readFile(path, function (err, data) {
            if (err) {
                path = documentPath + req.query.pan+"/" + req.query.pan + 'IPV.mp4';
                console.log('IPV path', path);
                fs.readFile(path, function (err, data) {
                    if (!err) {
                        res.contentType("video/mp4");
                        res.send(data);
                    }
                    else {
                        console.log("Err downloadFileVideo");
                        res.send("Err downloadFileVideo");
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
    

}


