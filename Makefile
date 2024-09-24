run:
	rm -rfd output/
	java -jar target/MapReduce-WordCount-example-0.1-SNAPSHOT-jar-with-dependencies.jar taxi-data-sorted-small.csv output


run-task2:
	rm -rfd intermediate_folders/
	rm -rfd intermediate_output/
	java -jar target/MapReduce-WordCount-example-0.1-SNAPSHOT-jar-with-dependencies.jar taxi-data-sorted-small.csv intermediate_folders intermediate_output


run-task3:
	rm -rfd intermediate_folders/
	rm -rfd intermediate_output/
	java -jar target/MapReduce-WordCount-example-0.1-SNAPSHOT-jar-with-dependencies.jar taxi-data-sorted-small.csv intermediate_folders intermediate_output a
