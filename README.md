#Android - Xamarin

Android application to be use in a Xamarin lab.

##JSON

Gson is currently used to both get and create JSON.
The Java objects representing the JSON are: Contact, News and Incident.


##Server communication

All server communication happens in SnalebodaServer.java.

The different activities ContactActivity, NewsActivity and IncidentActivity get an instance of the 
server, add them self as listener and invoke the server call to fetch the required data.
When the server received the data it is sent back using the specified listener.



