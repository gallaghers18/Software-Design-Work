/*
 * teams.js for use in team.html
 * Sean Gallagher & David White
 * CS 257
 */

initialize();

function initialize() {
    createOrderedTeamTable('id', 'ascend');
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

/*Creates a re-orderable table of every team and some stats*/
function createOrderedTeamTable(order_stat, order_direction='descend') {
    var url = getBaseURL() + '/teams?stat=' + order_stat + '&order=' + order_direction;
    fetch(url, {method: 'get'})
    .then((response) => response.json())

    .then(function(teamsList) {
        // Build the table body
        var tableBody = '';
        tableBody += '<tr>';
        tableBody += '<th><a onclick="createOrderedTeamTable(\'team_name\',\'ascend\')">Name</a></th>';
        tableBody += '<th><a onclick="createOrderedTeamTable(\'points\')">Points</a></th>';
        tableBody += '<th><a onclick="createOrderedTeamTable(\'wins\')">Wins</a></th>';
        tableBody += '<th><a onclick="createOrderedTeamTable(\'losses\')">Losses</a></th>';
        tableBody += '<th><a onclick="createOrderedTeamTable(\'ot_losses\')">OT Losses</a></th>';
        tableBody += '<th><a onclick="createOrderedTeamTable(\'percent\')">Points Percentage</a></th>';
        tableBody += '<th><a onclick="createOrderedTeamTable(\'goals_for\')">Goals For</a></th>';
        tableBody += '<th><a onclick="createOrderedTeamTable(\'goals_against\')">Goals Against</a></th>';
        tableBody += '</tr>';
        for (var k = 0; k < teamsList.length; k++) {
            //Add tag for zebra style table
            if (k%2 == 1) {
                tableBody += '<tr class="odd">'
            } else {
                tableBody += '<tr>';
            }

            tableBody += '<td><a href="'+ getHostURL() +'/team/'+ teamsList[k]['id'] + '">' + teamsList[k]['team_name'] + '</a></td>';
            tableBody += '<td>' + teamsList[k]['points'] + '</td>';
            tableBody += '<td>' + teamsList[k]['wins'] + '</td>';
            tableBody += '<td>' + teamsList[k]['losses'] + '</td>';
            tableBody += '<td>' + teamsList[k]['ot_losses'] + '</td>';
            tableBody += '<td>' + teamsList[k]['percent'] + '</td>';
            tableBody += '<td>' + teamsList[k]['goals_for'] + '</td>';
            tableBody += '<td>' + teamsList[k]['goals_against'] + '</td>';
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


