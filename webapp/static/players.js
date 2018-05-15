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
    createOrderedPlayerTable('id','ascend');
}

function getBaseURL() {
    var baseURL = window.location.protocol + '//' + window.location.hostname + ':' + api_port;
    return baseURL;
}

function getHostURL() {
    var hostURL = window.location.protocol + '//' + window.location.hostname + ':' + host_port;
    return hostURL;
}

function createOrderedPlayerTable(order_stat,order_direction='descend') {
    var url = getBaseURL() + '/players?stat=' + order_stat + '&order=' + order_direction;
    // Send the request to the Books API /authors/ endpoint
    fetch(url, {method: 'get'})

    // When the results come back, transform them from JSON string into
    // a Javascript object (in this case, a list of author dictionaries).
    .then((response) => response.json())

    // Once you have your list of author dictionaries, use it to build
    // an HTML table displaying the author names and lifespan.
    .then(function(playersList) {
        // Build the table body.
        var tableBody = '';
        tableBody += '<tr>';
        tableBody += '<th><a onclick="createOrderedPlayerTable(\'id\',\'ascend\')">Player</a></th>';
        tableBody += '<th><a onclick="createOrderedPlayerTable(\'age\')">Age</a></th>';
        tableBody += '<th><a onclick="createOrderedPlayerTable(\'position\',\'ascend\')">Position</a></th>';
        tableBody += '<th><a onclick="createOrderedPlayerTable(\'team_code\',\'ascend\')">Team</a></th>';
        tableBody += '<th><a onclick="createOrderedPlayerTable(\'played\')">Games Played</a></th>';
        tableBody += '<th><a onclick="createOrderedPlayerTable(\'goals\')">Goals</a></th>';
        tableBody += '<th><a onclick="createOrderedPlayerTable(\'assists\')">Assists</a></th>';
        tableBody += '<th><a onclick="createOrderedPlayerTable(\'points\')">Points</a></th>';
        tableBody += '<th><a onclick="createOrderedPlayerTable(\'plus_minus\')">+/-</a></th>';
        tableBody += '</tr>';
        for (var k = 0; k < playersList.length; k++) {
            tableBody += '<tr>';

            tableBody += '<td><a href=\"' + getHostUrl() + '/player/' + playersList[k]['id'] + '\">'
                            + playersList[k]['player_name'] + '</a></td>';            
            tableBody += '<td>' + playersList[k]['age'] + '</td>';
            tableBody += '<td>' + playersList[k]['position'] + '</td>';
            tableBody += '<td>' + playersList[k]['team_code'] + '</td>';
            tableBody += '<td>' + playersList[k]['played'] + '</td>';
            tableBody += '<td>' + playersList[k]['goals'] + '</td>';
            tableBody += '<td>' + playersList[k]['assists'] + '</td>';
            tableBody += '<td>' + playersList[k]['points'] + '</td>';
            tableBody += '<td>' + playersList[k]['plus_minus'] + '</td>';
            tableBody += '</tr>';
        }

        // Put the table body we just built inside the table that's already on the page.
        var resultsTableElement = document.getElementById('results_table');
        if (resultsTableElement) {
            resultsTableElement.innerHTML = tableBody;
        }
    })

    // Log the error if anything went wrong during the fetch.
    .catch(function(error) {
        console.log(error);
    });
}

function getPlayer(player_id) {
    // Very similar pattern to onAuthorsButtonClicked, so I'm not
    // repeating those comments here. Read through this code
    // and see if it makes sense to you.
    var url = getBaseURL() + '/player/' + playerID;

    fetch(url, {method: 'get'})

    .then((response) => response.json())
    .then(function(playerList) {
        var tableBody = '<tr><th>' + playerName + '</th></tr>';
            tableBody += '<tr>';
            tableBody += '<td>' + playerList['points'] + '-';
            tableBody += playerList['assists']+ '-'+playerList['played'];
            tableBody += '</td>';
            tableBody += '</tr>';

        var resultsTableElement = document.getElementById('results_table');
        if (resultsTableElement) {
            resultsTableElement.innerHTML = tableBody;
        }
    })

    .catch(function(error) {
        console.log(error);
    });
}

