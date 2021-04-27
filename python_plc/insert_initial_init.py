
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
#db_sp_one = sys.argv[5]
db_sp_two = 'insert_initial_init_sp'
ip=''
conn = mysql.connector.connect(host = 'localhost', user = 'root', password = 'hydro', database = 'e1232')

#print "connected"
a = conn.cursor()

conn.commit()

a.execute('SELECT ip FROM plc_ip')

for row in a:
    ip =row[0]

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

    test_type = ReadMemory(plc,4,0,S7WLWord)
    leakage_type = ReadMemory(plc,6,0,S7WLWord)
    valve_type = ReadMemory(plc,10,0,S7WLWord)
    valve_class =ReadMemory(plc,6,0,S7WLWord)
    valve_size =ReadMemory(plc,8,0,S7WLWord)
    type_of_sealing = ReadMemory(plc,12,0,S7WLWord)
    test_standards = ReadMemory(plc,14,0,S7WLWord)
    valve_standards = '1'
    stabilization_time = ReadMemory(plc,92,0,S7WLDWord)
    holding_time = ReadMemory(plc,96,0,S7WLDWord)
    drain_delay = ReadMemory(plc,66,0,S7WLDWord)
    drain_time = ReadMemory(plc,100,0,S7WLDWord)
    hydro_set_pressure = ReadMemory(plc,32,0,S7WLReal)
    hydraulic_set_pressure = ReadMemory(plc,36,0,S7WLReal)
    bar_psi_kgcm = ReadMemory(plc,16,0,S7WLWord)
    machine_mode = ReadMemory(plc,2,0,S7WLWord)
    allowable_leakage = '1'
    offline_online = '0'
    wrong_selection = ReadMemory(plc,0,4,S7WLBit)

args = (test_type,leakage_type,valve_type,valve_class,valve_size,type_of_sealing,test_standards,valve_standards,stabilization_time,holding_time,drain_delay,drain_time,hydro_set_pressure,hydraulic_set_pressure,bar_psi_kgcm,machine_mode,allowable_leakage,offline_online,wrong_selection)
print args
a.callproc(db_sp_two, args)
 
conn.commit() 
a.close() 
conn.close()



