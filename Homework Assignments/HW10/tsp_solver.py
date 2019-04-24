###
### @author Daniel W. Anner and Sam Isidoro
###
from random import shuffle, randint, random
from math import sqrt, exp
import sys

# Simulated Annealing Solution
def simulatedAnnealing(initial_tour):
    theState = list(initial_tour) #get our initial state
    fitnessState = fitness(theState) #get the fitness of the initial state
    # Set our max/min's for runtime
    max = 25000
    min = 2.5

    #while the max is greater than the min, keep running
    while max > min:
        n1 = randint(0, len(theState) - 1) #first neighbor
        n2 = randint(0, len(theState) - 1) #second neighbor
        neighbor = list(theState) #get the neighbor list

        neighbor[n1], neighbor[n2] = neighbor[n2], neighbor[n1] #set the values
        fit_neighbor = fitness(neighbor) #get the fitness of the neighbor

        if fit_neighbor < fitnessState: #if the neighbor fitness is less than the current fitness state, set it and set the list to our neighbor
            fitnessState = fit_neighbor #set the fitness state to the neighbors, since the neighbor is more fit
            theState = list(neighbor) #set the sate to that neighbor
        else:
            diff = fit_neighbor - fitnessState #otherwise set the diff to the difference

            if diff < 0 or exp(-diff / max) > random():
                fitnessState = fit_neighbor
                theState = list(neighbor)

        max = max * .9999 #control run, make it slightly smaller
        shortestTour = [] #create blank shortest tour

        for i in range(len(theState)):
            shortestTour.append(theState[i][0]) #append items to the shortest tour

    return shortestTour

def distance(city1, city2):
    #   int(round(sqrt((   x1    -    x2   ) * (   x1    -    x2   ) + (   y1    -    y2   ) * (   y1    -    y2   ))))
    dist = int(round(sqrt((city1[1] - city2[1]) * (city1[1] - city2[1]) + (city1[2] - city2[2]) * (city1[2] - city2[2])))) #provided by the professor
    return dist

def fitness(tour):
    fit = 0
    for i in range(len(tour)):
        fit += distance(tour[i], tour[i - 1]) #simpy calculate the fitness
    return fit

def parseFile(initial):
    newTour = [] #blank tour
    DIMENSION = 0 #empty dimension
    with open(initial, 'r') as f:
        for line in f:
            if line.startswith('DIMENSION : '): #check for our dimension
                x, DIMENSIONS = line.split('DIMENSION : ')
                DIMENSION = int(DIMENSIONS) #save the dimension
                break
            elif line.startswith('DIMENSION: '): #the dj38 is in slightly different format
                x, DIMENSIONS = line.split('DIMENSION: ')
                DIMENSION = int(DIMENSIONS)
                break

        f.readline() #move on 2 lines
        f.readline()

        for i in range(DIMENSION):
            city_index, coord1, coord2 = f.readline().split(' ') #get the 3 values provided
            index = int(city_index) - 1 #set index back 1 because we need to start at 0, not 1
            x1 = float(coord1) #create x1 coordinate
            y1 = float(coord2) #create y1 coordinate
            city = (index, x1, y1) #init our city
            newTour.append(city) #append to our list
    return newTour

def print_shortest_tour(shortestTour):
    for item in shortestTour:
        print(item, end=" ") #simply print with a space inbetween locations

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
    print(fitness(tour)) #print total cost
    print_shortest_tour(shortestTour) #print tour
