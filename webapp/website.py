#!/usr/bin/env python3
'''
    website.py
    Sean Gallagher & David White
    Flask app for CS 257 Spring 2018 Hockey Website
'''
import sys
import flask

app = flask.Flask(__name__, static_folder='static', template_folder='templates')

@app.route('/') 
def get_main_page():
    global api_port
    global port
    return flask.render_template('index.html', api_port=api_port, host_port=port)

@app.route('/teams')
def get_teams_page():
   return flask.render_template('teams.html', api_port=api_port, host_port=port)

@app.route('/players')
def get_players_page():
   return flask.render_template('players.html', api_port=api_port, host_port=port)

@app.route('/team/<team_id>')
def get_team_page(team_id):
  return flask.render_template('team.html', team_id=team_id, api_port=api_port, host_port=port)

@app.route('/player/<player_id>')
def get_player_page(player_id):
    return flask.render_template('player.html', player_id=player_id, api_port=api_port, host_port=port)

if __name__ == '__main__':
    host = sys.argv[1]
    port = sys.argv[2]
    api_port = sys.argv[3]
    app.run(host=host, port=port)
    
