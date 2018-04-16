'''
    api_test.py
    Original by Jeff Ondich, 11 April 2016
    Heavily revised by Sean Gallagher, 15 April 2018

    An example for CS 257 Software Design. How to retrieve results
    from an HTTP-based API, parse the results (JSON in this case),
    and manage the potential errors.
    
    NOTE FOR USER: Requires that "requests" be installed (python 3 version).
                   I had to use this to work around a recurring error with
                   connecting to the API.
'''

import sys
import argparse
import json
import requests


def get_character_features(searchName):
    '''
    Returns a list of dictionaries, each of which contains {name, hair color, eye color, birth year},
    where each dictionary corresponds to a single character that is matched by the searchName.
    Hence for broad search terms, multiple character descriptions may be returned.

    Raises exceptions on network connection errors and on data
    format errors.
    '''
    base_url = 'https://swapi.co/api/people/?search={0}'
    url = base_url.format(searchName)
    data = requests.get(url)
    character_list = json.loads(data.content)["results"]
    result_list = []
    for character_dict in character_list:
        name = character_dict['name']
        hair_color = character_dict['hair_color']
        eye_color = character_dict['eye_color']
        birth_year = character_dict['birth_year']
        

        if type(name) != type(''):
            raise Exception('name has wrong type: "{0}"'.format(name))
        if type(hair_color) != type(''):
            raise Exception('Hair color has wrong type: "{0}"'.format(hair_color))
        if type(eye_color) != type(''):
            raise Exception('Eye color has wrong type: "{0}"'.format(eye_color))
        if type(birth_year) != type(''):
            raise Exception('Birth year has wrong type: "{0}"'.format(birth_year))
            
        result_list.append({'name':name, 'hair_color':hair_color, 'eye_color':eye_color, 'birth_year':birth_year})
        
    return result_list


def get_film_characters(searchName):
    '''
    Returns a list containing a dictionary for each film that matches the
    searchName. Each dictionary contains {film name, character list} (in 
    which the character list contains the url pointing to each character
    in that film)

    Raises exceptions on network connection errors and on data
    format errors.
    '''
    base_url = 'https://swapi.co/api/films/?search={0}'
    url = base_url.format(searchName)
    data = requests.get(url)
    film_list = json.loads(data.content)["results"]
    result_list = []
    for film_dict in film_list:
        film_name = film_dict['title']
        character_list = film_dict['characters']
        
        if type(film_name) != type(''):
            raise Exception('Film name has wrong type: "{0}"'.format(film_name))
        if type(character_list) != type([]):
            raise Exception('Film name has wrong type: "{0}"'.format(character_list))
            
        result_list.append({'film_name':film_name, 'character_list':character_list})
        
    return result_list


def get_char_name_direct(url):
    data = requests.get(url)
    character = json.loads(data.content)
    
    return character['name']
    

def main(args):
    '''
    Depending on the action selected either 
    (I) Prints a sentence describing the hair color, eye color, and birth year of each matching character.
    (II) Prints the name of every character in the matching movie(s).
    '''
    if args.action == 'features':
        character_list = get_character_features(args.name)
        for character in character_list:
            name = character['name']
            hair_color = character['hair_color']
            eye_color = character['eye_color']
            birth_year = character['birth_year']
            if hair_color != 'n/a' and hair_color !='none':
                print('{0} has {1} hair, {2} eyes, and was born in the year {3} \n'.format(name, hair_color, eye_color, birth_year))
            else:
                print('{0} has no hair, but has {1} eyes, and was born in the year {2} \n'.format(name, eye_color, birth_year))
  
    elif args.action == 'films':
        film_list = get_film_characters(args.name)
        for film in film_list:
            film_name = film['film_name']
            character_list = film['character_list']
            print('The film: {0} has the following characters: '.format(film_name))
            for character_url in character_list:
                print(get_char_name_direct(character_url))
            print('\n')
            
    
    
if __name__ == '__main__':
    
    parser = argparse.ArgumentParser(description='Get character info from SWAPI (Star Wars API)')

    parser.add_argument('action',
                        metavar='action',
                        help='action to perform on the character ("features" or "films")',
                        choices=['features', 'films'])

    parser.add_argument('name', help='the name of the character/film that you want to know about')

    args = parser.parse_args()
    main(args)