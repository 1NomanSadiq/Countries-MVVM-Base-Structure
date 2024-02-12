package com.gsc.app.utils.misc

import androidx.recyclerview.widget.DiffUtil

typealias DiffUtilMaker<T> = () -> DiffUtil.ItemCallback<T>