const fs = require('fs');
const myDbString = loadMyDBConfig();
const mysql = require('mysql');
exports.poolCluster = poolCluster = mysql.createPoolCluster();

let dir = __dirname;
let documentPath = dir.replace('Admin/router', 'Files/Documents/')

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
if (poolCluster)
    console.log(`${myDbString.db} Database Connected on Host ${myDbString.host}`);
else 
    console.log('Cant connect to db, Check ur db connection');

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
            console.log('SQLITE DB Slave: ERR 3');
            func(dalResult.connectionError, 0);
            return;
        });
    });
}

function querymaster(query, func) {
    poolCluster.getConnection('MASTER', function (err, connection) {
        if (err) {
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
            console.log('SQLITE DB Master: ERR 3');
            func(dalResult.connectionError, 0);
            return;
        });
    });
}

exports.writeToLogFile = function(logName, data) {
    let dir = __dirname;
    let d = new Date();
    let mm = (((d.getMonth() + 1) / 10) >= 1) ? parseInt(d.getMonth() + 1) : "0" + parseInt(d.getMonth() + 1);
    let dd = ((d.getDate() / 10) >= 1) ? d.getDate() : "0" + d.getDate();
    let yyyy = d.getFullYear();
    let date = `${dd}${mm}${yyyy}`
    dir = dir.replace('router', 'logFiles')
    if (!fs.existsSync(dir)) {
        fs.mkdirSync(dir)
    }
    let file = dir + '/' + logName + '_' + date + '.txt';
    console.log('file', file);
    fs.appendFileSync(file, data, (err)=> {
        if(err) 
        console.log('Error Printing Log');
    })
}

exports.getDateTime = function() {
    let date_ob = new Date();
    let day = ("0" + date_ob.getDate()).slice(-2);
    let month = ("0" + (date_ob.getMonth() + 1)).slice(-2);
    let year = date_ob.getFullYear();
    let date = year + "-" + month + "-" + day;
    console.log(date);
    let hours = date_ob.getHours();
    let minutes = date_ob.getMinutes();
    let seconds = date_ob.getSeconds();
    let dateTime = day + "-" + month + "-" + year + " " + hours + ":" + minutes + ":" + seconds;
    console.log(dateTime)
    return dateTime;
}

exports.getCurrTime = function() {
	let time = new Date();
	return time;
}

/*
exports.getAllStats = function(func) {
    query("SELECT first.* FROM (select stage,IF(stage=0,'OTP NOT VERIFIED' ,IF(stage='0','OTP VERIFIED' ,IF(stage='1','PAN VERIFIED'  ,\
    IF(stage='7','COMPLETED USERS',  IF(stage='9','FINISH USERS' ,IF(stage='11','AUTHORIZED USERS' ,\
    IF(stage='6'||stage='5'||stage='3'||stage='2','IN PROCESS',IF(stage='4','PLAN SELECTION', stage)))))))) as reporttitle,\
     count(stage) as count from ekyc group by reporttitle) as first UNION ALL SELECT second.* FROM (select '21' as stage, \
     'all' as reporttitle, count(*) as count from ekyc)as second UNION ALL SELECT third.* FROM (select '-1' as stage,\
      'First Time User' as reporttitle, count(*) as count from ekyc)as third", function (err, rows) {
        func(err,rows);
    });
} 
*/

exports.getAllStats = function(func) {
    query("SELECT first.* FROM (select stage,IF(stage='-1','OTP NOT VERIFIED' ,IF(stage='0','OTP VERIFIED' ,IF(stage='1','PAN VERIFIED'  ,\
    IF(stage='7','COMPLETED USERS',  IF(stage='9','FINISH USERS' ,IF(stage='11','AUTHORIZED USERS' ,\
    IF(stage='6'||stage='5'||stage='4'||stage='3'||stage='2','IN PROCESS', stage))))))) as reporttitle,\
     count(stage) as count from ekyc group by reporttitle) as first UNION ALL SELECT second.* FROM (select '21' as stage, \
     'all' as reporttitle, count(*) as count from ekyc)as second UNION ALL SELECT third.* FROM (select '-1' as stage,\
      'First Time User' as reporttitle, count(*) as count from ekyc where stage < 0)as third", function (err, rows) {
        func(err, rows);
    });
}

