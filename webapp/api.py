#!/usr/bin/env python3
'''
    api.py
    Sean Gallagher & David White, May 3, 2018
    Adapted from example_flask_app.py by Jeff Ondich
'''
import sys
import flask
import json
import psycopg2
from config import password
from config import database
from config import user

app = flask.Flask(__name__)

def connect():
    '''Connects to the database'''
    try:
        connection = psycopg2.connect(database=database, user=user, password=password)
    except Exception as e:
        print(e)
        exit()
    return connection

def query(connection,query):
    '''Returns a cursor over the results for a given query.'''
    try:
        cursor = connection.cursor()
        cursor.execute(query)
    except Exception as e:
        print(e)
        exit()
    return cursor

def pack_json(cursor):
    '''Packs all rows of the query result into a json format.'''
    output = []
    for row in cursor:
        row_json={}
        col_num = 0
        for col_name in cursor.description:
            row_json[col_name[0]]=row[col_num]
            col_num+=1
        output.append(row_json)
    if(len(output)==1):
        return output[0]
    return output

@app.route('/')
def hello():
    return 'Go to a more interesting page! (You know the endpoints...)'


@app.route('/team/<team_id>')
def get_team(team_id):
    ''' Returns a list containing each matching team. Each team is a dictionary with  
        (I)team stats dictionary and (II)list of each player dictionary containing their stats'''
    connection=connect()
    team_dict = {}

    #Team stats query and dictionary construction
    cursor = query(connection,'SELECT * FROM teams WHERE id={0} LIMIT 1'.format(team_id))
    team_dict['team_stats'] = pack_json(cursor)
    
    #Players query and list construction
    cursor = query(connection, 'SELECT players.* FROM players, teams, player_team WHERE teams.id = {0} AND teams.id = player_team.team_id AND players.id = player_team.player_id AND players.team_code = player_team.team_code;'.format(team_id))
    team_dict['player_list'] = pack_json(cursor)

    connection.close()
    return json.dumps(team_dict)



@app.route('/teams')
def get_teams():
    ''' Returns a list of all the teams, each team conisting off some season statistics '''
    stat_arg = flask.request.args.get('stat')
    stat = 'id'
    if stat_arg in ['wins','losses','percent','ot_losses','points','goals_for','team_name','id','goals_against']:
        stat = stat_arg
        
    limit_arg = flask.request.args.get('limit')
    limit = None
    try:
        limit = int(limit_arg)
    except:
        limit = 91897
    
    order_arg = flask.request.args.get('order')
    order = 'DESC'
    if order_arg == 'ascend':
        order = ''

    connection=connect()
    
    cursor=query(connection,'SELECT * FROM teams ORDER BY {0} {1} LIMIT {2}'.format(stat, order, limit))
    team_list=pack_json(cursor)

    connection.close()
    return json.dumps(team_list)

@app.after_request
def set_headers(response):
    response.headers['Access-Control-Allow-Origin'] = '*'
    return response

@app.route('/player/<player_id>')
def get_player(player_id):
    ''' Return a dictionary containing the stats for a given player '''
    connection=connect()

    cursor = query(connection,'SELECT * FROM players WHERE id = {0} ORDER BY played DESC LIMIT 1'.format(player_id))
    player_list=pack_json(cursor)

    connection.close()
    return json.dumps(player_list)



@app.route('/players')
def get_top_players():
    ''' Return a list of the player profiles (dictionary with all stats) of the top 25 in a given stat'''
    stat_arg = flask.request.args.get('stat')
    stat = 'id'
    if stat_arg in ['player_name','played','age','plus_minus','assists','goals','position','team_code','id','points']:
        stat = stat_arg
        
    limit_arg = flask.request.args.get('limit')
    limit = None
    try:
        limit = int(limit_arg)
    except:
        limit = 91897
    
    order_arg = flask.request.args.get('order')
    order = 'DESC'
    if order_arg == 'ascend':
        order = ''

    connection=connect()

    cursor=query(connection,"SELECT a.* FROM players a INNER JOIN (SELECT id, MAX(played) played FROM players GROUP BY id) b ON a.id = b.id AND a.played=b.played ORDER BY {0} {1} LIMIT {2}".format(stat, order, limit))
    player_list=pack_json(cursor)
    
    connection.close()
    
    return json.dumps(player_list)
    


if __name__ == '__main__':
    if len(sys.argv) != 3:
        print('Usage: {0} host port'.format(sys.argv[0]))
        print('  Example: {0} perlman.mathcs.carleton.edu 5101'.format(sys.argv[0]))
        exit()
    
    host = sys.argv[1]
    port = int(sys.argv[2])
    app.run(host=host, port=port, debug=True)
