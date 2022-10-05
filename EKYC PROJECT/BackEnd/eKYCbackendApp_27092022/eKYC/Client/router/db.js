const fs = require("fs");
const mysql = require('mysql');
const Client = require('node-rest-client').Client;
const crypto = require('crypto');
const http = require('http');
const https = require('https');
const nodemailer = require('nodemailer');
const smtpTransport = require('nodemailer-smtp-transport');
const PDFDocument = require('pdf-lib').PDFDocument
const parseString = require('xml2js').parseString;
const request = require('request');

const myDbString = loadMyDBConfig();
const config = loadConfigFile();

let documentPath = __dirname;
documentPath = documentPath.replace('Client/router', 'Files/Documents/')

console.log('Dal Documents documentPath', documentPath);
const cdslUrl = 'https://www.cvlkra.com/'
const digioDevURL = 'https://ext.digio.in:444';
const digioProdURL = 'https://api.digio.in';

const devAuthKey = 'Basic QUlPOE9TTVE3VVFHTFpGQkU0WVJBQ0ZIVE9PS0VQOUI6NDhMR1BYNVpJUFdOVVExRjUyUFNHUUE5V1ZER1FENFI='
const prodAuthKey = 'Basic QUlJVkM0QzQ3MVNBMkM3Qk9aUUxaRUlYTDRRRlQyNzM6SVFKMUFOV1VFUjJFT0Y0WUgzSVpBSDJTMTMyNVVTNE0='

const enviromentKey = prodAuthKey;
const digioURL = digioProdURL;

if (config.isProduction == 'false') {
    digioURL = digioDevURL;
    enviromentKey = devAuthKey;
}

const digioPANVerifyAPIName = digioURL + '/v3/client/kyc/pan/verify';
const digioDocumentUpload = digioURL + '/v2/client/document/upload';
const digioBankVerify = digioURL + '/client/verify/bank_account';
const digiodownloaddocument = digioURL + '/v2/client/document/download?document_id=';
const idcardupload = digioURL + '/v3/client/kyc/analyze/file/idcard';

exports.poolCluster = poolCluster = mysql.createPoolCluster();
const clusterConfig = {
	removeNodeErrorCount: 1, // Remove the node immediately when connection fails.
	defaultSelector: 'ORDER'
};

const master_config = {
	connectionLimit: 50 ,
	host: myDbString.host,
	user: myDbString.user,
	password: myDbString.password,
	database: myDbString.db,
	debug: false,
	connectTimeout: 60 * 60 * 1000,
	acquireTimeout: 60 * 60 * 1000,
	timeout: 60 * 60 * 1000,
	defaultSelector: 'RR',
	multipleStatements: true,
	removeNodeErrorCount: 1
};

const slave_config = {
	connectionLimit: 50,
	host: myDbString.host2,
	user: myDbString.user2,
	password: myDbString.password2,
	database: myDbString.db2,
	debug: false,
	connectTimeout: 60 * 60 * 1000,
	acquireTimeout: 60 * 60 * 1000,
	timeout: 60 * 60 * 1000,
	defaultSelector: 'RR',
	multipleStatements: true,
	removeNodeErrorCount: 1
};

poolCluster.add(master_config); 
poolCluster.add('MASTER', master_config); 
poolCluster.add('SLAVE1', slave_config);

exports.dalResult = dalResult = {
    success: 0,
    connectionError: 1,
    queryError: 2,
    noResult: 3
}
console.log('**************************************************');
if (poolCluster) {
    console.log(`${myDbString.db} Database Connected on Host ${myDbString.host}`);
} else {
    console.log('Cant connect to db, Check ur db connection');
}

exports.getCurrTime = () => {
	let time = new Date();
	return time;
}

exports.getDateTime = function() {
    let dateObj = new Date();
    let dd = ((dateObj.getDate() / 10) >= 1) ? dateObj.getDate() : "0" + dateObj.getDate();
    let mm = (((dateObj.getMonth() + 1) / 10) >= 1) ? parseInt(dateObj.getMonth() + 1) : "0" + parseInt(dateObj.getMonth() + 1);
    let yyyy = dateObj.getFullYear();
    let hrs = dateObj.getHours();
    let mins = dateObj.getMinutes();
    let secs = dateObj.getSeconds();
    let dateTimeStamp = `${dd}-${mm}-${yyyy} ${hrs}:${mins}:${secs}`
    return dateTimeStamp;
}

exports.getDate = function() {
    let date_ob = new Date();
    let day = ("0" + date_ob.getDate()).slice(-2);
    let month = ("0" + (date_ob.getMonth() + 1)).slice(-2);
    let year = date_ob.getFullYear();
    let dateToSend = day + "-" + month + "-" + year;
    return dateToSend;
}

exports.getTime = function() {
    let date_ob = new Date();
    let hours = date_ob.getHours();
    let minutes = ("0" + date_ob.getMinutes()).slice(-2);
    let seconds = ("0" + date_ob.getSeconds()).slice(-2);
    let time = hours + ":" + minutes + ":" + seconds;
    return time;
}

exports.loadMyDBConfig = loadMyDBConfig;
function loadMyDBConfig () {
    let fileCSV = '\n';
    fileCSV += fs.readFileSync("./mydbconfig.txt").toString('utf8');
    let json = "";
    let ignore = false;
    let started = false;
    for (let i = 0, len = fileCSV.length; i < len; i++) {
        if (fileCSV[i] == '#') {
            ignore = true;
        } else if (fileCSV[i] == '\n' || fileCSV[i] == '\r') {
            ignore = false;
            if (started == false) {
                json += '"';
                started = true;
            } else {
                json += '","';
            }
        } else if (ignore == false && fileCSV[i] != ' ' && fileCSV[i] != '\t') {
            if (fileCSV[i] == '=')
                json = json += '":"';
            else
                json = json + fileCSV[i];
        }
    }
    let temp = json; json = '';
    for (let i = 0, len = temp.length - 2; i < len; i++) {
        if (temp[i] == ',' && temp[i + 1] == '"' && temp[i + 2] == '"')
            i += 2;
        else
            json += temp[i];
    }
    if (json.substr(0, 3) == '"",')
        json = json.substr(3);

    json = "{" + json + "}";
    let config = JSON.parse(json);
    return config;
}

exports.loadConfigFile = loadConfigFile;
function loadConfigFile () {
    let fileCSV = '\n';
    fileCSV += fs.readFileSync("./config.txt").toString('utf8');
    let json = "";
    let ignore = false;
    let started = false;
    for (let i = 0, len = fileCSV.length; i < len; i++) {
        if (fileCSV[i] == '#') {
            ignore = true;
        } else if (fileCSV[i] == '\n' || fileCSV[i] == '\r') {
            ignore = false;
            if (started == false) {
                json += '"';
                started = true;
            } else {
                json += '","';
            }
        } else if (ignore == false && fileCSV[i] != ' ' && fileCSV[i] != '\t') {
            if (fileCSV[i] == '=')
                json = json += '":"';
            else
                json = json + fileCSV[i];
        }
    }
    let temp = json; json = '';
    for (let i = 0, len = temp.length - 2; i < len; i++) {
        if (temp[i] == ',' && temp[i + 1] == '"' && temp[i + 2] == '"')
            i += 2;
        else
            json += temp[i];
    }
    if (json.substr(0, 3) == '"",')
        json = json.substr(3);

    json = "{" + json + "}";
    let config = JSON.parse(json);
    return config;
}

function query(query, func) {
    poolCluster.getConnection(function (err, connection) {
        if (err) {
            console.log('SQLITE DB Slave: ERR 1 == ' + err);
            func(dalResult.connectionError, 0);
            return;
        }
        console.log("query :" + query);
        connection.query(query, function (err, rows) {
            connection.release();
            if (!err) {
                if (rows.length == 0) {
                    func(dalResult.noResult, 0);
                    return;
                }
                else {
                    func(dalResult.success, rows);
                    return;
                }
            }
            else {
                func(dalResult.queryError, 0);
                console.log('SQLITE DB Slave: ERR 2 == ' + err);
                return;
            }
        });
        connection.on('error', function (err) {

            connection.release();
            console.log('Error in on error : ' + err);
            console.log('SQLITE DB Slave: ERR 3 == ');
            func(dalResult.connectionError, 0);
            return;
        });
    });
}

function querymaster(query, func) {
    poolCluster.getConnection('MASTER', function (err, connection) {
        if (err) {
            console.log('SQLITE DB Slave: ERR 1 == ' + err);
            func(dalResult.connectionError, 0);
            return;
        }
        connection.query(query, function (err, rows) {
            connection.release();
            if (!err) {
                if (rows.length == 0) {
                    func(dalResult.noResult, 0);
                    return;
                }
                else {
                    func(dalResult.success, rows);
                    return;
                }
            }
            else {
                func(dalResult.queryError, 0);
                console.log('SQLITE DB Master: ERR 2 == ' + err);
                return;
            }
        });

        connection.on('error', function (err) {
            connection.release();
            console.log('Error in on error : ' + err);
            console.log('SQLITE DB Master: ERR 3 == ');
            func(dalResult.connectionError, 0);
            return;
        });
    });
}

function bulkquery(query, myArray, func) {
    console.log("QUERY=" + query);
    poolCluster.getConnection(function (err, connection) {
        if (err) {
            console.log('SQLITE DB: ERR 1 == ' + err);
            func(dalResult.connectionError, 0);
            return;
        }
        connection.query(query, [myArray], function (err, rows) {
            connection.release();
            if (!err) {
                if (rows.length == 0) {
                    func(dalResult.noResult, 0);
                    return;
                }
                else {
                    func(dalResult.success, rows);
                    return;
                }
            }
            else {
                func(dalResult.queryError, 0);
                console.log('SQLITE DB: ERR 2 == ' + err);
                return;
            }
        });
        connection.on('error', function (err) {
            connection.release();
            console.log('Error in on error : ' + err);
            console.log('SQLITE DB: ERR 3 == ');
            func(dalResult.connectionError, 0);
            return;
        });
    });
}

exports.createEkycTable = function (func) {
    query(`CREATE TABLE IF NOT EXISTS ekyc (
        unique_id int(11) NOT NULL AUTO_INCREMENT, 
        stage varchar(50) NOT NULL, 
        full_name varchar(45) NOT NULL, 
        email_id varchar(45) NOT NULL, 
        mobile_no varchar(45) NOT NULL, 
        client_code varchar(45) NOT NULL, 
        mobile_otp varchar(45) NOT NULL, 
        email_otp varchar(45) NOT NULL, 
        otp_verified varchar(6) NOT NULL, 
        lextra1 varchar(45) NOT NULL, 
        lextra2 varchar(45) NOT NULL, 
        aadhar varchar(20) NOT NULL, 
        pan varchar(45) NOT NULL, 
        panfullname varchar(45) NOT NULL, 
        dob varchar(45) NOT NULL, 
        pextra1 varchar(45) NOT NULL, 
        pextra2 varchar(45) NOT NULL, 
        nse_cash varchar(45) NOT NULL, 
        nse_fo varchar(45) NOT NULL, 
        nse_currency varchar(45) NOT NULL, 
        mcx_commodty varchar(45) NOT NULL, 
        bse_cash varchar(45) NOT NULL, 
        bse_fo varchar(45) NOT NULL, 
        bse_currency varchar(45) NOT NULL, 
        ncdex_commodty varchar(45) NOT NULL, 
        res_addr_1 varchar(45) NOT NULL, 
        res_addr_2 varchar(45) NOT NULL, 
        res_addr_state varchar(45) NOT NUll, 
        res_addr_city varchar(45) NOT NUll, 
        res_addr_pincode varchar(45) NOT NUll, 
        parm_addr_1 varchar(45) NOT NULL, 
        parm_addr_2 varchar(45) NOT NULL, 
        parm_addr_state varchar(45) NOT NUll, 
        parm_addr_city varchar(45) NOT NUll, 
        parm_addr_pincode varchar(45) NOT NUll, 
        nationality varchar(45) NOT NULL, 
        gender varchar(45) NOT NULL, 
        firstname1 varchar(45) NOT NULL, 
        middlename1 varchar(45) NOT NULL, 
        lastname1 varchar(45) NOT NULL, 
        maritalstatus varchar(45) NOT NULL, 
        fatherspouse varchar(45) NOT NULL, 
        firstname2 varchar(45) NOT NULL, 
        middlename2 varchar(45) NOT NULL, 
        lastname2 varchar(45) NOT NULL, 
        firstname_mother varchar(30) NOT NULL, 
        middlename_mother varchar(30) NOT NULL, 
        lastname_mother varchar(30) NOT NULL, 
        incomerange varchar(45) NOT NULL, 
        occupation varchar(45) NOT NULL, 
        action varchar(45) NOT NULL, 
        past_regulatory_action_details text NOT NULL, 
        perextra1 varchar(45) NOT NULL, 
        perextra2 varchar(45) NOT NULL, 
        ifsccode varchar(45) NOT NULL, 
        accountnumber varchar(45) NOT NULL, 
        bankname varchar(45) NOT NULL, 
        verified_id varchar(45) NOT NULL, 
        beneficiary_name_with_bank varchar(45) NOT NULL, 
        verified_at varchar(45) NOT NULL, 
        bextra1 varchar(45) NOT NULL, 
        bextra2 varchar(45) NOT NULL, 
        pack varchar(45) NOT NULL, 
        payextra1 varchar(45) NOT NULL, 
        payextra2 varchar(45) NOT NULL, 
        pancard varchar(100) NOT NULL, 
        signature varchar(100) NOT NULL, 
        bankproof varchar(100) NOT NULL, 
        bankprooftype varchar(100) NOT NULL, 
        addressproof varchar(100) NOT NULL, 
        addressprooftype varchar(100) NOT NULL, 
        incomeproof varchar(100) NOT NULL, 
        incomeprooftype varchar(100) NOT NULL, 
        photograph varchar(100) NOT NULL, 
        uextra1 varchar(45) NOT NULL, 
        uextra2 varchar(45) NOT NULL, 
        ipv varchar(100) NOT NULL, 
        iextra1 varchar(45) NOT NULL, 
        iextra2 varchar(45) NOT NULL, 
        esign varchar(45) NOT NULL, 
        esignaddhar varchar(100) NOT NULL, 
        ekycdocument varchar(250) NOT NULL, 
        latitude varchar(45) NOT NULL, 
        longitude varchar(45) NOT NULL, 
        bank_address varchar(250) NOT NULL, 
        micr_no varchar(9) NOT NULL, 
        address_proof_id VARCHAR(50) NOT NULL, 
        nominee_name VARCHAR(100) NOT NULL, 
        nominee_relation VARCHAR(20) NOT NULL, 
        nominee_identity_proof VARCHAR(200) NOT NULL, 
        latitude VARCHAR(45) NOT NULL, 
        longitude VARCHAR(45) NOT NULL, 
        bank_address VARCHAR(250) NOT NULL, 
        bank_address_state varchar(30) NOT NULL, 
        bank_address_city varchar(30) NOT NULL, 
        bank_address_pincode varchar(10) NOT NULL, 
        bank_address_contactno varchar(15) NOT NULL, 
        bank_branch varchar(30) NOT NULL,
        micr_no VARCHAR(9) NOT NULL, 
        address_proof_id VARCHAR(50) NOT NULL, 
        nominee_name VARCHAR(100) NOT NULL, 
        nominee_relation VARCHAR(20) NOT NULL, 
        nominee_identity_proof VARCHAR(200) NOT NULL
        nominee_email varchar(50) NOT NULL, 
        nom_addr_1 varchar(50) NOT NULL, 
        nom_addr_2 varchar(50) NOT NULL, 
        nom_addr_state varchar(50) NOT NULL, 
        nom_addr_city varchar(50) NOT NULL, 
        nom_addr_pincode varchar(10) NOT NULL, 
        json_digiosign_response text NOT NULL, 
        json_pennydrop_response text NOT NULL, 
        json_atom_response text NOT NULL, 
        remark text NOT NULL, 
        eKycStartDate varchar(50) NOT NULL, 
        eKycLastUpdateTime varchar(50) NOT NULL,
        expiryDate varchar(30) NOT NULL,
        PRIMARY KEY (unique_id, email_id, mobile_no) USING BTREE ) ENGINE=InnoDB DEFAULT CHARSET=latin1`, func)
}

