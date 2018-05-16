/*
 * team.js for use in team.html
 * Sean Gallagher & David White
 * CS 257
 */

initialize(team_id);

function initialize() {
    createOrderedPlayerTable(team_id,"id","ascend");
}

function getBaseURL() {
    var baseURL = window.location.protocol + '//' + window.location.hostname + ':' + api_port;
    return baseURL;
}

function getHostURL() {
    var hostURL = window.location.protocol + '//' + window.location.hostname + ':' + host_port;
    return hostURL;
}

function createOrderedPlayerTable(team_id,stat='id',order='descend') {
    var url = getBaseURL() + '/team/' + team_id + "?stat="+stat+"&order="+order;
    // Send the request to the Books API /authors/ endpoint
    fetch(url, {method: 'get'})

    // When the results come back, transform them from JSON string into
    // a Javascript object (in this case, a list of author dictionaries).
    .then((response) => response.json())

    // Once you have your list of author dictionaries, use it to build
    // an HTML table displaying the author names and lifespan.
    .then(function(teamDictionary) {
        var teamName=teamDictionary['team_stats']['team_name'];
        var TeamNameElement = document.getElementById('team_name');
        if (TeamNameElement) {
            TeamNameElement.innerHTML = teamName;
        }
        
        var teamsList = teamDictionary['team_stats'];
        var teamStatsBody = '';
        teamStatsBody += '<tr>';
        teamStatsBody += '<th>Points</th>';
        teamStatsBody += '<th>Wins</th>';
        teamStatsBody += '<th>Losses</th>';
        teamStatsBody += '<th>OT Losses</th>';
        teamStatsBody += '<th>Points Percentage</th>';
        teamStatsBody += '<th>Goals For</th>';
        teamStatsBody += '<th>Goals Against</th>';
        teamStatsBody += '</tr>';
        
            teamStatsBody += '<tr>';
            teamStatsBody += '<td>' + teamsList['points'] + '</td>';
            teamStatsBody += '<td>' + teamsList['wins'] + '</td>';
            teamStatsBody += '<td>' + teamsList['losses'] + '</td>';
            teamStatsBody += '<td>' + teamsList['ot_losses'] + '</td>';
            teamStatsBody += '<td>' + teamsList['percent'] + '</td>';
            teamStatsBody += '<td>' + teamsList['goals_for'] + '</td>';
            teamStatsBody += '<td>' + teamsList['goals_against'] + '</td>';
            teamStatsBody += '</tr>';
        

        // Put the table body we just built inside the table that's already on the page.
        var teamsTableElement = document.getElementById('team_stats');
        if (teamsTableElement) {
            teamsTableElement.innerHTML = teamStatsBody;
        }
        
        
        
        
        // Build the table body.
        var tableBody = '';
        tableBody += '<tr>';
        tableBody += '<th><a onclick="createOrderedPlayerTable('+team_id+',\'id\',\'ascend\')">Player</a></th>';
        tableBody += '<th><a onclick="createOrderedPlayerTable('+team_id+',\'age\')">Age</a></th>';
        tableBody += '<th><a onclick="createOrderedPlayerTable('+team_id+',\'position\',\'ascend\')">Position</a></th>';
        tableBody += '<th><a onclick="createOrderedPlayerTable('+team_id+',\'team_code\',\'ascend\')">Team</a></th>';
        tableBody += '<th><a onclick="createOrderedPlayerTable('+team_id+',\'played\')">Games Played</a></th>';
        tableBody += '<th><a onclick="createOrderedPlayerTable('+team_id+',\'goals\')">Goals</a></th>';
        tableBody += '<th><a onclick="createOrderedPlayerTable('+team_id+',\'assists\')">Assists</a></th>';
        tableBody += '<th><a onclick="createOrderedPlayerTable('+team_id+',\'points\')">Points</a></th>';
        tableBody += '<th><a onclick="createOrderedPlayerTable('+team_id+',\'plus_minus\')">+/-</a></th>';
        tableBody += '</tr>';
        var playersList=teamDictionary['player_list'];
        for (var k = 0; k < playersList.length; k++) {
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

        // Put the table body we just built inside the table that's already on the page.
        var playersTableElement = document.getElementById('team_players');
        if (playersTableElement) {
            playersTableElement.innerHTML = tableBody;
        }
    })

    // Log the error if anything went wrong during the fetch.
    .catch(function(error) {
        console.log(error);
    });
}


