package com.oliva.samuel.tricountclone.data.database.dao

import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.oliva.samuel.tricountclone.data.database.TricountDatabase
import com.oliva.samuel.tricountclone.data.database.entities.ExpenseEntity
import com.oliva.samuel.tricountclone.data.database.entities.ParticipantEntity
import com.oliva.samuel.tricountclone.data.database.entities.TricountEntity
import com.oliva.samuel.tricountclone.data.database.entities.UserEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date


@RunWith(AndroidJUnit4::class)
@SmallTest
class TricountDaoTest {
    private lateinit var database: TricountDatabase
    private lateinit var userDao: UserDao
    private lateinit var tricountDao: TricountDao
    private lateinit var participantDao: ParticipantDao
    private lateinit var expenseDao: ExpenseDao

    // Usuarios de ejemplo
    private val existingUser1 = UserEntity(name = "Name1")
    private val existingUser2 = UserEntity(name = "Name2")
    private val existingUser3 = UserEntity(name = "Name3")

    // Tricounts de ejemplo
    private val tricount1fromUser1 = TricountEntity(
        title = "Tricount 1",
        icon = "icon1",
        currency = "EUR",
        createdBy = existingUser1.id,
        createdAt = Date()
    )
    private val tricount2fromUser1 = TricountEntity(
        title = "Tricount 2",
        icon = "icon2",
        currency = "USD",
        createdBy = existingUser1.id,
        createdAt = Date()
    )

    // Participants de ejemplo
    private val participant1fromTricount1 = ParticipantEntity(
        name = "Participant 1.1",
        joinedAt = Date(),
        userId = existingUser1.id,
        tricountId = tricount1fromUser1.id
    )

    private val participant2fromTricount1 = ParticipantEntity(
        name = "Participant 1.2",
        joinedAt = Date(),
        userId = null,
        tricountId = tricount1fromUser1.id
    )

    private val participant1fromTricount2 = ParticipantEntity(
        name = "Participant 2.1",
        joinedAt = Date(),
        userId = existingUser1.id,
        tricountId = tricount2fromUser1.id
    )

    private val participant2fromTricount2 = ParticipantEntity(
        name = "Participant 2.2",
        joinedAt = Date(),
        userId = existingUser2.id,
        tricountId = tricount2fromUser1.id
    )

    // Expenses de ejemplo
    private val expense1fromTricount1 = ExpenseEntity(
        title = "Expense 1.1",
        amount = 50.0,
        paidBy = participant1fromTricount1.id,
        createdAt = Date(),
        tricountId = tricount1fromUser1.id,
        note = "Dinner"
    )

    private val expense2fromTricount1 = ExpenseEntity(
        title = "Expense 1.2",
        amount = 30.0,
        paidBy = participant2fromTricount1.id,
        createdAt = Date(),
        tricountId = tricount1fromUser1.id,
        note = "Drinks"
    )

    private val expense1fromTricount2 = ExpenseEntity(
        title = "Expense 2.1",
        amount = 50.0,
        paidBy = participant1fromTricount2.id,
        createdAt = Date(),
        tricountId = tricount2fromUser1.id,
        note = "Dinner"
    )

