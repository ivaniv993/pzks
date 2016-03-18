/**
 * Created by iivaniv on 15.07.2015.
 */
(function () {

})();

var userLocation = null,
    geocoder,
    defaults = {
        "location": {
            "latitude": 50.4020355,
            "longitude": 30.5326905
        },
        "zoom": 9
    };


function modAutocompleteAddressInput(input, callback) {
    console.log("modAutocompleteAddressInput")
    geocoder = new google.maps.Geocoder();
    var autocomplete = new google.maps.places.Autocomplete(
        input,
        {types: []}
        //{ types: ['geocode'] }
    )
    google.maps.event.addListener(autocomplete, 'place_changed', function () {
        if (autocomplete.getPlace().geometry != undefined) {
            callback(input, autocomplete.getPlace())
        }
    })

    //fix for correct work with coordinates like 50.447644999, 30.4559055
    var jqinput = $(input)
    jqinput.bind("change", function (e) {
        //costil
        var pat = /[-+]?\d*\.?\d+[,\. ]+[-+]?\d*\.?\d+/
        var address = jqinput.val()
        if (address == address.match(pat)) {
            geocoder.geocode({'address': address}, function (results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    console.log("location field change event")
                    var place = results[0]
                    if (place.geometry != undefined) {
                        callback(input, place)
                    }
                } else {
                    console.log('Geocode was not successful for the following reason: ' + status);
                }
            })
        }
    })
};


function autoCompl() {
    modAutocompleteAddressInput(document.getElementById("search:stock-address"), function () {
        console.log('callback')
    });
}

$(document).ready(function(){
    $(".autoCompl").on('input propertychange', function(){
        modAutocompleteAddressInput(document.getElementById("search:stock-address"), function () {
            console.log('callback')
        });
    })
});
