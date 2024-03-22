INSERT INTO "category" (name, type) VALUES
                                        ('Food & Drinks', 'debit'),
                                        ('Shopping', 'debit'),
                                        ('Housing', 'debit'),
                                        ('Transportation', 'debit'),
                                        ('Vehicle', 'debit'),
                                        ('Life & Entertainment', 'debit'),
                                        ('Communication, PC', 'debit'),
                                        ('Financial expenses', 'debit'),
                                        ('Investments', 'debit'),
                                        ('Income', 'credit');


INSERT INTO "sub_category" (name, category_id) VALUES
                                                   ('Bar, cafe', (SELECT id FROM "category" WHERE name = 'Food & Drinks')),
                                                   ('Groceries', (SELECT id FROM "category" WHERE name = 'Food & Drinks')),
                                                   ('Restaurant, fast-food', (SELECT id FROM "category" WHERE name = 'Food & Drinks')),
                                                   ('Shopping', (SELECT id FROM "category" WHERE name = 'Shopping')),
                                                   ('Housing', (SELECT id FROM "category" WHERE name = 'Housing')),
                                                   ('Transportation', (SELECT id FROM "category" WHERE name = 'Transportation')),
                                                   ('Vehicle', (SELECT id FROM "category" WHERE name = 'Vehicle')),
                                                   ('Life & Entertainment', (SELECT id FROM "category" WHERE name = 'Life & Entertainment')),
                                                   ('Communication, PC', (SELECT id FROM "category" WHERE name = 'Communication, PC')),
                                                   ('Financial expenses', (SELECT id FROM "category" WHERE name = 'Financial expenses')),
                                                   ('Investments', (SELECT id FROM "category" WHERE name = 'Investments')),
                                                   ('Income', (SELECT id FROM "category" WHERE name = 'Income'));

INSERT INTO "category" (name, type) VALUES
                                        ('Salary', 'credit'),
                                        ('Investment Returns', 'credit'),
                                        ('Freelance Income', 'credit'),
                                        ('Rental Income', 'credit');

INSERT INTO "sub_category" (name, category_id) VALUES
                                                   ('Regular Salary', (SELECT id FROM "category" WHERE name = 'Salary')),
                                                   ('Bonus Income', (SELECT id FROM "category" WHERE name = 'Salary')),
                                                   ('Dividend Payments', (SELECT id FROM "category" WHERE name = 'Investment Returns')),
                                                   ('Capital Gains', (SELECT id FROM "category" WHERE name = 'Investment Returns')),
                                                   ('Consulting Fees', (SELECT id FROM "category" WHERE name = 'Freelance Income')),
                                                   ('Contract Work', (SELECT id FROM "category" WHERE name = 'Freelance Income')),
                                                   ('Property Rent', (SELECT id FROM "category" WHERE name = 'Rental Income')),
                                                   ('Royalties', (SELECT id FROM "category" WHERE name = 'Rental Income'));