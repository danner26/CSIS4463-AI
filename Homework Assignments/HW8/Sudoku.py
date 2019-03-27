# Reads in the file and prints line by line
def readFile():
    file = open("s01a.txt","r")
    f1 = file.readlines()
    for x in f1:
        print(x)

if __name__ == "__main__":
    readFile()
