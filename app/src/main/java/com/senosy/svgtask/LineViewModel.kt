package com.senosy.svgtask

import android.util.Log
import androidx.lifecycle.*
import com.senosy.svgtask.models.Line
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream

private const val TAG = "MainViewModel"

class LineViewModel(repo:LinesRepo):ViewModel() {
    private val _linesList = MutableLiveData<List<Line>>()
    val lineList :LiveData<List<Line>>
    get() = _linesList

    init {
        viewModelScope.launch {
            _linesList.value = repo.parseData()

        }
    }

    fun getLine(position: String?): Line? {
        return lineList.value?.find {
            it.id == position
        }
    }
    class LineViewModelFactory(private val linesRepo: LinesRepo):ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            (LineViewModel(linesRepo) as T)

    }
}