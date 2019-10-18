import matplotlib.pyplot as plt
import numpy as np
import csv
import sys

file='Data2.csv'
fname = open(file,'rt')
plt.plotfile(fname, ('angle', 'intensity', 'difference'), subplots=False)
plt.show()