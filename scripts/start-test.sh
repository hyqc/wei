#!/bin/bash
java -Xms512m -Xms512m -Xdebug -jar \
/data/server/api/wei/target/wei-0.0.1-SNAPSHOT.jar \
--spring.profiles.active=test >/tmp/wei.console.log 2>&1 &
