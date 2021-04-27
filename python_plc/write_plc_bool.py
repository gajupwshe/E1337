import snap7.client as c
from snap7.util import *
from snap7.snap7types import *
import sys, getopt
import mysql.connector


ip=''
conn = mysql.connector.connect(host = 'localhost', user = 'root', password = 'hydro', database = 'e1257')
a = conn.cursor()
a.execute('SELECT ip FROM plc_ip')

for row in a:
    ip =row[0]

byte1 = int(sys.argv[1])
to= int(sys.argv[2])
value1 = int(sys.argv[3])
datatype1 = S7WLBit

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
def WriteMemory(plc,byte,bit,datatype,value):
    result = plc.read_area(areas['MK'],0,byte,datatype)
    if datatype==S7WLBit:
        set_bool(result,0,bit,value)
    elif datatype==S7WLByte or datatype==S7WLWord:
        set_int(result,0,value)
    elif datatype==S7WLReal:
        set_real(result,0,value)
    elif datatype==S7WLDWord:
        set_dword(result,0,value)
    plc.write_area(areas['MK'],0,byte,result)
if __name__=="__main__":
    plc = c.Client()
    plc.connect(ip,0,1)
    WriteMemory(plc,byte1,to,datatype1,value1)
 

