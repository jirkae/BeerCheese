INSERT INTO `user` (`id`, `login`, `password`, `first_name`, `last_name`, `birthday`, `phone_number`, `email`, `security_question`, `security_answer`)
VALUES ('1', 'dummy', '$2a$10$abqFjbMuW8wadiKbOMpguuM5WfJK1i9J4mRE/qKEy82Uz/VjgJbsW', 'Jan', 'Jiri', '2011/12/31',
        '+420777888777', 'dummy@dummy.io',
        'Are you dummy?', 'Yes');
INSERT INTO `user` (`id`, `login`, `password`, `first_name`, `last_name`, `birthday`, `phone_number`, `email`, `security_question`, `security_answer`)
VALUES ('2', 'user', '$2a$10$abqFjbMuW8wadiKbOMpguuM5WfJK1i9J4mRE/qKEy82Uz/VjgJbsW', 'Ivan', 'Jiri', '2011/12/31',
        '+420777888777', 'dummy@dummy.io',
        'Are you dummy?', 'Yes');

INSERT INTO `user_role` (`user`, `role`) VALUES (1, 1);
INSERT INTO `user_role` (`user`, `role`) VALUES (2, 2);

INSERT INTO `address` (`id`, `street`, `city`, `country`, `note`)
VALUES (1, 'shitty street 7', 'shittown', 1, 'yolo');
INSERT INTO `address` (`id`, `street`, `city`, `country`, `note`)
VALUES (2, 'shitty street 8', 'shittown 2', 1, 'yolo');

INSERT INTO `supplier` (`id`, `name`, `phone_number`, `delivery_time`) VALUES (1, 'YoloCorp', '+420789098678', 3);

INSERT INTO `product` (`id`, `supplier`, `category`, `name`, `price`, `quantity`, `price_after_discount`, `active`, `image`, `description`)
VALUES (1, 1, 1, 'Beer', 100, 2, 90, 1, 'guhppkyg5c', 'test description');

INSERT INTO `token` (`id`, `user`, `token`, `created`, `expiration`)
VALUES (1, 1, 'C6zQDwexloGL8KDMuEyE3zVdiExxJtrj', '1970/12/1', '1970/12/2');
INSERT INTO `token` (`id`, `user`, `token`, `created`, `expiration`)
VALUES (2, 1, 'Lut9VKxv6nUWAAWI2yCvdD0z7FVTBQ5x', '2018/12/1', '2018/12/1');

INSERT INTO `order` (`id`, `user`, `order_status`, `payment_type`, `shipping`, `shipping_address`, `billing_address`, `discount`)
VALUES (1, 2, 1, 1, 1, 1, 2, 100);

INSERT INTO `package` (`id`, `order`) VALUES (1, 1);

INSERT INTO `product_package` (`product`, `package`, `quantity`, `price`, `message`) VALUES (1, 1, 1, 1, NULL);