exports.createEkycTable = function (func) {
    query("CREATE TABLE IF NOT EXISTS `ekyc` ( `unique_id` int(11) NOT NULL AUTO_INCREMENT, `stage` varchar(50) NOT NULL, `full_name` varchar(45) NOT NULL, `email_id` varchar(45) NOT NULL, `mobile_no` varchar(45) NOT NULL, `client_code` varchar(45) NOT NULL, `mobile_otp` varchar(45) NOT NULL, `email_otp` varchar(45) NOT NULL, `otp_verified` varchar(6) NOT NULL, `lextra1` varchar(45) NOT NULL, `lextra2` varchar(45) NOT NULL, `pan` varchar(45) NOT NULL, `panfullname` varchar(45) NOT NULL, `dob` varchar(45) NOT NULL, `pextra1` varchar(45) NOT NULL, `pextra2` varchar(45) NOT NULL, `nse_cash` varchar(45) NOT NULL, `nse_fo` varchar(45) NOT NULL, `nse_currency` varchar(45) NOT NULL, `mcx_commodty` varchar(45) NOT NULL, `bse_cash` varchar(45) NOT NULL, `bse_fo` varchar(45) NOT NULL, `bse_currency` varchar(45) NOT NULL, `ncdex_commodty` varchar(45) NOT NULL, `res_addr_1` varchar(45) NOT NULL, `res_addr_2` varchar(45) NOT NULL, `res_addr_state` varchar(45) NOT NUll, `res_addr_city` varchar(45) NOT NUll, `res_addr_pincode` varchar(45) NOT NUll, `parm_addr_1` varchar(45) NOT NULL, `parm_addr_2` varchar(45) NOT NULL, `parm_addr_state` varchar(45) NOT NUll, `parm_addr_city` varchar(45) NOT NUll, `parm_addr_pincode` varchar(45) NOT NUll, `nationality` varchar(45) NOT NULL, `gender` varchar(45) NOT NULL, `firstname1` varchar(45) NOT NULL, `middlename1` varchar(45) NOT NULL, `lastname1` varchar(45) NOT NULL, `maritalstatus` varchar(45) NOT NULL, `fatherspouse` varchar(45) NOT NULL, `firstname2` varchar(45) NOT NULL, `middlename2` varchar(45) NOT NULL, `lastname2` varchar(45) NOT NULL, `incomerange` varchar(45) NOT NULL, `occupation` varchar(45) NOT NULL, `action` varchar(45) NOT NULL, `perextra1` varchar(45) NOT NULL, `perextra2` varchar(45) NOT NULL, `ifsccode` varchar(45) NOT NULL, `accountnumber` varchar(45) NOT NULL, `bankname` varchar(45) NOT NULL, `verified_id` varchar(45) NOT NULL, `beneficiary_name_with_bank` varchar(45) NOT NULL, `verified_at` varchar(45) NOT NULL, `bextra1` varchar(45) NOT NULL, `bextra2` varchar(45) NOT NULL, `pack` varchar(45) NOT NULL, `payextra1` varchar(45) NOT NULL, `payextra2` varchar(45) NOT NULL, `pancard` varchar(100) NOT NULL, `signature` varchar(100) NOT NULL, `bankproof` varchar(100) NOT NULL, `bankprooftype` varchar(100) NOT NULL, `addressproof` varchar(100) NOT NULL, `addressprooftype` varchar(100) NOT NULL, `incomeproof` varchar(100) NOT NULL, `incomeprooftype` varchar(100) NOT NULL, `photograph` varchar(100) NOT NULL, `uextra1` varchar(45) NOT NULL, `uextra2` varchar(45) NOT NULL, `ipv` varchar(100) NOT NULL, `iextra1` varchar(45) NOT NULL, `iextra2` varchar(45) NOT NULL, `esign` varchar(45) NOT NULL, `esignaddhar` varchar(100) NOT NULL, `ekycdocument` varchar(250) NOT NULL, `latitude` varchar(45) NOT NULL, `longitude` varchar(45) NOT NULL, `bank_address` varchar(250) NOT NULL, `micr_no` varchar(9) NOT NULL, `address_proof_id` VARCHAR(50) NOT NULL, `nominee_name` VARCHAR(100) NOT NULL, `nominee_relation` VARCHAR(20) NOT NULL, `nominee_identity_proof` VARCHAR(200) NOT NULL, PRIMARY KEY (`unique_id`,`email_id`,`mobile_no`) USING BTREE ) ENGINE=InnoDB DEFAULT CHARSET=latin1;", func)
}


exports.selectIfColumExist = function (func) {
    query("SELECT latitude, longitude, bank_address, micr_no, address_proof_id, past_regulatory_action_details, aadhar, nominee_name, nominee_relation, nominee_identity_proof, nominee_email, nom_addr_1, nom_addr_2, nom_addr_state, nom_addr_city, nom_addr_pincode, json_digiosign_response,  json_pennydrop_response, json_atom_response, remark, eKycStartDate, eKycLastUpdateTime from ekyc", func)
}

exports.alterEkycTable = function (func) {
    query("ALTER TABLE `ekyc` ADD COLUMN (`latitude` VARCHAR(45) NOT NULL, `longitude` VARCHAR(45) NOT NULL, `bank_address` VARCHAR(250) NOT NULL, `micr_no` VARCHAR(9) NOT NULL, `address_proof_id` VARCHAR(50) NOT NULL, `nominee_name` VARCHAR(100) NOT NULL, `nominee_relation` VARCHAR(20) NOT NULL, `nominee_identity_proof` VARCHAR(200) NOT NULL)", func)
    query("alter table ekyc ADD COLUMN past_regulatory_action_details text NOT NULL AFTER action, ADD COLUMN aadhar varchar(20) NOT NULL AFTER `lextra2`, ADD COLUMN nominee_email varchar(50) NOT NULL, ADD COLUMN nom_addr_1 varchar(50) NOT NULL, ADD COLUMN nom_addr_2 varchar(50) NOT NULL, ADD COLUMN nom_addr_state varchar(50) NOT NULL, ADD COLUMN nom_addr_city varchar(50) NOT NULL, ADD COLUMN nom_addr_pincode varchar(10) NOT NULL, ADD COLUMN json_digiosign_response text NOT NULL, ADD COLUMN json_pennydrop_response text NOT NULL, ADD COLUMN json_atom_response text NOT NULL, ADD COLUMN remark text NOT NULL, ADD COLUMN eKycStartDate varchar(50) NOT NULL, ADD COLUMN eKycLastUpdateTime varchar(50) NOT NULL", func);
    query("alter table ekyc ADD COLUMN firstname_mother varchar(30) NOT NULL AFTER lastname2, ADD COLUMN middlename_mother varchar(30) NOT NULL AFTER firstname_mother, ADD COLUMN lastname_mother varchar(30) NOT NULL after middlename_mother, ADD COLUMN expiryDate varchar(30) NOT NULL", func);
    query("alter table ekyc ADD COLUMN bank_address_state varchar(30) NOT NULL AFTER bank_address, ADD COLUMN bank_address_city varchar(30) NOT NULL AFTER bank_address_state, ADD COLUMN bank_address_pincode varchar(10) NOT NULL after bank_address_city, ADD COLUMN bank_address_contactno varchar(15) NOT NULL after bank_address_pincode, ADD COLUMN bank_branch varchar(30) NOT NULL after bank_address_contactno", func);
}

exports.selectIfColumExistOTP = function (func) {
    query("SELECT otp_verified from ekyc", func)
}

exports.alterEkycTableOTPVerified = function (func) {
    query("ALTER TABLE `ekyc` ADD COLUMN `otp_verified` VARCHAR(6) NOT NULL AFTER `email_otp`", func)
}

exports.pdfmerger = async function (bank_proof, income_proof, pan, documentPath, func) {
    console.log('pdfmerger call', 'success');
    let bankProof = fs.readFileSync(documentPath +  pan + "/" + bank_proof);
    let incomeProof = fs.readFileSync(documentPath + pan + "/" + income_proof);
    let pdfsToMerge = [bankProof, incomeProof]

    const mergedPdf = await PDFDocument.create();
    for (const pdfBytes of pdfsToMerge) {
        const pdf = await PDFDocument.load(pdfBytes, { ignoreEncryption: true });
        const copiedPages = await mergedPdf.copyPages(pdf, pdf.getPageIndices());
        copiedPages.forEach((page) => {
            mergedPdf.addPage(page);
        });
    }
    const buf = await mergedPdf.save();        // Uint8Array
    let path = documentPath + pan + "/" + pan + 'merge.pdf';
    fs.open(path, 'w', function (err, fd) {
        fs.write(fd, buf, 0, buf.length, null, function (err) {
            fs.close(fd, function () {
                console.log('merge first file successfully');
                mergeFinalPdf(documentPath, pan, path, func);
            });
        });
    });
}

async function mergeFinalPdf(documentPath, pan, path, func) {
    let KycDocument = fs.readFileSync(documentPath + pan + "/" + pan + "KycDocument.pdf");
    let mergepath = fs.readFileSync(path);
    let pdfsToMerges = [KycDocument, mergepath]

    const mergedPdfs = await PDFDocument.create();
    for (const pdfBytes of pdfsToMerges) {
        const pdf = await PDFDocument.load(pdfBytes, { ignoreEncryption: true });
        const copiedPages = await mergedPdfs.copyPages(pdf, pdf.getPageIndices());
        copiedPages.forEach((page) => {
            mergedPdfs.addPage(page);
        });
    }
    const bufs = await mergedPdfs.save();
    let paths = documentPath + pan + "/" + pan + 'FinalEKycDocument.pdf';
    fs.open(paths, 'w', function (err, fd) {
        fs.write(fd, bufs, 0, bufs.length, null, function (err) {
            fs.close(fd, function () {
                console.log('merge second file successfully');
                fs.unlinkSync(documentPath + pan + "/" + pan + 'KycDocument.pdf');
                fs.unlinkSync(documentPath + pan + "/" + pan + 'merge.pdf');
                func(0, paths);
            });
        });
    });
}

exports.getContractsInfo = function (token, func) {
    query("Select * from contracts where lourtoken = '" + token + "'", func)
}

exports.digioReturResponse = function (success, email_id, digio_doc_id, pan, documentPath, func) {
    console.log('INSIDE EKYC', success, email_id, pan, digio_doc_id);
    let https = require('follow-redirects').https;
    let options;
    if (config.isProduction == 'false') {
        options = {
            'method': 'GET',
            'hostname': 'ext.digio.in:444',
            'path': digiodownloaddocument + digio_doc_id + '',
            'headers': {
                'Authorization': enviromentKey
            },
            'maxRedirects': 20
        };
    } else {
        options = {
            'method': 'GET',
            'hostname': 'api.digio.in',
            'path': digiodownloaddocument + digio_doc_id + '',
            'headers': {
                'Authorization': enviromentKey
            },
            'maxRedirects': 20
        };
    }
    let req = https.request(options, function (res) {
        let chunks = [];

        res.on("data", function (chunk) {
            chunks.push(chunk);
        });

        res.on("end", function (chunk) {
            let body = Buffer.concat(chunks);
            let file = documentPath +  pan + '/' + pan + 'FinalEKycDocument.pdf';
            fs.writeFile(file, body, function (err) {
                if (err) {
                    console.log(err);
                    console.log("Error in  PDF file!");
                }
                else {
                    console.log("Final PDF file was saved!");
                }
            });
        });

        res.on("error", function (error) {
            console.error(error);
        });
    });
    req.end();
    query("UPDATE ekyc SET stage = 9, esign = 0, eKycLastUpdateTime = from_unixtime(unix_timestamp()) WHERE email_id = '" + email_id + "'", func)
}

exports.getEkycUserDetails = function (email_id, mobile_no, func) {
    console.log('INSIDE EKYC');
    query("SELECT * FROM ekyc WHERE email_id='" + email_id + "' and mobile_no='" + mobile_no + "'", func)
}

exports.saveEkycLoginDetails = function (full_name, email_id, mobile_no, client_code, mobile_otp, email_otp, is_otp_verified, func) {
    console.log('INSIDE EKYC SaveEkycLoginDetails');
    query("SELECT * FROM ekyc WHERE email_id='" + email_id + "' and mobile_no='" + mobile_no + "'", function (err, rows) {
        if (err == 0) {
            if (rows.length > 0) {
                console.log("no need to insert only update");
                query("UPDATE ekyc SET stage = 0, full_name='" + full_name + "', client_code='" + client_code + "', mobile_otp='" + mobile_otp + "', email_otp='" + email_otp + "', otp_verified='" + is_otp_verified + "', eKycStartDate = from_unixtime(unix_timestamp()), eKycLastUpdateTime = from_unixtime(unix_timestamp()) WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", func)
            } else {
                query("INSERT INTO ekyc (stage ,full_name, email_id, mobile_no, client_code, mobile_otp, email_otp,otp_verified, eKycStartDate, eKycLastUpdateTime, expiryDate) VALUES ('-1','" + full_name + "', '" + email_id + "'," + mobile_no + "," + client_code + "," + mobile_otp + "," + email_otp + "," + is_otp_verified + ", from_unixtime(unix_timestamp()), from_unixtime(unix_timestamp()), DATE_ADD(from_unixtime(unix_timestamp()), INTERVAL 3 MONTH));", func)
            }
        } else {
            query("INSERT INTO ekyc (stage ,full_name, email_id, mobile_no, client_code, mobile_otp, email_otp,otp_verified, eKycStartDate, eKycLastUpdateTime, expiryDate) VALUES ('-1','" + full_name + "', '" + email_id + "'," + mobile_no + "," + client_code + "," + mobile_otp + "," + email_otp + "," + is_otp_verified + ", from_unixtime(unix_timestamp()), from_unixtime(unix_timestamp()), DATE_ADD(from_unixtime(unix_timestamp()), INTERVAL 3 MONTH));", func)
        }
    });
}

exports.savePanDetails = function (email_id, mobile_no, pan, panfullname, dob, func) {
    console.log('INSIDE EKYC', email_id, mobile_no, pan, panfullname, dob);
    let options = {
        'method': 'POST',
        'url': digioPANVerifyAPIName,
        'headers': {
            'Authorization': enviromentKey,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "pan_no": pan,
            "full_name": panfullname,
            "date_of_birth": dob
        })

    };
    request(options, function (error, response) {
        if (error) {
            console.log("Digio Pan validate error" + error);
            func(4, 0);
        } else {
            console.log("Digio Pan validate" + response.body);
            try {
                let objData = JSON.parse(response.body);
                console.log('SavePanDetails objData 1',objData);
                console.log(objData.is_pan_dob_valid);
                console.log(objData.name_matched);
                let panvalidate = false;

                if (objData.is_pan_dob_valid) {
                    panvalidate = true;

                    if (objData.name_matched) {

                        panvalidate = true;
                    } else if (objData.error_message != null) {

                        console.log("INVALID " + objData.error_message);
                        func(1, 0);
                    }
                    else {
                        panvalidate = false;
                        console.log("INVALID PAN NAME");
                        func(2, 0);
                    }
                } else {
                    panvalidate = false;
                    console.log("INVALID PAN DOB");
                    func(3, 0);
                }
                if (panvalidate) {
                    console.log("Digio Pan panvalidate true" + response.body);
                    validatePanOnLogin(pan, dob, email_id, mobile_no, function (err, rows) {
                        query("UPDATE ekyc SET stage = 1, pan='" + pan + "',panfullname='" + panfullname + "', dob='" + dob + "', eKycLastUpdateTime = from_unixtime(unix_timestamp())  WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", func)
                    });
                }
            } catch (e) {
                func(4, 0);
            }
        }
    });
}

function savePdfFilePath (email_id, mobile_no, pdfpath, name, func) {
    console.log('INSIDE EKYC', email_id, mobile_no, pdfpath, name);
    query("UPDATE ekyc SET esignaddhar = '" + pdfpath + "' WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", function (err, rows) {
        if (err == 0) {
            func(0, 0);
        } else {
            func(1, 0);
        }
    });
}

