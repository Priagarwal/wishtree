var express = require('express')
   , bodyParser = require('body-parser')

var app = express();
app.use(bodyParser());
var https = require('https');
var nodemailer = require("nodemailer");

// create reusable transport method (opens pool of SMTP connections)
var smtpTransport = nodemailer.createTransport("SMTP",{
    service: "Gmail",
    auth: {
        user: "wishtree2014@gmail.com",
        pass: "ciohackathon"
    }
});

// setup e-mail data with unicode symbols
var mailOptions = {
    from: "wishtree2014@gmail.com", // sender address
    to: "wishtree2014@gmail.com", // list of receivers
    subject: "Hello test", // Subject line
    text: "Hello world ?", // plaintext body
    html: "<b>Hello world ?</b>" // html body
};

// send mail with defined transport object


app.get('/sendemail', function(req, res, next) {
	console.log("GET");
	smtpTransport.sendMail(mailOptions, function(error, response){
		if(error){
			console.log(error);
		}else{
			console.log("Message sent: " + response.message);
		}
	});
	res.send('OK');
})

app.listen(3000)


