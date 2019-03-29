class Sudoku:

    #We have to get the text file to be read like this. No whitespace or comma/quotes.
    puzzle = '040000179002008054006005008080070910050090030019060040300400700570100200928000060'

# Reads in the file and prints line by line
##    def readFile():
##       # file = open("s01a.txt","r")
##        #f1 = file.readlines()
##       # file = " ".join(file.split())
##        #print(file)
##
##
##        with open('s01a.txt') as f:
##            mylist = [line.rstrip('\n') for line in f]
##            mylist = [item.replace(",", "") for item in mylist]
##            print(mylist)
##            f1 = mylist.readlines()
##            file = open('testfile.txt','w+')
##            file.write(f1) 
## 
##            file.close() 
            
        
    # Creates the grid 
    def createGrid(A, B):

        return [a+b for a in A for b in B]
        digits   = '123456789'
        rows     = 'ABCDEFGHI'
        columns     = digits
        squares  = createGrid(rows, columns)
        unitlist = ([createGrid(rows, c) for c in columns] +
                [createGrid(r, columns) for r in rows] +
                [createGrid(rs, cs) for rs in ('ABC','DEF','GHI') for cs in ('123','456','789')])
        units = dict((s, [u for u in unitlist if s in u]) 
                 for s in squares)
        peers = dict((s, set(sum(units[s],[]))-set([s]))
                 for s in squares)

    
    # Creates a dict of values or returns false if it can't
    def parseGrid(puzzle):
        
        values = dict((s, digits) for s in squares)
        for s,d in gridValues(puzzle).items():
            if d in digits and not assignValues(values, s, d):
                return False ## (Fail if we can't assign d to square s.)
        return values
    

    # Converts grid to a hashmap
    def gridValues(puzzle):
        
        chars = [c for c in puzzle if c in digits or c in '0.']
        assert len(chars) == 81
        return dict(zip(squares, chars))


    # Assigns values to proper spots and returns false if it can't
    def assignValues(values, s, d):

        other_values = values[s].replace(d, '')
        if all(eliminateSpots(values, s, d2) for d2 in other_values):
            return values
        else:
            return False

        
    # Propagation step. Eliminates a position once it can only be 1 value and assigns it and returns false if it can't
    def eliminateSpots(values, s, d):

        if d not in values[s]:
            return values 
        values[s] = values[s].replace(d,'')
        if len(values[s]) == 0:
            return False 
        elif len(values[s]) == 1:
            d2 = values[s]
            if not all(eliminateSpots(values, s2, d2) for s2 in peers[s]):
                return False
        for u in units[s]:
            dplaces = [s for s in u if d in values[s]]
            if len(dplaces) == 0:
                return False
            elif len(dplaces) == 1:
                if not assignValues(values, dplaces[0], d):
                    return False
        return values


    # Uses dfs and propagtion 
    def searchSpots(values):
        if values is False:
            return False 
        if all(len(values[s]) == 1 for s in squares): 
            return values 
        n,s = min((len(values[s]), s) for s in squares if len(values[s]) > 1)
        return some(searchSpots(assignValues(values.copy(), s, d)) 
                    for d in values[s])

    # Solves the puzzle
    def solution(puzzle):
        return searchSpots(parseGrid(puzzle))


        

