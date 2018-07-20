call gradlew.bat build

set LOG_FILENAME=reports/current
set GC_ALG=-XX:+UseG1GC

set MEMORY=-Xmx500m -Xms500m

@rem -XX:+UseSerialGC
@rem -XX:+UseParallelGC
@rem -XX:-UseParallelOldGC
@rem -XX:+UseConcMarkSweepGC
@rem -XX:+UseG1GC

set GC_LOG=-verbose:gc -Xloggc:%LOG_FILENAME%.log
@rem set GC_LOG="-verbose:gc -Xloggc:%LOG_FILENAME%.log -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX: +UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=100M"

%JAVA_HOME%/bin/java %MEMORY% %GC_ALG% %GC_LOG% -jar build/libs/dz03_gc-1.jar


@rem follow strings exists on the course slides but doesn't work on the 1.8.0_121
@rem set GC_ALG="-XX:+UseConcMarkSweepGC -XX:+UseParNewGC"
@rem set GC_EXT_OPTIONS="-XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -XX:+ScavengeBeforeFullGC -XX:+CMSScavengeBeforeRemark"
@rem set L4J_OPTIONS="-Dlog4j.debug=true"