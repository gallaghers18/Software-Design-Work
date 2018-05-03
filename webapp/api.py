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

@app.route('/')
def hello():
    return 'Go to a more interesting page! (You know the endpoints...)'

@app.route('/team/<team_id>')
def get_team(team_id):
    ''' Returns a list containing each matching team. Each team is a dictionary with  
        (I)team stats and (II)a list of each player with their stats'''

    try:
        connection = psycopg2.connect(database=database, user=user, password=password)
    except Exception as e:
        print(e)
        exit()
    
    team_dict = {}
    
    try:
        cursor = connection.cursor()
        query = 'SELECT * FROM teams WHERE id={0}'.format(team_id)
        cursor.execute(query)
    except Exception as e:
        print(e)
        exit()
        
    team_stats = {}
    col_num=0
    team_info_row = cursor.fetchone()
    for col_name in cursor.description:
        team_stats[col_name[0]]=team_info_row[col_num]
        col_num+=1
    team_dict['team_stats'] = team_stats
    
    try:
        cursor = connection.cursor()
        query = 'SELECT players.* FROM players, teams, player_team WHERE teams.id = {0} AND teams.id = player_team.team_id AND players.id = player_team.player_id AND players.team_code = player_team.team_code;'.format(team_id)
        cursor.execute(query)
    except Exception as e:
        print(e)
        exit()

    player_list =[]
    for row in cursor:
        player = {}
        col_num=0
        for col_name in cursor.description:
            player[col_name[0]]=row[col_num]
            col_num+=1
        player_list.append(player)
        
    team_dict['player_list'] = player_list
    
        
    connection.close()
    
    return json.dumps(team_dict)


@app.route('/team')
def get_all_teams():
    ''' Returns a list of all the teams, each team conisting off some season statistics '''
    try:
        connection = psycopg2.connect(database=database, user=user, password=password)
    except Exception as e:
        print(e)
        exit()
    
    try:
        cursor = connection.cursor()
        query = 'SELECT * FROM teams'
        cursor.execute(query)
    except Exception as e:
        print(e)
        exit()

    team_list=[]
    for row in cursor:
        team = {}
        col_num=0
        for col_name in cursor.description:
            team[col_name[0]]=row[col_num]
            col_num+=1
        team_list.append(team)
        
    connection.close()
    
    return json.dumps(team_list)



@app.route('/player/<player_id>')
def get_player(player_id):
    try:
        connection = psycopg2.connect(database=database, user=user, password=password)
    except Exception as e:
        print(e)
        exit()
    
    try:
        cursor = connection.cursor()
        query = 'SELECT * FROM players WHERE id = {0} ORDER BY played DESC LIMIT 1'.format(player_id)
        cursor.execute(query)
    except Exception as e:
        print(e)
        exit()

    player_list=[]
    for row in cursor:
        player = {}
        col_num=0
        for col_name in cursor.description:
            player[col_name[0]]=row[col_num]
            col_num+=1
        player_list.append(player)
        
    connection.close()
    
    return json.dumps(player_list)


@app.route('/stat/<stat_name>')
def get_top_25(stat_name):
    try:
        connection = psycopg2.connect(database=database, user=user, password=password)
    except Exception as e:
        print(e)
        exit()
    
    try:
        cursor = connection.cursor()
        query = 'SELECT * FROM players ORDER BY {0} DESC LIMIT 25'.format(stat_name)
        cursor.execute(query)
    except Exception as e:
        print(e)
        exit()

    player_list=[]
    for row in cursor:
        player = {}
        col_num=0
        for col_name in cursor.description:
            player[col_name[0]]=row[col_num]
            col_num+=1
        player_list.append(player)
        
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