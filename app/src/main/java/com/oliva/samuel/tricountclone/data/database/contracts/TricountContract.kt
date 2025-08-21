package com.oliva.samuel.tricountclone.data.database.contracts

object TricountContract {
    // BBDD Definition
    const val DATABASE_NAME = "tricount.db"
    const val DATABASE_VERSION = 1

    // User Table Definition
    const val TABLE_USERS = "users"
    const val TABLE_USERS_COLUMN_ID = "userId"
    const val TABLE_USERS_COLUMN_NAME = "name"

    // Tricount Table Definition
    const val TABLE_TRICOUNT = "tricount"
    const val TABLE_TRICOUNT_COLUMN_ID = "tricountId"
    const val TABLE_TRICOUNT_COLUMN_TITLE = "title"
    const val TABLE_TRICOUNT_COLUMN_ICON = "icon"
    const val TABLE_TRICOUNT_COLUMN_CURRENCY = "currency"
    const val TABLE_TRICOUNT_COLUMN_CREATED_BY = "createdBy" // UserEntity
    const val TABLE_TRICOUNT_COLUMN_CREATED_AT = "createdAt" // Date

    // Participant Table Definition
    const val TABLE_PARTICIPANT = "participant"
    const val TABLE_PARTICIPANT_COLUMN_ID = "participantId"
    const val TABLE_PARTICIPANT_COLUMN_NAME = "name"
    const val TABLE_PARTICIPANT_COLUMN_JOINED_AT = "joinedAt" // Date
    const val TABLE_PARTICIPANT_COLUMN_USER_ID = "userId" // UserEntity?
    const val TABLE_PARTICIPANT_COLUMN_TRICOUNT_ID = "tricountId" // TricountEntity

    // Expense Table Definition
    const val TABLE_EXPENSE = "expense"
    const val TABLE_EXPENSE_COLUMN_ID = "expenseId"
    const val TABLE_EXPENSE_COLUMN_TITLE = "title"
    const val TABLE_EXPENSE_COLUMN_AMOUNT = "amount"
    const val TABLE_EXPENSE_COLUMN_PAID_BY = "paidBy" // ParticipantEntity
    const val TABLE_EXPENSE_COLUMN_CREATED_AT = "createdAt" // Date
    const val TABLE_EXPENSE_COLUMN_TRICOUNT_ID = "tricountId" // TricountEntity
    const val TABLE_EXPENSE_COLUMN_NOTE = "note"

    // Expense Share Table Definition
    const val TABLE_EXPENSE_SHARE = "expenseShare"
    const val TABLE_EXPENSE_SHARE_COLUMN_EXPENSE_ID = "expenseId" // ExpenseEntity
    const val TABLE_EXPENSE_SHARE_COLUMN_PARTICIPANTS_ID = "participantsId" // ParticipantEntity
    const val TABLE_EXPENSE_SHARE_COLUMN_AMOUNT_OWNED = "amountOwner"
}
