let db = require("./db");
bcrypt = require('bcrypt-node');
crypto = require('crypto-js');

exports.authResult = authResult = {
    success: 0,
    passwordExpired: 1,
    userOrPasswordIncorrect: 2,
    failure: 3,
    duplicate: 4,
    maxAttemptsExceeded: 5,
    invalidSession: 6,
    inactiveUser:7,
    sameIdPassword:9,
    samePasswords:10,
    loginNotAllowed:11,
    maxUnlockExceeded:12,
}

const MAX_PASSWORD_ATTEMPTS = 3;
function isOlderThanDays(ts, days) {
    let tsMax = days * 24 * 60 * 60 * 1000;
    return Date.now() - ts > tsMax;
}

exports.authenticateWithSession = function(gscid, pass, gcid, userType, status, category, pwdAttempts, deviceId, deviceType, deviceDetails, func) {
    authenticateLogin(gscid, pass, gcid, userType, status, category, pwdAttempts, function(ret, err, gcid, device_id) {
        if(ret == 0 && err == 0) {
            bcrypt.genSalt(10, function(err, salt) {
                if(err) {
                    func(ret, err, gcid, salt, device_id);
                    return;
                }
                else {
                    db.setSession(gcid, salt, deviceId, deviceType, deviceDetails, function(err1) {
                        if(err1 == db.dalResult.success) {
                            func(ret, err1, gcid, salt, device_id);
                            return;
                        }
                    });
                }
            });
        } 
        else 
        func(ret, err, gcid, 0, device_id);
	});
}

function authenticateLogin(gscid, password, gcid, userType, status ,category, pwdAttempts, func) {
    password = crypto.MD5(password);
    console.log("Password :   " + password);
    if(status != 0 && status != 1 && category.toLowerCase() == "retail") {
        func(authResult.success, authResult.inactiveUser, gcid, "");
        return;
    }
    else {
        if(pwdAttempts>=MAX_PASSWORD_ATTEMPTS) {
            func(authResult.success, authResult.maxUnlockExceeded, 0, device_id);
        }
        else {
            db.getClientLoginDetails(gscid, function(err, storedPasswordLogin, saltLogin, attemptsLogin, lastUpdateTimeLogin, storedPasswordTrans, saltTrans, attemptsTrans, lastUpdateTimeTrans, device_id) {
                if(err) {
                    console.log('Client Login Details not Found');
                    func(authResult.failure, authResult.failure, gcid, device_id);
                    return;
                }
                else {
                    if(attemptsLogin >= MAX_PASSWORD_ATTEMPTS) {
                        func(authResult.success, authResult.maxAttemptsExceeded, gcid, device_id);
                        return;
                    }
                    console.log(" " + storedPasswordLogin + " :: " +  saltLogin  + " :: " +  attemptsLogin  + " :: " +  lastUpdateTimeLogin  + " :: " +  storedPasswordTrans  + " :: " + saltTrans  + " :: " +  attemptsTrans  + " :: " +  lastUpdateTimeTrans + " gcid" + gcid);
                    if(password == storedPasswordLogin) {
                        console.log("Password Match");
                        if(isOlderThanDays(lastUpdateTimeLogin * 1000, 1400))
                            func(authResult.success, authResult.passwordExpired, gcid, device_id);
                        else
                            func(authResult.success, authResult.success, gcid, device_id);

                        db.resetFailedPasswordAttempt_v1(gcid);
                        return;
                    }
                    else {
                        console.log("Password Not Match")
                        db.addFailedPasswordAttempt_v1(gcid);
                        func(authResult.success, authResult.userOrPasswordIncorrect, gcid, device_id);
                        return;
                    }
                }
            });
        }
    }
}

exports.changePassword = function(gscid, oldPassword, newPassword, gcid, status, category, func) {
    newPassword = crypto.MD5(newPassword);
    if(gscid.toLowerCase() == newPassword.toLowerCase())
        func(authResult.sameIdPassword);
    else {
        db.getClientLoginDetails(gscid, function(err2, storedPasswordLogin, saltLogin, attemptsLogin, lastUpdateTimeLogin, storedPasswordTrans, saltTrans, attemptsTrans, lastUpdateTimeTrans,device_id) {
            if(err) {
                func(err);
            } else {
                let pwd = storedPasswordTrans;
                if(newPassword == pwd) {
                    func(authResult.samePasswords);
                    return;
                } else {
                    authenticate(gscid, oldPassword, gcid, status, category, attemptsLogin, function(ret, err, gcid) {
                        if(ret == authResult.success && (err == authResult.success || err == authResult.passwordExpired)) {
                            setPassword(gcid, gscid, newPassword, 0, function(err) {
                                func(err);
                            });
                        } 
                        else
                            func(err);
                    });
                }
            }

        });
    }
}


function authenticate(gscid, password, gcid, status, category, pwdAttempts, func) {
    password = crypto.MD5(password);
	console.log("Password :   " + password);

    if(status != 0 && status != 1 && category.toLowerCase() == "retail") {
        func(authResult.success, authResult.inactiveUser, gcid, "");
        return;
    } else {
        if(status) {
            console.log('status is ', status);
            if(pwdAttempts >= MAX_PASSWORD_ATTEMPTS)
                func(authResult.success, authResult.maxUnlockExceeded, 0, '0');
            else {
                db.getClientLoginDetails(gscid, function(err, storedPasswordLogin, saltLogin, attemptsLogin, lastUpdateTimeLogin, storedPasswordTrans, saltTrans, attemptsTrans, lastUpdateTimeTrans, device_id) {
                    if(err) {
                        func(authResult.success, authResult.userOrPasswordIncorrect, 0, device_id);
                    }
                    else{
                        if(attemptsLogin >= MAX_PASSWORD_ATTEMPTS) {
                            func(authResult.success, authResult.maxAttemptsExceeded, gcid,device_id);
                            return;
                        }
                        if(password == storedPasswordLogin) {
                            if(isOlderThanDays(lastUpdateTimeLogin * 1000, 1400)) {
                                func(authResult.success, authResult.passwordExpired, gcid,device_id);
                            } else {
                                func(authResult.success, authResult.success, gcid,device_id);
                            }
                            db.resetFailedPasswordAttempt(gcid, lastUpdateTimeLogin);
                            return;
                        } else {
                            db.addFailedPasswordAttempt(gcid, lastUpdateTimeLogin);
                            func(authResult.success, authResult.userOrPasswordIncorrect, 0, device_id);
                            return;
                        }
                        
                    }
                });
            }
        } else {
            func(authResult.success, authResult.inactiveUser, gcid, device_id);
        }
    }
}
