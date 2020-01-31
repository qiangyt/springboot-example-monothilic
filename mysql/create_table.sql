USE order_db;



-- ############################################################################
-- account
-- ############################################################################
CREATE TABLE account (
  `id`                  VARCHAR(36) CHARACTER SET latin1     NOT NULL,

  `name`                VARCHAR(32)  NOT NULL,
  `first_name`          VARCHAR(32)  NOT NULL,
  `second_name`         VARCHAR(32)  NOT NULL,
  `password`            VARCHAR(32)  NOT NULL,
  `address`             VARCHAR(100),
  `roles`               VARCHAR(64)  NOT NULL,

  `updated_at`          DATETIME                             NOT NULL,
  `created_at`          DATETIME                             NOT NULL,

  PRIMARY KEY (id),
  UNIQUE(name),
  UNIQUE(first_name, second_name)
);



-- ############################################################################
-- product
-- ############################################################################
CREATE TABLE product (
  `id`          VARCHAR(36) CHARACTER SET latin1    NOT NULL,

  `name`        VARCHAR(32)                         NOT NULL,
  `amount`      INT                                 NOT NULL,

  `updated_at`  DATETIME                            NOT NULL,
  `created_at`  DATETIME                            NOT NULL,

  PRIMARY KEY (id),
  UNIQUE(name)
);


-- ############################################################################
-- order
-- ############################################################################
CREATE TABLE `order` (
  `id`                  VARCHAR(36) CHARACTER SET latin1     NOT NULL,

  `customer_account_id` VARCHAR(36) CHARACTER SET latin1     NOT NULL,
  `product_id`          VARCHAR(36) CHARACTER SET latin1     NOT NULL,

  `amount`              INT                                  NOT NULL,

  `updated_at`          DATETIME                             NOT NULL,
  `created_at`          DATETIME                             NOT NULL,

  PRIMARY KEY (id)
);

CREATE INDEX idx_order_1  ON `order`(customer_account_id);
CREATE INDEX idx_order_2  ON `order`(product_id);

