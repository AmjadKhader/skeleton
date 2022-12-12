#### Guides

Please consider to have to following sections inorder for a successful run:

* Java 8 installed in you local machine
* SQL database and **import** the .sql file to the database, make sure you update connection configuration in
  application.yml file if needed.
* If you changed the application.yml make sure you do a maven install.

#### Endpoints

* http://localhost:8082/authentication/login

#### Usage

1. Call the **`authentication/login`** api using the following credentials:

`username: amjad`

`password: 1234`

After successful login you will receive a token. Keep it somewhere safe because you will need it on every call next as a
authentication bearer.

#### **Note:**  Make sure to send the token you received on login as a bearer token authorization to each other request.

## Dockerized way
### Use the following steps to run on docker container:

Let's build a docker image first.
    
    docker build -t skeleton-service-container .

Now, Let's see the magic ...

    docker compose up 

Open another terminal session and do the following to import needed data to the database

    docker container exec -it db bash

    mysql -uamjad -p

And use **password** as a password

Use the following command to set up your database.

    USE skeleton;
    
    CREATE TABLE `userLoginRequest` (
    `user_id` int NOT NULL AUTO_INCREMENT,
    `username` varchar(10) NOT NULL,
    `password` text NOT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `user_id_UNIQUE` (`user_id`),
    UNIQUE KEY `username_UNIQUE` (`username`)
    );

    INSERT INTO `userLoginRequest` (user_id, username, password) VALUES (1 , 'amjad' , 'a243d9d9fa83288ee8dfb7fafb48360afec352eaf638061820c9af9db1ddc99d49b6dc8f920f169eff76a5b57ba420a7049ff1fbecf6eeebadae63ac17349ecd');


#### Now you can use postman to use the above-mentioned endpoints as below.

* http://0.0.0.0:8082/userLoginRequest/login (POST)
