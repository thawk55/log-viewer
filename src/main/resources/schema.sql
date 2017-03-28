CREATE TABLE logs (
    `id` int(11) AUTO_INCREMENT,
    `level` varchar(255),
    `dateTime` varchar(255),
    `source` varchar(255),
    `eventId` int(11),
    `taskCategory` varchar(255),
    `info` text,
    PRIMARY KEY (id)
);
