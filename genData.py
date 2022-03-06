#!/usr/bin/python
import os
import sys
import random
from time import mktime, strptime,localtime


def randomTimestamp(start, end):
    format = '%Y-%m-%d %H:%M:%S'
    startTime = mktime(strptime(start, format))
    endTime = mktime(strptime(end, format))
    time = startTime + random.random() * (endTime - startTime)
    return mktime(localtime(time))


fileSize = int(sys.argv[1])
x_size = 1280
y_size = 1024
userIdPool = 1000000
startDate = '2000-01-01 00:00:00'
endDate = '2022-01-01 00:00:00'
f = open(sys.argv[2],'w')
for i in range(fileSize):
    if random.choice(range(10)) != 5:
        x = random.randint(0,x_size)
        y = random.randint(0,y_size)
        userId = random.randint(0, userIdPool)
        timestamp= int(randomTimestamp(startDate, endDate))
        result = str(x) + ' ' + str(y) + ' ' + str(userId) + ' ' + str(timestamp)
        f.write(result + '\n')
    else:
        f.write('error\n')
f.close()
