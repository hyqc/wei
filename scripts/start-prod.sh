#!/bin/bash
java -Xms512m -Xms512m -Xdebug -jar \
/data/demo/target/demo-0.0.1-SNAPSHOT.jar \
--spring.profiles.active=test >/tmp/demo.console.log 2>&1 &
