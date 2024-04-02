## DOCUMENTATION
link : https://petstore3.swagger.io/?url=https://raw.githubusercontent.com/digital-bank-wallet-manager/back-end/Prod/docs/bank-wallet-management-spec.yaml

## initialisation 
- d-bank/merise_models/mlp/create_table.sql : SQL script to create database with all its table
- d-bank/merise_models/mlp/insertCategory.sql : SQL script to insert all required categories

## features

# account 
- create new account with auto-generated account-ref
- list off all accounts with all its information

# provisioning
- credit an account from an outsider account
- scheduled date effect ( you can choose when do you want it to be applied )

# transfer 
- multiple transfer
- local and foreign transfer
- scheduled transfer
- cancel pending transfer

# loan money
- edit interest for first seven day and above seven day
- repay whenever the customer want

# bank statement
- see each account transaction by month
- download as PDF file

# balance
- see each account balance state with loan and interest
- filter by date

# dashboard
- visualize the rate of income and expense
- filter by period