    private val expense2fromTricount2 = ExpenseEntity(
        title = "Expense 2.2",
        amount = 30.0,
        paidBy = participant2fromTricount2.id,
        createdAt = Date(),
        tricountId = tricount2fromUser1.id,
        note = "Drinks"
    )

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TricountDatabase::class.java
        ).allowMainThreadQueries().build()

        userDao = database.userDao()
        tricountDao = database.tricountDao()
        participantDao = database.participantDao()
        expenseDao = database.expenseDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    ////////////////////////////////////////////////////////////////
    /// Insert Tricount test cases:
    ///  1. Insert on empty Table with no users -> Insert FAIL
    ///  1. Insert on empty Table with users -> Insert OK
    ///  2. Insert on non empty Table no collision -> Insert OK
    ///  3. Insert on non empty Table with collision -> Override
    ////////////////////////////////////////////////////////////////

    /**
     * 1. Insert on empty Table with no users -> Insert FAIL
     */
    @Test
    fun insertTricount_whenEmptyTable_withNoUsers_shouldFail() = runTest {
        // Given: No users in DB

        // And: Tricount with random user (non-existent)
        val tricount = tricount1fromUser1

        // When/Then: Insert should fail due to FK constraint
        assertThrows(
            SQLiteConstraintException::class.java
        ) { runBlocking { tricountDao.insert(tricount) } }
    }

    /**
     * 1. Insert on empty Table with users -> Insert OK
     */
    @Test
    fun insertTricount_whenEmptyTable_withUsers_shouldInsertOk() = runTest {
        // Given: Empty Tricout table & User exists
        val insertUser = existingUser1
        val insertedTricount = tricount1fromUser1
        userDao.insert(insertUser)

        // When: Insert tricount
        tricountDao.insert(insertedTricount)

        // Then: Table only contains inserted tricount
        val tricounts = tricountDao.getAll().first()

        assertThat(tricounts).hasSize(1)
        assertThat(tricounts).containsExactly(insertedTricount)
    }

    /**
     * 2. Insert on non empty Table no collision -> Insert OK
     */
    @Test
    fun insertTricount_onNonEmptyTableNoCollision_shouldInsertOk() = runTest {
        // Given: Table with initial tricounts
        val insertUser = existingUser1
        val initialTricounts = listOf(tricount1fromUser1)
        val insertTricount = tricount2fromUser1
        val finalTricounts = initialTricounts.plusElement(insertTricount)

        userDao.insert(insertUser)
        initialTricounts.forEach { tricountDao.insert(it) }

        // When: Insert tricount2 (different ID, same user)
        tricountDao.insert(insertTricount)

        // Then: Both tricounts present
        val allTricounts = tricountDao.getAll().first()
        assertThat(allTricounts).hasSize(finalTricounts.size)
        assertThat(allTricounts).containsExactlyElementsIn(finalTricounts)
    }

    /**
     * 3. Insert on non empty Table with collision -> Override
     */
    @Test
    fun insertUser_whenDatabaseHasElements_withCollision() = runTest {
        // Given: Table with one element.
        val insertUser = existingUser1
        val initialTricount = tricount1fromUser1
        val insertTricount = tricount1fromUser1.copy(
            title = initialTricount.title.plus("OtherTitle")
        )

        userDao.insert(insertUser)
        tricountDao.insert(initialTricount)

        // When: Insert a tricount which ID already exists.
        tricountDao.insert(insertTricount)

        // Then: The previous user was replaced by the new one.
        val allTricounts = tricountDao.getAll().first()

        assertThat(allTricounts).hasSize(1)
        assertThat(allTricounts).containsExactly(insertTricount)
    }

    ////////////////////////////////////////////////////////////////
    /// Get All Tricounts test cases:
    ///  1. Get all on empty Table -> Empty list
    ///  2. Get all on non empty Table -> List contains only inserted elements.
    ///  3. Get all open flow notifies new insertion and deletions -> List contains inserted element
    ///  4. Get all open flow notifies updates -> List contains updated element
    ///  5. Get all open flow notifies updates -> List contains updated element
    ////////////////////////////////////////////////////////////////

    /**
     * 1. Get all on empty Table -> Empty list
     */
    @Test
    fun getAllTricounts_whenDatabaseIsEmpty() = runTest {
        // Given: Empty database.

        // When: Get all tricounts.
        val allTricounts = tricountDao.getAll().first()

        // Then: Returned list is empty.
        assertThat(allTricounts).isEmpty()
    }

    /**
     * 2. Get all on non empty Table -> List contains only inserted elements.
     */
    @Test
    fun getAllTricounts_whenDatabaseHasElements() = runTest {
        // Given: Existing user and inserted tricounts
        val user = existingUser1
        val insertedTricounts = listOf(tricount1fromUser1, tricount2fromUser1)

        userDao.insert(user)
        insertedTricounts.forEach { tricountDao.insert(it) }

        // When: Get all tricounts.
        val allTricounts = tricountDao.getAll().first()

        // Then: Returned list contains exactly the inserted tricounts.
        assertThat(allTricounts).hasSize(insertedTricounts.size)
        assertThat(allTricounts).containsExactlyElementsIn(insertedTricounts)
    }

    /**
     * 3. Get all open flow notifies new insertion -> List contains inserted element
     */
    @Test
    fun getAllTricounts_whenDatabaseStartsEmpty_flowNotifiesInsertions() = runTest {
        // Given: Existing user
        userDao.insert(existingUser1)

        // When: Get all tricounts.
        val allTricountsFlow = tricountDao.getAll()

        allTricountsFlow.test {
            // Then: Initial list is empty
            val firstEmptyEmission = awaitItem()
            assertThat(firstEmptyEmission).isEmpty()

            // Insert tricounts
            val insertedTricounts = mutableListOf<TricountEntity>()

            // First tricount
            tricountDao.insert(tricount1fromUser1)
            insertedTricounts.add(tricount1fromUser1)

            val firstInsertedEmission = awaitItem()
            assertThat(firstInsertedEmission).hasSize(insertedTricounts.size)
            assertThat(firstInsertedEmission).containsExactlyElementsIn(insertedTricounts)

            // Second tricount
            tricountDao.insert(tricount2fromUser1)
            insertedTricounts.add(tricount2fromUser1)

            val secondInsertedEmission = awaitItem()
            assertThat(secondInsertedEmission).hasSize(insertedTricounts.size)
            assertThat(secondInsertedEmission).containsExactlyElementsIn(insertedTricounts)

            cancelAndIgnoreRemainingEvents()
        }
    }

    /**
     * 4. Get all open flow notifies deletions -> List contains updated list
     */
    @Test
    fun getAllTricounts_whenDatabaseStartsWithElements_flowNotifiesDeletions() = runTest {
        // Given: Existing user and tricounts, and get tricounts flow is opened.
        userDao.insert(existingUser1)
        val initialTricounts = listOf(tricount1fromUser1, tricount2fromUser1)
        initialTricounts.forEach { tricountDao.insert(it) }

        val allTricountsFlow = tricountDao.getAll()

        allTricountsFlow.test {
            val initialEmission = awaitItem()
            assertThat(initialEmission).containsExactlyElementsIn(initialTricounts)

            // When: Deleting a tricount.
            tricountDao.delete(tricount2fromUser1)

            // Then: Flow notifies of deletion.
            val emitted = awaitItem()
            assertThat(emitted).hasSize(1)
            assertThat(emitted).doesNotContain(tricount2fromUser1)
            assertThat(emitted).containsExactly(tricount1fromUser1)

            cancelAndIgnoreRemainingEvents()
        }
    }

    /**
     * 5. Get all open flow notifies updates -> List contains updated element
     */
    @Test
    fun getAllTricounts_flowNotifiesUpdate() = runTest {
        // Given: Existing user and tricount, and opened tricount flow.
        userDao.insert(existingUser1)
        val tricountOriginal = tricount1fromUser1
        val updatedTricount = tricountOriginal.copy(
            title = tricountOriginal.title.plus("Updated Title")
        )

        tricountDao.insert(tricountOriginal)

        val flow = tricountDao.getAll()

        flow.test {
            val initialEmission = awaitItem()
            assertThat(initialEmission).containsExactly(tricountOriginal)

            // When: Update tricount (reinsert with same ID)
            tricountDao.insert(updatedTricount)

            // Then: Flow notifies of modification.
            val emitted = awaitItem()
            assertThat(emitted).containsExactly(updatedTricount)

            cancelAndIgnoreRemainingEvents()
        }
    }

    ///////////////////////////////////////////////////////////////
    /// Get Tricount With Participants And Expenses test cases:
    ///  1. Get on empty Dataset -> Gets null
    ///  2. Get on non empty Dataset with Tricount without Participants or Expenses -> Gets Tricount with empty lists
    ///  3. Get on non empty Dataset with Tricount with Participants and Expenses -> Gets Tricount with correct lists
    ///  4. Get on non empty Dataset with unrelated Participants/Expenses -> Gets only correct ones
    ///  5. Get on non empty Dataset without Tricount -> Gets null
    ///////////////////////////////////////////////////////////////

    /**
     * 1. Get on empty Dataset -> Gets null
     */
    @Test
    fun getTricountWithParticipantsAndExpenses_whenDatabaseIsEmpty() = runTest {
        // Given: Empty dataset.

        // When: Get tricount with relations
        val result =
            tricountDao.getTricountWithParticipantsAndExpenses(tricount1fromUser1.id).first()

        // Then: It's null
        assertThat(result).isNull()
    }


    /**
     * 2. Get on non empty Dataset with Tricount without Participants or Expenses -> Gets Tricount with empty lists
     */
    @Test
    fun getTricountWithParticipantsAndExpenses_whenTricountExistsWithNoRelations() = runTest {
        // Given: User and tricount inserted
        userDao.insert(existingUser1)
        tricountDao.insert(tricount1fromUser1)

        // When: Get tricount with relations
        val result =
            tricountDao.getTricountWithParticipantsAndExpenses(tricount1fromUser1.id).first()

        // Then: Tricount matches and lists are empty
        assertThat(result).isNotNull()
        assertThat(result.tricount).isEqualTo(tricount1fromUser1)
        assertThat(result.participants).isEmpty()
        assertThat(result.expenses).isEmpty()
    }

    /**
     * 3. Get on non empty Dataset with Tricount with Participants and Expenses -> Gets Tricount with correct lists
     */
    @Test
    fun getTricountWithParticipantsAndExpenses_whenTricountHasParticipantsAndExpenses() = runTest {
        // Given: User, tricount, participants and expenses
        userDao.insert(existingUser1)
        tricountDao.insert(tricount1fromUser1)

        val participants = listOf(participant1fromTricount1, participant2fromTricount1)
        val expenses = listOf(expense1fromTricount1, expense2fromTricount1)

        participants.forEach { participantDao.insert(it) }
        expenses.forEach { expenseDao.insert(it) }

        // When: Get tricount with relations
        val result =
            tricountDao.getTricountWithParticipantsAndExpenses(tricount1fromUser1.id).first()

        // Then: Tricount matches and lists contain the correct elements
        assertThat(result).isNotNull()
        assertThat(result.tricount).isEqualTo(tricount1fromUser1)
        assertThat(result.participants).containsExactlyElementsIn(participants)
        assertThat(result.expenses).containsExactlyElementsIn(expenses)
    }

    /**
     * 4. Get on non empty Dataset with unrelated Participants/Expenses -> Gets only correct ones
     */
    @Test
    fun getTricountWithParticipantsAndExpenses_whenOtherTricountsHaveParticipantsOrExpenses_doesNotIncludeThem() =
        runTest {
            // Given: Two tricounts, each with their participants and expenses
            userDao.insert(existingUser1)
            tricountDao.insert(tricount1fromUser1)
            tricountDao.insert(tricount2fromUser1)

            val participantsForTricount1 = listOf(participant1fromTricount1)
            val participantsForTricount2 = listOf(participant1fromTricount2)

            val expensesForTricount1 = listOf(expense1fromTricount1)
            val expensesForTricount2 = listOf(expense1fromTricount2)

            participantsForTricount1.forEach { participantDao.insert(it) }
            participantsForTricount2.forEach { participantDao.insert(it) }

            expensesForTricount1.forEach { expenseDao.insert(it) }
            expensesForTricount2.forEach { expenseDao.insert(it) }

            // When: Get tricount1 with relations
            val result =
                tricountDao.getTricountWithParticipantsAndExpenses(tricount1fromUser1.id).first()

            // Then: Only relations for tricount1 are present
            assertThat(result).isNotNull()
            assertThat(result.tricount).isEqualTo(tricount1fromUser1)
            assertThat(result.participants).containsExactlyElementsIn(participantsForTricount1)
            assertThat(result.expenses).containsExactlyElementsIn(expensesForTricount1)
        }


    /**
     * 5. Get on non empty Dataset without Tricount -> Gets null
     */
    @Test
    fun getTricountWithParticipantsAndExpenses_whenTricountDoesNotExist() = runTest {
        // Given: User and unrelated tricount
        userDao.insert(existingUser1)
        tricountDao.insert(tricount2fromUser1)

        // When: Try to get non-existent tricount
        val result =
            tricountDao.getTricountWithParticipantsAndExpenses(tricount1fromUser1.id).first()

        // Then: It's null
        assertThat(result).isNull()
    }

    ////////////////////////////////////////////////////////////////
    /// Update Tricount test cases:
    ///  1. Update on empty Table -> Result is 0 and table continues empty
    ///  2. Update on non empty Table with updating Tricount -> Result is 1 and table contains updated Tricount
    ///  3. Update on non empty Table without updating Tricount -> Result is 0 and table remains unmodified
    ////////////////////////////////////////////////////////////////

    /**
     * 1. Update on empty Table -> Result is 0 and table continues empty
     */
    @Test
    fun updateTricount_whenDatabaseIsEmpty() = runTest {
        // Given: Empty database.
        val tricountToUpdate = tricount1fromUser1

        // When: Try to update tricount.
        val updateResult = tricountDao.update(tricountToUpdate)

        // Then: No rows updated and table is still empty.
        val allTricounts = tricountDao.getAll().first()

        assertThat(updateResult).isEqualTo(0)
        assertThat(allTricounts).isEmpty()
    }

    /**
     * 2. Update on non empty Table with updating Tricount -> Result is 1 and table contains updated Tricount
     */
    @Test
    fun updateTricount_whenDatabaseHasElements_updatingTricount() = runTest {
        // Given: Database with a tricount.
        userDao.insert(existingUser1)
        tricountDao.insert(tricount1fromUser1)

        // When: Update that tricount with new title.
        val updatedTricount = tricount1fromUser1.copy(
            title = tricount1fromUser1.title.plus("Updated Title")
        )
        val updateResult = tricountDao.update(updatedTricount)

        // Then: Exactly one row updated and tricount is updated in table.
        val allTricounts = tricountDao.getAll().first()

        assertThat(updateResult).isEqualTo(1)
        assertThat(allTricounts).hasSize(1)
        assertThat(allTricounts).containsExactly(updatedTricount)
    }

    /**
     * 3. Update on non empty Table without updating Tricount -> Result is 0 and table remains unmodified
     */
    @Test
    fun updateTricount_whenDatabaseHasElements_tricountNotExists() = runTest {
        // Given: Database with some tricounts.
        userDao.insert(existingUser1)
        val initialTricounts = listOf(tricount1fromUser1)
        initialTricounts.forEach { tricountDao.insert(it) }

        // When: Try to update a tricount not present in database.
        val nonExistingTricount = tricount2fromUser1
        val updateResult = tricountDao.update(nonExistingTricount)

        // Then: No rows updated and table remains unchanged.
        val allTricounts = tricountDao.getAll().first()

        assertThat(updateResult).isEqualTo(0)
        assertThat(allTricounts).hasSize(initialTricounts.size)
        assertThat(allTricounts).containsExactlyElementsIn(initialTricounts)
    }

    ////////////////////////////////////////////////////////////////
    /// Update Tricount with open flow test cases:
    ///  1. Update on empty Table -> No emission, flow keeps empty
    ///  2. Update on non empty Table with updating Tricount -> Flow emite lista con tricount actualizado
    ///  3. Update on non empty Table without updating Tricount -> Flow no emite nada porque no hubo cambios
    ////////////////////////////////////////////////////////////////

    /**
     * 1. Update on empty Table -> No emission, flow keeps empty
     */
    @Test
    fun updateTricount_whenDatabaseIsEmpty_flowRemainsEmpty() = runTest {
        // Given: Empty database.
        val tricountToUpdate = tricount1fromUser1

        val allTricountsFlow = tricountDao.getAll()

        allTricountsFlow.test {
            val initialEmission = awaitItem()
            assertThat(initialEmission).isEmpty()

            // When: Attempt to update a non-existing tricount.
            val updateResult = tricountDao.update(tricountToUpdate)

            // Then: No emissions should be triggered.
            expectNoEvents()

            // And update result must be 0
            assertThat(updateResult).isEqualTo(0)

            cancelAndIgnoreRemainingEvents()
        }
    }

    /**
     * 2. Update on non empty Table with updating Tricount -> Flow emite lista con tricount actualizado
     */
    @Test
    fun updateTricount_whenDatabaseHasElements_updatingTricount_flowEmitsUpdate() = runTest {
        // Given: Table has a Tricount.
        userDao.insert(existingUser1)
        tricountDao.insert(tricount1fromUser1)

        val allTricountsFlow = tricountDao.getAll()

        allTricountsFlow.test {
            val initialEmission = awaitItem()
            assertThat(initialEmission).containsExactly(tricount1fromUser1)

            // When: A Tricount gets updated.
            val updatedTricount = tricount1fromUser1.copy(
                title = tricount1fromUser1.title.plus("Updated Title")
            )
            val updateResult = tricountDao.update(updatedTricount)

            // Then: Flow must emmit list with updated elements.
            val updatedEmission = awaitItem()
            assertThat(updatedEmission).containsExactly(updatedTricount)
            assertThat(updateResult).isEqualTo(1)

            cancelAndIgnoreRemainingEvents()
        }
    }

    /**
     * 3. Update on non empty Table without updating Tricount -> Flow no emite nada porque no hubo cambios
     */
    @Test
    fun updateTricount_whenDatabaseHasElements_tricountNotExists_flowDoesNotEmit() = runTest {
        // Given: Table has a Tricount.
        val initialTricounts = listOf(tricount1fromUser1)
        userDao.insert(existingUser1)
        initialTricounts.forEach { tricountDao.insert(it) }

        val allTricountsFlow = tricountDao.getAll()

        allTricountsFlow.test {
            val initialEmission = awaitItem()
            assertThat(initialEmission).containsExactlyElementsIn(initialTricounts)

            // When: Attempt to update a non-existing Tricount.
            val nonExistingTricount = tricount2fromUser1
            val updateResult = tricountDao.update(nonExistingTricount)

            // Then: Flow must not emit anything.
            expectNoEvents()
            assertThat(updateResult).isEqualTo(0)

            cancelAndIgnoreRemainingEvents()
        }
    }


    ////////////////////////////////////////////////////////////////
    /// Delete Tricount test cases:
    ///  1. Deleting on empty Table -> Result is 0 and table continues empty
    ///  2. Deleting on non empty Table with deleting Tricount -> Result is 1 and table removed deleting Tricount
    ///  3. Deleting on non empty Table with deleting Tricount with Expenses and Participants -> Result is 1 and table removed deleting Tricount and its relations
    ///  4. Deleting on non empty Table without deleting Tricount -> Result is 0 and table remains unmodified
    ////////////////////////////////////////////////////////////////

    /**
     * 1. Deleting on empty Table -> Result is 0 and table continues empty
     */
    @Test
    fun deleteTricount_whenDatabaseIsEmpty() = runTest {
        // Given: Empty database.

        // When: Attempt to delete tricount.
        val deleteResult = tricountDao.delete(tricount1fromUser1)

        // Then: No tricounts in database and deleteResult is 0
        val allTricounts = tricountDao.getAll().first()

        assertThat(deleteResult).isEqualTo(0)
        assertThat(allTricounts).isEmpty()
    }

    /**
     * 2. Deleting on non empty Table with deleting Tricount -> Result is 1 and table removed deleting Tricount
     */
    @Test
    fun deleteTricount_whenDatabaseHasElements_tricountExists() = runTest {
        // Given: Database with tricounts.
        val initialTricounts = listOf(tricount1fromUser1, tricount2fromUser1)

        userDao.insert(existingUser1)
        initialTricounts.forEach { tricountDao.insert(it) }

        // When: Delete an existing tricount.
        val deleteResult = tricountDao.delete(tricount1fromUser1)

        // Then: Tricount removed and only remaining tricounts present.
        val allTricounts = tricountDao.getAll().first()

        assertThat(deleteResult).isEqualTo(1)
        assertThat(allTricounts).hasSize(1)
        assertThat(allTricounts).containsExactly(tricount2fromUser1)
    }

    /**
     * 3. Deleting on non empty Table with deleting Tricount with Expenses and Participants -> Result is 1 and table removed deleting Tricount and its relations
     */
    @Test
    fun deleteTricount_whenDatabaseHasElements_tricountExistsWithRelations() = runTest {
        // Given: Database with tricount and its participants and expenses
        userDao.insert(existingUser1)
        tricountDao.insert(tricount1fromUser1)

        participantDao.insert(participant1fromTricount1)
        participantDao.insert(participant2fromTricount1)
        expenseDao.insert(expense1fromTricount1)
        expenseDao.insert(expense2fromTricount1)

        // When: Delete the tricount
        val deleteResult = tricountDao.delete(tricount1fromUser1)

        // Then: Tricount, participants, and expenses are removed
        val allTricounts = tricountDao.getAll().first()
        val allParticipants = participantDao.getAll().first()
        val allExpenses = expenseDao.getAll().first()

        assertThat(deleteResult).isEqualTo(1)
        assertThat(allTricounts).isEmpty()
        assertThat(allParticipants).isEmpty()
        assertThat(allExpenses).isEmpty()
    }

    /**
     * 4. Deleting on non empty Table without deleting Tricount -> Result is 0 and table remains unmodified
     */
    @Test
    fun deleteTricount_whenDatabaseHasElements_tricountDoesNotExist() = runTest {
        // Given: Database with one tricount
        userDao.insert(existingUser1)
        tricountDao.insert(tricount1fromUser1)

        // When: Attempt to delete a tricount not present
        val deleteResult = tricountDao.delete(tricount2fromUser1)

        // Then: Database unchanged
        val allTricounts = tricountDao.getAll().first()

        assertThat(deleteResult).isEqualTo(0)
        assertThat(allTricounts).hasSize(1)
        assertThat(allTricounts).containsExactly(tricount1fromUser1)
    }
}
