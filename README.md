# Please add your team members' names here. 

## Team members' names 

1. Student Name: Mauricio Garcia

   Student UT EID: mg64983

2. Student Name: Derek Chen

   Student UT EID: dyc377

3. Student Name: Mohamed Ilaiwi

   Student UT EID: mi5365

4. Ritu Karivaradasamy

   Student UT EID: rk27822

 ...

##  Course Name: CS378 - Cloud Computing 

##  Unique Number: 51515
    


# Add your Project REPORT HERE 


# Project Template

# Running on Laptop     ####

Prerequisite:

- Maven 3

- JDK 1.6 or higher

- (If working with eclipse) Eclipse with m2eclipse plugin installed


The java main class is:

edu.cs.utexas.HadoopEx.HourlyErrors 

Input file:  taxi-data-sorted-small.csv

Specify your own Output directory like 

# Running:

To run task1, run:

`make run`

To run task2: run,

`make run-task2'

This assumes that taxi-data-sorted-small.csv exists in the same directoy as `Makefile`.


## Create a JAR Using Maven 

To compile the project and create a single jar file with all dependencies: 
	
```	mvn clean package ```



## Run your application
Inside your shell with Hadoop

Running as Java Application:

```java -jar target/MapReduce-WordCount-example-0.1-SNAPSHOT-jar-with-dependencies.jar SOME-Text-Fiel.txt  output``` 

Or has hadoop application

```hadoop jar your-hadoop-application.jar edu.cs.utexas.HadoopEx.WordCount arg0 arg1 ... ```



## Create a single JAR File from eclipse



Create a single gar file with eclipse 

*  File export -> export  -> export as binary ->  "Extract generated libraries into generated JAR"
