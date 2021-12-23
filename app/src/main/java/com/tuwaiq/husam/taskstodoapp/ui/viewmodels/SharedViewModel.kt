package com.tuwaiq.husam.taskstodoapp.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tuwaiq.husam.taskstodoapp.data.models.MockToDoTask
import com.tuwaiq.husam.taskstodoapp.data.models.Priority
import com.tuwaiq.husam.taskstodoapp.data.models.ToDoTask
import com.tuwaiq.husam.taskstodoapp.data.repositories.DataStoreRepository
import com.tuwaiq.husam.taskstodoapp.data.repositories.MockRepo
import com.tuwaiq.husam.taskstodoapp.data.repositories.ToDoRepository
import com.tuwaiq.husam.taskstodoapp.util.Action
import com.tuwaiq.husam.taskstodoapp.util.Constants.MAX_TITLE_LENGTH
import com.tuwaiq.husam.taskstodoapp.util.RequestState
import com.tuwaiq.husam.taskstodoapp.util.SearchAppBarState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SharedViewModel(context: Application) : AndroidViewModel(context) {
    private val repository: ToDoRepository = ToDoRepository(context)
    private val dataStoreRepository: DataStoreRepository = DataStoreRepository(context = context)


    private val mockRepo: MockRepo = MockRepo()

    /*fun getMockTasks(): MutableLiveData<List<MockToDoTask>> {
        val tasks = MutableLiveData<List<MockToDoTask>>()
        viewModelScope.launch(Dispatchers.IO) {
            val list = mockRepo.fetchTasks()
            Log.e("mockTasks", "Called")
            tasks.postValue(list)
        }
        return tasks
    }*/

    private val _mockTasks = mutableStateListOf<MockToDoTask>()
    var errorMessage: String by mutableStateOf("")
    val mockTasks: List<MockToDoTask>
        get() = _mockTasks

    fun getMockTasks() {
        errorMessage = ""
        viewModelScope.launch {
            try {
                _mockTasks.clear()
                _mockTasks.addAll(mockRepo.fetchTasks())
            } catch (e: Throwable) {
                errorMessage = e.message.toString()
            }
        }
    }

    /* private fun getMockTasks() {
         _mockTasks.value = RequestState.Loading
         try {
             viewModelScope.launch {
                 mockRepo.getMockTasks.collect {
                     _mockTasks.value = RequestState.Success(it)
                 }
             }
         } catch (e: Throwable) {
             _mockTasks.value = RequestState.Error(e)
         }
     }*/


    /*  fun getMockTasks() : MutableLiveData<RequestState<List<MockToDoTask>>> {
          val tasks = MutableLiveData<RequestState<List<MockToDoTask>>>()
          viewModelScope.launch(Dispatchers.IO) {
              val list = mockRepo.fetchTasks()
              Log.e("mockTasks","Called")
              tasks.postValue(RequestState.Success(list))
          }
          return tasks
     }
 */

    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)
    val id: MutableState<Int> = mutableStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")

    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)
    val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState: MutableState<String> = mutableStateOf("")


    private val _searchedTasks =
        MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val searchedTasks: StateFlow<RequestState<List<ToDoTask>>> = _searchedTasks

    private val _sortState = MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState: StateFlow<RequestState<Priority>> = _sortState

    private val _allTasks =
        MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<ToDoTask>>> = _allTasks

    private val _rememberState = MutableStateFlow(false)
    val rememberState: StateFlow<Boolean> = _rememberState

    init {
        getAllTasks()
        readSortState()
        readRememberState()
    }

    fun searchDatabase(searchQuery: String) {
        _searchedTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.searchDatabase("%$searchQuery%").collect { searchedTasks ->
                    _searchedTasks.value = RequestState.Success(searchedTasks)
                }
            }
        } catch (e: Throwable) {
            _searchedTasks.value = RequestState.Error(e)
        }
        searchAppBarState.value = SearchAppBarState.TRIGGERED
    }

    val lowPriorityTasks: StateFlow<List<ToDoTask>> =
        repository.sortByLowPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    val highPriorityTasks: StateFlow<List<ToDoTask>> =
        repository.sortByHighPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    private fun readSortState() {
        _sortState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readSortState
                    .map { Priority.valueOf(it) }
                    .collect {
                        _sortState.value = RequestState.Success(it)
                    }
            }
        } catch (e: Throwable) {
            _sortState.value = RequestState.Error(e)
        }
    }

    fun persistSortState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistSortState(priority = priority)
        }
    }

     fun readRememberState() {
        try {
            viewModelScope.launch {
                dataStoreRepository.readRememberState.collect {
                    _rememberState.value = it
                }
            }
        } catch (e: Throwable) {
            Log.e("readRememberState", "${e.message}")
        }
    }

    fun persistRememberState(remember: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistRememberState(remember = remember)
        }
    }


    private fun getAllTasks() {
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.getAllTasks.collect {
                    _allTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Throwable) {
            _allTasks.value = RequestState.Error(e)
        }
    }

    private val _selectedTask: MutableStateFlow<ToDoTask?> = MutableStateFlow(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repository.getSelectedTask(taskId).collect { selectedTask ->
                _selectedTask.value = selectedTask
            }
        }
    }

    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.addTask(toDoTask = toDoTask)
        }
        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.updateTask(toDoTask = toDoTask)
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.deleteTask(toDoTask = toDoTask)
        }

    }

    private fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTasks()
        }
    }

    fun handleDatabaseAction(action: Action) {
        when (action) {
            Action.ADD -> {
                addTask()
            }
            Action.UPDATE -> {
                updateTask()
            }
            Action.DELETE -> {
                deleteTask()
            }
            Action.DELETE_ALL -> {
                deleteAllTasks()
            }
            Action.UNDO -> {
                addTask()
            }
            else -> {

            }
        }
    }

    fun updateTaskFields(selectedTask: ToDoTask?) {
        if (selectedTask != null) {
            id.value = selectedTask.id
            title.value = selectedTask.title
            description.value = selectedTask.description
            priority.value = selectedTask.priority
        } else {
            id.value = 0
            title.value = ""
            description.value = ""
            priority.value = Priority.LOW
        }
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length < MAX_TITLE_LENGTH) {
            title.value = newTitle
        }
    }

    fun validateFields(): Boolean {
//        return title.value.isNotEmpty() && description.value.isNotEmpty()
        return title.value.isNotEmpty()
    }
}