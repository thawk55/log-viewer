CREATE TABLE logs (
    `id` int(11) AUTO_INCREMENT,
    `version` varchar(255),
    `channel` varchar(255),
    `level` varchar(255),
    `opcode` varchar(255),
    `task` int(11),
    `keyword` int(20),
    PRIMARY KEY (id)
);