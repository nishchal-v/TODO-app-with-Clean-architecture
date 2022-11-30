package com.example.todo.presentation.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo.presentation.ui.ViewModelFactory

inline fun <reified T : ViewModel> AppCompatActivity.obtainViewModel(): Lazy<T> =
    lazy { ViewModelProvider(this, ViewModelFactory.getInstance(application))[T::class.java] }

inline fun <reified T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProvider(this, ViewModelFactory.getInstance(application))[viewModelClass]

inline fun <reified T : ViewModel> FragmentActivity.obtainViewModel(): Lazy<T> =
    lazy { ViewModelProvider(this, ViewModelFactory.getInstance(application))[T::class.java] }

inline fun <reified T : ViewModel> FragmentActivity.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProvider(this, ViewModelFactory.getInstance(application))[viewModelClass]

fun AppCompatActivity.closeSoftInputKeyboard() =
    currentFocus?.let { view ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

fun FragmentActivity.closeSoftInputKeyboard() =
    currentFocus?.let { view ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
