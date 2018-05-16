/*
 * players.js for use in players.html
 * Sean Gallagher & David White
 * CS 257
 */

initialize();

function initialize() {
    createOrderedPlayerTable('id','ascend');
}

/*Get API URL*/
function getBaseURL() {
    var baseURL = window.location.protocol + '//' + window.location.hostname + ':' + api_port;
    return baseURL;
}

/*Get Website base URL*/
function getHostURL() {
    var hostURL = window.location.protocol + '//' + window.location.hostname + ':' + host_port;
    return hostURL;
}

/*Create a re-orderable table of every player and their stats*/
function createOrderedPlayerTable(order_stat,order_direction='descend') {
    var url = getBaseURL() + '/players?stat=' + order_stat + '&order=' + order_direction;
    fetch(url, {method: 'get'})
    .then((response) => response.json())

    .then(function(playersList) {
        // Build the table body
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
            //Tag for zebra table styling.
            if (k%2 == 1) {
                tableBody += '<tr class="odd">'
            } else {
                tableBody += '<tr>';
            }
            tableBody += '<td><a href=\"' + getHostURL() + '/player/' + playersList[k]['id'] + '\">'
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

        // Put into table
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



