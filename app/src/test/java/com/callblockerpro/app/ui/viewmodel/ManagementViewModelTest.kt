package com.callblockerpro.app.ui.viewmodel

import app.cash.turbine.test
import com.callblockerpro.app.domain.exception.ListConflictException
import com.callblockerpro.app.domain.model.BlocklistEntry
import com.callblockerpro.app.domain.model.WhitelistEntry
import com.callblockerpro.app.domain.repository.ListRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for ManagementViewModel
 * Tests error handling, list operations, and state management
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ManagementViewModelTest {
    
    private lateinit var viewModel: ManagementViewModel
    private lateinit var listRepository: ListRepository
    private val testDispatcher = StandardTestDispatcher()
    
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        // Create mock repository
        listRepository = mockk(relaxed = true)
        
        // Setup default flows
        every { listRepository.getBlocklist() } returns flowOf(emptyList())
        every { listRepository.getWhitelist() } returns flowOf(emptyList())
        
        viewModel = ManagementViewModel(listRepository)
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    
    // ===== Error Handling Tests =====
    
    @Test
    fun `addEntry with duplicate number shows error message`() = runTest {
        // Given: Repository throws conflict exception
        coEvery { 
            listRepository.addToBlocklist(any()) 
        } throws ListConflictException("This number is already in your Blocklist.")
        
        // When: Trying to add duplicate number
        viewModel.addEntry("+1234567890", "Test")
        advanceUntilIdle()
        
        // Then: Error message is set
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.errorMessage).contains("already in your Blocklist")
        }
    }
    
    @Test
    fun `addEntry with valid number does not show error`() = runTest {
        // Given: Repository accepts the entry
        coEvery { listRepository.addToBlocklist(any()) } returns 1L
        
        // When: Adding valid number
        viewModel.addEntry("+1234567890", "Test")
        advanceUntilIdle()
        
        // Then: No error message
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.errorMessage).isNull()
        }
    }
    
    @Test
    fun `clearError removes error message`() = runTest {
        // Given: Error message exists
        coEvery { 
            listRepository.addToBlocklist(any()) 
        } throws ListConflictException("Error")
        viewModel.addEntry("+1234567890", "Test")
        advanceUntilIdle()
        
        // When: Clearing error
        viewModel.clearError()
        advanceUntilIdle()
        
        // Then: Error is cleared
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.errorMessage).isNull()
        }
    }
    
    @Test
    fun `undoDelete with conflict shows error message`() = runTest {
        // Given: Delete an item first
        val testEntry = BlocklistEntry(id = 1, phoneNumber = "+1234567890", name = "Test")
        every { listRepository.getBlocklist() } returns flowOf(listOf(testEntry))
        viewModel.deleteItem(1)
        advanceUntilIdle()
        
        // And: Undo will conflict
        coEvery { 
            listRepository.addToBlocklist(any()) 
        } throws ListConflictException("Number already exists")
        
        // When: Undoing delete
        viewModel.undoDelete()
        advanceUntilIdle()
        
        // Then: Error message shown
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.errorMessage).contains("already exists")
        }
    }
    
    // ===== Tab Management Tests =====
    
    @Test
    fun `setTab changes selected tab`() = runTest {
        // When: Switching to whitelist tab
        viewModel.setTab(ManagementTab.WHITELIST)
        advanceUntilIdle()
        
        // Then: Tab is updated
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.selectedTab).isEqualTo(ManagementTab.WHITELIST)
        }
    }
    
    @Test
    fun `setSearchQuery updates query in state`() = runTest {
        // When: Setting search query
        viewModel.setSearchQuery("test query")
        advanceUntilIdle()
        
        // Then: Query is updated
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.searchQuery).isEqualTo("test query")
        }
    }
    
    // ===== Selection Mode Tests =====
    
    @Test
    fun `setSelectionMode enables selection with empty set`() = runTest {
        // When: Enabling selection mode
        viewModel.setSelectionMode(true)
        advanceUntilIdle()
        
        // Then: Selection mode is active with no selections
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.isSelectionMode).isTrue()
            assertThat(state.selectedIds).isEmpty()
        }
    }
    
    @Test
    fun `setSelectionMode disabled clears selections`() = runTest {
        // Given: Selection mode with items selected
        viewModel.setSelectionMode(true)
        viewModel.toggleSelection(1L)
        viewModel.toggleSelection(2L)
        advanceUntilIdle()
        
        // When: Disabling selection mode
        viewModel.setSelectionMode(false)
        advanceUntilIdle()
        
        // Then: Selection cleared and mode disabled
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.isSelectionMode).isFalse()
            assertThat(state.selectedIds).isEmpty()
        }
    }
    
    @Test
    fun `toggleSelection adds and removes items`() = runTest {
        // Given: Selection mode enabled
        viewModel.setSelectionMode(true)
        
        // When: Toggling item 1 (add)
        viewModel.toggleSelection(1L)
        advanceUntilIdle()
        
        // Then: Item is selected
        var state = viewModel.uiState.value
        assertThat(state.selectedIds).contains(1L)
        
        // When: Toggling item 1 again (remove)
        viewModel.toggleSelection(1L)
        advanceUntilIdle()
        
        // Then: Item is deselected
        state = viewModel.uiState.value
        assertThat(state.selectedIds).doesNotContain(1L)
    }
    
    @Test
    fun `clearSelection removes all selections and exits mode`() = runTest {
        // Given: Multiple items selected
        viewModel.setSelectionMode(true)
        viewModel.toggleSelection(1L)
        viewModel.toggleSelection(2L)
        viewModel.toggleSelection(3L)
        advanceUntilIdle()
        
        // When: Clearing selection
        viewModel.clearSelection()
        advanceUntilIdle()
        
        // Then: All cleared and mode disabled
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.selectedIds).isEmpty()
            assertThat(state.isSelectionMode).isFalse()
        }
    }
    
    // ===== Deletion Tests =====
    
    @Test
    fun `deleteSelected calls repository for each selected item`() = runTest {
        // Given: Multiple items selected in blocklist
        viewModel.setTab(ManagementTab.BLOCKLIST)
        viewModel.setSelectionMode(true)
        viewModel.toggleSelection(1L)
        viewModel.toggleSelection(2L)
        advanceUntilIdle()
        
        // When: Deleting selected
        viewModel.deleteSelected()
        advanceUntilIdle()
        
        // Then: Repository called for each item
        coVerify { listRepository.removeMultipleFromBlocklist(listOf(1L, 2L)) }
    }
    
    @Test
    fun `deleteSelected on whitelist tab calls whitelist repository`() = runTest {
        // Given: Items selected in whitelist
        viewModel.setTab(ManagementTab.WHITELIST)
        viewModel.setSelectionMode(true)
        viewModel.toggleSelection(5L)
        advanceUntilIdle()
        
        // When: Deleting selected
        viewModel.deleteSelected()
        advanceUntilIdle()
        
        // Then: Whitelist repository called
        coVerify { listRepository.removeMultipleFromWhitelist(listOf(5L)) }
    }
}
