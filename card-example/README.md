# My Hadoop/Docker playground.

## Rationale behind using docker as underlying tool to learn hadoop
### Potential Pros
- I am very familiar with Docker
- Once this course is over, I don't need to spend money to keep using this.
- Keeping everything local speeds up dev iterations.
- Very reproducable. I can blow away and re-create everything with almost no effort/time.

### Potential Cons
- Most likely wouldn't be how a production system would work.
- Adds an extra layer to solution.
- Can't (or shouldn't) use docker inside docker.
- Hadoop and docker can sometimes be a little more finicky with networking.

Library being used: https://github.com/big-data-europe/docker-hadoop

## input/
Dir containing a json file representing some cards from a deck. In this case its all the 2's. The structure of this is jsonlines and each line contains a single "deck" of cards with some of them potentially missing.

## output/
Dir containing the output file after running through MR job. Should be the difference of a full deck and the input file.

## src/main/java/com/cards
Dir containing java source files.

## target/
Dir containing the maven built artifacts

## Dockerfile
Dockerfile to integrate with other Hadoop nodes and hold application code. Relies on built out jar and all other hadoop resources to be up and running in their containers on the same docker network.

## pom.xml
Any java deps and build configs.

## README.md
You are here.

# run.sh
Simple script to call hadoop from entrypoint.

# Run
To run this we first need on our system:
- Docker & Compose
- Java
- Maven
- Access to Github
- Ability to run Makefiles (for convenience)

Then run the following commands:

Optionally remove ALL containers and images

- ``make build``

- ``docker rm -vf $(docker ps -aq) && docker rmi -f $(docker images -aq)``

- ``docker compose up``

- ``source hbase-docker/start-hbase.sh``

- ``source run_cardexample-py.sh``


Disclaimer: I am using Ubuntu and have not tried this on any other OS.