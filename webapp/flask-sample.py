#!/usr/bin/env python3
'''
    example_flask_app.py
    Jeff Ondich, 25 April 2018
    A slightly more complicated Flask sample app than the
    "hello world" app found at http://flask.pocoo.org/.
'''
import sys
import flask
import json
import psycopg2
from config import password
from config import database
from config import user

#log into database


app = flask.Flask(__name__)

@app.route('/')
def hello():
    return 'Go to a more interesting page! (You know the endpoints...)'

@app.route('/team/<team_id>')
def get_team(team_id):
    ''' Returns the first matching actor, or an empty dictionary if there's no match. '''
    try:
        connection = psycopg2.connect(database=database, user=user, password=password)
    except Exception as e:
        print(e)
        exit()
    
    try:
        cursor = connection.cursor()
        query = 'SELECT * FROM teams WHERE id = {0}'.format(team_id)
        cursor.execute(query)
    except Exception as e:
        print(e)
        exit()

    # We have a cursor now. Iterate over its rows to print the results.
    for row in cursor:
        print(row)
        
    connection.close()
    
    return None

@app.route('/player/<player_id>')
def get_player(player_id):
    ''' Returns the list of movies that match thes (optional) GET parameters:
          start_year, int: reject any movie released earlier than this year
          end_year, int: reject any movie released later than this year
          genre: reject any movie whose genre does not match this genre exactly
        If a GET parameter is absent, then any movie is treated as though
        it meets the corresponding constraint. For example:
            /movies?start_year=1970
        returns all movies made in 1970 or later, regardless of genre.
    '''
    movie_list = []
    genre = flask.request.args.get('genre')
    start_year = flask.request.args.get('start_year', default=0, type=int)
    end_year = flask.request.args.get('end_year', default=10000, type=int)
    print('genre: {0}, start_year: {1}, end_year: {2}'.format(genre, start_year, end_year))

    for movie in movies:
        if genre is not None and genre != movie['genre']:
            continue
        if movie['year'] < start_year:
            continue
        if movie['year'] > end_year:
            continue
        movie_list.append(movie)

    return json.dumps(movie_list)

@app.route('/stat/<stat_name>')
def get_top_25(stat_name):
    
    
    return None



if __name__ == '__main__':
    if len(sys.argv) != 3:
        print('Usage: {0} host port'.format(sys.argv[0]))
        print('  Example: {0} perlman.mathcs.carleton.edu 5101'.format(sys.argv[0]))
        exit()
    
    host = sys.argv[1]
    port = int(sys.argv[2])
    app.run(host=host, port=port, debug=True)