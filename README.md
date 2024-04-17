[![Gitter chat](https://badges.gitter.im/gitterHQ/gitter.png)](https://gitter.im/big-data-europe/Lobby)

# Changes

Version 2.0.0 introduces uses wait_for_it script for the cluster startup

# Hadoop Docker

## Supported Hadoop Versions
See repository branches for supported hadoop versions

## Quick Start

To deploy an example HDFS cluster, run:
```
  docker-compose up
```

Run example wordcount job:
```
  make wordcount
```

Or deploy in swarm:
```
docker stack deploy -c docker-compose-v3.yml hadoop
```

`docker-compose` creates a docker network that can be found by running `docker network list`, e.g. `dockerhadoop_default`.

Run `docker network inspect` on the network (e.g. `dockerhadoop_default`) to find the IP the hadoop interfaces are published on. Access these interfaces with the following URLs:

* Namenode: http://<dockerhadoop_IP_address>:9870/dfshealth.html#tab-overview
* History server: http://<dockerhadoop_IP_address>:8188/applicationhistory
* Datanode: http://<dockerhadoop_IP_address>:9864/
* Nodemanager: http://<dockerhadoop_IP_address>:8042/node
* Resource manager: http://<dockerhadoop_IP_address>:8088/

## Configure Environment Variables

The configuration parameters can be specified in the hadoop.env file or as environmental variables for specific services (e.g. namenode, datanode etc.):
```
  CORE_CONF_fs_defaultFS=hdfs://namenode:8020
```

CORE_CONF corresponds to core-site.xml. fs_defaultFS=hdfs://namenode:8020 will be transformed into:
```
  <property><name>fs.defaultFS</name><value>hdfs://namenode:8020</value></property>
```
To define dash inside a configuration parameter, use triple underscore, such as YARN_CONF_yarn_log___aggregation___enable=true (yarn-site.xml):
```
  <property><name>yarn.log-aggregation-enable</name><value>true</value></property>
```

The available configurations are:
* /etc/hadoop/core-site.xml CORE_CONF
* /etc/hadoop/hdfs-site.xml HDFS_CONF
* /etc/hadoop/yarn-site.xml YARN_CONF
* /etc/hadoop/httpfs-site.xml HTTPFS_CONF
* /etc/hadoop/kms-site.xml KMS_CONF
* /etc/hadoop/mapred-site.xml  MAPRED_CONF

If you need to extend some other configuration file, refer to base/entrypoint.sh bash script.


- Actual Arrival Times	Gate arrival time is the instance when the pilot sets the aircraft parking brake after arriving at the airport gate or passenger unloading area.Â  If the parking brake is not set, record the time for the opening of the passenger door.Â  Also, carriers using a Docking Guidance System (DGS) may record the official gate-arrival time when the aircraft is stopped at the appropriate parking mark.
- Actual Departure Times	Gate departure time is the instance when the pilot releases the aircraft parking brake after passengers have loaded and aircraft doors have been closed.Â In cases where the flight returned to the departure gate before wheels-off time and departed a second time, report the last gate departure time before wheels-off time.Â  In cases of an air return, report the last gate departure time before the gate return.Â  If passengers were boarded without the parking brake being set, record the time that the passenger door was closed. Â Also, carriers using a Docking Guidance System may record the official gate-departure time based on aircraft movement. Â For example, one DGS records gate departure time when the aircraft moves more than 1 meter from the appropriate parking mark within 15 seconds.Â  Fifteen seconds is then subtracted from the recorded time to obtain the appropriate out time.
- Airline ID	An identification number assigned by US DOT to identify a unique airline (carrier). A unique airline (carrier) is defined as one holding and reporting under the same DOT certificate regardless of its Code, Name, or holding company/corporation. Use this field for analysis across a range of years.
- Airport Code	A three character alpha-numeric code issued by the U.S. Department of Transportation which is the official designation of the airport. The airport code is not always unique to a specific airport because airport codes can change or can be reused.
- Airport ID	An identification number assigned by US DOT to identify a unique airport. Use this field for airport analysis across a range of years because an airport can change its airport code and airport codes can be reused.
- Arrival Delay	Arrival delay equals the difference of the actual arrival time minus the scheduled arrival time. A flight is considered on-time when it arrives less than 15 minutes after its published arrival time.
- CRS	Computer Reservation System. CRS provide information on airline schedules, fares and seat availability to travel agencies and allow agents to book seats and issue tickets.
- Cancelled Flight	A flight that was listed in a carrier's computer reservation system during the seven calendar days prior to scheduled departure but was not operated.
- Carrier Code	Code assigned by IATA and commonly used to identify a carrier. As the same code may have been assigned to different carriers over time, the code is not always unique.
- Certificate Of Public Convenience And Necessity	A certificate issued to an air carrier under 49 U.S.C. 41102, by the Department of Transportation authorizing the carrier to engage in air transportation.
- Certificated Air Carrier	An air carrier holding a Certificate of Public Convenience and Necessity issued by DOT to conduct scheduled services interstate. Nonscheduled or charter operations may also be conducted by these carriers. (same as Certified Air Carrier)
- Certified Air Carrier	An air carrier holding a Certificate of Public Convenience and Necessity issued by DOT to conduct scheduled services interstate. Nonscheduled or charter operations may also be conducted by these carriers. (same as Certificated Air Carrier)
- City Market ID	An identification number assigned by US DOT to identify a city market. Use this field to consolidate airports serving the same city market.
- Departure Delay	The difference between the scheduled departure time and the actual departure time from the origin airport gate.
- Diverted Flight	A flight that is required to land at a destination other than the original scheduled destination for reasons beyond the control of the pilot/company.
- Domestic Operations	All air carrier operations having destinations within the 50 United States, the District of Columbia, the Commonwealth of Puerto Rico, and the U.S. Virgin Islands.
- Elapsed Time	The time computed from gate departure time to gate arrival time.
- FIPS	Federal Information Processing Standards. Usually referring to a code assigned to any of a variety of geographic entities (e.g. counties, states, metropolitan areas, etc). FIPS codes are intended to simplify the collection, processing, and dissemination of data and resources of the Federal Government.
- Flight Number	A one to four character alpha-numeric code for a particular flight.
- In-Flight Time	The total time an aircraft is in the air between an origin-destination airport pair, i.e. from wheels-off at the origin airport to wheels-down at the destination airport.
- Late Flight	A flight arriving or departing 15 minutes or more after the scheduled time.
- Passenger Revenues	Revenues from the air transportation of passengers.
- Scheduled Departure Time	The scheduled time that an aircraft should lift off from the origin airport.
- Scheduled Time Of Arrival	The scheduled time that an aircraft should cross a certain point (landing or metering fix).
- Taxi-In Time	The time elapsed between wheels down and arrival at the destination airport gate.
- Taxi-Out Time	The time elapsed between departure from the origin airport gate and wheels off.
- Unique Carrier	Unique Carrier Code. It is the Carrier Code most recently used by a carrier. A numeric suffix is used to distinguish duplicate codes, for example, PA, PA (1), PA (2). Use this field to perform analysis of data reported by one and only one carrier.
- World Area Code (WAC)	Numeric codes used to identify geopolitical areas such as countries, states (U.S.), provinces (Canada), and territories or possessions of certain countries. The codes are used within the various data banks maintained by the Office of Airline Information (OAI) and are created by OAI.