exports.getReportData = function(stage,func) {
	if(stage=="21") {
        query("select *,IF(stage='-1','OTP NOT VERIFIED' ,IF(stage='0','OTP VERIFIED' ,IF(stage='1','PAN VERIFIED'  ,IF(stage='7','COMPLETED USERS'  ,\
            IF(stage='9','FINISH USERS' ,IF(stage='11','AUTHORIZED USERS' ,IF(stage='6'||stage='5'||stage='4'||stage='3'||stage='2' , 'IN PROCESS', stage))))))) as activesession , datediff(  from_unixtime(unix_timestamp()), eKycStartDate) as start_days, datediff( from_unixtime(unix_timestamp()), eKycLastUpdateTime ) as lastupdate_days  from ekyc ", function (err, rows) {
            func(err,rows);
        });
    } else if(stage=='6'||stage=='5'||stage=='4'||stage=='3'||stage=='2') {
        /// for first time user we dont  have specific stage. we have managed state -1 communicated to front end 
        query("select *,IF(stage='-1','OTP NOT VERIFIED' ,IF(stage='0','OTP VERIFIED' ,IF(stage='1','PAN VERIFIED'  ,IF(stage='7','COMPLETED USERS'  ,\
            IF(stage='9','FINISH USERS' ,IF(stage='11','AUTHORIZED USERS' ,IF(stage='6'||stage='5'||stage='4'||stage='3'||stage='2','IN PROCESS', stage))))))) as activesession , datediff(  from_unixtime(unix_timestamp()), eKycStartDate) as start_days, datediff( from_unixtime(unix_timestamp()), eKycLastUpdateTime ) as lastupdate_days  from ekyc where stage='6' or stage='5' or stage='4' or stage='3' or stage='2' ", function (err, rows) {
            func(err,rows);
        });
    } else if(stage=="-1") {   
        /// for first time user we dont  have specific stage. we have managed state -1 communicated to front end 
        query("select *,IF(stage='-1','OTP NOT VERIFIED' ,IF(stage='0','OTP VERIFIED' ,IF(stage='1','PAN VERIFIED'  ,IF(stage='7','COMPLETED USERS'  ,\
            IF(stage='9','FINISH USERS' ,IF(stage='11','AUTHORIZED USERS' ,IF(stage='6'||stage='5'||stage='4'||stage='3'||stage='2','IN PROCESS', stage))))))) as activesession , datediff(  from_unixtime(unix_timestamp()), eKycStartDate) as start_days, datediff( from_unixtime(unix_timestamp()), eKycLastUpdateTime ) as lastupdate_days  from ekyc where stage < 0", function (err, rows) {
            func(err,rows);
        });
    } else {
        query("select *,IF(stage='-1','OTP NOT VERIFIED' ,IF(stage='0','OTP VERIFIED' ,IF(stage='1','PAN VERIFIED'  ,IF(stage='7','COMPLETED USERS'  ,\
            IF(stage='9','FINISH USERS' ,IF(stage='11','AUTHORIZED USERS' ,IF(stage='6'||stage='5'||stage='4'||stage='3'||stage='2','IN PROCESS',stage))))))) as activesession , datediff(  from_unixtime(unix_timestamp()), eKycStartDate) as start_days, datediff( from_unixtime(unix_timestamp()), eKycLastUpdateTime ) as lastupdate_days  from ekyc where stage= "+stage, function (err, rows) {
            func(err,rows);
        });
    }
}

exports.getUserDetails = function(uniqueid,func) {
  query("select *,IF(stage='-1','OTP NOT VERIFIED' ,IF(stage='0','OTP VERIFIED' ,IF(stage='1','PAN VERIFIED'  ,IF(stage='7','COMPLETED USERS'  ,\
    IF(stage='9','FINISH USERS' ,IF(stage='11','AUTHORIZED USERS' ,IF(stage='6'||stage='5'||stage='4'||stage='3'||stage='2','IN PROCESS', stage))))))) as activesession,  from ekyc where unique_id= "+uniqueid , function (err, rows) {
        func(err,rows);
    });

}

