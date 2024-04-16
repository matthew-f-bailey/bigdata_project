
echo 1. Create table in hbase ===================================
docker exec hbase-docker bash -c "echo \"create 'cards', 'details'\" | hbase shell"


echo 2. Build Docker Image for cardexample ===================================
docker build -t hadoop-cardexample ./card-example-py

echo 3. Removing hdfs input dir ===================================
docker run \
    --network docker-hadoop_default \
    --env-file hadoop.env \
    --volume $(pwd)/test_data:/local_input \
    bde2020/hadoop-base:master \
    hdfs dfs -rm -r /input

echo 4. Creating new input dir ===================================
docker run \
    --network docker-hadoop_default \
    --env-file hadoop.env \
    --volume $(pwd)/test_data:/local_input \
    bde2020/hadoop-base:master \
    hdfs dfs -mkdir -p /input

echo 5. Moving input file into hdfs ===================================
docker run \
    --network docker-hadoop_default \
    --env-file hadoop.env \
    --volume $(pwd)/test_data:/local_input \
    bde2020/hadoop-base:master \
    hdfs dfs -put /local_input/cards.jsonl /input/

echo 6. Verifying input looks correct ===================================
docker run \
    --network docker-hadoop_default \
    --env-file hadoop.env \
    --volume $(pwd)/test_data:/local_input \
    bde2020/hadoop-base:master \
    hdfs dfs -cat /input/*

echo 7. Running mapreduce job ===================================
docker run \
    --network docker-hadoop_default \
    --env-file hadoop.env \
    hadoop-cardexample

echo 8. Printing output ===================================
docker run \
    --network docker-hadoop_default \
    --env-file hadoop.env \
    --volume $(pwd)/test_data:/local_input bde2020/hadoop-base:master \
    hdfs dfs -cat /output/*


# AttemptID:attempt_1711484820482_0001_r_000000_0 Timed out after 600 secs
# 2024-03-26 20:43:10,856 INFO mapreduce.Job:  map 100% reduce 0%
# 2024-03-26 20:43:28,927 INFO mapreduce.Job:  map 100% reduce 67%
# 2024-03-26 20:43:59,002 INFO mapreduce.Job:  map 100% reduce 68%
# 2024-03-26 20:54:10,218 INFO mapreduce.Job: Task Id : attempt_1711484820482_0001_r_000000_1, Status : FAILED
# AttemptID:attempt_1711484820482_0001_r_000000_1 Timed out after 600 secs
# [2024-03-26 20:54:09.400]Container killed by the ApplicationMaster.
# [2024-03-26 20:54:09.400]Sent signal OUTPUT_THREAD_DUMP (SIGQUIT) to pid 1100 as user root for container container_e14_1711484820482_0001_01_000004, result=success
# [2024-03-26 20:54:09.443]Container killed on request. Exit code is 143
# [2024-03-26 20:54:09.444]Container exited with a non-zero exit code 143.

# 2024-03-26 20:54:11,220 INFO mapreduce.Job:  map 100% reduce 0%
# 2024-03-26 20:54:28,320 INFO mapreduce.Job:  map 100% reduce 67%