function saveCdslPanData(dob, app_name, gender, FName, Nationality, ResStatus,
    UIDNo, CorrAddr1, CorrAddr2, CorrAddr3, CorrCity, CorrPin, CorrState, CorrCntry, OffNo, ResNo, MobNo, FaxNo, Email,
    PerAddr1, PerAddr2, PerAddr3, PerCity, PerPin, PerState, PerCntry, CorrAddrRef, PerAddrRef, email_id, mobile_no, func) {
    console.log('INSIDE EKYC', dob[0], app_name[0], gender[0], FName[0], Nationality[0], ResStatus[0],
        UIDNo[0], CorrAddr1[0], CorrAddr2[0], CorrAddr3[0], CorrCity[0], CorrPin[0], CorrState[0], CorrCntry[0], OffNo[0], ResNo[0], MobNo[0], FaxNo[0], Email[0],
        PerAddr1[0], PerAddr2[0], PerAddr3[0], PerCity[0], PerPin[0], PerState[0], PerCntry[0], CorrAddrRef[0], PerAddrRef[0], email_id, mobile_no);
    let gen;
    if (gender[0].includes('M')) {
        gen = "Male"
    } else if (gender[0].includes('F')) {
        gen = "Female"
    } else {
        gen = "Other"
    }
    let fathername
    let fatherspousebool;
    if (FName[0].length == 6) {
        fathername = ""
    } else {
        fatherspousebool = "father"
        fathername = FName[0]
    }
    let corpstatename = getStateName(CorrState[0])
    let perstatename = getStateName(PerState[0])
    query("UPDATE ekyc SET  res_addr_1 = '" + CorrAddr1[0] + "',res_addr_2 = '" + CorrAddr2[0] + "',res_addr_state = '" + corpstatename + "',res_addr_city='" + CorrCity[0] + "' ,res_addr_pincode='" + CorrPin[0] + "' ,parm_addr_1='" + PerAddr1[0] + "' ,parm_addr_2='" + PerAddr2[0] + "' ,parm_addr_state='" + perstatename + "' ,parm_addr_city='" + PerCity[0] + "' ,parm_addr_pincode='" + PerPin[0] + "' ,gender='" + gen + "' ,firstname1='" + app_name[0] + "' ,fatherspouse='" + fatherspousebool + "' ,firstname2='" + fathername + "' WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", func)
}

exports.saveKycPdfFilePath = function (email_id, mobile_no, pdfpath, func) {
    console.log('INSIDE SaveKycPDfFilePath', email_id, mobile_no, pdfpath);
    query("UPDATE ekyc SET ekycdocument = '" + pdfpath + "' WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", func)
}

function getStateName(statecode) {
    let statname;
    if (statecode.length != 6) {
        if (statecode.includes('001')) {
            statname = "JAMMU AND KASHMIR"
        } else if (statecode.includes('002')) {
            statname = "HIMACHAL PRADESH"
        } else if (statecode.includes('003')) {
            statname = "PUNJAB"
        } else if (statecode.includes('004')) {
            statname = "CHANDIGARH"
        } else if (statecode.includes('005')) {
            statname = "UTTARAKHAND"
        } else if (statecode.includes('006')) {
            statname = "HARYANA"
        } else if (statecode.includes('007')) {
            statname = "DELHI"
        } else if (statecode.includes('008')) {
            statname = "RAJASTHAN"
        } else if (statecode.includes('009')) {
            statname = "UTTAR PRADESH"
        } else if (statecode.includes('010')) {
            statname = "BIHAR"
        } else if (statecode.includes('011')) {
            statname = "SIKKIM"
        } else if (statecode.includes('012')) {
            statname = "ARUNACHAL PRADESH"
        } else if (statecode.includes('013')) {
            statname = "ASSAM"
        } else if (statecode.includes('014')) {
            statname = "MANIPUR"
        } else if (statecode.includes('015')) {
            statname = "MIZORAM"
        } else if (statecode.includes('016')) {
            statname = "TRIPURA"
        } else if (statecode.includes('017')) {
            statname = "MEGHALAYA"
        } else if (statecode.includes('018')) {
            statname = "NAGALAND"
        } else if (statecode.includes('019')) {
            statname = "WEST BENGAL"
        } else if (statecode.includes('020')) {
            statname = "JHARKHAND"
        } else if (statecode.includes('021')) {
            statname = "ORISSA"
        } else if (statecode.includes('022')) {
            statname = "CHHATTISGARH"
        } else if (statecode.includes('023')) {
            statname = "MADHYA PRADESH"
        } else if (statecode.includes('024')) {
            statname = "GUJARAT"
        } else if (statecode.includes('025')) {
            statname = "DAMAN AND DIU"
        } else if (statecode.includes('026')) {
            statname = "DADRA AND NAGAR HAVELI"
        } else if (statecode.includes('027')) {
            statname = "MAHARASHTRA"
        } else if (statecode.includes('028')) {
            statname = "ANDHRA PRADESH"
        } else if (statecode.includes('029')) {
            statname = "KARNATAKA"
        } else if (statecode.includes('030')) {
            statname = "GOA"
        } else if (statecode.includes('031')) {
            statname = "LAKSHADWEEP"
        } else if (statecode.includes('032')) {
            statname = "KERALA"
        } else if (statecode.includes('033')) {
            statname = "TAMIL NADU"
        } else if (statecode.includes('034')) {
            statname = "PUDUCHERRY"
        } else if (statecode.includes('035')) {
            statname = "ANDAMAN AND NICOBAR ISLANDS"
        } else if (statecode.includes('036')) {
            statname = "APO"
        } else if (statecode.includes('037')) {
            statname = "TELANGANA"
        } else {
            statname = "OTHERS"
        }
    }
    return statname;
}

exports.pdfupload = function (email_id, mobile_no, imagePath, name, func) {
    console.log('INSIDE EKYC', email_id, mobile_no, imagePath, name);
    savePdfFilePath(email_id, mobile_no, imagePath, name, function (err, rows) {
        if (err == 0) {
            let options = {
                'method': 'POST',
                'url': digioDocumentUpload,
                'headers': {
                    'Authorization': enviromentKey
                },
                formData: {
                    'file': {
                        'value': fs.createReadStream(imagePath),
                        'options': {
                            'filename': imagePath,
                            'contentType': null
                        }
                    },
                    'request': '{"signers":[{"identifier":"' + email_id + '","name":"' + name + '","sign_type":"aadhaar","reason":"Loan Agreement"}],"expire_in_days":10,"display_on_page":"all"}'
                }
            };
            request(options, function (error, response) {
                if (error) {
                    console.log(error);
                    func(1, error);
                } else {
                    query("UPDATE ekyc SET stage = 7, eKycLastUpdateTime = from_unixtime(unix_timestamp())  WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "'", func)
                    func(0, response.body);
                    console.log(response.body);
                }
            });
        } else {
            func(1, 0);
        }
    });
}

exports.saveEKYCPersonalDetails = function (email_id, mobile_no, nseCash,
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
    func) {
        console.log('INSIDE EKYC Personal Details', email_id, mobile_no, nseCash, nsefo, nseCurrency, bseCash, bsefo, bseCurrency, mcxCommodity, ncdexCommodity, res_addr_1, res_addr_2, res_addr_state, res_addr_city, res_addr_pincode, parm_addr_1, parm_addr_2, parm_addr_state, parm_addr_city, parm_addr_pincode, nationality, gender, firstname1, middlename1, lastname1, maritalstatus, fatherspouse, firstname2, middlename2, lastname2, incomerange, occupation, action, perextra1, perextra2, past_regulatory_action_details, aadhar, nominee_name, nominee_relation, nominee_email, nom_addr_1, nom_addr_2, nom_addr_state, nom_addr_city, nom_addr_pincode);
        query("UPDATE ekyc SET stage = 2, nse_cash='" + nseCash + "', nse_fo='" + nsefo + "', nse_currency='" + nseCurrency + "', bse_cash='" + bseCash + "', bse_fo='" + bsefo + "', bse_currency='" + bseCurrency + "', mcx_commodty='" + mcxCommodity + "', ncdex_commodty='" + ncdexCommodity + "', res_addr_1='" + res_addr_1 + "', res_addr_2='" + res_addr_2 + "', res_addr_state='" + res_addr_state + "', res_addr_city='" + res_addr_city + "', res_addr_pincode='" + res_addr_pincode + "', parm_addr_1='" + parm_addr_1 + "', parm_addr_2='" + parm_addr_2 + "', parm_addr_state='" + parm_addr_state + "', parm_addr_city='" + parm_addr_city + "', parm_addr_pincode='" + parm_addr_pincode + "', nationality='" + nationality + "', gender='" + gender + "', firstname1='" + firstname1 + "', middlename1='" + middlename1 + "', lastname1='" + lastname1 + "', maritalstatus='" + maritalstatus + "', fatherspouse='" + fatherspouse + "', firstname2='" + firstname2 + "', middlename2='" + middlename2 + "', lastname2='" + lastname2 + "', incomerange='" + incomerange + "', occupation='" + occupation + "', action='" + action + "', perextra1='" + perextra1 + "', perextra2='" + perextra2 + "', nominee_name='" + nominee_name + "', nominee_relation='" + nominee_relation + "', nom_addr_1='"+nom_addr_1 +"', nom_addr_2='"+nom_addr_2 +"', nom_addr_state='"+nom_addr_state +"', nom_addr_city='"+nom_addr_city +"', nom_addr_pincode='"+ nom_addr_pincode +"', nominee_email='" + nominee_email+"', past_regulatory_action_details='" +past_regulatory_action_details+ "', aadhar='"+ aadhar +"', eKycLastUpdateTime = from_unixtime(unix_timestamp()), firstname_mother='"+firstname_mother+"', middlename_mother='"+middlename_mother+"', lastname_mother='"+lastname_mother+"' WHERE email_id='" + email_id + "' and mobile_no='" + mobile_no + "'", func)
    }

exports.saveBankDetails = function (email_id, mobile_no, ifsccode, accountnumber, bankname, banknames, address, micr, bank_address_state, bank_address_city, bank_address_pincode, bank_address_contactno, bank_branch, func) {
    console.log('INSIDE EKYC', email_id, mobile_no, ifsccode, accountnumber, bankname, banknames, address, bank_address_state, bank_address_city, bank_address_pincode, bank_address_contactno, bank_branch, micr);
    let options = {
        'method': 'POST',
        'url': digioBankVerify,
        'headers': {
            'Authorization': enviromentKey,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "beneficiary_account_no": accountnumber,
            "beneficiary_ifsc": ifsccode
        })
    };
    request(options, function (error, response) {
        if (error) {
            console.log("Digio bank validate error" + error);
            func(2, 0); // error code 2 from digio service if not work  
        } else {
            try {
                console.log("Digio bank  response" + response.body);
                let objData = JSON.parse(response.body);
                if(objData.verified != null) {
                    console.log('objData.verified != null');
                    let dataToInsert = JSON.stringify(objData);
                    console.log('json_dataToInsert', dataToInsert);
                    if (objData.verified.toString() != "false") {
                        query("UPDATE ekyc SET stage = 3, ifsccode='" + ifsccode + "', accountnumber='" + accountnumber + "', bankname='" + bankname + "', verified_id='" + objData.id + "', beneficiary_name_with_bank='" + objData.beneficiary_name_with_bank + "', verified_at='" + objData.verified_at + "', bankname = '" + banknames + "', bank_address = '" + address + "', micr_no =  '" + micr + "', json_pennydrop_response='" + dataToInsert + "', eKycLastUpdateTime = from_unixtime(unix_timestamp()), bank_address_state='"+ bank_address_state +"', bank_address_city='"+ bank_address_city +"', bank_address_pincode='"+ bank_address_pincode +"', bank_address_contactno='"+ bank_address_contactno +"', bank_branch='"+ bank_branch +"'   WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", func)
                    }
                }
                else {
                    if (objData.error_code != null) {
                        let code = "";
                        let message = "";
                        if (objData.error_code == "es405" || objData.error_code == "405") {
                            code = "es405";
                            message = "Some error message";
                        } else if (objData.error_code == "ns:E402" || objData.error_code == "402") {
                            code = "ns:E402";
                            message = "Insufficient Balance in debit account, payment required";
                        } else if (objData.error_code == "ns:E405" || objData.error_code == "E405") {
                            code = "ns:E405";
                            message = "Invalid Transfer Type";
                        } else if (objData.error_code == "ns:E429" || objData.error_code == "E429") {
                            code = "ns:E429";
                            message = "(Limit Daily/transaction/rate) exceeded";
                        } else if (objData.error_code == "ns:E406" || objData.error_code == "E406") {
                            code = "ns:E406";
                            message = "Beneficiary not acceptable";
                        } else if (objData.error_code == "ns:E502" || objData.error_code == "E502") {
                            code = "E502";
                            message = "Bad Gateway";
                        } else if (objData.error_code == "ns:E1001" || objData.error_code == "E1001") {
                            code = "ns:E1001";
                            message = "The transaction amount exceeds the maximum amount for IMPS";
                        } else if (objData.error_code == "ns:E1002" || objData.error_code == "E1002") {
                            code = "ns:E1002";
                            message = "The transfer currency is not supported. Supported currency is INR";
                        } else if (objData.error_code == "ns:E1003" || objData.error_code == "E1003") {
                            code = "ns:E1003";
                            message = "The transaction amount should be multiples of Re 1 for RTGS";
                        } else if (objData.error_code == "ns:E1004" || objData.error_code == "E1004") {
                            code = "ns:E1004";
                            message = "Transfer Amount is less than the minimum amount for RTGS";
                        } else if (objData.error_code == "ns:E6000" || objData.error_code == "E6000") {
                            code = "ns:E6000";
                            message = "Purpose Code not found";
                        } else if (objData.error_code == "ns:E6001" || objData.error_code == "E6001") {
                            code = "ns:E6001";
                            message = "Only registered beneficiaries are allowed for this purpose code";
                        } else if (objData.error_code == "ns:E6002" || objData.error_code == "E6002") {
                            code = "ns:E6002";
                            message = "Purpose Code is required for this customer";
                        } else if (objData.error_code == "ns:E8000" || objData.error_code == "E8000") {
                            code = "ns:E8000";
                            message = "A transaction with the same reference number is already processed or under processing";
                        } else if (objData.error_code == "ns:E6003" || objData.error_code == "E6003") {
                            code = "ns:E6003";
                            message = "Invalid Debit Account for Customer";
                        } else if (objData.error_code == "flex:E18" || objData.error_code == "E18") {
                            code = "flex:E18";
                            message = "Hold Funds Present - Refer to Drawer ( Account would Overdraw )";
                        } else if (objData.error_code == "flex:E404" || objData.error_code == "E404") {
                            code = "flex:E404";
                            message = "No Relationship Exists with the debit Account {AccountNo} and partner";
                        } else if (objData.error_code == "flex:E8036" || objData.error_code == "E8036") {
                            code = "flex:E8036";
                            message = "NEFT - Both Customer Mobile and Email is not valid.";
                        } else if (objData.error_code == "flex:E8087" || objData.error_code == "E8087") {
                            code = "flex:E8087";
                            message = "To Account Number is Invalid";
                        } else if (objData.error_code == "flex:E9072" || objData.error_code == "E9072") {
                            code = "flex:E9072";
                            message = "Invalid IFSC Code";
                        } else if (objData.error_code == "npci:E08" || objData.error_code == "E08") {
                            code = "npci:E08";
                            message = "Acquiring Bank CBS or node offline";
                        } else if (objData.error_code == "npci:EM1" || objData.error_code == "EM1") {
                            code = "npci:EM1";
                            message = "Invalid Beneficiary MMID/Mobile Number";
                        } else if (objData.error_code == "npci:EM2" || objData.error_code == "EM2") {
                            code = "npci:EM2";
                            message = "Amount limit exceeded";
                        } else if (objData.error_code == "npci:EM3" || objData.error_code == "EM3") {
                            code = "npci:EM3";
                            message = "Account blocked/frozen";
                        } else if (objData.error_code == "npci:EM4" || objData.error_code == "EM4") {
                            code = "npci:EM4";
                            message = "Beneficiary Bank is not enabled for Foreign Inward Remittance";
                        } else if (objData.error_code == "npci:EM5" || objData.error_code == "EM5") {
                            code = "npci:EM5";
                            message = "Account closed";
                        } else if (objData.error_code == "atom:E404" || objData.error_code == "E404") {
                            code = "atom:E404";
                            message = "No Relationship Exists with the debit Account {AccountNo} and partner";
                        } else if (objData.error_code == "sfms:E62" || objData.error_code == "E62") {
                            code = "sfms:E62";
                            message = "Transaction accepted by RBI but beneficiary bank rejected it";
                        } else if (objData.error_code == "sfms:E99" || objData.error_code == "E99") {
                            code = "sfms:E99";
                            message = "Manually Marked in Error";
                        } else if (objData.error_code == "sfms:E70" || objData.error_code == "E70") {
                            code = "sfms:E70";
                            message = "Outward Transaction Rejected";
                        } else if (objData.error_code == "sfms:E18" || objData.error_code == "E18") {
                            code = "sfms:E18";
                            message = "Rejected by SFMS";
                        } else if (objData.error_code == "R000") {
                            code = "R000";
                            message = "Remittance transactions has been successfully processed";
                        } else {
                            code = "0000";
                            message = "error.";
                        }
                        let obj = {
                            "details": objData.details,
                            "code": code,
                            "message": message
                        };
                        console.log('obj', obj);
                        func(2, obj)
                    } else {
                        func(1, response.body)
                    }
                }
            } catch (e) {
                func(2, 0);
            }
        }
    });
}

