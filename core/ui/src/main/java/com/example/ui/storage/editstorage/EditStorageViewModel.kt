package com.example.ui.storage.editstorage

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.domain.Currency
import com.example.domain.domainmodel.DomainColor
import com.example.domain.IEditStorage
import com.example.domain.TypeStorage
import com.example.domain.domainmodel.DomainStorage
import com.example.domain.reposytory.IColorRepository
import com.example.domain.reposytory.IStorageRepository
import com.example.domain.settings.ISettingsRepository
import com.example.navigation.INavigator
import com.example.navigation.NavigationRoute
import com.example.ui.storage.UIStatesDetailStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class EditStorageViewModel @Inject constructor(
    private val navigator: INavigator,
    private val colorRepo: IColorRepository,
    private val storageRepo: IStorageRepository,
    private val settings: ISettingsRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), IEditStorage {

    private val _uiState = MutableStateFlow(UIStatesDetailStorage())
    val uiState = _uiState.asStateFlow()
    val storageId = savedStateHandle.toRoute<NavigationRoute.EditStorage>().storageId
    init {
        viewModelScope.launch {
            val storage = storageRepo.getStorageById(storageId)
            if (storage==null){
                _uiState.update { it.copy(error = "Storage not found") }
            }
            else
            _uiState.update {
                it.copy(
                    name = storage.name,
                    note= storage.note,
                    typeStorage = storage.typeStorage,
                    currency = storage.currency,
                    color = storage.color,
                    availableColors = colorRepo.getAllColorsFlow(settings.userIdFlow.first()!!).first())
            }
        }
    }
    fun onSaveChangesStorage(){
        viewModelScope.launch {
            val storage = storageRepo.getStorageById(storageId)
            if (storage==null){
                _uiState.update { it.copy(error = "Storage not found") }
            }
            else{
                if(
                    _uiState.value.name == null||
                    _uiState.value.typeStorage == null||
                    _uiState.value.currency == null
                    ){
                    _uiState.update { it.copy(error = "Не все поля заполнены") }
                }
                else{
                    storageRepo.updateStorage(
                        DomainStorage(
                            id = storage.id,
                            userId = storage.userId,
                            name = _uiState.value.name!!,
                            note = _uiState.value.note,
                            typeStorage = _uiState.value.typeStorage!!,
                            currency = _uiState.value.currency!!,
                            color = _uiState.value.color,
                        )
                    )
                }
            }
        }
        onBack()
    }

    fun onDelete(){
        viewModelScope.launch {
            storageRepo.deleteStorage(storageId)
        }
        onBack()
    }

    fun onBack(){
        navigator.goBack()
    }

    override fun onNameChange(newName: String) {
        try {
            _uiState.update { it.copy(name = newName) }
        }
        catch (e: Exception){
            _uiState.update { it.copy(error = e.message) }
        }
    }

    override fun onNoteChange(newNote: String) {
        try {
            _uiState.update { it.copy(note = newNote) }
        }
        catch (e: Exception){
            _uiState.update { it.copy(error = e.message) }
        }
    }

    override fun onTypeChange(newType: TypeStorage) {
        try {
            _uiState.update { it.copy(typeStorage = newType) }
        }
        catch (e: Exception){
            _uiState.update { it.copy(error = e.message) }
        }
    }

    override fun onCurrencyChange(newCurrency: Currency) {
        try {
            _uiState.update { it.copy(currency = newCurrency) }
        }
        catch (e: Exception){
            _uiState.update { it.copy(error = e.message) }
        }
    }

    fun onColorChange(newColor: DomainColor?) {
        try {
            _uiState.update { it.copy(color = newColor) }
        }
        catch (e: Exception){
            _uiState.update { it.copy(error = e.message) }
        }
    }

    /**
    private val storageId: String = savedStateHandle.toRoute<NavigationRoute.EditStorage>().storageId
    //Состояние интерфейса
    private val _uiState = MutableStateFlow(DefaultStateDetailsStorage())
    val uiState: StateFlow<DefaultStateDetailsStorage> = combine(
    _uiState,
    colorService.colorListForPicker
    ) { state, colors ->
    state.copy(availableColors = colors)
    }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5000),
    initialValue = DefaultStateDetailsStorage()
    )

    init {
    loadStorage(storageId)
    }
    fun onBack(){
    navigator.goBack()
    }
    fun loadStorage(id: Long) {
    viewModelScope.launch {
    _uiState.update { it.copy(isLoading = true) }
    val storage = storageService.getStorage(id)
    _uiState.update { state ->
    when (storage) {
    is DomainState.Error -> state.copy(
    isLoading = false,
    error = "Storage not found"
    )
    is DomainState.Success -> {
    val storage = storage.domain
    state.copy(
    storage = storage,
    name = storage.name,
    note = storage.note ?: "",
    typeStorage = storage.typeStorage,
    currency = storage.currency,
    isStatistics = storage.isStatistics,
    isArchive = storage.isArchive,
    color = storage.color?.toUiState(),
    isLoading = false
    )
    }
    }
    }
    }
    }

    override fun onSaveColor(newColor: NewColor){
    viewModelScope.launch {
    val color = colorService.save(newColor)
    if (color!=null){
    val newList = _uiState.value.availableColors.toMutableList()
    newList.add(color.toUiState())
    _uiState.update {
    it.copy(
    availableColors = newList
    )
    }
    }
    else{
    _uiState.update { it.copy(error = "Ошибка сохранения цвета") }
    }
    }
    }

    override fun onNameChange(newName: String) {
    _uiState.update { it.copy(name = newName) }
    }

    override fun onNoteChange(newNote: String) {
    _uiState.update { it.copy(note = newNote) }
    }

    override fun onTypeChange(newType: TypeStorage) {
    _uiState.update { it.copy(typeStorage = newType) }
    }

    override fun onCurrencyChange(newCurrency: Currency) {
    _uiState.update { it.copy(currency = newCurrency) }
    }

    override fun onStatisticsChange(value: Boolean) {
    _uiState.update { it.copy(isStatistics = value) }
    }

    override fun onArchiveChange(value: Boolean) {
    _uiState.update { it.copy(isArchive = value) }
    }

    override fun onColorChange(newColor: ColorUIState?) {
    _uiState.update { it.copy(color = newColor) }
    }

    override fun toggleColorDeleteMode(enabled: Boolean) {
    _uiState.update { it.copy(isColorDeleteMode = enabled) }
    }

    override fun deleteColor(colorUiState: ColorUIState) {
    if (colorUiState is ColorUIState.DataBaseColor && colorUiState.color is UserColor) {
    viewModelScope.launch {
    colorService.delete(colorUiState.color as UserColor)
    }
    }
    _uiState.update {
    it.copy(
    availableColors = it.availableColors.filter { it != colorUiState }
    )
    }
    }

    fun saveChanges() {
    val currentState = _uiState.value
    if (currentState.name.isBlank()) {
    _uiState.update { it.copy(error = "Имя не может быть пустым") }
    return
    }

    viewModelScope.launch {
    val colorToSave: DomainColor? = when (val selectedColor = currentState.color) {
    is ColorUIState.DataBaseColor -> selectedColor.color
    is ColorUIState.LocalSystemColor -> {
    val colorSaved = colorService.save(selectedColor)
    if (colorSaved==null) {
    _uiState.update { it.copy(error = "Ошибка сохранения цвета") }
    cancel()
    }
    colorSaved
    }
    else -> null
    }

    val result = try {
    storageService.updateStorage(
    name = currentState.name,
    typeStorage = currentState.typeStorage,
    currency = currentState.currency,
    note = currentState.note,
    color = colorToSave,
    changingStorage = currentState.storage!!,
    isStatistic = currentState.isStatistics,
    isArchive = currentState.isArchive,
    )
    } catch (e: Exception) {
    DomainState.Error(e.message ?: "Unknown error")
    }
    // 3. Последнее обновление стейта по результату
    _uiState.update {
    when (result) {
    is DomainState.Success -> it.copy(isSaved = true, isLoading = false)
    is DomainState.Error -> it.copy(error = result.message, isLoading = false)
    }
    }
    }
    }

    fun deleteStorage() {
    val currentStorage = _uiState.value.storage ?: return
    viewModelScope.launch {
    storageService.deleteStorage(currentStorage)
    _uiState.update { it.copy(isSaved = true) } // Navigate back
    }
    }
     */

}
