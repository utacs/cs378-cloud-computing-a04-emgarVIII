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

##  Course Name: CS378 - Cloud Computing 

##  Unique Number: 51515
    


# Add your Project REPORT HERE 
Screenshots of our Google Cloud - Dataproc that shows the machines ran
![image](https://github.com/user-attachments/assets/3eb0aa0c-30af-4ee5-b05e-7064344cf46f)
<img width="1800" alt="image" src="https://github.com/user-attachments/assets/9b6d6179-4904-4bc6-b8c4-6cbfb4372c3e">


For each task, for each Hadoop job you ran, include a screen shot of the Yarn History.
![image](https://github.com/user-attachments/assets/83aaa07b-0209-48f9-b2e2-0b476f9d0d2b)


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

# Running locally using the makefile:

To run task1, run:

`make run`

To run task2: run,

`make run-task2`

To run task3, run,

`make run-task3`

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


## To run on Google Cloud (replace with your own information)
First, add the dataset to your Google bucket. Then,

### Task 1:
`gcloud dataproc jobs submit hadoop \
--cluster=cluster-a4v2 \
--region=us-central1 \
--class=edu.cs.utexas.HadoopEx.Driver.HourlyErrors \
--jars=gs://my-hadoop-bucket-mauricio/MapReduce-WordCount-example-0.1-SNAPSHOT-jar-with-dependencies.jar \
-- gs://my-hadoop-bucket-mauricio/taxi-data-sorted-large.csv gs://my-hadoop-bucket-mauricio/output` 

### Task 2 (Error Rates Runner):
`gcloud dataproc jobs submit hadoop \
--cluster=cluster-a4v2 \
--region=us-central1 \
--class=edu.cs.utexas.HadoopEx.Driver.HourlyErrors \
--jars=gs://my-hadoop-bucket-mauricio/MapReduce-WordCount-example-0.1-SNAPSHOT-jar-with-dependencies.jar \
-- gs://my-hadoop-bucket-mauricio/taxi-data-sorted-large.csv gs://my-hadoop-bucket-mauricio/intermediate-task2 gs://my-hadoop-bucket-mauricio/output-task2`

### Task 3 (Efficiency Runner):
`gcloud dataproc jobs submit hadoop \
  --cluster=cluster-a4v2 \
  --region=us-central1 \
  --class=edu.cs.utexas.HadoopEx.Driver.HourlyErrors \
  --jars=gs://my-hadoop-bucket-mauricio/MapReduce-WordCount-example-0.1-SNAPSHOT-jar-with-dependencies.jar \
  -- gs://my-hadoop-bucket-mauricio/taxi-data-sorted-large.csv \
  gs://my-hadoop-bucket-mauricio/intermediate-task3 \
  gs://my-hadoop-bucket-mauricio/output-task3 \
  gs://my-hadoop-bucket-mauricio/extra-task3`


## Create a single JAR File from eclipse

Create a single jar file with eclipse 

*  File export -> export  -> export as binary ->  "Extract generated libraries into generated JAR"