exports.savePaymentDetails = function (email_id, mobile_no, pack, func) {
    console.log('INSIDE EKYC', email_id, mobile_no, pack);
    query("UPDATE ekyc SET stage = 4, pack='" + pack + "', eKycLastUpdateTime = from_unixtime(unix_timestamp()) WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", func)
}

exports.saveDocumDetails = function (email_id, mobile_no, func) {
    console.log('INSIDE EKYC', email_id, mobile_no);
    query("UPDATE ekyc SET stage = 5, eKycLastUpdateTime = from_unixtime(unix_timestamp()) WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", func)
}

exports.saveIpvDetails = function (email_id, mobile_no, imageName, func) {
    console.log('INSIDE EKYC', email_id, mobile_no, imageName);
    query("UPDATE ekyc SET stage = 6, ipv = '" + imageName + "', eKycLastUpdateTime = from_unixtime(unix_timestamp())  WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", func)
}

exports.saveEsignDetails = function (email_id, mobile_no, esign, func) {
    console.log('INSIDE EKYC', email_id, mobile_no, esign);
    query("UPDATE ekyc SET stage = 7, esign='" + esign + "', eKycLastUpdateTime = from_unixtime(unix_timestamp())  WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", func)
}

exports.saveDocumentDetails = function (email_id, mobile_no, type, imageName, imagePath, prooftype, pan, func) {
    console.log('INSIDE EKYC', email_id, mobile_no, type, imageName, imagePath, pan);
    if (type == "pancard") {
        query("UPDATE ekyc SET  pancard='" + imagePath + "' WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", function (err, rows) {
            if (err == 0) {
                func(0, 0);
                checkAllimageuploaded(email_id, mobile_no);
            } else {
                func(1, 0);
            }
        });
    } else if (type == "signature") {
        query("UPDATE ekyc SET signature='" + imagePath + "' WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", function (err, rows) {

            if (err == 0) {
                func(0, 0);
                checkAllimageuploaded(email_id, mobile_no);
            } else {
                func(1, 0);
            }
        });
    } else if (type == "bankproof") {
        query("UPDATE ekyc SET  bankproof='" + imagePath + "' , bankprooftype='" + prooftype + "' WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", function (err, rows) {

            if (err == 0) {
                func(0, 0);
                checkAllimageuploaded(email_id, mobile_no);
            } else {
                func(1, 0);
            }
        });
    } else if (type == "addressproof") {
        query("UPDATE ekyc SET  addressproof='" + imagePath + "' , addressprooftype='" + prooftype + "' WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", function (err, rows) {

            if (err == 0) {
                func(0, 0);
                checkAllimageuploaded(email_id, mobile_no);
                saveAddressDocumentID(type, email_id, mobile_no, documentPath + pan + "/" + imagePath);
            } else {
                func(1, 0);
            }
        });
    } else if (type == "incomeproof") {
        query("UPDATE ekyc SET  incomeproof='" + imagePath + "' , incomeprooftype='" + prooftype + "' WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", function (err, rows) {
            if (err == 0) {
                func(0, 0);
                checkAllimageuploaded(email_id, mobile_no);
            } else {
                func(1, 0);
            }
        });
    } else if (type == "photograph") {
        query("UPDATE ekyc SET  photograph='" + imagePath + "' WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", function (err, rows) {

            if (err == 0) {
                func(0, 0);
                checkAllimageuploaded(email_id, mobile_no);
            } else {
                func(1, 0);

            }
        });
    } else if (type == "nominee_identity_proof") {
        query("UPDATE ekyc SET  nominee_identity_proof='" + imagePath + "' WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", function (err, rows) {

            if (err == 0) {
                func(0, 0);
            } else {
                func(1, 0);

            }
        });
    }
}

exports.saveAddressDocumentID = saveAddressDocumentID;
function saveAddressDocumentID(type, email_id, mobile_no, imagePath) {
    console.log('INSIDE saveAddressDocumentID', type, email_id, mobile_no, imagePath);
    let options = {
        'method': 'POST',
        'url': idcardupload,
        'headers': {
            'Authorization': enviromentKey,
            'content-type': 'application/json'
        },
        formData: {
            'front_part': {
                'value': fs.createReadStream(imagePath),
                'options': {
                    'filename': imagePath,
                    'contentType': null
                }
            }
        }
    };
    console.log('saveAddressDocumentID options ', options);
    request(options, function (error, response) {
        if (error) {
            throw new Error(error);
        } else {
            console.log(response.body);
            try {
                let objData = JSON.parse(response.body);
                console.log('saveAddressDocumentID objData 3',objData);
                console.log('Document id no- ' + objData.id_no);
                let address_id_no;
                if(objData != null  || objData != undefined) {
                if (objData.id_no == undefined) {
                    address_id_no = "";
                } else {
                    address_id_no = objData.id_no;
                }
                let dataToInsert = JSON.stringify(objData);
                console.log('json_dataToInsert', dataToInsert);
                query("UPDATE ekyc SET address_proof_id = '" + address_id_no + "'  WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "', json_digiosign_response='" + dataToInsert + "'", function (err, rows) {
                    if (err == 0) {
                        console.log(err);
                    } else {
                        console.log(err);
                    }
                });
            }
            } catch (e) {
            }
        }
    });
}

exports.checkAllimageuploaded = checkAllimageuploaded;
function checkAllimageuploaded(email_id, mobile_no) {
    console.log('INSIDE EKYC', email_id, mobile_no);
    query("SELECT pancard,signature,bankproof,addressproof,incomeproof,photograph  FROM ekyc WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "' IS NOT NULL;", function (err, rows) {
        if (err == 0) {
            console.log(rows);
            if (rows[0].pancard != "" && rows[0].signature != "" && rows[0].bankproof != "" && rows[0].addressproof != "" && rows[0].incomeproof != "" && rows[0].photograph != "") {
                console.log("all images stored now update stage");
                query("UPDATE ekyc SET stage = 5, eKycLastUpdateTime = from_unixtime(unix_timestamp())  WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", function (err, rows) {
                    if (err == 0) {
                        console.log(err);
                    } else {
                        console.log(err);
                    }
                });
            } else {
                console.log("Some images still pending");
            }
        } else {
            console.log(rows);
        }
    });
}

exports.getDocuments = function (email_id, mobile_no, type, func) {
    console.log('INSIDE EKYC', email_id, mobile_no, type);
    if (type == "pancard") {
        query("SELECT pancard FROM ekyc WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", func)
    } else if (type == "signature") {
        query("SELECT signature FROM ekyc WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", func)
    } else if (type == "bankproof") {
        query("SELECT bankproof FROM ekyc WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", func)
    } else if (type == "addressproof") {
        query("SELECT addressproof FROM ekyc WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", func)
    } else if (type == "incomeproof") {
        query("SELECT incomeproof FROM ekyc WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", func)
    } else if (type == "photograph") {
        query("SELECT photograph FROM ekyc WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", func)
    } else if (type == "nominee") {
        query("SELECT nominee_identity_proof FROM ekyc WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", func)
    }
}

exports.insertUsersData = function (email_id, mobile_no, full_name, latitude, longitude, func) {
    console.log('INSIDE EKYC', email_id, mobile_no, full_name, "---lat ", latitude, "----long ", longitude);
    query("INSERT INTO ekyc (stage, full_name, email_id, mobile_no, latitude, longitude, eKycStartDate, eKycLastUpdateTime, expiryDate) VALUES ('-1', '" + full_name + "','" + email_id + "'," + mobile_no + "," + latitude + "," + longitude + ", from_unixtime(unix_timestamp()), from_unixtime(unix_timestamp()), DATE_ADD(from_unixtime(unix_timestamp()), INTERVAL 3 MONTH));", func)
}

exports.insertLatLongData = function (email_id, mobile_no, latitude, longitude, func) {
    console.log('insertLatLongData');
    query("UPDATE ekyc SET latitude = '" + latitude + "', longitude = '" + longitude + "' WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", func);
}

function saveEmailOTP(emaild_otp, email_id, mobile_no, is_otp_verified, func) {
    console.log('INSIDE EKYC', emaild_otp, email_id, mobile_no);
    query("UPDATE ekyc SET email_otp = '" + emaild_otp + "' WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", function (err, rows) {
        if (err == 0) {
            func(0, 0);
        } else {
            func(1, 0);
        }
    });
}

