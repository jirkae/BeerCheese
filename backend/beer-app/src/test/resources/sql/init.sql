INSERT INTO `user` (`id`, `login`, `password`, `first_name`, `last_name`, `birthday`, `phone_number`, `email`, `security_question`, `security_answer`)
VALUES ('1', 'dummy', 'dummyEncryptedPassword', 'Jan', 'Jiri', '2011/12/31', '+420777888777', 'dummy@dummy.io',
        'Are you dummy?', 'Yes');

INSERT INTO `user_role` (`user`, `role`) VALUES (1, 1);

INSERT INTO `address` (`id`, `user`, `street`, `city`, `country`, `note`) VALUES (1, 1, 'shitty street 7', 'shittown', 1, 'yolo');
INSERT INTO `address` (`id`, `user`, `street`, `city`, `country`, `note`) VALUES (2, NULL, 'shitty street 8', 'shittown 2', 1, 'yolo');