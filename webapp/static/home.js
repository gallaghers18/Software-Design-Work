/*
 * books.js
 * Jeff Ondich, 27 April 2016
 * Updated, 4 May 2018
 *
 * A little bit of Javascript showing one small example of AJAX
 * within the "books and authors" sample for Carleton CS257,
 * Spring Term 2017.
 *
 * This example uses a very simple-minded approach to Javascript
 * program structure, which suffers from the problem of
 * "global namespace pollution". We'll talk more about this after
 * you get a feel for some Javascript basics.
 */

// IMPORTANT CONFIGURATION INFORMATION
// The contents of getBaseURL below reflects our assumption that
// the web application (books_website.py) and the API (books_api.py)
// will be running on the same host but on different ports.
//
// But if you take a look at the contents of getBaseURL, you may
// ask: where does the value of api_port come from? The answer is
// a little bit convoluted. (1) The command-line syntax of
// books_website.py includes an argument for the API port;
// and (2) the index.html Flask/Jinja2 template includes a tiny
// bit of Javascript that declares api_port and assigns that
// command-line API port argument to api_port. This happens
// before books.js is loaded, so the functions in books.js (like
// getBaseURL) can access api_port as needed.

initialize();

function initialize() {
    
}

function getBaseURL() {
    var baseURL = window.location.protocol + '//' + window.location.hostname + ':' + api_port;
    return baseURL;
}

function getHostURL() {
    var hostURL = window.location.protocol + '//' + window.location.hostname + ':' + host_port;
    return hostURL;
}

function getSearchResults(id) {
    var search_string = document.getElementById('search_text'+id).value;
    searchPlayer(search_string, id);
}


function searchPlayer(name, id) {
    var url = getBaseURL() + '/name/' + name;
    
    fetch(url, {method: 'get'})

    .then((response) => response.json())

    .then(function(playersList) {
        var tableBody = '';
        tableBody += '<tr><th>Player</th></tr>';
        for (var k = 0; k < playersList.length; k++) {
            tableBody += '<tr>';
            tableBody += '<td>' + playersList[k]['player_name'] + '</td>';
            tableBody += '</tr>';
        } 
        var resultsTableElement = document.getElementById('results_table'+id);
        if (resultsTableElement) {
            resultsTableElement.innerHTML = tableBody;
        }    
    })
    
    .catch(function(error) {
        console.log(error);
    });

}