exports.sendDatatoCDSLKYC = function(full_name, email_id, mobile_no, pan, panfullname, dob, nationality, gender, firstname1, middlename1, lastname1,
    maritalstatus, fatherspouse, firstname2, middlename2, lastname2, incomerange, occupation, action, res_addr_1, res_addr_2, res_addr_state,
    res_addr_city, res_addr_pincode, parm_addr_1, parm_addr_2, parm_addr_state, parm_addr_city, parm_addr_pincode, nominee_relation, nominee_name, func) {
    let result = crypto.randomBytes(5).toString('hex');
    let args = '<?xml version="1.0" encoding="utf-8"?><soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><GetPassword xmlns="' + cdslUrl + '"><password>' + config.cdslPassword + '</password><PassKey>' + result + '</PassKey></GetPassword></soap:Body></soap:Envelope>';
    request.post
        ({
            "url": cdslUrl + "PANInquiry.asmx",
            "body": args,
            "headers": {
                "SOAPAction": cdslUrl + "GetPassword",
                "Content-Type": "text/xml; charset=utf-8",
            },
        },
            (error, response, body) => {
                if (error) {
                    console.log(error);
                }
                else {
                    console.log(body.toString());
                    parseString(body.toString(), function (err, parsedData) {
                        let cData = JSON.parse(JSON.stringify(parsedData));
                        console.log(cData['soap:Envelope']['soap:Body'][0].GetPasswordResponse[0].GetPasswordResult[0]);
                        let password = cData['soap:Envelope']['soap:Body'][0].GetPasswordResponse[0].GetPasswordResult[0];
                        let d = new Date();
                        let year = d.getFullYear();
                        console.log("Date  Time :" + (d.getMonth() + 1) + " " + d);
                        let month, day;
                        if ((d.getMonth() + 1) / 10 >= 1) {
                            month = (d.getMonth() + 1);
                        }
                        else {
                            month = "0" + (d.getMonth() + 1);
                        }
                        if (d.getDate() / 10 >= 1) {
                            day = d.getDate();
                        }
                        else {
                            day = "0" + d.getDate();
                        }
                        let dateToday = day + "/" + month + "/" + year;
                        let gen;
                        if (gender.includes("Male")) {
                            gen = "M";
                        } else {
                            gen = "F";
                        }

                        let nationalitys;
                        if (nationality.includes("Indian")) {
                            nationalitys = "01";
                        } else {
                            nationalitys = "02";
                        }

                        let occupations;
                        if (occupation.includes("Private Sector")) {
                            occupations = "01";
                        } else if (occupation.includes("Public Sector")) {
                            occupations = "02";
                        } else if (occupation.includes("B-Business")) {
                            occupations = "03";
                        } else if (occupation.includes("Professional")) {
                            occupations = "04";
                        } else if (occupation.includes("Public Sector")) {
                            occupations = "05";
                        } else if (occupation.includes("Retired")) {
                            occupations = "06";
                        } else if (occupation.includes("Housewife")) {
                            occupations = "07";
                        } else if (occupation.includes("Student")) {
                            occupations = "08";
                        } else if (occupation.includes("Government Sector")) {
                            occupations = "10";
                        } else if (occupation.includes("S-Service")) {
                            occupations = "11";
                        } else if (occupation.includes("O-Others")) {
                            occupations = "99";
                        }

                        let resstatcode = getstatcodefromname(res_addr_state);
                        let rescitycode = getcountrycodefromname(nationality);
                        let parmstatcode = getstatcodefromname(parm_addr_state);
                        let parmcitycode = getcountrycodefromname(nationality);

                        let incomeprrof;
                        if (incomerange.includes("Below 1 Lakh")) {
                            incomeprrof = "01";
                        } else if (incomerange.includes("1 Lakh TO 5 Lakh")) {
                            incomeprrof = "02";
                        } else if (incomerange.includes("5 Lakh TO 10 Lakh")) {
                            incomeprrof = "03";
                        } else if (incomerange.includes("10 Lakh TO 25 Lakh")) {
                            incomeprrof = "04";
                        } else if (incomerange.includes("Above 25 Lakh")) {
                            incomeprrof = "05";
                        }

                        let maritalstatusflag;
                        if (maritalstatus.includes("Married")) {
                            maritalstatusflag = "01";
                        } else {
                            maritalstatusflag = "02";
                        }
                        let dobs = dob.replace("-", "/");
                        let dobss = dobs.replace("-", "/");

                        let args = "<ROOT><HEADER><COMPANY_CODE>" + config.cdslPoscode + "</COMPANY_CODE><BATCH_DATE>" + dateToday + "</BATCH_DATE></HEADER><KYCDATA><APP_UPDTFLG>02</APP_UPDTFLG><APP_POS_CODE>" + config.cdslPoscode + "</APP_POS_CODE><APP_TYPE>I</APP_TYPE><APP_NO>00001</APP_NO><APP_DATE>" + dateToday + "</APP_DATE><APP_PAN_NO>" + pan + "</APP_PAN_NO><APP_PAN_COPY>Y</APP_PAN_COPY><APP_EXMT>N</APP_EXMT><APP_EXMT_CAT></APP_EXMT_CAT><APP_EXMT_ID_PROOF>01</APP_EXMT_ID_PROOF><APP_IPV_FLAG>Y</APP_IPV_FLAG><APP_IPV_DATE>" + dateToday + "</APP_IPV_DATE><APP_GEN>" + gen + "</APP_GEN><APP_NAME>" + panfullname + "</APP_NAME><APP_F_NAME>" + firstname2 + " " + lastname2 + "</APP_F_NAME><APP_REGNO></APP_REGNO><APP_DOB_INCORP>" + dobss + "</APP_DOB_INCORP><APP_COMMENCE_DT></APP_COMMENCE_DT><APP_NATIONALITY>" + nationalitys + "</APP_NATIONALITY><APP_OTH_NATIONALITY></APP_OTH_NATIONALITY><APP_COMP_STATUS></APP_COMP_STATUS><APP_OTH_COMP_STATUS></APP_OTH_COMP_STATUS><APP_RES_STATUS>R</APP_RES_STATUS><APP_RES_STATUS_PROOF></APP_RES_STATUS_PROOF><APP_UID_NO></APP_UID_NO><APP_COR_ADD1>" + res_addr_1 + "</APP_COR_ADD1><APP_COR_ADD2>" + res_addr_2 + "</APP_COR_ADD2><APP_COR_ADD3></APP_COR_ADD3><APP_COR_CITY>" + res_addr_city + "</APP_COR_CITY><APP_COR_PINCD>" + res_addr_pincode + "</APP_COR_PINCD><APP_COR_STATE>" + resstatcode + "</APP_COR_STATE><APP_COR_CTRY>" + rescitycode + "</APP_COR_CTRY><APP_OFF_ISD>91</APP_OFF_ISD><APP_OFF_STD>022</APP_OFF_STD><APP_OFF_NO></APP_OFF_NO><APP_RES_ISD>91</APP_RES_ISD><APP_RES_STD>022</APP_RES_STD><APP_RES_NO></APP_RES_NO><APP_MOB_ISD>91</APP_MOB_ISD><APP_MOB_NO>" + mobile_no + "</APP_MOB_NO><APP_FAX_ISD>91</APP_FAX_ISD><APP_FAX_STD>022</APP_FAX_STD><APP_FAX_NO></APP_FAX_NO><APP_EMAIL>" + email_id + "</APP_EMAIL><APP_COR_ADD_PROOF>02</APP_COR_ADD_PROOF><APP_COR_ADD_REF></APP_COR_ADD_REF><APP_COR_ADD_DT>" + dateToday + "</APP_COR_ADD_DT><APP_PER_ADD_FLAG>Y</APP_PER_ADD_FLAG><APP_PER_ADD1>" + parm_addr_1 + "</APP_PER_ADD1><APP_PER_ADD2>" + parm_addr_2 + "</APP_PER_ADD2><APP_PER_ADD3></APP_PER_ADD3><APP_PER_CITY>" + parm_addr_city + "</APP_PER_CITY><APP_PER_PINCD>" + parm_addr_pincode + "</APP_PER_PINCD><APP_PER_STATE>" + parmstatcode + "</APP_PER_STATE><APP_PER_CTRY>" + parmcitycode + "</APP_PER_CTRY><APP_PER_ADD_PROOF>02</APP_PER_ADD_PROOF><APP_PER_ADD_REF></APP_PER_ADD_REF><APP_PER_ADD_DT>" + dateToday + "</APP_PER_ADD_DT><APP_INCOME>" + incomeprrof + "</APP_INCOME><APP_OCC>" + occupations + "</APP_OCC><APP_OTH_OCC></APP_OTH_OCC><APP_POL_CONN>NA</APP_POL_CONN><APP_DOC_PROOF>S</APP_DOC_PROOF><APP_INTERNAL_REF></APP_INTERNAL_REF><APP_BRANCH_CODE>HEADOFFICE</APP_BRANCH_CODE><APP_MAR_STATUS>" + maritalstatusflag + "</APP_MAR_STATUS><APP_NETWRTH></APP_NETWRTH><APP_NETWORTH_DT></APP_NETWORTH_DT><APP_INCORP_PLC></APP_INCORP_PLC><APP_OTHERINFO></APP_OTHERINFO><APP_FILLER1></APP_FILLER1><APP_FILLER2></APP_FILLER2><APP_FILLER3></APP_FILLER3><APP_IPV_DESG>" + occupation + "</APP_IPV_DESG><APP_IPV_ORGAN></APP_IPV_ORGAN><APP_KYC_MODE>0</APP_KYC_MODE><APP_VER_NO>V28</APP_VER_NO><APP_VID_NO></APP_VID_NO><APP_UID_TOKEN></APP_UID_TOKEN></KYCDATA > <FOOTER><NO_OF_KYC_RECORDS>1</NO_OF_KYC_RECORDS><NO_OF_ADDLDATA_RECORDS>0</NO_OF_ADDLDATA_RECORDS></FOOTER></ROOT > ";
                        console.log('Arguments', args);
                        let data = "inputXML=" + args + "&userName=" + config.cdslUsername + "&PosCode=" + config.cdslPoscode + "&password=" + password + "&PassKey=" + result + "";
                        let argsInner = '<?xml version="1.0" encoding="utf-8"?><soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><InsertUpdateKYCRecord xmlns="' + cdslUrl + '"><inputXML>' + args + '</inputXML><userName>' + config.cdslUsername + '</userName><PosCode>' + config.cdslPoscode + '</PosCode><password>' + password + '</password><PassKey>' + result + '</PassKey></InsertUpdateKYCRecord></soap:Body></soap:Envelope>'
                        console.log("*********************** " + data);
                        request.post
                            ({
                                "url": cdslUrl + "/PANInquiry.asmx/InsertUpdateKYCRecord",
                                "body": data,
                                "headers": {
                                    "Content-Type": "application/x-www-form-urlencoded"
                                },
                            },
                                (error, response, body) => {
                                    if (error) {
                                        console.log(error);
                                    }
                                    else {
                                        console.log("*****************************");
                                        parseString(body.toString(), function (err, parsedData1) {
                                            let cData1 = JSON.parse(JSON.stringify(parsedData1));
                                            if (cData1['ROOT'] != undefined) {
                                                console.log("Error code " + cData1['ROOT'].KYCDATA[0].APP_STATUS[0]);
                                                if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00000") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00000")) {
                                                    console.log("KYC UPDATED With KRA");
                                                    func(0, "Success");
                                                }
                                                else {
                                                    if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00001") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00001")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "ADDRESS ON APPLICATION NOT MATCHING WITH THE ADDRESS ");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00001") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00001")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "ADDRESS ON APPLICATION NOT MATCHING WITH THE ADDRESS ");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00002") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00002")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "ADDRESS PROOF SUBMITTED NOT CURRENT");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00003") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00003")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "APPLICANT HAS NOT SIGNED ON THE APPLICATION FORM");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00004") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00004")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "APPLICANT PHOTO MISSING ON THE APPLICATION FORM");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00005") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00005")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "APPLICATION FORM INCOMPLETE");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00006") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00006")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "APPLICATION FORM NOT IN PRESCRIBED FORMAT");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00007") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00007")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "DOC NOT RECEIVED FROM POS");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00008") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00008")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID APPLICATION FORM FORMAT (FORM NOT AS PER AMFI FORMAT");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00009") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00009")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID PAN");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00010") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00010")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "MINOR PAN CARD COPY SUBMITTED");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00011") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00011")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "MOBILE BILL SUBMITTED AS A ADDRESS PROOF");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00012") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00012")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "NAME ON PAN DOES NOT MATCH WITH THE NAME ON APPLICATION FORM");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00013") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00013")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "OTHERS");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00014") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00014")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "PAN CARD COPY NOT LEGIBLE");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00015") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00015")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "PAN CARD COPY NOT SUBMITTED");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00016") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00016")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "POS HAS NOT VERIFIED THE DOCUMENTS WITH THE ORIGINAL");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00017") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00017")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "PROOF OF ADDRESS  NOT SUBMITTED");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00018") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00018")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "PROOF OF ADDRESS  NOT VALID");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00019") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00019")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "PROOF OF ADDRESS NOT IN THE NAME OF APPLICANT");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00020") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00020")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "PROOF OF CORRESPONDENCE ADDRESS NOT PROPER");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00021") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00021")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "PROOF OF IDENTITY NOT AVAILABLE / IMPROPER");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00022") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00022")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "PROOF OF PERMANENT ADDRESS NOT PROPER");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00023") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00023")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "VALIDITY PAGE OF DRIVING LICENSE NOT SUBMITTED");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00024") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00024")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "VALIDITY PAGE OF PASSPORT COPY NOT SUBMITTED");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00025") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00025")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "SIGNATURE DOES NOT MATCH SUPPORTING DOCUMENTS");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-00099") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("00099")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "OLD KYC REJECTED DUE TO KYC ENTERED IN NEW FORMAT");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90001") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90001")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "KYC DATA NOT MATCHING THE FOOTER COUNT CONTROL");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90002") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90002")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "KYC ADDITIONAL DATA NOT MATCHING THE FOOTER COUNT CONTROL");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90003") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90003")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID UPDATE FLAG");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90004") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90004")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID ENTITY TYPE");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90005") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90005")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID EXEMPTION TYPE");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90006") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90006")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID EXEMPTION CATEOGORY");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90007") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90007")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID ID PROOF");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90008") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90008")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID IN-PERSON VERIFICATION FLAG");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90009") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90009")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID IN-PERSON VERIFICATION DATE");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90010") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90010")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID GENDER");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90011") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90011")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "APPLICANT NAME IS MANDATORY & SHOULD BE LESS THAN 100 CHARS");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90012") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90012")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "FATHER / SPOUSE NAME IS MANDATORY & SHOULD BE LESS THAN 100 CHARS");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90013") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90013")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID DATE OF BIRTH / INCORPORATION DATE");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90014") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90014")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID COMMENCEMENT DATE");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90015") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90015")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID NATIONALITY");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90016") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90016")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID NATIONALITY - OTHERS");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90017") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90017")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID COMPANY STATUS");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90018") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90018")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID COMPANY STATUS - OTHERS");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90019") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90019")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID RESIDENCE STATUS");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90020") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90020")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID RESIDENCE PROOF - FOR NON RESIDENTS");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90021") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90021")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID PAN NO");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90022") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90022")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID PAN COPY ATTACHMENT FLAG");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90023") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90023")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "CORRESPONDENCE ADDRESS1 IS MANDATORY");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90024") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90024")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "CORRESPONDENCE CITY IS MANDATORY");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90025") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90025")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "CORRESPONDENCE STATE IS MANDATORY");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90026") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90026")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID CORRESPONDENCE PIN CODE");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90027") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90027")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID CORRESPONDENCE COUNTRY");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90028") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90028")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID EMAIL ADDRESS");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90029") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90029")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID CORRESPONDENCE ADDRESS PROOF");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90030") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90030")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID CORRESPONDENCE ADDRESS PROOF ID");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90031") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90031")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID CORRESPONDENCE ADDRESS PROOF DATE");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90032") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90032")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID PERMANENT ADDRESS FLAG");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90033") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90033")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "REGD / PERMANENT / FOREIGN ADDRESS1 IS MANDATORY");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90034") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90034")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "REGD / PERMANENT / FOREIGN CITY IS MANDATORY");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90035") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90035")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "REGD / PERMANENT / FOREIGN STATE IS MANDATORY");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90036") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90036")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID REGD / PERMANENT / FOREIGN PIN CODE");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90037") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90037")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID REGD / PERMANENT / FOREIGN COUNTRY");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90038") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90038")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID REGD / PERMANENT / FOREIGN ADDRESS PROOF");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90039") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90039")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID REGD / PERMANENT / FOREIGN ADDRESS PROOF ID");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90040") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90040")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID REGD / PERMANENT / FOREIGN ADDRESS PROOF DATE");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90041") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90041")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID INCOME DETAILS");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90042") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90042")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID OCCUPATION DETAILS");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90043") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90043")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID OCCUPATION DETAILS - OTHERS");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90044") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90044")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID POLITICAL CONNECTION INFO");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90045") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90045")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID DOCUMENT PROOF TYPE");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90046") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90046")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID MARITAL STATUS");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90047") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90047")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID INCORPORATION CITY");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90048") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90048")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID NETWORTH DETAIL");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90049") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90049")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID NETWORTH DATE");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90050") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90050")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "EITHER (NETWORTH & DATE) OR INCOME DETAIL IS MANDATORY");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90051") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90051")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "BOTH NETWORTH, NETWORTH DATE AND INCOME DETAIL ARE MANDATORY");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90052") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90052")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID ADDITIONAL DATA UPDATE FLAG");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90053") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90053")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID ADDITIONAL DATA ENTITY PAN");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90054") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90054")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID ADDITIONAL DATA DIRECTOR`S PAN");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90055") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90055")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "DIRECTOR / KARTA / PARTNER NAME IS MANDATORY & SHOULD BE LESS THAN 100 CHARS");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90056") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90056")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "DIRECTOR / KARTA / PARTNER DIN / UID IS MANDATORY & SHOULD BE LESS THAN 20 CHARS");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90057") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90057")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "DIRECTOR / KARTA / PARTNER RELATIONSHIP IS INVALID");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90058") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90058")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "DIRECTOR / KARTA / PARTNER POLITICAL CONNECTION FLAG IS INVALID");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90059") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90059")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "REGISTRATION NO IS MANDATORY & LESS THAN 30 CHARS");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90060") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90060")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID REGISTRATION NO.");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90061") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90061")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "KYC DATA ALREADY EXISTS FOR THE PAN");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90062") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90062")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "COMPANY CODE IN HEADER DOES NOT MATCH YOUR COMPANY CODE");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90063") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90063")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "POS CODE IN DATA DOES NOT MATCH YOUR ELIGIBLE POS CODES");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90064") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90064")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "DIRECTOR / HUF DATA ERROR - ADDITIONAL DATA NOT PROPER");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90065") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90065")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "DUPLICATE PAN NO DATA");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90066") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90066")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID APP DATE");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90067") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90067")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "OFF TEL NO SHOULD BE LESS THAN OR EQUAL TO 20 CHARS");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90068") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90068")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "RES TEL NO SHOULD BE LESS THAN OR EQUAL TO 20 CHARS");
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90069") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90069")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "FAX NO SHOULD BE LESS THAN OR EQUAL TO 20 CHARS")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90070") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90070")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "MOB NO SHOULD BE LESS THAN OR EQUAL TO 10 CHARS")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90071") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90071")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID ACCOUNT SET UP DATE")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90072") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90072")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID ACCOUNT ACTIVATION DATE")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90073") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90073")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID ACCOUNT LAST MODIFICATION DATE")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90074") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90074")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID ACCOUNT SET UP DATE. SHOULD BE LESS THAN `OR` EQUAL TO 31-DEC-2011")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90075") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90075")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "IN CASE OF OLD KYC, PROVIDE EITHER ACCOUNT SETUP DATE `OR` ACTIVATION DATE `OR` LAST UPDATE DATE")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90076") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90076")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "RECORD CANNOT BE MODIFIED - YOU HAVE NOT CREATED / FETCHED THE KYC DATA / EARLIER RECORD IS PENDING VERFICATION AT CVL-KRA")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90077") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90077")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "RECORD CANNOT BE MODIFIED - EARLIER RECORD IS PENDING VERFICATION AT CVL-KRA")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90078") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90078")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID DETAILS GIVEN FOR MODIFICATION (PAN / DOB)")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90079") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90079")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "MF DATA FOR THE PAN NO. NOT AVAILABLE IN THE SYSTEM")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90080") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90080")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "NON-INDIVIDUAL MF DATA CANNOT BE MODIFIED")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90081") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90081")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "REJECTED DUE TO PAN NO. CHANGE")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90082") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90082")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "ENTRY REQUEST DONE WITHIN LAST 15 DAYS")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90083") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90083")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID IN-PERSON VERIFICATION DETAILS")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90084") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90084")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID ADDITIONAL DATA DIRECTOR`S ADDRESS DETAILS")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-90087") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("90087")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID KYC MODE /UID")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-99901") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("99001")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "KYC DATA NOT MATCHING THE FOOTER COUNT CONTROL")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-99902") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("99902")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "KYC ADDITIONAL DATA NOT MATCHING THE FOOTER COUNT CONTROL")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-99903") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("99903")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "INVALID UPDATE FLAG")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-99996") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("99996")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "KYC DATA PRESENT IN OTHER KRA-KARVY")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-99997") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("99997")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "KYC DATA PRESENT IN OTHER KRA-CAMS")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-99998") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("99998")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "KYC DATA PRESENT IN OTHER KRA-DOTEX")
                                                    } else if (cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("ERR-99999") || cData1['ROOT'].KYCDATA[0].APP_STATUS[0].includes("99999")) {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "KYC DATA PRESENT IN OTHER KRA-NDML")
                                                    } else {
                                                        func(cData1['ROOT'].KYCDATA[0].APP_STATUS[0], "Please try again.");
                                                    }
                                                }
                                            }
                                            else {
                                                func(1, "Error Updating KYC");
                                            }
                                        });
                                    }
                                });
                    });
                }
            });
}

