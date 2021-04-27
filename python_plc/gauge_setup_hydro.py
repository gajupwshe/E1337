
import snap7.client as c
from snap7.util import *
from snap7.snap7types import *
import sys, getopt
import logging
import sys, getopt
import mysql.connector 
import time

ip=''
conn = mysql.connector.connect(host = 'localhost', user = 'root', password = 'hydro', database = 'e1257')
a = conn.cursor()
a.execute('SELECT ip FROM plc_ip')

for row in a:
    ip =row[0]

start = time.time()
def ReadMemory(plc,byte,bit,datatype):
    result = plc.read_area(areas['MK'],0,byte,datatype)
    if datatype==S7WLBit:
        return get_bool(result,0,bit)
    elif datatype==S7WLByte or datatype==S7WLWord:
        return get_int(result,0)
    elif datatype==S7WLReal:
        return get_real(result,0)
    elif datatype==S7WLDWord:
        return get_dword(result,0)
    else:
        return None



if __name__=="__main__":
    plc = c.Client()
    #ip address of PLC
    plc.connect(ip,0,1)
    print ReadMemory(plc,80,0,S7WLWord) #MAX
    print ReadMemory(plc,82,0,S7WLWord) #GREEN
    print ReadMemory(plc,0,4,S7WLBit) #invalid
		
# c.close()