exports.getPennyDropReport = function(func) {
query("select *,IF(stage='-1','OTP NOT VERIFIED' ,IF(stage='0','OTP VERIFIED' ,IF(stage='1','PAN VERIFIED'  ,IF(stage='7','COMPLETED USERS'  ,\
            IF(stage='9','FINISH USERS' ,IF(stage='11','AUTHORIZED USERS' ,IF(stage='6'||stage='5'||stage='4'||stage='3'||stage='2','IN PROCESS', stage))))))) as activesession , datediff(  from_unixtime(unix_timestamp()), eKycStartDate) as start_days, datediff( from_unixtime(unix_timestamp()), eKycLastUpdateTime ) as lastupdate_days  from ekyc where stage>= 3", function (err, rows) {
            func(err,rows);
            });
    /*query("select full_name, mobile_no, Pan , verified_id, beneficiary_name_with_bank,verified_at,IF(stage=0,'OTP NOT VERIFIED' ,IF(stage='0','OTP VERIFIED' ,IF(stage='1','PAN VERIFIED'  ,IF(stage='7','COMPLETED USERS'  ,\
    IF(stage='9','FINISH USERS' ,IF(stage='11','AUTHORIZED USERS' ,IF(stage='6'||stage='5'||stage='3'||stage='2','IN PROCESS',IF(stage='4','PLAN SELECTION', stage)))))))) as activesession , datediff(  from_unixtime(unix_timestamp()), eKycStartDate) as start_days, datediff( from_unixtime(unix_timestamp()), eKycLastUpdateTime ) as lastupdate_days  from ekyc where stage>= 3", function (err, rows) {
        func(err,rows);
    });*/
}

exports.getStatusReport = function(func) {
    query("select *,IF(stage='-1','OTP NOT VERIFIED' ,IF(stage='0','OTP VERIFIED' ,IF(stage='1','PAN VERIFIED'  ,IF(stage='7','COMPLETED USERS'  ,\
            IF(stage='9','FINISH USERS' ,IF(stage='11','AUTHORIZED USERS' ,IF(stage='6'||stage='5'||stage='4'||stage='3'||stage='2','IN PROCESS', stage))))))) as activesession , datediff(  from_unixtime(unix_timestamp()), eKycStartDate) as start_days, datediff( from_unixtime(unix_timestamp()), eKycLastUpdateTime ) as lastupdate_days  from ekyc", function (err, rows) {
            func(err,rows);
            });
    /*query("select *,IF(stage=0,'OTP NOT VERIFIED' ,IF(stage='0','OTP VERIFIED' ,IF(stage='1','PAN VERIFIED'  ,IF(stage='7','COMPLETED USERS',\
            IF(stage='9','FINISH USERS' ,IF(stage='11','AUTHORIZED USERS' ,IF(stage='6'||stage='5'||stage='3'||stage='2','IN PROCESS',IF(stage='4','PLAN SELECTION', stage)))))))) as activesession , datediff(  from_unixtime(unix_timestamp()), eKycStartDate) as start_days, datediff( from_unixtime(unix_timestamp()), eKycLastUpdateTime ) as lastupdate_days  from ekyc ", function (err, rows) {
                func(err,rows);    
        });*/
}

exports.getUserInfo = function(email_id, mobile_no, pan, func) {
    query('select * from ekyc where email_id = "'+ email_id +'" and mobile_no = '+ mobile_no+' and pan = "'+ pan +'"', func)
}

exports.updatePersonalDetails = function (key, value, email_id, mobile_no, pan, func) {
    query('update ekyc set '+ key +' = "'+ value +'" where email_id = "'+email_id+'" and mobile_no = "'+ mobile_no+'" and pan = "'+ pan +'"', func)
}

