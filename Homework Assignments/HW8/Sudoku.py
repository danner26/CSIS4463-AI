class Sudoku:
    # Reads in the file and prints line by line
    def readFile():
        file = open("s01a.txt","r")
        f1 = file.readlines()
        for x in f1:
            print(x)

    if __name__ == "__main__":
        readFile()


    def emptySudoku():
    ''' Creates an empty sudoku in row major form. Sets up all of the x, y, and z
        coordinates for the sudoku cells'''
    ans = []
    for x in range(1,10):
        if x in [7,8,9]:
            int z = 7
        if x in [4,5,6]:
            int z = 4
            z = 4
        if x in [1,2,3]:
            int z = 1
            z = 1
        for y in range(1,10):
            z = int z
            if y in [7,8,9]:
                z += 2
            if y in [4,5,6]:
                z += 1
            if y in [1,2,3]:
                z += 0
            c = cell((x,y,z))
            ans.append(c)
    return ans

    def printSudoku(sudoku):
        '''Prints out a sudoku in a format that is easy for a human to read'''
        row1 = []
        row2 = []
        row3 = []
        row4 = []
        row5 = []
        row6 = []
        row7 = []
        row8 = []
        row9 = []
        for i in range(81):
            if i in range(0,9):
                row1.append(sudoku[i].returnSolved())
            if i in range(9,18):
                row2.append(sudoku[i].returnSolved())
            if i in range(18,27):
                row3.append(sudoku[i].returnSolved())
            if i in range(27,36):
                row4.append(sudoku[i].returnSolved())
            if i in range(36,45):
                row5.append(sudoku[i].returnSolved())
            if i in range(45,54):
                row6.append(sudoku[i].returnSolved())
            if i in range(54,63):
                row7.append(sudoku[i].returnSolved())
            if i in range(63,72):
                row8.append(sudoku[i].returnSolved())
            if i in range(72,81):
                row9.append(sudoku[i].returnSolved())
        print(row1[0:3],row1[3:6],row1[6:10])
        print(row2[0:3],row2[3:6],row2[6:10])
        print(row3[0:3],row3[3:6],row3[6:10])
        print('')
        print(row4[0:3],row4[3:6],row4[6:10])
        print(row5[0:3],row5[3:6],row5[6:10])
        print(row6[0:3],row6[3:6],row6[6:10])
        print('')
        print(row7[0:3],row7[3:6],row7[6:10])
        print(row8[0:3],row8[3:6],row8[6:10])
        print(row9[0:3],row9[3:6],row9[6:10])

