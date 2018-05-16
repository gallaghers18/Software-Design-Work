/*
 * home.js for use in index.html
 * Sean Gallagher & David White
 * CS 257
 */

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


function getSearchResults(text_id) {
    var search_string = document.getElementById('search_text'+text_id).value;
    searchPlayer(search_string, text_id);
}


function searchPlayer(name, table_id) {
    var url = getBaseURL() + '/name/' + name;
    
    fetch(url, {method: 'get'})

    .then((response) => response.json())

    .then(function(playersList) {
        if (!Array.isArray(playersList)) {
        var tempArray = [];
        tempArray.push(playersList);
        playersList = tempArray;
        }

        var tableBody = '';
        tableBody += '<tr><th>Player</th></tr>';
        for (var k = 0; k < playersList.length; k++) {
            tableBody += '<tr>';
            tableBody += '<td><button onclick="createPlayerCompTable('+playersList[k]['id']+','+table_id+')">' + playersList[k]['player_name'] + '</button></td>';
            tableBody += '</tr>';
        } 
        var resultsTableElement = document.getElementById('results_table'+table_id);
        if (resultsTableElement) {
            resultsTableElement.innerHTML = tableBody;
        }
        return;
    })
    
    .catch(function(error) {
        console.log(error);
    });
    
}


function createPlayerCompTable(player_id, table_id) {
    var url = getBaseURL() + '/player/' + player_id;

    fetch(url, {method: 'get'})
    
    .then((response) => response.json())

    .then(function(player) {
        // Build the table body.
        var tableBody = '';
        tableBody += '<tr><th>Player</th><td>' + player['player_name'] + '</td></tr>';
        tableBody += '<tr><th>Position</th><td>' + player['position'] + '</td></tr>';
        tableBody += '<tr><th>Team</th><td>' + player['team_code'] + '</td></tr>';
        tableBody += '<tr><th>Age</th><td>' + player['age'] + '</td></tr>';
        tableBody += '<tr><th>Games Played</th><td>' + player['played'] + '</td></tr>';
        tableBody += '<tr><th>Goals</th><td>' + player['goals'] + '</td></tr>';
        tableBody += '<tr><th>Assists</th><td>' + player['assists'] + '</td></tr>';
        tableBody += '<tr><th>Points</th><td>' + player['points'] + '</td></tr>';
        tableBody += '<tr><th>+/-</th><td>' + player['plus_minus'] + '</td></tr>';
        
        // Put the table body we just built inside the table that's already on the page.
        var resultsTableElement = document.getElementById('results_table'+table_id);
        if (resultsTableElement) {
            resultsTableElement.innerHTML = tableBody;
        }
        colorTable();
        return;
    })
    
    // Log the error if anything went wrong during the fetch.
    .catch(function(error) {
        console.log(error);
    });
    
    
}


function isNumber(n) {
      return !isNaN(parseFloat(n)) && isFinite(n);
}



function colorTable() {
    var table1 = document.getElementById('results_table1').getElementsByTagName('td');
    
    var table2 = document.getElementById('results_table2').getElementsByTagName('td');
    
    var i=0;
    while(i<table1.length && i<table2.length) {
        var num1=table1[i].innerHTML;
        var num2=table2[i].innerHTML;
        if(isNumber(num1) && isNumber(num2)){
            if(parseFloat(num1)>parseFloat(num2)){
                table1[i].style.background = "#aaffaa";
                table2[i].style.background = "#ffaaaa"
            }
            if(parseFloat(num2)>parseFloat(num1)){
                table1[i].style.background = "#ffaaaa";
                table2[i].style.background = "#aaffaa";
            }
        }
        i++;
    }return;
    
    
}





