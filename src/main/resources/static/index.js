const appUrl = './gif-finance/';

function loadCurrencies() {
    $.ajax({
        url: appUrl + 'get-currencies',
        method: 'GET',
        complete: function (data) {
            let codes = JSON.parse(data.responseText);
            let select = document.querySelector("#codes_curr")
            select.innerHTML = ''
            for (let i = 0; i < codes.length; i++) {
                let option = document.createElement("option")
                option.value = codes[i]
                option.text = codes[i]
                select.insertAdjacentElement("beforeend", option);
            }
        }
    })
}

function loadGif() {
    let currency = $("#codes_curr").val();
    $.ajax({
        url: appUrl + 'get-gif/' + currency,
        method: 'GET',
        dataType: "json",
        complete: function (data) {
            cleanPrevious();
            let content = JSON.parse(data.responseText)
            let img = document.createElement("img")
            img.className = "imgGif"
            let gifKey = content.tag
            console.log(content)
            let currencyCompare = document.createElement("p")
            currencyCompare.className = "gifText"
            img.src = content.data.images.original.url;
            switch (gifKey) {
                case "rich":
                    currencyCompare.textContent = currency + ' to USD:stonks'
                    break;
                case "broke":
                    currencyCompare.textContent = currency + ' to USD:not stonks'
                    break;
                case "no-difference":
                    currencyCompare.textContent = currency + ' to USD:no difference'
                    break;
                default:
                    currencyCompare.textContent = "error:("
                    break;

            }
            let gifContent = document.querySelector(".gif-content")
            gifContent.insertAdjacentElement("afterbegin", currencyCompare);
            gifContent.insertAdjacentElement("afterbegin", img);
        }
    })
}

function cleanPrevious() {
    let gifContent = document.querySelector(".gif-content")
    gifContent.innerHTML = '';
}
