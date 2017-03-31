CREATE TABLE `logs` (
    `id` int(11) AUTO_INCREMENT,
    `level` varchar(255),
    `date_time` varchar(255),
    `source` varchar(255),
    `event_id` int(11),
    `task_category` varchar(255),
    `info` text,
    PRIMARY KEY (id)
);


CREATE INDEX `logs_level_index` ON `logs` (`level`);
CREATE INDEX `logs_datetime_index` ON `logs` (`date_time`);
CREATE INDEX `logs_source_index` ON `logs` (`source`);
CREATE INDEX `logs_event_id_index` ON `logs` (`event_id`);
CREATE INDEX `logs_task_category_index` ON `logs` (`task_category`);