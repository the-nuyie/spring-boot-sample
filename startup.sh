java -Dhttp.proxyHost=egress-proxy -Dhttp.proxyPort=8080 \
-Dhttps.proxyHost=egress-proxy -Dhttps.proxyPort=8080 \
-Dhttp.nonProxyHosts="*.vpce-01ad73a99bd415b3c-3awu8laz.s3.ap-southeast-1.vpce.amazonaws.com" \
-Dlogging.level.org.apache.tomcat.util.net.NioEndpoint=INFO \
-jar /spring-boot-sample.jar --trace