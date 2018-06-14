
FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD ./build/libs/iot-iot-0.0.1-SNAPSHOT.jar iot.jar
RUN sh -c 'touch /iot.jar'
ENV JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"

# access remote debug port through ssh:
# ssh -L 5432:localhost:5432 accepted

#EXPOSE 8080 5005 5006 5007
#
#CMD java -jar \
#		 -Dspring.profiles.active="$DEPLOY_ENV" \
#		 -Dspring.cloud.config.uri="$CONFIG_SRV" \
#		 -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 \
#		 -Dcom.sun.management.jmxremote \
#		 -Dcom.sun.management.jmxremote.port=5006 \
#		 -Dcom.sun.management.jmxremote.authenticate=false \
#		 -Dcom.sun.management.jmxremote.ssl=false \
#		 -Dcom.sun.management.jmxremote.rmi.port=5007 \
#		 -Djava.rmi.server.hostname="$DEPLOY_HOST" \
#         -Xmx1024m \
#		 *.jar


EXPOSE 8081 5005

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /accepted_core.jar" ]
