# ChineseChessGame
Chinese Chess Game online (Spring Boot + ReactJs)

- This project is in development. Then, you need to setup a lot of thing to run.
- We will deploy all thing into internet when all done.

### How to run ?
- Prerequisite: You need to have mysql server and node.js installed, mozilla firefox browser(at this now, this can only use on firefox).
1. Clone `git clone https://github.com/Puskin2911/SE04-Group-23.1`.
2. Change database connection information in `application.yml` file.
3. Run service:
  - At root directory. `cd ccgame-service` then `mvn clean install` then `java -jar target/ccgame-1.0.jar`.
4. Run Web UI:
  - At root directory. `cd game-ui` then `set HTTPS=true&&react-scripts start`.
5. Sign up and ....
