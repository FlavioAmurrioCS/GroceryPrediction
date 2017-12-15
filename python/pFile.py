# Python
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'
import tensorflow as tf
from numpy import*

ORIGINAL_FILE = "./storeTrain/store_1.csv"

def printFile(srcFile, num):
    with open(srcFile, 'r') as f:
        for line in f.readlines():
            print(line)
            break


printFile(ORIGINAL_FILE, 3)

points = genfromtxt(ORIGINAL_FILE, delimiter=',', skipfirst = 1)


# goo.gl/0OgXiL