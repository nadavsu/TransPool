$(function () {
    var mapName = getUrlVars()["map-name"];
    $.ajax({
        data: {"map-name": mapName},
        method: "POST",
        url: "get-request-map",
        timeout: 3000,
        error: function () {
            console.error("Failed to get ajax response");
        },
        //Resp:
        //[userTripRequestsJson, userMatchedTripRequestsJson, mapTripOffersJson, mapStopsJson]
        success: function (resp) {
            $.each(resp[0] || [], loadTripRequest);
            $.each(resp[1] || [], loadMatchedTrip);
            $.each(resp[2] || [], loadTripOffer);
            $.each(resp[3] || [], loadStops);
            initializeNewTripForm(mapName);
        }
    })
});

