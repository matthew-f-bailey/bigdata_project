FROM bde2020/hadoop-base:2.0.0-hadoop3.2.1-java8

COPY target/taxi-times-example-1.0-SNAPSHOT.jar /opt/hadoop/applications/TaxiTimes.jar
# COPY input/cards.jsonl /opt/hadoop-3.2.1/cards.jsonl
# RUN chmod a+rwx /opt/hadoop-3.2.1/cards.jsonl

ENV JAR_FILEPATH="/opt/hadoop/applications/TaxiTimes.jar"
ENV CLASS_TO_RUN="TaxiTimesDriver"
ENV PARAMS="/input /output"

ADD run.sh /run.sh
RUN chmod a+x /run.sh

CMD ["/run.sh"]


# cd card-example && mvn clean package && cd ..
# docker build -t hadoop-cardexample ./card-example
# docker run --network docker-hadoop_default --env-file hadoop.env --volume $(pwd)/test_data:/local_input bde2020/hadoop-base:master hdfs dfs -rm -r /input
# docker run --network docker-hadoop_default --env-file hadoop.env --volume $(pwd)/test_data:/local_input bde2020/hadoop-base:master hdfs dfs -mkdir -p /input
# docker run --network docker-hadoop_default --env-file hadoop.env --volume $(pwd)/test_data:/local_input bde2020/hadoop-base:master hdfs dfs -put /local_input/cards.jsonl /input/
# docker run --network docker-hadoop_default --env-file hadoop.env --volume $(pwd)/test_data:/local_input bde2020/hadoop-base:master hdfs dfs -cat /input/*
# docker run --network docker-hadoop_default --env-file hadoop.env hadoop-cardexample
# docker run --network docker-hadoop_default --env-file hadoop.env --volume $(pwd)/test_data:/local_input bde2020/hadoop-base:master hdfs dfs -cat /output/*
