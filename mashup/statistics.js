var url_wishes = "http://10.9.205.55:3000/collections/ewt_wishes/";
var url_campaigns = "http://10.9.205.55:3000/collections/ewt_campaign/";
var url_organizations = "http://10.9.205.55:3000/collections/ewt_organizations/";

	var xhReq = new XMLHttpRequest();
	xhReq.open("GET", url_wishes, false);
	xhReq.send(null);
	var data_wishes = JSON.parse(xhReq.responseText);
	var fulfilled = 0;
	var wishes = data_wishes.length;
	console.log("Total no. of wishes are ",wishes);
	for( var i = 0; i < data_wishes.length; i++) {
		if(data_wishes[i].status == 1) {
			fulfilled++;
		}
	}
	console.log("Total no. of wishes fulfilled are ",fulfilled);
	
	xhReq.open("GET", url_organizations, false);
	xhReq.send(null);
	var data_organizations = JSON.parse(xhReq.responseText);
	var organizations = data_organizations.length;
	console.log("Total no. of organisations participating are ",organizations);
	
	xhReq.open("GET", url_campaigns, false);
	xhReq.send(null);
	var data_campaigns = JSON.parse(xhReq.responseText);
	var campaignslen = data_campaigns.length;
	console.log("Total no. of campaigns are ",campaigns);
	