$(function () {
    var mapName = getUrlVars()["map-name"];
    $.ajax({
        data: {"map-name": mapName},
        method: "POST",
        url: "get-offer-map",
        timeout: 3000,
        error: function () {
            console.error("Failed to get ajax response");
        },
        //Resp:
        //[userTripOffersJson, allFeedbacksJson, mapTripRequestsJson, mapMatchedTripsJson]
        success: function (resp) {
            $.each(resp[0] || [], loadTripOffer);
            $.each(resp[2] || [], loadTripRequest);
            $.each(resp[3] || [], loadMatchedTrip);
            loadFeedbackTab(resp[1]);
            initializeNewTripForm(mapName);
        }
    })
});

//----------------------------------------------------------------------------------------------------------------------
