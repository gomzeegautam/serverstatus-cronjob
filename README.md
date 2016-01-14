# serverstatus-cronjob
<b>Description:</b></br>
Java program to run from cron job in order to check the status of your server and send email to ADMIN's using mailgun API.

<b>Usage:</b></br>
Import the following code to your system. You can also use this program from Eclipse. </br>
This program needs 3 important JARs.

- jersey-bundle-1.13-b01.jar
- json_simple-1.1.jar
- log4j-1.2.17.jar

Create a executable jar of this code and place it on your Linux server .</br>
<b>For eg:</b> at path /home/user/ServerCheck/serverstatus.jar
Also, create a "log4j.properties" file in the same folder.

Your log4j.properties file may look loke this:</br>
<p>
log4j.rootLogger=DEBUG, stdout, file</br>
log4j.appender.stdout=org.apache.log4j.ConsoleAppender</br>
log4j.appender.stdout.Target=System.out</br>
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout</br>
log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n</br>
</br>
log4j.appender.file=org.apache.log4j.RollingFileAppender</br>
log4j.appender.file.File=/home/user/status.log</br>
log4j.appender.file.MaxFileSize=5MB</br>
log4j.appender.file.MaxBackupIndex=10</br>
log4j.appender.file.layout=org.apache.log4j.PatternLayout</br>
log4j.appender.file.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n</br>
</p>
</br>
Now, make an entry to your "crontab" file.</br>
<b>For Eg: </b> */5 * * * * java -jar serverstatus.jar /home/user/ServerCheck/log4j.properties /home/user/ServerCheck/
</br>

This will run your program every 5 minutes.

