# comp5349_Assignment1

#Navigate to the program folder and Use the following command to run the project:

bash runshell.sh inputfile outputfile

#The java program will use the ant (version:1.10.5) and build.xml to compile the java files. 
#To install to ant, first download and unzip the ant zip in hadoop user's home directory by using command:
wget http://www.strategylions.com.au/mirror//ant/binaries/apache-ant-1.10.5-bin.zip
unzip apache-ant-1.10.5-bin.zip
#update the path variable to include the executable path using conmmand:
export PATH=hadoop/user/apache-ant-1.10.5/bin:$PATH

#The program need to run on Spark: Spark 2.4.0 on Hadoop 2.8.5 YARN with Ganglia 3.7.2 and Zeppelin 0.8.1
