INSERT INTO `users` (`user_name`, `password`, `role`) VALUES ('user', 'user', 'USER');
INSERT INTO `user_profiles` (`user_name`, `fullname`, `phonenumber`, `address`, `age`) VALUES ('user', 'user', '6969696969', 'Ioannina', 18);
INSERT INTO `book_categories` (id, `name`) VALUES (1, 'comedy');
INSERT INTO `books` (id, `title`, `summary`, `user_profile_id`, book_category_id) VALUES (1, 'book', 'book', 'user', 1);
INSERT INTO `books` (id, `title`, `summary`, `user_profile_id`, book_category_id) VALUES (2, 'super book', 'super book', 'user', 1);