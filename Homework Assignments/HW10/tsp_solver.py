from random import shuffle, randint, random
from math import sqrt, exp
import sys

def simulatedAnnealing(initial_tour):
    theState = list(initial_tour)
    fitnessState = fitness(theState)

    max = 25000
    min = 2.5

    while max > min:
        n1 = randint(0, len(theState) - 1)
        n2 = randint(0, len(theState) - 1)
        neighbor = list(theState)

        neighbor[n1], neighbor[n2] = neighbor[n2], neighbor[n1]
        fit_neighbor = fitness(neighbor) #get the fitness of the neighbor

        if fit_neighbor < fitnessState: #if the neighbor fitness is less than the current fitness state, set it and set the list to our neighbor
            fitnessState = fit_neighbor
            theState = list(neighbor)
        else:
            difference = fit_neighbor - fitnessState

            if difference < 0 or exp(-difference / max) > random():
                fitnessState = fit_neighbor
                theState = list(neighbor)

        max = max * .9999 #control run
        shortestTour = []

        for i in range(len(theState)):
            shortestTour.append(theState[i][0])

    return shortestTour

def distance(city1, city2):
    #   int(round(sqrt((   x1    -    x2   ) * (   x1    -    x2   ) + (   y1    -    y2   ) * (   y1    -    y2   ))))
    dist = int(round(sqrt((city1[1] - city2[1]) * (city1[1] - city2[1]) + (city1[2] - city2[2]) * (city1[2] - city2[2]))))
    return dist

def fitness(tour):
    fit = 0
    for i in range(len(tour)):
        fit += distance(tour[i], tour[i - 1])
    return fit

def parseFile(initial):
    newTour = []
    DIMENSION = 0
    with open(initial, 'r') as f:
        for line in f:
            if line.startswith('DIMENSION : '):
                x, DIMENSIONS = line.split('DIMENSION : ')
                DIMENSION = int(DIMENSIONS)
                break
            elif line.startswith('DIMENSION: '):
                x, DIMENSIONS = line.split('DIMENSION: ')
                DIMENSION = int(DIMENSIONS)
                break

        f.readline()
        f.readline()

        for i in range(DIMENSION):
            city_index, coord1, coord2 = f.readline().split(' ')
            index = int(city_index) - 1
            x1 = float(coord1)
            y1 = float(coord2)
            city = (index, x1, y1)
            newTour.append(city)
    return newTour

def print_shortest_tour(shortestTour):
    for item in shortestTour:
        print(item, end=" ")

if __name__ == "__main__":
    if len(sys.argv) == 1:
        toSolve = input("What is the name of the file to solve?\n")
    else:
        for arg in sys.argv:
            if arg == '-h':
                print('python tsp_solver.py')
                print('This will run the program and ask for a File Name\n')
                print('python tspsolver.py <File Name>')
                print('This will run the program with the specified file\n')
                sys.exit()  # end the program
            else:
                toSolve = sys.argv[1]

    filename = toSolve
    tour = parseFile(filename)

    shortestTour = simulatedAnnealing(tour)
    print(fitness(tour))
    print_shortest_tour(shortestTour)
