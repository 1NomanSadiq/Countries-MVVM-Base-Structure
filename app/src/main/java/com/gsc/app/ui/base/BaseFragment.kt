package com.gsc.app.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.gsc.app.data.DataStorePreferenceRepository
import com.gsc.app.data.SharedPreference
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    @Inject
    lateinit var dataStorePrefs: DataStorePreferenceRepository

    @Inject
    lateinit var prefs: SharedPreference

    protected val binding: VB by lazy { inflateViewBinding(layoutInflater) }

    protected fun goBack() = findNavController().navigateUp()

    protected fun finishActivity() = requireActivity().finish()

    protected fun finishAffinity() = requireActivity().finishAffinity()

    protected abstract fun inflateViewBinding(inflater: LayoutInflater): VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root

    protected fun <T> LiveData<T>.observe(observer: Observer<in T>) = observe(this@BaseFragment, observer)

}