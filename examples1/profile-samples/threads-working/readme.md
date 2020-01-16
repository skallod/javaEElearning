jcmd 1 JFR.start duration=60s filename=mathWork.jfr
jcmd 1 JFR.check
docker cp thread-work:/usr/src/app/mathWork.jfr ~/Projects
ps -p 1 -Lo pid,lwp,nlwp,ruser,pcpu,stime,etime
docker exec -it thread-work /bin/bash

