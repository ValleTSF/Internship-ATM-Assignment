// With this function I take the value from the html buttons and put them in the "money to withdraw" input field.
function MoneyToInputField(amount) {
    let field = $("#moneyToWithdraw");

    if (field.val().length > 1) {
        let totalValue = parseInt(field.val()) + parseInt(amount);
        field.val(totalValue)
    } else {
        field.val(amount);
    }

}

// With this method I clear the "money to withdraw" input field.
function Clear() {
    $("#moneyToWithdraw").val(0);
}


function Withdraw() {
    let responseFromATM = document.getElementById("responseFromATM");
    let amount = parseInt($("#moneyToWithdraw").val());
    let hundredBillAmount = 0;
    let fiveHundredBillAmount = 0;
    let thousandBillAmount = 0;

        // I use ajax to execute a post request to my application.
    $.ajax({
        // The url is the same as in the ATMController class where the post method resides.
        url: `/ATM/1/withdraw/` + amount,
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (result) {
            responseFromATM.innerHTML = "";
            responseFromATM.innerHTML = "Success! You withdrew ";
            result.forEach(element => {
                if (parseInt(element.value) === 100) {
                    hundredBillAmount += 1;
                } else if (element.value === 500)
                    fiveHundredBillAmount += 1;
                else if (element.value === 1000)
                    thousandBillAmount += 1;
            });
            if (hundredBillAmount > 0) {
                responseFromATM.innerHTML += hundredBillAmount + "x Hundred Bills ";
            }
            if (fiveHundredBillAmount > 0) {
                responseFromATM.innerHTML += fiveHundredBillAmount + "x Five hundred Bills ";

            }
            if (thousandBillAmount > 0) {
                responseFromATM.innerHTML += thousandBillAmount + "x Thousand Bills ";

            }
        }, // If the post request could not be completed I run this function.
        error: function () {
            alert('Error - could not finish the transaction');
        }

    })
}

