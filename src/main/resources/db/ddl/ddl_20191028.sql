CREATE TABLE `customers_audit`
(
    `id`         int(11)      NOT NULL AUTO_INCREMENT,
    `createDate` datetime(6)  NOT NULL,
    `operation`  varchar(255) NOT NULL,
    `userId`     int(11)      NOT NULL,
    PRIMARY KEY (`id`)
);