function getcountrycodefromname(countryname) {
    let countrcode;
    if (countryname.length != "") {
        if (countryname.includes('Afghanistan')) {
            countrcode = "001"
        } else if (countryname.includes('Aland Islands')) {
            countrcode = "002"
        } else if (countryname.includes('Albania')) {
            countrcode = "003"
        } else if (countryname.includes('Algeria')) {
            countrcode = "004"
        } else if (countryname.includes('American Samoa')) {
            countrcode = "005"
        } else if (countryname.includes('Andorra')) {
            countrcode = "006"
        } else if (countryname.includes('Angola')) {
            countrcode = "007"
        } else if (countryname.includes('Anguilla')) {
            countrcode = "008"
        } else if (countryname.includes('Antarctica')) {
            countrcode = "009"
        } else if (countryname.includes('Antigua And Barbuda')) {
            countrcode = "010"
        } else if (countryname.includes('Argentina')) {
            countrcode = "011"
        } else if (countryname.includes('Armenia')) {
            countrcode = "012"
        } else if (countryname.includes('Aruba')) {
            countrcode = "013"
        } else if (countryname.includes('Australia')) {
            countrcode = "014"
        } else if (countryname.includes('Austria')) {
            countrcode = "015"
        } else if (countryname.includes('Azerbaijan')) {
            countrcode = "016"
        } else if (countryname.includes('Bahamas')) {
            countrcode = "017"
        } else if (countryname.includes('Bahrain')) {
            countrcode = "018"
        } else if (countryname.includes('Bangladesh')) {
            countrcode = "019"
        } else if (countryname.includes('Barbados')) {
            countrcode = "020"
        } else if (countryname.includes('Belarus')) {
            countrcode = "021"
        } else if (countryname.includes('Belgium')) {
            countrcode = "022"
        } else if (countryname.includes('Belize')) {
            countrcode = "023"
        } else if (countryname.includes('Benin')) {
            countrcode = "024"
        } else if (countryname.includes('Bermuda')) {
            countrcode = "025"
        } else if (countryname.includes('Bhutan')) {
            countrcode = "026"
        } else if (countryname.includes('Bolivia')) {
            countrcode = "027"
        } else if (countryname.includes('Bosnia And Herzegovina')) {
            countrcode = "028"
        } else if (countryname.includes('Botswana')) {
            countrcode = "029"
        } else if (countryname.includes('Bouvet Island')) {
            countrcode = "030"
        } else if (countryname.includes('Brazil')) {
            countrcode = "031"
        } else if (countryname.includes('British Indian Ocean Territory')) {
            countrcode = "032"
        } else if (countryname.includes('Brunei Darussalam')) {
            countrcode = "033"
        } else if (countryname.includes('Bulgaria')) {
            countrcode = "034"
        } else if (countryname.includes('Burkina Faso')) {
            countrcode = "035"
        } else if (countryname.includes('Burundi')) {
            countrcode = "036"
        } else if (countryname.includes('Cambodia')) {
            countrcode = "037"
        } else if (countryname.includes('Cameroon')) {
            countrcode = "038"
        } else if (countryname.includes('Canada')) {
            countrcode = "039"
        } else if (countryname.includes('Cape Verde')) {
            countrcode = "040"
        } else if (countryname.includes('Cayman Islands')) {
            countrcode = "041"
        } else if (countryname.includes('Central African Republic')) {
            countrcode = "042"
        } else if (countryname.includes('Chad')) {
            countrcode = "043"
        } else if (countryname.includes('Chile')) {
            countrcode = "044"
        } else if (countryname.includes('China')) {
            countrcode = "045"
        } else if (countryname.includes('Christmas Island')) {
            countrcode = "046"
        } else if (countryname.includes('Cocos (Keeling) Islands')) {
            countrcode = "047"
        } else if (countryname.includes('Colombia')) {
            countrcode = "048"
        } else if (countryname.includes('Comoros')) {
            countrcode = "049"
        } else if (countryname.includes('Congo')) {
            countrcode = "050"
        } else if (countryname.includes('Congo, The Democratic Republic Of The')) {
            countrcode = "051"
        } else if (countryname.includes('Cook Islands')) {
            countrcode = "052"
        } else if (countryname.includes('Costa Rica')) {
            countrcode = "053"
        } else if (countryname.includes('Cote DIvoire')) {
            countrcode = "054"
        } else if (countryname.includes('Croatia')) {
            countrcode = "055"
        } else if (countryname.includes('Cuba')) {
            countrcode = "056"
        } else if (countryname.includes('Cyprus')) {
            countrcode = "057"
        } else if (countryname.includes('Czech Republic')) {
            countrcode = "058"
        } else if (countryname.includes('Denmark')) {
            countrcode = "059"
        } else if (countryname.includes('Djibouti')) {
            countrcode = "060"
        } else if (countryname.includes('Dominica')) {
            countrcode = "061"
        } else if (countryname.includes('Dominican Republic')) {
            countrcode = "062"
        } else if (countryname.includes('Ecuador')) {
            countrcode = "063"
        } else if (countryname.includes('Egypt')) {
            countrcode = "064"
        } else if (countryname.includes('El Salvador')) {
            countrcode = "065"
        } else if (countryname.includes('Equatorial Guinea')) {
            countrcode = "066"
        } else if (countryname.includes('Eritrea')) {
            countrcode = "067"
        } else if (countryname.includes('Estonia')) {
            countrcode = "068"
        } else if (countryname.includes('Ethiopia')) {
            countrcode = "069"
        } else if (countryname.includes('Falkland Islands (Malvinas)')) {
            countrcode = "070"
        } else if (countryname.includes('Faroe Islands')) {
            countrcode = "071"
        } else if (countryname.includes('Fiji')) {
            countrcode = "072"
        } else if (countryname.includes('Finland')) {
            countrcode = "073"
        } else if (countryname.includes('France')) {
            countrcode = "074"
        } else if (countryname.includes('French Guiana')) {
            countrcode = "075"
        } else if (countryname.includes('French Polynesia')) {
            countrcode = "076"
        } else if (countryname.includes('French Southern Territories')) {
            countrcode = "077"
        } else if (countryname.includes('Gabon')) {
            countrcode = "078"
        } else if (countryname.includes('Gambia')) {
            countrcode = "079"
        } else if (countryname.includes('Georgia')) {
            countrcode = "080"
        } else if (countryname.includes('Germany')) {
            countrcode = "081"
        } else if (countryname.includes('Ghana')) {
            countrcode = "082"
        } else if (countryname.includes('Gibraltar')) {
            countrcode = "083"
        } else if (countryname.includes('Greece')) {
            countrcode = "084"
        } else if (countryname.includes('Greenland')) {
            countrcode = "085"
        } else if (countryname.includes('Grenada')) {
            countrcode = "086"
        } else if (countryname.includes('Guadeloupe')) {
            countrcode = "087"
        } else if (countryname.includes('Guam')) {
            countrcode = "088"
        } else if (countryname.includes('Guatemala')) {
            countrcode = "089"
        } else if (countryname.includes('Guernsey')) {
            countrcode = "090"
        } else if (countryname.includes('Guinea')) {
            countrcode = "091"
        } else if (countryname.includes('Guinea-Bissau')) {
            countrcode = "092"
        } else if (countryname.includes('Guyana')) {
            countrcode = "093"
        } else if (countryname.includes('Haiti')) {
            countrcode = "094"
        } else if (countryname.includes('Heard Island And Mcdonald Islands')) {
            countrcode = "095"
        } else if (countryname.includes('Holy See (Vatican City State)')) {
            countrcode = "096"
        } else if (countryname.includes('Honduras')) {
            countrcode = "097"
        } else if (countryname.includes('Hong Kong')) {
            countrcode = "098"
        } else if (countryname.includes('Hungary')) {
            countrcode = "099"
        } else if (countryname.includes('Iceland')) {
            countrcode = "100"
        } else if (countryname.includes('Indian')) {
            countrcode = "101"
        } else if (countryname.includes('Indonesia')) {
            countrcode = "102"
        } else if (countryname.includes('Iran')) {
            countrcode = "103"
        } else if (countryname.includes('Iraq')) {
            countrcode = "104"
        } else if (countryname.includes('Ireland')) {
            countrcode = "105"
        } else if (countryname.includes('Isle Of Man')) {
            countrcode = "106"
        } else if (countryname.includes('Israel')) {
            countrcode = "107"
        } else if (countryname.includes('Italy')) {
            countrcode = "108"
        } else if (countryname.includes('Jamaica')) {
            countrcode = "109"
        } else if (countryname.includes('Japan')) {
            countrcode = "110"
        } else if (countryname.includes('Jersey')) {
            countrcode = "111"
        } else if (countryname.includes('Jordan')) {
            countrcode = "112"
        } else if (countryname.includes('Kazakhstan')) {
            countrcode = "113"
        } else if (countryname.includes('Kenya')) {
            countrcode = "114"
        } else if (countryname.includes('Kiribati')) {
            countrcode = "115"
        } else if (countryname.includes('Korea')) {
            countrcode = "116"
        } else if (countryname.includes('Korea')) {
            countrcode = "117"
        } else if (countryname.includes('Kuwait')) {
            countrcode = "118"
        } else if (countryname.includes('Kyrgyzstan')) {
            countrcode = "119"
        } else if (countryname.includes('Lao')) {
            countrcode = "120"
        } else if (countryname.includes('Latvia')) {
            countrcode = "121"
        } else if (countryname.includes('Lebanon')) {
            countrcode = "122"
        } else if (countryname.includes('Lesotho')) {
            countrcode = "123"
        } else if (countryname.includes('Liberia')) {
            countrcode = "124"
        } else if (countryname.includes('Libyan Arab Jamahiriya')) {
            countrcode = "125"
        } else if (countryname.includes('Liechtenstein')) {
            countrcode = "126"
        } else if (countryname.includes('Lithuania')) {
            countrcode = "127"
        } else if (countryname.includes('Luxembourg')) {
            countrcode = "128"
        } else if (countryname.includes('Macao')) {
            countrcode = "129"
        } else if (countryname.includes('Macedonia')) {
            countrcode = "130"
        } else if (countryname.includes('Madagascar')) {
            countrcode = "131"
        } else if (countryname.includes('Malawi')) {
            countrcode = "132"
        } else if (countryname.includes('Malaysia')) {
            countrcode = "133"
        } else if (countryname.includes('Maldives')) {
            countrcode = "134"
        } else if (countryname.includes('Mali')) {
            countrcode = "135"
        } else if (countryname.includes('Malta')) {
            countrcode = "136"
        } else if (countryname.includes('Marshall Islands')) {
            countrcode = "137"
        } else if (countryname.includes('Martinique')) {
            countrcode = "138"
        } else if (countryname.includes('Mauritania')) {
            countrcode = "139"
        } else if (countryname.includes('Mauritius')) {
            countrcode = "140"
        } else if (countryname.includes('Mayotte')) {
            countrcode = "141"
        } else if (countryname.includes('Mexico')) {
            countrcode = "142"
        } else if (countryname.includes('Micronesia')) {
            countrcode = "143"
        } else if (countryname.includes('Moldova')) {
            countrcode = "144"
        } else if (countryname.includes('Monaco')) {
            countrcode = "145"
        } else if (countryname.includes('Mongolia')) {
            countrcode = "146"
        } else if (countryname.includes('Montserrat')) {
            countrcode = "147"
        } else if (countryname.includes('Morocco')) {
            countrcode = "148"
        } else if (countryname.includes('Mozambique')) {
            countrcode = "149"
        } else if (countryname.includes('Myanmar')) {
            countrcode = "150"
        } else if (countryname.includes('Namibia')) {
            countrcode = "151"
        } else if (countryname.includes('Nauru')) {
            countrcode = "152"
        } else if (countryname.includes('Nepal')) {
            countrcode = "153"
        } else if (countryname.includes('Netherlands')) {
            countrcode = "154"
        } else if (countryname.includes('Netherlands Antilles')) {
            countrcode = "155"
        } else if (countryname.includes('New Caledonia')) {
            countrcode = "156"
        } else if (countryname.includes('New Zealand')) {
            countrcode = "157"
        } else if (countryname.includes('Nicaragua')) {
            countrcode = "158"
        } else if (countryname.includes('Niger')) {
            countrcode = "159"
        } else if (countryname.includes('Nigeria')) {
            countrcode = "160"
        } else if (countryname.includes('Niue')) {
            countrcode = "161"
        } else if (countryname.includes('Norfolk Island')) {
            countrcode = "162"
        } else if (countryname.includes('Northern Mariana Islands')) {
            countrcode = "163"
        } else if (countryname.includes('Norway')) {
            countrcode = "164"
        } else if (countryname.includes('Oman')) {
            countrcode = "165"
        } else if (countryname.includes('Pakistan')) {
            countrcode = "166"
        } else if (countryname.includes('Palau')) {
            countrcode = "167"
        } else if (countryname.includes('Palestinian Territory, Occupied')) {
            countrcode = "168"
        } else if (countryname.includes('Panama')) {
            countrcode = "169"
        } else if (countryname.includes('Papua New Guinea')) {
            countrcode = "170"
        } else if (countryname.includes('Paraguay')) {
            countrcode = "171"
        } else if (countryname.includes('Peru')) {
            countrcode = "172"
        } else if (countryname.includes('Philippines')) {countrcode = "173"
        } else if (countryname.includes('Pitcairn')) {
            countrcode = "174"
        } else if (countryname.includes('Poland')) {
            countrcode = "175"
        } else if (countryname.includes('Portugal')) {
            countrcode = "176"
        } else if (countryname.includes('Puerto Rico')) {
            countrcode = "177"
        } else if (countryname.includes('Qatar')) {
            countrcode = "178"
        } else if (countryname.includes('Reunion')) {
            countrcode = "179"
        } else if (countryname.includes('Romania')) {
            countrcode = "180"
        } else if (countryname.includes('Russian Federation')) {
            countrcode = "181"
        } else if (countryname.includes('Rwanda')) {
            countrcode = "182"
        } else if (countryname.includes('Saint Helena')) {
            countrcode = "183"
        } else if (countryname.includes('Saint Kitts And Nevis')) {
            countrcode = "184"
        } else if (countryname.includes('Saint Lucia')) {
            countrcode = "185"
        } else if (countryname.includes('Saint Pierre And Miquelon')) {
            countrcode = "186"
        } else if (countryname.includes('Saint Vincent And The Grenadines')) {
            countrcode = "187"
        } else if (countryname.includes('Samoa')) {
            countrcode = "188"
        } else if (countryname.includes('San Marino')) {
            countrcode = "189"
        } else if (countryname.includes('Sao Tome And Principe')) {
            countrcode = "190"
        } else if (countryname.includes('Saudi Arabia')) {
            countrcode = "191"
        } else if (countryname.includes('Senegal')) {
            countrcode = "192"
        } else if (countryname.includes('Serbia And Montenegro')) {
            countrcode = "193"
        } else if (countryname.includes('Seychelles')) {
            countrcode = "194"
        } else if (countryname.includes('Sierra Leone')) {
            countrcode = "195"
        } else if (countryname.includes('Singapore')) {
            countrcode = "196"
        } else if (countryname.includes('Slovakia')) {
            countrcode = "197"
        } else if (countryname.includes('Slovenia')) {
            countrcode = "198"
        } else if (countryname.includes('Solomon Islands')) {
            countrcode = "199"
        } else if (countryname.includes('Somalia')) {
            countrcode = "200"
        } else if (countryname.includes('South Africa')) {
            countrcode = "201"
        } else if (countryname.includes('South Georgia And The South Sandwich Islands')) {
            countrcode = "202"
        } else if (countryname.includes('Spain')) {
            countrcode = "203"
        } else if (countryname.includes('Sri Lanka')) {
            countrcode = "204"
        } else if (countryname.includes('Sudan')) {
            countrcode = "205"
        } else if (countryname.includes('Suriname')) {
            countrcode = "206"
        } else if (countryname.includes('Svalbard And Jan Mayen')) {
            countrcode = "207"
        } else if (countryname.includes('Swaziland')) {
            countrcode = "208"
        } else if (countryname.includes('Sweden')) {
            countrcode = "209"
        } else if (countryname.includes('Switzerland')) {
            countrcode = "210"
        } else if (countryname.includes('Syrian Arab Republic')) {
            countrcode = "211"
        } else if (countryname.includes('Taiwan, Province Of China')) {
            countrcode = "212"
        } else if (countryname.includes('Tajikistan')) {
            countrcode = "213"
        } else if (countryname.includes('Tanzania, United Republic Of')) {
            countrcode = "214"
        } else if (countryname.includes('Thailand')) {
            countrcode = "215"
        } else if (countryname.includes('Timor-Leste')) {
            countrcode = "216"
        } else if (countryname.includes('Togo')) {
            countrcode = "217"
        } else if (countryname.includes('Tokelau')) {
            countrcode = "218"
        } else if (countryname.includes('Tonga')) {
            countrcode = "219"
        } else if (countryname.includes('Trinidad And Tobago')) {
            countrcode = "220"
        } else if (countryname.includes('Tunisia')) {
            countrcode = "221"
        } else if (countryname.includes('Turkey')) {
            countrcode = "222"
        } else if (countryname.includes('Turkmenistan')) {
            countrcode = "223"
        } else if (countryname.includes('Turks And Caicos Islands')) {
            countrcode = "224"
        } else if (countryname.includes('Tuvalu')) {
            countrcode = "225"
        } else if (countryname.includes('Uganda')) {
            countrcode = "226"
        } else if (countryname.includes('Ukraine')) {
            countrcode = "227"
        } else if (countryname.includes('United Arab Emirates')) {
            countrcode = "228"
        } else if (countryname.includes('United Kingdom')) {
            countrcode = "219"
        } else if (countryname.includes('United States')) {
            countrcode = "230"
        } else if (countryname.includes('United States Minor Outlying Islands')) {
            countrcode = "231"
        } else if (countryname.includes('Uruguay')) {
            countrcode = "232"
        } else if (countryname.includes('Uzbekistan')) {
            countrcode = "233"
        } else if (countryname.includes('Vanuatu')) {
            countrcode = "234"
        } else if (countryname.includes('Venezuela')) {
            countrcode = "235"
        } else if (countryname.includes('Viet Nam')) {
            countrcode = "236"
        } else if (countryname.includes('Virgin Islands, British')) {
            countrcode = "237"
        } else if (countryname.includes('Virgin Islands')) {
            countrcode = "238"
        } else if (countryname.includes('Wallis And Futuna')) {
            countrcode = "239"
        } else if (countryname.includes('Western Sahara')) {
            countrcode = "240"
        } else if (countryname.includes('Yemen')) {
            countrcode = "241"
        } else if (countryname.includes('Zambia')) {
            countrcode = "242"
        } else if (countryname.includes('Zimbabwe')) {
            countrcode = "243"
        } else if (countryname.includes('Curacao')) {
            countrcode = "245"
        } else {
            countrcode = "000"
        }
    }
    return countrcode;
}

