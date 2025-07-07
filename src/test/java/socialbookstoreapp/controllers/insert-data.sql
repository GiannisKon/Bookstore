INSERT INTO `users` (`user_name`, `password`, `role`) VALUES ('user', 'user', 'USER');
INSERT INTO `users` (`user_name`, `password`, `role`) VALUES ('user1', 'user1', 'USER');

INSERT INTO `user_profiles` (`user_name`, `fullname`, `phonenumber`, `address`, `age`) VALUES ('user', 'user', '6969696969', 'Ioannina', 18);
INSERT INTO `user_profiles` (`user_name`, `fullname`, `phonenumber`, `address`, `age`) VALUES ('user1', 'user1', '6969696969', 'Ioannina', 18);

INSERT INTO `book_authors` (id, `name`) VALUES (1, 'author');
INSERT INTO `book_categories` (id, `name`) VALUES (1, 'comedy');

INSERT INTO `favourite_authors` (user_profile_id, book_author_id) VALUES ('user', 1);
INSERT INTO `favourite_categories` (user_profile_id, book_category_id) VALUES ('user', 1);

INSERT INTO `books` (id, `title`, `summary`, `user_profile_id`, book_category_id) VALUES (1, 'book', 'book', 'user1', 1);
INSERT INTO `books` (id, `title`, `summary`, `user_profile_id`, book_category_id) VALUES (2, 'super book', 'super book', 'user1', 1);

INSERT INTO `book_write` (book_id, `book_author_id`) VALUES (1, 1);
INSERT INTO `book_write` (book_id, `book_author_id`) VALUES (2, 1);