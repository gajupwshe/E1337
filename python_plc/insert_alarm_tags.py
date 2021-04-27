
import snap7.client as c
from snap7.util import *
from snap7.snap7types import *
import sys, getopt
import logging
import sys, getopt
import mysql.connector

#db_host = sys.argv[1]
#db_user = sys.argv[2]
#db_password = sys.argv[3]
#db_name = sys.argv[4]
db_sp_one = 'alarm_tags_sp'
#test_no = sys.argv[6]

conn = mysql.connector.connect(host = 'localhost', user = 'root', password = 'hydro', database = 'e1257')

#print "connected"
a = conn.cursor()
# a = conn.cursor()
a.execute('SELECT ip FROM plc_ip')

for row in a:
    ip =row[0]

conn.commit()
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
    machine_mode = ReadMemory(plc,2,0,S7WLWord)
    A_side_pt = ReadMemory(plc,0,1,S7WLBit)
    B_side_pt = ReadMemory(plc,0,2,S7WLBit)
    pre_mot = ReadMemory(plc,68,0,S7WLWord)
    drain_mot = ReadMemory(plc,70,0,S7WLWord)
    hydralic_mot_1 = ReadMemory(plc,72,0,S7WLWord)
    oil_level = ReadMemory(plc,74,0,S7WLWord)
    air_inlet ='NA'
    temp = 'NA'
    hydralic_mot_2 = 'NA'

	# offline_online = ReadMemory(plc,4,1,S7WLBit)

args = (oil_level,air_inlet,temp,hydralic_mot_1,pre_mot,drain_mot,A_side_pt,B_side_pt,machine_mode,'NA')

print args

a.callproc(db_sp_one, args)
conn.commit() 
# a.close() 
conn.close()
# c.close()
