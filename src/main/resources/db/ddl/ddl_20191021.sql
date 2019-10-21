CREATE TABLE `ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date` datetime(6) DEFAULT NULL,
  `order_id` varchar(255) NOT NULL,
  `ticket_info_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `ticket_info` (
  `id` varchar(255) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `last_update_date` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2quh0fo5du8jw07gdkqps15x2` (`name`)
);

CREATE TABLE `ticket_lock_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `count` int(11) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `order_id` varchar(255) NOT NULL,
  `ticket_info_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);
