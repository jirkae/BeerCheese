SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'TRADITIONAL,ALLOW_INVALID_DATES';

USE `beer`;

CREATE TABLE IF NOT EXISTS `supplier` (
  `id`            INT          NOT NULL AUTO_INCREMENT,
  `name`          VARCHAR(255) NOT NULL,
  `phone_number`  VARCHAR(45)  NULL,
  `delivery_time` FLOAT        NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE IF NOT EXISTS `category` (
  `id`            INT          NOT NULL AUTO_INCREMENT,
  `name`          VARCHAR(255) NOT NULL,
  `main_category` INT          NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_category_category1_idx` (`main_category` ASC),
  CONSTRAINT `fk_category_category1`
  FOREIGN KEY (`main_category`)
  REFERENCES `category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE IF NOT EXISTS `product` (
  `id`                   INT           NOT NULL AUTO_INCREMENT,
  `supplier`             INT           NOT NULL,
  `category`             INT           NOT NULL,
  `name`                 VARCHAR(255)  NOT NULL,
  `price`                FLOAT         NOT NULL,
  `quantity`             INT           NOT NULL,
  `price_after_discount` FLOAT         NULL,
  `active`               INT(1)        NOT NULL,
  `image`                VARCHAR(255)  NULL,
  `description`          VARCHAR(2048) NULL,
  `message`              VARCHAR(2048) NULL,
  PRIMARY KEY (`id`, `category`),
  INDEX `fk_Product_Supplier_idx` (`supplier` ASC),
  INDEX `fk_product_category1_idx` (`category` ASC),
  CONSTRAINT `fk_Product_Supplier`
  FOREIGN KEY (`supplier`)
  REFERENCES `supplier` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_category1`
  FOREIGN KEY (`category`)
  REFERENCES `category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE IF NOT EXISTS `user` (
  `id`                INT          NOT NULL AUTO_INCREMENT,
  `login`             VARCHAR(255) NOT NULL,
  `password`          VARCHAR(255) NOT NULL,
  `first_name`        VARCHAR(255) NOT NULL,
  `last_name`         VARCHAR(255) NOT NULL,
  `birthday`          DATE         NULL,
  `phone_number`      VARCHAR(45)  NULL,
  `email`             VARCHAR(255) NOT NULL,
  `security_question` VARCHAR(255) NULL,
  `security_answer`   VARCHAR(255) NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE IF NOT EXISTS `order_status` (
  `id`   INT          NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE IF NOT EXISTS `payment_type` (
  `id`   INT          NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE IF NOT EXISTS `shipping` (
  `id`            INT          NOT NULL AUTO_INCREMENT,
  `name`          VARCHAR(255) NOT NULL,
  `price`         FLOAT        NOT NULL,
  `delivery_time` FLOAT        NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE IF NOT EXISTS `country` (
  `id`   INT          NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE IF NOT EXISTS `address` (
  `id`      INT           NOT NULL AUTO_INCREMENT,
  `street`  VARCHAR(255)  NOT NULL,
  `city`    VARCHAR(255)  NOT NULL,
  `country` INT           NOT NULL,
  `note`    VARCHAR(1024) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_address_country1_idx` (`country` ASC),
  CONSTRAINT `fk_address_country1`
  FOREIGN KEY (`country`)
  REFERENCES `country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE IF NOT EXISTS `order` (
  `id`               INT   NOT NULL AUTO_INCREMENT,
  `user`             INT   NOT NULL,
  `order_status`     INT   NOT NULL,
  `payment_type`     INT   NOT NULL,
  `shipping`         INT   NOT NULL,
  `shipping_address` INT   NOT NULL,
  `billing_address`  INT   NOT NULL,
  `discount`         FLOAT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Order_User1_idx` (`user` ASC),
  INDEX `fk_order_order_status1_idx` (`order_status` ASC),
  INDEX `fk_order_payment_type1_idx` (`payment_type` ASC),
  INDEX `fk_order_shipping1_idx` (`shipping` ASC),
  INDEX `fk_order_address1_idx` (`shipping_address` ASC),
  INDEX `fk_order_address2_idx` (`billing_address` ASC),
  CONSTRAINT `fk_Order_User1`
  FOREIGN KEY (`user`)
  REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_order_status1`
  FOREIGN KEY (`order_status`)
  REFERENCES `order_status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_payment_type1`
  FOREIGN KEY (`payment_type`)
  REFERENCES `payment_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_shipping1`
  FOREIGN KEY (`shipping`)
  REFERENCES `shipping` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_address1`
  FOREIGN KEY (`shipping_address`)
  REFERENCES `address` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_address2`
  FOREIGN KEY (`billing_address`)
  REFERENCES `address` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE IF NOT EXISTS `role` (
  `id`   INT         NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE IF NOT EXISTS `user_role` (
  `user` INT NOT NULL,
  `role` INT NOT NULL,
  PRIMARY KEY (`user`, `role`),
  INDEX `fk_User_has_Role_Role1_idx` (`role` ASC),
  INDEX `fk_User_has_Role_User1_idx` (`user` ASC),
  CONSTRAINT `fk_User_has_Role_User1`
  FOREIGN KEY (`user`)
  REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Role_Role1`
  FOREIGN KEY (`role`)
  REFERENCES `role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE IF NOT EXISTS `package` (
  `id`    INT NOT NULL AUTO_INCREMENT,
  `order` INT NOT NULL,
  PRIMARY KEY (`id`, `order`),
  INDEX `fk_package_order1_idx` (`order` ASC),
  CONSTRAINT `fk_package_order1`
  FOREIGN KEY (`order`)
  REFERENCES `order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE IF NOT EXISTS `product_package` (
  `product`  INT   NOT NULL AUTO_INCREMENT,
  `package`  INT   NOT NULL,
  `quantity` INT   NOT NULL,
  `price`    FLOAT NOT NULL,
  PRIMARY KEY (`product`, `package`),
  INDEX `fk_product_has_package_package1_idx` (`package` ASC),
  INDEX `fk_product_has_package_product1_idx` (`product` ASC),
  CONSTRAINT `fk_product_has_package_product1`
  FOREIGN KEY (`product`)
  REFERENCES `product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_has_package_package1`
  FOREIGN KEY (`package`)
  REFERENCES `package` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE IF NOT EXISTS `token` (
  `id`         INT           NOT NULL AUTO_INCREMENT,
  `user`       INT           NOT NULL,
  `token`      VARCHAR(1000) NOT NULL,
  `created`    DATETIME      NOT NULL,
  `expiration` DATETIME      NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARACTER SET = utf8mb4;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
