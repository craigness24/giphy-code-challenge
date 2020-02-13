# giphy-code-challenge

The current version of the app uses an embeded mongo DB, Spring Boot server (java/kotlin), and Reactjs front end.
The application will allow you to register, login, and like giphys. You will have to manually 
navigate because the redirecting isn't fully finished. Once you search for some ghiphys and like some, 
search again for the same text and notice the results are still liked. I had full intentions to convert to postgres (see docker-compose file in this project), 
use flyway to maintain the sql scripts, clean up the server side into proper service layers, but this turned into throwing stuff at the wall
to see what would stick given the time constraints.  

Dev Setup

`$ java -version`<br>
`java version "1.8.0_131"`<br>
`Java(TM) SE Runtime Environment (build 1.8.0_131-b11)`<br>
`Java HotSpot(TM) 64-Bit Server VM (build 25.131-b11, mixed mode)`<br>

`$ npm -v`
<br> 
`6.13.4`


####Start the server in directory giphy-server
`./gradlew bootRun`

Server port will be :8080

####Start the UI in directory giphy-ui
`npm run start`

UI port will be :3000




