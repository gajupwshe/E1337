 

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
db_sp_one = 'Insert_read_write_sp'
db_sp_two = 'truncate_insert_read_write'
ip=''
conn = mysql.connector.connect(host = 'localhost', user = 'root', password = 'hydro', database = 'e1232')

#print "connected"
a = conn.cursor()
a.callproc(db_sp_two)
conn.commit()

testType=''
valveType=''
valveSize=''
valveClass=''
testStd=''
valveStd=''
typeSealing=''
lekageType=''
unit=''
toggelA=''
toggelB=''

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
	
        stabilization_time_set = ReadMemory(plc,92,0,S7WLDWord)
        holding_time_set = ReadMemory(plc,96,0,S7WLDWord)
        drain_time_set = ReadMemory(plc,100,0,S7WLDWord)
        hydro_set = ReadMemory(plc,32,0,S7WLReal)
        hydraulic_set = ReadMemory(plc,36,0,S7WLReal)
        machine_mode =ReadMemory(plc,2,0,S7WLWord)
        offline_online = '0'
        max_gauge= ReadMemory(plc,80,0,S7WLWord) #MAX
        green_gauge= ReadMemory(plc,82,0,S7WLWord) #GREEN
        invalid= ReadMemory(plc,0,4,S7WLBit) #invalid'
	

b = conn.cursor()
b.execute("SELECT testType,valveType,valveSize,valveClass,testStd,valveStd,typeSealing,lekageType,unit,toggelA,toggelB FROM writedropdownplc WHERE id='1'")

for row in b:
    #cycle =row[0]
    #holding=row[1]
    #drain=row[2]
    testType=row[0]
    valveType=row[1]
    valveSize=row[2]
    valveClass=row[3]
    testStd=row[4]
    valveStd=row[5]
    typeSealing=row[6]
    lekageType=row[7]
    unit=row[8]
    toggelA=row[9]
    toggelB=row[10]

if testType !=100 :
	WriteMemory(plc,4,0,S7WLWord,int(testType))
	
	print '0cy'
	b.execute("UPDATE writedropdownplc set testType='100' WHERE id='1'")
	conn.commit() 

if valveType !=100 :
	WriteMemory(plc,10,0,S7WLWord,int(valveType))
	
	print '0HL'
	b.execute("UPDATE writedropdownplc set valveType='100' WHERE id='1'")
	conn.commit() 

if valveSize !=100 :
	WriteMemory(plc,8,0,S7WLWord,int(valveSize))

	b.execute("UPDATE writedropdownplc set valveSize='100' WHERE id='1'")
	conn.commit()

if valveClass !=100 :
	WriteMemory(plc,6,0,S7WLWord,int(valveClass))

	b.execute("UPDATE writedropdownplc set valveClass='100' WHERE id='1'")
	conn.commit()
if testStd !=100 :
	WriteMemory(plc,14,0,S7WLWord,int(testStd))

	b.execute("UPDATE writedropdownplc set testStd='100' WHERE id='1'")
	conn.commit()
# if valveStd !=100 :
# 	WriteMemory(plc,14,0,S7WLWord,int())

# 	b.execute("UPDATE writedropdownplc set valveStd='100' WHERE id='1'")
# 	conn.commit()
if typeSealing !=100 :
	WriteMemory(plc,12,0,S7WLWord,int(typeSealing))

	b.execute("UPDATE writedropdownplc set typeSealing='100' WHERE id='1'")
	conn.commit()
if unit !=100 :
	WriteMemory(plc,16,0,S7WLWord,int(unit))

	b.execute("UPDATE writedropdownplc set unit='100' WHERE id='1'")
	conn.commit()
if lekageType !=100 :
	WriteMemory(plc,0,6,S7WLBit,int(lekageType))

	b.execute("UPDATE writedropdownplc set lekageType='100' WHERE id='1'")
	conn.commit()

if toggelA !=100 :
    WriteMemory(plc,0,7,S7WLBit,int(toggelA))

    b.execute("UPDATE writedropdownplc set toggelA='100' WHERE id='1'")
    conn.commit()

if toggelB !=100 :
    WriteMemory(plc,1,0,S7WLBit,int(toggelB))

    b.execute("UPDATE writedropdownplc set toggelB='100' WHERE id='1'")
    conn.commit()



args = (machine_mode,stabilization_time_set,holding_time_set,drain_time_set,hydraulic_set,hydro_set,offline_online,max_gauge,green_gauge,invalid) 
 
print args
a.callproc(db_sp_one, args)

conn.commit() 
a.close() 
conn.close()

