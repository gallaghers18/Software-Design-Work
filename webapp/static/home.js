/*
 * home.js for use in index.html
 * Sean Gallagher & David White
 * CS 257
 */

initialize();

function initialize() {
    
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

/*Grabs the search string, and runs the search function on it*/
function getSearchResults(text_id) {
    var search_string = document.getElementById('search_text'+text_id).value;
    searchPlayer(search_string, text_id);
}

/*Fills in the table with buttons for possible search matches*/
function searchPlayer(name, table_id) {
    var url = getBaseURL() + '/name/' + name;
    //Queries endpoint returning names like the searchstring
    
    fetch(url, {method: 'get'})

    .then((response) => response.json())

    .then(function(playersList) {
        if (!Array.isArray(playersList)) {
        var tempArray = [];
        tempArray.push(playersList);
        playersList = tempArray;
        }
        
        //Fills table body for table #id with a button for each potential search result.
        var tableBody = '';
        tableBody += '<tr><th>Player</th></tr>';
        for (var k = 0; k < playersList.length; k++) {
            //Zebra Pattern
            if (k%2 == 1) {
                tableBody += '<tr class="odd">'
            } else {
                tableBody += '<tr>';
            }
            tableBody += '<td><a onclick="createPlayerCompTable('+playersList[k]['id']+','+table_id+')">' + playersList[k]['player_name'] + '</a></td>';
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

/*Fills in table #id with the stats of the player player_id*/
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
        // Color the table (this does nothing if there aren't two players selected)
        colorTable();
        return;
    })
    
    // Log the error if anything went wrong during the fetch.
    .catch(function(error) {
        console.log(error);
    });
    
    
}

/*Borrowed from Stack Overflow. Pretty self-explanatory. */
function isNumber(n) {
      return !isNaN(parseFloat(n)) && isFinite(n);
}


/*Compares and colors the table if two players have been selected.*/
function colorTable() {
    var table1 = document.getElementById('results_table1').getElementsByTagName('td');
    
    var table2 = document.getElementById('results_table2').getElementsByTagName('td');
    
    var i=0;
    //Loop through both tables as far as possible.
    while(i<table1.length && i<table2.length) {
        var num1=table1[i].innerHTML;
        var num2=table2[i].innerHTML;
        
        //Color larger number green and smaller number red (if they're numbers)
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





