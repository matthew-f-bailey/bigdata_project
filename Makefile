DOCKER_NETWORK = docker-hadoop_default
ENV_FILE = hadoop.env
current_branch := $(shell git rev-parse --abbrev-ref HEAD)
build:
	docker build -t bde2020/hadoop-base:$(current_branch) ./base
	docker build -t bde2020/hadoop-namenode:$(current_branch) ./namenode
	docker build -t bde2020/hadoop-datanode:$(current_branch) ./datanode
	docker build -t bde2020/hadoop-resourcemanager:$(current_branch) ./resourcemanager
	docker build -t bde2020/hadoop-nodemanager:$(current_branch) ./nodemanager
	docker build -t bde2020/hadoop-historyserver:$(current_branch) ./historyserver
	docker build -t bde2020/hadoop-submit:$(current_branch) ./submit
	docker build -t bde2020/hadoop-card:$(current_branch) ./card-example

wordcount:
	docker build -t hadoop-wordcount ./submit
	docker run --network ${DOCKER_NETWORK} --env-file ${ENV_FILE} bde2020/hadoop-base:$(current_branch) hdfs dfs -mkdir -p /input
	docker run --network ${DOCKER_NETWORK} --env-file ${ENV_FILE} bde2020/hadoop-base:$(current_branch) hdfs dfs -copyFromLocal -f /opt/hadoop-3.2.1/README.txt /input/
	docker run --network ${DOCKER_NETWORK} --env-file ${ENV_FILE} hadoop-wordcount
	docker run --network ${DOCKER_NETWORK} --env-file ${ENV_FILE} bde2020/hadoop-base:$(current_branch) hdfs dfs -cat /output/*
	docker run --network ${DOCKER_NETWORK} --env-file ${ENV_FILE} bde2020/hadoop-base:$(current_branch) hdfs dfs -rm -r /output
	docker run --network ${DOCKER_NETWORK} --env-file ${ENV_FILE} bde2020/hadoop-base:$(current_branch) hdfs dfs -rm -r /input

cardexample:

	@echo 1. Build out JAR container ===================================
	cd card-example && mvn clean package && cd ..

	@echo 2. Build Docker Image for cardexample ===================================
	docker build -t hadoop-cardexample ./card-example

	@echo 3. Removing hdfs input dir ===================================
	docker run --network docker-hadoop_default --env-file hadoop.env --volume $(pwd)/test_data:/local_input bde2020/hadoop-base:master hdfs dfs -rm -r /input

	@echo 4. Creating new input dir ===================================
	docker run --network docker-hadoop_default --env-file hadoop.env --volume $(pwd)/test_data:/local_input bde2020/hadoop-base:master hdfs dfs -mkdir -p /input

	@echo 5. Moving input file into hdfs ===================================
	docker run --network docker-hadoop_default --env-file hadoop.env --volume $(pwd)/test_data:/local_input bde2020/hadoop-base:master hdfs dfs -put /local_input/cards.jsonl /input/

	@echo 6. Verifying input looks correct ===================================
	docker run --network docker-hadoop_default --env-file hadoop.env --volume $(pwd)/test_data:/local_input bde2020/hadoop-base:master hdfs dfs -cat /input/*

	@echo 7. Running mapreduce job ===================================
	docker run --network docker-hadoop_default --env-file hadoop.env hadoop-cardexample

	@echo 8. Printing output ===================================
	docker run --network docker-hadoop_default --env-file hadoop.env --volume $(pwd)/test_data:/local_input bde2020/hadoop-base:master hdfs dfs -cat /output/*

