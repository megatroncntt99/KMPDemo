package viewmodel

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import model.BirdImage

data class BirdsUiState(val images:List<BirdImage> = emptyList())
class BirdViewModel :ViewModel(){
    override fun onCleared() {
        super.onCleared()
    }
}