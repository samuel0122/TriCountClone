package com.oliva.samuel.tricountclone.data.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.oliva.samuel.tricountclone.data.database.TricountDatabase
import com.oliva.samuel.tricountclone.data.database.entities.TricountEntity
import com.oliva.samuel.tricountclone.data.database.entities.UserEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date
import java.util.UUID

@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDaoTest {
    private lateinit var database: TricountDatabase
    private lateinit var userDao: UserDao
    private lateinit var tricountDao: TricountDao

    private val newUser1 = UserEntity(name = "User1")
    private val newUser2 = UserEntity(name = "User2")
    private val newUser3 = UserEntity(name = "User3")

    private val existingUser1 = UserEntity(newUser1.id, "Name1")
    private val existingUser2 = UserEntity(newUser2.id, "Name2")
    private val existingUser3 = UserEntity(newUser3.id, "Name3")

    private val tricount1 = TricountEntity(
        title = "Tricount 1",
        icon = "icon1",
        currency = "EUR",
        createdBy = existingUser1.id,
        createdAt = Date()
    )
    private val tricount2 = TricountEntity(
        id = UUID.randomUUID(),
        title = "Tricount 2",
        icon = "icon2",
        currency = "USD",
        createdBy = existingUser1.id,
        createdAt = Date()
    )

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TricountDatabase::class.java
        ).allowMainThreadQueries().build()

        userDao = database.userDao()
        tricountDao = database.tricountDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    ////////////////////////////////////////////////////////////////
    /// Insert User test cases:
    ///  1. Insert on empty Table -> Insert OK
    ///  2. Insert on non empty Table no collision -> Insert OK
    ///  3. Insert on non empty Table with collision -> Override
    ////////////////////////////////////////////////////////////////

    /**
     * 1. Insert on empty Table -> Insert OK
     */
    @Test
    fun insertUser_whenDatabaseIsEmpty() = runTest {
        // Given: Empty database.
        val insertUser = newUser1

        // When: Insert a user.
        userDao.insert(insertUser)

        // Then: Database only contains inserted user.
        val allUsers = userDao.getAll().first()

        assertThat(allUsers).hasSize(1)
        assertThat(allUsers).containsExactly(insertUser)
    }

    /**
     * 2. Insert on non empty Table no collision -> Insert OK
     */
    @Test
    fun insertUser_whenDatabaseHasElements_noCollisions() = runTest {
        // Given: Table with initial users.
        val initialUsers = listOf(existingUser1, existingUser2)
        val insertUser = newUser3
        val finalUsers = initialUsers.plusElement(insertUser)

        initialUsers.forEach { userDao.insert(it) }

        // When: Insert the new user.
        userDao.insert(insertUser)

        // Then: Database contains previous users and new one.
        val allUsers = userDao.getAll().first()

        assertThat(allUsers).hasSize(finalUsers.size)
        assertThat(allUsers).containsExactlyElementsIn(finalUsers)
    }

    /**
     * 3. Insert on non empty Table with collision -> Override
     */
    @Test
    fun insertUser_whenDatabaseHasElements_withCollision() = runTest {
        // Given: Database with one element.
        val initialUser = existingUser1
        val insertUser = newUser1

        userDao.insert(initialUser)

        // When: Insert a user which ID already exists.
        userDao.insert(insertUser)

        // Then: The previous user was replaced by the new one.
        val allUsers = userDao.getAll().first()

        assertThat(allUsers).hasSize(1)
        assertThat(allUsers).containsExactly(insertUser)
    }

    ////////////////////////////////////////////////////////////////
    /// Get All Users test cases:
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
    fun getAllUsers_whenDatabaseIsEmpty() = runTest {
        // Given: Empty database.

        // When: Get all users.
        val allUsers = userDao.getAll().first()

        // Then: Returned list is empty.
        assertThat(allUsers).isEmpty()
    }

    /**
     * 2. Get all on non empty Table -> List contains only inserted elements.
     */
    @Test
    fun getAllUsers_whenDatabaseHasElements() = runTest {
        // Given: Database with initial users.
        val initialUsers = listOf(existingUser1, existingUser2, existingUser3)
        initialUsers.forEach { userDao.insert(it) }

        // When: Get all users.
        val allUsers = userDao.getAll().first()

        // Then: Returned list contains exactly the inserted users.
        assertThat(allUsers).hasSize(initialUsers.size)
        assertThat(allUsers).containsExactlyElementsIn(initialUsers)
    }

    /**
     * 3. Get all open flow notifies new insertion and deletions -> List contains inserted element
     */
    @Test
    fun getAllUsers_whenDatabaseStartsEmpty_flowNotifiesInsertions() = runTest {
        // Given: Empty database.

        // When: Get all users.
        val allUsersFlow = userDao.getAll()

        allUsersFlow.test {
            // Then: Initial list is empty, then it fills up.
            val firstEmptyEmission = awaitItem()
            assertThat(firstEmptyEmission).isEmpty()

            // Insert user
            val insertedUsers = mutableListOf<UserEntity>()

            // First user
            userDao.insert(existingUser1)
            insertedUsers.add(existingUser1)

            val firstInsertedEmission = awaitItem()
            assertThat(firstInsertedEmission).hasSize(insertedUsers.size)
            assertThat(firstInsertedEmission).containsExactlyElementsIn(insertedUsers)

            // Second user
            userDao.insert(existingUser2)
            insertedUsers.add(existingUser2)

            val secondInsertedEmission = awaitItem()
            assertThat(secondInsertedEmission).hasSize(insertedUsers.size)
            assertThat(secondInsertedEmission).containsExactlyElementsIn(insertedUsers)

            // Third user
            userDao.insert(existingUser3)
            insertedUsers.add(existingUser3)

            val thirdInsertedEmission = awaitItem()
            assertThat(thirdInsertedEmission).hasSize(insertedUsers.size)
            assertThat(thirdInsertedEmission).containsExactlyElementsIn(insertedUsers)

            // Delete one user
            val userToDelete = existingUser2
            val deleteResult = userDao.delete(userToDelete)
            insertedUsers.remove(userToDelete)
            assertThat(deleteResult).isEqualTo(1)

            // Flow should emit list without the deleted user
            val firstDeletionEmission = awaitItem()
            assertThat(firstDeletionEmission).hasSize(insertedUsers.size)
            assertThat(firstDeletionEmission).doesNotContain(userToDelete)
            assertThat(firstDeletionEmission).containsExactlyElementsIn(insertedUsers)

            cancelAndIgnoreRemainingEvents()
        }
    }

    /**
     * 4. Get all open flow notifies new deletions -> List contains inserted element
     */
    @Test
    fun getAllUsers_whenDatabaseStartsWithElements_flowNotifiesDeletions() = runTest {
        // Given: Full database.
        val initialUsers = listOf(existingUser1, existingUser2, existingUser3)
        initialUsers.forEach { userDao.insert(it) }

        // When: Get all users.
        val allUsersFlow = userDao.getAll()

        allUsersFlow.test {
            val initialEmission = awaitItem()
            assertThat(initialEmission).containsExactlyElementsIn(initialUsers)

            // Then: Flow notifies of deletion.
            userDao.delete(existingUser2)

            val emitted = awaitItem()
            assertThat(emitted).hasSize(2)
            assertThat(emitted).doesNotContain(existingUser2)
            assertThat(emitted).containsExactly(existingUser1, existingUser3)

            cancelAndIgnoreRemainingEvents()
        }
    }

    /**
     * 5. Get all open flow notifies updates -> List contains updated element
     */
    @Test
    fun getAllUsers_flowNotifiesUpdate() = runTest {
        // Given: Database with one element.
        val userOriginal = existingUser1
        val updatedUser = userOriginal.copy(name = "Updated Name")

        userDao.insert(userOriginal)

        val flow = userDao.getAll()

        flow.test {
            val initialEmission = awaitItem()
            assertThat(initialEmission).containsExactly(userOriginal)

            // When: Updated an element.
            userDao.update(updatedUser)

            // Then: Flow notifies of modification.
            val emitted = awaitItem()
            assertThat(emitted).containsExactly(updatedUser)

            cancelAndIgnoreRemainingEvents()
        }
    }

    ////////////////////////////////////////////////////////////////
    /// Get User With Tricounts test cases:
    ///  1. Get on empty Dataset -> Gets null
    ///  2. Get on non empty Dataset with getting User without Tricounts -> Gets User with getting ID and empty list of Tricounts
    ///  3. Get on non empty Dataset with getting User with Tricounts -> Gets User with getting ID and list of Tricounts
    ///  4. Get on non empty Dataset without getting User -> Gets null
    ////////////////////////////////////////////////////////////////

    /**
     * 1. Get on empty Dataset -> Gets null
     */
    @Test
    fun getUserWithTricounts_whenDatabaseIsEmpty() = runTest {
        // Given: Empty dataset.

        // When: Get user with tricounts in empty dataset.
        val getUser = userDao.getUserWithCreatedTricounts(existingUser1.id).first()

        // Then: It's null.
        assertThat(getUser).isNull()
    }

    /**
     * 2. Get on non empty Dataset with getting User without Tricounts -> Gets User with getting ID and empty list of Tricounts
     */
    @Test
    fun getUserWithTricounts_whenDatabaseHasElements_userExistsWithNoTricounts() = runTest {
        // Given: Database with users but no tricounts.
        val initialUsers = listOf(existingUser1, existingUser2, existingUser3)
        val userToGet = existingUser1

        initialUsers.forEach { userDao.insert(it) }

        // When: Get user that exists.
        val getUser = userDao.getUserWithCreatedTricounts(userToGet.id).first()

        // Then: Get user is same than inserted and has no tricounts.
        assertThat(getUser).isNotNull()
        assertThat(getUser.user).isEqualTo(userToGet)
        assertThat(getUser.tricounts).isEmpty()
    }

    /**
     * 3. Get on non empty Dataset with getting User with Tricounts -> Gets User with getting ID and list of Tricounts
     */
    @Test
    fun getUserWithTricounts_whenDatabaseHasElements_userExistsWithTricounts() = runTest {
        // Given: Database with user and their tricounts.
        val userToGet = existingUser1
        val tricounts = listOf(
            tricount1.copy(createdBy = userToGet.id),
            tricount2.copy(createdBy = userToGet.id)
        )

        userDao.insert(userToGet)
        tricounts.forEach { tricountDao.insert(it) }

        // When: Get user with tricounts.
        val getUser = userDao.getUserWithCreatedTricounts(userToGet.id).first()

        // Then: User and their tricounts match the inserted ones.
        assertThat(getUser).isNotNull()
        assertThat(getUser.user).isEqualTo(userToGet)
        assertThat(getUser.tricounts).containsExactlyElementsIn(tricounts)
    }

    /**
     * 4. Get on non empty Dataset with getting User with Tricounts -> Gets User with getting ID and list of Tricounts, excluding other Tricounts
     */
    @Test
    fun getUserWithTricounts_whenDatabaseHasTricountsFromOtherUsers_doesNotIncludeThem() = runTest {
        // Given: Usuario y tricounts creados por otros usuarios.
        val userToGet = existingUser1
        val otherUser = existingUser2

        val tricountsFromUser = listOf(
            tricount1.copy(createdBy = userToGet.id)
        )

        val tricountsFromOtherUser = listOf(
            tricount2.copy(createdBy = otherUser.id)
        )

        userDao.insert(userToGet)
        userDao.insert(otherUser)
        tricountsFromUser.forEach { tricountDao.insert(it) }
        tricountsFromOtherUser.forEach { tricountDao.insert(it) }

        // When: Get user with tricounts.
        val getUser = userDao.getUserWithCreatedTricounts(userToGet.id).first()

        // Then: Solo debe tener los tricounts creados por él.
        assertThat(getUser).isNotNull()
        assertThat(getUser.user).isEqualTo(userToGet)
        assertThat(getUser.tricounts).containsExactlyElementsIn(tricountsFromUser)
    }

    /**
     * 5. Get on non empty Dataset without getting User -> Gets null
     */
    @Test
    fun getUserWithTricounts_whenDatabaseHasElements_userDoesNotExist() = runTest {
        // Given: Database with users.
        val initialUsers = listOf(existingUser1, existingUser2)
        val userToGet = newUser3

        initialUsers.forEach { userDao.insert(it) }

        // When: Get user that does not exist.
        val getUser = userDao.getUserWithCreatedTricounts(userToGet.id).first()

        // Then: It's null.
        assertThat(getUser).isNull()
    }

    ////////////////////////////////////////////////////////////////
    /// Update User test cases:
    ///  1. Update on empty Table -> Result is 0 and table continues empty
    ///  2. Update on non empty Table with updating User -> Result is 1 and table contains updated user
    ///  3. Update on non empty Table without updating User -> Result is 0 and table remains unmodified
    ////////////////////////////////////////////////////////////////

    /**
     * 1. Update on empty Table -> Result is 0 and table continues empty
     */
    @Test
    fun updateUser_whenDatabaseIsEmpty() = runTest {
        // Given: Empty database.
        val updateUser = existingUser1

        // When: Try to update user.
        val updateResult = userDao.update(updateUser)

        // Then: No rows updated and table is still empty.
        val allUsers = userDao.getAll().first()

        assertThat(updateResult).isEqualTo(0)
        assertThat(allUsers).isEmpty()
    }

    /**
     * 2. Update on non empty Table with updating User -> Result is 1 and table contains updated user
     */
    @Test
    fun updateUser_whenDatabaseHasElements_updatingUser() = runTest {
        // Given: Database with user.
        userDao.insert(existingUser1)

        // When: Update that user with new name.
        val updatedUser = existingUser1.copy(name = "UpdatedName")
        val updateResult = userDao.update(updatedUser)

        // Then: Exactly one row updated and user is updated in table.
        val allUsers = userDao.getAll().first()

        assertThat(updateResult).isEqualTo(1)
        assertThat(allUsers).hasSize(1)
        assertThat(allUsers).containsExactly(updatedUser)
    }

    /**
     * 3. Update on non empty Table without updating User -> Result is 0 and table remains unmodified
     */
    @Test
    fun updateUser_whenDatabaseHasElements_userNotExists() = runTest {
        // Given: Database with some users.
        val initialUsers = listOf(existingUser1, existingUser2)
        initialUsers.forEach { userDao.insert(it) }

        // When: Try to update a user not present in database.
        val nonExistingUser = newUser3
        val updateResult = userDao.update(nonExistingUser)

        // Then: No rows updated and table remains unchanged.
        val allUsers = userDao.getAll().first()

        assertThat(updateResult).isEqualTo(0)
        assertThat(allUsers).hasSize(initialUsers.size)
        assertThat(allUsers).containsExactlyElementsIn(initialUsers)
    }

    ////////////////////////////////////////////////////////////////
    /// Delete User test cases:
    ///  1. Deleting on empty Table -> Result is 0 and table continues empty
    ///  2. Deleting on non empty Table with deleting User -> Result is 1 and table removed deleting User
    ///  3. Deleting on non empty Table with deleting User with Tricounts -> Result is 1 and table removed deleting User and its Tricounts
    ///  4. Deleting on non empty Table without deleting User -> Result is 0 and table remains unmodified
    ////////////////////////////////////////////////////////////////

    /**
     * 1. Deleting on empty Table -> Result is 0 and table continues empty
     */
    @Test
    fun deleteUser_whenDatabaseIsEmpty() = runTest {
        // Given: Empty database.

        // When: Attempt to delete user.
        val deleteResult = userDao.delete(existingUser1)

        // Then: No users in database and deleteResult is 0
        val allUsers = userDao.getAll().first()

        assertThat(deleteResult).isEqualTo(0)
        assertThat(allUsers).isEmpty()
    }

    /**
     * 2. Deleting on non empty Table with deleting User -> Result is 1 and table removed deleting User
     */
    @Test
    fun deleteUser_whenDatabaseHasElements_userExists() = runTest {
        // Given: Database with users.
        val initialUsers = listOf(existingUser1, existingUser2)

        initialUsers.forEach { userDao.insert(it) }

        // When: Delete an existing user.
        val deleteResult = userDao.delete(existingUser1)

        // Then: User removed and only remaining users present.
        val allUsers = userDao.getAll().first()

        assertThat(deleteResult).isEqualTo(1)
        assertThat(allUsers).hasSize(1)
        assertThat(allUsers).containsExactly(existingUser2)
    }

    /**
     * 3. Deleting on non empty Table with deleting User with Tricounts -> Result is 1 and table removed deleting User and its Tricounts
     */
    @Test
    fun deleteUser_whenDatabaseHasElements_userExistsWithTricounts() = runTest {
        // Given: Database with user and its tricounts
        userDao.insert(existingUser1)

        tricountDao.insert(tricount1)
        tricountDao.insert(tricount2)

        // When: Delete the user
        val deleteResult = userDao.delete(existingUser1)

        // Then: User and its tricounts are removed
        val allUsers = userDao.getAll().first()
        val allTricounts = tricountDao.getAll().first()

        assertThat(deleteResult).isEqualTo(1)
        assertThat(allUsers).isEmpty()
        assertThat(allTricounts).isEmpty()
    }

    /**
     * 4. Deleting on non empty Table without deleting User -> Result is 0 and table remains unmodified
     */
    @Test
    fun deleteUser_whenDatabaseHasElements_userDoesNotExist() = runTest {
        // Given: Database with different user
        userDao.insert(existingUser1)

        // When: Attempt to delete a user not present
        val deleteResult = userDao.delete(existingUser2)

        // Then: Database unchanged
        val allUsers = userDao.getAll().first()

        assertThat(deleteResult).isEqualTo(0)
        assertThat(allUsers).hasSize(1)
        assertThat(allUsers).containsExactly(existingUser1)
    }
}
