$(getUser(createUserCard));


// userJson = {ID: "" ; username: "" ; balance: "" ; transactionHistory: {date: "{}" ; transactionType: "" ; amount: ""}}
function createUserCard(user) {
    var lastThreeTransactions = user.transactionHistory.slice(Math.max(user.transactionHistory.length - 3, 0));
    //TODO: There is no such k,v called user.type
    if (user.type === "RIDER") {
        $(".user-image").src = RIDER_IMAGE;
    } else {
        $(".user-image").src = DRIVER_IMAGE;

    }
    $.each(lastThreeTransactions || [], createRecentTransactions);
}

// transaction = {"date":{"time":{"hour":0,"minute":0,"second":0,"nano":0},"day":1},"type":"PAY","amount":200.0}
function createRecentTransactions(index, transaction) {
    var transactionHour = checkTime(transaction.date.time.hour);
    var transactionMinute = checkTime(transaction.date.time.minute);
    var img = checkTransactionType(transaction.type);

    var newTransaction = $("<li class='list-group-item transaction'>")
        .append(
            $("<div>")
                .append(
                    $("<h6 class='transaction-date'>")
                        .text("Day " + transaction.date.day + "\n" + transactionHour + ":" + transactionMinute)
                        .append("<br>")
                        .append(
                            $("<img class='transaction-type'>")
                                .attr("src", img)
                        ).append(
                            $("<small class='text-muted transaction-amount'>")
                                .text(" $" + transaction.amount)
                        )
                )
        );

    $(".last-transactions").append(newTransaction);
}