function getstatcodefromname(statcodename) {
    let statcode;
    if (statcodename.length != "") {
        if (statcodename.includes('Jammu and Kashmir')) {
            statcode = "001"
        } else if (statcodename.includes('Himachal Pradesh')) {
            statcode = "002"
        } else if (statcodename.includes('Punjab')) {
            statcode = "003"
        } else if (statcodename.includes('Chandigarh')) {
            statcode = "004"
        } else if (statcodename.includes('Uttarakhand')) {
            statcode = "005"
        } else if (statcodename.includes('Haryana')) {
            statcode = "006"
        } else if (statcodename.includes('Delhi')) {
            statcode = "007"
        } else if (statcodename.includes('Rajasthan')) {
            statcode = "008"
        } else if (statcodename.includes('Uttar Pradesh')) {
            statcode = "009"
        } else if (statcodename.includes('Bihar')) {
            statcode = "010"
        } else if (statcodename.includes('Sikkim')) {
            statcode = "011"
        } else if (statcodename.includes('Arunachal Pradesh')) {
            statcode = "012"
        } else if (statcodename.includes('Assam')) {
            statcode = "013"
        } else if (statcodename.includes('Manipur')) {
            statcode = "014"
        } else if (statcodename.includes('Mizoram')) {
            statcode = "015"
        } else if (statcodename.includes('Tripura')) {
            statcode = "016"
        } else if (statcodename.includes('Meghalaya')) {
            statcode = "017"
        } else if (statcodename.includes('Nagaland')) {
            statcode = "018"
        } else if (statcodename.includes('West Bengal')) {
            statcode = "019"
        } else if (statcodename.includes('Jharkhand')) {
            statcode = "020"
        } else if (statcodename.includes('Orissa')) {
            statcode = "021"
        } else if (statcodename.includes('Chhattisgarh')) {
            statcode = "022"
        } else if (statcodename.includes('Madhya Pradesh')) {
            statcode = "023"
        } else if (statcodename.includes('Gujarat')) {
            statcode = "024"
        } else if (statcodename.includes('Daman and Diu')) {
            statcode = "025"
        } else if (statcodename.includes('Dadra and Nagar Haveli')) {
            statcode = "026"
        } else if (statcodename.includes('Maharashtra')) {
            statcode = "027"
        } else if (statcodename.includes('Andhra Pradesh')) {
            statcode = "028"
        } else if (statcodename.includes('Karnataka')) {
            statcode = "029"
        } else if (statcodename.includes('Goa')) {
            statcode = "030"
        } else if (statcodename.includes('Lakshadweep')) {
            statcode = "031"
        } else if (statcodename.includes('Kerala')) {
            statcode = "032"
        } else if (statcodename.includes('Tamil Nadu')) {
            statcode = "033"
        } else if (statcodename.includes('Puducherry')) {
            statcode = "034"
        } else if (statcodename.includes('Andaman and Nicobar Islands')) {
            statcode = "035"
        } else if (statcodename.includes('APO')) {
            statcode = "036"
        } else if (statcodename.includes('Telangana')) {
            statcode = "037"
        } else {
            statcode = "000"
        }
    }
    return statcode;
}

function saveMobileOTP(mobile_otp, email_id, mobile_no, is_otp_verified, func) {
    console.log('INSIDE EKYC', mobile_otp, email_id, mobile_no);
    query("UPDATE ekyc SET mobile_otp = '" + mobile_otp + "' WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", function (err, rows) {
        if (err == 0) {
            func(0, 0);
        } else {
            func(1, 0);
        }
    });
}

