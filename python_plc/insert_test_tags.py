 

import snap7.client as c
from snap7.util import *
from snap7.snap7types import *
import sys, getopt
import logging
import sys, getopt
import mysql.connector

# db_host = sys.argv[1]
# db_user = sys.argv[2]
# db_password = sys.argv[3]
# db_name = sys.argv[4]
db_sp_one = 'truncate_test_tags_sp'
db_sp_two = 'insert_test_tags_sp'
ip=''

cycle=''
holding=''
drain=''
cyle_conti=''
toggelA=''
toggelB=''
conn = mysql.connector.connect(host = 'localhost', user = 'root', password = 'hydro', database = 'e1232')

#print "connected"
a = conn.cursor()
a.callproc(db_sp_one)
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
	#ip address of PLC
	plc.connect(ip,0,1)
	over_all_time_time = ReadMemory(plc,66,0,S7WLWord)
	hydro_actual_a_pressure = ReadMemory(plc,20,0,S7WLReal)
    hydro_actual_b_pressure = ReadMemory(plc,24,0,S7WLReal)
    hydraulic_actual_pressure =ReadMemory(plc,28,0,S7WLReal)
    cycle_status= ReadMemory(plc,76,0,S7WLWord)
    stabilization_timer= ReadMemory(plc,48,0,S7WLWord)
    holding_timer= ReadMemory(plc,50,0,S7WLWord)
    drain_timer= ReadMemory(plc,60,0,S7WLWord)
    machine_mode =ReadMemory(plc,2,0,S7WLWord)
    offline_online = '0'
    pop_ups = ReadMemory(plc,18,0,S7WLWord)
    invalid = ReadMemory(plc,0,4,S7WLBit)
    result = ReadMemory(plc,78,0,S7WLWord)
    bubble = ReadMemory(plc,108,0,S7WLWord)

b = conn.cursor()
b.execute("SELECT cycle_start,holding,drain,toggelA,toggelB FROM pop_value WHERE id='1'")

for row in b:
    cycle =row[0]
    holding=row[1]
    drain=row[2]
    toggelA=row[3]
    toggelB=row[4]
    #cyle_conti=row[3]
    # pop_offline_1_2=row[4]
    # srv_stop=row[5]

if cycle ==0 :
	WriteMemory(plc,18,0,S7WLWord,0)
	print '0cy'
	b.execute("UPDATE pop_value set cycle_start='6' WHERE id='1'")
	conn.commit() 
elif cycle ==4 :
	WriteMemory(plc,18,0,S7WLWord,4)
	print '4cy'
	b.execute("UPDATE pop_value set cycle_start='6' WHERE id='1'")
	conn.commit() 

if holding ==0 :
	WriteMemory(plc,18,0,S7WLWord,0)
	print '0HL'
	b.execute("UPDATE pop_value set holding='6' WHERE id='1'")
	conn.commit() 

if drain ==0 :
	WriteMemory(plc,18,0,S7WLWord,0)
	b.execute("UPDATE pop_value set drain='6' WHERE id='1'")
	conn.commit()
if toggelA !=6 :
    WriteMemory(plc,0,7,S7WLBit,int(toggelA))

    b.execute("UPDATE pop_value set toggelA='6' WHERE id='1'")
    conn.commit()
if toggelB !=6 :
    WriteMemory(plc,1,0,S7WLBit,int(toggelB))

    b.execute("UPDATE pop_value set toggelB='6' WHERE id='1'")
    conn.commit()



args = (machine_mode,
	over_all_time_time,
	'NA',
	'NA',
	hydro_actual_a_pressure,
	hydro_actual_b_pressure,
	hydraulic_actual_pressure,
	cycle_status,
	stabilization_timer,
	holding_timer,
	drain_timer,
	'NA',
	offline_online,
	result,
	pop_ups,
	'NA',
	'NA') 
 
print args
a.callproc(db_sp_two, args)

conn.commit() 
a.close() 
conn.close()

