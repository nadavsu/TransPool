var mapName;
var refreshRate = 2000;

$(function () {
    mapName = getUrlVars()["map-name"];
    $('title.page-title').text("TransPool - " + mapName);
    initializeOfferMapPage();
    setInterval(updateOfferMapPage, refreshRate);
});

function updateOfferMapPage() {
    getMapTripRequests();
    getMapMatchedTripRequests();
    getDriverFeedbacks();
}

function initializeOfferMapPage() {
    getMap();
    getMapTripRequests();
    getMapMatchedTripRequests();
    getDriverFeedbacks();
    getDriverTripOffers();
    initializeNewTripForm(mapName);
}

//-------------------------------------------------------------------------------------------------------------------//

function getMapTripRequests() {
    $.ajax({
        data: {'map-name': mapName},
        url: 'get-map-requests',
        timeout: 2000,
        error: function () {
            console.error('Failed to get ajax response')
        },
        //Resp - List<TripRequestDTO>
        success: function (resp) {
            $('ul.requests-list').empty();
            $.each(resp || [], loadTripRequest);
        }
    })
}

function getMapMatchedTripRequests() {
    $.ajax({
        data: {'map-name': mapName},
        url: 'get-map-matches',
        timeout: 2000,
        error: function () {
            console.error('Failed to get ajax response')
        },
        //Resp - List<MatchedTripDTO>
        success: function (resp) {
            $('ul.matches-list').empty();
            $.each(resp || [], loadMatchedTrip);
        }
    })
}

function getDriverFeedbacks() {
    $.ajax({
        url: 'get-driver-feedbacks',
        timeout: 2000,
        error: function () {
            console.error('Failed to get ajax response')
        },
        //Resp - FeedbackDTO
        success: function (resp) {
            $('ul.user-feedbacks').empty();
            loadFeedbackTab(resp);
        }
    })
}

function getDriverTripOffers() {
    $.ajax({
        url: 'get-driver-offers',
        timeout: 2000,
        error: function () {
            console.error('Failed to get ajax response')
        },
        //Resp - List<TripOfferDTO>
        success: function (resp) {
            $.each(resp || [], loadTripOffer);
        }
    })
}


//----------------------------------------------------------------------------------------------------------------------

//feedback = {averageRating, feedbacks:[{feedbackerID, feedbackerName, rating, comment}]}
function loadFeedbackTab(feedback) {
    $(".averageRating").text(feedback.averageRating);
    var feedbacksList = feedback.feedbacks;
    $.each(feedbacksList || [], loadFeedbackList);
}

function loadFeedbackList(index, feedback) {
    var currentFeedback =
        $('<li class="list-group-item feedback">')
            .append(
                $('<h5 class="feedbacker-name">')
                    .text(feedback.feedbackerName)
            ).append(
            $('<h6 class="subtitle text-muted rating">')
                .text(feedback.rating + "/5 stars")
        ).append(
            $('<p class="comment">')
                .text(feedback.comment)
        );
    $('ul.user-feedbacks').append(currentFeedback);
}


/*function initializeOfferMapPage() {
    $.ajax({
        data: {"map-name": mapName},
        method: "POST",
        url: "get-offer-map",
        timeout: 3000,
        error: function () {
            console.error("Failed to get ajax response");
        },
        //Resp:
        //[mapDetails, userTripOffersJson, allFeedbacksJson, mapTripRequestsJson, mapMatchedTripsJson]
        success: function (resp) {
            generateMap(resp[0]);
            $.each(resp[1] || [], loadTripOffer);
            $.each(resp[3] || [], loadTripRequest);
            $.each(resp[4] || [], loadMatchedTrip);
            loadFeedbackTab(resp[2]);
            initializeNewTripForm(mapName);
        }
    })
}*/


