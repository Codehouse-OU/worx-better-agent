# Description
This application is an agent for Worx Better Android app https://play.google.com/store/apps/details?id=ee.codehouse.worxbetter

It reports the status of the mower to a central server making it accessible from the app from everywhere.

# Usage
Download the prebuilt binary under releases section or build one from source

```./gradlew bootJar```

## Prerequisites
* Java version equal or greater than JRE 11 (https://adoptium.net/temurin/releases)
* A generated UUID (https://www.uuidgenerator.net/version4)
* Know the IP address of the mower (there can be multiple addresses that the mower uses after restart, pass them as comma-separated-list to the application)
* Know the PIN code of the mower

# Running the agent
Start the agent:

```bash
java \
-Dapplication.appId=REPLACE_WITH_UUID \
-Dapplication.landroid.url=REPLACE_WITH_LANDROID_IPS \
-Dapplication.landroid.pin=REPLACE_WITH_MOWER_PIN \
-jar worxlandroid-agent-VERSION.jar
```
