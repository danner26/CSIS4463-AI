###
### @author Daniel @. Anner and Sam Isidoro
###
import os
from pathlib import Path

class Puzzle:
  _puzzle = ''

  def __init__(self, data):
    self._puzzle = self.createPuzzle(data) # so you can just call Puzzle

  def createPuzzle(self, file):
    file_name = file + '.txt' # Append .txt to the file name
    filePuzzle = Path('./Inputs/' + file_name).read_text() # Read the text from the file we select
    filePuzzle = filePuzzle.replace('\n', ' ') # replace new lines with a space
    filePuzzle = filePuzzle.split(' ') # split all numbers into an array using space as the delimiter
    filePuzzle = [i for i in filePuzzle if i] # this removes and values that are empty

    puzzleMatrix = [[0]*9 for i in range(9)] # init an empty 9x9 2d matix filled with 0's
    r = 0 # row placeholder
    c = 0 # column placeholder
    for val in filePuzzle: # loop through the entier list of values
      if (c == 9): # if we are on the next value, aka the 9th value (0-8 is a row)
        c=0 # reset to 0
        r+=1 # add 1 to the row
        puzzleMatrix[r][c] = int(val) # set the value regardless of 0 or different
        c+=1 # increment the column
      else:
        puzzleMatrix[r][c] = int(val) # set the value regardless of 0 or different
        c+=1 # increment the column

    return puzzleMatrix # return the matric to be assigned

  def checkPuzzle(puzzle):
    for row in range(0,9): # iterate through each row
        for col in range(0,9): # iterate through each column
          if puzzle[row][col]==0: # if the value is not set, we are not done
            return False
    return True # Goal state!!

def solvePuzzle(puzzle):
  for i in range(0,81):
    r=i//9 # floor of i divided by 9
    c=i%9 # need to make sure we are not on an overflow
    if puzzle[r][c]==0:
      for value in range (1,10):
        if not(value in puzzle[r]): # ensure value isnt already in the row
          # ensure value isnt already used in the column
          if not value in (puzzle[0][c],puzzle[1][c],puzzle[2][c],puzzle[3][c],puzzle[4][c],puzzle[5][c],puzzle[6][c],puzzle[7][c],puzzle[8][c]):
            box=[]
            if r<3: # first row set (row 1-3)
              if c<3: # first col set (col 1-3)
                box=[puzzle[i][0:3] for i in range(0,3)]
              elif c<6: # second col set (col 4-6)
                box=[puzzle[i][3:6] for i in range(0,3)]
              else: # third col set (col 7-9)
                box=[puzzle[i][6:9] for i in range(0,3)]
            elif r<6: # second box (row 4-6)
              if c<3: # first col set (col 1-3)
                box=[puzzle[i][0:3] for i in range(3,6)]
              elif c<6: # second col set (col 4-6)
                box=[puzzle[i][3:6] for i in range(3,6)]
              else: # third col set (col 7-9)
                box=[puzzle[i][6:9] for i in range(3,6)]
            else:
              if c<3: # first col set (col 1-3)
                box=[puzzle[i][0:3] for i in range(6,9)]
              elif c<6: # second col set (col 4-6)
                box=[puzzle[i][3:6] for i in range(6,9)]
              else: # third col set (col 7-9)
                box=[puzzle[i][6:9] for i in range(6,9)]
            #Check that this value has not already be used on this 3x3 box
            if not value in (box[0] + box[1] + box[2]): # ensure value isnt already in the box
              puzzle[r][c]=value # set value
              if Puzzle.checkPuzzle(puzzle): # check if its complete
                return True
              else:
                if solvePuzzle(puzzle): # rerun
                  return puzzle
      break # break if we reach the end and start next interation
  puzzle[r][c]=0 # set back to 0 and back track

def printPuzzle(solvedPuzzle):
  print("+---+---+---+---+---+---+---+---+---+")
  for i, row in enumerate(solvedPuzzle):
      print(("|" + " {}   {}   {} |"*3).format(*[x if x != 0 else " " for x in row]))
      if i == 8:
          print("+---+---+---+---+---+---+---+---+---+") # end of sudoku table
      elif i % 3 == 2:
          print("|" + "---+---+---+---+---+---+---+---+" + "---|") # print middle rows on the lines of the boxes
      else:
          print("|" + "   +   +   +   +   +   +   +   +" + "   |") # print inner rows in between boxes

if __name__ == "__main__":
  toSolve = input("What is the name of the file to solve? (do not include .txt)\n")

  print("Puzzle before solution is derived")
  puzzleToSolve = Puzzle(toSolve)
  printPuzzle(puzzleToSolve._puzzle)

  print()
  print("Puzzle after solution is derived")
  solved = solvePuzzle(puzzleToSolve._puzzle)
  printPuzzle(solved)
