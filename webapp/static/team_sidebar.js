/*
 * team_sidebar.js for use in every html page
 * Sean Gallagher & David White
 * CS 257
 */

initialize();

function initialize() {
    addSidebar();
    addTopbar();
    createSidebarTeamTable('team_name', 'ascend');
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

function addSidebar() {
    document.body.innerHTML = "<div class=\"sidenav\" id=\"teams\"></div>"+ document.body.innerHTML
}

/*Creates a top bar with links to Home, Players (list of all players), and Teams (list of all teams)*/
function addTopbar() {
    document.body.innerHTML = "<div class=\"topnav\">"
        +"<a href=\""+getHostURL()+"\">Home</a>"
        +"<a href=\""+getHostURL()+"/players"+"\">Players</a>"
        +"<a href=\""+getHostURL()+"/teams"+"\">Teams</a>"
        +"</div>"+document.body.innerHTML
}

/*Creates a sidebar with each team as buttons that link to specific team page*/
function createSidebarTeamTable(order_stat, order_direction='descend') {
    var url = getBaseURL() + '/teams?stat=' + order_stat + '&order=' + order_direction;
    fetch(url, {method: 'get'})
    .then((response) => response.json())
    
    //Build table body
    .then(function(teamsList) {
        var tableBody = '';
        for (var k = 0; k < teamsList.length; k++) {
            tableBody += '<button onclick="window.location=\''+getHostURL()+'/team/'+teamsList[k]['id']+'\';">'
                            + teamsList[k]['team_name'] + '</button>';
        }

        //Put into table on page
        var resultsTableElement = document.getElementById('teams');
        if (resultsTableElement) {
            resultsTableElement.innerHTML = tableBody;
        }
    })

    // Log the error if anything went wrong during the fetch.
    .catch(function(error) {
        console.log(error);
    });
}

