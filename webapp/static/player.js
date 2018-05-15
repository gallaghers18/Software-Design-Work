



initialize(player_id);

function initialize() {
    createPlayerTable(player_id);
}

function getBaseURL() {
    var baseURL = window.location.protocol + '//' + window.location.hostname + ':' + api_port;
    return baseURL;
}

function getHostURL() {
    var hostURL = window.location.protocol + '//' + window.location.hostname + ':' + host_port;
    return hostURL;
}

function createPlayerTable(player_id) {
    var url = getBaseURL() + '/player/' + player_id;

    fetch(url, {method: 'get'})
    
    .then((response) => response.json())

    .then(function(playerDict) {
        // Build the table body.
        var tableBody = '';
        tableBody += '<tr>';
        tableBody += '<th>Player</th>';
        tableBody += '<th>Age</th>';
        tableBody += '<th>Position</th>';
        tableBody += '<th>Team</th>';
        tableBody += '<th>Games Played</th>';
        tableBody += '<th>Goals</th>';
        tableBody += '<th>Assists</th>';
        tableBody += '<th>Points</th>';
        tableBody += '<th>+/-</th>';
        tableBody += '</tr>';
        tableBody += '<tr>';
        tableBody += '<td>' + playerDict['player_name'] + '</td>';
        tableBody += '<td>' + playerDict['age'] + '</td>';
        tableBody += '<td>' + playerDict['position'] + '</td>';
        tableBody += '<td>' + playerDict['team_code'] + '</td>';
        tableBody += '<td>' + playerDict['played'] + '</td>';
        tableBody += '<td>' + playerDict['goals'] + '</td>';
        tableBody += '<td>' + playerDict['assists'] + '</td>';
        tableBody += '<td>' + playerDict['points'] + '</td>';
        tableBody += '<td>' + playerDict['plus_minus'] + '</td>';
        tableBody += '</tr>';
        
        // Put the table body we just built inside the table that's already on the page.
        var resultsTableElement = document.getElementById('player_table');
        if (resultsTableElement) {
            resultsTableElement.innerHTML = tableBody;
        }
        return;
    })
    
    // Log the error if anything went wrong during the fetch.
    .catch(function(error) {
        console.log(error);
    });
    
    
}
