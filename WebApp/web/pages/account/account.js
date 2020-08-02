$(getUser(createUserTable));

// user = {ID: "" ; username: "" ; balance: "" ; transactionHistory: {date: "{}" ; type: "" ; transactionAmount: ""; amountBefore; amountAfter}}
function createUserTable(user) {
    var transactions = user.transactionHistory;
    $.each(transactions || [], addTransactionToTransactionsTable);
}

// transaction = {"date":{"time":{"hour":0,"minute":0,"second":0,"nano":0},"day":1},"type":"PAY","amount":200.0}
function addTransactionToTransactionsTable(index, transaction) {
    var transactionHour = checkTime(transaction.date.time.hour);
    var transactionMinute = checkTime(transaction.date.time.minute);
    var img = checkTransactionType(transaction.type);

    var newTransaction = $("<tr class='transaction'>")
        .append(
            $("<td class='transaction-type'>")
                .append(
                    $("<img alt='Trasaction type'>")
                        .attr("src", img)
                )
        ).append(
            $("<td class ='transaction-date'>")
                .text("Day " + transaction.date.day + " at " + transactionHour + ":" + transactionMinute)
        ).append(
            $("<td class='transaction-amount'>")
                .text("$" + transaction.transactionAmount)
        ).append(
            $("<td class='amount-before'>")
                .text("$" + transaction.amountBefore)
        ).append(
            $("<td class='amount-after'>")
                .text("$" + transaction.amountAfter)
        );

    $("tbody.transactions-data").append(newTransaction);
}