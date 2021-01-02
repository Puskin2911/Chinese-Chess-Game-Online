# Chinese Chess Game online

- This app now is available at http://ec2-54-254-252-59.ap-southeast-1.compute.amazonaws.com/game.

## Introduction
#### Why is this application born?
- The fist thing, I and my friend really like the chinese chess game.
We often play this game online.
Then I asked myself why not make my own game? This is the first bricks base.
- The second thing, to build this game, we can be cover a lot of things:
  - Building a restful web application with Java Spring Boot.
  - ORM framework - Hibernate and Redis cache DB.
  - Using WebSocket to achieve real-time communication.
  - Handle Authenticate user with JWT and OAuth2.
  - Single Page App with ReactJs.
  - CI/CD and DevOps (AWS - Amazon web service) - Reserve proxy with NGinX.
  - More other tool : Git, IntelliJ IDE, WebStorm IDE, GitHub
#### Motivation ?
- We are all students, and we need prepare knowledge for our job in the future.
- We may not be able to complete this project, but we will do our best.
## Project Detail
### Technology
- Spring Framework: Spring Boot, STOMP message, Spring Security, Spring Data Jpa
- ReactJs, Bootstrap, SockJs
- AWS EC2, Nginx
- Github CI/CD, IntelliJ IDE, WebStorm IDE, VisualStudio Code, SSH
### System Architecture
#### Use cases
- The use case for this app is very simple.
- Just one actor is `User`, who can `login`, `sign up`, `play a game`, `watching a game`.
  ![Use case image](https://github.com/Puskin2911/SE04-Group-23.1/blob/v1.0-dev/refs/uml/use-case.PNG)
### Authenticate Strategy
- First at all is authenticate user. We use JWT:
#### JWT workflow
  ![JWT workflow](https://github.com/Puskin2911/SE04-Group-23.1/blob/v1.0-dev/refs/uml/jwt-workflow.PNG)
#### Generate JWT token
  ![Generate JWT](https://github.com/Puskin2911/SE04-Group-23.1/blob/v1.0-dev/refs/uml/gen-jwt.PNG)
#### Validate JWT token
  ![Validate JWT](https://github.com/Puskin2911/SE04-Group-23.1/blob/v1.0-dev/refs/uml/validate-jwt.PNG)
-**Note:** For more detail about authentication with JWT, you can see in wiki page. You can click [here](https://github.com/Puskin2911/SE04-Group-23.1/wiki/Authentication-with-JWT) 
### Room Strategy
### Message Strategy
### CI/CD & Deployment
#### Github CI
- Git flow: Protected branch `v1.0-dev` is main. Other branch follow format `task/...` or `bugfix...`.
  ![Git flow diagram](https://github.com/Puskin2911/SE04-Group-23.1/blob/v1.0-dev/refs/git-flow-diagram.png)
- Github CI: Maven build, trigger when have new Pull Request on branch `v1.0-dev` or push to `v1.0-dev`.
  ```
  name: Java CI with Maven
  on:
    push:
      branches: [ v1.0-dev ]
    pull_request:
      branches: [ v1.0-dev ]
  jobs:
    build:
      runs-on: ubuntu-latest
      steps:
        - uses: actions/checkout@v2
        - name: Set up JDK 11
          uses: actions/setup-java@v1
          with:
            java-version: 11
        - name: Build with Maven
          run: mvn -B package -Dmaven.test.skip=true --file ccgame-service/pom.xml
  ```
  #### AWS Deployment
  - Application run on Elastic Compute Cloud (EC2) linux instance.
  - Use Nginx as reverse proxy: 
    - Serve static page web ui at port `80`(HTTP).
    - All requests match `/api/**` or `/stomp/**` will be redirect to spring boot back end service at `127.0.0.1:8080`.
    - Config: 
      ```
        server {
                listen        80;
                server_name   ec2-54-151-177-156.ap-southeast-1.compute.amazonaws.com;
        
        		root /home/ubuntu/ccgame/web-ui/; 
        		index index.html index.htm;
        
        		location / {
        			try_files $uri /index.html =401;
        		}
        		
        		# Proxy pass for backend service
        		location /api/ {
        			proxy_set_header Host $host;
        			proxy_set_header X-Real-IP $remote_addr;
        			proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                     		proxy_set_header X-Forwarded-Proto $scheme;
                     		proxy_set_header X-Forwarded-Port $server_port;
        			proxy_pass http://127.0.0.1:8080/api/;
        		}
        
        		# Proxy pass for stomp client
        		location /stomp/ {
        			proxy_pass http://127.0.0.1:8080/stomp/;
        			proxy_http_version 1.1;
        			proxy_set_header Upgrade $http_upgrade;
        			proxy_set_header Connection "Upgrade";
            			proxy_set_header Host $host;
        		}
            }
      ```
  - Architecture diagram:
    ![AWS architecture](https://github.com/Puskin2911/SE04-Group-23.1/blob/v1.0-dev/refs/aws_architecture.png)
## My Team
- We are "Ngáo Cần Team", we came from K63 Department of Computer and Information Science, VNU University Of Science.
- We are:
  - Nguyễn Thế Hợp
  - Vũ Thu Thanh
  - Nguyễn Hải Đăng
  - Nguyễn Văn An 
  - Trần Thị Bích Phượng