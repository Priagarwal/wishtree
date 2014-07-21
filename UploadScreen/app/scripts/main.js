console.log('\'Allo \'Allo!');
$(document).ready(function(){
function getRequests() {
    var s1 = location.search.substring(1, location.search.length).split('&'),
        r = {}, s2, i;
    for (i = 0; i < s1.length; i += 1) {
        s2 = s1[i].split('=');
        r[decodeURIComponent(s2[0]).toLowerCase()] = decodeURIComponent(s2[1]);
    }
    return r;
};

var QueryString = getRequests();
var orgID = QueryString["org"];
	 var campcount = 1;
   // var orgID = "3";
   // var campID = "53719f97e8bc5d0d1cbee6d3";
   
   var orgsArr = [];
  var target = "http://10.9.205.55:3000/collections/ewt_organizations/";
    var request = $.ajax({
        url: target,
        success: function(response) {
            console.log(response);
            orgs = response;
            for (var i = 0; i < orgs.length; i++) {
                if(orgs[i]["Org_id"] == orgID)
                	orgsArr.push(orgs[i]);
            }
            $('#orgName').html(orgsArr[0]["name"]);
            $('#OrgDescription').html(orgsArr[0]["description"])
            if(orgsArr[0]["createdOn"]!="")$('#createdON').html(orgsArr[0]["createdOn"])
              else $('#createdON').html("NA");
            
        }
    });
    function UpdateStatisticsForORG(id){
      var count = 0;
      var countfullfilled = 0;
      $('.totwishescount').each(function(){
          count += parseInt($(this).text());
      });
      $('.totwishescountfullfill').each(function(){
          countfullfilled += parseInt($(this).text());
      });
      $('#totWishes').html(count);
      $('#totWishesfullfilled').html(countfullfilled);
      
    }

    function updateCampaigns(id,name){
      var target = "http://10.9.205.55:3000/collections/ewt_wishes/";
        var request = $.ajax({
            url: target,
            success: function(response) {
                wishes = response;
                var count = 0;
                var pending = 0;
                for (var i = 0; i < wishes.length; i++) {
                    if(wishes[i]["campaignId"] != id)continue;
                    if(wishes[i]["status"] == 1)
                      {
                        count++;
                      }
                      else pending++;
                }
                var listCampaigns = "<tr>\
                      <td>"+campcount++ +"</td>\
                      <td><a class='opencampaign' data-name='"+name+"' data-cid='"+id+"' href='javascript:void(0)'>"+name+"</a></td>\
                      <td class='totwishescount'>"+(count+pending)+"</td>\
                      <td class='totwishescountfullfill'>"+count+"</td>\
                      <td>"+pending+"</td>\
                      <td>"+count*100/(count+pending)+"</td>\
                    </tr>";
                $("#list-campaigns").append(listCampaigns);
                UpdateStatisticsForORG(orgID);
            }

        });
    }
   var campaignsArr = [];
    var target = "http://10.9.205.55:3000/collections/ewt_campaign/";
    campcount = 1;
    var request = $.ajax({
        url: target,
        success: function(response) {
            console.log(response);
            campaigns = response;
            for (var i = 0; i < campaigns.length; i++) {
                if(campaigns[i]["organizationId"] == orgID)
                	campaignsArr.push(campaigns[i]);
            }
            var listCampaigns = "";
            $("#list-campaigns").html("");
            for (var i = 0; i < campaignsArr.length; i++) {
              updateCampaigns(campaignsArr[i]["Camp_id"],campaignsArr[i]["name"])
            }
        }
    });




    $(document.body).on('click', 'a.opencampaign', function (event) {
    	var wishesArr = [];
    	var campaignId= $(this).attr('data-cid');
    	$('#CNameBold').html($(this).attr('data-name'));
    	    var target = "http://10.9.205.55:3000/collections/ewt_wishes/";
		    var request = $.ajax({
		        url: target,
		        success: function(response) {
		            wishes = response;
		            for (var i = 0; i < wishes.length; i++) {
		                if(wishes[i]["campaignId"] == campaignId)
		                	wishesArr.push(wishes[i]);
		            }
		            var listWishes = "";
		            if(wishesArr.length ==0 )listWishes ="No Wishes.Please Add Wishes"
		            for (var i = 0; i < wishesArr.length; i++) {
		            	listWishes +='     <tr>\
						                      <td>'+(i+1)+'</td>\
						                      <td>'+wishesArr[i]["firstName"]+'</td>\
						                      <td>'+wishesArr[i]["age"]+'</td>\
						                      <td>'+wishesArr[i]["gender"]+'</td>\
						                      <td>'+wishesArr[i]["itemId"]+'</td>\
						                      <td>'+wishesArr[i]["cost"]+'</td>';
                  if(wishesArr[i]["status"]==1)listWishes += "<td>Fullfilled</td>" 
                    else listWishes += "<td>Incomplete</td>" 
						              listWishes += '</tr>';
		            }
		            $("#wishes").html(listWishes);
		            $('.campaign-info').css('display','block');
		        }

		    });
    })

	console.log("HERE");
	$('#myTab a').click(function (e) {
	  e.preventDefault()
	  $(this).tab('show');
	  $('.campaign-info').css('display','none');
	});
	$('.opencampaign').click(function(){
		$('.campaign-info').css('display','block');
		$('.campaign-info .name').html($(this).attr('data-name'));
	})
	$('#addWish').click(function(){

		if($('#addWish-modal').length!=0){
			$('#addWish-modal').modal('toggle');
			return;
		}
		var str="";
		str += "<div class='modal' id='addWish-modal' tabindex='-1' role='dialog' aria-labelledby='addNewWish' aria-hidden='true'>";
		str += "<div class='modal-dialog'>";
		str += "<div class='modal-content'>";
		str += "<div class='modal-header'>";
		str += "<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button>";
		str += "<h4 class='modal-title' id='addNewWish'>Create Wish</h4>";
		str += "</div>";
		str += "<div class='modal-body'>";
		str += '<div class="form-group">\
                <label>Ebay Id Number</label>\
                <input class="form-control" placeholder="ID...">\
                <label>Name</label>\
                <input class="form-control" placeholder="Wish Maker...">\
                <label>Age</label>\
                <input class="form-control" placeholder="Age">\
                <label>Gender</label>\
                <input class="form-control" placeholder="Gender">\
                 <label>Cost</label>\
                <input class="form-control" placeholder="Cost">\
                <label>Special</label>\
                <textarea class="form-control" placeholder="Special Notes..."></textarea>\
              </div>';
		str += "</div>";
		str += "<div class='modal-footer'>";
		str += "<button type='button' class='btn btn-default' data-dismiss='modal'>Close</button>";
		str += "<button type='button' id='addNewWishTODb' class='btn btn-primary'>Add Wish</button>";
		str += "</div>";
		str += "</div>";
		str += "</div>";
		str += "</div>"
		$("body").append(str);
		$('#addWish-modal').hide();		
		$('#addWish-modal').modal('toggle');
	

	});
	$('#launchCampaign').click(function(){
		
		if($('#launch-modal').length!=0){
			$('#launch-modal').modal('toggle');
			return;
		}
		var str="";
		str += "<div class='modal' id='launch-modal' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>";
		str += "<div class='modal-dialog'>";
		str += "<div class='modal-content'>";
		str += "<div class='modal-header'>";
		str += "<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button>";
		str += "<h4 class='modal-title' id='myModalLabel'>Create Campaign</h4>";
		str += "</div>";
		str += "<div class='modal-body'>";
		str += '<div class="form-group">\
                <label>Campaign Name</label>\
                <input class="form-control" placeholder="Name">\
                <label>Description</label>\
                <textarea class="form-control" placeholder="Describe the campaign..."></textarea>\
                <label>ADDRESS</label>\
                <textarea class="form-control" placeholder="Address"></textarea>\
                <label>CITY</label>\
                <input class="form-control" placeholder="City">\
                <label>STATE</label>\
                <input class="form-control" placeholder="STATE">\
                <label>COUNTRY</label>\
                <input class="form-control" placeholder="COUNTRY">\
                <label>EMAIL</label>\
                <input class="form-control" placeholder="EMAIL">\
                <label>ZIPCODE</label>\
                <input class="form-control" placeholder="ZIPCODE">\
                <label>EMAIL</label>\
                <input class="form-control" placeholder="EMAIL">\
                <label>PHONE</label>\
                <input class="form-control" placeholder="PHONE">\
              </div>';
  // str += '<form class="form" action="##" method="post" id="registrationForm">\
  //                     <div class="form-group">\
  //                         \
  //                         <div class="col-xs-6">\
  //                             <label for="first_name">Campaign Name</label>\
  //                             <input type="text" class="form-control" name="first_name" id="first_name" placeholder="first name" title="enter your first name if any.">\
  //                         </div>\
  //                     </div>          </br>\
  //                     <div class="form-group">\
  //                         \
  //                         <div class="col-xs-6">\
  //                             <label for="phone">Phone</label>\
  //                             <input type="text" class="form-control" name="phone" id="phone" placeholder="enter phone" title="enter your phone number if any.">\
  //                         </div>\
  //                     </div></br>\
  //         \
  //                     <div class="form-group">\
  //                         <div class="col-xs-6">\
  //                            <label for="mobile">Mobile</label>\
  //                             <input type="text" class="form-control" name="mobile" id="mobile" placeholder="enter mobile number" title="enter your mobile number if any.">\
  //                         </div>\
  //                     </div></br>\
  //                     <div class="form-group">\
  //                         \
  //                         <div class="col-xs-6">\
  //                             <label for="email">Email</label>\
  //                             <input type="email" class="form-control" name="email" id="email" placeholder="you@email.com" title="enter your email.">\
  //                         </div></br>\
  //                     </div>\
  //                     <div class="form-group">\
  //                         \
  //                         <div class="col-xs-6">\
  //                             <label for="email">Location</label>\
  //                             <input type="email" class="form-control" id="location" placeholder="somewhere" title="enter a location">\
  //                         </div></br>\
  //                     </div>\
  //                     <div class="form-group">\
  //                         \
  //                         <div class="col-xs-6">\
  //                             <label for="password">Password</label>\
  //                             <input type="password" class="form-control" name="password" id="password" placeholder="password" title="enter your password.">\
  //                         </div>\
  //                     </div>\
  //               </form>';

		str += "</div>";
		str += "<div class='modal-footer'>";
		str += "<button type='button' class='btn btn-default' data-dismiss='modal'>Close</button>";
		str += "<button type='button' id='uploadCampaign' class='btn btn-primary'>Launch Campaign</button>";
		str += "</div>";
		str += "</div>";
		str += "</div>";
		str += "</div>"
		$("body").append(str);
		$('#launch-modal').hide();		
		$('#launch-modal').modal('toggle');
	});

})

  // `EWT_CAMPAIGN_ID` int(11) NOT NULL auto_increment,
  // `EWT_CAMPAIGN_DESCRIPTION` varchar(255) default NULL,    
  // `EWT_ORGANIZATION_ID` int(11),
  // `ADDRESS1` varchar(255) default NULL,  
  // `ADDRESS2` varchar(255) default NULL,  
  // `CITY` varchar(255) default NULL,  
  // `STATE` varchar(255) default NULL,  
  // `COUNTRY` varchar(255) default NULL,  
  // `ZIPCODE` varchar(255) default NULL,  
  // `EMAIL` varchar(255) default NULL,   
  // `PHONE` varchar(255) default NULL, 
  // `CREATED_DATE` datetime default NULL,  