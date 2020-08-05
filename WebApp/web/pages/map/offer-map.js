var mapName;
var refreshRate = 2000;
var feedbacksVersion = 0;
var ridersVersion = 0;

$(function () {
    mapName = getUrlVars()["map-name"];
    $('title.page-title').text("TransPool - " + mapName);
    initializeOfferMapPage();
    setInterval(updateOfferMapPage, refreshRate);
});

function updateOfferMapPage() {
    getMapTripRequests();
    getMapMatchedTripRequests();
    getDriverFeedbacks(function(feedback) {
        if (feedback.version !== feedbacksVersion) {
            feedbacksVersion = feedback.version;
            loadFeedbackTab(feedback);
            $.each(feedback.feedbacks || [], appendFeedbackMessage);
            $("#notification-modal").modal("show");
        }
    });
    getDriverRiders(function (riders) {
        if (riders.version !== ridersVersion) {
            ridersVersion = riders.version;
            $.each(riders.riders || [], appendRidersMessage);
            $("#notification-modal").modal("show");
        }
    })
}

function initializeOfferMapPage() {
    getMap();
    getMapTripRequests();
    getMapMatchedTripRequests();
    getDriverTripOffers();
    initializeNewTripForm(mapName);
    getDriverFeedbacks(function (feedback) {
        loadFeedbackTab(feedback);
        feedbacksVersion = feedback.version;
    });
    getDriverRiders(function (riders) {
        ridersVersion = riders.version;
    })
}

//-------------------------------------------------------------------------------------------------------------------//

function getMapTripRequests() {
    $.ajax({
        data: {'map-name': mapName},
        url: 'get-map-requests',
        timeout: 2000,
        error: function () {
            console.error('Failed to get ajax response from get-map-requests')
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
            console.error('Failed to get ajax response from get-map-matches')
        },
        //Resp - List<MatchedTripDTO>
        success: function (resp) {
            $('ul.matches-list').empty();
            $.each(resp || [], loadMatchedTrip);
        }
    })
}

function getDriverTripOffers() {
    $.ajax({
        url: 'get-driver-offers',
        timeout: 2000,
        error: function () {
            console.error('Failed to get ajax response from get-driver-offers')
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