exports.sendEmailOtp = function (email, mobile_no, is_otp_verified, func) {
    console.log('INSIDE EKYC', email, mobile_no);
    let isSecureSMTP;
    if (config.isSecureSMTP == "true") {
        isSecureSMTP = true;
    } else {
        isSecureSMTP = false;
    }

    let transporter = nodemailer.createTransport(smtpTransport({
        host: config.emailHost,
        port: config.emailPort,
        secure: isSecureSMTP,
        requireTLS: true,
        auth: {
            user: config.emailId,
            pass: config.emailPass
        },
        tls: {
            rejectUnauthorized: false
        }
    }));

    let pass = "";
    let mailText;
    let maxBytes = 2;
    let maxDec = 65536;
    let mimimum = 100000;
    let maximum = 999999;

    let randombytes = parseInt(crypto.randomBytes(maxBytes).toString('hex'), 16);
    let result = Math.floor(randombytes / maxDec * (maximum - mimimum + 1) + mimimum);
    pass = result;

    mailText = "Dear Sir/Madam,\n\nYour One Time Password is  " + pass + ". Please use the password to complete the Process. Please do not share this with any one.\n\nRegards,\nSupport Team"

    let mailOption = {
        from: config.emailId,
        to: email,
        subject: "One Time Password",
        text: mailText
    };

    transporter.sendMail(mailOption, function (err, info) {
        if (err) {
            console.log("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            console.log(err);
            console.log("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            func(1, 0);
        }
        else {
            console.log("----------------------------------------------------------------------------------");
            console.log("Message Sent" + info.response);
            console.log("----------------------------------------------------------------------------------");
            console.log(err);
            saveEmailOTP(result, email, mobile_no, is_otp_verified, function (err, rows) {
                func(0, pass);
            });
        }
    });
}

exports.sendMobileOtp = function (email, mobile_no, is_otp_verified, func) {
    console.log('INSIDE EKYC');

    let maxBytes = 2;
    let maxDec = 65536;
    let mimimum = 100000;
    let maximum = 999999;
    let randombytes = parseInt(crypto.randomBytes(maxBytes).toString('hex'), 16);
    let result = Math.floor(randombytes / maxDec * (maximum - mimimum + 1) + mimimum);
    let msgData = "Greetings Dear Client!! Thank You for initiating your account opening with Vishwas Fincap Services Pvt. Ltd. . Your OTP is  " + result + ". This is valid for 10 mins only.";
    let client = new Client();
    let vishwasSmsIsSecure;
    if (config.vishwasSmsIsSecure == "true") {
        vishwasSmsIsSecure = "https"
    } else {
        vishwasSmsIsSecure = "http"
    }
    console.log("sms Url :- " + vishwasSmsIsSecure + "://apps.vibgyortel.in/client/api/sendmessage?apikey=02fac732fa750cd6&mobiles=" + mobile_no + "&sms=" + msgData + "&senderid=VishEq&schedule=No&unicode=no&messagetype=sms");
    client.get("" + vishwasSmsIsSecure + "://apps.vibgyortel.in/client/api/sendmessage?apikey=02fac732fa750cd6&mobiles=" + mobile_no + "&sms=" + msgData + "&senderid=VishEq&schedule=No&unicode=no&messagetype=sms", function (data, response) {
        saveMobileOTP(result, email, mobile_no, is_otp_verified, function (err, rows) {
            func(0, result);
            console.log("Response From Send SMS :" + response);
        });
    })
        .on('error', function (err) {
            console.log("Something went wrong on the request : " + err);
            func(1, 0);
        });

    client.on('error', function (err) {
        console.log("Something went wrong on the Client : " + err);
        func(1, 0);
    });
}

exports.readIndexFile = function (indexname, filepath, func) {
    let stream = fs.createReadStream(filepath);
    let myArray = [];
    let _date = parseInt(Date.now() / 1000);
    let csvstream = csv
        .parse()
        .on("data", (data) => {
            let sData = '';
            sData = [data[0], data[1], data[2], data[3], data[4], indexname, _date.toString()];
            console.log(sData);
            myArray.push(sData);
        })
        .on("end", function () {
            myArray.shift();

            let _query = "INSERT INTO IndexScripData(cCompanyName,cIndustry,cSymbol,cSeries,cISINCode,datakey,last_updated_time) 					    Values ?";
            bulkquery(_query, myArray, function (err, rows) {
                func(err);
            });
        });
    stream.pipe(csvstream);
}

exports.insertImage = function (email_id, mobile_no, pancardpath, func) {
    querymaster("UPDATE ekyc SET stage = 5, pancard='" + pancardpath + "' WHERE email_id = '" + email_id + "' and mobile_no = '" + mobile_no + "';", func)
}

function validatePanOnLogin(panNo, dob, email_id, mobile_no, func) {
    let result = crypto.randomBytes(5).toString('hex');
    console.log("RESULT : " + result);
    let statusCode = "", errorMsg = "";
    let args = '<?xml version="1.0" encoding="utf-8"?><soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><GetPassword xmlns="' + cdslUrl + '"><password>' + config.cdslPassword + '</password><PassKey>' + result + '</PassKey></GetPassword></soap:Body></soap:Envelope>';

    request.post
        ({
            "url": cdslUrl + "PANInquiry.asmx",
            "body": args,
            "headers": {
                "SOAPAction": cdslUrl + "GetPassword",
                "Content-Type": "text/xml; charset=utf-8",
            },
        },
            (error, response, body) => {
                if (error) {
                    console.log(error);
                }
                else {
                    console.log(body.toString());
                    parseString(body.toString(), function (err, parsedData) {
                        let cData = JSON.parse(JSON.stringify(parsedData));
                        console.log(cData['soap:Envelope']['soap:Body'][0].GetPasswordResponse[0].GetPasswordResult[0]);
                        let password = cData['soap:Envelope']['soap:Body'][0].GetPasswordResponse[0].GetPasswordResult[0];
                        let argsInner = '<?xml version="1.0" encoding="utf-8"?><soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body><GetPanStatus xmlns="' + cdslUrl + '"><panNo>' + panNo + '</panNo><userName>' + config.cdslUsername + '</userName><PosCode>' + config.cdslPoscode + '</PosCode><password>' + password + '</password><PassKey>' + result + '</PassKey></GetPanStatus></soap:Body></soap:Envelope>'
                        request.post({
                            "url": cdslUrl + "PANInquiry.asmx",
                            "body": argsInner,
                            "headers": {
                                "SOAPAction": cdslUrl + "GetPanStatus",
                                "Content-Type": "text/xml; charset=utf-8",
                            },
                        }, (error, response, body) => {
                            console.log("*****************************");
                            console.log(body.toString());
                            parseString(body.toString(), function (err, parsedData) {
                                let cData = JSON.parse(JSON.stringify(parsedData));
                                console.log(cData['soap:Envelope']['soap:Body'][0].GetPanStatusResponse[0].GetPanStatusResult[0].APP_RES_ROOT[0].APP_PAN_INQ[0].APP_PAN_NO[0].toString());
                                let statusCode = parseInt(cData['soap:Envelope']['soap:Body'][0].GetPanStatusResponse[0].GetPanStatusResult[0].APP_RES_ROOT[0].APP_PAN_INQ[0].APP_STATUS
                                [0].toString());
                                console.log("@@  :: statusCode :: @@ " + statusCode);
                                let agency = "";
                                if (parseInt(statusCode / 100) == 0) {
                                    agency = "CVLKRA";
                                }
                                else if (parseInt(statusCode / 100) == 1) {
                                    agency = "NDML";
                                }
                                else if (parseInt(statusCode / 100) == 2) {
                                    agency = "DOTEX";
                                }
                                else if (parseInt(statusCode / 100) == 3) {
                                    agency = "CAMS";
                                }
                                else if (parseInt(statusCode / 100) == 4) {
                                    agency = "KARVY";
                                }

                                console.log("@@ ::   agency  :: @@ " + agency);
                                if (statusCode % 100 == 1) {
                                    errorMsg = "Submitted to " + agency;
                                }
                                else if (statusCode % 100 == 2) {
                                    errorMsg = "KRA Verified by " + agency;
                                }
                                else if (statusCode % 100 == 3) {
                                    errorMsg = "On Hold by " + agency;
                                }
                                else if (statusCode % 100 == 4) {
                                    errorMsg = "Rejected by " + agency;
                                }
                                else if (statusCode % 100 == 5) {
                                    errorMsg = "Not available at " + agency;
                                }
                                else if (statusCode % 100 == 6) {
                                    errorMsg = "Deactivated by " + agency;
                                }
                                else if (statusCode % 100 == 11) {
                                    errorMsg = "Existing KYC Submitted to " + agency;
                                }
                                else if (statusCode % 100 == 12) {
                                    errorMsg = "Existing KYC Verified by " + agency;
                                }
                                else if (statusCode % 100 == 13) {
                                    errorMsg = "Existing KYC hold by " + agency;
                                }
                                else if (statusCode % 100 == 14) {
                                    errorMsg = "Existing KYC Rejected by " + agency;
                                }
                                else if (statusCode == "022") {
                                    errorMsg = "KYC REGISTERED WITH CVLMF";
                                }
                                else if (statusCode == "888") {
                                    errorMsg = "KYC REGISTERED WITH CVLMF";
                                }
                                else if (statusCode == "999") {
                                    errorMsg = "Invalid PAN NO Format";
                                }
                                console.log("@@ ::   errorMsg  :: @@ " + errorMsg);
                                if (statusCode % 100 == 2) {

                                    getPanDetails_CVLKRA(panNo, dob, password, result, agency, email_id, mobile_no, func)
                                }
                                else {
                                    func(1, "No data found");
                                }

                            });
                        });
                    });

                }
            });
}

function getPanDetails_CVLKRA(panNo, dob, password, result, agency, email_id, mobile_no, func) {
    let args = '<APP_REQ_ROOT> <APP_PAN_INQ> <APP_PAN_NO>' + panNo + '</APP_PAN_NO> <APP_DOB_INCORP>' + dob + '</APP_DOB_INCORP> <APP_POS_CODE>' + config.cdslPoscode + '</APP_POS_CODE> <APP_RTA_CODE>' + config.cdslPoscode + '</APP_RTA_CODE> <APP_KRA_CODE>' + agency + '</APP_KRA_CODE> <FETCH_TYPE>I</FETCH_TYPE> </APP_PAN_INQ> </APP_REQ_ROOT>'
    let url = cdslUrl + "PANInquiry.asmx/SolicitPANDetailsFetchALLKRA?inputXML=" + args + "&userName=" + config.cdslUsername + "&PosCode=" + config.cdslPoscode + "&password=" + password + "&PassKey=" + result;
    console.log(args);
    console.log('================');
    console.log(url);
    if (url.includes("https")) {
        https.get(url, (resp) => {
            let data = '';
            resp.on('data', (chunk) => {
                data += chunk;
            });
            resp.on('end', () => {
                console.log(data);
                parseString(data.toString(), function (err, parsedData1) {
                    let cData1 = JSON.parse(JSON.stringify(parsedData1));

                    if (cData1['ROOT'] != undefined) {

                        if (cData1['ROOT'].ERROR != undefined) {
                            console.log(cData1['ROOT'].ERROR[0].ERROR_MSG[0]);
                        }
                        else {
                            try {
                                console.log("@@@@ 1 " + cData1['ROOT'].KYC_DATA[0].APP_DOB_DT);
                                console.log("@@@@ 1 " + cData1['ROOT'].KYC_DATA[0].APP_NAME);
                                console.log("@@@@ 1 " + cData1['ROOT'].KYC_DATA[0].APP_GEN);

                                saveCdslPanData(cData1['ROOT'].KYC_DATA[0].APP_DOB_DT,
                                    cData1['ROOT'].KYC_DATA[0].APP_NAME,
                                    cData1['ROOT'].KYC_DATA[0].APP_GEN,
                                    cData1['ROOT'].KYC_DATA[0].APP_F_NAME,
                                    cData1['ROOT'].KYC_DATA[0].APP_NATIONALITY,
                                    cData1['ROOT'].KYC_DATA[0].APP_RES_STATUS,
                                    cData1['ROOT'].KYC_DATA[0].APP_UID_NO,
                                    cData1['ROOT'].KYC_DATA[0].APP_COR_ADD1,
                                    cData1['ROOT'].KYC_DATA[0].APP_COR_ADD2,
                                    cData1['ROOT'].KYC_DATA[0].APP_COR_ADD3,
                                    cData1['ROOT'].KYC_DATA[0].APP_COR_CITY,
                                    cData1['ROOT'].KYC_DATA[0].APP_COR_PINCD,
                                    cData1['ROOT'].KYC_DATA[0].APP_COR_STATE,
                                    cData1['ROOT'].KYC_DATA[0].APP_COR_CTRY,
                                    cData1['ROOT'].KYC_DATA[0].APP_OFF_NO,
                                    cData1['ROOT'].KYC_DATA[0].APP_RES_NO,
                                    cData1['ROOT'].KYC_DATA[0].APP_MOB_NO,
                                    cData1['ROOT'].KYC_DATA[0].APP_FAX_NO,
                                    cData1['ROOT'].KYC_DATA[0].APP_EMAIL,
                                    cData1['ROOT'].KYC_DATA[0].APP_PER_ADD1,
                                    cData1['ROOT'].KYC_DATA[0].APP_PER_ADD2,
                                    cData1['ROOT'].KYC_DATA[0].APP_PER_ADD3,
                                    cData1['ROOT'].KYC_DATA[0].APP_PER_CITY,
                                    cData1['ROOT'].KYC_DATA[0].APP_PER_PINCD,
                                    cData1['ROOT'].KYC_DATA[0].APP_PER_STATE,
                                    cData1['ROOT'].KYC_DATA[0].APP_PER_CTRY,
                                    cData1['ROOT'].KYC_DATA[0].APP_COR_ADD_REF,
                                    cData1['ROOT'].KYC_DATA[0].APP_PER_ADD_REF, email_id, mobile_no, func)
                            } catch (err) {
                                func(1, "No data found");
                            }
                        }
                    }
                    else {
                        if (cData1['APP_REQ_ROOT'] != undefined) {
                        }
                    }
                });
            });
        }).on("error", (err) => {
            console.log("Error: " + err.message);
        });
    }
    else {
        http.get(url, (resp) => {
            let data = '';
            resp.on('data', (chunk) => {
                data += chunk;
            });
            resp.on('end', () => {
                console.log(data);
                parseString(data.toString(), function (err, parsedData1) {
                    let cData1 = JSON.parse(JSON.stringify(parsedData1));

                    if (cData1['ROOT'] != undefined) {
                        console.log("@@@@ 1 " + cData1['ROOT'].KYC_DATA[0].APP_DOB_DT);
                        console.log("@@@@ 1 " + cData1['ROOT'].KYC_DATA[0].APP_NAME);
                        console.log("@@@@ 1 " + cData1['ROOT'].KYC_DATA[0].APP_GEN);
                        func(0, cData1['ROOT'].KYC_DATA[0].APP_DOB_DT, cData1['ROOT'].KYC_DATA[0].APP_NAME, cData1['ROOT'].KYC_DATA[0].APP_GEN, cData1['ROOT'].KYC_DATA[0].APP_F_NAME, cData1['ROOT'].KYC_DATA[0].APP_NATIONALITY, cData1['ROOT'].KYC_DATA[0].APP_RES_STATUS, cData1['ROOT'].KYC_DATA[0].APP_UID_NO, cData1['ROOT'].KYC_DATA[0].APP_COR_ADD1, cData1['ROOT'].KYC_DATA[0].APP_COR_ADD2, cData1['ROOT'].KYC_DATA[0].APP_COR_ADD3, cData1['ROOT'].KYC_DATA[0].APP_COR_CITY, cData1['ROOT'].KYC_DATA[0].APP_COR_PINCD, cData1['ROOT'].KYC_DATA[0].APP_COR_STATE, cData1['ROOT'].KYC_DATA[0].APP_COR_CTRY, cData1['ROOT'].KYC_DATA[0].APP_OFF_NO, cData1['ROOT'].KYC_DATA[0].APP_RES_NO, cData1['ROOT'].KYC_DATA[0].APP_MOB_NO, cData1['ROOT'].KYC_DATA[0].APP_FAX_NO, cData1['ROOT'].KYC_DATA[0].APP_EMAIL, cData1['ROOT'].KYC_DATA[0].APP_PER_ADD1, cData1['ROOT'].KYC_DATA[0].APP_PER_ADD2, cData1['ROOT'].KYC_DATA[0].APP_PER_ADD3, cData1['ROOT'].KYC_DATA[0].APP_PER_CITY, cData1['ROOT'].KYC_DATA[0].APP_PER_PINCD, cData1['ROOT'].KYC_DATA[0].APP_PER_STATE, cData1['ROOT'].KYC_DATA[0].APP_PER_CTRY, cData1['ROOT'].KYC_DATA[0].APP_COR_ADD_REF, cData1['ROOT'].KYC_DATA[0].APP_PER_ADD_REF);
                    }
                    else {
                        console.log("Error : " + cData1['APP_REQ_ROOT']);
                    }
                });
            });

        }).on("error", (err) => {
            console.log("Error: " + err.message);
        });
    }
}

exports.responseBankDetailsObject = function (err, status, details, code, message) {
    let obj = {
        "response": {
            "error_code": err,
            "data": {
                "status": status,
                "details": details,
                "code": code,
                "message": message
            }
        }
    };
    return obj;
}

exports.responseUserDetailsModelObject = function (err, message) {
    let obj;
    if (err != 0) {
        obj = {
            response: {
                error_code: err,
                data: {
                    message: [
                        {
                            unique_id: "",
                            stage: "",
                            full_name: "",
                            email_id: "",
                            mobile_no: "",
                            client_code: "",
                            mobile_otp: "",
                            email_otp: "",
                            is_otp_verified: "",
                            lextra1: "",
                            lextra2: "",
                            pan: "",
                            panfullname: "",
                            dob: "",
                            pextra1: "",
                            pextra2: "",
                            nse_cash: "",
                            nse_fo: "",
                            nse_currency: "",
                            mcx_commodty: "",
                            bse_cash: "",
                            bse_fo: "",
                            bse_currency: "",
                            ncdex_commodty: "",
                            res_addr_1: "",
                            res_addr_2: "",
                            res_addr_state: "",
                            res_addr_city: "",
                            res_addr_pincode: "",
                            parm_addr_1: "",
                            parm_addr_2: "",
                            parm_addr_state: "",
                            parm_addr_city: "",
                            parm_addr_pincode: "",
                            nationality: "",
                            gender: "",
                            firstname1: "",
                            middlename1: "",
                            lastname1: "",
                            maritalstatus: "",
                            fatherspouse: "",
                            firstname2: "",
                            middlename2: "",
                            lastname2: "",
                            incomerange: "",
                            occupation: "",
                            action: "",
                            perextra1: "",
                            perextra2: "",
                            ifsccode: "",
                            accountnumber: "",
                            bankname: "",
                            verified_id: "",
                            beneficiary_name_with_bank: "",
                            verified_at: "",
                            bextra1: "",
                            bextra2: "",
                            pack: "",
                            payextra1: "",
                            payextra2: "",
                            pancard: "",
                            signature: "",
                            bankproof: "",
                            bankprooftype: "",
                            addressproof: "",
                            addressprooftype: "",
                            incomeproof: "",
                            incomeprooftype: "",
                            photograph: "",
                            uextra1: "",
                            uextra2: "",
                            ipv: "",
                            iextra1: "",
                            iextra2: "",
                            esign: "",
                            esignaddhar: "",
                            ekycdocument: "",
                            latitude: "",
                            longitude: "",
                            bank_address: "",
                            micr_no: "",
                            bank_address_state:"",
                            bank_address_city:"",
                            bank_address_pincode:"",
                            bank_address_contactno:"",
                            bank_branch:"",
                            address_proof_id: "",
                            address_proof_id: "",
                            past_regulatory_action_details: "",
                            aadhar: "",
                            nominee_name: "",
                            nominee_relation: "",
                            nominee_identity_proof: "",
                            nominee_email: "",
                            nom_addr_1: "",
                            nom_addr_2: "",
                            nom_addr_state: "",
                            nom_addr_city: "",
                            nom_addr_pincode: "",
                            firstname_mother: "",
                            middlename_mother: "",
                            lastname_mother: "",

                        }
                    ]
                }
            }
        };

    } else {
        console.log("inique id " + message[0].unique_id);

        obj = {
            response: {
                error_code: err,
                data: {
                    message: [
                        {
                            unique_id: "" + message[0].unique_id,
                            stage: message[0].stage,
                            full_name: message[0].full_name,
                            email_id: message[0].email_id,
                            mobile_no: message[0].mobile_no,
                            client_code: message[0].client_code,
                            mobile_otp: message[0].mobile_otp,
                            email_otp: message[0].email_otp,
                            is_otp_verified: message[0].otp_verified,
                            lextra1: message[0].lextra1,
                            lextra2: message[0].lextra2,
                            pan: message[0].pan,
                            panfullname: message[0].panfullname,
                            dob: message[0].dob,
                            pextra1: message[0].pextra1,
                            pextra2: message[0].pextra2,
                            nse_cash: message[0].nse_cash,
                            nse_fo: message[0].nse_fo,
                            nse_currency: message[0].nse_currency,
                            mcx_commodty: message[0].mcx_commodty,
                            bse_cash: message[0].bse_cash,
                            bse_fo: message[0].bse_fo,
                            bse_currency: message[0].bse_currency,
                            ncdex_commodty: message[0].ncdex_commodty,
                            res_addr_1: message[0].res_addr_1,
                            res_addr_2: message[0].res_addr_2,
                            res_addr_state: message[0].res_addr_state,
                            res_addr_city: message[0].res_addr_city,
                            res_addr_pincode: message[0].res_addr_pincode,
                            parm_addr_1: message[0].parm_addr_1,
                            parm_addr_2: message[0].parm_addr_2,
                            parm_addr_state: message[0].parm_addr_state,
                            parm_addr_city: message[0].parm_addr_city,
                            parm_addr_pincode: message[0].parm_addr_pincode,
                            nationality: message[0].nationality,
                            gender: message[0].gender,
                            firstname1: message[0].firstname1,
                            middlename1: message[0].middlename1,
                            lastname1: message[0].lastname1,
                            maritalstatus: message[0].maritalstatus,
                            fatherspouse: message[0].fatherspouse,
                            firstname2: message[0].firstname2,
                            middlename2: message[0].middlename2,
                            lastname2: message[0].lastname2,
                            incomerange: message[0].incomerange,
                            occupation: message[0].occupation,
                            action: message[0].action,
                            perextra1: message[0].perextra1,
                            perextra2: message[0].perextra2,
                            ifsccode: message[0].ifsccode,
                            accountnumber: message[0].accountnumber,
                            bankname: message[0].bankname,
                            verified_id: message[0].verified_id,
                            beneficiary_name_with_bank: message[0].beneficiary_name_with_bank,
                            verified_at: message[0].verified_at,
                            bextra1: message[0].bextra1,
                            bextra2: message[0].bextra2,
                            pack: message[0].pack,
                            payextra1: message[0].payextra1,
                            payextra2: message[0].payextra2,
                            pancard: message[0].pancard,
                            signature: message[0].signature,
                            bankproof: message[0].bankproof,
                            bankprooftype: message[0].bankprooftype,
                            addressproof: message[0].addressproof,
                            addressprooftype: message[0].addressprooftype,
                            incomeproof: message[0].incomeproof,
                            incomeprooftype: message[0].incomeprooftype,
                            photograph: message[0].photograph,
                            uextra1: message[0].uextra1,
                            uextra2: message[0].uextra2,
                            ipv: message[0].ipv,
                            iextra1: message[0].iextra1,
                            iextra2: message[0].iextra2,
                            esign: message[0].esign,
                            esignaddhar: message[0].esignaddhar,
                            ekycdocument: message[0].ekycdocument,
                            latitude: message[0].latitude,
                            longitude: message[0].longitude,
                            bank_address: message[0].bank_address,
                            bank_address_state: message[0].bank_address_state,
                            bank_address_city: message[0].bank_address_city,
                            bank_address_pincode: message[0].bank_address_pincode,
                            bank_address_contactno: message[0].bank_address_contactno,
                            bank_branch: message[0].bank_branch,
                            micr_no: message[0].micr_no,
                            address_proof_id: message[0].address_proof_id,
                            nominee_name: message[0].nominee_name,
                            past_regulatory_action_details: message[0].past_regulatory_action_details,
                            aadhar: message[0].aadhar,
                            nominee_relation: message[0].nominee_relation,
                            nominee_identity_proof: message[0].nominee_identity_proof,
                            nominee_email: message[0].nominee_email,
                            nom_addr_1: message[0].nom_addr_1,
                            nom_addr_2: message[0].nom_addr_2,
                            nom_addr_state: message[0].nom_addr_state,
                            nom_addr_city: message[0].nom_addr_city,
                            nom_addr_pincode: message[0].nom_addr_pincode,
                            firstname_mother: message[0].firstname_mother,
                            middlename_mother: message[0].middlename_mother,
                            lastname_mother: message[0].lastname_mother,

                        }
                    ]
                }
            }
        };
    }
    return obj;
}

exports.digiouploadresponse = function (err, message, gatwayurl) {
    let obj = {
        "response": {
            "error_code": err,
            "data": {
                "message": message,
                "url": gatwayurl
            }
        }
    };
    return obj;
}

exports.responseModelObject = function (err, message) {
    let obj = {
        "response": {
            "error_code": err,
            "data": {
                "message": message
            }
        }
    };
    return obj;
}

