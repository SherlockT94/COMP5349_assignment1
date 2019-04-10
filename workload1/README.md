# comp5349cc_selfrepository

#Navigate to the program folder and Use the following command to run the project:

bash runshell.sh inputfile outputfile

#The java program will use the ant (version:1.10.5) and build.xml to compile the java files. 
#To install to ant, first download and unzip the ant zip in hadoop user's home directory by using command:
wget http://www.strategylions.com.au/mirror//ant/binaries/apache-ant-1.10.5-bin.zip
unzip apache-ant-1.10.5-bin.zip
#update the path variable to include the executable path using conmmand:
export PATH=hadoop/user/apache-ant-1.10.5/bin:$PATH

#The program need to run on hadoop 2.8.5 with Ganglia 3.7.2, Hive 2.3.4, Hue 4.3.0, Mahout 0.13.0, Pig 0.17.0, and Tez 0.9.1
