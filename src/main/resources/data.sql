INSERT INTO ACCOUNT (account_balance, account_holder_email, account_holder_name, account_number)
VALUES (15900, 'johndoe@gmail.com','John Doe', '111'), (890, 'samfedrik@hotmail.com','Sam Fedrik', '222');

INSERT INTO Transaction (transaction_date, transaction_amount, transaction_type, transaction_description, account_id)
VALUES  ('2023-04-20', 4000, 'credited', 'test 1', 1),
        ('2023-03-20', 370, 'credited', 'test 2', 1),
        ('2023-03-20', 10980, 'credited', 'test 3', 1),
        ('2023-03-21', 50, 'credited', 'test 4', 1),
        ('2023-03-22', 10, 'credited', 'test 5', 1),
        ('2023-03-23', 20, 'credited', 'test 6', 1),
        ('2023-03-24', 30, 'credited', 'test 7', 1),
        ('2023-03-25', 40, 'credited', 'test 8', 1),
        ('2023-03-25', 50, 'credited', 'test 9', 1),
        ('2023-03-26', 60, 'credited', 'test 10', 1),
        ('2023-03-27', 70, 'credited', 'test 11', 1),
        ('2023-03-28', 80, 'credited', 'test 12', 1),
        ('2023-03-29', 90, 'credited', 'test 13', 1),
        ('2023-03-30', 200, 'credited', 'test 14', 1),
        ('2023-04-01', 100, 'debited', 'test 15', 1),
        ('2023-04-05', 50, 'debited', 'test 16', 1),
        ('2023-04-06', 30, 'debited', 'test 17', 1),
        ('2023-04-06', 40, 'debited', 'test 18', 1),
        ('2023-04-07', 50, 'debited', 'test 19', 1),
        ('2023-04-08', 60, 'credited', 'test 20', 1),
        ('2023-04-09', 60, 'credited', 'test 21', 1),
        ('2023-03-09', 890, 'credited', 'test 21', 2);