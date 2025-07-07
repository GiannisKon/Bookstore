CREATE DATABASE  IF NOT EXISTS `bookstore`;
USE `bookstore`;

DROP TABLE IF EXISTS `books`;
DROP TABLE IF EXISTS `book_authors`;
DROP TABLE IF EXISTS `book_categories`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `user_profiles`;
DROP TABLE IF EXISTS `book_write`;
DROP TABLE IF EXISTS `favourite_authors`;
DROP TABLE IF EXISTS `favourite_categories`;
DROP TABLE IF EXISTS `book_request`;

CREATE TABLE `users` (
  `user_name` varchar(255),
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user_profiles` (
  `user_name` varchar(255),
  `fullname` varchar(255) DEFAULT NULL,
  `phonenumber` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `age` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `book_authors` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `unique_name` UNIQUE (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `book_categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `unique_name` UNIQUE (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `books` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `user_profile_id` varchar(255) DEFAULT NULL,
  `book_category_id` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_profile_id`) REFERENCES `user_profiles` (`user_name`),
  FOREIGN KEY (`book_category_id`) REFERENCES `book_categories` (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `favourite_authors` (
  `user_profile_id` varchar(255) DEFAULT NULL,
  `book_author_id` int NOT NULL DEFAULT 0,
  FOREIGN KEY (`user_profile_id`) REFERENCES `user_profiles` (`user_name`),
  FOREIGN KEY (`book_author_id`) REFERENCES `book_authors` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `favourite_categories` (
  `user_profile_id` varchar(255) DEFAULT NULL,
  `book_category_id` int NOT NULL DEFAULT 0,
  FOREIGN KEY (`user_profile_id`) REFERENCES `user_profiles` (`user_name`),
  FOREIGN KEY (`book_category_id`) REFERENCES `book_categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `book_write` (
  `book_id` int NOT NULL DEFAULT 0,
  `book_author_id` int NOT NULL DEFAULT 0,
  FOREIGN KEY (`book_id`) REFERENCES `books` (`id`),
  FOREIGN KEY (`book_author_id`) REFERENCES `book_authors` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `book_request` (
  `user_profile_id` varchar(255) DEFAULT NULL,
  `book_id` int NOT NULL DEFAULT 0,
  FOREIGN KEY (`user_profile_id`) REFERENCES `user_profiles` (`user_name`),
  FOREIGN KEY (`book_id`) REFERENCES `books` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