exports.getUserInfoDB = function (gscid, func) {
    query("SELECT cli.gcid as 'gcid', cli.type as 'type', cli.status as 'status', clidet.cCategory as 'category',IFNULL(cliMstr.iuserType,0) as 'mfClient',IFNULL(clidet.cPanNo,0) as 'panNo',IFNULL((IF(cliMstr.cCKYC='0','N',IF(cliMstr.cCKYC='1','Y',cliMstr.cCKYC))),'N') as 'kYCStatus',IFNULL(cliMstr.cFirstApplicantDOB,0) as 'dob', cli.email as 'email', cli.mobile as 'mobile',clidet.cGreekUserOrInvestorName as 'CliName',clidet.iUnlockPwdAttempts as 'pwdAttempts' from clients cli join clientdetails clidet on(cli.gscid = clidet.cGreekUserOrInvestorId) left outer join clientmasterreport cliMstr on (cliMstr.cClientCode = cli.gscid) where cli.gscid='" + gscid + "' and clidet.cGreekUserOrInvestorId='" + gscid + "'", function (err, rows) {
    if (err == dalResult.success)
        func(err, rows[0].gcid, rows[0].type, rows[0].status, rows[0].category, rows[0].mfClient, rows[0].panNo, rows[0].kYCStatus, rows[0].dob, rows[0].email, rows[0].mobile, rows[0].CliName,rows[0].pwdAttempts);
    else
        func(err, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0);
    });
}

exports.setSession = function (gcid, sessionId, deviceId = 1, deviceType = 1, deviceDetails = 1, func) {
    querymaster("Update authinfo set session_id = '" + sessionId + "', session_updated_time = UNIX_TIMESTAMP(now()), device_id = '" + deviceId + "', device_type = '" + deviceType + "', device_details = '" + deviceDetails + "' where gcid='" + gcid + "'", function (err) {
        if (err == dalResult.success) 
            func(err);
        else 
            func(err);
    });
}

exports.getClientLoginDetails = function (gscid, func) {
    query("SELECT c.cPassword,c.iLoginAttempts,Unix_Timestamp(STR_TO_DATE(c.cPwdTime,'%d-%b-%Y %T')) as 'llogtime',a.device_id from clientdetails c,authinfo a where c.cGreekUserOrInvestorId=a.gscid and c.cGreekUserOrInvestorId='" + gscid + "' ORDER BY llogtime DESC LIMIT 1", function (err, rows) {
        if (err == dalResult.success)
            func(err, rows[0].cPassword, "", rows[0].iLoginAttempts, rows[0].llogtime, "", "", 0, 0, rows[0].device_id);
        else
            func(err, "", "", 0, 0, "", "", 0, 0, 0);
    });
}

exports.resetFailedPasswordAttempt = function (gcid, lastUpdatedTime) {
	querymaster("UPDATE authinfo SET fail_attempts=0 WHERE gcid=" + gcid + " AND last_updated_time=" + lastUpdatedTime, function (err, rows) { });
}
exports.resetFailedPasswordAttempt_v1 = function (gcid) {
    querymaster("UPDATE authinfo a,clientDetails b SET a.fail_attempts=0,b.iIBTLoginAttempts=0,b.iLoginAttempts=0 WHERE  (b.cGreekUserOrInvestorId=a.gscid) and a.gcid='"+gcid+"'", function (err, rows) {});
}

exports.addFailedPasswordAttempt = function (gcid, lastUpdatedTime) {
	querymaster("UPDATE authinfo SET fail_attempts=fail_attempts+1 WHERE gcid=" + gcid + " AND last_updated_time=" + lastUpdatedTime, function (err, rows) { });
}

exports.addFailedPasswordAttempt_v1 = function(gcid) {
    querymaster("UPDATE authinfo a,clientDetails b SET a.fail_attempts=a.fail_attempts+1,b.iIBTLoginAttempts=b.iIBTLoginAttempts+1,b.iLoginAttempts=b.iLoginAttempts+1 WHERE  (b.cGreekUserOrInvestorId=a.gscid) and a.gcid='"+gcid+"'", function (err, rows) {});
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
                saveAddressDocumentID(type, email_id, mobile_no, documentPath + "/" + pan + "/" + imagePath);
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