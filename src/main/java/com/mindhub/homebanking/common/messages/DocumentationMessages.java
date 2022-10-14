package com.mindhub.homebanking.common.messages;

public final class DocumentationMessages {

    // Account Controller
    public static final String ACCOUNT_CONTROLLER_ADMIN_GET = "Lists all accounts";
    public static final String ACCOUNT_CONTROLLER_ADMIN_GET_DESCRIPTION = "Lists all accounts of all clients - Admin only";
    public static final String ACCOUNT_CONTROLLER_ID = "Lists an account";
    public static final String ACCOUNT_CONTROLLER_ID_DESCRIPTION = "Provide an ID to find the corresponding account";
    public static final String ACCOUNT_CONTROLLER_CREATE = "Creates an account";
    public static final String ACCOUNT_CONTROLLER_CREATE_DESCRIPTION = "The client can create a new account";
    public static final String ACCOUNT_CONTROLLER_GET = "Lists all accounts";
    public static final String ACCOUNT_CONTROLLER_GET_DESCRIPTION = "Lists all accounts belonging to the authenticated client";
    public static final String ACCOUNT_CONTROLLER_TYPES = "Account types";
    public static final String ACCOUNT_CONTROLLER_TYPES_DESCRIPTION = "Lists all existing account types";
    public static final String ACCOUNT_CONTROLLER_DELETE = "Deletes an account";
    public static final String ACCOUNT_CONTROLLER_DELETE_DESCRIPTION = "Provide an account number to perform a soft delete over it";

    // Card Controller
    public static final String CARD_CONTROLLER_CREATE = "Creates a card";
    public static final String CARD_CONTROLLER_CREATE_DESCRIPTION = "The client can create a new card choosing type and color";
    public static final String CARD_CONTROLLER_GET = "Lists all cards";
    public static final String CARD_CONTROLLER_GET_DESCRIPTION = "Lists all cards belonging to the authenticated client";
    public static final String CARD_CONTROLLER_DELETE = "Deletes a card";
    public static final String CARD_CONTROLLER_DELETE_DESCRIPTION = "Provide a card number to perform a soft delete over it";

    // Client Controller
    public static final String CLIENT_CONTROLLER_ADMIN_GET = "Lists all clients";
    public static final String CLIENT_CONTROLLER_ADMIN_GET_DESCRIPTION = "Lists all clients - Admin only";
    public static final String CLIENT_CONTROLLER_DETAILS = "Clients details";
    public static final String CLIENT_CONTROLLER_DETAILS_DESCRIPTION = "Returns the authenticated client details of profile, accounts, cards, loans";
    public static final String CLIENT_CONTROLLER_CREATE = "Creates a client";
    public static final String CLIENT_CONTROLLER_CREATE_DESCRIPTION = "Creates a new Client with CLIENT authority";
    public static final String CLIENT_CONTROLLER_ADMIN_DELETE = "Deletes a client";
    public static final String CLIENT_CONTROLLER_ADMIN_DELETE_DESCRIPTION = "Provide a client ID to perform a soft delete over it - Admin only";

    // Loan Controller
    public static final String LOAN_CONTROLLER_GET = "List of Loans";
    public static final String LOAN_CONTROLLER_GET_DESCRIPTION = "Lists all the loans available";
    public static final String LOAN_CONTROLLER_CREATE = "Creates a loan";
    public static final String LOAN_CONTROLLER_CREATE_DESCRIPTION = "The authenticated client can apply to a loan";

    // Payment Controller
    public static final String PAYMENT_CONTROLLER_CREATE = "Make a payment";
    public static final String PAYMENT_CONTROLLER_CREATE_DESCRIPTION =
            "Performs a payment from a client's credit card. " +
                    "It is not necessary to be logged to use it";

    // Transaction Controller
    public static final String TRANSACTION_CONTROLLER_CREATE = "Make a transaction";
    public static final String TRANSACTION_CONTROLLER_CREATE_DESCRIPTION = "Performs a transaction between accounts ";

}
