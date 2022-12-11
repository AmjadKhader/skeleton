USE skeleton;

CREATE TABLE `user` (
`user_id` int NOT NULL AUTO_INCREMENT,
`username` varchar(10) NOT NULL,
`password` text NOT NULL,
PRIMARY KEY (`user_id`),
UNIQUE KEY `user_id_UNIQUE` (`user_id`),
UNIQUE KEY `username_UNIQUE` (`username`)
);

INSERT INTO `user` (user_id, username, password) VALUES (1 , 'amjad' , 'a243d9d9fa83288ee8dfb7fafb48360afec352eaf638061820c9af9db1ddc99d49b6dc8f920f169eff76a5b57ba420a7049ff1fbecf6eeebadae63ac17349ecd');
